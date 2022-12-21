<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Plateforme d'enchères</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link href="custom.css" rel="stylesheet">
</head>
<body>
	<header>
		<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
	      <h5 class="my-0 mr-md-auto font-weight-normal">ENCHERES COMPANY</h5>
	      <nav class="my-2 my-md-0 mr-md-4">
	        <a class="p-2 text-dark" href="/P_Auctions/ServletEncheresConnectees">Mes enchères et ventes</a>
	        <a class="p-2 text-dark" href="/P_Auctions/ServletNouvelleVente">Vendre un article</a>
	        <a class="p-2 text-dark" href="/P_Auctions/ServletListeEncheresNonConnecte">Déconnexion</a>
	        <button id="btnCredits" type="button" class="btn btn-primary position-relative">
  				Credits
  				<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
   				${credit}
  				</span>
			</button>
			<a class="p-2 text-dark" href="/P_Auctions/ServletProfilUtilisateur?idRech=${idConnect}">Mon profil (${pseudo })</a>
			<a class="avatar avatar-sm p-0 show" href="/P_Auctions/ServletProfilUtilisateur?idRech=${idConnect}" id="profileDropdown" role="button" data-bs-auto-close="outside" data-bs-display="static" data-bs-toggle="dropdown" aria-expanded="true">
				<img class="rounded-circle" src="./images/Didier.png" alt="avatar">
			</a>
	      </nav>
	    </div>
	</header>
	<main class="main">
		<div class="jumbotron p-3 p-md-5 text-white rounded bg-dark">
		  <div class="col-md-7 px-0">
			<h1 class="display-4 font-italic">Mes ventes et mes enchères</h1>
			<p class="lead my-3">Première plateforme d'enchères pour les articles de secondes vie. Acheter, vendre donnez une nouvelle vie à vos produits d'occasions.  </p>
		  </div>
      	</div>
      	<div class="corps">
      		<div class="photo">
      		</div>
      		<div class="details">
      			<p>${article.getNomArticle() }</p>
      			<p>Description: ${article.getDescription() }</p>
      			<p>Catégorie: ${article.getCategorie().getLibelle() }</p>
      			<p>Meilleur offre: ${meilleurEnchere.getMontant_enchere()} crédits  par ${meilleurEnchere.getUtilisateur().getPseudo()}</p>
      			<p>Mise a prix: ${article.getMiseAPrix() } crédits</p>
      			<p>Fin de l'enchère: ${article.getDateFinEncheres() }</p>
      			<p>Retrait: ${article.getRetrait().getRue()} ${article.getRetrait().getCodePostal()}   ${article.getRetrait().getVille() }</p>
      			<p>Vendeur:  ${article.getUtilisateur().getPseudo()}</p>
      			<p>Téléphone: ${article.getUtilisateur().getTelephone()}</p>
      			<form action="/P_Auctions/Encherir?idArticle=${article.getNoArticle() }" method="post" name="encherir" id="encherir">
      				<label for="enchere">Ma proposition:</label>
					<input type="number" id="enchere" name="enchere" min="1" max="10000000">
					<input type="submit" value="Encherir" class="btn btn-secondary btn-lg">
      			</form>
      			<p>${retourEnchere }</p>
      			<button type="button" class="btn btn-secondary">Retrait effectué</button>
      			<button type="button" class="btn btn-secondary" name="Modifier"><a href="/P_Auctions/ServletModificationVente?idArticle=${article.getNoArticle() }">Modifier la vente</a></button>
      			  			
      		</div>
      	</div>	
  	</main> 
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>    
</body>
</html>