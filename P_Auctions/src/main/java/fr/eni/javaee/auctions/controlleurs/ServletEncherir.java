package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bll.EncheresManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Enchere;
import fr.eni.javaee.auctions.bo.Utilisateur;


@WebServlet("/Encherir")
public class ServletEncherir extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("TEST");
		//Récupération de la jsp l'id article sur lequel a encherit l'utilisateur connecté et son offre
		int idArticleEnchere = Integer.parseInt(request.getParameter("idArticle"));
		System.out.println(idArticleEnchere);
		int offre = Integer.parseInt(request.getParameter("enchere"));
		System.out.println(offre);
		
		//Récupération de la session
		HttpSession session =request.getSession();
		Utilisateur userConnecte = new Utilisateur();
		userConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");
		request.setAttribute("pseudo", userConnecte.getPseudo());
		request.setAttribute("credit", userConnecte.getCredit());
		request.setAttribute("idConnect", userConnecte.getNoUtilisateur());
		
		//Instancation de l'objet enchere
		Enchere enchere = new Enchere();
		ArticleVendu articleResult = new ArticleVendu();
		ArticleVendu articleRecherche = new ArticleVendu();
		
		ArticleVenduManager av = ArticleVenduManager.getArticleVenduManager();
		articleRecherche.setNoArticle(idArticleEnchere);
		articleResult = av.selectByIDArticle(articleRecherche);
		System.out.println(articleResult);
		
		enchere.setDateEnchere(LocalDate.now());
		enchere.setMontant_enchere(offre);
		enchere.setUtilisateur(userConnecte);
		enchere.setArticleVendus(articleResult);
		//Encherir
		EncheresManager em = EncheresManager.getEnchereManager();
		
		String result = em.encherir(enchere, articleResult);
	
		request.setAttribute("retourEnchere", result);
		request.setAttribute("article", articleResult);
		
		
		Enchere meilleurEnchere = new Enchere();
		List<Enchere> encheres = new ArrayList<>();
		EncheresManager emr = EncheresManager.getEnchereManager();
		encheres = emr.selectEnchereByArticleID(articleRecherche);
		meilleurEnchere = calculMeilleurOffre(encheres);
		request.setAttribute("meilleurEnchere", meilleurEnchere);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/DetailsVentesEncheres.jsp");
		rd.forward(request, response);
	}
	
	public Enchere calculMeilleurOffre (List<Enchere> liste) {
		int meilleurOffre = 0;
		String pseudoMeilleurOffre = null;
		for(Enchere e : liste) {
			if(e.getMontant_enchere() >= meilleurOffre) {
				meilleurOffre = e.getMontant_enchere();
				pseudoMeilleurOffre = e.getUtilisateur().getPseudo();
			}
		}
		Enchere enchere = new Enchere();
		enchere.setMontant_enchere(meilleurOffre);
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPseudo(pseudoMeilleurOffre);
		enchere.setUtilisateur(utilisateur);
		
		return enchere;	
	}

}
