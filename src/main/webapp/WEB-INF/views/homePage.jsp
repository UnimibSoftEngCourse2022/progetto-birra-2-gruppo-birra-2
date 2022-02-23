<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <spring:url value="/resources/assets/lente.png" var="lentePNG" />
        <spring:url value="/resources/assets/logo.png" var="logoPNG" />
        <spring:url value="/resources/assets/honey.png" var="honeyPNG" />
        <spring:url value="/resources/assets/grain.png" var="grainPNG" />
        <spring:url value="/resources/assets/hop.png" var="hopPNG" />
        <spring:url value="/resources/assets/malt.png" var="maltPNG" />
        <spring:url value="/resources/assets/water.png" var="waterPNG" />
        <spring:url value="/resources/assets/honey.orange.png" var="honeyorangePNG" />
        <spring:url value="/resources/assets/grain.grey.png" var="graingreyPNG" />
        <spring:url value="/resources/assets/alcohol.png" var="alcoholPNG" />
        <spring:url value="/resources/assets/water.pink.png" var="waterpinkPNG" />
        <link href="${logoPNG}" rel="icon" />

        <spring:url value="/resources/style.css" var="styleCSS" />
        <spring:url value="/resources/home/header.js" var="headerhomeJS" />
        <spring:url value="/resources/login/header.css" var="headerloginCSS" />
        <spring:url value="/resources/recipes/header.css" var="headerrecipesCSS" />
		<spring:url value="/resources/tools/tools.css" var="toolsCSS" />
		<spring:url value="/resources/home/home.css" var="homeCSS" />
		<spring:url value="/resources/components/recipeCard.js" var="recipeCardJS" />
        <spring:url value="/resources/components/recipeCard.css" var="recipeCardCSS" />

        <link href="${styleCSS}" rel="stylesheet" />
        <script src="${headerhomeJS}"></script>
        <link href="${headerloginCSS}" rel="stylesheet" />
        <link href="${headerrecipesCSS}" rel="stylesheet" />
		<link href="${toolsCSS}" rel="stylesheet" />
		<link href="${homeCSS}" rel="stylesheet" />
		<script src="${recipeCardJS}"></script>
        <link href="${recipeCardCSS}" rel="stylesheet" />

	</head>
	<body>
		
		<header-home logo="${logoPNG}" value="Esci" submitTo="logout"
		ingredients="location.href='editUserIng?nick=${autore}';" 
		tools="location.href='editUserEquip?nick=${autore}';">
		</header-home>

		<div class="ContainerForm">
            <h1>Cosa posso preparare oggi?</h1>
                
            <div id="lista"></div>
        </div>
 	
 	<script>
		var lista = document.getElementById("lista");
		var ricette = new Array();
		var quantita = "";
		<c:forEach var="recipes" items="${listRicetta}">
			ricette.push(new Array("${recipes.ID}","${recipes.nome}",`${recipes.descrizione}`));                              
		</c:forEach>
		
		<c:forEach var="qtn" items="${listQuantita}">
			var text = "${qtn}";
			quantita += text;                              
		</c:forEach>

		if(ricette.length == 0) {
			var vuoto = document.createElement("p");
			vuoto.innerText = "Purtroppo nulla :(";
			vuoto.id = "outcard";
			lista.appendChild(vuoto);
		}
		else {
			var number = Math.floor(Math.random() * 5);
			var img = "${honeyPNG}";

			for (const val of ricette) {
				number = Math.floor(Math.random() * 8);
				switch(number) {
					case 0: img = "${honeyPNG}"; break;
					case 1: img = "${hopPNG}"; break;
					case 2: img = "${grainPNG}"; break;
					case 3: img = "${maltPNG}"; break;
					case 4: img = "${waterPNG}"; break;
					case 5: img = "${honeyorangePNG}"; break;
					case 6: img = "${waterpinkPNG}"; break;
					case 7: img = "${graingreyPNG}"; break;
					default: img = "${honeyPNG}"; break;
				}

				lista.innerHTML += `
					<recipe-card 
						image="`+img+`"
						title="`+val[1]+` (fino a `+quantita+` L)" 
						description="`+val[2]+`" 
						elementClicked="location.href='makeBeerPage?id='+`+val[0]+`+'';">
					</recipe-card>
			`;
			}
		}
   
	</script>
	</body>
</html>