<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ecrans Connexion Utilisateur</title>
</head>
<body>
<p>LOGO</p>

<form action="<%=request.getContextPath()%>/ServletConnexionUtilisateur" method="post">

	<label for="idIdentifiant">Identifiant :</label>
	<input type="name" id="idIdentifiant" name="identifiant">

	<br><br>

	<label for="IdMotDePasse">Mot de passe:</label>
	<input type="text" id="idMotDePasse" name="motDePasse">

	<br><br>
	
	<input type="checkbox" id="idSeSouvenir" name="check">
	<label for="idSeSouvenir">Se souvenir de moi</label>
	
	<input type="submit" value="Connexion">

</form>

<a href="">Mot de passe oublié?</a>

<br>

<a href="">Créer un compte</a>

</body>
</html>