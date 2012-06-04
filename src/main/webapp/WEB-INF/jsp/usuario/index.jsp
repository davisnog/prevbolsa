<jsp:include page="../decorators/cabecalho.jsp" />
<style type="text/css">
	fieldset {
		width: 400px;
	}
	
	fieldset legend {
		width: 100%;
	}
	
	fieldset .field {
		margin: 0.5em;
		padding: 0.5em;
	}
</style>

	<center>
		<div style="margin-top: 100px; margin-bottom: 100px;">
			<form action="<c:url value="/usuarios/valida-login/" />" method="post">
				<fieldset>
					<legend>Controle de Acesso</legend>
					<div id="msgErro" style="display: none;">Campos em destaque s&atilde;o de preenchimento obrigat&oacute;rio</div>
					
					<c:if test="${not empty msgUsuarioSenha}">
						<div style="color: red;">${msgUsuarioSenha}</div>
					</c:if>
					
					<input type="text" id="login" name="usuario.login" placeholder="Informe seu Login" class="form_text_desc field" /> <br />
					<input type="password" id="senha" name="usuario.senha" placeholder="Informe sua Senha" class="form_text_desc field" style="height: 25px;" /> <br />
					<button id="btnLogin" type="submit">Entrar</button>
					
				</fieldset>
			</form>
			<br /> <a href="<c:url value="/usuarios/novo/" />">Novo Usu&aacuterio</a>
			<br/> <a href="mailto:davisnog@gmail.com">Desenvolvido por Davi Nogueira</a>
		</div>
	</center>
	
<jsp:include page="../decorators/rodape.jsp" />

<script type="text/javascript" src="${pageContext.request.contextPath}/javascripts/login.js" charset="utf-8" /></script>