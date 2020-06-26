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
<!-- Include Editor style. -->
<link
	href='https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.8.5/css/froala_editor.min.css'
	rel='stylesheet' type='text/css' />
<link
	href='https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.8.5/css/froala_style.min.css'
	rel='stylesheet' type='text/css' />

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
				<a class="breadcrumb-item active" href="#">Avaliar participantes</a>
				</nav>
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
				<h1>Avaliar participantes</h1>
				<c:if test="${empty participantesEtapa}">
					<p class="text-muted">Esta etapa ainda não possui candidatos
						cadastrados!</p>
				</c:if>
				<br>
				<table class="table table-responsive" text-align="center">
					<thead>
						<tr>
							<th scope="col">Participante</th>
							<th scope="col">Status</th>
							<th scope="col">Nota</th>
							<th scope="col">Opção</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="participante" items="${participantesEtapa}">
							<c:set var="avaliado" value="${false}" />
							<c:set var="avaliacaoParticipante" value="${null}" />
							<tr>
								<td>${participante.candidato.nome}</td>
								<c:if test="${not empty etapa.avaliacoes}">
									<c:forEach var="avaliacao" items="${etapa.avaliacoes}">
										<c:if
											test="${(avaliacao.participante.codParticipante == participante.codParticipante) and (avaliacao.avaliador.codUsuario == avaliador.codUsuario)}">
											<c:set var="avaliacaoParticipante" value="${avaliacao}" />
											<c:if test="${avaliacaoParticipante.estado == PENDENTE}">
												<td><span class="badge badge-danger">${avaliacao.estado}</span></td>
												<td>  -</td>
												<td><button type="button"
														class="btn btn-primary btn-sm" data-toggle="modal"
														data-target="#avaliar${participante.candidato.codUsuario}">Avaliar</button></td>
												<c:set var="avaliado" value="${false}" />
											</c:if>
											<c:if test="${avaliacao.estado != 'PENDENTE'}">
												<c:set var="avaliado" value="${true}" />
												<td>
												<c:if test="${etapa.criterioDeAvaliacao == 'APROVACAO'}" >${avaliacaoParticipante.aprovado ? "Aprovado" : "Reprovado"}</c:if>
												<c:if test="${etapa.criterioDeAvaliacao == 'DEFERIMENTO'}" >${avaliacaoParticipante.aprovado ? "Deferido" : "Indeferido"}</c:if>
												<c:if test="${etapa.criterioDeAvaliacao == 'NOTA'}" >${avaliacaoParticipante.aprovado ? "Aprovado" : "Reprovado"}</c:if>
												</td>
												<td>
												<c:if test="${etapa.criterioDeAvaliacao == 'NOTA'}" >${avaliacaoParticipante.nota}</c:if>
												<c:if test="${etapa.criterioDeAvaliacao != 'NOTA'}" >-</c:if>
												</td>
												<td><button type="button"
														class="btn btn-primary btn-sm" data-toggle="modal"
														data-target="#avaliar${participante.candidato.codUsuario}">Editar Avaliação</button></td>
											</c:if>
										</c:if>
									</c:forEach>
								</c:if>
								<c:if test="${empty etapa.avaliacoes}">
									<c:set var="avaliado" value="${false}" />
									<td><span class="badge badge-danger">Pendente</span></td>
									<td>  -</td>
									<td><button type="button" class="btn btn-primary btn-sm"
											data-toggle="modal"
											data-target="#avaliar${participante.candidato.codUsuario}">Avaliar</button></td>
								</c:if>
								<c:if test="${not empty etapa.avaliacoes and avaliado == false}">
									<c:set var="avaliado" value="${false}" />
									<td><span class="badge badge-danger">Pendente</span></td>
									<td>-</td>
									<td><button type="button" class="btn btn-primary btn-sm"
											data-toggle="modal"
											data-target="#avaliar${participante.candidato.codUsuario}">Avaliar</button></td>
								</c:if>
				
							</tr>
							<div class="modal fade"
								id="avaliar${participante.candidato.codUsuario}" tabindex="-1"
								role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<form
											<c:if test="${avaliado}">action="${pageContext.request.contextPath}/avaliar/atualizar/${etapa.codEtapa}/${avaliacaoParticipante.codAvaliacao}"</c:if>
											<c:if test="${not avaliado}">action=""</c:if> method="post"
											accept-charset="UTF-8">
											<div class="modal-header">
												<h5 class="modal-title" id="modalLabel">${avaliado ? "Editar Avaliação" : "Avaliar Participante"}</h5>
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
											</div>
											<div class="modal-body">

												<input type="hidden" name="participante"
													value="${participante.codParticipante}">
												<div class="form-group">
													<label for="recipient-name" class="form-control-label">Participante:</label>
													<p>${participante.candidato.nome}</p>
												</div>
												<div class="form-group">
													<label for="recipient-name" class="form-control-label">Documentação:</label>
													<c:if test="${not empty etapa.documentacoes}">
														<a
															href="${pageContext.request.contextPath}/avaliar/download/${selecao.codSelecao}/${etapa.codEtapa}/${participante.codParticipante}">
															Download completo da documentação (.zip)</a>
													</c:if>
													<c:forEach var="documentacao"
														items="${etapa.documentacoes}">
														<c:if
															test="${documentacao.candidato.codParticipante == participante.codParticipante}">
															<c:forEach var="documento"
																items="${documentacao.documentos}">
																<p>
																	<b>${documento.titulo}:</b><a
																		href="${pageContext.request.contextPath}/visualizarDocumentacao?selecao=${selecao.codSelecao}&etapa=${etapa.codEtapa}&documento=${documento.codArquivo}">Ver</a>
																</p>
															</c:forEach>
														</c:if>
													</c:forEach>
												</div>
												<div class="form-group">
													<label for="message-text" class="form-control-label">Avaliação:</label>
													<c:if test="${(etapa.criterioDeAvaliacao.criterio == 1)}">
														<input type="number" name="nota" step="0.01"
															class="form-control col-sm-2 disabled" id="notaInput"
															required
															${etapa.estado == "FINALIZADA" ? "readonly" : ""}
															value="${(avaliado and (not empty avaliacaoParticipante)) ? avaliacaoParticipante.nota : '0'}"
															min="0" max="10">
													</c:if>
													<c:if test="${(etapa.criterioDeAvaliacao.criterio == 2)}">
														<div class="form-check form-check-inline">
															<label class="form-check-label"> <input
																${etapa.estado == "FINALIZADA" ? "disabled" : ""}
																class="form-check-input" type="radio" name="aprovacao"
																id="aprovadoOpcao" value="1"
																${(avaliado and (not empty avaliacaoParticipante) and avaliacaoParticipante.aprovado) ? "checked='checked'" : ""}>
																Aprovado
															</label>
														</div>
														<div class="form-check form-check-inline">
															<label class="form-check-label"> <input
																${etapa.estado == "FINALIZADA" ? "disabled" : ""}	
																class="form-check-input" type="radio" name="aprovacao"
																id="reprovadoOpcao" value="0"
																${(avaliado and (not empty avaliacaoParticipante) and (not avaliacaoParticipante.aprovado)) ? "checked='checked'" : ""}>
																Reprovado
															</label>
														</div>
													</c:if>
													<c:if test="${(etapa.criterioDeAvaliacao.criterio == 3)}">
														<div class="form-check form-check-inline">
															<label class="form-check-label"> <input
																${etapa.estado == "FINALIZADA" ? "disabled" : ""}
																class="form-check-input" type="radio" name="deferimento"
																id="deferidoOpcao" required value="1"
																${(avaliado and (not empty avaliacaoParticipante) and avaliacaoParticipante.aprovado) ? "checked='checked'" : ""}>
																Deferido
															</label>
														</div>
														<div class="form-check form-check-inline">
															<label class="form-check-label"> <input
																${etapa.estado == "FINALIZADA" ? "disabled" : ""}
																class="form-check-input" type="radio" name="deferimento"
																id="indeferidoOpcao" value="0"
																${(avaliado and (not empty avaliacaoParticipante) and (not avaliacaoParticipante.aprovado)) ? "checked='checked'" : ""}>
																Indeferido
															</label>
														</div>
													</c:if>
												</div>
												<div class="form-group">
													<label for="message-text" class="form-control-label">Observações:</label>
													<textarea class="form-control" id="message-text"
														name="observacoes"
														${etapa.estado == "FINALIZADA" ? "readonly" : ""}
														value="${(avaliado and (not empty avaliacaoParticipante) and not empty avaliacaoParticipante.observacao) ? avaliacaoParticipante.observacao : ''}">${(avaliado and (not empty avaliacaoParticipante) and not empty avaliacaoParticipante.observacao) ? avaliacaoParticipante.observacao : ''}</textarea>
												</div>

											</div>
											<div class="modal-footer">
												<c:if test="${(etapa.estado) != FINALIZADA}">
													<button type="submit" class="btn btn-primary">Salvar</button>
												</c:if>
												
												<button type="button" class="btn btn-secondary"
													data-dismiss="modal">Cancelar</button>
											</div>
										</form>
									</div>
								</div>
						</c:forEach>
					</tbody>

				</table>
				<a
					href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}"
					class="btn btn-secondary btn-sm"> Voltar </a>
			</div>
		</div>

	</div>
	<c:import url="elements/rodape.jsp" charEncoding="UTF-8"></c:import>
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
	<!-- Include JS file. -->
	<script type='text/javascript'
		src='https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.8.5/js/froala_editor.min.js'></script>
</body>
</html>