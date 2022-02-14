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
        <spring:url value="/resources/assets/lente.png" var="lentePNG" />
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

		<header-simple logo="${logoPNG}" plus="${plusPNG}" add=""
        ingredients="location.href='editUserIng?nick=${autore}';" 
		tools="location.href='editUserEquip?nick=${autore}';"></header-plus>


		<label>Indicare il tipo di ingrediente da aggiungere e poi premere +</label>
		<br>
		<br>
		<select name="types" id="types">
		<option value="Acqua">Acqua</option>
		<option value="Malto">Malto</option>
		<option value="Zucchero">Zucchero</option>
		<option value="Additivo">Additivo</option>
		<option value="Luppolo">Luppolo</option>
		<option value="Lievito">Lievito</option>
		</select>
		
		<button id="add_comp" onclick="add_comp()">+</button>

		<form action="editUserIng" method="POST">
				
				<div id="container"></div>
				
				<input type="hidden" id="autore" name="autore" value="${autore}"/>
				
				<input type="submit" id="Invia" value="Invia"/>
		</form>


		<script>
		
		function load_ing(tipo)
		{
			var components = new Array();
			
			switch(tipo)
			{
			case "Acqua":
				if(!(document.querySelector('[id^="Acqua"]') !== null))
					components.push("Acqua");
				break;
			case "Malto":
				<c:forEach var="ingredient" items="${listMalto}">
					if(!(document.querySelector('[id^=${ingredient.nome}]') !== null))        
						components.push('${ingredient.nome}');
				</c:forEach>
				break;
			case "Zucchero":
				<c:forEach var="ingredient" items="${listZucchero}">
					if(!(document.querySelector('[id^=${ingredient.nome}]') !== null))
						components.push('${ingredient.nome}');                              
				</c:forEach>
				break;
			case "Additivo":
				<c:forEach var="ingredient" items="${listAdditivo}">
					if(!(document.querySelector('[id^=${ingredient.nome}]') !== null))
						components.push('${ingredient.nome}');                              
				</c:forEach>
				break;
			case "Luppolo":
				<c:forEach var="ingredient" items="${listLuppolo}">
					if(!(document.querySelector('[id^=${ingredient.nome}]') !== null))
						components.push('${ingredient.nome}');                              
				</c:forEach>
				break;
			case "Lievito":
				<c:forEach var="ingredient" items="${listLievito}">
					if(!(document.querySelector('[id^=${ingredient.nome}]') !== null))
						components.push('${ingredient.nome}');                              
				</c:forEach>
			}
			
			return components;
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
			
			var type = document.getElementById("types").value;
			var components = load_ing(type);
			
			if(components.length > 0)
				{
				var cont = document.createElement("div");
				cont.id="div" + nmIng;
				
				var select = document.createElement("select");
				select.name = "comp" + nmIng + "!" + type;
				select.onchange=function() {	var v = this.value;
												var el = document.querySelector('[id^='+v+']');
												if(el !== null)	
													{	var pel = el.parentNode.parentNode;
														pel.parentNode.removeChild(pel);
													}
												this.id = this.value+this.name;	};
				select.required = true;
				
				var option = document.createElement("option");
				option.value = "";
				option.text = "Scegli";
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
			
				var labels = document.createElement("label");
				labels.innerHTML = "Scegli l'ingrediente presente nel magazzino: ";
				
				var qa = document.createElement("input");
				qa.name="quantita"+ nmIng;
				qa.id="quantita"+ nmIng;
				qa.setAttribute("min", "0.01");
				qa.setAttribute("type", "number");
				qa.setAttribute("step", ".01");
				qa.required = true;
				
				var labelq = document.createElement("label");
			
				if(type == "Acqua")
					labelq.innerHTML = "Indica in litri (l) la quantità di acqua presente: ";
				else
					labelq.innerHTML = "Indica in grammi (g) la quantità dell'ingrediente presente: ";
				labelq.htmlFor = "qa" + nmIng;
				
				var br = document.createElement("br");
				
				var remove = document.createElement("button");
				remove.innerHTML = "-";
				remove.id="button" + nmIng;
				remove.onclick= function() {	var parent = this.parentNode;
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
											};
				
				cont.appendChild(labels).appendChild(select);
				
				cont.appendChild(remove);
				
				cont.appendChild(br);
				
				cont.appendChild(labelq).appendChild(qa);
				
				document.getElementById("container").appendChild(cont);
				nmIng++;
				
				}}
		}
		
		</script>
		
		<script>
		var nmIng = 0;
		var div = document.getElementById("container");
		var existingComponents = new Array();
		<c:forEach var="excomp" items="${listUserIngredients}">
			var text = '${excomp}';
			existingComponents.push(text.split(" - "));
		</c:forEach>
		
		for(var i = 0; i < existingComponents.length; i++)
			{
				var cont = document.createElement("div");
				cont.id="div" + i;
				
				var tipo = existingComponents[i][2].charAt(0).toUpperCase() + existingComponents[i][2].slice(1);
				
				var labels = document.createElement("label");
				labels.innerHTML = "Scegli l'ingrediente presente nel magazzino: ";
				
				var select = document.createElement("select");
				select.name = "comp" + i + "!" + tipo;
				select.onchange=function() {	var v = this.value;
												var el = document.querySelector('[id^='+v+']');
												if(el !== null)	
													{	var pel = el.parentNode.parentNode;
														pel.parentNode.removeChild(pel);
													}
												this.id = this.value+this.name;	};
				select.required = true;
				
				var option = document.createElement("option");
				option.value = existingComponents[i][0];
				option.text = existingComponents[i][0].charAt(0).toUpperCase() + existingComponents[i][0].slice(1);
				option.selected = true;
				select.appendChild(option);
				
				select.id = select.value+select.name;
				
				
				var comps = load_ing(tipo);
				
				for(const el of comps)
				{
					if(existingComponents[i][0] != el)
						{
							var option = document.createElement("option");
							option.value = el;
							option.text = el.charAt(0).toUpperCase() + el.slice(1);
							select.appendChild(option);
						}
				}
				
				var labelq = document.createElement("label");
				
				if(tipo == "Acqua")
					labelq.innerHTML = "Indica in litri (l) la quantità di acqua presente: ";
				else
					labelq.innerHTML = "Indica in grammi (g) la quantità dell'ingrediente presente: ";
				labelq.htmlFor = "qa" + i;
				
				var qa = document.createElement("input");
				qa.name="quantita"+ i;
				qa.id="quantita"+ i;
				qa.value = Number(existingComponents[i][1]);
				qa.setAttribute("min", "0.01");
				qa.setAttribute("type", "number");
				qa.setAttribute("step", ".01");
				qa.required = true;
				
				var br = document.createElement("br");
				
				var remove = document.createElement("button");
				remove.innerHTML = "-";
				remove.id="button" + i;
				remove.onclick= function() {	var parent = this.parentNode;
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
														elems[i].appendChild(option);}
													}
														parent.parentNode.removeChild(parent);
											};
				
				cont.appendChild(labels).appendChild(select);
				
				cont.appendChild(remove);
				
				cont.appendChild(br);
				
				cont.appendChild(labelq).appendChild(qa);
				
				document.getElementById("container").appendChild(cont);
				nmIng++;
				}
		
		</script>

	</body>
</html>