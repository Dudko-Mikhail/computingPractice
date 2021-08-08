package by.epam.computingPractice.jdbc.dao;

import by.epam.computingPractice.jdbc.entity.Maker;
import by.epam.computingPractice.jdbc.dao.exception.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class MakerDAO extends AbstractDao<Maker> {
    private static final String SELECT_ALL_MAKER = """
            SELECT id, name, country
            FROM maker
            """;
    private static final String SELECT_ALL_MAKER_BY_COUNTRY = """
                SELECT id, name, country
                FROM maker
                WHERE country = ?
            """;
    private static final String SELECT_ALL_MAKER_BY_NAME = """
                SELECT id, name, country
                FROM maker
                WHERE name = ?
            """;
    private static final String SELECT_MAKER_BY_ID = """
                SELECT id, name, country
                FROM maker
                WHERE id = ?
            """;
    private static final String SELECT_ALL_MAKER_BY_SOUVENIR_NAME_AND_PRODUCTION_DATE = """
                SELECT DISTINCT m.id, m.name, m.country
                FROM souvenir AS s JOIN maker AS m ON m.id = s.id
                WHERE s.name = ? AND s.production_date = ?
            """;
    private static final String DELETE_MAKER_BY_ID = """
                DELETE FROM maker
                WHERE id = ?
            """;
    private static final String CREATE_MAKER_BY_NAME_AND_COUNTRY = """
                INSERT maker (name, country)
                VALUES (?, ?)
            """;
    private static final String SELECT_ALL_MAKER_WHOSE_SOUVENIR_PRICES_LESS_THAN_GIVEN = """
                SELECT m.id, m.name, m.country
                FROM maker AS m
                WHERE m.id in (SELECT maker_id
                               FROM souvenir
                               GROUP BY maker_id
                               HAVING MAX(price) < ?)
            """;

    public MakerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Maker> findAll() throws DAOException {
        try (var statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_MAKER);
            return getListMakerByResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Maker> findAllByCountry(String country) throws DAOException {
        try (var preparedStatement = connection.prepareStatement(SELECT_ALL_MAKER_BY_COUNTRY)) {
            preparedStatement.setString(1, country);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getListMakerByResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Maker> findByLessSouvenirPrices(BigDecimal price) throws DAOException {
        try (var preparedStatement = connection.prepareStatement(SELECT_ALL_MAKER_WHOSE_SOUVENIR_PRICES_LESS_THAN_GIVEN)) {
            preparedStatement.setBigDecimal(1, price);
            var resultSet = preparedStatement.executeQuery();
            return getListMakerByResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Maker> findAllByName(String name) throws DAOException {
        try (var preparedStatement = connection.prepareStatement(SELECT_ALL_MAKER_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getListMakerByResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<Maker> findBySouvenirNameAndProductionDate(String name, Date date) throws DAOException {
        try (var preparedStatement = connection.prepareStatement(SELECT_ALL_MAKER_BY_SOUVENIR_NAME_AND_PRODUCTION_DATE)) {
            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, date);
            var resultSet = preparedStatement.executeQuery();
            return getListMakerByResultSet(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }

    }

    @Override
    public Optional<Maker> findById(long id) throws DAOException {
        try (var preparedStatement = connection.prepareStatement(SELECT_MAKER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();
            var makerList = getListMakerByResultSet(result);
            return makerList.isEmpty() ? Optional.empty() : Optional.of(makerList.get(0));

        } catch (SQLException e) {
            throw new DAOException("Error in getting maker by id", e);
        }
    }

    /*
        delete a maker with his souvenirs
     */
    @Override
    public boolean delete(long id) throws DAOException {
        Optional<Maker> optionalMaker = findById(id);
        if (optionalMaker.isPresent()) {
            try (var preparedStatement = connection.prepareStatement(DELETE_MAKER_BY_ID)) {
                preparedStatement.setLong(1, id);
                return preparedStatement.executeUpdate() == 1;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        else {
            throw new DAOException(new NoSuchElementException("No maker with the given id"));
        }
    }

    @Override
    public boolean create(Maker maker) throws DAOException { // TODO check
        try (var preparedStatement = connection.prepareStatement(CREATE_MAKER_BY_NAME_AND_COUNTRY)) {
            preparedStatement.setString(1, maker.getName());
            preparedStatement.setString(2, maker.getCountry());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public boolean create(String name, String country) throws DAOException {
        try (var preparedStatement = connection.prepareStatement(CREATE_MAKER_BY_NAME_AND_COUNTRY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, country);
            return  preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DAOException("Error in creating maker", e);
        }
    }

    @Override
    public Maker update(Maker maker) throws DAOException { // TODO
        Optional<Maker> optionalMaker = findById(maker.getId());
        if (optionalMaker.isPresent()) {
            try (var preparedStatement = connection.prepareStatement(SELECT_MAKER_BY_ID,
                        ResultSet.TYPE_SCROLL_SENSITIVE , ResultSet.CONCUR_UPDATABLE)) {
                preparedStatement.setLong(1, maker.getId());
                var resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    resultSet.updateString("name", maker.getName());
                    resultSet.updateString("country", maker.getCountry());
                    resultSet.updateRow();
                }
                resultSet.beforeFirst();
                var list = getListMakerByResultSet(resultSet);
                return list.get(0);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        else {
            throw new DAOException(new NoSuchElementException("No maker with the given id"));
        }
    }

    private List<Maker> getListMakerByResultSet(ResultSet resultSet) throws SQLException {
        List<Maker> makers = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String country = resultSet.getString("country");
            makers.add(new Maker(id, name, country));
        }
        return  makers;
    }
}
