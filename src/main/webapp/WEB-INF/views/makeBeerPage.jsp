<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="it">
	<head>
	<meta charset="ISO-8859-1">
	<title>BrewDay!</title>

	<!-- Custom Fonts -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=Sora:wght@100;200;300;400;500;600;700;800&display=swap" rel="stylesheet">

	<!-- External Files -->
	<spring:url value="/resources/assets/+.png" var="plusPNG" />
	<spring:url value="/resources/assets/-.png" var="minusPNG" />
	<spring:url value="/resources/assets/lente.png" var="lentePNG" />
	<spring:url value="/resources/assets/logo.png" var="logoPNG" />
	<link href="${logoPNG}" rel="icon" />

	<spring:url value="/resources/style.css" var="styleCSS" />
	<spring:url value="/resources/components/header.js" var="headerJS" />
	<spring:url value="/resources/login/header.css" var="headerloginCSS" />
	<spring:url value="/resources/recipes/header.css" var="headerrecipesCSS" />
	<spring:url value="/resources/recipes/recipes.css" var="recipesCSS" />
	<spring:url value="/resources/recipes/editRecipes.css" var="editRecipesCSS" />

	<link href="${styleCSS}" rel="stylesheet" />
	<script src="${headerJS}"></script>
	<link href="${headerloginCSS}" rel="stylesheet" />
	<link href="${headerrecipesCSS}" rel="stylesheet" />
	<link href="${recipesCSS}" rel="stylesheet" />
	<link href="${editRecipesCSS}" rel="stylesheet" />
	
	</head>
	<body>
		<style>
			#birre {
				color: black;
			}

			#ricette {
				color: #8E8E93;
			}

			#ricette:hover {
				color: black;
			}

			.ButtonContainer {
				justify-content: end;
			}
		</style>

		<header-simple logo="${logoPNG}" plus="${plusPNG}" add=""
		ingredients="location.href='editUserIng?nick=${autore}';" 
		tools="location.href='editUserEquip?nick=${autore}';"></header-plus>
		
		<div class="RecipeContainer">
			<div class="VStack" id="RecipeContainer">
				
				<div class="TitleContainer">
					<h1>${Ricetta.nome}</h1>
					
					<div class="ButtonContainer">
						<form action="infoCreateBeer" method="GET">
							<input class="Edit" type="submit" value="Crea una birra" />
						</form>
					</div>
				</div>

				<section>
					<h3>Descrizione</h3>
					<p>${Ricetta.descrizione}<p>
				</section>

				<section>
					<h3>Procedimento</h3>
					<p>${Ricetta.procedimento}<p>
				</section>
				
				<section>
					<h3>Ingredienti</h3>
					<div id="lista-ingredienti">
				</section>

				<section>
					<h3>Attrezzatura</h3>
					<div id="lista-attrezzi"></div>
				</section>

			</div>
		</div>
		<script>

			let listaIngredienti = document.getElementById('lista-ingredienti');
			let listaAttrezzi = document.getElementById('lista-attrezzi');

			var ingredienti = new Array();
			<c:forEach var="component" items="${listRecComponents}" varStatus="status">
				ingredienti.push(new Array("${component}"));
			</c:forEach>

			for (const val of ingredienti) {
				var unità = `g/L`;
				var tipo = "";

				if (val[0].split(" - ")[0].toLowerCase() == 'acqua') {
					unità = `%`;
				} else {
					tipo = val[0].split(" - ")[2] + " ";
				}

				listaIngredienti.innerHTML += `<h6>` 
				+ tipo + val[0].split(" - ")[0] + `\xa0\xa0\xa0` + val[0].split(" - ")[1] + unità +
				`</h6>`;
			}

			var attrezzi = new Array();
			<c:forEach var="tool" items="${listRecTools}" varStatus="status">
				attrezzi.push(new Array("${tool}"));
			</c:forEach>

			for (const val of attrezzi) {
				listaAttrezzi.innerHTML += `<h6>` 
				+ val[0].split(" - ")[2] + `\xa0\xa0\xa0` + val[0].split(" - ")[1] +
				`</h6>`;
			}
		
		</script>
</body>
</html>