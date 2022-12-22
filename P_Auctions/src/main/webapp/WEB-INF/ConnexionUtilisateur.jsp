<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ecrans Connexion Utilisateur</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<link rel="canonical"
	href="https://getbootstrap.com/docs/4.0/examples/sign-in/">
<link href="custom.css" rel="stylesheet">
<script type="text/javascript" src="./functions.js"></script>
</head>
<body class="text-center">

	<section class="vh-100 gradient-custom">
		<div class="container py-5 h-100">
			<div class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-12 col-md-8 col-lg-6 col-xl-5">
					<div class="card bg-dark text-white" style="border-radius: 1rem;">
						<div class="card-body p-5 text-center">
							<div class="mb-md-5 mt-md-4 pb-5">
								<h2 class="fw-bold mb-2 text-uppercase">Connexion</h2>
								<p class="text-white-50 mb-5">Merci d'entrer votre identifiant et mot de passe</p>
								<p class="erreur">${requestScope.err }</p>
									<form action="<%=request.getContextPath()%>/ServletConnexionUtilisateur" method="post">
										<div class="form-outline form-white mb-4">
											<label class="form-label" for="typeEmailX">Identifiant</label>
											<input type="name" id="idIdentifiant" name="identifiant" class="form-control form-control-lg" required/> 										
										</div>
										<div class="form-outline form-white mb-4">
											<label class="form-label" for="typePasswordX">Mot de passe</label>
											<input type="password" class="form-control form-control-lg" id="idMotDePasse" name="motDePasse" required /> 
										</div>
										<div class="form-check d-flex justify-content-start mb-4">
              								<input class="form-check-input" type="checkbox" value="" id="form1Example3" />
              								<label class="form-check-label" for="form1Example3">Se souvenir de moi</label>
            							</div>
            							<button class="btn btn-outline-light btn-lg px-5" type="submit">Se connecter</button>
            						</form>
								<p class="small mb-5 pb-lg-2">
									<a class="text-white-50" href="#!">Mot de passe oublié?</a>
								</p>
							</div>
							<div>
								<p class="mb-0">
									Pas de compte ? <a href="<%=request.getContextPath()%>/ServletInscription" class="text-white-50 fw-bold">Créer
										un compte</a>
								</p>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
		integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
		integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
		crossorigin="anonymous"></script>
</body>
</html>