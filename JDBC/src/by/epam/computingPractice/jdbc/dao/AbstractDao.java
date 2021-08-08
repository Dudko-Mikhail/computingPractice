package by.epam.computingPractice.jdbc.dao;

import by.epam.computingPractice.jdbc.entity.Entity;
import by.epam.computingPractice.jdbc.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity> implements AutoCloseable {
    protected Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public abstract List<T> findAll() throws DAOException;

    public abstract Optional<T> findById (long id) throws DAOException;

    public abstract boolean delete(long id) throws DAOException;

    public abstract boolean create(T t) throws DAOException;

    public abstract T update(T t) throws DAOException;

    @Override
    public final void close() throws DAOException {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            }
            catch (SQLException e) {
                throw new DAOException("Failed to close the connection", e);
            }
            finally {
                connection = null;
            }
        }
    }

}
