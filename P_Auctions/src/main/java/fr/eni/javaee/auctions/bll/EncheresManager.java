package fr.eni.javaee.auctions.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.dal.DAOEnchere;
import fr.eni.javaee.auctions.dal.DAOFactory;

public class EncheresManager {

	private static EncheresManager encheresManager;	
	private List<Enchere> encheres = new ArrayList<>();
	private DAOEnchere daoEncheres = DAOFactory.getEnchereDAOImplSQLServer();
	
	private EncheresManager() {
	}
	
	public static EncheresManager getEnchereManager(){
		if(encheresManager == null) {
			encheresManager = new EncheresManager();
		}
		return encheresManager;		
	}
	
	public List<Enchere> selectAllEnchereByUser (Utilisateur user){
		encheres = daoEncheres.selectAllEnchereByUser(user);
		setEtatEnchere(encheres);
		return encheres;		
	}
	
	public List<Enchere> selectAllEnchereByUserByArticleName (Utilisateur user, ArticleVendu article){
		encheres = daoEncheres.selectAllEnchereByUserByArticleName(user, article);
		setEtatEnchere(encheres);
		return encheres;		
	}
	
	public List<Enchere> selectAllEnchereByUserByCategorie (Utilisateur user, Categorie categorie){
		encheres = daoEncheres.selectAllEnchereByUserByCategorie(user, categorie);
		setEtatEnchere(encheres);
		return encheres;		
	}
	
	public List<Enchere> selectAllEnchereByUserByCategorieByArticleName (Utilisateur user, Categorie categorie,ArticleVendu article){
		encheres = daoEncheres.selectAllEnchereByUserByCategorieByArticleName(user, categorie, article);
		setEtatEnchere(encheres);
		return encheres;		
	}
	
	
	//Méthode qui définit l'état d'une enchère
	public List<Enchere> setEtatEnchere (List<Enchere> encheres){
		for(Enchere enchere : encheres) {
			if(enchere.getArticleVendus().getDateDebutEncheres().isAfter(LocalDate.now())) {
				enchere.getArticleVendus().setEtatVente("nonDebutee");
			};
			if(enchere.getArticleVendus().getDateDebutEncheres().isBefore(LocalDate.now())&& enchere.getArticleVendus().getDateFinEncheres().isAfter(LocalDate.now()) ) {
				enchere.getArticleVendus().setEtatVente("enCours");
			};
			if(enchere.getArticleVendus().getDateFinEncheres().isBefore(LocalDate.now()) ) {
				enchere.getArticleVendus().setEtatVente("terminee");
			};						
		}		
		return encheres;
	}	
}
