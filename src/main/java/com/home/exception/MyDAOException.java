package com.home.exception;

import java.io.IOException;
import java.sql.SQLException;

public class MyDAOException extends Exception {

    public MyDAOException(String description, SQLException e) {
        super(description);
        addSuppressed(e);
    }

    public MyDAOException(String description, InstantiationException e) {
        super(description);
        initCause(e);
    }

    public MyDAOException(String description, IllegalAccessException e) {
        super(description);
        initCause(e);
    }

    public MyDAOException(String description, ClassNotFoundException e) {
        super(description);
        initCause(e);
    }

    public MyDAOException(String description, IOException e) {
        super(description);
        initCause(e);
    }

}
