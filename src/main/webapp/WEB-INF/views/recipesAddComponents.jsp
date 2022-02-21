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
        <spring:url value="/resources/assets/+.png" var="plusPNG" />
		<spring:url value="/resources/assets/-.png" var="minusPNG" />
        <spring:url value="/resources/assets/logo.png" var="logoPNG" />
		<link href="${logoPNG}" rel="icon" />

        <spring:url value="/resources/style.css" var="styleCSS" />
        <spring:url value="/resources/components/header.js" var="headerJS" />
        <spring:url value="/resources/login/header.css" var="headerloginCSS" />
        <spring:url value="/resources/recipes/header.css" var="headerrecipesCSS" />
        <spring:url value="/resources/recipes/recipes.css" var="recipesCSS" />
		<spring:url value="/resources/ingredients/ingredients.css" var="ingredientsCSS" />

        <link href="${styleCSS}" rel="stylesheet" />
        <script src="${headerJS}"></script>
        <link href="${headerloginCSS}" rel="stylesheet" />
        <link href="${headerrecipesCSS}" rel="stylesheet" />
        <link href="${recipesCSS}" rel="stylesheet" />
		<link href="${ingredientsCSS}" rel="stylesheet" />
	</head>
	
	<body>
		<style>
			#ricette {
			color: black;
			}

			#ingredienti {
			color: #8E8E93;
			}

			#ingredienti:hover {
			color: black;
			}
		</style>
	<header-simple logo="${logoPNG}" plus="${plusPNG}" add=""
	ingredients="location.href='editUserIng?nick=${autore}';" 
	tools="location.href='editUserEquip?nick=${autore}';"></header-simple>
	
	<div class="ContainerForm">
		<h1>Aggiungi gli ingredienti</h1>
		<form action="addcomponents" method="POST">

			<div id="container"></div>

			<input type="hidden" id="ricetta" name="ricetta" value="${ricettaID}"/>

		  <div class="HStack" id="addContainer">
			  <div class="HStack" id="add_comp" onclick="add_comp()">
				  <img src="${plusPNG}"></img>
			  </div>

			  <select id="types">
				  <option>Acqua</option>
				  <option>Lievito</option>
				  <option>Additivo</option>
				  <option>Malto</option>
				  <option>Zucchero</option>
				  <option>Luppolo</option>
			  </select>
		  </div>

			<div class="buttonContainer">
				<input type="submit" id="Invia" value="Avanti" onclick="check_elem()"/>
			</div>
		</form>
	</div>

	<script>

	// aggiustamento della dimensione del form quando viene aggiunto un elemento
	let lista = document.getElementById('container');
	let container = document.querySelector('.ContainerForm');

	var calculate = 284 + (lista.childElementCount * 86);
	container.style.height = `
	`+ calculate +`px
	`;
	
	var nmIng = 0;
	
	function check_elem()
	{
		var div = document.getElementById("container");
		var nmElem = div.children.length;
		if(nmElem == 0)
			document.getElementById("Invia").disabled = true;
		else
			document.getElementById("Invia").disabled = false;
	}
	
	function add_comp() 
	{
		var div = document.getElementById("container");
		var divchild = div.lastElementChild;
		var flag = true;
		if(divchild !== null)
			{	
				
				var child = divchild.firstElementChild.lastElementChild;
				if (child.value == "")
					flag = false;
			
			}
		if (flag) {

		// aggiustamento della dimensione del form quando viene aggiunto un elemento
		let lista = document.getElementById('container');
		let container = document.querySelector('.ContainerForm');

		var calculate = 284 + (lista.childElementCount * 86);
		container.style.height = `
		`+ calculate +`px
		`;
		
		var type = document.getElementById("types").value;
		var components = new Array();
		
		switch(type)
		{
		case "Acqua":
			if(!(document.querySelector('[id^="Acqua"]') !== null))
				components.push("Acqua");
			break;
		case "Malto":
			<c:forEach var="ingredient" items="${listMalto}">
				if(!(document.querySelector("[id^='${ingredient.nome}']") !== null))        
					components.push('${ingredient.nome}');
			</c:forEach>
			break;
		case "Zucchero":
			<c:forEach var="ingredient" items="${listZucchero}">
				if(!(document.querySelector("[id^='${ingredient.nome}']") !== null))
		    		components.push('${ingredient.nome}');                              
			</c:forEach>
			break;
		case "Additivo":
			<c:forEach var="ingredient" items="${listAdditivo}">
				if(!(document.querySelector("[id^='${ingredient.nome}']") !== null))
		    		components.push('${ingredient.nome}');                              
			</c:forEach>
			break;
		case "Luppolo":
			<c:forEach var="ingredient" items="${listLuppolo}">
				if(!(document.querySelector("[id^='${ingredient.nome}']") !== null))
			    	components.push('${ingredient.nome}');                              
			</c:forEach>
			break;
		case "Lievito":
			<c:forEach var="ingredient" items="${listLievito}">
				if(!(document.querySelector("[id^='${ingredient.nome}']") !== null))
			    	components.push('${ingredient.nome}');                              
			</c:forEach>
		}
		
		if(components.length > 0)
			{
			var cont = document.createElement("div");
			cont.id="div" + nmIng;
			cont.classList = "HStack";
			
			var select = document.createElement("select");
		    select.name = "comp" + nmIng + "!" + type;
		    select.onchange=function() {	var v = this.value;
		    								var el = document.querySelector("[id^='"+v+"']");
		    								if(el !== null)	
		    									{	var pel = el.parentNode.parentNode;
		    										pel.parentNode.removeChild(pel);
		    									}
		    								this.id = this.value+this.name;	};
		    select.required = true;
		    
		    var option = document.createElement("option");
	        option.value = "";
	        option.text = "Scegli il tipo";
	        option.selected = true;
	        option.disabled = true;
	        option.hidden = true;
	        select.appendChild(option);
		 
		    for (const val of components)
		    {
		        var option = document.createElement("option");
		        option.value = val;
		        option.text = val.charAt(0).toUpperCase() + val.slice(1);
		        select.appendChild(option);
		    }
		    
		    var qa = document.createElement("input");
		    qa.name="quantita"+ nmIng;
		    qa.id="quantita"+ nmIng;
			qa.classList = "quantità";
		    
		    if(type == "Acqua")
		    	qa.setAttribute("max", "100");
		    
		    qa.setAttribute("min", "0.01");
		    qa.setAttribute("type", "number");
		    qa.setAttribute("step", ".01");
		    qa.required = true;
		    
		    var remove = document.createElement("div");
		    remove.id="button" + nmIng;
			remove.classList = "-button";
		    remove.onclick= function() {	var parent = this.parentNode.parentNode;
		    								var sel = parent.firstElementChild.lastElementChild;
		    								var valsel = sel.value;
		    								var seltype = sel.name.substring(sel.name.indexOf("!") + 1);
		    								var elems = document.querySelectorAll('[name$='+seltype+']');
		    								for(var i=0; i < elems.length; i++)
		    								{
	    										var flagval = true;
	    										for (var j=0; j < elems[i].options.length && flagval; j++) {
	    									    	if(elems[i].options[j].value == valsel)
	    									    		flagval = false;
	    									   		}
	    										if(flagval)
	    											{
		    											var option = document.createElement("option");
				    							        option.value = valsel;
				    							        option.text = valsel.charAt(0).toUpperCase() + valsel.slice(1);
				    							        elems[i].appendChild(option);
			    							        }
		    									}
		    										parent.parentNode.removeChild(parent);
		    										check_elem();

													// aggiustamento della dimensione del form quando viene aggiunto un elemento
													let lista = document.getElementById('container');
													let container = document.querySelector('.ContainerForm');

													var calculate = 284 + (lista.childElementCount * 86);
													container.style.height = `
													`+ calculate +`px
													`;
										};
		    
		    // creo 2 contenitori verticali (uno per la select, l'altro per l'input quantità)
				var vstackIng = document.createElement("div");
				vstackIng.classList = "VStack";
				vstackIng.id = "ingrediente";
				
				var vstackQuantity = document.createElement("div");
				vstackQuantity.classList = "VStack";
				vstackQuantity.id = "quantità";

				var vstackRemove = document.createElement("div");
				vstackRemove.classList = "VStack";
				vstackRemove.id = "rimuovi";

				// creo le 2 label (una per l'attrezzo e una per la quantità)
				var labelIng = document.createElement("label");
				labelIng.innerHTML = ``+type+``;
				labelIng.htmlFor = "attrezzo";

				var labelQuantity = document.createElement("label");
				labelQuantity.innerHTML = "Quantità";
				labelQuantity.htmlFor = "quantità";

				var img = document.createElement("img");
				img.src = "${minusPNG}";
				img.id = "rimuovi";
				
				// appendo tutto
				vstackIng.appendChild(labelIng);
				vstackIng.appendChild(select);

				vstackQuantity.appendChild(labelQuantity);
				vstackQuantity.appendChild(qa);

				remove.appendChild(img);
				vstackRemove.appendChild(remove);

				cont.appendChild(vstackIng);
				cont.appendChild(vstackQuantity);
				cont.appendChild(vstackRemove);
				
				document.getElementById("container").appendChild(cont);
				nmIng++;
				
				}}
		}
	
	</script>
 

	</body>
</html>