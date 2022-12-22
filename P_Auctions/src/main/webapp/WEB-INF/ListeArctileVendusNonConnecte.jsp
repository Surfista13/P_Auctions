<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ page import="fr.eni.javaee.auctions.bo.ArticleVendu"%>
<%@ page import="java.util.List"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link href="custom.css" rel="stylesheet">
<title>Plateforme d'enchères</title>
</head>
<body>
<header>
		<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
	      <h5 class="my-0 mr-md-auto font-weight-normal">ENCHERES COMPANY</h5>
	      <nav class="my-2 my-md-0 mr-md-3">
	        <a class="btn btn-sm btn-outline-secondary" href="/P_Auctions/ServletInscription">S'inscrire</a>
	        <a class="btn btn-sm btn-outline-secondary" href="/P_Auctions/ServletConnexionUtilisateur">Se connecter</a>
	      </nav>
	    </div>
	</header>
	<main class="main">
		<div class="jumbotron p-3 p-md-5 text-white rounded bg-dark">
		   	<div class="col-md-9 px-0">
			   <h1 class="display-4 font-italic">${slogan }</h1>
			   <p class="lead my-3">Première plateforme d'enchères pour les articles de secondes vie. Acheter, vendre donnez une nouvelle vie à vos produits d'occasions.  </p>
			</div>
      	</div>
		<h2>Liste des enchères en cours</h2>
		<h3>Filtres</h3>
		<form action="/P_Auctions/ServletListeEncheresNonConnecte"
			method="post" name="filtres" id="filtres">
	      	<div class="filtres">
				<div class="col-md-3 position-relative">
				    <input type="text" class="form-control" id="validationTooltip01" name="recherche" id="recherche"
						placeholder="Recherche par nom d'article">
			    </div>
				<div class="col-md-3 position-relative">
				    	<select name="categories" id="categories" class="form-control" aria-label="Default select example">
							<option value="Toutes">Toutes</option>
							<c:forEach items="${listeCategories}" var="listeCate">
								<c:choose>
									<c:when test="${selected == listeCate.getLibelle()}">
										<option value="${listeCate.getLibelle() }" selected>${listeCate.getLibelle()}</option>
									</c:when>
									<c:otherwise>
										<option value="${listeCate.getLibelle() }">${listeCate.getLibelle()}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
				  </div>
	      	   </div>
			<div class="btnRecherche">
      			<input type="submit" value="Recherche" class="btn btn-secondary btn-lg">
      		</div>
		</form>
	</main>

	<div class="container">
		<div class="row mb-2">
			<c:forEach items="${liste}" var="listeResult">
				<div class="col-md-6">
          			<div class="card flex-md-row mb-4 box-shadow h-md-250">
           				<div class="card-body d-flex flex-column align-items-start">
           				    <strong class="d-inline-block mb-2 text-success">${listeResult.getCategorie().getLibelle()}</strong>
              				<h3 class="mb-0">
                				<p class="text-dark">${listeResult.getNomArticle() }</p>
              				</h3>
              				<div class="mb-1 text-muted">Fin enchère: ${listeResult.getDateFinEncheres() }</div>
              				<p class="card-text mb-auto">Prix: ${listeResult.getMiseAPrix() }</p>
              				<p class="card-text mb-auto">${listeResult.getDescription() }</p>
              				<p>${listeResult.getUtilisateur().getPseudo()}</p>
						</div>
						<img id="imgTN" class="card-img-right flex-auto d-none d-md-block" data-src="holder.js/200x250?theme=thumb" alt="Thumbnail [200x250]" src="./images/cone.jpg" data-holder-rendered="true" style="width: 200px; height: 200px;">
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<footer> </footer>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>