package fr.eni.javaee.auctions.testsunitaires;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bll.CategorieManager;
import fr.eni.javaee.auctions.bll.EncheresManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;
import fr.eni.javaee.auctions.dal.ConnectionProvider;

/**
 * Servlet implementation class ServletTestManager
 */
@WebServlet("/ServletTestManager")
public class ServletTestManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Test1 affichage catégories		
		/*
		PrintWriter out = response.getWriter();
        CategorieManager cm = CategorieManager.getCategorieManager();
		
		List<Categorie> categories = new ArrayList<>();
		categories = cm.selectAllCategories();
		for (Categorie a : categories){
		    out.print("Categorie: " + a + "\n");
		    out.print("\n");
		};
        
        //Test2 delete d'une catégorie
        PrintWriter out2 = response.getWriter();
        Categorie c1 = new Categorie(13,"Maison");
        CategorieManager cm2 = CategorieManager.getCategorieManager();            
		int rowDeleted = cm2.deleteCategories(c1);
		out2.print(rowDeleted);
		out2.print("\n");
		out2.print("\n");
        
        
      //Test3 affichage des ventes non terminées
        PrintWriter out3 = response.getWriter();
        ArticleVenduManager cm3 = ArticleVenduManager.getArticleVenduManager();
        List<ArticleVendu> articles = new ArrayList<>();
		articles = cm3.selectAllArticleVendu();
		for (ArticleVendu b : articles){
		    out3.print("Article: " + b + "\n");
		    out3.print("\n");
		}
		
	    //Test4 affichage des ventes d'un utilisateur
        PrintWriter out4 = response.getWriter();
        ArticleVenduManager cm4 = ArticleVenduManager.getArticleVenduManager();
        List<ArticleVendu> articles2 = new ArrayList<>();
        Utilisateur user1 =  new Utilisateur();
        user1.setNoUtilisateur(3);
		articles2 = cm4.articleVenduUtilisateur(user1);
		for (ArticleVendu b : articles2){
		    out4.print("Article utilisateurs: " + b + "\n");
		    out4.print("\n");
		}

	    //Test5 affichage enchères utilisateurs
		PrintWriter out6 = response.getWriter();
        EncheresManager em = EncheresManager.getEnchereManager();
        List<Enchere> encheres = new ArrayList<>();
        Utilisateur user2 =  new Utilisateur();
        user2.setNoUtilisateur(3);
		encheres = em.selectAllEnchereByUser(user2);
		for (Enchere e : encheres){
		    out6.print("Encheres utilisateurs: " + e + "\n");
		    out6.print("\n");
		}
		
	    //Test6 affichage enchères utilisateurs par article name
		PrintWriter out7 = response.getWriter();
        EncheresManager em2 = EncheresManager.getEnchereManager();
        List<Enchere> encheres2 = new ArrayList<>();
        Utilisateur user3 =  new Utilisateur();
        user3.setNoUtilisateur(2);
        ArticleVendu a1 = new ArticleVendu();
        a1.setNomArticle("ba");       
		encheres2 = em2.selectAllEnchereByUserByArticleName(user3, a1);
		for (Enchere e : encheres2){
		    out7.print("Encheres utilisateurs par article name: \n" + e + "\n");
		    out7.print("\n");
		}
		
	    //Test7 affichage enchères utilisateurs par categories
		PrintWriter out8 = response.getWriter();
        EncheresManager em2 = EncheresManager.getEnchereManager();
        List<Enchere> encheres2 = new ArrayList<>();
        Utilisateur user3 =  new Utilisateur();
        user3.setNoUtilisateur(2);
        Categorie categorie = new Categorie();
        categorie.setLibelle("Auto");      
		encheres2 = em2.selectAllEnchereByUserByCategorie(user3, categorie);
		out8.print("test");
		for (Enchere e : encheres2){
		    out8.print("Encheres utilisateurs par categorie: \n" + e + "\n");
		    out8.print("\n");
		}

		
	    //Test8 affichage enchères utilisateurs par categories et article name
		PrintWriter out8 = response.getWriter();
        EncheresManager em2 = EncheresManager.getEnchereManager();
        List<Enchere> encheres2 = new ArrayList<>();
        Utilisateur user3 =  new Utilisateur();
        user3.setNoUtilisateur(2);
        Categorie categorie = new Categorie();
        categorie.setLibelle("Auto"); 
        ArticleVendu a1 = new ArticleVendu();
        a1.setNomArticle("Vo");
		encheres2 = em2.selectAllEnchereByUserByCategorieByArticleName(user3, categorie, a1);
		out8.print("test");
		for (Enchere e : encheres2){
		    out8.print("Encheres utilisateurs par categorie et par article name: \n" + e + "\n");
		    out8.print("\n");
		}
		
		//Test9 affichage des ventes par utilisateur par article name
        PrintWriter out3 = response.getWriter();
        ArticleVenduManager cm3 = ArticleVenduManager.getArticleVenduManager();
        List<ArticleVendu> articles = new ArrayList<>();
        Utilisateur user3 =  new Utilisateur();
        user3.setNoUtilisateur(3);
        ArticleVendu a1 = new ArticleVendu();
        a1.setNomArticle("Vo");
		articles = cm3.selectByUserByArticle(user3, a1);
		for (ArticleVendu b : articles){
		    out3.print("Article: " + b + "\n");
		    out3.print("\n");
		}
		
		//Test10 affichage des ventes par utilisateur par categorie
        PrintWriter out3 = response.getWriter();
        ArticleVenduManager cm3 = ArticleVenduManager.getArticleVenduManager();
        List<ArticleVendu> articles = new ArrayList<>();
        Utilisateur user3 =  new Utilisateur();
        user3.setNoUtilisateur(3);
        Categorie categorie = new Categorie();
        categorie.setLibelle("Auto");
		articles = cm3.selectByUserByCategorie(user3, categorie);
		for (ArticleVendu b : articles){
		    out3.print("Article: " + b + "\n");
		    out3.print("\n");
		}
		*/
		//Test11 affichage des ventes par utilisateur par categorie par article name
        PrintWriter out3 = response.getWriter();
        ArticleVenduManager cm3 = ArticleVenduManager.getArticleVenduManager();
        List<ArticleVendu> articles = new ArrayList<>();
        Utilisateur user3 =  new Utilisateur();
        user3.setNoUtilisateur(3);
        Categorie categorie = new Categorie();
        categorie.setLibelle("Auto");
        ArticleVendu a1 = new ArticleVendu();
        a1.setNomArticle("Vo");
		articles = cm3.selectByUserByCategorieByArticleName(user3, categorie, a1);
		for (ArticleVendu b : articles){
		    out3.print("Article: " + b + "\n");
		    out3.print("\n");
		}
		
		out3.close();
		
		
		
		
		
	}
}

