package fr.eni.javaee.auctions.dal;

import java.sql.SQLException;
import java.util.List;

import fr.eni.javaee.auctions.bo.Utilisateur;

public interface UtilisateurDAO {
	
	public Utilisateur  validerConnexion(String pseudo,String email,String motDePasse) throws SQLException;
	
	

}
