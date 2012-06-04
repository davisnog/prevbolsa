<div >
	<fieldset class="ui-widget-content">
		<legend class="ui-widget-header ui-corner-all" style="width: 100%; font-size: 1.2em;">Empresas</legend>
		
		<ul id="empresas" class="connectedSortable">
				<c:forEach items="${dimEmpresaList}" var="empresa">
					<li id="${empresa.simbolo}" class="ui-state-highlight">${empresa}</li>
				</c:forEach>	
		</ul>
	
	</fieldset>
</div>