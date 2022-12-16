package fr.eni.javaee.auctions.dal;

import java.util.List;

import fr.eni.javaee.auctions.bo.Categorie;

public interface DAOCategorie {
	public List<Categorie> selectAll();
	public int deleteCategorie(Categorie categorie);	
}
