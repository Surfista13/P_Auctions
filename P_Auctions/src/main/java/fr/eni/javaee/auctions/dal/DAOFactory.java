package fr.eni.javaee.auctions.dal;

public abstract class DAOFactory {
	
	public static DAOArticleVendu getArticleVenduDAOImplSQLServer (){
		return new ArticleVenduDAOImplSQLServer();
	}

	public static DAOCategorie getCategorieDAOImplSQLServer() {
		return new CategorieDAOImplSQLServer();
	}

	public static DAOEnchere getEnchereDAOImplSQLServer() {
		// TODO Auto-generated method stub
		return new EnchereDAOImplSQLServer();
	}
	//Ajout Nicolas du 15 Decembre
	
	public static DAOArticleVendu getDAOArticleVendu() {
		return new ArticleVenduDAOImplSQLServer();
	}
	
	public static DAOUtilisateur getUtilisateurDao()  {
			return new UtilisateurDaoJDBCImpl();
		}
	
}
