package fr.eni.javaee.auctions.dal;

import java.util.List;

import fr.eni.javaee.auctions.bo.Categorie;

public interface DAOCategorie {
	public abstract List<Categorie> selectAll();
	public abstract int deleteCategorie(Categorie categorie);	
}
