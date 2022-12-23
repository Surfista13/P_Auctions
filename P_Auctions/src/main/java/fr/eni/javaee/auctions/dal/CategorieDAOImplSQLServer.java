package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.auctions.bo.Categorie;

public class CategorieDAOImplSQLServer implements DAOCategorie {

	private static final String SELECT_ALL = "SELECT * FROM CATEGORIES;";
	private static final String DELETE = "DELETE FROM CATEGORIES WHERE libelle = ?;";

	List<Categorie> categories = new ArrayList();

	@Override
	public List<Categorie> selectAll() {
		// TODO Auto-generated method stub
		Statement stmt;
		ResultSet rs;

		try (Connection cnx = ConnectionProvider.getConnection()) {
			stmt = cnx.createStatement();
			rs = stmt.executeQuery(SELECT_ALL);
			while (rs.next()) {
				Categorie categorie = new Categorie();
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				categories.add(categorie);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public int deleteCategorie(Categorie categorieToDelete) {
		int nbRowDeleted = 0;
		PreparedStatement pstmt;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			pstmt = cnx.prepareStatement(DELETE);
			pstmt.setString(1, categorieToDelete.getLibelle());
			nbRowDeleted = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nbRowDeleted;
	}
}
