package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import fr.eni.javaee.auctions.bo.Utilisateur;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

@WebServlet("/ServletTestapi")
public class ServletUserRandom extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String HOST = "https://randomuser.me/api/";

		// Construct the URL.
		URL url = new URL(HOST);
		PrintWriter out = response.getWriter();
		// Open the connection.
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		// Receive the JSON response body.
		InputStream stream = connection.getInputStream();
		String response1 = new Scanner(stream).useDelimiter("\\A").next();

		// Construct the result object.
		Utilisateur user = new Utilisateur();
		JSONObject js = new JSONObject(response1);
		JSONArray arr = js.getJSONArray("results");
		user.setPrenom(arr.getJSONObject(0).getJSONObject("name").getString("first"));
		user.setNom(arr.getJSONObject(0).getJSONObject("name").getString("last"));
		user.setPseudo(arr.getJSONObject(0).getJSONObject("login").getString("username"));
		user.setEmail(arr.getJSONObject(0).getString("email"));
		user.setMotDePasse(arr.getJSONObject(0).getJSONObject("login").getString("password"));
		user.setCredit(0);
		user.setRue(arr.getJSONObject(0).getJSONObject("location").getJSONObject("street").getString("number") + " "
				+ arr.getJSONObject(0).getJSONObject("location").getJSONObject("street").getString("name"));
		user.setCodePostal((arr.getJSONObject(0).getJSONObject("location").getString("postcode")));
		user.setVille((arr.getJSONObject(0).getJSONObject("location").getString("city")));
		user.setTelephone(arr.getJSONObject(0).getString("phone"));
		user.setAdministrateur((byte) 0);
		stream.close();

		request.setAttribute("user", user);

		RequestDispatcher rd = request.getRequestDispatcher("/ServletInscription");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
