package fr.eni.javaee.auctions.testsunitaires;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.auctions.bll.CategorieManager;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.dal.ConnectionProvider;

/**
 * Servlet implementation class ServletTestManager
 */
@WebServlet("/ServletTestManager")
public class ServletTestManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Test1		
		PrintWriter out = response.getWriter();
        CategorieManager cm = CategorieManager.getCategorieManager();
		
		List<Categorie> categories = new ArrayList<>();
		categories = cm.selectAllCategories();
		for (Categorie a : categories){
		    out.print("Categorie: " + a + "\n");
		}
        
        //Test2
        PrintWriter out2 = response.getWriter();
        Categorie c1 = new Categorie(13,"Maison");
        CategorieManager cm2 = CategorieManager.getCategorieManager();            
		int rowDeleted = cm2.deleteCategories(c1);
		out2.print(rowDeleted);

		
		
		
        out.close();	
	}


}
