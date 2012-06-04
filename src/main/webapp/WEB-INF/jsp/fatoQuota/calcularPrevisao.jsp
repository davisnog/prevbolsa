<form id="frmCalcularPrevisao"action="<c:url value="/fato-quota/projetar-previsao/" />" method="post">
	
	<table>
		<tr>
			<td>
				<input type="hidden" name="dtInicial" id="hidDtInicio" />
				<input type="hidden" name="dtFinal" id="hidDtFim" />
				* <input type="text" id="meses" name="dias" placeholder="Projetar previs&acirc;o" style="width: 120px;" /></td>
			<td>
				<div id="radio">
					<input type="radio" id="emDias" name="radio" checked="checked" />
					<label for="emDias">em dias</label>
				</div>
			</td>
			<td>
				<button id="btnCalcPrevisao" type="submit">Calcular</button>
				<a href="<c:url value="/usuarios/sair/" />" style="margin-left: 5px;">Sair</a>
			</td>
		</tr>

	</table>
</form>