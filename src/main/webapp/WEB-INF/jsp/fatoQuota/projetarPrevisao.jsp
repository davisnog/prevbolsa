<div id="tabs">
<ul >
	<c:forEach items="${projecoes}" var="dto">
		<li><a href="#${dto.key}">${dto.key}</a></li>
	</c:forEach>
</ul>
	<c:forEach items="${projecoes}" var="dto">
		<div id="${dto.key}">
			<table>
				<c:forEach items="${dto.value}" var="projecao" varStatus="status">
					<c:if test="${status.count eq 1}">
					<tr style="font-weight: bold;">
						<td>
							Coeficiente de Determina&ccedil;&atilde;o: 
						</td>
						<td>
							<fmt:formatNumber type="percent" maxIntegerDigits="2" value="${projecao.coeficienteDeterminacao}" />
						</td>
						
					</tr>
					<tr style="font-weight: bold;">
						<td>
							Linha Regress&atilde;o: 
						</td>
						<td colspan="4">
							${projecao.linhaRegressao}
						</td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;&nbsp;</td>
					</tr>
					</c:if>
					<tr>
						<td>
							<fmt:formatDate value="${projecao.dataPrevista}" pattern="dd/MM/yyyy"/>
						</td>
						<td <c:if test="${projecao.valorPrevisto < 0}"> style="color: red;" </c:if>>
							<fmt:formatNumber type="currency" value="${projecao.valorPrevisto}" />
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</c:forEach>
</div>