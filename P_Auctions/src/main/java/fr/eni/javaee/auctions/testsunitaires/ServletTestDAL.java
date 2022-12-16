package fr.eni.javaee.auctions.testsunitaires;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.dal.ConnectionProvider;
import fr.eni.javaee.auctions.dal.DAOCategorie;
import fr.eni.javaee.auctions.dal.DAOArticleVendu;
import fr.eni.javaee.auctions.dal.DAOFactory;

/**
 * Servlet implementation class ServletTestDAL
 */
@WebServlet("/ServletTestDAL")
public class ServletTestDAL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletTestDAL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		//Test1: select all categories
		PrintWriter out = response.getWriter();
        DAOCategorie categorieDAO = DAOFactory.getCategorieDAOImplSQLServer();
        List<Categorie> categories = new ArrayList<>();
        categories = categorieDAO.selectAll();
        for (Categorie categorie : categories){
                out.print("Test selectall: " + categorie + "\n");
            }
        out.print("\n");
        
        //Test2: delete catégories
        Categorie c1 = new Categorie(4,"Maison");
        PrintWriter out2 = response.getWriter();
        DAOCategorie categorieDAO2 = DAOFactory.getCategorieDAOImplSQLServer();
        int nbDeletedRow = categorieDAO2.deleteCategorie(c1);
        out2.print(nbDeletedRow);       
        out2.print("\n");

		//Test3: select all articles vendus
		PrintWriter out3 = response.getWriter();
        DAOArticleVendu articleDAO3 = DAOFactory.getArticleVenduDAOImplSQLServer();
        List<ArticleVendu> articles = new ArrayList<>();
        articles = articleDAO3.selectAll();
        for (ArticleVendu article : articles){
        	out3.print("Test select all articles: " + article +"\n");
        } 
        out3.print("\n");
		
        //Test4: select enchere par nom article
        PrintWriter out4 = response.getWriter();
        DAOArticleVendu articleDAO4 = DAOFactory.getArticleVenduDAOImplSQLServer();
        List<ArticleVendu> articles2 = new ArrayList<>();
        ArticleVendu a1 = new ArticleVendu();
        a1.setNomArticle("Brouette");
        articles2 = articleDAO4.selectByNameArticle(a1);
        for (ArticleVendu article2 : articles2){
        	out4.print("Test select by name: " + article2 +"\n");
        } 
        out4.print("\n");


        //Test5: select enchere par categorie
        Categorie c2 = new Categorie(3,"Auto");
        PrintWriter out5 = response.getWriter();
        DAOArticleVendu articleDAO5 = DAOFactory.getArticleVenduDAOImplSQLServer();
        List<ArticleVendu> articles3 = new ArrayList<>();
        articles3 = articleDAO5.selectByCategorie(c2);
        for (ArticleVendu article3 : articles3){
            out5.print("Test selec par catégorie: " + article3 + "\n");
        } 
        out5.print("\n");
 
        out.close();
        out2.close();
        out3.close();
        out4.close();
        out5.close();
          
	}
}
