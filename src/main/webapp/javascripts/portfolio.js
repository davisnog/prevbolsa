$(function() {
	carregarportfolio();
	pesquisarportfolio();
	calcularProjecao();

	$("#radio").buttonset();
	
	var options = {
		success : carregarportfolio,
		dataType : 'json',
		clearForm : true,
		beforeSubmit : function(arr, form, options) {
			var enviar = validar("#empresa");
			return enviar;
		},
		statusCode: {
		    404: function() {
		      alert('Empresa com o simbolo ' + $("#empresa").val() + ' não foi encontrada');
		    }
		}
	};

	$('#frmAdicionarportfolio').ajaxForm(options);

	$("#addEmpresa").button({
		icons : {
			primary : "ui-icon-plus"
		}
	});

	$("#btnPesquisar").button({
		icons : {
			primary : "ui-icon-search"
		}
	});

	$("#btnCalcPrevisao").button({
		icons : {
			primary : "ui-icon-check"
		}
	});

});

function calcularProjecao() {
	var options = {
		success : function(response, status, xhr) {
			if (status == 'success') {
				$("#divTabs").empty();
				$("#divTabs").append(response);
				$("#tabs").tabs();
			}
		}
	};
	
	$("#frmCalcularPrevisao").submit(function(){
		$("#hidDtInicio").val($("#pesqportfolioInicio").val());
		$("#hidDtFim").val($("#pesqportfolioFim").val());
		var enviar = validar("#meses");
		
		if(enviar){
			$(this).ajaxSubmit(options);
		}		
		return false;
	});
}

function pesquisarportfolio() {
	$('#frmPesquisarportfolio').ajaxForm({
		success : graficosPorfifolio,
		beforeSubmit : function(arr, form, options) {
			$('#grafico-linha').empty(); $('#grafico-dispersao').empty();
			 
			var inicio = validar("#pesqportfolioInicio");
			var fim = validar("#pesqportfolioFim");
			return inicio && fim;
		}

	});
}

function graficosPorfifolio(response) {
	var dados = [ [] ];
	
	$(response.list).each(function(idx, arr) {
		var serie = {
			name : arr[idx].simbolo,
			data : []
		};
		
		$(arr).each(function(i, val) {
			serie.data.push([ new Date(val.lancadoEm).getTime(), val.fechamento ]);
		});

		dados[idx] = serie;
	});

	criarGraficos('line', 'grafico-linha', dados);
	criarGraficos('scatter', 'grafico-dispersao', dados);

	$("#tabsGraficos").show();
	$("#tabsGraficos").tabs();
}

function criarGraficos(tipo, div, dados){
	var titulo = 'Ações na Bolsa de Valores/Bovespa';
	var subTitulo = 'no período de ' + $("#pesqportfolioInicio").val() + ' a '+ $("#pesqportfolioFim").val();
	
	new Highcharts.Chart({
		chart : {
			renderTo : div,
			zoomType : 'x',
			spacingRight : 20,
			defaultSeriesType: tipo
		},
		title : {
			text : titulo,
			x : -20
		},
		subtitle : {
			text : subTitulo,
			x : -20
		},
		tooltip : {
			shared : true,
			crosshairs : true
		},
		xAxis : {
			type : 'datetime'
		},
		yAxis : {
			title : {
				text : 'Valor das Ações no período'
			},
			min : 0,
			showFirstLabel : false
		},
		exporting : {
			enabled : false
		},
		series : dados
	});
}

function carregarportfolio() {
	var empresas = context + "/portfolio/listar-empresas/";
	var portfolio = context + "/portfolio/listar-portfolio/";

	$('#divEmpresas').load(empresas, function(response, status, xhr) {
		if (status == 'success') {
			listaSortable();
		}
	});

	$('#divportfolio').load(portfolio, function(response, status, xhr) {
		if (status == 'success') {
			listaSortable();
		}
	});

}

function listaSortable() {
	if ($("#portfolio, #empresas").find('li').length == 0) {
		$("#portfolio, #empresas").css('padding-bottom', '50px');
	}
	
	$("#portfolio, #empresas").sortable({
		connectWith : ".connectedSortable",
		dropOnEmpty : true,
		update : function(event, ui) {
			var move = $(this).attr('id');
			
			if (move == 'empresas') {
				var url = context + '/portfolio/remover-portfolio/' + $(this).sortable('toArray').toString();
				
				$.post(url, function(response, status, xhr) {
					if (status == 'success') {
						listaSortable();
					}
				});
				
			}else if (move == 'portfolio') {
				var url = context + '/portfolio/adicionar-portfolio/' + $(this).sortable('toArray').toString();
				
				$.post(url, function(response, status, xhr) {
					if (status == 'success') {
						listaSortable();
					}
				});
				
			} 
		},

		remove : function(event, ui) {
			if ($(this).find('li').length == 0) {
				$(this).css('padding-bottom', '50px');
			} else {
				$(this).css('padding-bottom', '0');
			}
		}

	}).disableSelection();

}

function validar(input){
	var obj = $(input);
	if (obj.val() == '') {
		obj.css('border-color', 'red');
		$("#msgErro").show();
		return false;
	}else{
		obj.css('border-color', '');
		$("#msgErro").hide();
		return true;
	}
}