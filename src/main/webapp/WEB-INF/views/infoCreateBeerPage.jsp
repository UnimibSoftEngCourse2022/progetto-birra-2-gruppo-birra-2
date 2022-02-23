<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="it">
	<head>
		<meta charset="UTF-8">
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
		<spring:url value="/resources/form.css" var="formCSS" />
		<spring:url value="/resources/recipes/addRecipes.css" var="addRecipesCSS" />

		<link href="${styleCSS}" rel="stylesheet" />
		<script src="${headerJS}"></script>
		<link href="${headerloginCSS}" rel="stylesheet" />
		<link href="${headerrecipesCSS}" rel="stylesheet" />
		<link href="${recipesCSS}" rel="stylesheet" />
		<link href="${formCSS}" rel="stylesheet" />
		<link href="${addRecipesCSS}" rel="stylesheet" />
	
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

			#labelNote {
				margin-top: -5px;
			}

			#ContainerForm {
				margin-top: 100px;
			}
		</style>

		<header-simple logo="${logoPNG}" plus="${plusPNG}"
		ingredients="location.href='editUserIng?nick=${autore}';" 
		tools="location.href='editUserEquip?nick=${autore}';"></header-simple>
		
		<div class="VStack" id="ContainerForm">
			<div class="ContainerForm">
				<h1>Crea la tua birra</h1>
				
				<form action="makebeer" method="POST">
					<div class="VStack">

						<label class="insert" for="nome">Quantità</label>
						<input class="insert" type="number" step=".01"
						min=0.01 max=50 id="quantita" name="quantita" value="" 
						required />

						<input type="hidden" id="autore" name="autore" value="${autore}"/>
						<input type="hidden" id="idricetta" name="idricetta" value="${IDRicetta}"/>
						&nbsp;&nbsp;
						
						<label class="insert" for="note" id="labelNote">Note</label>
						<textarea id="note" name="note"></textarea>
						
						<div class="HStack">
							<input class="send" type="submit" id="submit" value="Salva"/>
						</div>

					</div>
				</form>
			</div>
		</div>

</body>
</html>