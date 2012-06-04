$(function(){
	configBlockUI();
	$("form").form();
	$('body').addClass('ui-widget').css('font-size', '0.9em');
	$('input, textarea').placeholder();
});

function configBlockUI(){
	var src = context+"/images/ajax-loader.gif";
	
	function config(){
		var block = $.blockUI({
			message: '<h3><img src='+ src +' width="20px" heigth="20px" /> Processando...</h3>',
			css: { 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#000', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px',
	            'border-radius' : '10px',
	            opacity: .5, 
	            color: '#fff'
	    } });
		return block;
	}
	
	$(document).ajaxStart(config).ajaxStop($.unblockUI);
}