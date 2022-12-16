package fr.eni.javaee.auctions.testsunitaires;

import fr.eni.javaee.auctions.dal.ConnectionProvider;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ServletTestCnx")
public class ServletTestCnx extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try{
        Connection cnx = ConnectionProvider.getConnection();
        out.print("La connexion est " + ((cnx.isClosed()) ? "fermée" : "ouverte"));
        cnx.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        out.close();
    }
}
