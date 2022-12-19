<%@page import="fr.eni.javaee.auctions.messages.LecteurMessage"%>
<%@page import="fr.eni.javaee.auctions.bo.Utilisateur"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription</title>
</head>
<body>

	<%
		List<Integer> listeCodesErreur = (List<Integer>)request.getAttribute("listeCodesErreur");
		if(listeCodesErreur != null) {
	%>
		<p style="color:red">Erreur, l'inscription n'a pas pu être effectuée :</p>
		<%
			for(int code : listeCodesErreur) {
		%>
			<p><%=code %> : <%=LecteurMessage.getMessageErreur(code) %></p>
		<%
			}
		}
	%>
	
	<h1>Mon profil</h1>

	<form action="<%=request.getContextPath()%>/ServletInscription"
		method="post">

		<label for="idPseudo">Pseudo :</label> <input type="text"
			id="idPseudo" name="pseudo"> <br> <br> 
			
			<label for="idPrenom">Prénom :</label> <input type="text" id="idPrenom"
			name="prenom"> <br><br>  
			
			<label for="idTelephone">Téléphone	:</label> <input type="number" id="idTelephone" name="telephone" /> <br><br>

		 <label for="idCodePostal">Code postal :</label> <input	type="text" id="idCodePostal" name="codePostal"> <br> <br>
			
		<label for="idMotDePasse">Mot de passe :</label> <input	type="password" id="idMotDePasse" name="motDePasse"> <br><br>

		 <label for="idNom">Nom :</label> <input type="text" id="idNom" name="nom"> <br><br>
			
			 <label	for="idEmail">Email :</label> <input type="email" id="idEmail" name="email"> <br><br> 
			 
			 <label for="idRue">Rue :</label> <input type="text" id="idRue" name="rue"> <br> <br>
			 
		<label for="idVille">Ville :</label> <input type="text" id="idVille" name="ville"><br> <br> 
		
		<label for="idConfirmation">Confirmation :</label> 
		
		<input type="password" id="idConfirmation" name="confirmation"> <br><br>
		
		 <input type="submit" value="Créer"> <br>
		 
	</form>
	
	
	<a href="<%=request.getContextPath()%>/ServletListeEncheresNonConnecte">Annulez</a>
</body>
</html>