package com.home.dao;

import com.home.entity.User;
import com.home.exception.MyDAOException;
import com.home.entity.Account;

import java.util.List;

public interface IUser {
    public List<User> getAll() throws MyDAOException;

    public User findById(int userId) throws MyDAOException;

    public User getUserNameByMaxSum() throws MyDAOException;

    public Account getAccountByMaxSum() throws MyDAOException;
}
