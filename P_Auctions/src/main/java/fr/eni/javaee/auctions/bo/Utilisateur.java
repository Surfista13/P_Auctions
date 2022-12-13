package fr.eni.javaee.auctions.bo;

import java.util.List;

public class Utilisateur {
	
	private int noUtilisateur = 0;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private String rue;
	private String codePostal;
	private String ville;
	private String motDePasse;
	private int credit;
	private byte administrateur;
	
	private List <ArticleVendu> articles;
	
	
	public Utilisateur() {
		
	}


	public int getNoUtilisateur() {
		return noUtilisateur;
	}


	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}


	public String getPseudo() {
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getRue() {
		return rue;
	}


	public void setRue(String rue) {
		this.rue = rue;
	}


	public String getCodePostal() {
		return codePostal;
	}


	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}


	public String getVille() {
		return ville;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public String getMotDePasse() {
		return motDePasse;
	}


	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}


	public int getCredit() {
		return credit;
	}


	public void setCredit(int credit) {
		this.credit = credit;
	}


	public byte getAdministrateur() {
		return administrateur;
	}


	public void setAdministrateur(byte administrateur) {
		this.administrateur = administrateur;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Utilisateur [noUtilisateur=").append(noUtilisateur).append(", pseudo=").append(pseudo)
				.append(", nom=").append(nom).append(", prenom=").append(prenom).append(", email=").append(email)
				.append(", telephone=").append(telephone).append(", rue=").append(rue).append(", codePostal=")
				.append(codePostal).append(", ville=").append(ville).append(", motDePasse=").append(motDePasse)
				.append(", credit=").append(credit).append(", administrateur=").append(administrateur).append("]");
		return builder.toString();
	}
	
	
	
	
	
	
	
	
}
