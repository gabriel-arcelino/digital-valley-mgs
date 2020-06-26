<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="n2s">
<link rel="icon" href="favicon.ico">
<title>Darwin - Sistema de Gerenciamento de Seleções</title>


<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
	crossorigin="anonymous">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/design.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.css" />
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.standalone.css" />
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
	integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
	crossorigin="anonymous">

</head>
<body>
	<c:import url="elements/menu-superior.jsp" charEncoding="UTF-8"></c:import>
	<div class="container-fluid">
		<div class="row row-offcanvas row-offcanvas-right">
			<c:import url="elements/menu-lateral-esquerdo.jsp"
				charEncoding="UTF-8"></c:import>
			<div class="col-sm-8">
				<nav class="breadcrumb"> <span class="breadcrumb-item">Você
					está em:</span> <a class="breadcrumb-item"
					href="${pageContext.request.contextPath}/">Início</a> <a
					class="breadcrumb-item"
					href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}">${selecao.titulo}</a>
				<a class="breadcrumb-item active" href="#">Resultado</a> </nav>
				<c:if test="${not empty mensagem}">
					<div class="alert alert-${status} alert-dismissible fade show"
						role="alert">
						${mensagem}
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>
				<h1>Resultado da Seleção</h1>
				<br>
				<c:if test="${(isResponsavel)}">
					<div class="form-check" style="float: right;"
						title="Ao selecionar este campo, os participantes poderão visualizar suas notas.">
						<form method="POST" id="showNote"
							action="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}/resultado">
							<input class="form-check-input" type="checkbox"
								name="exibirNotas" onclick="atualizaExibirNotas('showNote')"
								value="" id="defaultCheck1"
								${(selecao.exibirNotas) ? 'checked': ''}> Exibir notas
							ao divulgar o resultado
						</form>
					</div>
				</c:if>
				<table class="table table-responsive">
					<thead>
						<tr>
							<th scope="col">Colocação</th>
							<th scope="col">Candidato</th>

							<c:if test="${selecao.exibirNotas || (isResponsavel)}">
								<c:forEach var="etapa" items="${etapasComNota}">
									<th scope="col">${etapa.titulo} - ${etapa.pesoNota}</th>
								</c:forEach>
								<th scope="col">Média Geral</th>
							</c:if>
							<th scope="col">Situação</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="resultado" items="${resultadosSelecao}">
							<tr>
								<c:if test="${(resultado.colocacao == 0)}">
									<td>-</td>
								</c:if>
								<c:if test="${(resultado.colocacao != 0)}">
									<td>${resultado.colocacao}</td>
								</c:if>
								<td>${resultado.participante.candidato.nome}</td>
								<c:if test="${selecao.exibirNotas || (isResponsavel)}">
									<c:forEach var="nota" items="${resultado.notasEtapas}">
										<th class="text-center" scope="col">${nota}</th>
									</c:forEach>
									<c:if test="${not empty resultado.notasEtapas}">
										<td>${resultado.mediaGeral}</td>
									</c:if>
									<c:if test="${empty resultado.notasEtapas}">
										<td>-</td>
									</c:if>
								</c:if>
								<c:if
									test="${(resultado.aprovado) and ((resultado.colocacao) <= (selecao.vagasRemuneradas + selecao.vagasVoluntarias))}">
									<td>CLASSIFICADO</td>
								</c:if>
								<c:if
									test="${(resultado.aprovado) and ((resultado.colocacao) > (selecao.vagasRemuneradas + selecao.vagasVoluntarias))}">
									<td>CLASSIFICÁVEL</td>
								</c:if>
								<c:if test="${(not resultado.aprovado)}">
									<td>DESCLASSIFICADO</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br>
				<c:set var="estado" value="${selecao.estado.estado}"></c:set>
				<c:if test="${estado == 4}">
					<c:if
						test="${(not selecao.divulgadoResultado) and ((fn:contains(permissoes, 'ADMINISTRADOR') or (isResponsavel)))}">
						<a
							href="${pageContext.request.contextPath}/resultadoSelecao/${selecao.codSelecao}/divulgaResultado"
							class="btn btn-primary btn-sm active"
							class="btn btn-primary btn-sm" style="height: 30px;"
							data-toggle="modal" data-target="#divulgaresultados"> <i
							class="fas fa-bullhorn"></i> Divulgar Resultado
						</a>
					</c:if>
					<c:if
						test="${(selecao.divulgadoResultado) and ((fn:contains(permissoes, 'ADMINISTRADOR') or (isResponsavel)))}">
						<button disabled class="btn btn-secondary btn-sm" role="button"
							aria-pressed="true">
							<i class="fas fa-user-check"></i> Divulgado
						</button>
					</c:if>
					<a
						href="${pageContext.request.contextPath}/resultadoSelecao/${selecao.codSelecao}/imprimir"
						class="btn btn-primary btn-sm active"
						class="btn btn-primary btn-sm" style="height: 30px;"> <i
						class="fas fa-file-pdf"></i> Gerar PDF
					</a>
					<!-- divulgação de resultados -->
					<div class="modal fade" id="divulgaresultados" tabindex="-1"
						role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="modalLabel">Divulgar
										resultados</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<p>Ao divulgar o resultado da seleção, todos poderão ver.
										Deseja continuar?</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary btn-sm"
										data-dismiss="modal">Cancelar</button>
									<a class="btn btn-sm btn-primary"
										href="${pageContext.request.contextPath}/resultadoSelecao/${selecao.codSelecao}/divulgaResultado">
										Continuar</a>
								</div>
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${estado != 4}">
					<div class="text-center">
						<p>Os resultados aparecerão após a finalização da última
							etapa</p>
						<a
							href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}"
							id="enviar" class="btn btn-secondary"> <i
							class="fas fa-arrow-left"></i> Voltar
						</a>
					</div>
				</c:if>
			</div>
		</div>
	</div>
	<c:import url="elements/rodape.jsp" charEncoding="UTF-8"></c:import>
	<script>
		function atualizaExibirNotas(formSelecionado) {
			document.getElementById(formSelecionado).submit();
		}
	</script>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js"
		integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js"
		integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1"
		crossorigin="anonymous"></script>
	<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>


</body>
</html>