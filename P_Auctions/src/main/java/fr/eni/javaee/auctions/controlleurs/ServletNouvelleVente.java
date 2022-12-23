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
import fr.eni.javaee.auctions.bll.UtilisateurManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Retrait;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.dal.DALException;

/**
 * Servlet implementation class ServletNouvelleVente
 */
@WebServlet("/ServletNouvelleVente")
public class ServletNouvelleVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	List<Categorie> categories = new ArrayList<>();
	Utilisateur userConnecte = new Utilisateur();

	public void init() {
		// Liste des cat�gories
		CategorieManager categorieManager = CategorieManager.getCategorieManager();
		categories = categorieManager.selectAllCategories();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("/ServletListeEncheresNonConnecte");
			rd.forward(request, response);
		}
		userConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");
		request.setAttribute("pseudo", userConnecte.getPseudo());
		request.setAttribute("credit", userConnecte.getCredit());
		request.setAttribute("idConnect", userConnecte.getNoUtilisateur());
		session.setAttribute("utilisateurConnecte", userConnecte);
	
		//Mise à jour crédit
		UtilisateurManager um = new UtilisateurManager();
		Utilisateur userMaJ = new Utilisateur();
		try {
			userMaJ = um.selectByUserId(userConnecte.getNoUtilisateur());
		} catch (DALException e) {
			response.sendRedirect("erreurDAL.html");
		}
		request.setAttribute("credit", userMaJ.getCredit());
		
	
		request.setAttribute("listeCategories", categories);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/NouvelleVente.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Redirige vers la page d'accueil non connecté si la session est nulle
		HttpSession session = request.getSession(false);
		// Redirige vers la page d'accueil non connecté si la session est nulle
		if (session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("/ServletListeEncheresNonConnecte");
			rd.forward(request, response);
		}
		Utilisateur userConnecte = new Utilisateur();
		userConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");
		request.setAttribute("pseudo", userConnecte.getPseudo());
		request.setAttribute("credit", userConnecte.getCredit());
		request.setAttribute("id", userConnecte.getNoUtilisateur());

		// 1.Donn�es arrivant de la requ�te

		Categorie categorie = new Categorie();
		session = request.getSession();
		Utilisateur utilisateurConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");
		ArticleVendu newArticle = new ArticleVendu();

		newArticle.setNomArticle(request.getParameter("article"));
		newArticle.setDescription(request.getParameter("description"));
		newArticle.setDateDebutEncheres(LocalDate.parse(request.getParameter("dateDebut")));
		newArticle.setDateFinEncheres(LocalDate.parse(request.getParameter("dateFin")));
		newArticle.setMiseAPrix(Integer.parseInt(request.getParameter("prix")));
		newArticle.setPrixVente(Integer.parseInt(request.getParameter("prix")));
		newArticle.getUtilisateur().setNoUtilisateur(utilisateurConnecte.getNoUtilisateur());
		newArticle.getCategorie().setNoCategorie(Integer.parseInt(request.getParameter("listeCategories")));

		Retrait retrait = new Retrait();
		retrait.setRue(request.getParameter("rue"));
		retrait.setCodePostal(request.getParameter("codePostal"));
		retrait.setVille(request.getParameter("ville"));

		ArticleVendu newVente = ArticleVenduManager.getArticleVenduManager().insertNewArticle(newArticle, retrait);

		RequestDispatcher rd = request.getRequestDispatcher(
				"/ServletEncheresConnectees?connect=mesAchats&categories=Toutes&recherche=&encheresOuvertes=1&encheresEnCours=2&encheresRemportees=3");
		rd.forward(request, response);
	}

}
