<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>BrewDay!</title>
		
		<!-- Custom Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Sora:wght@100;200;300;400;500;600;700;800&display=swap" rel="stylesheet">

        <!-- External Files -->
        <spring:url value="/resources/assets/eye.disable.png" var="disabledPNG" />
        <spring:url value="/resources/assets/eye.able.png" var="ablePNG" />
        <spring:url value="/resources/assets/logo.png" var="logoPNG" />
        <link href="${logoPNG}" rel="icon" />

        <spring:url value="/resources/style.css" var="styleCSS" />
        <spring:url value="/resources/home/header.js" var="headerhomeJS" />
        <spring:url value="/resources/login/header.css" var="headerloginCSS" />
        <spring:url value="/resources/recipes/header.css" var="headerrecipesCSS" />
		<spring:url value="/resources/home/home.css" var="homeCSS" />

        <link href="${styleCSS}" rel="stylesheet" />
        <script src="${headerhomeJS}"></script>
        <link href="${headerloginCSS}" rel="stylesheet" />
        <link href="${headerrecipesCSS}" rel="stylesheet" />
		<link href="${homeCSS}" rel="stylesheet" />

	</head>
	<body>
		
		<header-home logo="${logoPNG}" value="Esci" submitTo="logout"
		ingredients="location.href='editUserIng?nick=${autore}';" 
		tools="location.href='editUserEquip?nick=${autore}';">
		</header-home>

	<div id="lista">
 		<h3>Cosa dovrei preparare oggi?</h3>
 	</div>
 	
 	<script>
		var div = document.getElementById("lista");
		var ricette = new Array();
		var quantita = "";
		<c:forEach var="recipes" items="${listRicetta}">
			ricette.push(new Array("${recipes.ID}","${recipes.nome}","${recipes.descrizione}"));                              
		</c:forEach>
		
		<c:forEach var="qtn" items="${listQuantita}">
			var text = "${qtn}";
			quantita += text;                              
		</c:forEach>
		
		if(ricette.length == 0)
		{
			var vuoto = document.createElement("p");
			vuoto.innerText = "Purtroppo nulla :(";
			div.appendChild(vuoto);
		}
		else {for (const val of ricette)
	   	{
			var cont = document.createElement("div");
			cont.innerHTML += "<h2>"+val[1]+"</h2><p>"+val[2]+" <br>Quantità massima producibile (consigliata): "+quantita+" L</p>";
			cont.onclick = function() {location.href='makeBeerPage?id='+val[0]+'';};
			div.appendChild(cont);
	   	}}	
	</script>

	</body>
</html>