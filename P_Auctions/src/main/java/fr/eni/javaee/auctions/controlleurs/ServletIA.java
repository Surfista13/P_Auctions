package fr.eni.javaee.auctions.controlleurs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 * Servlet implementation class TestChat
 */
@WebServlet("/ServletIA")
public class ServletIA extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String apiKey = "Bearer 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";
		String order = "Brainstorm auctions company slogan french";
		final String HOST = "https://api.openai.com/v1/completions";

		// create a request
		var client = HttpClient.newHttpClient();
		var request2 = HttpRequest.newBuilder().uri(URI.create(HOST)).header("Authorization", apiKey)
				.header("Content-Type", "application/json")
				.POST(BodyPublishers
						.ofString("{\r\n  \"model\": \"text-davinci-002\",\r\n  \"prompt\": \"" + order + "\"\r\n}"))
				.build();
		try {
			HttpResponse<String> response3 = client.send(request2, HttpResponse.BodyHandlers.ofString());
			PrintWriter out = response.getWriter();
			String json = response3.body().toString();
			JSONObject js = new JSONObject(json);
			String answer = js.getJSONArray("choices").getJSONObject(0).getString("text");
			request.setAttribute("slogan", answer);
			System.out.println(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("test");
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("/ServletListeEncheresNonConnecte");
		rd.forward(request, response);
	}
}
