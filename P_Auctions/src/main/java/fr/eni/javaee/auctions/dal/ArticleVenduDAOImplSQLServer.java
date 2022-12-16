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
import fr.eni.javaee.auctions.bo.Utilisateur;

public class ArticleVenduDAOImplSQLServer implements DAOArticleVendu {

	private static final String SELECT_VENTES_NON_TERMINEES = "SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE ARTICLES_VENDUS.date_fin_encheres >= GETDATE();";
	private static final String SELECT_VENTES_NON_TERMINEES_BY_ARTICLE_NAME = "SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE ARTICLES_VENDUS.date_fin_encheres >= GETDATE() AND ARTICLES_VENDUS.nom_article LIKE ?;";
	private static final String SELECT_VENTES_NON_TERMINEES_BY_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE ARTICLES_VENDUS.date_fin_encheres >= GETDATE() AND CATEGORIES.libelle = ?;";
	private static final String SELECT_VENTES_NON_TERMINEES_BY_CATEGORIE_ARTICLE = "SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE ARTICLES_VENDUS.date_fin_encheres >= GETDATE() AND CATEGORIES.libelle = ? AND ARTICLES_VENDUS.nom_article LIKE ?;";
	private static final String SELECT_ALL_VENTES_FOR_A_USER ="SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE UTILISATEURS.no_utilisateur = ?;";
	
	private static final String SELECT_VENTES_BY_USER_BY_NAME = "SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE UTILISATEURS.no_utilisateur = ? AND ARTICLES_VENDUS.nom_article LIKE ?;";
	private static final String SELECT_VENTES_BY_USER_BY_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE UTILISATEURS.no_utilisateur = ? AND CATEGORIES.libelle = ?;";
	private static final String SELECT_VENTES_BY_USER_BY_CATEGORIE_BY_NAME = "SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE UTILISATEURS.no_utilisateur = ? AND CATEGORIES.libelle = ? AND ARTICLES_VENDUS.nom_article LIKE ?;";

	
	
	List<ArticleVendu> articles;
	
	@Override
	public List<ArticleVendu> selectAll() {
		// TODO Auto-generated method stub
		Statement stmt;
		ResultSet rs;
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			articles = new ArrayList<>();
			stmt = cnx.createStatement();
			rs = stmt.executeQuery(SELECT_VENTES_NON_TERMINEES);
			while(rs.next()) {
				ArticleVendu article = new ArticleVendu();
				Categorie categorie =  new Categorie();
				Utilisateur utilisateur = new Utilisateur();
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));							
				article.setCategorie(categorie);
				article.setUtilisateur(utilisateur);
				articles.add(article);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}


	@Override
	public List<ArticleVendu> selectByNameArticle(ArticleVendu articleVendu) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			articles = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_VENTES_NON_TERMINEES_BY_ARTICLE_NAME);
			pstmt.setString(1, "%"+articleVendu.getNomArticle()+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = new ArticleVendu();
				Categorie categorie =  new Categorie();
				Utilisateur utilisateur = new Utilisateur();
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));							
				article.setCategorie(categorie);
				article.setUtilisateur(utilisateur);
				articles.add(article);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public List<ArticleVendu> selectByCategorie(Categorie categorieToFind) {		
		PreparedStatement pstmt;
		ResultSet rs;
		try(Connection cnx = ConnectionProvider.getConnection()){
			articles = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_VENTES_NON_TERMINEES_BY_CATEGORIE);
			pstmt.setString(1,categorieToFind.getLibelle());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = new ArticleVendu();
				Categorie categorie =  new Categorie();
				Utilisateur utilisateur = new Utilisateur();
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));							
				article.setCategorie(categorie);
				article.setUtilisateur(utilisateur);
				articles.add(article);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}
	
	@Override
	public List<ArticleVendu> selectByCategorieByArticle(Categorie categorieToFind,ArticleVendu articleVendu) {		
		PreparedStatement pstmt;
		ResultSet rs;
		try(Connection cnx = ConnectionProvider.getConnection()){
			articles = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_VENTES_NON_TERMINEES_BY_CATEGORIE_ARTICLE);
			pstmt.setString(1,categorieToFind.getLibelle());
			pstmt.setString(2,"%"+articleVendu.getNomArticle()+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = new ArticleVendu();
				Categorie categorie =  new Categorie();
				Utilisateur utilisateur = new Utilisateur();
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));							
				article.setCategorie(categorie);
				article.setUtilisateur(utilisateur);
				articles.add(article);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}
	@Override
	public List<ArticleVendu> selectByUser(Utilisateur user) {		
		PreparedStatement pstmt;
		ResultSet rs;
		try(Connection cnx = ConnectionProvider.getConnection()){
			articles = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_ALL_VENTES_FOR_A_USER);
			pstmt.setInt(1,user.getNoUtilisateur());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = new ArticleVendu();
				Categorie categorie =  new Categorie();
				Utilisateur utilisateur = new Utilisateur();
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));							
				article.setCategorie(categorie);
				article.setUtilisateur(utilisateur);
				articles.add(article);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}	
	
	@Override
	public List<ArticleVendu> selectByUserByNameArticle(Utilisateur user, ArticleVendu articleVendu) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			articles = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_VENTES_BY_USER_BY_NAME);
			pstmt.setInt(1, user.getNoUtilisateur());
			pstmt.setString(2, "%"+articleVendu.getNomArticle()+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = new ArticleVendu();
				Categorie categorie =  new Categorie();
				Utilisateur utilisateur = new Utilisateur();
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));							
				article.setCategorie(categorie);
				article.setUtilisateur(utilisateur);
				articles.add(article);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public List<ArticleVendu> selectByUserByCategorie(Utilisateur user, Categorie categorieRecherchee) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			articles = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_VENTES_BY_USER_BY_CATEGORIE);
			pstmt.setInt(1, user.getNoUtilisateur());
			pstmt.setString(2,categorieRecherchee.getLibelle());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = new ArticleVendu();
				Categorie categorie =  new Categorie();
				Utilisateur utilisateur = new Utilisateur();
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));							
				article.setCategorie(categorie);
				article.setUtilisateur(utilisateur);
				articles.add(article);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public List<ArticleVendu> selectByUserByCategorieByArticleName(Utilisateur user, Categorie categorieRecherchee, ArticleVendu articleVendu) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		
		try(Connection cnx = ConnectionProvider.getConnection()){
			articles = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_VENTES_BY_USER_BY_CATEGORIE_BY_NAME);
			pstmt.setInt(1, user.getNoUtilisateur());
			pstmt.setString(2,categorieRecherchee.getLibelle());
			pstmt.setString(3,"%"+articleVendu.getNomArticle()+"%");

			rs = pstmt.executeQuery();
			while(rs.next()) {
				ArticleVendu article = new ArticleVendu();
				Categorie categorie =  new Categorie();
				Utilisateur utilisateur = new Utilisateur();
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));							
				article.setCategorie(categorie);
				article.setUtilisateur(utilisateur);
				articles.add(article);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}	
}
