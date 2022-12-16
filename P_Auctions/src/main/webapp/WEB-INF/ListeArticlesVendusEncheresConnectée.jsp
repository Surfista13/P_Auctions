<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ma liste des enchères</title>
<link href="./Style.css" rel="stylesheet">
</head>
<body>
	<header>
		<h1>ENI_Encheres</h1>
		<ul>
			<li><a href="#">Enchères</a></li>
			<li><a href="#">Vendre un article</a></li>
			<li><a href="#">Mon profil</a></li>
			<li><a href="#">Déconnexion</a></li>
		</ul>
	</header>
	<main>
		<h2>Liste des enchères</h2>
		<form action="/P_Auctions/ServletEncheresConnectees" method="post"
			name="filtres" id="filtres">
			<label>Filtres:</label>
			<div>
				<input type="text" name="recherche" id="recherche"
					placeholder="Recherche par nom d'article">
			</div>
			<div>
				<label for="categories">Enter your name: </label> <select
					name="categories" id="categories">
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
			<div>
				<fieldset>
					<div>
						<input type="radio" id="achats" name="connect" value="mesAchats"
							  onclick="disableVentes()" > <label
							for="achats">Achats</label>
					</div>
					<fieldset>
						<input type="checkbox" id="1" name="encheresOuvertes" value="1"
							> <label for="1">enchères en cours</label>
						<input type="checkbox" id="2" name="encheresEnCours" value="2" 
							> <label for="2">mes enchères en
							cours</label> <input type="checkbox" id="3" name="encheresRemportees"
							value="3"  > <label for="3">mes
							enchères remportées</label>
					</fieldset>
					<div>
						<input type="radio" id="mesVentes" name="connect"
							value="mesVentes" onclick="disableAchats()">
						<label for="mesVentes">Mes ventes</label>
					</div>
					<fieldset>
						<input type="checkbox" id="4" name="ventesEnCours" value="4" >
						<label for="4">mes ventes en cours</label> <input type="checkbox"
							id="5" name="ventesNonDebutees" value="5"  > <label for="5">ventes
							non débutées</label> <input type="checkbox" id="6" name="ventesTerminees"
							value="6" > <label for="6">ventes terminées</label>
					</fieldset>
				</fieldset>
				<script>
					function disableAchats() {
						document.getElementById("1").disabled = true;
						document.getElementById("1").checked = false;
						document.getElementById("2").disabled = true;
						document.getElementById("2").checked = false;
						document.getElementById("3").disabled = true;
						document.getElementById("3").checked = false;
						document.getElementById("4").disabled = false;
						document.getElementById("4").checked = true;
						document.getElementById("5").disabled = false;
						document.getElementById("5").checked = true;
						document.getElementById("6").disabled = false;
						document.getElementById("6").checked = true;
					}
					function disableVentes() {
						document.getElementById("1").disabled = false;
						document.getElementById("1").checked = true;
						document.getElementById("2").disabled = false;
						document.getElementById("2").checked = true;
						document.getElementById("3").disabled = false;
						document.getElementById("3").checked = true;
						document.getElementById("4").disabled = true;
						document.getElementById("4").checked = false;
						document.getElementById("5").disabled = true;
						document.getElementById("5").checked = false;
						document.getElementById("6").disabled = true;
						document.getElementById("6").checked = false;
					}
				</script>
			</div>
			<div>
				<input type="submit" value="Recherche">
			</div>
		</form>
		<c:if test="${type.equals(achat)}">
			<div class="blockArticle">
				<c:forEach items="${liste}" var="listeResult">
					<div>
						<img src="./images/cone.jpg">
					</div>
					<ul>
						<li>${listeResult.getArticleVendus().getDescription() }</li>
						<li>Prix: ${listeResult.getArticleVendus().getMiseAPrix() }</li>
						<li>${listeResult.getArticleVendus().getDateFinEncheres() }</li>
						<li>Vendeur:${listeResult.getArticleVendus().getUtilisateur().getPseudo() }</li>
					</ul>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${type.equals(vente)}">
			<div class="blockArticle">
				<c:forEach items="${liste}" var="listeResult">
					<div>
						<img src="./images/cone.jpg">
					</div>

					<ul>
						<li>${listeResult.getDescription() }</li>
						<li>Prix: ${listeResult.getMiseAPrix() }</li>
						<li>Fin de l'enchère: ${listeResult.getDateFinEncheres() }</li>
						<li>Vendeur:${listeResult.getUtilisateur().getPseudo() }</li>
					</ul>
				</c:forEach>
			</div>
		</c:if>
	</main>
	<footer> </footer>
</body>
</html>