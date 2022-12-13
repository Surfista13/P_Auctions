package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;

public class EnchereDAOImplSQLServer implements DAOEnchere {

	private static final String SELECT_ALL = "SELECT * FROM ENCHERES;";
	private static final String SELECT_BY_ARTICLE_NAME = "SELECT * FROM ENCHERES RIGHT JOIN ARTICLES_VENDUS ON ENCHERES.no_article = ARTICLES_VENDUS.no_article WHERE ARTICLES_VENDUS.nom_article LIKE ?;";
	private static final String SELECT_BY_CATEGORIE = "SELECT * FROM ENCHERES RIGHT JOIN ARTICLES_VENDUS ON ENCHERES.no_article = ARTICLES_VENDUS.no_article WHERE ARTICLES_VENDUS.no_categorie = ?;";
	
	List<Enchere> encheres = new ArrayList();
	
	@Override
	public List<Enchere> selectAll() {
		// TODO Auto-generated method stub
		Statement stmt;
		ResultSet rs;
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			stmt = cnx.createStatement();
			rs = stmt.executeQuery(SELECT_ALL);
			while(rs.next()) {
				Enchere enchere = new Enchere();
				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontant_enchere(rs.getInt("montant_enchere"));
				encheres.add(enchere);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encheres;
	}


	@Override
	public List<Enchere> selectByNameArticle(ArticleVendu articleVendu) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			pstmt = cnx.prepareStatement(SELECT_BY_ARTICLE_NAME);
			pstmt.setString(1, articleVendu.getNomArticle());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Enchere enchere = new Enchere();
				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontant_enchere(rs.getInt("montant_enchere"));
				encheres.add(enchere);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encheres;
	}

	@Override
	public List<Enchere> selectByCategorieEncheres(Categorie categorie) {		
		PreparedStatement pstmt;
		ResultSet rs;
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			pstmt = cnx.prepareStatement(SELECT_BY_CATEGORIE);
			pstmt.setInt(1, categorie.getNoCategorie());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Enchere enchere = new Enchere();
				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontant_enchere(rs.getInt("montant_enchere"));
				encheres.add(enchere);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encheres;
	}



}
