$(function() {
	$("#btnLogin").button({
		icons : {
			primary : "ui-icon-locked"
		}
	});

	$("#btnLogin").click(function() {
		var valido = valida("#login");
		valido = valida("#senha");
		return valido;
	});
	
	$("#btnNovoUsuario").button({
		icons : {
			primary : "ui-icon-arrowreturnthick-1-e"
		}
	});
	
	$("#btnNovoUsuario").click(function(){
		var valido = valida("#nome");
		valido = valida("#login");
		valido = valida("#senha");
		return valido;
	});
});

function valida(input) {
	if ($(input).val() == '') {
		$(input).css('border-color', 'red');
		$("#msgErro").show();
		return false;
	} else {
		$(input).css('border-color', '');
		$("#msgErro").hide();
		return true;
	}
}