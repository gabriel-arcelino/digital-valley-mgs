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
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/texteditor.css" />
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
				<a class="breadcrumb-item active" href="">Editar Etapa</a> </nav>
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
				<h1>Editar Etapa</h1>
				<p>Atenção: Os campos abaixo (*) são de preenchimento
					obrigatório</p>
				<br>
				<div class="form-group">
					<c:if test="${tipo eq 'etapa'}">
						<form method="POST"
							action="${pageContext.request.contextPath}/editarEtapa/${selecao.codSelecao}/${etapa.codEtapa}"
							accept-charset="UTF-8" id="needs-validation" novalidate>
					</c:if>
					<c:if test="${tipo eq 'inscricao'}">
						<form method="POST"
							action="${pageContext.request.contextPath}/editarEtapa/${selecao.codSelecao}/inscricao/${etapa.codEtapa}"
							accept-charset="UTF-8" id="needs-validation" novalidate>
					</c:if>

					<label for="tituloInput">Título*</label> <input type="text"
						${etapa.estado ne 'ESPERA' ? 'disabled' : ''} name="titulo"
						value="${etapa.titulo}" class="form-control" id="tituloInput"
						aria-describedby="tituloHelp"
						placeholder="Digite um título para a etapa" required> <br>
					<label for="descricaoInput"> Descrição*</label>
					<c:if test="${etapa.estado ne 'ESPERA'}">
						<textarea class="form-control" name="descricao" rows="15"
							placeholder="Digite uma breve descrição sobre a etapa" required
							disabled>${etapa.descricao}</textarea>
					</c:if>
					<c:if test="${etapa.estado eq 'ESPERA'}">
						<textarea class="form-control" name="descricao"
							id="descricaoInput"
							placeholder="Digite uma breve descrição sobre a etapa" required>${etapa.descricao}</textarea>
					</c:if>

					<c:if test="${tipo eq 'etapa'}">
						<c:if test="${not empty selecao.inscricao}">
							<br>
							<br>
							<label for="etapaAnteriorInput">Etapa anterior*</label>
							<c:if test="${etapa.estado eq 'ESPERA'}">
								<select name="prereq" class="form-control col-md-8"
									id="etapaAnteriorInput" required>
									<option value="0" selected="selected" disabled="disabled">Selecione
										a etapa anterior a esta</option>
									<fmt:parseDate value="${selecao.inscricao.periodo.termino}"
										pattern="yyyy-MM-dd" var="parseDataTerminoIncricao"
										type="date" />
									<fmt:formatDate value="${parseDataTerminoIncricao}"
										pattern="dd/MM/yyyy" var="dataTerminoIncricao" type="date" />
									<option value="${selecao.inscricao.codEtapa}"
										onclick="atualizaDataMinimaPermitida('${dataTerminoIncricao}')"
										${(etapa.prerequisito.codEtapa == selecao.inscricao.codEtapa) ? "selected='selected'" : ""}>${selecao.inscricao.titulo}</option>
									<c:forEach var="e" items="${selecao.etapas}">
										<fmt:parseDate value="${etapa.periodo.termino}"
											pattern="yyyy-MM-dd" var="parseDataTermino" type="date" />
										<fmt:formatDate value="${parseDataTermino}"
											pattern="dd/MM/yyyy" var="dataTermino" type="date" />
										<option value="${e.codEtapa}"
											onclick="atualizaDataMinimaPermitida('${dataTermino}')"
											${(etapa.prerequisito.codEtapa == e.codEtapa) ? "selected='selected'" : ""}>${e.titulo}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${etapa.estado ne 'ESPERA'}">
								<input name="prereq" class="form-control col-md-8" disabled
									value="${etapa.prerequisito.titulo}">
							</c:if>
							<div class="invalid-feedback"></div>
							<br>
						</c:if>
					</c:if>

					<br /> <br /> <label for="periodoInput"> Período*</label> <br />
					<div id="sandbox-container" class="input-group col-lg-6 align-left"
						style="padding-left: 0px;">

						<c:if test="${selecao.divulgada eq true}">
							<div
								<c:if test="${not empty selecao.inscricao and etapa.codEtapa != selecao.inscricao.codEtapa}">
				                      class="input-daterange"
				                    </c:if>>
								<fmt:parseDate value="${etapa.periodo.inicio}"
									pattern="yyyy-MM-dd" var="parseDataInicio" type="date" />
								<fmt:formatDate value="${parseDataInicio}" pattern="dd/MM/yyyy"
									var="dataInicio" type="date" />
								<input type="text" class="form-control text-left"
									placeholder="Digite a data de início desta etapa"
									id="periodoInput1"
									<c:if test="${not empty selecao.inscricao and etapa.codEtapa == selecao.inscricao.codEtapa}">
				                     readonly 
				                    </c:if>
									<c:if test="${not empty selecao.inscricao and etapa.codEtapa != selecao.inscricao.codEtapa}">
				                     name="dataInicio" required 
				                    </c:if>
									value="${dataInicio}" style="width: 271.05px; height: 38px" />
							</div>
							<div>
								<span class="input-group-addon">até</span>
							</div>
							<div class="input-daterange">

								<fmt:parseDate value="${etapa.periodo.termino}"
									pattern="yyyy-MM-dd" var="parseDataTermino" type="date" />
								<fmt:formatDate value="${parseDataTermino}" pattern="dd/MM/yyyy"
									var="dataTermino" type="date" />

								<input type="text" class="form-control text-left "
									placeholder="Digite a data de término desta etapa"
									id="periodoInput2" name="dataTermino" value="${dataTermino}"
									required style="width: 271.05px; height: 38px" />
							</div>
						</c:if>
						<c:if test="${!selecao.divulgada eq true}">
							<!-- Se nao -->
							<div class="input-daterange input-group col-lg-6 align-left"
								style="padding-left: 0px;" id="datepicker">
								<fmt:parseDate value="${etapa.periodo.inicio}"
									pattern="yyyy-MM-dd" var="parseDataInicio" type="date" />
								<fmt:formatDate value="${parseDataInicio}" pattern="dd/MM/yyyy"
									var="dataInicio" type="date" />
								<fmt:parseDate value="${etapa.periodo.termino}"
									pattern="yyyy-MM-dd" var="parseDataTermino" type="date" />
								<fmt:formatDate value="${parseDataTermino}" pattern="dd/MM/yyyy"
									var="dataTermino" type="date" />
								<input type="text" style="width: 271.05px; height: 38px"
									class="form-control text-left"
									placeholder="Digite a data de início desta etapa"
									id="periodoInput1" name="dataInicio" value="${dataInicio}"
									required /> <span class="input-group-addon">até</span> <input
									type="text" style="width: 271.05px; height: 38px"
									class="form-control text-left "
									placeholder="Digite a data de término desta etapa"
									id="periodoInput2" name="dataTermino" value="${dataTermino}"
									required />
							</div>
						</c:if>

					</div>
					<br>
					<!--  -->
					<div class="card">
						<div class="card-header col-auto">
							<label for="periodoRecursoInput">Período Destinado para
								Recurso (caso tenha)</label>
						</div>
						<div class="card-body">
							<div class="form-row">
								<div id="sandbox-container" style="width: 100%">
									<c:if test="${empty etapa.recurso}">
										<div class="input-daterange input-group col-lg-6 align-left"
											style="padding-left: 0px;" id="datepicker">
											<input type="text" class="form-control text-left"
												placeholder="Digite a data de início"
												name="dataInicioRecurso" id="dataInicioRecursoInput" /> <span
												class="input-group-addon">até</span> <input type="text"
												class="form-control text-left "
												placeholder="Digite a data de término"
												name="dataTerminoRecurso" id="dataTerminoRecursoInput" />
											<div class="invalid-feedback"></div>
										</div>

									</c:if>
									<c:if test="${not empty etapa.recurso}">
										<div class="input-daterange input-group col-lg-6 align-left"
											style="padding-left: 0px;" id="datepicker">
											<fmt:parseDate value="${etapa.recurso.periodo.inicio}"
												pattern="yyyy-MM-dd" var="parseDataInicioRecurso"
												type="date" />
											<fmt:formatDate value="${parseDataInicioRecurso}"
												pattern="dd/MM/yyyy" var="dataInicioRecurso" type="date" />
											<fmt:parseDate value="${etapa.recurso.periodo.termino}"
												pattern="yyyy-MM-dd" var="parseDataTerminoRecurso"
												type="date" />
											<fmt:formatDate value="${parseDataTerminoRecurso}"
												pattern="dd/MM/yyyy" var="dataTerminoRecurso" type="date" />
											<input type="text" class="form-control text-left"
												placeholder="Digite a data de início"
												name="dataInicioRecurso" value="${dataInicioRecurso}"
												id="dataInicioRecursoInput" /> <span
												class="input-group-addon">até</span> <input type="text"
												class="form-control text-left "
												placeholder="Digite a data de término"
												name="dataTerminoRecurso" value="${dataTerminoRecurso}"
												id="dataTerminoRecursoInput" />
											<div class="invalid-feedback"></div>
										</div>

									</c:if>

									<small id="periodoHelp" class="form-text text-muted">Caso
										esta etapa tenha a possibilidade de recurso, selecione o
										período destinado para isso.</small>
								</div>
							</div>
						</div>
					</div>
					<br>
					<!--  -->

					<div class="card">
						<div class="card-header col-auto">
							<label for="documentoInput">Documentação Exigida</label>
						</div>
						<div class="card-body">
							<c:if test="${etapa.estado eq 'ESPERA'}">
								<div class="form-row">
									<input type="text" class="form-control col-md-8"
										id="documentoInput"
										placeholder="Digite o nome do documento exigido para esta etapa">&nbsp;&nbsp;
									<input type="button" class="btn btn-secondary btn-sm "
										onclick="adicionaDocumento()" value="Adicionar">
								</div>
								<br>
							</c:if>

							<ul class="list-group col-md-8 " id="listaDocumentos">
								<c:if
									test="${empty etapa.documentacaoExigida  and (etapa.estado ne 'ESPERA')}">Não possui documentação exigida</c:if>
								<c:forEach var="documento" items="${etapa.documentacaoExigida}">
									<li class="list-group-item"><input type="hidden"
										name="documentosExigidos" value="${documento}"
										style="display: none;">${documento} <c:if
											test="${(etapa.estado eq 'ESPERA')}">
											<button type="button"
												class="btn btn-light btn-sm material-icons float-right"
												style="font-size: 15px;"
												onclick="removeDocumento('${documento}')">clear</button>
										</c:if></li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<br>
					<!--  -->

					<div class="card">
						<div class="card-header col-auto">
							<label for="documentoOpInput">Documentação Opcional</label>
						</div>
						<div class="card-body">
							<c:if test="${etapa.estado eq 'ESPERA'}">
								<div class="form-row">
									<input type="text" class="form-control col-md-8"
										id="documentoOpInput"
										placeholder="Digite o nome do documento opcional para esta etapa">&nbsp;&nbsp;
									<input type="button" class="btn btn-secondary btn-sm "
										onclick="adicionaDocumentoOp()" value="Adicionar">
								</div>
								<br>
							</c:if>

							<ul class="list-group col-md-8 " id="listaDocumentosOp">
								<c:if
									test="${(empty etapa.documentacaoOpcional) and (etapa.estado ne 'ESPERA')}">Não possui documentação opcional</c:if>
								<c:forEach var="documentoOp"
									items="${etapa.documentacaoOpcional}">
									<li class="list-group-item"><input type="hidden"
										name="documentosOpcionais" value="${documentoOp}"
										style="display: none;">${documentoOp} <c:if
											test="${(etapa.estado eq 'ESPERA')}">
											<button type="button"
												class="btn btn-light btn-sm material-icons float-right"
												style="font-size: 15px;"
												onclick="removeDocumentoOp('${documentoOp}')">clear</button>
										</c:if></li>
								</c:forEach>
							</ul>
						</div>
					</div>

					<br>
					<div class="card">
						<div class="card-header col-auto">
							<label for="documentacaoExigidaInput">Dados sobre a
								avaliação</label>
						</div>
						<div class="card-body">
							<c:if test="${tipo eq 'etapa'}">
								<br>
								<label for="criterioDeAvaliacaoInput">Critério de
									Avaliação*</label>

								<select ${etapa.estado ne 'ESPERA'? 'disabled' : ''}
									name="criterio" class="form-control col-md-8"
									class="criterioInput" id="criterioInput"
									onchange="atualizaCampoNotaMinima()" required>
									<option
										${(etapa.criterioDeAvaliacao.criterio == 1 ? "selected" : "")}
										value="1">Nota</option>
									<option
										${(etapa.criterioDeAvaliacao.criterio == 2 ? "selected" : "")}
										value="2">Aprovação</option>
									<option
										${(etapa.criterioDeAvaliacao.criterio == 3 ? "selected" : "")}
										value="3">Deferimento</option>
								</select>
							</c:if>
							<span id="campoNotaMinima"> <c:if
									test="${etapa.criterioDeAvaliacao.criterio == 1}">
									<br>
									<label>Nota mínima*</label>
									<input type='text'
										${etapa.estado ne 'ESPERA' ? 'disabled' : ''}
										name='notaMinima' value="${etapa.notaMinima}"
										style='width: 150px' class='form-control criterioInput'
										id="nota_minima" placeholder='Nota miníma requerida' required>
								</c:if>
							</span>

							<div class="invalid-feedback"></div>

							<br> <label for="AvaliadoresInput">Avaliadores*</label>
							<div class="form-row">
								<c:if test="${etapa.estado eq 'ESPERA' }">
									<select id="avaliadorInput" class="form-control col-md-8"
										style="margin-left: 3px" onclick="adicionaAvaliador()">
										<option selected="selected" disabled="disabled">Selecione
											os avaliadores desta etapa</option>
										<c:forEach items="${usuarios}" var="usuario">
											<option id="avaliadorOption-${usuario.codUsuario}"
												value="${usuario.codUsuario}">${usuario.nome}</option>
										</c:forEach>
									</select>
								</c:if>
							</div>
							<br/>
							<ul class="list-group col-md-8" id="listaAvaliadores">
								<c:if
									test="${(empty etapa.avaliadores) and (etapa.estado ne 'ESPERA')}">Não possui avaliadores</c:if>
								<c:forEach var="avaliador" items="${etapa.avaliadores}">
									<li class="list-group-item"><input type="hidden"
										name="codAvaliadores" value="${avaliador.codUsuario}"
										style="display: none;" />${avaliador.nome} <c:if
											test="${(etapa.estado eq 'ESPERA')}">
											<button type="button"
												class="btn btn-light btn-sm material-icons float-right"
												style="font-size: 15px;"
												onclick="removeAvaliador('${avaliador.nome}')">clear</button>
										</c:if></li>
								</c:forEach>
							</ul>
							<br>
						</div>
					</div>


					<br> <a
						href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}"
						class="btn btn-secondary"> Cancelar </a> <input type="button"
						value="Salvar" class="btn btn-primary" data-toggle="modal"
						data-target="#confirmarEtapa">

					<!-- Modal -->
					<div class="modal fade" id="confirmarEtapa" tabindex="-1"
						role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="modalLabel">Confirmar edição
										da etapa</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<p>Você deseja confirmar a edição da etapa?</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary btn-sm"
										data-dismiss="modal">Cancelar</button>
									<button type="submit" class="btn btn-primary btn-sm"
										onclick="verificarDescricao()">Confirmar</button>
								</div>
							</div>
						</div>
					</div>
					</form>
				</div>
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
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.pt-BR.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>
	<!-- Include JS file. -->
	<script type='text/javascript'
		src='https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.8.5/js/froala_editor.min.js'></script>
	<!--   <script src="${pageContext.request.contextPath}/resources/js/scriptEditarEtapa.js" ></script>  -->
	<script type="text/javascript">
		$('#sandbox-container .input-daterange').datepicker({
			format : "dd/mm/yyyy",
			todayBtn : "linked",
			language : "pt-BR",
			orientation : "top left",
			todayHighlight : true,
			toggleActive : true
		});
		function habilitaEdicao(id) {
			var input = $("#" + id);
			if (document.getElementById(id).getAttribute('readonly')) {
				input.removeAttr('readonly');
			} else {
				input.attr('readonly', true);
			}
			if (id == "criterioInput") {
				habilitaNotaMinima();
			}
		}
		function habilitaNotaMinima() {
			var input = $("#nota_minima");
			var attr = input.attr('readonly');
			if (typeof attr !== typeof undefined && attr !== false) {
				input.removeAttr('readonly');
			} else {
				input.attr('readonly', true);
			}
		}

		var listaDocumentosOp = ${listaDocumentosOp}
		var numDocumentosOp = ${listaNumeroDocumentosOp}

		var nomeDocumentosOp = [];
		var numeroDocumentosOp = 0;

		$(document).ready(function() {
			for (i = 0; i < listaDocumentosOp.length; i++) {
				nomeDocumentosOp[i] = listaDocumentosOp[i];
				numeroDocumentosOp++;
			}
		});

		function adicionaDocumentoOp() {
			var nomeDocumentoOp = document.getElementById("documentoOpInput").value;
			if (nomeDocumentoOp !== "") {
				nomeDocumentosOp[numeroDocumentosOp] = nomeDocumentoOp;
				numeroDocumentosOp++;
			}
			document.getElementById("documentoOpInput").value = "";
			atualizaDocumentosOp();

		}
		function atualizaDocumentosOp() {
			var list = document.getElementById("listaDocumentosOp");
			list.innerHTML = "";
			for (i = 0; i < nomeDocumentosOp.length; i++) {
				if (nomeDocumentosOp[i] !== "") {
					list.innerHTML += '<li class="list-group-item" ><input type="hidden" name="documentosOpcionais" value="'+nomeDocumentosOp[i]+'" style="display: none;"> '
							+ nomeDocumentosOp[i]
							+ '<button type="button" class="btn btn-light btn-sm material-icons float-right" style="font-size: 15px;" onclick="removeDocumentoOp(\''
							+ nomeDocumentosOp[i] + '\')">clear</button></li>';
				}
			}
		}
		function removeDocumentoOp(nome) {
			for (i = 0; i < nomeDocumentosOp.length; i++) {
				if (nomeDocumentosOp[i] === nome) {
					nomeDocumentosOp[i] = "";
				}
			}
			console.log(nomeDocumentosOp[i]);
			atualizaDocumentosOp();
		}

		var listaCodAvaliadores = ${codAvaliadores}
		var listaNomeAvaliadores = ${nomeAvaliadores}

		var codAvaliadores = [];
		var nomeAvaliadores = [];
		var numAvaliadores = 0;

		var listaDocumentos = ${listaDocumentos}
		var numDocumentos = ${listaNumeroDocumentos}

		var nomeDocumentos = [];
		var numeroDocumentos = 0;

		$(document)
				.ready(
						function() {
							for (i = 0; i < listaCodAvaliadores.length; i++) {
								document.getElementById("avaliadorOption-"
										+ listaCodAvaliadores[i]).disabled = "disabled";
								codAvaliadores[i] = listaCodAvaliadores[i];
								nomeAvaliadores[i] = listaNomeAvaliadores[i];
								numAvaliadores++;
							}
							for (i = 0; i < listaDocumentos.length; i++) {
								nomeDocumentos[i] = listaDocumentos[i];
								numeroDocumentos++;
							}
						});

		function adicionaAvaliador() {
			if (parseInt(document.getElementById("avaliadorInput").value) !== NaN) {
				var codAvaliador = parseInt(document.getElementById("avaliadorInput").value)
				
				var nomeAvaliador = document.getElementById("avaliadorOption-"
						+ codAvaliador).textContent;
			
				if (nomeAvaliador !== "") {
					codAvaliadores[numAvaliadores] = Number(codAvaliador);
					nomeAvaliadores[numAvaliadores] = nomeAvaliador;
					document.getElementById("avaliadorOption-" + codAvaliador + "").disabled = "disabled";
					numAvaliadores++;
				}
				
				document.getElementById("avaliadorInput").value = "Selecione os avaliadores desta etapa";
				document.getElementById("avaliadorInput").disable = "";
				atualizaAvaliadores();
			}

		}
		function atualizaAvaliadores() {
			var list = document.getElementById("listaAvaliadores");
			list.innerHTML = "";
			for (i = 0; i < codAvaliadores.length; i++) {
				if (codAvaliadores[i] !== "") {
					list.innerHTML += '<li class="list-group-item"><input type="hidden" name="codAvaliadores" value="'+codAvaliadores[i]+'" style="display: none;"> '
							+ nomeAvaliadores[i]
							+ '<button type="button" class="btn btn-light btn-sm material-icons float-right" style="font-size: 15px;" onclick="removeAvaliador(\''
							+ codAvaliadores[i] + '\')">clear</button></li>';
				}
			}
		}
		function removeAvaliador(codAvaliador) {
			codAvaliador = Number(codAvaliador);
			for (i = 0; i < codAvaliadores.length; i++) {
				if (codAvaliadores[i] === codAvaliador) {
					document.getElementById("avaliadorOption-" + codAvaliador
							+ "").disabled = "";
					codAvaliadores[i] = "";
				}
			}
			atualizaAvaliadores();
		}

		function adicionaDocumento() {
			var nomeDocumento = document.getElementById("documentoInput").value;
			if (nomeDocumento !== "") {
				nomeDocumentos[numeroDocumentos] = nomeDocumento;
				numeroDocumentos++;
			}
			document.getElementById("documentoInput").value = "";
			atualizaDocumentos();

		}
		function atualizaDocumentos() {
			var list = document.getElementById("listaDocumentos");
			list.innerHTML = "";
			for (i = 0; i < nomeDocumentos.length; i++) {
				if (nomeDocumentos[i] !== "") {
					list.innerHTML += '<li class="list-group-item" ><input type="hidden" name="documentosExigidos" value="'+nomeDocumentos[i]+'" style="display: none;"> '
							+ nomeDocumentos[i]
							+ '<button type="button" class="btn btn-light btn-sm material-icons float-right" style="font-size: 15px;" onclick="removeDocumento(\''
							+ nomeDocumentos[i] + '\')">clear</button></li>';
				}
			}
		}
		function removeDocumento(nome) {
			for (i = 0; i < nomeDocumentos.length; i++) {
				if (nomeDocumentos[i] === nome) {
					nomeDocumentos[i] = "";
				}
			}
			atualizaDocumentos();
		}

		function atualizaCampoNotaMinima() {
			if (document.getElementById("criterioInput").value === '1'
					|| document.getElementById("criterioInput").value === '2'
					|| document.getElementById("criterioInput").value === '3') {
				document.getElementById('avaliadorInput').disabled = false;
			}
			if (document.getElementById("criterioInput").value === '1') {
				adicionaCampoNotaMinima();
			} else {
				removeCampoNotaMinima();
			}
		}
		function adicionaCampoNotaMinima() {
			if (document.getElementById("criterioInput").value === '1') {
				document.getElementById("campoNotaMinima").innerHTML = "Nota mínima: <input type='text' name='notaMinima' value='" + ${etapa.notaMinima} + "' style='width: 150px' class='form-control' placeholder='Nota miníma requerida' required>";
			}
		}
		function removeCampoNotaMinima() {
			document.getElementById("campoNotaMinima").innerHTML = "";
		}
		function verificarDescricao() {
			let descricao_div = document.getElementsByClassName('cazary')[0];

			if (descricao_div != undefined) {
				let frame = descricao_div.getElementsByTagName('iframe')[0];

				if (frame.contentDocument.getElementsByClassName('empty').length == 1
						|| frame.contentDocument.getElementsByTagName('body')[0].textContent.length === 0) {
					descricao_div.setAttribute('style', 'border-color: red');
					frame.contentDocument.getElementsByTagName('body')[0].className = 'empty';
				} else {
					descricao_div.setAttribute('style', '');
				}

			}
		}
	</script>
	<script
		src="${pageContext.request.contextPath}/resources/js/cazary.min.js"></script>
	<script type="text/javascript">
		(function($, window) {
			$(function($) {
				$("textarea#descricaoInput").cazary({
					commands : "FULL"
				});

			});
		})(jQuery, window);
	</script>
</body>
</html>