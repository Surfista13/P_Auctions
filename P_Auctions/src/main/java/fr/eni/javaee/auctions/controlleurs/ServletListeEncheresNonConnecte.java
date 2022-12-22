package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import fr.eni.javaee.auctions.bll.ArticleVenduManager;
import fr.eni.javaee.auctions.bll.CategorieManager;
import fr.eni.javaee.auctions.bo.ArticleVendu;
import fr.eni.javaee.auctions.bo.Categorie;
import fr.eni.javaee.auctions.bo.Utilisateur;

/**
 * Servlet implementation class ServletListeEncheresNonConnecte
 */
@WebServlet("/ServletListeEncheresNonConnecte")
public class ServletListeEncheresNonConnecte extends HttpServlet {
	private static final long serialVersionUID = 1L;

	List<Categorie> categories = new ArrayList();
	String slogan;
	
	public void init() {
		// Liste des catégories
		CategorieManager categorieManager = CategorieManager.getCategorieManager();
		categories = categorieManager.selectAllCategories();
		System.out.println(categories);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Liste des article vendus en cours
		ArticleVenduManager articleManager = ArticleVenduManager.getArticleVenduManager();
		List<ArticleVendu> articles = new ArrayList<>();
		articles = articleManager.selectAllArticleVendu();
		request.setAttribute("liste", articles);
		// Liste des catégories
		request.setAttribute("listeCategories", categories);
		
		//TODO gérer la déconnexion qui renvoi vers cette page en supprimant la session
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ListeArctileVendusNonConnecte.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<ArticleVendu> articles = new ArrayList<>();
		ArticleVenduManager articleManager = ArticleVenduManager.getArticleVenduManager();
		Categorie categorie = new Categorie();
		categorie.setLibelle(request.getParameter("categories"));
		ArticleVendu articleVendu = new ArticleVendu();
		articleVendu.setNomArticle(request.getParameter("recherche"));

		if (categorie.getLibelle().equals("Toutes") && articleVendu.getNomArticle().isEmpty()) {
			articles = articleManager.selectAllArticleVendu();
		} else if (articleVendu.getNomArticle().isEmpty() && !categorie.getLibelle().equals("Toutes")) {
			articles = articleManager.selectByCategorieEncheres(categorie);
		} else if (categorie.getLibelle().equals("Toutes") && !articleVendu.getNomArticle().isEmpty()) {
			articles = articleManager.selectByNameArticleEncheres(articleVendu);
		} else {
			articles = articleManager.selectByCategorieByArticle(categorie, articleVendu);
		}
		request.setAttribute("slogan",slogan);
		request.setAttribute("liste", articles);
		request.setAttribute("listeCategories", categories);
		request.setAttribute("selected", categorie.getLibelle());
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ListeArctileVendusNonConnecte.jsp");
		rd.forward(request, response);

	}
	
	public void sloganIA(HttpServletRequest request) {
		//Génération d'un slogan automatique via appel API ChatGPT
		String apiKey ="Bearer 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";
		String order ="Brainstorm auctions company slogan french";
		final String HOST = "https://api.openai.com/v1/completions";

		var client = HttpClient.newHttpClient();
		var request2 = HttpRequest.newBuilder()
			.uri(URI.create(HOST))
			.header("Authorization", apiKey)
			.header("Content-Type", "application/json")
			.POST(BodyPublishers.ofString("{\r\n  \"model\": \"text-davinci-002\",\r\n  \"prompt\": \""+order+"\"\r\n}"))
			.build();		
		try {
			HttpResponse<String> response3 = client.send(request2,HttpResponse.BodyHandlers.ofString());
			String json =response3.body().toString();
			JSONObject js = new JSONObject(json);
			String answer = js.getJSONArray("choices").getJSONObject(0).getString("text");
			slogan = answer;
			request.setAttribute("slogan",answer);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
	}
}
