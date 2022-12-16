<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Utilisateur Connecte</title>
</head>
<body>

	<c:if test="${!empty sessionScope.utilisateurConnecte}">
		
		<p>Bonjour ${sessionScope.utilisateurConnecte.noUtilisateur}!</p>


	</c:if>

	<h1>Bonjour vous êtes connecté</h1>

</body>
</html>