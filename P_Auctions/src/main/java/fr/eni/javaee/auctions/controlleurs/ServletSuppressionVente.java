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
import fr.eni.javaee.auctions.bll.CategorieManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Utilisateur;

/**
 * Servlet implementation class ServletSuppressionVente
 */
@WebServlet("/ServletSuppressionVente")
public class ServletSuppressionVente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	List<Categorie> categories = new ArrayList<>();
	ArticleVendu article = new ArticleVendu();
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

		HttpSession session = request.getSession();
		userConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");
		request.setAttribute("pseudo", userConnecte.getPseudo());

		request.setAttribute("idConnect", userConnecte.getNoUtilisateur());
		session.setAttribute("utilisateurConnecte", userConnecte);

		int idArticle = Integer.parseInt(request.getParameter("idArticle"));
		ArticleVendu articleRecherche = new ArticleVendu();
		articleRecherche.setNoArticle(idArticle);
		ArticleVenduManager articleManager = ArticleVenduManager.getArticleVenduManager();
		article = articleManager.selectByIDArticle(articleRecherche);
		request.setAttribute("article", article);

		request.setAttribute("listeCategories", categories);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/SuppressionVente.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur utilisateurConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");
		Categorie categorie = new Categorie();
		ArticleVendu articleRecherche = new ArticleVendu();

		ArticleVendu deleteArticle = new ArticleVendu();
		int num = Integer.parseInt(request.getParameter("majArticle"));
		deleteArticle.setNoArticle(num);

		ArticleVendu supp = ArticleVenduManager.getArticleVenduManager().deleteVente(deleteArticle);

		RequestDispatcher rd = request.getRequestDispatcher(
				"/ServletEncheresConnectees?connect=mesAchats&categories=Toutes&recherche=&encheresOuvertes=1&encheresEnCours=2&encheresRemportees=3");
		rd.forward(request, response);
	}

}
