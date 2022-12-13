package fr.eni.javaee.auctions.dal;

import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;

public interface DAOEnchere{
	public abstract List<Enchere> selectAll();
	public abstract List<Enchere> selectByNameArticle(ArticleVendu articleVendu);
	public abstract List<Enchere> selectByCategorieEncheres(Categorie categorie);
}
