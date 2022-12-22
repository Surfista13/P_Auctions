    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="fr.eni.javaee.auctions.bo.Categorie"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nouvelle vente</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link href="custom.css" rel="stylesheet">
<script type="text/javascript" src="./functions.js"></script>
</head>
<body>
<header>
		<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
	      <h5 class="my-0 mr-md-auto font-weight-normal">ENCHERES COMPANY</h5>
	      <nav class="my-2 my-md-0 mr-md-3">
	        <a class="p-2 text-dark" href="/P_Auctions/ServletEncheresConnectees">Mes ventes et enchères</a>
	        <a class="p-2 text-dark" href="/P_Auctions/ServletDeconnexionUtilisateur">Déconnexion</a>
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


<div class="container">
      <div class="py-5 text-center">
        <h2>Nouvelle vente</h2>
        <p class="lead">Saisir les détails de l'article que vous souhaitez vendre.</p>
      </div>
      <div class="row">
        <div class="col-md-1 order-md-1 mb-1">
        </div>
        <div class="col-md-8 order-md-1">
          <form class="needs-validation" action="/P_Auctions/ServletNouvelleVente" method="post">
            <div class="mb-3">
              <label for="idArticle">Article <span class="text-muted"></span></label>
              <input type="text" class="form-control" id="idArticle" name="article" required>
            </div>
             <div class="mb-3">
              <label for="idDescription">Description</label>
              <textarea class="form-control" id="idDescription" name="description" rows="4" cols="60" required></textarea>
            </div>
          <div class="col1-md-5 mb-3">
                <label for="country">Catégorie</label>
                <select class="custom-select d-block w-100" name="listeCategories" id="select-categorie">
                <option value="">--Choisir une catégorie article--</option>
			<c:forEach items="${listeCategories}" var="listeCate">
				<c:choose>
					<c:when test="${selected == listeCate.getLibelle()}">
						<option value="${listeCate.getNoCategorie() }" selected>${listeCate.getLibelle()}</option>
					</c:when>
					<c:otherwise>
						<option value="${listeCate.getNoCategorie() }">${listeCate.getLibelle()}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		</br>
		</br>
          <input type="file" id="produit" name="photo" accept="image/png, image/jpeg"> 
          </br>
          </br>
          </br>
           <div class="row">
              <div class="col-md-6 mb-3">
                <label for="idPrix">Mise à prix</label>
                <input type="number" class="form-control" id="idPrix" name="prix" required>
              </div>
              </div>
            </div> 
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="idDebutEnchere">Date début d'enchère</label>
                <input type="date" class="form-control" id="idDebutEnchere" name="dateDebut" required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="idFinEnchere">Date fin d'enchère</label>
                <input type="date" class="form-control" id="idFinEnchere" name="dateFin" required>
              </div>
            </div>
            </br>
            <legend>Retrait</legend> 
            <div class="mb-3">
              <label for="idRue">Addresse</label>
              <input type="text" class="form-control" id="idRue"  name="rue" value="${sessionScope.utilisateurConnecte.rue }" required>
            </div>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="idCodePostal">Code Postal</label>
                <input type="number" class="form-control" id="idCodePostal" name="codePostal" value="${sessionScope.utilisateurConnecte.codePostal }" required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="idVille">Ville</label>
                <input type="text" class="form-control" id="idVille" name="ville" value="${sessionScope.utilisateurConnecte.ville }" required>
              </div>
            </div>       
              </div>
            </div>
            </br>
            </br>
            <div class="container">
            	<div class="col-md-8 order-md-3 mb-4">
            	<input class="btn btn-secondary btn-lg" type="submit" value="Enregistrer">
            	<a href="<%=request.getContextPath()%>/ServletEncheresConnectees"><input class="btn btn-secondary btn-lg" type="button" value="Annuler"></a>	   			
            	</div>
   			</div>
	</form>
        </div>

      <footer class="my-5 pt-5 text-muted text-center text-small">
        <p class="mb-1">© 2012-2013 ENI Enchères company</p>
        <ul class="list-inline">
          <li class="list-inline-item"><a href="#">Contact Team</a></li>
        </ul>
      </footer>
    </div>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>