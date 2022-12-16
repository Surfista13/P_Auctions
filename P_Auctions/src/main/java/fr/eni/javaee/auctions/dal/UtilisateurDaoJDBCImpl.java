package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.auctions.bo.Utilisateur;

public class UtilisateurDaoJDBCImpl implements UtilisateurDAO {

	private final static String FIND_UTILISATEUR = "SELECT * FROM UTILISATEURS WHERE (LOWER(pseudo) =? or LOWER(email)=?) and LOWER(mot_de_passe) = ? ;";


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
