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
<title>Modification d'une vente</title>
</head>
<body>

	<h1>Modification de votre vente</h1>
	
		<p class="erreur">${requestScope.err }</p>

	<form action="/P_Auctions/ServletModificationVente" method="post">

		<label for="idArticle">Article :</label> <input type="text"
			id="idArticle" name="article" value=" ${article.getNomArticle()}"> <br>
		<br> 
		<label for="idDescription">Description :</label>
		<textarea id="idDescription" name="description"  rows="5" cols="33">${article.getDescription()}</textarea>

		<br>
		<br> 
		<label for="select-categorie">Choix catégorie</label> 
		 <select
			name="listeCategories" id="select-categorie">
			<option value="">--Please choose an option--</option>
			

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


		</select> <br>
		<br> <input type="file" id="produit" name="photo"
			accept="image/png, image/jpeg"> <br>
		<br> <label for="idPrix">Mise à prix : </label> <input
			type="number" id="idPrix" name="prix" value="${article.getMiseAPrix() }" placeholder="100" step="5"
			min="5"> <br>
		<br> <label for="idDebutEnchere">Début de l'enchère</label> <input
			type="date" id="idDebutEnchere" name="dateDebut" value="${article.getDateDebutEncheres() }"> <br>
		<br> <label for="idFinEnchere">Fin de l'enchère</label> <input
			type="date" id="idFinEnchere" value="${article.getDateFinEncheres() }" name="dateFin"> <br>
		<br>
		<fieldset>
			<legend>Retrait</legend>

			<label for="idRue">Rue :</label> <input type="text" id="idRue"
				name="rue" value="${sessionScope.utilisateurConnecte.rue }"> <br>
			<br> 
			
			<label for="idCodePostal">Code postal :</label> <input
				type="text" id="idCodePostal" name="codePostal" value="${sessionScope.utilisateurConnecte.codePostal }"> <br>
			<br> 
			
			<label for="idVille">Ville:</label> <input type="text"
				id="idVille" name="ville" value="${sessionScope.utilisateurConnecte.ville }">

		</fieldset>
		<br>
		<br> <input type="submit"  name="Update" value="Enregistrer">
		<br>
			
			<input type="text" value="${article.getNoArticle()}" name="majArticle" hidden>

	</form>

	<a href="/P_Auctions/ServletEncheresConnectees">Annuler</a>

	</form>

</body>
</html>