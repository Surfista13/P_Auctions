package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bll.EncheresManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Enchere;

/**
 * Servlet implementation class ServletDetailsArticle
 */
@WebServlet("/ServletDetailsArticle")
public class ServletDetailsArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	ArticleVendu article = new ArticleVendu();
	List<Enchere> encheres = new ArrayList<>();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		int idArticle = Integer.parseInt(request.getParameter("idArticle"));
		ArticleVendu articleRecherche = new ArticleVendu();
		articleRecherche.setNoArticle(5);
		ArticleVenduManager articleManager = ArticleVenduManager.getArticleVenduManager();
		article = articleManager.selectByIDArticle(articleRecherche);
		
		EncheresManager encheresManager = EncheresManager.getEnchereManager();
		encheres = encheresManager.selectEnchereByArticleID(articleRecherche);
		
		request.setAttribute("article", article);
		request.setAttribute("listeEncheres", encheres);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/DetailsVentesEncheres.jsp");
		rd.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

}
