	function disableAchats() {
		document.getElementById("1").disabled = true;
		document.getElementById("1").checked = false;
		document.getElementById("2").disabled = true;
		document.getElementById("2").checked = false;
		document.getElementById("3").disabled = true;
		document.getElementById("3").checked = false;
		document.getElementById("4").disabled = false;
		document.getElementById("4").checked = true;
		document.getElementById("5").disabled = false;
		document.getElementById("5").checked = true;
		document.getElementById("6").disabled = false;
		document.getElementById("6").checked = true;
}
function disableVentes() {
		document.getElementById("1").disabled = false;
		document.getElementById("1").checked = true;
		document.getElementById("2").disabled = false;
		document.getElementById("2").checked = true;
		document.getElementById("3").disabled = false;
		document.getElementById("3").checked = true;
		document.getElementById("4").disabled = true;
		document.getElementById("4").checked = false;
		document.getElementById("5").disabled = true;
		document.getElementById("5").checked = false;
		document.getElementById("6").disabled = true;
		document.getElementById("6").checked = false;
}
function disableOnLoad() {
		if(document.getElementById("achats").checked === false){
			document.getElementById("1").disabled = true;
			document.getElementById("2").disabled = true;
			document.getElementById("3").disabled = true;
		}
		if(document.getElementById("mesVentes").checked === false){
			document.getElementById("4").disabled = true;
			document.getElementById("5").disabled = true;
			document.getElementById("6").disabled = true;
		}	
}