package fr.eni.javaee.auctions.bll;

import java.sql.SQLException;

import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.dal.DAOFactory;

public class UtilisateurManager {
	
	private static UtilisateurManager instance;
	
	public static UtilisateurManager getInstance() {
		if(instance == null) {
			instance = new UtilisateurManager();
		}
		return instance;
	}
	
	public Utilisateur validerConnexion(String pseudo, String email, String motDePasse) throws BusinessException {
		Utilisateur unUtilisateur=null;
		try {
			unUtilisateur = DAOFactory.getUtilisateurDao().validerConnexion(pseudo, email, motDePasse);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return unUtilisateur;		
	}
	
	
	public Utilisateur insert (Utilisateur utilisateur, String mdpConfirmation)throws SQLException, BusinessException { 
		BusinessException be = new BusinessException();
		
		if(!(utilisateur.getMotDePasse().equals(mdpConfirmation))) {
			be.ajouterErreur(CodesErreurBLL.MOT_DE_PASSE_DIFFERENT);
		}
		
		validationUtilisateur(utilisateur, be);
		if(be.hasErreur()) {
			throw be;
		}
		DAOFactory.getUtilisateurDao().insert(utilisateur);
		
		return utilisateur;
	}
	
	//private validationUtilisateur(Utilisateur utilisateur, BusinessException be) {	
			//validation de tous les attributs
	
	private void validationUtilisateur(Utilisateur utilisateur, BusinessException be) {
		if(utilisateur.getPseudo() == null ||utilisateur.getPseudo().length()> 30) {
			be.ajouterErreur(CodesErreurBLL.CHAMPS_PSEUDO_NON_SAISI_OU_TROP_LONG_ERREUR);
		}
		
		if(utilisateur.getNom()== null || utilisateur.getNom().length()> 30) {
			be.ajouterErreur(CodesErreurBLL.CHAMPS_NOM_NON_SAISI_OU_TROP_LONG_ERREUR);
		}
		
		if(utilisateur.getPrenom()== null || utilisateur.getPrenom().length()> 30) {
			be.ajouterErreur(CodesErreurBLL.CHAMPS_PRENOM_NON_SAISI_OU_TROP_LONG_ERREUR);
		}
		
		if(utilisateur.getEmail() == null || utilisateur.getEmail().length()> 20) {
			be.ajouterErreur(CodesErreurBLL.CHAMPS_EMAIL_NON_SAISI_OU_TROP_LONG_ERREUR);
		}
		
		if(utilisateur.getRue()== null || utilisateur.getRue().length()> 30) {
			be.ajouterErreur(CodesErreurBLL.CHAMPS_RUE_NON_SAISI_OU_TROP_LONG_ERREUR);
		}

		if(utilisateur.getCodePostal() == null || utilisateur.getCodePostal().length()> 10) {
			be.ajouterErreur(CodesErreurBLL.CHAMPS_CODEPOSTAL_NON_SAISI_OU_TROP_LONG_ERREUR);
		}
		
		if(utilisateur.getVille() == null || utilisateur.getVille().length()> 30) {
			be.ajouterErreur(CodesErreurBLL.CHAMPS_VILLE_NON_SAISI_OU_TROP_LONG_ERREUR);
		}
		if(utilisateur.getMotDePasse() == null || utilisateur.getMotDePasse().length()> 30) {
			be.ajouterErreur(CodesErreurBLL.CHAMPS_MOTDEPASSE_NON_SAISI_OU_TROP_LONG_ERREUR);
		}
		}
	
	public Utilisateur selectByUserId (int id) throws BusinessException { 		
		Utilisateur utilisateur = new Utilisateur();
		utilisateur = DAOFactory.getUtilisateurDao().selectUserById(id);		
		return utilisateur;
	}
	
	
	
	}















