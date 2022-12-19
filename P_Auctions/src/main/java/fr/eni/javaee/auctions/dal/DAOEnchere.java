package fr.eni.javaee.auctions.dal;

import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;

public interface DAOEnchere {

	public List<Enchere> selectAllEnchereByUser(Utilisateur user);

	public List<Enchere> selectAllEnchereByUserByArticleName(Utilisateur user, ArticleVendu article);

	public List<Enchere> selectAllEnchereByUserByCategorie(Utilisateur user, Categorie categorie);

	public List<Enchere> selectAllEnchereByUserByCategorieByArticleName(Utilisateur user, Categorie categorieRecherchee,ArticleVendu articleVendu);

	public List<Enchere> selectEnchereByByArticleID(ArticleVendu articleVendu);
}
