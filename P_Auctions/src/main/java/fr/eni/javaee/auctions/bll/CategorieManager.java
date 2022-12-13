package fr.eni.javaee.auctions.bll;

import java.util.List;

import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.dal.DAOCategorie;
import fr.eni.javaee.auctions.dal.DAOArticleVendu;
import fr.eni.javaee.auctions.dal.DAOFactory;

public class CategorieManager {
	private static CategorieManager categorieManager;	
	private List<Categorie> categories;
	private DAOCategorie daoCategorie = DAOFactory.getCategorieDAOImplSQLServer();
	
	private CategorieManager() {
	}
	
	public static CategorieManager getCategorieManager(){
		if(categorieManager == null) {
			categorieManager = new CategorieManager();
		}
		return categorieManager;		
	}
	
	public List<Categorie> selectAllCategories (){
		categories = daoCategorie.selectAll();
		return categories;		
	}
	
	public int deleteCategories (Categorie categorie){
		int rowDeleted = 0;
		rowDeleted = daoCategorie.deleteCategorie(categorie);
		return rowDeleted;
	}
	
	
}
