<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Plateforme d'enchères</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link href="custom.css" rel="stylesheet">
<script type="text/javascript" src="./functions.js"></script>
</head>
<body onload="disableOnLoad()">
	<header>
		<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
	      <h5 class="my-0 mr-md-auto font-weight-normal">ENCHERES COMPANY</h5>
	      <nav class="my-2 my-md-0 mr-md-3">
	        <a class="p-2 text-dark" href="#">Enchères</a>
	        <a class="p-2 text-dark" href="/P_Auctions/ServletNouvelleVente">Vendre un article</a>
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
      	<h3>Filtres</h3>     	
      	<form action="/P_Auctions/ServletEncheresConnectees" method="post" name="filtres" id="filtres">     	
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
				<div class="radioCheck">
				<div class="achats">			
					<c:choose>
							<c:when test="${type.equals(achat)}">
								<div  class="form-check">
									<input type="radio" id="achats" name="connect" value="mesAchats" onchange="disableVentes()" checked> <label for="achats">Achats</label>
								</div>													
									<c:choose>
										<c:when test="${param1 == null}">
											<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="1" name="encheresOuvertes" value="1">
											<label class="form-check-label"  for="1">enchères ouvertes</label> 										
											</div>
										</c:when>
										<c:otherwise>
											<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="1" name="encheresOuvertes" value="1" checked>
											<label class="form-check-label"  for="1">enchères ouvertes</label>										
											</div>
										</c:otherwise>
									</c:choose>	
									<c:choose>
										<c:when test="${param2 == null}">
											<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="2" name="encheresEnCours" value="2">
											<label class="form-check-label"  for="2">enchères en cours</label> 										
											</div>
										</c:when>
										<c:otherwise>
											<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="2" name="encheresEnCours" value="2" checked>
											<label class="form-check-label"  for="2">enchères en cours</label> 										
											</div>
										</c:otherwise>
								</c:choose>	
								<c:choose>
								<c:when test="${param3 == null}">
											<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="3" name="encheresRemportees" value="3">
											<label class="form-check-label"  for="3">mes enchères remportées</label> 										
											</div>
								</c:when>
								<c:otherwise>
											<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="3" name="encheresRemportees" value="3" checked>
											<label class="form-check-label"  for="3">mes enchères remportées</label> 										
											</div>
								</c:otherwise>
								</c:choose>								

				</div>
				<div class="ventes">
							<div class="form-check">
								<input type="radio" id="mesVentes" name="connect" value="mesVentes" onchange="disableAchats()"> <label for="mesVentes">Mes ventes</label>
							</div>

								<c:choose>
									<c:when test="${param4 == null}">
										<div  class="form-check form-switch">
										<input class="form-check-input"  type="checkbox" id="4" name="ventesEnCours" value="4">
										<label class="form-check-label"  for="4">mes ventes en cours</label> 										
										</div>
									</c:when>
									<c:otherwise>
										<div  class="form-check form-switch">
										<input class="form-check-input"  type="checkbox" id="4" name="ventesEnCours" value="4" checked>
										<label class="form-check-label"  for="4">mes ventes en cours</label> 										
										</div>
									</c:otherwise>
								</c:choose>	
								<c:choose>
									<c:when test="${param5 == null}">
										<div  class="form-check form-switch">
										<input class="form-check-input"  type="checkbox" id="5" name="ventesNonDebutees" value="5">
										<label class="form-check-label"  for="5">ventes non débutées</label> 										
										</div>
									</c:when>
									<c:otherwise>
										<div  class="form-check form-switch">
										<input class="form-check-input"  type="checkbox" id="5" name="ventesNonDebutees" value="5" checked>
										<label class="form-check-label"  for="5">ventes non débutées</label> 										
										</div>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${param6 == null}">
										<div  class="form-check form-switch">
										<input class="form-check-input"  type="checkbox" id="6" name="ventesTerminees" value="6">
										<label class="form-check-label"  for="6">ventes terminées</label> 										
										</div>
									</c:when>
									<c:otherwise>
										<div  class="form-check form-switch">
										<input class="form-check-input"  type="checkbox" id="6" name="ventesTerminees" value="6" checked>
										<label class="form-check-label"  for="6">ventes terminées</label> 										
										</div>
									</c:otherwise>
								</c:choose>								
	</c:when>
	<c:otherwise>
			</div>						
			<div class="achats">		
							<div class="form-check">
								<input type="radio" id="achats" name="connect" value="mesAchats" onchange="disableVentes()"> <label for="achats">Achats</label>
							</div>							

								<c:choose>
									<c:when test="${param1 == null}">
										<div  class="form-check form-switch">
										<input class="form-check-input"  type="checkbox" id="1" name="encheresOuvertes" value="1">
										<label class="form-check-label"  for="1">enchères ouvertes</label> 										
										</div>
									</c:when>
									<c:otherwise>
										<div  class="form-check form-switch">
										<input class="form-check-input"  type="checkbox" id="1" name="encheresOuvertes" value="1" checked>
										<label class="form-check-label"  for="1">enchères ouvertes</label> 										
										</div>
									</c:otherwise>
								</c:choose>	
								<c:choose>
									<c:when test="${param2 == null}">
										<div  class="form-check form-switch">
										<input class="form-check-input"  type="checkbox" id="2" name="encheresEnCours" value="2">
										<label class="form-check-label"  for="2">enchères en cours</label> 										
										</div>
									</c:when>
									<c:otherwise>
										<div  class="form-check form-switch">
										<input class="form-check-input"  type="checkbox" id="2" name="encheresEnCours" value="2" checked>
										<label class="form-check-label"  for="2">enchères en cours</label> 										
										</div>
									</c:otherwise>
								</c:choose>	
								<c:choose>
									<c:when test="${param3 == null}">
										<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="3" name="encheresRemportees" value="3">
											<label class="form-check-label"  for="3">mes enchères remportées</label> 										
										</div>
									</c:when>
									<c:otherwise>
										<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="3" name="encheresRemportees" value="3" checked>
											<label class="form-check-label"  for="3">mes enchères remportées</label> 										
										</div>
									</c:otherwise>
								</c:choose>								

							</div>
			<div class="ventes">
							<div class="form-check">
								<input type="radio" id="mesVentes" name="connect" value="mesVentes" onchange="disableAchats()" checked> <label for="mesVentes">Mes ventes</label>
							</div>						

								<c:choose>
									<c:when test="${param4 == null}">
										<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="4" name="ventesEnCours" value="4">
											<label class="form-check-label"  for="4">mes ventes en cours</label> 										
										</div>
									</c:when>
									<c:otherwise>
										<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="4" name="ventesEnCours" value="4" checked>
											<label class="form-check-label"  for="4">mes ventes en cours</label> 										
										</div>
									</c:otherwise>
								</c:choose>	
								<c:choose>
									<c:when test="${param5 == null}">
										<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="5" name="ventesNonDebutees" value="5">
											<label class="form-check-label"  for="5">ventes non débutées</label> 										
										</div>
									</c:when>
									<c:otherwise>
										<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="5" name="ventesNonDebutees" value="5" checked>
											<label class="form-check-label"  for="5">ventes non débutées</label> 										
										</div>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${param6 == null}">
										<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="6" name="ventesTerminees" value="6">
											<label class="form-check-label"  for="6">ventes terminées</label> 										
										</div>
									</c:when>
									<c:otherwise>
										<div  class="form-check form-switch">
											<input class="form-check-input"  type="checkbox" id="6" name="ventesTerminees" value="6" checked>
											<label class="form-check-label"  for="6">ventes terminées</label> 										
										</div>
									</c:otherwise>
								</c:choose>		

							</div>						
		</c:otherwise>
