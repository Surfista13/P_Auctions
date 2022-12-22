<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plateforme d'enchères: Profil utilisateur</title>
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
			<a class="p-2 text-dark" href="/P_Auctions/ServletProfilUtilisateur?idRech=${id}">Mon profil (${pseudo })</a>
			<a class="avatar avatar-sm p-0 show" href="/P_Auctions/ServletProfilUtilisateur?idRech=${id}" id="profileDropdown" role="button" data-bs-auto-close="outside" data-bs-display="static" data-bs-toggle="dropdown" aria-expanded="true">
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
      	<div class="container">
      <div class="py-5 text-center">
        <h2>Profil</h2>
        <p class="lead"></p>
      </div>

      <div class="row">
        <div class="col-md-4 order-md-2 mb-4">
        </div>
        <div class="col-md-8 order-md-1">
          <h4 class="mb-3">Détails du profil</h4>
          <form class="needs-validation" novalidate="">
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="firstName">Prénom</label>
                <input type="text" class="form-control" id="firstName" placeholder="" value="${utilisateur.getPrenom()}" disabled>
              </div>
              <div class="col-md-6 mb-3">
                <label for="lastName">Nom</label>
                <input type="text" class="form-control" id="lastName" value="${utilisateur.getNom()}" disabled>
              </div>
            </div>

            <div class="mb-3">
              <label for="username">Pseudo</label>
              <div class="input-group">
                <div class="input-group-prepend">
                </div>
                <input type="text" class="form-control" value="${utilisateur.getPseudo()}" id="username"disabled>
              </div>
            </div>

            <div class="mb-3">
              <label for="email">Email</span></label>
              <input type="email" class="form-control" id="email" value="${utilisateur.getEmail()}" disabled>
            </div>
            
            <div class="mb-3">
              <label for="tel">Téléphone</span></label>
              <input type="text" class="form-control" id="tel" value="${utilisateur.getTelephone()}" disabled>
            </div>

            <div class="mb-3">
              <label for="address">Adresse</label>
              <input type="text" class="form-control" id="address" value="${utilisateur.getRue()}" disabled>
            </div>

			<div class="row">
              <div class="col-md-6 mb-3">
                <label for="firstName">Code postale</label>
                <input type="text" class="form-control" id="codePostale" value="${utilisateur.getCodePostal()}" disabled>
              </div>
              <div class="col-md-6 mb-3">
                <label for="lastName">Ville</label>
                <input type="text" class="form-control" id="ville" value="${utilisateur.getVille()}" disabled>
              </div>
            </div>

            <div class="row">
            </div>
          </form>
        </div>
      </div>
      <c:if test="${utilisateur.getNoUtilisateur() == id}">
      	<button type="button" id="boutModifier" class="btn btn-secondary" >Modifier</button>
      </c:if>     
      <footer class="my-5 pt-5 text-muted text-center text-small">
        <p class="mb-1">© 2012-2013 ENI Enchères company</p>
        <ul class="list-inline">
          <li class="list-inline-item"><a href="#">Contact Team</a></li>
        </ul>
      </footer>
    </div>
      	
      	
      	
      	
	
	</main>
	
	
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>