package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.auctions.bo.Utilisateur;

public class UtilisateurDaoJDBCImpl implements DAOUtilisateur {

	private final static String FIND_UTILISATEUR = "SELECT * FROM UTILISATEURS WHERE (LOWER(pseudo) =? or LOWER(email)=?) and LOWER(mot_de_passe) = ? ;";

	private final static String INSERT = "INSERT INTO UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur)VALUES(?,?,?,?,?,?,?,?,?,?,?);";

	@Override
	public void insert(Utilisateur utilisateur) {

		try (Connection cnx = ConnectionProvider.getConnection()) {

			// Insertion du contact
			PreparedStatement pSmt = cnx.prepareStatement(INSERT);
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

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/**
	 * @return un Utilisateur s'il existe, null sinon
	 */
	@Override
	public Utilisateur validerConnexion(String pseudo, String email, String motDePasse)  {
		Utilisateur unUtilisateur = null;

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pStmt = cnx.prepareStatement(FIND_UTILISATEUR);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return unUtilisateur;

	}

}