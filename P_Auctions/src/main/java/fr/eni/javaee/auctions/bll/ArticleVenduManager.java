package fr.eni.javaee.auctions.bll;

import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Retrait;
import fr.eni.javaee.auctions.dal.DAOArticleVendu;
import fr.eni.javaee.auctions.dal.DAOFactory;

public class ArticleVenduManager {
	
	private static ArticleVenduManager articleManager;	
	private List<ArticleVendu> articlesVendu;
	private DAOArticleVendu daoArticleVendu = DAOFactory.getArticleVenduDAOImplSQLServer();
	
	private ArticleVenduManager() {
	}
	
	public static ArticleVenduManager getArticleVenduManager(){
		if(articleManager == null) {
			articleManager = new ArticleVenduManager();
		}
		return articleManager;		
	}
	
	public List<ArticleVendu> selectAllArticleVendu (){
		articlesVendu = daoArticleVendu.selectAll();
		return articlesVendu;		
	}
	
	public List<ArticleVendu> selectByNameArticleEncheres (String name){
		articlesVendu = daoArticleVendu.selectByNameArticle(name);
		return articlesVendu;		
	}
	
	public List<ArticleVendu> selectByCategorieEncheres (Categorie categorie){
		articlesVendu = daoArticleVendu.selectByCategorie(categorie);
		return articlesVendu;		
	}
	
	//Ajout Nicolas 15 decembre
	
	public void insertNewArticle(ArticleVendu newArticle,Retrait retrait) {
			
	}
	
	
}
