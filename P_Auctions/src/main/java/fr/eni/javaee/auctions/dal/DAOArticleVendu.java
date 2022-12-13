package fr.eni.javaee.auctions.dal;

import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;

public interface DAOArticleVendu{
	public abstract List<ArticleVendu> selectAll();
	public abstract List<ArticleVendu> selectByNameArticle(String name);
	public abstract List<ArticleVendu> selectByCategorie(Categorie categorie);
}