</c:choose>					
			</div>
			</div>
			<div class="btnRecherche">
      			<input type="submit" value="Recherche" class="btn btn-secondary btn-lg">
      		</div>
</form>
	
		<c:if test="${type.equals(achat)}">
			<div class="container">
      			<div class="row mb-2">
					<c:forEach items="${liste}" var="listeResult">	
			   			<div class="col-md-6">
          					<div class="card flex-md-row mb-4 box-shadow h-md-250">
           						 <div class="card-body d-flex flex-column align-items-start">
              							<strong class="d-inline-block mb-2 text-success">${listeResult.getArticleVendus().getCategorie().getLibelle()}</strong>
              							<h3 class="mb-0">
                						<a class="text-dark" href="/P_Auctions/ServletDetailsArticle?idArticle=${listeResult.getArticleVendus().getNoArticle()}">${listeResult.getArticleVendus().getNomArticle()}</a>
              							</h3>
              							<div class="mb-1 text-muted">Fin enchère: ${listeResult.getArticleVendus().getDateFinEncheres() }</div>
              							<p class="card-text mb-auto">Prix: ${listeResult.getArticleVendus().getMiseAPrix() }</p>
              							<p class="card-text mb-auto">${listeResult.getArticleVendus().getDescription() }</p>
              							<a href="/P_Auctions/ServletProfilUtilisateur?idRech=${listeResult.getArticleVendus().getUtilisateur().getNoUtilisateur()}">${listeResult.getArticleVendus().getUtilisateur().getPseudo()}</a>
            					</div>
            					<img id="imgTN" class="card-img-right flex-auto d-none d-md-block" data-src="holder.js/200x250?theme=thumb" alt="Thumbnail [200x250]" src="./images/cone.jpg" data-holder-rendered="true" style="width: 200px; height: 250px;">
          					</div>
        				</div>
					</c:forEach>
				</div>
    		</div>
		</c:if>
		
		<c:if test="${type.equals(vente)}">
		   <div class="container">
      			<div class="row mb-2">
					<c:forEach items="${liste}" var="listeResult">	
			   			<div class="col-md-6">
          					<div class="card flex-md-row mb-4 box-shadow h-md-250">
           						 <div class="card-body d-flex flex-column align-items-start">
              							<strong class="d-inline-block mb-2 text-success">${listeResult.getCategorie().getLibelle()}</strong>
              							<h3 class="mb-0">
                						<a class="text-dark" href="/P_Auctions/ServletDetailsArticle?idArticle=${listeResult.getNoArticle()}">${listeResult.getNomArticle()}</a>
              							</h3>
              							<div class="mb-1 text-muted">Fin enchère: ${listeResult.getDateFinEncheres() }</div>
              							<p class="card-text mb-auto">Prix: ${listeResult.getMiseAPrix() }</p>
              							<p class="card-text mb-auto">${listeResult.getDescription() }</p>
              							<a href="/P_Auctions/ServletProfilUtilisateur?idRech=${listeResult.getUtilisateur().getNoUtilisateur()}">${listeResult.getUtilisateur().getPseudo()}</a>
            							
            					</div>
            					<img id="imgTN" class="card-img-right flex-auto d-none d-md-block" data-src="holder.js/200x250?theme=thumb" alt="Thumbnail [200x250]" src="./images/cone.jpg" data-holder-rendered="true" style="width: 200px; height: 250px;">
          					</div>
        				</div>
					</c:forEach>
				</div>
    		</div>
		</c:if> 
		
    		
	</main>
	<footer> </footer>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>