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


	<form action="" method="post">

		<label for="idArticle">Article :</label> <input type="text"
			id="idArticle" name="article"> <br>
		<br> 
		<label for="idDescription">Description :</label>
		<textarea id="idDescription" name="description" rows="5" cols="33"></textarea>

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
			type="number" id="idPrix" name="prix" placeholder="100" step="5"
			min="5"> <br>
		<br> <label for="idDebutEnchere">Début de l'enchère</label> <input
			type="date" id="idDebutEnchere" name="dateDebut"> <br>
		<br> <label for="idFinEnchere">Fin de l'enchère</label> <input
			type="date" id="idFinEnchere" name="dateFin"> <br>
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
		<br> <input type="submit" value="Enregistrer" name="Update">
		<br>
			<input type ="submit" value="Annuler la vente" name="Delete">

	</form>

	<a href="/P_Auctions/ServletEncheresConnectees">Annuler</a>

	</form>

</body>
</html>