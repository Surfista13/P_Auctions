package fr.eni.javaee.auctions.dal;

public abstract class DAOFactory {
	
	public static DAOArticleVendu getArticleVenduDAOImplSQLServer (){
		return new ArticleVenduDAOImplSQLServer();
	}

	public static DAOCategorie getCategorieDAOImplSQLServer() {
		return new CategorieDAOImplSQLServer();
	}
	
	public static DAOUtilisateur getUtilisateurDao()  {
			return new UtilisateurDaoJDBCImpl();
		}
}
