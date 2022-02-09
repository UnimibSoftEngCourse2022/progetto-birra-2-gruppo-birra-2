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
	
	<button id="add" onclick="add_equip()">Aggiungi nuovo attrezzo</button>
	<br>
	<form action="addrecequips" method="POST">
			
			<div id="container"></div>
			
			<input type="hidden" id="ricetta" name="ricetta" value="${ricettaID}"/>
			
			<input type="submit" id="Invia" value="Invia" onclick="check_elem()"/>
	</form>
	
	<script>
	
	var nmEqp = 0;
	
	function check_elem()
	{
		var div = document.getElementById("container");
		var nmElem = div.children.length;
		if(nmElem == 0)
			document.getElementById("Invia").disabled = true;
		else
			document.getElementById("Invia").disabled = false;
	}
	
	
	function add_equip() 
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
		
		var equipments = new Array();
		<c:forEach var="equip" items="${listAttrezzi}">
			var valore = ${equip.ID};
			if(!(document.querySelector('[id^=' + CSS.escape(valore) + ']') !== null))    
				equipments.push(new Array('${equip.ID}','${equip.nome}'));
		</c:forEach>
		
		if(equipments.length > 0)
			{
			var cont = document.createElement("div");
			cont.id="div" + nmEqp;
			
			var select = document.createElement("select");
		    select.name = "eqp" + nmEqp;
		    select.onchange=function() {	var v = this.value;
		    								var el = document.querySelector('[id^=' + CSS.escape(v) +']');
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
		 
		    for (const val of equipments)
		    {
		        var option = document.createElement("option");
		        option.value = val[0];
		        option.text = val[1].charAt(0).toUpperCase() + val[1].slice(1);
		        select.appendChild(option);
		    }
		 
		    var labels = document.createElement("label");
		    labels.innerHTML = "Scegli l'attrezzo presente: ";
		    
		    var qa = document.createElement("input");
		    qa.name="quantita"+ nmEqp;
		    qa.id="quantita"+ nmEqp;
		    qa.setAttribute("min", "1");
		    qa.setAttribute("type", "number");
		    qa.required = true;
		    
		    var labelq = document.createElement("label");
		    labelq.innerHTML = "Indica il numero di attrezzi di questo tipo che sono necessari: ";
		    labelq.htmlFor = "qa" + nmEqp;
		    
		    var br = document.createElement("br");
		    
		    var remove = document.createElement("button");
		    remove.innerHTML = "-";
		    remove.id="button" + nmEqp;
		    remove.onclick= function() {	var parent = this.parentNode;
		    								var sel = parent.firstElementChild.lastElementChild;
		    								var valsel = sel.value;
		    								var textsel = sel.options[sel.selectedIndex].text;
		    								var elems = document.querySelectorAll('[name^="eqp"]');
		    								for(var i=0; i < elems.length; i++)
		    									{
			    									var option = document.createElement("option");
			    							        option.value = valsel;
			    							        option.text = textsel.charAt(0).toUpperCase() + textsel.slice(1);
			    							        elems[i].appendChild(option);
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
		    nmEqp++;
			}}
	}
	
	</script>
 

	</body>
</html>