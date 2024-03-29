package fr.eni.javaee.auctions.dal;

import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Retrait;
import fr.eni.javaee.auctions.bo.Utilisateur;

public interface DAOArticleVendu {
	public List<ArticleVendu> selectAll();

	public List<ArticleVendu> selectByNameArticle(ArticleVendu articleVendu);

	public List<ArticleVendu> selectByCategorie(Categorie categorie);

	public List<ArticleVendu> selectByCategorieByArticle(Categorie categorieToFind, ArticleVendu articleVendu);

	public List<ArticleVendu> selectByUser(Utilisateur user);

	public List<ArticleVendu> selectByUserByNameArticle(Utilisateur user, ArticleVendu articleVendu);

	public List<ArticleVendu> selectByUserByCategorie(Utilisateur user, Categorie categorie);

	public List<ArticleVendu> selectByUserByCategorieByArticleName(Utilisateur user, Categorie categorieRecherchee,
			ArticleVendu articleVendu);

	public ArticleVendu insertNewArticle(ArticleVendu newArticle, Retrait retrait);

	public ArticleVendu selectByIDArticle(ArticleVendu articleVendu);

	public abstract ArticleVendu updateVente(ArticleVendu articleVendu);

	public abstract void deleteVente(ArticleVendu articleVendu);

	public int updatePrixVente(int prixVente, int noArticle);
}
