package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bll.CategorieManager;
import fr.eni.javaee.auctions.bll.EncheresManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;

/**
 * Servlet implementation class ServletEncheresConnectees
 */
@WebServlet("/ServletEncheresConnectees")
public class ServletEncheresConnectees extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	List<Categorie> categories = new ArrayList();
	EncheresManager encheresManager = EncheresManager.getEnchereManager();
	ArticleVenduManager articleVenduManager = ArticleVenduManager.getArticleVenduManager();
	Utilisateur user = new Utilisateur();
	Categorie categorie = new Categorie();
	ArticleVendu articleVendu = new ArticleVendu();
	String param1,param2,param3,param4,param5,param6,radio;
	
	public void init(){
		//Liste des catégories
		CategorieManager categorieManager = CategorieManager.getCategorieManager();		
		categories = categorieManager.selectAllCategories();	
	  }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//HttpSession session = request.getSession();
		//Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
		Utilisateur util = new Utilisateur("bob");
		request.setAttribute("utilisateur", util);
		//Liste des article vendus en cours
		ArticleVenduManager articleManager = ArticleVenduManager.getArticleVenduManager();
		List<ArticleVendu> articles = new ArrayList<>();
		//TODO articles = articleManager
		request.setAttribute("liste",articles);
		//Liste des catégories
		request.setAttribute("listeCategories",categories);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ListeArticlesVendusEncheresConnectée.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//HttpSession session = request.getSession();
		//int x = session.getAttribuet(idUtilisateur);
		int x = 2;
		Utilisateur user = new Utilisateur();
		Categorie categorie = new Categorie();
		ArticleVendu articleVendu = new ArticleVendu();
				
		articleVendu.setNomArticle(request.getParameter("recherche"));
		categorie.setLibelle(request.getParameter("categories"));		
		user.setNoUtilisateur(x);
	
		String param1 = request.getParameter("encheresOuvertes");
		String param2 = request.getParameter("encheresEnCours");
		String param3 = request.getParameter("encheresRemportees");
		String param4 = request.getParameter("ventesEnCours");
		String param5 = request.getParameter("ventesNonDebutees");
		String param6 = request.getParameter("ventesTerminees");
		String radio = request.getParameter("connect");

		listeFiltree(user,articleVendu,categorie,radio,param1,param2,param3,param4,param5,param6,request);
			
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ListeArticlesVendusEncheresConnectée.jsp");
		rd.forward(request, response);		
	}



	public List<Enchere> rechercheEtCategorieEnchere (Utilisateur userConnecte, ArticleVendu articleVenduRecherche, Categorie categorieFiltree){
	    List<Enchere> listeEnchere = new ArrayList<>();    
	    if(categorie.getLibelle().equals("Toutes") && articleVenduRecherche.getNomArticle().isEmpty()) {
	    	listeEnchere = encheresManager.selectAllEnchereByUser(userConnecte);
	        } else if(articleVenduRecherche.getNomArticle().isEmpty() && !categorieFiltree.getLibelle().equals("Toutes")) {
	        	listeEnchere = encheresManager.selectAllEnchereByUserByCategorie(userConnecte, categorieFiltree);
	        } else if(categorie.getLibelle().equals("Toutes")&& !articleVenduRecherche.getNomArticle().isEmpty()){
	        	listeEnchere = encheresManager.selectAllEnchereByUserByArticleName(userConnecte, articleVenduRecherche);
	        } else {
	        	listeEnchere = encheresManager.selectAllEnchereByUserByCategorieByArticleName(userConnecte, categorieFiltree, articleVenduRecherche);
	    } 
	    return listeEnchere;
	}

	public List<ArticleVendu> rechercheEtCategorieArticleVendu(Utilisateur userConnecte, ArticleVendu articleVenduRecherche, Categorie categorieFiltree){
	    List<ArticleVendu> listeArticleVendu = new ArrayList<>();
	    if(categorie.getLibelle().equals("Toutes") && articleVenduRecherche.getNomArticle().isEmpty()) {
	    	listeArticleVendu = articleVenduManager.selectByUser(userConnecte);
	    } else if(articleVenduRecherche.getNomArticle().isEmpty() && !categorie.getLibelle().equals("Toutes")) {
	    	listeArticleVendu = articleVenduManager.selectByUserByCategorie(userConnecte, categorieFiltree);
	    } else if(categorie.getLibelle().equals("Toutes")&& !articleVenduRecherche.getNomArticle().isEmpty()){
	    	listeArticleVendu= articleVenduManager.selectByUserByArticle(userConnecte, articleVenduRecherche);
	    } else {
	    	listeArticleVendu = articleVenduManager.selectByUserByCategorieByArticleName(userConnecte, categorieFiltree, articleVenduRecherche);
	    }
	    return listeArticleVendu;
	}

	public void listeFiltree (Utilisateur user, ArticleVendu article, Categorie categorie, String radio,String param1, String param2, String param3, String param4, String param5, String param6,HttpServletRequest request){
	    List<Enchere> listeEnchere = new ArrayList<>();
	    List<Enchere> listeEnchereFiltree = new ArrayList<>();
	    List<ArticleVendu> listeArticleVendu = new ArrayList<>();
	    List<ArticleVendu> listeArticleVenduFiltree = new ArrayList<>();

	    if(radio.equals("mesAchats")){
	        listeEnchere = rechercheEtCategorieEnchere(user, article, categorie);
	    
	        if(param1!= null && param2!= null && param3!= null){
	            listeEnchereFiltree = listeEnchere.stream()
	                    .filter(entry -> entry.getArticleVendus().getEtatVente().equals("enCours") || entry.getArticleVendus().getEtatVente().equals("nonDebutee") || entry.getArticleVendus().getEtatVente().equals("terminee"))
	                    .collect(Collectors.toList());
	        }
	        if(param1!= null && param2 == null && param3 == null){
	            listeEnchereFiltree = listeEnchere.stream()
	                    .filter(entry -> entry.getArticleVendus().getEtatVente().equals("enCours"))
	                    .collect(Collectors.toList());
	        }
	        if(param1!= null && param2!= null && param3== null){
	            listeEnchereFiltree = listeEnchere.stream()
	                    .filter(entry -> entry.getArticleVendus().getEtatVente().equals("enCours") || entry.getArticleVendus().getEtatVente().equals("nonDebutee"))
	                    .collect(Collectors.toList());
	        }
	        if(param1== null && param2!= null && param3!= null){
	            listeEnchereFiltree = listeEnchere.stream()
	                    .filter(entry -> entry.getArticleVendus().getEtatVente().equals("nonDebutee") || entry.getArticleVendus().getEtatVente().equals("terminee"))
	                    .collect(Collectors.toList());
	        }
	        if(param1!= null && param2== null && param3!= null){
	            listeEnchereFiltree = listeEnchere.stream()
	                    .filter(entry -> entry.getArticleVendus().getEtatVente().equals("enCours") || entry.getArticleVendus().getEtatVente().equals("terminee"))
	                    .collect(Collectors.toList());
	        }
	        if(param1== null && param2== null && param3== null){
	            listeEnchereFiltree = new ArrayList<>();
	        }
	        request.setAttribute("liste",listeEnchereFiltree);
	    } else {
	        listeArticleVendu = rechercheEtCategorieArticleVendu (user,article,categorie);   
	         if(param4!= null && param5!= null && param5!= null){
	             listeArticleVenduFiltree = listeArticleVendu.stream()
	                    .filter(entry -> entry.getEtatVente().equals("enCours") || entry.getEtatVente().equals("nonDebutee") || entry.getEtatVente().equals("terminee"))
	                    .collect(Collectors.toList());
	        }
	        if(param4!= null && param5== null && param5== null){
	            listeArticleVenduFiltree= listeArticleVendu.stream()
	                    .filter(entry -> entry.getEtatVente().equals("enCours"))
	                    .collect(Collectors.toList());
	        }
	        if(param4!= null && param5!= null && param5== null){
	            listeArticleVenduFiltree = listeArticleVendu.stream()
	                    .filter(entry -> entry.getEtatVente().equals("enCours") || entry.getEtatVente().equals("nonDebutee"))
	                    .collect(Collectors.toList());
	        }
	        if(param4== null && param5!= null && param5!= null){
	            listeArticleVenduFiltree = listeArticleVendu.stream()
	                    .filter(entry -> entry.getEtatVente().equals("nonDebutee") || entry.getEtatVente().equals("terminee"))
	                    .collect(Collectors.toList());
	        }
	        if(param4!= null && param5== null && param5!= null){
	            listeArticleVenduFiltree= listeArticleVendu.stream()
	                    .filter(entry -> entry.getEtatVente().equals("enCours") || entry.getEtatVente().equals("terminee"))
	                    .collect(Collectors.toList());
	        }
	        if(param4== null && param5 == null && param6 == null){
	            listeArticleVenduFiltree =  new ArrayList<>();
	        }
	        request.setAttribute("liste",listeArticleVenduFiltree);
	}
	    
	}
	
	
	
	
}
