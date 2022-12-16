package fr.eni.javaee.auctions.bll;

import java.sql.SQLException;

import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.dal.DAOFactoryNR;
import fr.eni.javaee.auctions.dal.UtilisateurDAO;

public class UtilisateurManager {
	
	
	
	private static UtilisateurManager instance;
	
	public static UtilisateurManager getInstance() {
		if(instance ==null) {
			instance=new UtilisateurManager();
		}
		
		return instance;
	}
	
	private UtilisateurManager() {}
	
	public Utilisateur validerConnexion(String pseudo, String email, String motDePasse) {
		Utilisateur unUtilisateur=null;
		try {
			unUtilisateur = DAOFactoryNR.getUtilisateurDAO().validerConnexion(pseudo, email, motDePasse);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return unUtilisateur;		
	}
	

}
