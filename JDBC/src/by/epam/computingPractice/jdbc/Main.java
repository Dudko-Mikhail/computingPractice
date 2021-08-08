package by.epam.computingPractice.jdbc;

import by.epam.computingPractice.jdbc.dao.DBConnector;
import by.epam.computingPractice.jdbc.dao.MakerDAO;
import by.epam.computingPractice.jdbc.dao.SouvenirDAO;
import by.epam.computingPractice.jdbc.entity.Souvenir;
import by.epam.computingPractice.jdbc.dao.exception.DAOException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try (Connection connection = DBConnector.getConnection()) {
            MakerDAO makerDAO = new MakerDAO(connection);
            SouvenirDAO souvenirDAO = new SouvenirDAO(connection);
            for (int i = 0; i < 10; i++) {
                makerDAO.create("maker" + i, "country" + i);
                souvenirDAO.create(new Souvenir(i + 1, i + 1,
                        "souvenir" + i, Date.valueOf("2020-04-1" + i), new BigDecimal((10 * i) + "." + i)));
            }
            System.out.println(souvenirDAO.findByMaker(2));
            System.out.println(souvenirDAO.findByCountry("country3"));
            System.out.println(makerDAO.findByLessSouvenirPrices(new BigDecimal("300.45")));
            System.out.println(makerDAO.findBySouvenirNameAndProductionDate("souvenir0", Date.valueOf("2020-04-10")));

            System.out.println(makerDAO.findAll());
            System.out.println(souvenirDAO.findAll());
            System.out.println(makerDAO.delete(3));
            System.out.println(makerDAO.findAll());
            System.out.println(souvenirDAO.findAll());
        } catch (SQLException | DAOException e) {
            e.printStackTrace();
        }
    }
}