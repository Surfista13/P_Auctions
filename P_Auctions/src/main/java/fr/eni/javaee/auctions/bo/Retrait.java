package fr.eni.javaee.auctions.bo;

public class Retrait {
	
	private String rue;
	private String codePostal;
	private String ville;

	
	public Retrait() {

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Retrait [rue=").append(rue).append(", codePostal=").append(codePostal).append(", ville=")
				.append(ville).append("]");
		return builder.toString();
	}
	
	
	

}
