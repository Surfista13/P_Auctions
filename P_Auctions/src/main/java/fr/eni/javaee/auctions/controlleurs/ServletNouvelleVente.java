package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bll.CategorieManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Retrait;
import fr.eni.javaee.auctions.bo.Utilisateur;

/**
 * Servlet implementation class ServletNouvelleVente
 */
@WebServlet("/ServletNouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	List<Categorie> categories= new ArrayList<>();
	
	public void init(){
        //Liste des catégories
        CategorieManager categorieManager = CategorieManager.getCategorieManager();        
        categories = categorieManager.selectAllCategories();
        }
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   //Liste des catégories
    
		request.setAttribute("listeCategories",categories);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/NouvelleVente.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//1.Données arrivant de la requête
		
		Categorie categorie = new Categorie();
		HttpSession session = request.getSession();
		Utilisateur utilisateurConnecte = (Utilisateur)session.getAttribute("utilisateurConnecte"); 
		ArticleVendu newArticle = new ArticleVendu();
		
		newArticle.setNomArticle(request.getParameter("article"));
		newArticle.setDescription(request.getParameter("description"));
		newArticle.setDateDebutEncheres(LocalDate.parse (request.getParameter("dateDebut")));
		newArticle.setDateFinEncheres(LocalDate.parse (request.getParameter("dateFin")));
		newArticle.setMiseAPrix(Integer.parseInt(request.getParameter("prix")));
		newArticle.getUtilisateur().setNoUtilisateur(utilisateurConnecte.getNoUtilisateur());
		newArticle.getCategorie().setNoCategorie(Integer.parseInt (request.getParameter("listeCategories")));

		Retrait retrait = new Retrait();
		retrait.setRue(request.getParameter("rue"));
		retrait.setCodePostal(request.getParameter("codePostal"));
		retrait.setVille(request.getParameter("ville"));
		
		ArticleVendu newVente = ArticleVenduManager.getArticleVenduManager().insertNewArticle(newArticle, retrait);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ListeArticlesVendusEncheresConnectee.jsp");
		rd.forward(request, response);
	}

}
