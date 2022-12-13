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
import fr.eni.javaee.auctions.dal.DAOEnchere;
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
        

        //Test3: select enchere par nom article
        ArticleVendu a1 = new ArticleVendu(1,"Ballon","Ballon",LocalDate.of(2022, 5, 20),LocalDate.of(2022, 12, 20),100,200,"en cours");
        PrintWriter out3 = response.getWriter();
        DAOEnchere enchereDAO3 = DAOFactory.getEnchereDAOImplSQLServer();
        List<Enchere> encheres = new ArrayList<>();
        encheres = enchereDAO3.selectByNameArticle(a1);
        out3.print(a1.getNomArticle());
        out3.print(encheres.toString());
        for (Enchere enchere : encheres){
        	out3.print("Test select par nom  article: " + enchere + "\n");
        } 
        out3.print("\n");
  
  
  
        //Test4: select enchere par categorie
        PrintWriter out4 = response.getWriter();
        DAOEnchere enchereDAO4 = DAOFactory.getEnchereDAOImplSQLServer();
        List<Enchere> encheres2 = new ArrayList<>();
        encheres2 = enchereDAO4.selectByCategorieEncheres(c1);
        for (Enchere enchere2 : encheres2){
            out4.print("Test selec par catégorie: " + enchere2 + "\n");
        } 
        out4.print("\n");
        out4.print("test");

        out2.close();	      
	}
}
