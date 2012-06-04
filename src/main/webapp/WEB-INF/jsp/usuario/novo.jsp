<jsp:include page="../decorators/cabecalho.jsp" />
<style type="text/css">
	fieldset {
		width: 400px;
	}
	
	fieldset legend {
		width: 100%;
	}
	
	fieldset legend div {
		margin: 0.3em 0.5em;
	}
	
	fieldset .field {
		margin: 0.5em;
		padding: 0.5em;
	}
</style>

	<center>
		<div style="margin-top: 100px; margin-bottom: 100px;">
			<form action="<c:url value="/usuarios" />" method="post">
				<fieldset>
					<legend>Novo Usu&aacuterio</legend>
					<span id="msgErro" style="display: none;">Campos em destaque s&atilde;o de preenchimento obrigat&oacute;rio</span>
					
					<c:if test="${not empty msgUsuarioExistente}">
						<div style="color: red;">${msgUsuarioExistente}</div>
					</c:if>
					
					 <input type="text" id="nome" name="usuario.nome" placeholder="Informe seu Nome" class="form_text_desc field" /> * 
					 <input type="text" id="login" name="usuario.login" placeholder="Informe seu Login" class="form_text_desc field" /> * 
					 <input type="password" id="senha" name="usuario.senha" placeholder="Informe sua Senha" class="form_text_desc field" style="height: 25px;" /> *
					
					<button id="btnNovoUsuario" type="submit">Salvar</button>
					
				</fieldset>
			</form>
			<br /> <a href="<c:url value="/" />">Voltar</a>
		</div>
	</center>
	
<jsp:include page="../decorators/rodape.jsp" />
<script type="text/javascript" src="${pageContext.request.contextPath}/javascripts/login.js" charset="utf-8" /></script>