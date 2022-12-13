package fr.eni.javaee.auctions.bo;

public class Categorie {

	private int noCategorie;
	private String libelle;
	
	public Categorie() {
	
	}
	public Categorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}
	public Categorie(int noCategorie, String libelle) {
		this.libelle = libelle;
		this.noCategorie = noCategorie;
	}


	public int getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Categorie [noCategorie=").append(noCategorie).append(", libelle=").append(libelle).append("]");
		return builder.toString();
	}
	
	
	
	
	
	
}
