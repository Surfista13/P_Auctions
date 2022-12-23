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
import javax.servlet.http.HttpSession;

import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bll.EncheresManager;
import fr.eni.javaee.auctions.bll.UtilisateurManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.dal.DALException;

/**
 * Servlet implementation class ServletDetailsArticle
 */
@WebServlet("/ServletDetailsArticle")
public class ServletDetailsArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ArticleVendu article = new ArticleVendu();
	List<Enchere> encheres = new ArrayList<>();
	Utilisateur userConnecte = new Utilisateur();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Création de l'utilisateur connecté
		HttpSession session = request.getSession();
		userConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");
		request.setAttribute("pseudo", userConnecte.getPseudo());
		request.setAttribute("credit", userConnecte.getCredit());
		request.setAttribute("idConnect", userConnecte.getNoUtilisateur());
		session.setAttribute("utilisateurConnecte", userConnecte);

		int idArticle = Integer.parseInt(request.getParameter("idArticle"));
		ArticleVendu articleRecherche = new ArticleVendu();
		articleRecherche.setNoArticle(idArticle);
		ArticleVenduManager articleManager = ArticleVenduManager.getArticleVenduManager();
		article = articleManager.selectByIDArticle(articleRecherche);
		EncheresManager encheresManager = EncheresManager.getEnchereManager();
		encheres = encheresManager.selectEnchereByArticleID(articleRecherche);
		request.setAttribute("article", article);
		request.setAttribute("listeEncheres", encheres);

		Enchere meilleurEnchere = new Enchere();
		meilleurEnchere = calculMeilleurOffre(encheres);
		request.setAttribute("meilleurEnchere", meilleurEnchere);

		int typePage = 0;
		int typePage2 = 0;
		if (meilleurEnchere.getUtilisateur().getNoUtilisateur() == userConnecte.getNoUtilisateur()
				&& (meilleurEnchere.getArticleVendus().getEtatVente().equals("terminee")
						|| meilleurEnchere.getArticleVendus().getEtatVente().equals("remportee"))) {
			typePage = 1;
		} else if (meilleurEnchere.getUtilisateur().getNoUtilisateur() != userConnecte.getNoUtilisateur()
				&& (meilleurEnchere.getArticleVendus().getEtatVente().equals("terminee")
						|| meilleurEnchere.getArticleVendus().getEtatVente().equals("remportee"))) {
			typePage = 2;
		} else {
			typePage = 3;
		}
		if (meilleurEnchere.getUtilisateur().getNoUtilisateur() == userConnecte.getNoUtilisateur()) {
			typePage2 = 1;
		}

		request.setAttribute("typePage", typePage);
		request.setAttribute("typePage2", typePage2);

		//Mise à jour crédit
		UtilisateurManager um = new UtilisateurManager();
		Utilisateur userMaJ = new Utilisateur();
		try {
			userMaJ = um.selectByUserId(userConnecte.getNoUtilisateur());
		} catch (DALException e) {
			response.sendRedirect("erreurDAL.html");
		}
		request.setAttribute("credit", userMaJ.getCredit());
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/DetailsVentesEncheres.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public Enchere calculMeilleurOffre(List<Enchere> liste) {
		int meilleurOffre = 0;
		String pseudoMeilleurOffre = null;
		int idUserMeilleurOffre = 0;
		String etatEnchere = "";

		for (Enchere e : liste) {
			if (e.getMontant_enchere() >= meilleurOffre) {
				meilleurOffre = e.getMontant_enchere();
				pseudoMeilleurOffre = e.getUtilisateur().getPseudo();
				idUserMeilleurOffre = e.getUtilisateur().getNoUtilisateur();
				etatEnchere = e.getArticleVendus().getEtatVente();
			}
		}
		Enchere enchere = new Enchere();
		enchere.setMontant_enchere(meilleurOffre);
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPseudo(pseudoMeilleurOffre);
		utilisateur.setNoUtilisateur(idUserMeilleurOffre);
		enchere.setUtilisateur(utilisateur);
		ArticleVendu article = new ArticleVendu();
		article.setEtatVente(etatEnchere);
		enchere.setArticleVendus(article);
		return enchere;
	}

}
