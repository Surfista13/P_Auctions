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
import fr.eni.javaee.auctions.bll.CategorieManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;

/**
 * Servlet implementation class ServletListeEncheresNonConnecte
 */
@WebServlet("/ServletListeEncheresNonConnecte")
public class ServletListeEncheresNonConnecte extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	List<Categorie> categories = new ArrayList();
	
	public void init(){
		//Liste des catégories
		CategorieManager categorieManager = CategorieManager.getCategorieManager();		
		categories = categorieManager.selectAllCategories();
		
		
	  }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {				
		//Liste des article vendus en cours
		ArticleVenduManager articleManager = ArticleVenduManager.getArticleVenduManager();
		List<ArticleVendu> articles = new ArrayList<>();
		articles = articleManager.selectAllArticleVendu();
		request.setAttribute("liste",articles);
		//Liste des catégories
		request.setAttribute("listeCategories",categories);
		
		RequestDispatcher     rd = request.getRequestDispatcher("/WEB-INF/ListeArctileVendusNonConnecte.jsp");
        rd.forward(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		List<ArticleVendu> articles = new ArrayList<>();
		ArticleVenduManager articleManager = ArticleVenduManager.getArticleVenduManager();
		Categorie categorie = new Categorie();		
		categorie.setLibelle(request.getParameter("categories"));
		ArticleVendu articleVendu = new ArticleVendu();
		articleVendu.setNomArticle(request.getParameter("recherche"));
		
		if(categorie.getLibelle().equals("Toutes") && articleVendu.getNomArticle().isEmpty()) {
			doGet(request, response);
		} else if(articleVendu.getNomArticle().isEmpty() && !categorie.getLibelle().equals("Toutes")) {
			articles = articleManager.selectByCategorieEncheres(categorie);
		} else if(categorie.getLibelle().equals("Toutes")&& !articleVendu.getNomArticle().isEmpty()){
			articles = articleManager.selectByNameArticleEncheres(articleVendu);
		} else {
			articles = articleManager.selectByCategorieByArticle(categorie, articleVendu);
		}
		request.setAttribute("liste",articles);
		request.setAttribute("listeCategories",categories);
		request.setAttribute("selected", categorie.getLibelle());
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ListeArctileVendusNonConnecte.jsp");
        rd.forward(request,response);
       
    }

}
