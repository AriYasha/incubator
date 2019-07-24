package com.home.servlets;

import com.home.entity.User;
import com.home.dao.IUserImpl;
import com.home.entity.Account;
import com.home.exception.MyDAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(-1);
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            action = "/getUserIdByMaxSum";
        }

        switch (action) {
            case "/getUserIdByMaxSum":
                getUserIdByMaxSum(request, response);
                break;
            case "/getAccountByMaxSum":
                getAccountByMaxSum(request, response);
                break;
        }
    }

    private void getUserIdByMaxSum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            IUserImpl userDAO = (IUserImpl) session.getAttribute("userConnect");
            User user = userDAO.getUserNameByMaxSum();
            session.setAttribute("userName", user.getName());
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (MyDAOException e) {
            throw new ServletException("Some error with getUserIdByMaxSum method in controller", e);
        }
    }

    private void getAccountByMaxSum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            IUserImpl userDAO = (IUserImpl) session.getAttribute("userConnect");
            Account account = userDAO.getAccountByMaxSum();
            session.setAttribute("account", account.getAccount());
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (MyDAOException e) {
            throw new ServletException("Some error with getAccountByMaxSum method in controller", e);
        }
    }
}
