<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Ricette</title>
        <meta charset="UTF-8">

        <!-- Custom Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Sora:wght@100;200;300;400;500;600;700;800&display=swap" rel="stylesheet">

        <!-- External Files -->
        <spring:url value="/resources/assets/+.png" var="plusPNG" />
        <spring:url value="/resources/assets/lente.png" var="lentePNG" />
        <spring:url value="/resources/assets/logo.png" var="logoPNG" />

        <spring:url value="/resources/style.css" var="styleCSS" />
        <spring:url value="/resources/recipes/header.js" var="headerrecipesJS" />
        <spring:url value="/resources/login/header.css" var="headerloginCSS" />
        <spring:url value="/resources/recipes/header.css" var="headerrecipesCSS" />
        <spring:url value="/resources/recipes/recipes.css" var="recipesCSS" />

        <link href="${styleCSS}" rel="stylesheet" />
        <script src="${headerrecipesJS}"></script>
        <link href="${headerloginCSS}" rel="stylesheet" />
        <link href="${headerrecipesCSS}" rel="stylesheet" />
        <link href="${recipesCSS}" rel="stylesheet" />

    </head>
    <body>
        <header-sito author="${autore}" logo="${logoPNG}" plus="${plusPNG}"
        search="showrecipes" add="Addrecipes" lente="${lentePNG}"
        ingredients="location.href='editUserIng?nick=${autore}';" 
		tools="location.href='editUserEquip?nick=${autore}';"></header-sito>

         <div id="lista"></div>

         <script>

            var div = document.getElementById("lista");
            var ricette = new Array();
            <c:forEach var="recipes" items="${listRicette}">
                ricette.push(new Array('${recipes.ID}','${recipes.nome}','${recipes.descrizione}'));
            </c:forEach>
            for (const val of ricette) {
                var cont = document.createElement("div");
                cont.innerHTML += "<h2>"+val[1]+"</h2><p>"+val[2]+"</p>";
                cont.onclick = function() {location.href='editRecipe?id='+val[0]+'';};
                div.appendChild(cont);
            }

        </script>
    </body>
</html>
