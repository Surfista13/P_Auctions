<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
 <%@ page import="fr.eni.javaee.auctions.bo.ArticleVendu" %>
 <%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste des enchères</title>
</head>
<body>
<header>
	<h1>ENI_Encheres</h1>
	<ul>
		<li><a href="#">S'inscrire</a></li>
		<li><a href="#">Se connecter</a></li>
	</ul>
</header>
<main>
	<h2>Liste des enchères</h2>
	<form action="/P_Auctions/ServletListeEncheresNonConnecte" method="post" name="filtres" id="filtres">
	<label>Filtres:</label>
	<div>
	    <input type="text" name="recherche" id="recherche" placeholder="Recherche par nom d'article">
  	</div>
  <div>
    <label for="categories">Enter your name: </label>
    <select name="categories" id="categories">	   
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
    <input type="submit" value="Recherche">
  </div>
</form>	
</main>
<footer>
</footer>
<ul>
   <c:forEach items="${liste}" var="listeResult">
   <li>${listeResult.getNomArticle() }</li>
   <li>${listeResult.getMiseAPrix() }</li>
   <li>${listeResult.getDateFinEncheres() }</li>
   <li>Vendeur:${listeResult.getUtilisateur().getPseudo() }</li>
   </br>
   </c:forEach> 
</ul>
</body>
</html>