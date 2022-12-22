package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;
import java.sql.SQLException;

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
import fr.eni.javaee.auctions.dal.DALException;

@WebServlet("/ServletInscription")
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RequestDispatcher rd;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Inscription.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		Utilisateur nouvelUtilisateur = new Utilisateur();
		nouvelUtilisateur.setPseudo(request.getParameter("pseudo"));
		nouvelUtilisateur.setNom(request.getParameter("nom"));
		nouvelUtilisateur.setPrenom(request.getParameter("prenom"));
		nouvelUtilisateur.setEmail(request.getParameter("email"));
		nouvelUtilisateur.setTelephone(request.getParameter("telephone"));
		nouvelUtilisateur.setRue(request.getParameter("rue"));
		nouvelUtilisateur.setCodePostal(request.getParameter("codePostal"));
		nouvelUtilisateur.setVille(request.getParameter("ville"));
		nouvelUtilisateur.setMotDePasse(request.getParameter("motDePasse"));
		String confirmation = request.getParameter("confirmation");		
		try {
			nouvelUtilisateur = UtilisateurManager.getInstance().insert(nouvelUtilisateur, confirmation);
			HttpSession session = request.getSession();
			session.setAttribute("utilisateurConnecte", nouvelUtilisateur);
			RequestDispatcher rd = request.getRequestDispatcher("/ServletEncheresConnectees?connect=mesAchats&categories=Toutes&recherche=&encheresOuvertes=1&encheresEnCours=2&encheresRemportees=3");
			rd.forward(request, response);
		} catch (DALException e) {
			response.sendRedirect("erreurDAL.html");
		} catch (BusinessException e) {
			request.setAttribute("listeCodesErreur", e.getListeCodesErreur());
			rd = request.getRequestDispatcher("/WEB-INF/Inscription.jsp");
			rd.forward(request, response);
		}
	}	
}
