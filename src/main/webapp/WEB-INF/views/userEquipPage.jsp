<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<spring:url value="/resources/assets/-.png" var="minusPNG" />
		<spring:url value="/resources/assets/lente.png" var="lentePNG" />
		<spring:url value="/resources/assets/logo.png" var="logoPNG" />
		<link href="${logoPNG}" rel="icon" />

		<spring:url value="/resources/style.css" var="styleCSS" />
		<spring:url value="/resources/components/header.js" var="headerJS" />
		<spring:url value="/resources/login/header.css" var="headerloginCSS" />
		<spring:url value="/resources/recipes/header.css" var="headerrecipesCSS" />
		<spring:url value="/resources/recipes/recipes.css" var="recipesCSS" />
		<spring:url value="/resources/tools/tools.css" var="toolsCSS" />

		<link href="${styleCSS}" rel="stylesheet" />
		<script src="${headerJS}"></script>
		<link href="${headerloginCSS}" rel="stylesheet" />
		<link href="${headerrecipesCSS}" rel="stylesheet" />
		<link href="${recipesCSS}" rel="stylesheet" />
		<link href="${toolsCSS}" rel="stylesheet" />
	</head>
	<body>

		<header-simple logo="${logoPNG}" plus="${plusPNG}" add=""
		ingredients="location.href='editUserIng?nick=${autore}';" 
		tools="location.href='editUserEquip?nick=${autore}';"></header-simple>

		
		<div class="ContainerForm">
            <h1>La mia attrezzatura</h1>
            <form action="editUserTools" method="POST">
                
                <div id="container"></div>
				
				<input type="hidden" id="autore" name="autore" value="${autore}"/>
				
				
				<div class="HStack" id="add" onclick="add_equip()">
					<img alt="piub" src="${plusPNG}"></img>
					<p>Aggiungi</p>
				</div>
				
				<div class="buttonContainer">
                	<input type="submit" id="Invia" value="Salva"/>
				</div>
            </form>
        </div>

		
	<script>

		let lista = document.getElementById('container');
		let container = document.querySelector('.ContainerForm');

		var calculate = 284 + (lista.childElementCount * 86);
		container.style.height = `
		`+ calculate +`px
		`;
	
		function add_equip() {

			// Vede se nel container c'è l'ultimo elemento e controlla se l'utente ha selezionato l'attrezzo
			// se si il flag rimane true (puoi aggiungere altrimenti) diventa false
			var div = document.getElementById("container");
			var divchild = div.lastElementChild;
			var flag = true;

			if(divchild !== null) {	
				var child = divchild.firstElementChild.lastElementChild;
				if (child.value == "")
					flag = false;
			}

			if(flag) {

				// aggiustamento della dimensione del form quando viene aggiunto un elemento
				let lista = document.getElementById('container');
				let container = document.querySelector('.ContainerForm');

				var calculate = 284 + (lista.childElementCount * 86);
				container.style.height = `
				`+ calculate +`px
				`;

				// crea un array di attrezzi e lo mette in equipments
				var equipments = new Array();
				<c:forEach var="equip" items="${listAttrezzi}">
					var valore = ${equip.ID};
					if(!(document.querySelector('[id^=' + CSS.escape(valore) + ']') !== null))    
						equipments.push(new Array('${equip.ID}','${equip.nome}', '${equip.capacitaMax}'));
				</c:forEach>
				
				// se c'è più di un attrezzo nel database (quelli possibili)
				if(equipments.length > 0) {
					
					// DIV
					var cont = document.createElement("div");
					cont.id = "div" + nmEqp;
					cont.classList = "HStack";
					
					// SELECT
					var select = document.createElement("select");
					select.name = "eqp" + nmEqp;
					
					// se l'utente seleziona una roba già aggiunta cancella quella riga
					select.onchange = function() {
						var v = this.value;
						var el = document.querySelector('[id^=' + CSS.escape(v) +']');
						
						if(el !== null)	{	
							var pel = el.parentNode.parentNode;
							pel.parentNode.removeChild(pel);
						}
						this.id = this.value+this.name;	
					};
					select.required = true;
					
					// 1° OPZIONE
					var option = document.createElement("option");
					option.value = "";
					option.text = "Scegli";
					option.selected = true;
					option.disabled = true;
					option.hidden = true;
					select.appendChild(option);
				
					// OPZIONI
					for (const val of equipments) {
						var option = document.createElement("option");
						option.value = val[0];
						option.text = val[1].charAt(0).toUpperCase() + val[1].slice(1) + " da " + val[2] + " L";
						select.appendChild(option);
					}
					
					// INPUT QUANTITÀ
					var qa = document.createElement("input");
					qa.name = "quantita" + nmEqp;
					qa.id = "quantita" + nmEqp;
					qa.classList = "quantità";
					qa.setAttribute("min", "1");
					qa.setAttribute("type", "number");
					qa.required = true;
					
					// RIMUOVI 
					var remove = document.createElement("div");
					remove.id="button" + nmEqp;
					remove.classList = "-button";
					remove.onclick = function() {	
						var parent = this.parentNode.parentNode;
						var sel = parent.firstElementChild.lastElementChild;
						var valsel = sel.value;
						var textsel = sel.options[sel.selectedIndex].text;
						var elems = document.querySelectorAll('[name^="eqp"]');
						
						for(var i=0; i < elems.length; i++) {
							var flagval = true;
							for (var j=0; j < elems[i].options.length && flagval; j++) {
								if(elems[i].options[j].value == valsel)
									flagval = false;
							}
							if(flagval) {
								var option = document.createElement("option");
								option.value = valsel;
								option.text = textsel.charAt(0).toUpperCase() + textsel.slice(1);
								elems[i].appendChild(option);
							}
								
						}
						parent.parentNode.removeChild(parent);

						// aggiustamento della dimensione del form quando viene aggiunto un elemento
						let lista = document.getElementById('container');
						let container = document.querySelector('.ContainerForm');

						var calculate = 284 + (lista.childElementCount * 86);
						container.style.height = `
						`+ calculate +`px
						`;
					};
					
					// creo 2 contenitori verticali (uno per la select, l'altro per l'input quantità)
					var vstackTool = document.createElement("div");
					vstackTool.classList = "VStack";
					vstackTool.id = "attrezzo";
					
					var vstackQuantity = document.createElement("div");
					vstackQuantity.classList = "VStack";
					vstackQuantity.id = "quantità";

					var vstackRemove = document.createElement("div");
					vstackRemove.classList = "VStack";
					vstackRemove.id = "rimuovi";

					
					// creo le 2 label (una per l'attrezzo e una per la quantità)
					var labelTool = document.createElement("label");
					labelTool.innerHTML = "Nome";
					labelTool.htmlFor = "attrezzo";

					var labelQuantity = document.createElement("label");
					labelQuantity.innerHTML = "Quantità";
					labelQuantity.htmlFor = "quantità";

					var img = document.createElement("img");
					img.src = "${minusPNG}";
					img.id = "rimuovi";
					
					// appendo tutto
					vstackTool.appendChild(labelTool);
					vstackTool.appendChild(select);

					vstackQuantity.appendChild(labelQuantity);
					vstackQuantity.appendChild(qa);

					remove.appendChild(img);
					vstackRemove.appendChild(remove);

					cont.appendChild(vstackTool);
					cont.appendChild(vstackQuantity);
					cont.appendChild(vstackRemove);
					
					// appende la riga al container e incrementa l'id per il prossimo elemento
					document.getElementById("container").appendChild(cont);
					nmEqp++;
				}
			}
		}
	
	</script>
 
	 <script>
	 	var nmEqp = 0;
	 	var div = document.getElementById("container");
	 	var existingTools = new Array();
	 	<c:forEach var="extool" items="${listUserTools}">
		 	var text = '${extool}';
	 		existingTools.push(text.split(" - "));
		</c:forEach>

		for(var i = 0; i < existingTools.length; i++) {
			// DIV
			var cont = document.createElement("div");
			cont.id="div" + i;
			cont.classList = "HStack";
			
			// SELECT
			var select = document.createElement("select");
			select.name = "eqp" + i;
			select.onchange=function() {	
				var v = this.value;
				var el = document.querySelector('[id^=' + CSS.escape(v) +']');
				if(el !== null)	{	
					var pel = el.parentNode.parentNode;
					pel.parentNode.removeChild(pel);
				}
				this.id = this.value + this.name;	
			};
			select.required = true;
			
			var option = document.createElement("option");
			option.value = existingTools[i][0];
			option.text = existingTools[i][1].charAt(0).toUpperCase() + existingTools[i][1].slice(1) + " da " + existingTools[i][3] + " L";
			option.selected = true;
			select.appendChild(option);
			
			select.id = select.value+select.name;
			
			var tol = new Array();
			
			<c:forEach var="equip" items="${listAttrezzi}">
				var valore = ${equip.ID};
				if(!(document.querySelector('[id^=' + CSS.escape(valore) + ']') !== null))    
					tol.push(new Array('${equip.ID}','${equip.nome}', '${equip.capacitaMax}'));
			</c:forEach>
			
			for(const val of tol) {
				if(val[0] != existingTools[i][0])
					{var option = document.createElement("option");
					option.value = val[0];
					option.text = val[1].charAt(0).toUpperCase() + val[1].slice(1) + " da " + val[2] + " L";
					select.appendChild(option);}
			}

			// INPUT	
			var qa = document.createElement("input");
			qa.name="quantita"+ i;
			qa.id="quantita"+ i;
			qa.setAttribute("min", "1");
			qa.setAttribute("type", "number");
			qa.required = true;
			qa.classList = "quantità";
			qa.value = Number(existingTools[i][2]);
			    
			// RIMUOVI
			var remove = document.createElement("div");
			remove.classList = "-button";
			remove.id = "button" + i;
			remove.onclick= function() {	
				var parent = this.parentNode.parentNode;
				var sel = parent.firstElementChild.lastElementChild;
				var valsel = sel.value;
				var textsel = sel.options[sel.selectedIndex].text;
				var elems = document.querySelectorAll('[name^="eqp"]');
				for(var i=0; i < elems.length; i++) {
					var flagval = true;
					for (var j=0; j < elems[i].options.length && flagval; j++) {
						if(elems[i].options[j].value == valsel)
							flagval = false;
					}
					if(flagval) {
						var option = document.createElement("option");
						option.value = valsel;
						option.text = textsel.charAt(0).toUpperCase() + textsel.slice(1);
						elems[i].appendChild(option);
					}
				}
				parent.parentNode.removeChild(parent);

				// aggiustamento della dimensione del form quando viene aggiunto un elemento
				let lista = document.getElementById('container');
				let container = document.querySelector('.ContainerForm');

				var calculate = 284 + (lista.childElementCount * 86);
				container.style.height = `
				`+ calculate +`px
				`;
			};
			    
			// creo 2 contenitori verticali (uno per la select, l'altro per l'input quantità)
			var vstackTool = document.createElement("div");
			vstackTool.classList = "VStack";
			vstackTool.id = "attrezzo";
			
			var vstackQuantity = document.createElement("div");
			vstackQuantity.classList = "VStack";
			vstackQuantity.id = "quantità";

			var vstackRemove = document.createElement("div");
			vstackRemove.classList = "VStack";
			vstackRemove.id = "rimuovi";

			
			// creo le 2 label (una per l'attrezzo e una per la quantità)
			var labelTool = document.createElement("label");
			labelTool.innerHTML = "Nome";
			labelTool.htmlFor = "attrezzo";

			var labelQuantity = document.createElement("label");
			labelQuantity.innerHTML = "Quantità";
			labelQuantity.htmlFor = "quantità";

			var img = document.createElement("img");
			img.src = "${minusPNG}";
			img.id = "rimuovi";
			
			// appendo tutto
			vstackTool.appendChild(labelTool);
			vstackTool.appendChild(select);

			vstackQuantity.appendChild(labelQuantity);
			vstackQuantity.appendChild(qa);

			remove.appendChild(img);
			vstackRemove.appendChild(remove);

			cont.appendChild(vstackTool);
			cont.appendChild(vstackQuantity);
			cont.appendChild(vstackRemove);
			
			document.getElementById("container").appendChild(cont);
			nmEqp++;}

	 	</script>

	</body>
</html>