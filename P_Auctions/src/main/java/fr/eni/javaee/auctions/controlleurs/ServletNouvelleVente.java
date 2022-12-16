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

import fr.eni.javaee.auctions.bll.CategorieManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Retrait;

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
		
		/*HttpSession session =request.getSession();	
		session.setAttribute("utilisateur", utilisateur);*/
		
		ArticleVendu newArticle = new ArticleVendu();
		Categorie categorie = new Categorie();
		
		
		
		
		newArticle.setNomArticle(request.getParameter("article"));
		newArticle.setDescription(request.getParameter("description"));
		newArticle.setDateDebutEncheres(LocalDate.parse (request.getParameter("dateDebut")));
		newArticle.setDateFinEncheres(LocalDate.parse (request.getParameter("dateFin")));
		newArticle.setMiseAPrix(Integer.parseInt(request.getParameter("prix")));
		//TODO recuperer no utilisateur
		
		newArticle.getUtilisateur().setNoUtilisateur(Integer.parseInt(request.getParameter("")));
		//TODO recuperer no categorie

		
		Retrait retrait = new Retrait();
		
		retrait.setRue(request.getParameter("rue"));
		retrait.setCodePostal(request.getParameter("codePostal"));
		retrait.setVille(request.getParameter("ville"));
		
		
		
	/*	String article = request.getParameter("article");
		String description = request.getParameter("description");
		String categorie = request.getParameter("categories");
		String photo = request.getParameter("photo");
		String prixVente = request.getParameter("prix");
		String dateDebutEnchere = request.getParameter("deteDebut");
		String dateFinEnchere = request.getParameter("dateFin");
		String rue = request.getParameter("rue");
		String codePostal = request.getParameter("codePostal");
		String ville = request.getParameter("ville");
		
		
		//2.Conversion des données de la requête vers le bon type
		
		LocalDate dateDebut = null;
		
		dateDebut = LocalDate.parse(dateDebutEnchere);
		
		LocalDate dateFin = null;
		
		dateFin = LocalDate.parse(dateFinEnchere);
		
		int miseAPrix;
		
		miseAPrix = Integer.parseInt(prixVente);
		
		System.out.println("dateDebut" + dateDebut);
		System.out.println("dateFin" + dateFin);
		System.out.println("miseAPRIX"+ miseAPrix);
		
		//3.Transmission vers la BLL
		
		ArticleVendu newArticle = new ArticleVendu(article,description,dateDebut,dateFin,miseAPrix);
		Retrait retrait = new Retrait(rue,codePostal,ville);
		*/
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/NouvelleVente.jsp");
		rd.forward(request, response);
	}

}
