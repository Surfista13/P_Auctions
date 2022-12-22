<%@page import="fr.eni.javaee.auctions.messages.LecteurMessage"%>
<%@page import="fr.eni.javaee.auctions.bo.Utilisateur"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<link href="custom.css" rel="stylesheet">
<title>Inscription</title>
</head>
<body>	
<header>
		<div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
	      <h5 class="my-0 mr-md-auto font-weight-normal">ENCHERES COMPANY</h5>
	      <nav class="my-2 my-md-0 mr-md-3">
	        <a class="btn btn-sm btn-outline-secondary" href="/P_Auctions/ServletConnexionUtilisateur">Se connecter</a>
	      </nav>
	    </div>
</header>	
<div class="jumbotron p-3 p-md-5 text-white rounded bg-dark">
		        <div class="col-md-7 px-0">
			          <h1 class="display-4 font-italic">Mes ventes et mes enchères</h1>
			          <p class="lead my-3">Première plateforme d'enchères pour les articles de secondes vie. Acheter, vendre donnez une nouvelle vie à vos produits d'occasions.  </p>
		        </div>
      	</div>
<div class="container">
	<%
		List<Integer> listeCodesErreur = (List<Integer>)request.getAttribute("listeCodesErreur");
		if(listeCodesErreur != null) {
	%>
		<p style="color:red">Erreur, l'inscription n'a pas pu être effectuée :</p>
		<%
			for(int code : listeCodesErreur) {
		%>
			<%=LecteurMessage.getMessageErreur(code) %></p>
		<%
			}
		}
	%>	

      <div class="row">
        <div>
        </div>
        <div class="col-md-8 order-md-1">
            <div class="py-5 text-center">
        <h2>Inscription</h2>
        <p class="lead">Merci de compléter le formulaire ci-dessous pour créer votre profil</p>
      </div>
          <form class="form-signin" action="<%=request.getContextPath()%>/ServletInscription" method="post">
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="idPrenom">Prénom</label>
                <input type="text" class="form-control" id="idPrenom" name="prenom" required>
                <div class="invalid-feedback">
                  Valid first name is required.
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="idNom">Nom</label>
                <input type="text" class="form-control" id="idNom" name="nom" required>
                <div class="invalid-feedback">
                  Valid last name is required.
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label for="idPseudo">Pseudo</label>
                <input type="text" class="form-control" id="idPseudo" name="pseudo" required>
                <div class="invalid-feedback" style="width: 100%;">
                  Your username is required.
                </div>
            </div>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="idMotDePasse">Mot de passe</label>
                <input type="password" class="form-control" id="idMotDePasse" name="motDePasse" required>
                <div class="invalid-feedback">
                  Valid first name is required.
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="idConfirmation">Confirmation mot de passe</label>
                <input type="password" class="form-control" id="idConfirmation" name="confirmation" required>
                <div class="invalid-feedback">
                  Valid last name is required.
                </div>
              </div>
            </div>
            <div class="row">
            	<div class="col-md-6 mb-3">
            	<label for="idEmail">Email</label>
              	<input type="email" class="form-control" id="idEmail"  name="email" required>
              <div class="invalid-feedback">
                Please enter your shipping address.
              </div>
              </div>
            </div>
            <div class="row">
            	<div class="col-md-6 mb-3">
            	<label for="idTelephone">Téléphone</label>
              	<input type="text" class="form-control" id="idTelephone" name="telephone" required>
              <div class="invalid-feedback">
                Please enter your shipping address.
              </div>
              </div>
            </div>
            <div class="mb-3">
              <label for="idRue">Addresse</label>
              <input type="text" class="form-control" id="idRue"  name="rue" required>
              <div class="invalid-feedback">
                Please enter your shipping address.
              </div>
            </div>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="firstName">Code Postal</label>
                <input type="number" class="form-control" id="firstName" name="codePostal" required>
                <div class="invalid-feedback">
                  Valid first name is required.
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="idVille">Ville</label>
                <input type="text" class="form-control" id="idVille" name="ville" required>
                <div class="invalid-feedback">
                  Valid last name is required.
                </div>
              </div>
            </div>
			</div>
            </div>
            </br>
            </br>
            <div class="row">
            	<div class="col-md-4 order-md-2 mb-4">
            	<input class="btn btn-secondary btn-lg" type="submit" value="Créer">
            	<a href="<%=request.getContextPath()%>/ServletListeEncheresNonConnecte"><input class="btn btn-secondary btn-lg" type="button" value="Annuler"></a>	   			
            	</div>
   			</div>
          </form>
        </div>
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