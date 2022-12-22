package fr.eni.javaee.auctions.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni.javaee.auctions.bll.BusinessException;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;

public class EnchereDAOImplSQLServer implements DAOEnchere {

	private static final String SELECT_ALL_ENCHERES_USER = "SELECT no_enchere, ENCHERES.no_utilisateur AS no_user_enchere, UTILISATEURS.pseudo AS user_enchere_pseudo, ENCHERES.no_article,date_enchere,montant_enchere,ARTICLES_VENDUS.no_categorie,CATEGORIES.libelle,ARTICLES_VENDUS.date_debut_encheres,ARTICLES_VENDUS.date_fin_encheres,ARTICLES_VENDUS.description,ARTICLES_VENDUS.nom_article,ARTICLES_VENDUS.no_utilisateur AS no_user_vente,ARTICLES_VENDUS.prix_initial,ARTICLES_VENDUS.prix_vente,userVente.pseudo AS user_vente_pseudo FROM ENCHERES INNER JOIN UTILISATEURS ON ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN (ARTICLES_VENDUS INNER JOIN UTILISATEURS AS userVente ON ARTICLES_VENDUS.no_utilisateur = userVente.no_utilisateur) ON ARTICLES_VENDUS.no_article = ENCHERES.no_article INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie ORDER BY  ENCHERES.no_article, montant_enchere DESC;";
	private static final String SELECT_ALL_ENCHERES_USER_BY_ARTICLE_NAME = "SELECT no_enchere, ENCHERES.no_utilisateur AS no_user_enchere, UTILISATEURS.pseudo AS user_enchere_pseudo, ENCHERES.no_article,date_enchere,montant_enchere,ARTICLES_VENDUS.no_categorie,CATEGORIES.libelle,ARTICLES_VENDUS.date_debut_encheres,ARTICLES_VENDUS.date_fin_encheres,ARTICLES_VENDUS.description,ARTICLES_VENDUS.nom_article,ARTICLES_VENDUS.no_utilisateur AS no_user_vente,ARTICLES_VENDUS.prix_initial,ARTICLES_VENDUS.prix_vente,userVente.pseudo AS user_vente_pseudo FROM ENCHERES INNER JOIN UTILISATEURS ON ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN (ARTICLES_VENDUS INNER JOIN UTILISATEURS AS userVente ON ARTICLES_VENDUS.no_utilisateur = userVente.no_utilisateur) ON ARTICLES_VENDUS.no_article = ENCHERES.no_article INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE ARTICLES_VENDUS.nom_article LIKE ? ORDER BY ENCHERES.no_article, montant_enchere DESC ;";
	private static final String SELECT_ALL_ENCHERES_USER_BY_CATEGORIE_NAME = "SELECT no_enchere, ENCHERES.no_utilisateur AS no_user_enchere, UTILISATEURS.pseudo AS user_enchere_pseudo, ENCHERES.no_article,date_enchere,montant_enchere,ARTICLES_VENDUS.no_categorie,CATEGORIES.libelle,ARTICLES_VENDUS.date_debut_encheres,ARTICLES_VENDUS.date_fin_encheres,ARTICLES_VENDUS.description,ARTICLES_VENDUS.nom_article,ARTICLES_VENDUS.no_utilisateur AS no_user_vente,ARTICLES_VENDUS.prix_initial,ARTICLES_VENDUS.prix_vente,userVente.pseudo AS user_vente_pseudo FROM ENCHERES INNER JOIN UTILISATEURS ON ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN (ARTICLES_VENDUS INNER JOIN UTILISATEURS AS userVente ON ARTICLES_VENDUS.no_utilisateur = userVente.no_utilisateur) ON ARTICLES_VENDUS.no_article = ENCHERES.no_article INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE CATEGORIES.libelle = ? ORDER BY ENCHERES.no_article, montant_enchere DESC;";
	private static final String SELECT_ALL_ENCHERES_USER_BY_CATEGORIE_NAME_BY_ARTICLE_NAME = "SELECT no_enchere, ENCHERES.no_utilisateur AS no_user_enchere, UTILISATEURS.pseudo AS user_enchere_pseudo, ENCHERES.no_article,date_enchere,montant_enchere,ARTICLES_VENDUS.no_categorie,CATEGORIES.libelle,ARTICLES_VENDUS.date_debut_encheres,ARTICLES_VENDUS.date_fin_encheres,ARTICLES_VENDUS.description,ARTICLES_VENDUS.nom_article,ARTICLES_VENDUS.no_utilisateur AS no_user_vente,ARTICLES_VENDUS.prix_initial,ARTICLES_VENDUS.prix_vente,userVente.pseudo AS user_vente_pseudo FROM ENCHERES INNER JOIN UTILISATEURS ON ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN (ARTICLES_VENDUS INNER JOIN UTILISATEURS AS userVente ON ARTICLES_VENDUS.no_utilisateur = userVente.no_utilisateur) ON ARTICLES_VENDUS.no_article = ENCHERES.no_article INNER JOIN CATEGORIES ON ARTICLES_VENDUS.no_categorie = CATEGORIES.no_categorie WHERE ARTICLES_VENDUS.nom_article LIKE ? AND CATEGORIES.libelle = ? ORDER BY ENCHERES.no_article, montant_enchere DESC;";

