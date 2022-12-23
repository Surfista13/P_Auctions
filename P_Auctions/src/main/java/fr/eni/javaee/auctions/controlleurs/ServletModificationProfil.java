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
 * Servlet implementation class ServletModificationProfil
 */
@WebServlet("/ServletModificationProfil")
public class ServletModificationProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Utilisateur userConnecte = new Utilisateur();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
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

		// int idUser= 33;
		UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
		Utilisateur utilisateurRecherche = new Utilisateur();
		try {
			utilisateurRecherche = utilisateurManager.selectByUserId(idUser);
		} catch (DALException e) {
			response.sendRedirect("erreurDAL.html");
		}
		request.setAttribute("utilisateur", utilisateurRecherche);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ModificationProfil.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utilisateur utilisateur = new Utilisateur();

		request.setAttribute("noArticle", userConnecte.getNoUtilisateur());
		utilisateur.setNoUtilisateur(Integer.parseInt(request.getParameter("majUtilisateur")));
		utilisateur.setPseudo(request.getParameter("pseudo"));
		utilisateur.setNom(request.getParameter("nom"));
		utilisateur.setPrenom(request.getParameter("prenom"));
		utilisateur.setEmail(request.getParameter("email"));
		utilisateur.setTelephone(request.getParameter("telephone"));
		utilisateur.setRue(request.getParameter("adresse"));
		utilisateur.setCodePostal(request.getParameter("codePostal"));
		utilisateur.setVille(request.getParameter("ville"));
		utilisateur.setMotDePasse(request.getParameter("motDePasse"));

		Utilisateur utilisateurMaj = UtilisateurManager.getInstance().updateUtilisateur(utilisateur);

		RequestDispatcher rd = request.getRequestDispatcher(
				"/ServletEncheresConnectees?connect=mesAchats&categories=Toutes&recherche=&encheresOuvertes=1&encheresEnCours=2&encheresRemportees=3");
		rd.forward(request, response);

	}

}
