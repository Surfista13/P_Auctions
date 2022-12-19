package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;
import java.io.PrintWriter;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.auctions.bll.BusinessException;
import fr.eni.javaee.auctions.bll.UtilisateurManager;
import fr.eni.javaee.auctions.bo.Utilisateur;

/**
 * Servlet implementation class ServletConnexionUtilisateur
 */
@WebServlet("/ServletConnexionUtilisateur")
public class ServletConnexionUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ConnexionUtilisateur.jsp");
		rd.forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pseudo = request.getParameter("identifiant");
		String motDePasse= request.getParameter("motDePasse");
		
		
		
		
		Utilisateur utilisateurExistant = UtilisateurManager.getInstance().validerConnexion(pseudo, pseudo, motDePasse);
		if(utilisateurExistant != null) {
		HttpSession session =request.getSession();	
		session.setAttribute("utilisateurConnecte", utilisateurExistant);
		}else {
		
		String erreur = "Utilisateur inconnu";
		request.setAttribute("err", erreur);	
		doGet(request, response);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ListeArticlesVendusEncheresConnectée.jsp");
		rd.forward(request, response);
		
		
	

	}

}
