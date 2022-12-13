package fr.eni.javaee.auctions.bll;

import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.dal.DAOEnchere;
import fr.eni.javaee.auctions.dal.DAOFactory;

public class EnchereManager {
	
	private static EnchereManager enchereManager;	
	private List<Enchere> encheres;
	private DAOEnchere daoEnchere = DAOFactory.getEnchereDAOImplSQLServer();
	
	private EnchereManager() {
	}
	
	public static EnchereManager getEnchereManager(){
		if(enchereManager == null) {
			enchereManager = new EnchereManager();
		}
		return enchereManager;		
	}
	
	public List<Enchere> selectAllEncheres (){
		encheres = daoEnchere.selectAll();
		return encheres;		
	}
	
	public List<Enchere> selectByNameArticleEncheres (ArticleVendu articleVendu){
		encheres = daoEnchere.selectByNameArticle(articleVendu);
		return encheres;		
	}
	
	public List<Enchere> selectByCategorieEncheres (Categorie categorie){
		encheres = daoEnchere.selectByCategorieEncheres(categorie) ;
		return encheres;		
	}
	
	
}
