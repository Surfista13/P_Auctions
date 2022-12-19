package fr.eni.javaee.auctions.dal;

import java.sql.SQLException;

import fr.eni.javaee.auctions.bll.BusinessException;
import fr.eni.javaee.auctions.bo.Utilisateur;

public interface DAOUtilisateur {
	
	public void insert (Utilisateur utilisateur) throws BusinessException;
	public Utilisateur  validerConnexion(String pseudo,String email,String motDePasse) throws SQLException;
	public Utilisateur selectUserById(int idUser);

}
