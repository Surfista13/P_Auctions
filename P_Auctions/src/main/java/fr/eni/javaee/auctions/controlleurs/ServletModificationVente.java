package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;
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

import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bll.CategorieManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Utilisateur;

/**
 * Servlet implementation class ServletModificationVente
 */
@WebServlet("/ServletModificationVente")
public class ServletModificationVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
List<Categorie> categories= new ArrayList<>();
	
	public void init(){
        //Liste des catégories
        CategorieManager categorieManager = CategorieManager.getCategorieManager();        
        categories = categorieManager.selectAllCategories();
        }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("listeCategories",categories);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ModificationVente.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String update = request.getParameter("Update");
		String delete = request.getParameter("Delete");
		HttpSession session = request.getSession();
		Utilisateur utilisateurConnecte = (Utilisateur)session.getAttribute("utilisateurConnecte");
		Categorie categorie = new Categorie();
		if(update!=null) {
			
			System.out.println("Vous pouvez mettre la vente a jour");
		ArticleVendu updateArticle = new ArticleVendu();
		int num = 4;
		//TODO RELIER A BRUNO
		updateArticle.setNoArticle(num);
		updateArticle.setNomArticle(request.getParameter("article"));
		updateArticle.setDescription(request.getParameter("description"));
		updateArticle.setDateDebutEncheres(LocalDate.parse (request.getParameter("dateDebut")));
		updateArticle.setDateFinEncheres(LocalDate.parse (request.getParameter("dateFin")));
		updateArticle.setMiseAPrix(Integer.parseInt(request.getParameter("prix")));
		updateArticle.getCategorie().setNoCategorie(Integer.parseInt (request.getParameter("listeCategories")));
			
		ArticleVendu maj = ArticleVenduManager.getArticleVenduManager().updateVente(updateArticle);	
		}else {
			
			ArticleVendu deleteArticle = new ArticleVendu();
			//TODO RECUPERER NUM ARTICLE
			int num = 15;
			deleteArticle.setNoArticle(num);
			
			ArticleVendu supp = ArticleVenduManager.getArticleVenduManager().deleteVente(deleteArticle);
			System.out.println("Votre vente est supprimé");
			
		}
	}

}
