package fr.eni.javaee.auctions.dal;

public abstract class DAOFactory {
	
	public static DAOEnchere getEnchereDAOImplSQLServer (){
		return new EnchereDAOImplSQLServer();
	}

	public static DAOCategorie getCategorieDAOImplSQLServer() {
		return new CategorieDAOImplSQLServer();
	}
}
