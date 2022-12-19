package fr.eni.javaee.auctions.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.bo.Retrait;
import fr.eni.javaee.auctions.dal.DAOArticleVendu;
import fr.eni.javaee.auctions.dal.DAOFactory;

public class ArticleVenduManager {
	
	private static ArticleVenduManager articleManager;	
	private List<ArticleVendu> articlesVendu = new ArrayList<>();
	private DAOArticleVendu daoArticleVendu = DAOFactory.getArticleVenduDAOImplSQLServer();
	
	private ArticleVenduManager() {
	}
	
	public static ArticleVenduManager getArticleVenduManager(){
		if(articleManager == null) {
			articleManager = new ArticleVenduManager();
		}
		return articleManager;		
	}
	
	//select tous les articles vendus dont l'enchères n'est pas terminées
	public List<ArticleVendu> selectAllArticleVendu (){
		articlesVendu = daoArticleVendu.selectAll();
		articlesVendu = setEtatVente(articlesVendu);
		return articlesVendu;		
	}
	//select tous les articles vendus par nom d'article (pas de restriction sur l'état de la vente) (like)
	public List<ArticleVendu> selectByNameArticleEncheres (ArticleVendu articleVendu){
		articlesVendu = daoArticleVendu.selectByNameArticle(articleVendu);
		articlesVendu = setEtatVente(articlesVendu);
		return articlesVendu;		
	}
	//select tous les articles vendus pour une catégorie
	public List<ArticleVendu> selectByCategorieEncheres (Categorie categorie){
		articlesVendu = daoArticleVendu.selectByCategorie(categorie);
		articlesVendu = setEtatVente(articlesVendu);
		return articlesVendu;		
	}
	//select tous les articles vendus pour une catégorie et un nom d'article
	public List<ArticleVendu> selectByCategorieByArticle (Categorie categorie, ArticleVendu articleVendu){
		articlesVendu = daoArticleVendu.selectByCategorieByArticle(categorie, articleVendu);
		articlesVendu = setEtatVente(articlesVendu);
		return articlesVendu;		
	}
	//select tous les articles vendus pour un utilisateur pour un nom d'article
	public List<ArticleVendu> selectByUserByArticle (Utilisateur user, ArticleVendu articleVendu){
		articlesVendu = daoArticleVendu.selectByUserByNameArticle(user, articleVendu);
		articlesVendu = setEtatVente(articlesVendu);
		return articlesVendu;		
	}
	//select tous les articles vendus pour un utilisateur pour une catégorie
	public List<ArticleVendu> selectByUserByCategorie (Utilisateur user, Categorie categorie){
		articlesVendu = daoArticleVendu.selectByUserByCategorie(user, categorie);
		articlesVendu = setEtatVente(articlesVendu);
		return articlesVendu;		
	}
	//select tous les articles vendus pour un utilisateur pour une catégorie pour un article name
	public List<ArticleVendu> selectByUserByCategorieByArticleName (Utilisateur user, Categorie categorie, ArticleVendu article){
		articlesVendu = daoArticleVendu.selectByUserByCategorieByArticleName(user, categorie, article);
		articlesVendu = setEtatVente(articlesVendu);
		return articlesVendu;		
	}
	//select tous les articles vendus pour un utilisateur
		public List<ArticleVendu> selectByUser (Utilisateur user){
			articlesVendu = daoArticleVendu.selectByUser(user);
			articlesVendu = setEtatVente(articlesVendu);
			return articlesVendu;		
		}
	

	
	//Méthode qui définit l'état d'une vente
	public List<ArticleVendu> setEtatVente (List<ArticleVendu> articles){
		for(ArticleVendu article : articlesVendu) {
			LocalDate dateDebutEnchere = article.getDateDebutEncheres();
			LocalDate dateFinEnchere = article.getDateFinEncheres();
			
			if(dateDebutEnchere.isAfter(LocalDate.now())) {
				article.setEtatVente("nonDebutee");
			};
			if((dateDebutEnchere.isBefore(LocalDate.now()) || dateDebutEnchere.equals(LocalDate.now()))&& (dateFinEnchere.isAfter(LocalDate.now()) || dateFinEnchere.equals(LocalDate.now()) )) {
				article.setEtatVente("enCours");
			};
			if(dateFinEnchere.isBefore(LocalDate.now()) ) {
				article.setEtatVente("terminee");
			};						
		}
		return articlesVendu;	
	}
	
	
	//Ajout Nicolas 15 decembre
	
	public void insertNewArticle(ArticleVendu newArticle,Retrait retrait) {
			
	}
	
	
}
