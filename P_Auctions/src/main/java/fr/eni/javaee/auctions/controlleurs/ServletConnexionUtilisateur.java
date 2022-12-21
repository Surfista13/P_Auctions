package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
	private int nbTentativesSaisieMdp = 0;
	Timer time = new Timer();
	private boolean compteBloque = false;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ConnexionUtilisateur.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pseudo = request.getParameter("identifiant");
		String motDePasse = request.getParameter("motDePasse");

		// initialisation de page connectée sur les achats

		Utilisateur utilisateurExistant = null;
		try {
			utilisateurExistant = UtilisateurManager.getInstance().validerConnexion(pseudo, pseudo, motDePasse);
		} catch (BusinessException e) {
		}

		if (utilisateurExistant != null && nbTentativesSaisieMdp < 2) {
			HttpSession session = request.getSession();
			session.setAttribute("utilisateurConnecte", utilisateurExistant);
			nbTentativesSaisieMdp = 0;
			RequestDispatcher rd = request.getRequestDispatcher(
					"/ServletEncheresConnectees?connect=mesAchats&categories=Toutes&recherche=&encheresOuvertes=1&encheresEnCours=2&encheresRemportees=3");
			rd.forward(request, response);
		} else {
			nbTentativesSaisieMdp++;
			if (nbTentativesSaisieMdp > 1 && compteBloque == false) {
				String erreur = "Compte desactiver pendant 10 secondes ";
				request.setAttribute("err", erreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ConnexionUtilisateur.jsp");
				rd.forward(request, response);
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						nbTentativesSaisieMdp = 0;
						compteBloque = false;
						time.cancel();
						request.setAttribute("err", erreur);
					}
				};
				Random ra = new Random();
				int timeBloque = 8 + ra.nextInt(12 - 8);
				Timer timer = new Timer();
				timer.schedule(task, timeBloque * 1000);
				compteBloque = true;
			} else if (compteBloque == true) {
				String erreur = "Compte bloqué";
				request.setAttribute("err", erreur);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ConnexionUtilisateur.jsp");
				rd.forward(request, response);
			}
			String erreur = "Utilisateur inconnu, encore 1 essai avant desactivation pendant 10 secondes";
			request.setAttribute("err", erreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ConnexionUtilisateur.jsp");
			rd.forward(request, response);
		}
	}
}
