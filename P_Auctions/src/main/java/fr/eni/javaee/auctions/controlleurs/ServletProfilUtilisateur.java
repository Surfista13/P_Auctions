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
import fr.eni.javaee.auctions.dal.DALException;

/**
 * Servlet implementation class ServletProfilUtilisateur
 */
@WebServlet("/ServletProfilUtilisateur")
public class ServletProfilUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Récupération de l'utilisateur connecté dans la session
		HttpSession session = request.getSession(false);
		// Redirige vers la page d'accueil non connecté si la session est nulle
		if (session == null) {
			RequestDispatcher rd = request.getRequestDispatcher("/ServletListeEncheresNonConnecte");
			rd.forward(request, response);
		}
		Utilisateur userConnecte = new Utilisateur();
		userConnecte = (Utilisateur) session.getAttribute("utilisateurConnecte");
		request.setAttribute("pseudo", userConnecte.getPseudo());
		request.setAttribute("credit", userConnecte.getCredit());
		request.setAttribute("id", userConnecte.getNoUtilisateur());

		// Récupérer l'id utilisateur dont on souhaite afficher le profil
		// TODO lier avec page précédente qui doit renvoyer l'id utilisateur
		int idUser = Integer.parseInt(request.getParameter("idRech"));

		// int idUser= 4;
		UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
		Utilisateur utilisateurRecherche = new Utilisateur();
		try {
			utilisateurRecherche = utilisateurManager.selectByUserId(idUser);
		} catch (DALException e) {
			response.sendRedirect("erreurDAL.html");
		}
		
		//Mise à jour crédit
		UtilisateurManager um = new UtilisateurManager();
		Utilisateur userMaJ = new Utilisateur();
		try {
			userMaJ = um.selectByUserId(userConnecte.getNoUtilisateur());
		} catch (DALException e) {
			response.sendRedirect("erreurDAL.html");
		}
		request.setAttribute("credit", userMaJ.getCredit());
		
		
		request.setAttribute("utilisateur", utilisateurRecherche);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Profil.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Récupération de l'utilisateur connecté dans la session
		HttpSession session = request.getSession(false);

		// Redirige vers la page d'accueil non connecté si la session est nulle
		if (session == null) {
			System.out.println(session);
			RequestDispatcher rd = request.getRequestDispatcher("/ServletListeEncheresNonConnecte");
			rd.forward(request, response);
		}
		doGet(request, response);
	}

}
