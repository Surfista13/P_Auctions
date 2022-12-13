package fr.eni.javaee.auctions.bo;

import java.time.LocalDate;

public class Enchere {

	private LocalDate dateEnchere;
	private int montant_enchere;
	
	private ArticleVendu articleVendus;
	private Utilisateur utilisateur;
	
	public Enchere() {
	
	}

	public LocalDate getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDate dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontant_enchere() {
		return montant_enchere;
	}

	public void setMontant_enchere(int montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enchere [dateEnchere=").append(dateEnchere).append(", montant_enchere=").append(montant_enchere)
				.append("]");
		return builder.toString();
	}
	
	
	
	
	
}
