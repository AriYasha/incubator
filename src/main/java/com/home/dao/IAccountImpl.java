package com.home.dao;

import com.home.entity.Account;
import com.home.entity.User;
import com.home.exception.MyDAOException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public  class IAccountImpl  implements IAccount{


    private Connection connection;
    private PreparedStatement getAll;


    public IAccountImpl() throws MyDAOException {
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
            getAll = connection.prepareStatement("SELECT accountId, account, userId FROM account");
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
    public List<Account> getAll() throws MyDAOException {
        List<Account> accountList = new LinkedList<>();
        try (ResultSet resultSet = getAll.executeQuery()) {
            while (resultSet.next()) {
                Account myaccount = new Account();
                int accountId = resultSet.getInt("accountId");
                int account = resultSet.getInt("account");
                int userId = resultSet.getInt("userId");
                myaccount.setAccountId(accountId);
                myaccount.setAccount(account);
                myaccount.setUserId(userId);
                accountList.add(myaccount);
            }
            return accountList;
        } catch (SQLException e) {
            throw new MyDAOException("Error in getAll method", e);
        }
    }
}
