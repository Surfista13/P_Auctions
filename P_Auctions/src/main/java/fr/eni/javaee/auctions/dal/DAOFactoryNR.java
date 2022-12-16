package fr.eni.javaee.auctions.dal;

public abstract class DAOFactoryNR {
	
	public static UtilisateurDAO getUtilisateurDAO() {
		return new UtilisateurDaoJDBCImpl();
	}
	
	

}
