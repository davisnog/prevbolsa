<jsp:include page="../decorators/cabecalho.jsp" />

<style type="text/css">


#portfolio, #empresas { list-style-type: none; margin: 0; background: #eee; padding: 5px;}
#portfolio li, #empresas li { margin: 5px 0 5px 0; padding: 5px; }
	
</style>

<div style="width: 1000px">
<div style="width: 250px; float: left;">
	<table>
		<tr>
			<td>
				<div style="width: 250px;">
					<form id="frmAdicionarportfolio" action="<c:url value="/empresas" />" method="post">
						* <input type="text" id="empresa" name="dimempresa.simbolo" placeholder="Simbolo Empresa" style="width: 120px;" />
						<button id="addEmpresa" type="submit">Adicionar</button>
						<a href="http://br.finance.yahoo.com/actives;_ylt=AkiwnBvGWaHxB2PSu9uC2ovi.5ZG;_ylu=X3oDMTFhOTJzbDA1BHBvcwMxBHNlYwN5ZmlNYXJrZXRNb3ZlcnMEc2xrA2Rlc3RhcXVlc2RvbQ--?e=SA" target="_blank">Yahoo Finance</a>
					</form>
				</div>
			</td>
		</tr>
		
		<tr>
			<td>
				<div id="divportfolio"></div>
			</td>
		</tr>
		<tr>
			<td>
				<div id="divEmpresas" ></div>
				<a href="<c:url value="/empresas/atualizar-quotas" />">Atualizar Quotas</a>
				<br/>
				<a href="mailto:davisnog@gmail.com">Por Davi Nogueira</a>
			</td>			
		</tr>
		
		
	</table>
</div>
<div>

	<table>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<div style="width: 350px;">
					<form id="frmPesquisarportfolio" action="<c:url value="/portfolio/quotas/" />" method="post">
						<input type="hidden" id="hidSimbolos" name="simbolos" />
						* <input type="text" id="pesqportfolioInicio" name="inicio" style="width: 100px;" class="date" placeholder="Data Inicio" />
						* <input type="text" id="pesqportfolioFim" name="fim" style="width: 100px;" class="date" placeholder="Data Fim" />
						<button id="btnPesquisar" type="submit">Pesquisar</button>
					</form>	
				</div>
			</td>
			
			<td>
				<div style="width: 370px;">
					<jsp:include page="../fatoQuota/calcularPrevisao.jsp" />
				</div>
			</td>
		</tr>
		
		<tr>
			<td colspan="3"><span id="msgErro" style="display: none;">Campos em destaque s&atilde;o de preenchimento obrigat&oacute;rio</span></td>
		</tr>
		
		<tr>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td colspan="3">
				<jsp:include page="tabsGraficos.jsp" />	
			</td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td colspan="3">
				<div id="divTabs"></div>
			</td>
		</tr>

		
	</table>
</div>	
</div>
<jsp:include page="../decorators/rodape.jsp" />
<script type="text/javascript" src="${pageContext.request.contextPath}/javascripts/portfolio.js" charset="utf-8" /></script>