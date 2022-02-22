<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
        <spring:url value="/resources/assets/x.png" var="xPNG" />
        <spring:url value="/resources/assets/circle.png" var="circlePNG" />
        <link href="${logoPNG}" rel="icon" />

        <spring:url value="/resources/style.css" var="styleCSS" />
        <spring:url value="/resources/brews/header.js" var="headerbrewsJS" />
        <spring:url value="/resources/login/header.css" var="headerloginCSS" />
        <spring:url value="/resources/recipes/header.css" var="headerrecipesCSS" />
        <spring:url value="/resources/recipes/recipes.css" var="recipesCSS" />
		<spring:url value="/resources/brews/brews.css" var="brewsCSS" />
        <spring:url value="/resources/components/recipeCard.js" var="recipeCardJS" />
        <spring:url value="/resources/components/recipeCard.css" var="recipeCardCSS" />
        <spring:url value="/resources/components/modal.css" var="modalCSS" />

        <link href="${styleCSS}" rel="stylesheet" />
        <script src="${headerbrewsJS}"></script>
        <link href="${headerloginCSS}" rel="stylesheet" />
        <link href="${headerrecipesCSS}" rel="stylesheet" />
        <link href="${recipesCSS}" rel="stylesheet" />
		<link href="${brewsCSS}" rel="stylesheet" />
        <script src="${recipeCardJS}"></script>
        <link href="${recipeCardCSS}" rel="stylesheet" />
        <link href="${modalCSS}" rel="stylesheet" />

    </head>
    <body>
        <style>
            .Card {
                cursor: default;
            }

            .Card:hover {
                background: white;
                border-color: #F0F0F1;
            }
        </style>

        <div class="modal">
            <div class="VStack" id="contentModal">
                <div class="HStackBetween">
                    <h3>Lista della Spesa</h3>
                    <div class="CloseButton">
                        <img src="${xPNG}" id="x"></img>
                    </div>
                </div>
                <hr>
                <p>Purtroppo non hai tutto il necessario per produrre la tua birra, ecco cosa devi comprare:</p>
                <div id="contenuto"></div>
            </div>
        </div>
		
        <header-brews logo="${logoPNG}" plus="${plusPNG}"
        add="createBeer"
        ingredients="location.href='editUserIng?nick=${autore}';" 
		tools="location.href='editUserEquip?nick=${autore}';"></header-brews>

		<div id="lista"></div>

		<script>
            var modal = document.getElementsByClassName("modal")[0];
            var closeBtn = document.getElementsByClassName("CloseButton")[0];
            var contenuto = document.getElementById('contenuto');

            closeBtn.onclick = function() {
                modal.style.display = "none";
            }

            window.onclick = function(event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }

            var elementi = new Array();
			<c:forEach var="item" items="${spesa}">
                elementi.push("${item}")
			</c:forEach>
			
            if(elementi.length > 0) {

                for(const val of elementi) {
                    contenuto.innerHTML += `

                    <div class="HStackStart">
                        <img src="${circlePNG}" id="circle"></img>
                        <h4>`+val+`</h4>
                    </div>

                    `;
                }

                modal.style.display = "block";
            }
			    
		</script>

		<script>
			var lista = document.getElementById("lista");
			var birre = new Array();
			<c:forEach var="birre" items="${listBirre}">
				var text = "${birre}";
				birre.push(text.split(" - "));
			</c:forEach>
            
            var number = Math.floor(Math.random() * 5);
            var img = "${honeyPNG}";

            for (const val of birre) {
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
                        title="`+val[0]+` (`+val[1]+` L)"  
                        description="`+val[2]+`">
                    </recipe-card>
            `;
            }
        
        </script>

		</body>
</html>