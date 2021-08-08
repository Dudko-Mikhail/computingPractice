package by.epam.computingPractice.jdbc.dao;

import by.epam.computingPractice.jdbc.entity.Souvenir;
import by.epam.computingPractice.jdbc.dao.exception.DAOException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class SouvenirDAO extends AbstractDao<Souvenir> {
    private static final String SELECT_ALL_SOUVENIR = """
            SELECT id, maker_id, name, production_date, price
            FROM souvenir
            """;
    private static final String SELECT_SOUVENIR_BY_ID = """
                SELECT id, maker_id, name, production_date, price
                FROM souvenir
                WHERE id = ?
            """;
    private static final String DELETE_SOUVENIR_BY_ID = """
                DELETE FROM souvenir
                WHERE id = ?
            """;
    private static final String CREATE_SOUVENIR_BY_MAKER_ID_NAME_PRODUCTION_DATE_AND_PRICE = """
                INSERT souvenir (maker_id, name, production_DATE, price)
                VALUES (?, ?, ?, ?)
            """;
    private static final String SELECT_ALL_SOUVENIR_BY_MAKER_ID = """
                SELECT id, maker_id, name, production_date, price
                FROM souvenir
                WHERE souvenir.maker_id = ?
            """;
    private static final String SELECT_ALL_SOUVENIR_BY_COUNTRY = """
                SELECT s.id, s.maker_id, s.name, s.production_date, s.price
                FROM maker AS m JOIN souvenir AS s
                                  ON s.maker_id = m.id
                WHERE m.country = ?
            """;

    public SouvenirDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Souvenir> findAll() throws DAOException {
        try (var statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_SOUVENIR);
            return getListSouvenirByResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Optional<Souvenir> findById(long id) throws DAOException {
        try (var preparedStatement = connection.prepareStatement(SELECT_SOUVENIR_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();
            var makerList = getListSouvenirByResultSet(result);
            return makerList.isEmpty() ? Optional.empty() : Optional.of(makerList.get(0));

        } catch (SQLException e) {
            throw new DAOException("Error in getting souvenir by id", e);
        }
    }

    public List<Souvenir> findByCountry(String country) throws DAOException {
        try (var preparedStatement = connection.prepareStatement(SELECT_ALL_SOUVENIR_BY_COUNTRY)) {
            preparedStatement.setString(1, country);
            var resultSet = preparedStatement.executeQuery();
            return getListSouvenirByResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Souvenir> findByMaker(long makerId) throws DAOException {
        try (var preparedStatement = connection.prepareStatement(SELECT_ALL_SOUVENIR_BY_MAKER_ID)) {
            preparedStatement.setLong(1, makerId);
            var resultSet = preparedStatement.executeQuery();
            return getListSouvenirByResultSet(resultSet);
        } catch (SQLException e) {
            throw  new DAOException(e);
        }
    }

    @Override
    public boolean delete(long id) throws DAOException {
        Optional<Souvenir> optionalMaker = findById(id);
        if (optionalMaker.isPresent()) {
            try (var preparedStatement = connection.prepareStatement(DELETE_SOUVENIR_BY_ID)) {
                preparedStatement.setLong(1, id);
                return preparedStatement.executeUpdate() == 1;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        else {
            throw new DAOException(new NoSuchElementException("No souvenir with the given id"));
        }
    }

    @Override
    public boolean create(Souvenir souvenir) throws DAOException {
        try (var preparedStatement = connection.prepareStatement(CREATE_SOUVENIR_BY_MAKER_ID_NAME_PRODUCTION_DATE_AND_PRICE)) {
            preparedStatement.setLong(1, souvenir.getMakerId());
            preparedStatement.setString(2, souvenir.getName());
            preparedStatement.setDate(3, souvenir.getProductionDate());
            preparedStatement.setBigDecimal(4, souvenir.getPrice());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Souvenir update(Souvenir souvenir) throws DAOException  {
        Optional<Souvenir> optionalMaker = findById(souvenir.getId());
        if (optionalMaker.isPresent()) {
            try (var preparedStatement = connection.prepareStatement(SELECT_SOUVENIR_BY_ID,
                    ResultSet.TYPE_SCROLL_SENSITIVE , ResultSet.CONCUR_UPDATABLE)) {
                preparedStatement.setLong(1, souvenir.getId());
                var resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultSet.updateLong("maker_id", souvenir.getMakerId());
                    resultSet.updateString("name", souvenir.getName());
                    resultSet.updateDate("production_date", souvenir.getProductionDate());
                    resultSet.updateBigDecimal("price", souvenir.getPrice());
                    resultSet.updateRow();
                }
                resultSet.beforeFirst();
                var list = getListSouvenirByResultSet(resultSet);
                return list.get(0);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        else {
            throw new DAOException(new NoSuchElementException("No souvenir with the given id"));
        }
    }

    private List<Souvenir> getListSouvenirByResultSet(ResultSet resultSet) throws SQLException {
        List<Souvenir> souvenirs = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            long makerId = resultSet.getLong("maker_id");
            String name = resultSet.getString("name");
            Date date = resultSet.getDate("production_date");
            BigDecimal price = resultSet.getBigDecimal("price");
            souvenirs.add(new Souvenir(id, makerId, name, date, price));
        }
        return souvenirs;
    }
}
