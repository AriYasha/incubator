package com.home.dao;

import com.home.entity.User;
import com.home.exception.MyDAOException;
import com.home.entity.Account;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class IUserImpl implements IUser, AutoCloseable {

    private Connection connection;
    private PreparedStatement getAll;
    private PreparedStatement getUserByMaxSum;

    public IUserImpl() throws MyDAOException {
        Properties property = new Properties();
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream reader = classLoader.getResourceAsStream("properties.properties");
        try {
            property.load(reader);
            String driverName = property.getProperty("driver");
            String url = property.getProperty("url");
            String user = property.getProperty("user");
            String pass = property.getProperty("pass");
            reader.close();
            Class.forName(driverName).newInstance();
            connection = DriverManager.getConnection(url, user, pass);
            getAll = connection.prepareStatement("SELECT idUser, nameUser, surnameUser FROM user");
            getUserByMaxSum = connection.prepareStatement("SELECT idUser, SUM(account) AS summary FROM account GROUP BY idUser ORDER BY summary DESC LIMIT 1");
        } catch (IOException e) {
            throw new MyDAOException("Error in constructor with file opening", e);
        } catch (InstantiationException e) {
            throw new MyDAOException("Error in constructor: object Class not found", e);
        } catch (IllegalAccessException e) {
            throw new MyDAOException("Error in constructor with access to DB", e);
        } catch (ClassNotFoundException e) {
            throw new MyDAOException("Error in constructor with ClassPath", e);
        } catch (SQLException e) {
            throw new MyDAOException("Error in constructor with SQLQuery", e);
        }
    }

    @Override
    public List<User> getAll() throws MyDAOException {
        List<User> userList = new LinkedList<>();
        try (ResultSet resultSet = getAll.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                String idUser = resultSet.getString("idUser");
                String nameUser = resultSet.getString("nameUser");
                String surnameUser = resultSet.getString("surnameUser");
                user.setName(nameUser);
                user.setSureName(surnameUser);
                user.setUserId(Integer.parseInt(idUser));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            throw new MyDAOException("Error in getAll method", e);
        }
    }

    @Override
    public User findById(int idUser) throws MyDAOException {
        User user = new User();
        try (ResultSet resultSet = getAll.executeQuery()) {
            while (resultSet.next()) {
                int idForSearch = Integer.parseInt(resultSet.getString("idUser"));
                if (idForSearch == idUser) {
                    String nameUser = resultSet.getString("nameUser");
                    String surnameUser = resultSet.getString("surnameUser");
                    user.setName(nameUser);
                    user.setSureName(surnameUser);
                    user.setUserId(idUser);
                }
            }
            return user;
        } catch (SQLException e) {
            throw new MyDAOException("Error in findById method", e);
        }
    }

    @Override
    public User getUserNameByMaxSum() throws MyDAOException {
        User user = new User();
        try (ResultSet resultSet = getUserByMaxSum.executeQuery()) {
            while (resultSet.next()) {
                String idUser = resultSet.getString("idUser");
                user = findById(Integer.parseInt(idUser));
            }
            return user;
        } catch (SQLException e) {
            throw new MyDAOException("Error in getUserNameByMaxSum method", e);
        }
    }

    @Override
    public Account getAccountByMaxSum() throws MyDAOException {
        Account account = new Account();
        try (ResultSet resultSet = getUserByMaxSum.executeQuery()) {
            while (resultSet.next()) {
                String total = resultSet.getString("summary");
                account.setAccount(Integer.parseInt(total));
            }
            return account;
        } catch (SQLException e) {
            throw new MyDAOException("Error with getAccountByMaxSum method", e);
        }
    }

    @Override
    public void close() throws MyDAOException {
        SQLException exception = new SQLException("Some errors in closing");
        if (getAll != null) {
            try {
                getAll.close();
            } catch (SQLException e) {
                exception.addSuppressed(e);
            }
        }
        if (getUserByMaxSum != null) {
            try {
                getUserByMaxSum.close();
            } catch (SQLException e) {
                exception.addSuppressed(e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                exception.addSuppressed(e);
            }
        }
        if (exception.getSuppressed().length > 0) {
            throw new MyDAOException("errors with closing PreperedStatement in user dao", exception);
        }

    }
}
