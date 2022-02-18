<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
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
		<spring:url value="/resources/recipes/addFormRecipe.js" var="addFormRecipeJS" />

		<link href="${styleCSS}" rel="stylesheet" />
		<script src="${headerJS}"></script>
		<link href="${headerloginCSS}" rel="stylesheet" />
		<link href="${headerrecipesCSS}" rel="stylesheet" />
		<link href="${recipesCSS}" rel="stylesheet" />
		<link href="${formCSS}" rel="stylesheet" />
		<link href="${addRecipesCSS}" rel="stylesheet" />
		<script src="${addFormRecipeJS}"></script>
	
	</head>
	<body>

		<header-simple logo="${logoPNG}" plus="${plusPNG}" add=""
		ingredients="location.href='editUserIng?nick=${autore}';" 
		tools="location.href='editUserEquip?nick=${autore}';"></header-simple>

		<div class="VStack">
			<div class="ContainerForm">
				<h1>Modifica la tua ricetta</h1>
				
				<form:form action="modifyInfoRecipe" method="POST" modelAttribute="r">
					<div class="VStack">

						<label class="insert" for="nome">Nome</label>
						<input type="hidden" id="iD" name="iD" value="${ricettaID}"/>
						<input class="insert" type="text" id="nome" name="nome" value="${Ricetta.nome}" 
							pattern="^[^-\s][A-Za-z0-9!&()?'ìèé-ùàò_.:,;\s-]{3,}$"
							required 
							oninvalid="this.setCustomValidity('Nome non consentito')"
							oninput="this.setCustomValidity('')"/>
						
						<label class="insert" for="nome">Descrizione</label>
						<textarea id="descrizione" name="descrizione">${Ricetta.descrizione}</textarea>
						
						<label class="insert" for="nome">Procedimento</label>
						<textarea id="procedimento" name="procedimento">${Ricetta.procedimento}</textarea>
						<input type="hidden" id="autore" name="autore" value="${autore}"/>
						
						<div class="HStack">
							<input class="send" type="submit" id="submit" value="Avanti"/>
						</div>

					</div>
				</form:form>
			</div>
		</div>
	 	
	</body>
</html>

