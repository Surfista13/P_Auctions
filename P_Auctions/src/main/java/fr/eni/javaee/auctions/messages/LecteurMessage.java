package fr.eni.javaee.auctions.messages;

import java.util.ResourceBundle;

public class LecteurMessage {

	private static ResourceBundle rb;
	
	//bloc static qui charge les messages (1 seule fois)
	static {
		try {
			rb = ResourceBundle.getBundle("fr.eni.javaee.auctions.messages.messages_erreur");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getMessageErreur(int code) {
		String message = "";
		
		if(rb != null) {
			try {
				message = rb.getString(String.valueOf(code));
			} catch (Exception e) {
				e.printStackTrace();
				message = code + " : erreur inconnue";
			}
		} else {
			message = "Problème lors de la lecture du fichier messages_erreur";
		}
		
		return message;
	}
}