	private static final String SELECT_ENCHERES_BY_ID_ARTICLE = "SELECT * FROM ENCHERES INNER JOIN UTILISATEURS ON ENCHERES.no_utilisateur = UTILISATEURS.no_utilisateur INNER JOIN ARTICLES_VENDUS ON ENCHERES.no_article = ARTICLES_VENDUS.no_article WHERE ENCHERES.no_article = ? ORDER BY date_enchere DESC;";

	private static final String INSERT_ENCHERES = "INSERT INTO ENCHERES(no_utilisateur,no_article,date_enchere,montant_enchere) VALUES (?,?,?,?); ";

	List<Enchere> encheres;

	@Override
	public List<Enchere> selectAllEnchereByUser(Utilisateur user) {
		PreparedStatement pstmt;
		ResultSet rs;
		int idArticlePrecedent = 0;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			encheres = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_ALL_ENCHERES_USER);
			//pstmt.setInt(1, 0);
			rs = pstmt.executeQuery();
			while (rs.next()) {	
				if(idArticlePrecedent != rs.getInt("no_article")){
					// Création utilisateur qui a créer la vente
					Utilisateur utilisateurVente = new Utilisateur();
					utilisateurVente.setNoUtilisateur(rs.getInt("no_user_vente"));
					utilisateurVente.setPseudo(rs.getString("user_vente_pseudo"));
					// Création utilisateur qui poser une enchère
					Utilisateur utilisateurEnchere = new Utilisateur();
					utilisateurEnchere.setNoUtilisateur(rs.getInt("no_user_enchere"));
					utilisateurEnchere.setPseudo(rs.getString("user_enchere_pseudo"));
					// Création de la catégorie de l'article vendu
					Categorie categorie = new Categorie();
					categorie.setNoCategorie(rs.getInt("no_categorie"));
					categorie.setLibelle(rs.getString("libelle"));
					// Création de l'article vendu
					ArticleVendu article = new ArticleVendu();
					article.setNoArticle(rs.getInt("no_article"));
					article.setNomArticle(rs.getString("nom_article"));
					article.setDescription(rs.getString("description"));
					article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
					article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
					article.setMiseAPrix(rs.getInt("prix_initial"));
					article.setPrixVente(rs.getInt("prix_vente"));
					article.setCategorie(categorie);
					article.setUtilisateur(utilisateurVente);
					// Création de l'enchère
					Enchere enchere = new Enchere();
					enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
					enchere.setMontant_enchere(rs.getInt("montant_enchere"));
					enchere.setArticleVendus(article);
					enchere.setUtilisateur(utilisateurEnchere);
					// Ajout de l'enchère à la liste				
					encheres.add(enchere);
					}
				idArticlePrecedent = rs.getInt("no_article");								
				}					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encheres;
	}

	@Override
	public List<Enchere> selectAllEnchereByUserByArticleName(Utilisateur user, ArticleVendu articleVendu) {
		PreparedStatement pstmt;
		ResultSet rs;
		int idArticlePrecedent = 0;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			encheres = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_ALL_ENCHERES_USER_BY_ARTICLE_NAME);
			pstmt.setInt(1, user.getNoUtilisateur());
			pstmt.setString(2, "%" + articleVendu.getNomArticle() + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if(idArticlePrecedent != rs.getInt("no_article")){
				// Création utilisateur qui a créer la vente
				Utilisateur utilisateurVente = new Utilisateur();
				utilisateurVente.setNoUtilisateur(rs.getInt("no_user_vente"));
				utilisateurVente.setPseudo(rs.getString("user_vente_pseudo"));
				// Création utilisateur qui poser une enchère
				Utilisateur utilisateurEnchere = new Utilisateur();
				utilisateurEnchere.setNoUtilisateur(rs.getInt("no_user_enchere"));
				utilisateurEnchere.setPseudo(rs.getString("user_enchere_pseudo"));
				// Création de la catégorie de l'article vendu
				Categorie categorie = new Categorie();
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				// Création de l'article vendu
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setCategorie(categorie);
				article.setUtilisateur(utilisateurVente);
				// Création de l'enchère
				Enchere enchere = new Enchere();
				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontant_enchere(rs.getInt("montant_enchere"));
				enchere.setArticleVendus(article);
				enchere.setUtilisateur(utilisateurEnchere);
				// Ajout de l'enchère à la liste
				encheres.add(enchere);
				}
				idArticlePrecedent = rs.getInt("no_article");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encheres;
	}

	@Override
	public List<Enchere> selectAllEnchereByUserByCategorie(Utilisateur user, Categorie categorieRecherchee) {
		PreparedStatement pstmt;
		ResultSet rs;
		int idArticlePrecedent = 0;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			encheres = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_ALL_ENCHERES_USER_BY_CATEGORIE_NAME);
			pstmt.setInt(1, user.getNoUtilisateur());
			pstmt.setString(2, categorieRecherchee.getLibelle());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if(idArticlePrecedent != rs.getInt("no_article")){
				// Création utilisateur qui a créer la vente
				Utilisateur utilisateurVente = new Utilisateur();
				utilisateurVente.setNoUtilisateur(rs.getInt("no_user_vente"));
				utilisateurVente.setPseudo(rs.getString("user_vente_pseudo"));
				// Création utilisateur qui poser une enchère
				Utilisateur utilisateurEnchere = new Utilisateur();
				utilisateurEnchere.setNoUtilisateur(rs.getInt("no_user_enchere"));
				utilisateurEnchere.setPseudo(rs.getString("user_enchere_pseudo"));
				// Création de la catégorie de l'article vendu
				Categorie categorie = new Categorie();
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				// Création de l'article vendu
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setCategorie(categorie);
				article.setUtilisateur(utilisateurVente);
				// Création de l'enchère
				Enchere enchere = new Enchere();
				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontant_enchere(rs.getInt("montant_enchere"));
				enchere.setArticleVendus(article);
				enchere.setUtilisateur(utilisateurEnchere);
				// Ajout de l'enchère à la liste
				encheres.add(enchere);
			}
				idArticlePrecedent = rs.getInt("no_article");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encheres;
	}

	@Override
	public List<Enchere> selectAllEnchereByUserByCategorieByArticleName(Utilisateur user, Categorie categorieRecherchee,
			ArticleVendu articleVendu) {
		PreparedStatement pstmt;
		ResultSet rs;
		int idArticlePrecedent = 0;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			encheres = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_ALL_ENCHERES_USER_BY_CATEGORIE_NAME_BY_ARTICLE_NAME);
			pstmt.setInt(1, user.getNoUtilisateur());
			pstmt.setString(2, "%" + articleVendu.getNomArticle() + "%");
			pstmt.setString(3, categorieRecherchee.getLibelle());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if(idArticlePrecedent != rs.getInt("no_article")){
				// Création utilisateur qui a créer la vente
				Utilisateur utilisateurVente = new Utilisateur();
				utilisateurVente.setNoUtilisateur(rs.getInt("no_user_vente"));
				utilisateurVente.setPseudo(rs.getString("user_vente_pseudo"));
				// Création utilisateur qui poser une enchère
				Utilisateur utilisateurEnchere = new Utilisateur();
				utilisateurEnchere.setNoUtilisateur(rs.getInt("no_user_enchere"));
				utilisateurEnchere.setPseudo(rs.getString("user_enchere_pseudo"));
				// Création de la catégorie de l'article vendu
				Categorie categorie = new Categorie();
				categorie.setNoCategorie(rs.getInt("no_categorie"));
				categorie.setLibelle(rs.getString("libelle"));
				// Création de l'article vendu
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setDescription(rs.getString("description"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setPrixVente(rs.getInt("prix_vente"));
				article.setCategorie(categorie);
				article.setUtilisateur(utilisateurVente);
				// Création de l'enchère
				Enchere enchere = new Enchere();
				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontant_enchere(rs.getInt("montant_enchere"));
				enchere.setArticleVendus(article);
				enchere.setUtilisateur(utilisateurEnchere);
				// Ajout de l'enchère à la liste
				encheres.add(enchere);
			}
				idArticlePrecedent = rs.getInt("no_article");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encheres;
	}

	@Override
	public List<Enchere> selectEnchereByByArticleID(ArticleVendu articleVendu) {
		PreparedStatement pstmt;
		ResultSet rs;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			encheres = new ArrayList<>();
			pstmt = cnx.prepareStatement(SELECT_ENCHERES_BY_ID_ARTICLE);
			pstmt.setInt(1, articleVendu.getNoArticle());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// Création utilisateur de l'enchère
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
				utilisateur.setPseudo(rs.getString("pseudo"));
				// Création de l'article associée à l'enchère
				ArticleVendu article = new ArticleVendu();
				article.setNoArticle(rs.getInt("no_article"));
				article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
				article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
				article.setDescription(rs.getString("description"));
				article.setMiseAPrix(rs.getInt("prix_initial"));
				article.setNoArticle(rs.getInt("no_article"));
				article.setNomArticle(rs.getString("nom_article"));
				article.setPrixVente(rs.getInt("prix_vente"));
				// Création de l'enchère
				Enchere enchere = new Enchere();
				enchere.setDateEnchere(rs.getDate("date_enchere").toLocalDate());
				enchere.setMontant_enchere(rs.getInt("montant_enchere"));
				enchere.setUtilisateur(utilisateur);
				enchere.setArticleVendus(article);
				;
				// Ajout de l'enchère à la liste
				encheres.add(enchere);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encheres;
	}

	@Override
	public int insert(Enchere enchere) {

		int nbRow = 0;
		try (Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				// Insertion du contact
				PreparedStatement pSmt = cnx.prepareStatement(INSERT_ENCHERES);
				pSmt.setInt(1, enchere.getUtilisateur().getNoUtilisateur());
				pSmt.setInt(2, enchere.getArticleVendus().getNoArticle());
				pSmt.setDate(3, Date.valueOf(enchere.getDateEnchere()));
				pSmt.setInt(4, enchere.getMontant_enchere());
				nbRow = pSmt.executeUpdate();
				cnx.commit();

			} catch (SQLException e) {
				cnx.rollback();
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return nbRow;
	}

}
