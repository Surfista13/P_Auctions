package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Retrait;
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

	private static final String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie;";
	private static final String SELECT_BY_ARTICLE_NAME = "SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE ARTICLES_VENDUS.nom_article LIKE ?;";
	private static final String SELECT_BY_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE CATEGORIES.libelle = ?;";
	
	private static final String SELECT_BY_ID_ARTICLE ="SELECT * FROM ARTICLES_VENDUS INNER JOIN UTILISATEURS ON ARTICLES_VENDUS.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie INNER JOIN RETRAITS ON ARTICLES_VENDUS.no_article = RETRAITS.no_article WHERE ARTICLES_VENDUS.no_article = ?;";
	
	
	private static final String INSERT_NEW_VENTE ="INSERT INTO ARTICLES_VENDUS (nom_article,description,date_debut_encheres,date_fin_encheres,prix_initial,no_utilisateur,no_categorie) VALUES(?,?,?,?,?,?,?);";
	private static final String INSERT_NEW_RETRAIT="INSERT INTO RETRAITS (no_article,rue,code_postal,ville) VALUES(?,?,?,?);";
	
	List<ArticleVendu> articles;
	ArticleVendu article = new ArticleVendu();
	
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
	
	//Ajout Nicolas 15 decembre
		@Override
		public ArticleVendu insertNewArticle(ArticleVendu newArticle, Retrait retrait) {
			
			try(Connection cnx = ConnectionProvider.getConnection()){
				
				try {
					
				cnx.setAutoCommit(false);
				
			PreparedStatement pStmt = cnx.prepareStatement(INSERT_NEW_VENTE, Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1,newArticle.getNomArticle());
			pStmt.setString(2, newArticle.getDescription());
			pStmt.setDate(3,Date.valueOf(newArticle.getDateDebutEncheres()));
			pStmt.setDate(4, Date.valueOf(newArticle.getDateFinEncheres()));
			pStmt.setInt(5,newArticle.getMiseAPrix());
			pStmt.setInt(6,newArticle.getUtilisateur().getNoUtilisateur() );//bll creation utilateur
			pStmt.setInt(7,newArticle.getCategorie().getNoCategorie());
			pStmt.executeUpdate();
			
			//insertion dans la table retrait
			
			ResultSet clesPrimairesGenerees = pStmt.getGeneratedKeys();
			if(clesPrimairesGenerees.next()) {
			int noArticle = clesPrimairesGenerees.getInt(1);
			newArticle.setNoArticle(noArticle);
			pStmt= cnx.prepareStatement(INSERT_NEW_RETRAIT);
			
			pStmt.setInt(1,noArticle);
			pStmt.setString(2,retrait.getRue());
			pStmt.setString(3, retrait.getCodePostal());
			pStmt.setString(4, retrait.getVille());
			pStmt.executeUpdate();		
			cnx.commit();
			}
				
				}catch (SQLException e) {
				e.printStackTrace();
				cnx.rollback();
				
			}
				
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			return newArticle;
	}
	
		@Override
		public ArticleVendu selectByIDArticle(ArticleVendu articleVendu) {
			PreparedStatement pstmt;
			ResultSet rs;
			
			try(Connection cnx = ConnectionProvider.getConnection()){
				pstmt = cnx.prepareStatement(SELECT_BY_ID_ARTICLE);
				pstmt.setInt(1, articleVendu.getNoArticle());;
				rs = pstmt.executeQuery();
				if (rs.next()) {
					Categorie categorie =  new Categorie();
					Utilisateur utilisateur = new Utilisateur();
					Retrait retrait = new Retrait();
					article.setNoArticle(rs.getInt("no_article"));
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
					utilisateur.setCodePostal(rs.getString("code_postal"));
					utilisateur.setCredit(rs.getInt("credit"));
					utilisateur.setEmail(rs.getString("email"));
					utilisateur.setNom(rs.getString("nom"));
					utilisateur.setPrenom(rs.getString("prenom"));
					utilisateur.setRue(rs.getString("rue"));
					utilisateur.setTelephone(rs.getString("telephone"));
					utilisateur.setVille(rs.getString("ville"));
					retrait.setCodePostal(rs.getString("code_postal"));
					retrait.setRue(rs.getString("rue"));
					retrait.setVille(rs.getString("ville"));
					article.setCategorie(categorie);
					article.setUtilisateur(utilisateur);
					article.setRetrait(retrait);
				}			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(article);
			return article;
		}
}
