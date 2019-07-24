package com.home.dao;

import com.home.entity.Account;
import com.home.entity.User;
import com.home.exception.MyDAOException;

import java.util.List;

public interface IAccount {
    public List<Account> getAll() throws MyDAOException;




}
