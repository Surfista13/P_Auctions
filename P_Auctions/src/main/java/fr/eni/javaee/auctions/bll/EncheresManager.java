package fr.eni.javaee.auctions.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.jni.Local;

import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.dal.DAOEnchere;
import fr.eni.javaee.auctions.dal.DAOFactory;

public class EncheresManager {

	private static EncheresManager encheresManager;
	private List<Enchere> encheres = new ArrayList<>();
	private DAOEnchere daoEncheres = DAOFactory.getEnchereDAOImplSQLServer();

	private EncheresManager() {
	}

	public static EncheresManager getEnchereManager() {
		if (encheresManager == null) {
			encheresManager = new EncheresManager();
		}
		return encheresManager;
	}

	public List<Enchere> selectAllEnchereByUser(Utilisateur user) {
		encheres = daoEncheres.selectAllEnchereByUser(user);
		encheres = setEtatEnchere(encheres);
		return encheres;
	}

	public List<Enchere> selectAllEnchereByUserByArticleName(Utilisateur user, ArticleVendu article) {
		encheres = daoEncheres.selectAllEnchereByUserByArticleName(user, article);
		encheres = setEtatEnchere(encheres);
		return encheres;
	}

	public List<Enchere> selectAllEnchereByUserByCategorie(Utilisateur user, Categorie categorie) {
		encheres = daoEncheres.selectAllEnchereByUserByCategorie(user, categorie);
		encheres = setEtatEnchere(encheres);
		return encheres;
	}

	public List<Enchere> selectAllEnchereByUserByCategorieByArticleName(Utilisateur user, Categorie categorie,
			ArticleVendu article) {
		encheres = daoEncheres.selectAllEnchereByUserByCategorieByArticleName(user, categorie, article);
		encheres = setEtatEnchere(encheres);
		return encheres;
	}

	public List<Enchere> selectEnchereByArticleID(ArticleVendu article) {
		encheres = daoEncheres.selectEnchereByByArticleID(article);
		encheres = setEtatEnchere(encheres);
		return encheres;
	}

	public int insertEnchere(Enchere enchere) {
		int nbRow = daoEncheres.insert(enchere);
		return nbRow;
	}

	// Méthode pour enchérir
	public String encherir(Enchere enchere, ArticleVendu article) {
		String result = "";
		if (enchere.getMontant_enchere() > article.getPrixVente()
				&& enchere.getMontant_enchere() > article.getMiseAPrix()) {
			if (article.getDateDebutEncheres().isBefore(LocalDate.now())
					|| article.getDateFinEncheres().isAfter(LocalDate.now())) {
				if (enchere.getUtilisateur().getCredit() > enchere.getMontant_enchere()) {
					EncheresManager em = EncheresManager.getEnchereManager();
					ArticleVenduManager av = ArticleVenduManager.getArticleVenduManager();
					UtilisateurManager um = new UtilisateurManager();	
					Utilisateur userPrecedent = new Utilisateur();
					Enchere encherePrecedente = new Enchere();
					encherePrecedente = calculMeilleurOffre(article);
					userPrecedent.setCredit(encherePrecedente.getUtilisateur().getCredit() + encherePrecedente.getMontant_enchere());
					userPrecedent.setNoUtilisateur(encherePrecedente.getUtilisateur().getNoUtilisateur());
					Utilisateur userNx = new Utilisateur();
					userNx.setCredit(enchere.getUtilisateur().getCredit()-enchere.getMontant_enchere());
					userNx.setNoUtilisateur(enchere.getUtilisateur().getNoUtilisateur());
					um.miseAJourCredit(userPrecedent, userNx);					
					article.setPrixVente(enchere.getMontant_enchere());
					em.insertEnchere(enchere);
					av.MiseAJourPrixDeVente(article.getPrixVente(), article.getNoArticle());
					result = "Votre en chère a été prise  en compte";
				} else {
					result = "Crédit insuffisant";
				}
			} else {
				result = "l'enchere n'a pas commencée ou est terminée";
			}
		} else {
			result = "Enchere trop basse";
		}
		return result;
	}

	// Méthode qui définit l'état d'une enchère
	public List<Enchere> setEtatEnchere(List<Enchere> encheres) {
		for (Enchere enchere : encheres) {
			LocalDate dateDebutEnchere = enchere.getArticleVendus().getDateDebutEncheres();
			LocalDate dateFinEnchere = enchere.getArticleVendus().getDateFinEncheres();
			int prixDeVente = enchere.getArticleVendus().getPrixVente();
			int montantEnchere = enchere.getMontant_enchere();

			if (dateDebutEnchere.isAfter(LocalDate.now())) {
				enchere.getArticleVendus().setEtatVente("nonDebutee");
			}
			;
			if ((dateDebutEnchere.isBefore(LocalDate.now()) || dateDebutEnchere.equals(LocalDate.now()))
					&& (dateFinEnchere.isAfter(LocalDate.now()) || dateDebutEnchere.equals(LocalDate.now()))) {
				enchere.getArticleVendus().setEtatVente("enCours");
			}
			;
			if (dateFinEnchere.isBefore(LocalDate.now())) {
				enchere.getArticleVendus().setEtatVente("terminee");
			}
			;
			if (dateFinEnchere.isBefore(LocalDate.now()) && montantEnchere >= prixDeVente) {
				enchere.getArticleVendus().setEtatVente("remportee");
			}
			;
		}
		return encheres;
	}
	
	//Méthode définition de la meilleur enchères
	public Enchere calculMeilleurOffre(ArticleVendu article) {
		List<Enchere> encheres = new ArrayList<>();
		encheres = daoEncheres.selectEnchereByByArticleID(article);
		System.out.println(encheres);
		int meilleurOffre = 0;
		String pseudoMeilleurOffre = null;
		int idUtilisateurEnchere=0;
		int creditUtilisateurEnchere=0;
		for (Enchere e : encheres) {
			if (e.getMontant_enchere() >= meilleurOffre) {
				meilleurOffre = e.getMontant_enchere();
				pseudoMeilleurOffre = e.getUtilisateur().getPseudo();
				idUtilisateurEnchere = e.getUtilisateur().getNoUtilisateur();
				creditUtilisateurEnchere = e.getUtilisateur().getCredit();
			}
		}
		Enchere enchere = new Enchere();
		enchere.setMontant_enchere(meilleurOffre);
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPseudo(pseudoMeilleurOffre);
		utilisateur.setCredit(creditUtilisateurEnchere);
		enchere.setUtilisateur(utilisateur);
		utilisateur.setNoUtilisateur(idUtilisateurEnchere);
		return enchere;	
	}
}
