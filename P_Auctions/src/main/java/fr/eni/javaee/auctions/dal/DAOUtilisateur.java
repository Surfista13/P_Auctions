package fr.eni.javaee.auctions.dal;

import fr.eni.javaee.auctions.bo.Utilisateur;

public interface DAOUtilisateur {

	public void insert(Utilisateur utilisateur) throws DALException;

	public Utilisateur validerConnexion(String pseudo, String email, String motDePasse) throws DALException;

	public Utilisateur selectUserById(int idUser) throws DALException;

	public Utilisateur updateProfil(Utilisateur utilisateur);

	public void deleteProfil(Utilisateur utilisateur);

	public void updateCredit(Utilisateur utilisateurPrecedent, Utilisateur utilisateurNx);

	public void updateCredit(Utilisateur utilisateur);

}
