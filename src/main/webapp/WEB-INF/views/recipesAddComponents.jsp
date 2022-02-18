<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>BrewDay!</title>
	</head>
	<body>
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

	<form action="addcomponents" method="POST">
			
			<div id="container"></div>
			
			<input type="hidden" id="ricetta" name="ricetta" value="${ricettaID}"/>
			
			<input type="submit" id="Invia" value="Invia" onclick="check_elem()"/>
	</form>

	<script>
	
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
		    labels.innerHTML = "Scegli l'ingrediente presente: ";
		    
		    var qa = document.createElement("input");
		    qa.name="quantita"+ nmIng;
		    qa.id="quantita"+ nmIng;
		    
		    if(type == "Acqua")
		    	qa.setAttribute("max", "100");
		    
		    qa.setAttribute("min", "0.01");
		    qa.setAttribute("type", "number");
		    qa.setAttribute("step", ".01");
		    qa.required = true;
		    
		    var labelq = document.createElement("label");
		   
		    if(type == "Acqua")
		    	labelq.innerHTML = "Indica in percentuale la quantità di acqua presente: ";
		    else
		    	labelq.innerHTML = "Indica la quantità assoluta (g/l) dell'ingrediente presente: ";
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
		    										check_elem();
										};
		    
		    cont.appendChild(labels).appendChild(select);
		    
		    cont.appendChild(remove);
		    
		    cont.appendChild(br);
			
		    cont.appendChild(labelq).appendChild(qa);
		    
		    document.getElementById("container").appendChild(cont);
		    check_elem();
		    nmIng++;}}
	}
	
	</script>
 

	</body>
</html>