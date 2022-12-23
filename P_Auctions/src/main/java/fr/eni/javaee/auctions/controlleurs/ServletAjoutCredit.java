package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.javaee.auctions.bll.UtilisateurManager;
import fr.eni.javaee.auctions.bo.Utilisateur;

/**
 * Servlet implementation class ServletAjoutCredit
 */
@WebServlet("/ServletAjoutCredit")
public class ServletAjoutCredit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Utilisateur userConnecte = new Utilisateur();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Création de l'utilisateur connecté
		HttpSession session = request.getSession();
		if (session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("/ServletListeEncheresNonConnecte");
			rd.forward(request, response);
		}
		userConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");		
		int creditAAjouter = Integer.parseInt(request.getParameter("credit"));		
		UtilisateurManager um = UtilisateurManager.getInstance();
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setCredit(creditAAjouter + userConnecte.getCredit());
		utilisateur.setNoUtilisateur(userConnecte.getNoUtilisateur());
		um.miseAJourCredit(utilisateur);

		RequestDispatcher rd = request.getRequestDispatcher("/ServletEncheresConnectees");
		rd.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}

}
