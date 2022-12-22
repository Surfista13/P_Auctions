package fr.eni.javaee.auctions.dal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.eni.javaee.auctions.bll.BusinessException;
import fr.eni.javaee.auctions.bo.LoggerFactory;
import fr.eni.javaee.auctions.bo.Utilisateur;

public class UtilisateurDaoJDBCImpl implements DAOUtilisateur {

	private final static String SELECT_USER = "SELECT * FROM UTILISATEURS WHERE (LOWER(pseudo) =? or LOWER(email)=?) and LOWER(mot_de_passe) = ? ;";
	private final static String INSERT_USER = "INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur)VALUES(?,?,?,?,?,?,?,?,?,?,?);";
	private final static String SELECT_USER_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?;";
	
	private static final String UPDATE_UTILISATEUR="UPDATE UTILISATEURS set pseudo=?,nom=?,prenom=?,email=?,telephone=?,rue=?,code_postal=?,ville=?,mot_de_passe=? where no_utilisateur=?;";
	private static final String DELETE_UTILISATEUR="DELETE FROM UTILISATEURS WHERE no_utilisateur=?;";

	private static Logger logger = null;

	/**
	 * param utilisateur = utilisateur à insérer dans la base de donnée suite à la
	 * saisie d'un formulaire
	 * @throws DALException 
	 * @throws SQLException 
	 */
	@Override
	public void insert(Utilisateur utilisateur) throws DALException{
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pSmt = cnx.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
			pSmt.setString(1, utilisateur.getPseudo());
			pSmt.setString(2, utilisateur.getNom());
			pSmt.setString(3, utilisateur.getPrenom());
			pSmt.setString(4, utilisateur.getEmail());
			pSmt.setString(5, utilisateur.getTelephone());
			pSmt.setString(6, utilisateur.getRue());
			pSmt.setString(7, utilisateur.getCodePostal());
			pSmt.setString(8, utilisateur.getVille());
			pSmt.setString(9, utilisateur.getMotDePasse());
			pSmt.setInt(10, utilisateur.getCredit());
			pSmt.setByte(11, utilisateur.getAdministrateur());
			pSmt.executeUpdate();
			ResultSet clePrimairesGenerees = pSmt.getGeneratedKeys();
			if (clePrimairesGenerees.next()) {
				int idUtilisateur = clePrimairesGenerees.getInt(1);
				utilisateur.setNoUtilisateur(idUtilisateur);
			}
		}catch (SQLException e) {
				try {
					logger = LoggerFactory.getLogger(this.getClass().getName());
				} catch (SecurityException | IOException e1) {
					DALException de = new DALException();
					de.ajouterErreur(30000);
					throw de;
				}
				logger.log(Level.SEVERE,"Erreur d'accès à la base de donnée dans la méthode: " + e.getClass()+" .Détails: "+e.getCause());
				DALException de = new DALException();
				de.ajouterErreur(30000);
				throw de;
			}
	}
	/**
	 * @return un Utilisateur s'il existe, null sinon
	 * @throws DALException 
	 * @throws BusinessException 
	 */
	@Override
	public Utilisateur validerConnexion(String pseudo, String email, String motDePasse) throws DALException{
		Utilisateur unUtilisateur = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pStmt = cnx.prepareStatement(SELECT_USER);
			pStmt.setString(1, pseudo);
			pStmt.setString(2, email);
			pStmt.setString(3, motDePasse);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				unUtilisateur = new Utilisateur();
				unUtilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				unUtilisateur.setPseudo(rs.getString("pseudo"));
				unUtilisateur.setNom(rs.getString("nom"));
				unUtilisateur.setPrenom(rs.getString("prenom"));
				unUtilisateur.setEmail(rs.getString("email"));
				unUtilisateur.setTelephone(rs.getString("telephone"));
				unUtilisateur.setRue(rs.getString("rue"));
				unUtilisateur.setCodePostal(rs.getString("code_postal"));
				unUtilisateur.setVille(rs.getString("ville"));
				unUtilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				unUtilisateur.setCredit(rs.getInt("credit"));
				unUtilisateur.setAdministrateur(rs.getByte("administrateur"));
			}
		} catch (SQLException e) {
			try {
				logger = LoggerFactory.getLogger(this.getClass().getName());
			} catch (SecurityException | IOException e1) {
				DALException de = new DALException();
				de.ajouterErreur(30000);
				throw de;
			}
			logger.log(Level.SEVERE,"Erreur d'accès à la base de donnée dans la méthode: " + e.getClass()+" .Détails: "+e.getCause());
			DALException de = new DALException();
			de.ajouterErreur(30000);
			throw de;
		}
		return unUtilisateur;
	}
	/**
	 * Param1 = identifiant de l'utilisateur vendeur
	 * @throws DALException 
	 */
	@Override
	public Utilisateur selectUserById(int idUser) throws DALException {
		Utilisateur unUtilisateur = null;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pStmt = cnx.prepareStatement(SELECT_USER_BY_ID);
			pStmt.setInt(1, idUser);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				unUtilisateur = new Utilisateur();
				unUtilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				unUtilisateur.setPseudo(rs.getString("pseudo"));
				unUtilisateur.setNom(rs.getString("nom"));
				unUtilisateur.setPrenom(rs.getString("prenom"));
				unUtilisateur.setEmail(rs.getString("email"));
				unUtilisateur.setTelephone(rs.getString("telephone"));
				unUtilisateur.setRue(rs.getString("rue"));
				unUtilisateur.setCodePostal(rs.getString("code_postal"));
				unUtilisateur.setVille(rs.getString("ville"));
				unUtilisateur.setMotDePasse(rs.getString("mot_de_passe"));
				unUtilisateur.setCredit(rs.getInt("credit"));
				unUtilisateur.setAdministrateur(rs.getByte("administrateur"));
			}
			
		} catch (SQLException e) {
			try {
				logger = LoggerFactory.getLogger(this.getClass().getName());
			} catch (SecurityException | IOException e1) {
				e1.printStackTrace();
			}
			logger.log(Level.SEVERE,"Erreur d'accès à la base de donnée dans la méthode: " + e.getClass()+" .Détails: "+e.getCause());
			DALException de = new DALException();
			de.ajouterErreur(30000);
			throw de;	
		}
		return unUtilisateur;
	}
	@Override
	public Utilisateur updateProfil(Utilisateur utilisateur) {
		
		try(Connection cnx=ConnectionProvider.getConnection()){
			PreparedStatement pStmt= cnx.prepareStatement(UPDATE_UTILISATEUR);
			pStmt.setString(1,utilisateur.getPseudo());
			pStmt.setString(2, utilisateur.getNom());
			pStmt.setString(3, utilisateur.getPrenom());
			pStmt.setString(4, utilisateur.getEmail());
			pStmt.setString(5, utilisateur.getTelephone());
			pStmt.setString(6, utilisateur.getRue());
			pStmt.setString(7, utilisateur.getCodePostal());
			pStmt.setString(8, utilisateur.getVille());
			pStmt.setString(9, utilisateur.getMotDePasse());
			pStmt.setInt(10, utilisateur.getNoUtilisateur());
			pStmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utilisateur;
	}
	@Override
	public void deleteProfil(Utilisateur utilisateur){
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			PreparedStatement pStmt = cnx.prepareStatement(DELETE_UTILISATEUR);
			pStmt.setInt(1, utilisateur.getNoUtilisateur());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}