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
	String param1, param2, param3, param4, param5, param6, radio;

	public void init() {
		// Liste des catégories à l'initialisation de la servlet
		CategorieManager categorieManager = CategorieManager.getCategorieManager();
		categories = categorieManager.selectAllCategories();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Création de l'utilisateur connecté
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setNoUtilisateur(2);
		// Select de la liste des enchères de l'acheteur
		EncheresManager enchereManager = EncheresManager.getEnchereManager();
		List<Enchere> listeEnchere = new ArrayList<>();
		listeEnchere = encheresManager.selectAllEnchereByUser(utilisateur);
		request.setAttribute("liste", listeEnchere);
		String mesAchats = "mesAchats";
		String radio = "mesAchats";
		request.setAttribute("achat",mesAchats );
		request.setAttribute("type", radio);

		// Liste des catégories
		request.setAttribute("listeCategories", categories);

		// Dispatch vers jsp
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ListeArticlesVendusEncheresConnectée.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Liste des catégories
		request.setAttribute("listeCategories", categories);

		// Création de l'utilisateur connecté
		int x = 2;// user 2 en attente retour de la session
		Utilisateur user = new Utilisateur();
		user.setNoUtilisateur(x);

		// Passage de la catégorie précedemment séléctionnée
		Categorie categorie = new Categorie();
		categorie.setLibelle(request.getParameter("categories"));
		request.setAttribute("selected", categorie.getLibelle());

		// Passage à la jsp de la liste des catégories
		request.setAttribute("listeCategories", categories);

		// Initialisation d'un article pour stocker la recherche par nom
		ArticleVendu articleVendu = new ArticleVendu();
		articleVendu.setNomArticle(request.getParameter("recherche"));

		// Récupération de l'état des checkbox
		
		String param1 = request.getParameter("encheresOuvertes");
		String param2 = request.getParameter("encheresEnCours");
		String param3 = request.getParameter("encheresRemportees");
		String param4 = request.getParameter("ventesEnCours");
		String param5 = request.getParameter("ventesNonDebutees");
		String param6 = request.getParameter("ventesTerminees");

		// Récupération de l'état du bouton radio
		String radio = request.getParameter("connect");
		request.setAttribute("type", radio);

		// Transfert de l'état des bouton radio vers la jsp
		String mesAchats = "mesAchats";
		String mesVentes = "mesVentes";
		request.setAttribute("achat", mesAchats);
		request.setAttribute("vente", mesVentes);
		//if(radio.equals(mesVentes)) {request.setAttribute("etatBoutonVente", "checked");request.setAttribute("etatBoutonAchat", null);}
		//if(radio.equals(mesAchats)) {request.setAttribute("etatBoutonVente", null);request.setAttribute("etatBoutonAchat", "checked");}
		
		// Transfert de l'état des check box vers la jsp
		//if(param1 != null) {request.setAttribute("param1", "checked");}else{request.setAttribute("param1","");};
		//if(param2 == null) {request.setAttribute("param2", "");}else{request.setAttribute("param2", "checked");};
		//if(param3 == null) {request.setAttribute("param3", "");}else{request.setAttribute("param3", "checked");};
		//if(param4 == null) {request.setAttribute("param4", "");}else{request.setAttribute("param4", "checked");};
		//if(param5 == null) {request.setAttribute("param5", "");}else{request.setAttribute("param5", "checked");};
		//if(param6 == null) {request.setAttribute("param6", "");}else{request.setAttribute("param6", "checked");};

		// Filtre de la recherche utilisateur
		listeFiltree(user, articleVendu, categorie, radio, param1, param2, param3, param4, param5, param6, request);

		// Dispatch vers jsp
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ListeArticlesVendusEncheresConnectée.jsp");
		rd.forward(request, response);
	}

	// Méthode de filtre des enchères via recherche et liste catégorie
	public List<Enchere> rechercheEtCategorieEnchere(Utilisateur userConnecte, ArticleVendu articleVenduRecherche,
			Categorie categorieFiltree) {
		List<Enchere> listeEnchere = new ArrayList<>();
		if (categorieFiltree.getLibelle().equals("Toutes") && articleVenduRecherche.getNomArticle().isEmpty()) {
			listeEnchere = encheresManager.selectAllEnchereByUser(userConnecte);
		} else if (articleVenduRecherche.getNomArticle().isEmpty() && !categorieFiltree.getLibelle().equals("Toutes")) {
			listeEnchere = encheresManager.selectAllEnchereByUserByCategorie(userConnecte, categorieFiltree);
		} else if (categorieFiltree.getLibelle().equals("Toutes") && !articleVenduRecherche.getNomArticle().isEmpty()) {
			listeEnchere = encheresManager.selectAllEnchereByUserByArticleName(userConnecte, articleVenduRecherche);
		} else {
			listeEnchere = encheresManager.selectAllEnchereByUserByCategorieByArticleName(userConnecte,
					categorieFiltree, articleVenduRecherche);
		}
		return listeEnchere;
	}

	// Méthode de filtre des articles vendus via recherche et liste catégorie
	public List<ArticleVendu> rechercheEtCategorieArticleVendu(Utilisateur userConnecte,
			ArticleVendu articleVenduRecherche, Categorie categorieFiltree) {
		List<ArticleVendu> listeArticleVendu = new ArrayList<>();
		if (categorieFiltree.getLibelle().equals("Toutes") && articleVenduRecherche.getNomArticle().isEmpty()) {
			listeArticleVendu = articleVenduManager.selectByUser(userConnecte);
		} else if (articleVenduRecherche.getNomArticle().isEmpty() && !categorieFiltree.getLibelle().equals("Toutes")) {
			listeArticleVendu = articleVenduManager.selectByUserByCategorie(userConnecte, categorieFiltree);
		} else if (categorie.getLibelle().equals("Toutes") && !articleVenduRecherche.getNomArticle().isEmpty()) {
			listeArticleVendu = articleVenduManager.selectByUserByArticle(userConnecte, articleVenduRecherche);
		} else {
			listeArticleVendu = articleVenduManager.selectByUserByCategorieByArticleName(userConnecte, categorieFiltree,
					articleVenduRecherche);
		}
		return listeArticleVendu;
	}

	// Méthode de filtre par catégories d'enchères ou catégorie d'article vendus
	public void listeFiltree(Utilisateur user, ArticleVendu article, Categorie categorie, String radio, String param1,
			String param2, String param3, String param4, String param5, String param6, HttpServletRequest request) {
		List<Enchere> listeEnchere = new ArrayList<>();
		List<Enchere> listeEnchereFiltree = new ArrayList<>();
		List<ArticleVendu> listeArticleVendu = new ArrayList<>();
		List<ArticleVendu> listeArticleVenduFiltree = new ArrayList<>();
		if (radio.equals("mesAchats")) {
			listeEnchere = rechercheEtCategorieEnchere(user, article, categorie);

			if (param1 != null && param2 != null && param3 != null) {
				listeEnchereFiltree = listeEnchere.stream()
						.filter(entry -> entry.getArticleVendus().getEtatVente().equals("enCours")
								|| entry.getArticleVendus().getEtatVente().equals("nonDebutee")
								|| entry.getArticleVendus().getEtatVente().equals("terminee"))
						.collect(Collectors.toList());
			}
			if (param1 != null && param2 == null && param3 == null) {
				listeEnchereFiltree = listeEnchere.stream()
						.filter(entry -> entry.getArticleVendus().getEtatVente().equals("enCours"))
						.collect(Collectors.toList());
			}
			if (param1 != null && param2 != null && param3 == null) {
				listeEnchereFiltree = listeEnchere.stream()
						.filter(entry -> entry.getArticleVendus().getEtatVente().equals("enCours")
								|| entry.getArticleVendus().getEtatVente().equals("nonDebutee"))
						.collect(Collectors.toList());
			}
			if (param1 == null && param2 != null && param3 != null) {
				listeEnchereFiltree = listeEnchere.stream()
						.filter(entry -> entry.getArticleVendus().getEtatVente().equals("nonDebutee")
								|| entry.getArticleVendus().getEtatVente().equals("terminee"))
						.collect(Collectors.toList());
			}
			if (param1 != null && param2 == null && param3 != null) {
				listeEnchereFiltree = listeEnchere.stream()
						.filter(entry -> entry.getArticleVendus().getEtatVente().equals("enCours")
								|| entry.getArticleVendus().getEtatVente().equals("terminee"))
						.collect(Collectors.toList());
			}
			if (param1 == null && param2 == null && param3 == null) {
				listeEnchereFiltree = new ArrayList<>();
			}
			request.setAttribute("liste", listeEnchereFiltree);
		} else {
			listeArticleVendu = rechercheEtCategorieArticleVendu(user, article, categorie);
			if (param4 != null && param5 != null && param5 != null) {
				listeArticleVenduFiltree = listeArticleVendu.stream()
						.filter(entry -> entry.getEtatVente().equals("enCours")
								|| entry.getEtatVente().equals("nonDebutee") || entry.getEtatVente().equals("terminee"))
						.collect(Collectors.toList());
			}
			if (param4 != null && param5 == null && param5 == null) {
				listeArticleVenduFiltree = listeArticleVendu.stream()
						.filter(entry -> entry.getEtatVente().equals("enCours")).collect(Collectors.toList());
			}
			if (param4 != null && param5 != null && param5 == null) {
				listeArticleVenduFiltree = listeArticleVendu.stream().filter(
						entry -> entry.getEtatVente().equals("enCours") || entry.getEtatVente().equals("nonDebutee"))
						.collect(Collectors.toList());
			}
			if (param4 == null && param5 != null && param5 != null) {
				listeArticleVenduFiltree = listeArticleVendu.stream().filter(
						entry -> entry.getEtatVente().equals("nonDebutee") || entry.getEtatVente().equals("terminee"))
						.collect(Collectors.toList());
			}
			if (param4 != null && param5 == null && param5 != null) {
				listeArticleVenduFiltree = listeArticleVendu.stream().filter(
						entry -> entry.getEtatVente().equals("enCours") || entry.getEtatVente().equals("terminee"))
						.collect(Collectors.toList());
			}
			if (param4 == null && param5 == null && param6 == null) {
				listeArticleVenduFiltree = new ArrayList<>();
			}
			request.setAttribute("liste", listeArticleVenduFiltree);
		}
	}
}
