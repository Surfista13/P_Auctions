package fr.eni.javaee.auctions.dal;

import java.util.ArrayList;
import java.util.List;

public class DALException extends Exception {
	private List<Integer> listeCodesErreur;

	public List<Integer> getListeCodesErreur() {
		return listeCodesErreur;
	}

	public DALException() {
		listeCodesErreur = new ArrayList<>();
	}

	public void ajouterErreur(int code) {
		if (!listeCodesErreur.contains(code)) {
			listeCodesErreur.add(code);
		}
	}

	public boolean hasErreur() {
		return !listeCodesErreur.isEmpty();
	}
}
