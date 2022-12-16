package fr.eni.javaee.auctions.dal;

import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Retrait;

public interface DAOArticleVendu{
	public abstract List<ArticleVendu> selectAll();
	public abstract List<ArticleVendu> selectByNameArticle(String name);
	public abstract List<ArticleVendu> selectByCategorie(Categorie categorie);
	
	//Ajout Nicolas 15 decembre
	public void  insertNewArticle(ArticleVendu newArticle, Retrait retrait);
}
