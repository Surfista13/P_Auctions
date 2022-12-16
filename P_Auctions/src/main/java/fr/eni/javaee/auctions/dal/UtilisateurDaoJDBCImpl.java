package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import fr.eni.javaee.auctions.bo.Utilisateur;

public class UtilisateurDaoJDBCImpl implements DAOUtilisateur {

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
}