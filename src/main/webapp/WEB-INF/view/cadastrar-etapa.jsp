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
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/texteditor.css" />
</head>
<body onload="adicionaCampoNotaMinima()">
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
				<a class="breadcrumb-item active" href="cadastrarEtapas">Cadastrar
					Etapa</a> </nav>
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
				<h1>Cadastrar Etapa</h1>
				<p>Atenção: Os campos abaixo (*) são de preenchimento
					obrigatório</p>
				<br>
				<div class="form-group">
					<c:if test="${not empty selecao.inscricao}">
						<form method="POST"
							action="${pageContext.request.contextPath}/cadastrarEtapa/${selecao.codSelecao}"
							accept-charset="UTF-8" id="needs-validation" novalidate>
					</c:if>
					<c:if test="${empty selecao.inscricao}">
						<form method="POST"
							action="${pageContext.request.contextPath}/cadastrarEtapa/inscricao/${selecao.codSelecao}"
							accept-charset="UTF-8" id="needs-validation" novalidate>
					</c:if>
					<label for="tituloInput">Título*</label> <input type="text"
						name="titulo" value="${empty selecao.inscricao ? 'Inscrição': ''}"
						class="form-control" id="tituloInput"
						aria-describedby="tituloHelp"
						placeholder="Digite um título para a etapa"
						${empty selecao.inscricao ? 'readonly': ''} required> <small
						id="tituloHelp" class="form-text text-muted">Exemplo:
						Inscrição</small>
					<div class="invalid-feedback"></div>
					<br> <label for="descricaoInput">Descrição*</label>
					<textarea class="form-control" name="descricao" id="descricaoInput"
						placeholder="Digite uma breve descrição sobre a etapa" required>${etapa.descricao}</textarea>
					<div class="invalid-feedback"></div>
					<br>

					<c:if test="${not empty selecao.inscricao}">
						<br>
						<fmt:parseDate value="${selecao.inscricao.periodo.termino}"
							pattern="yyyy-MM-dd" var="parseDataTerminoIncricao" type="date" />
						<fmt:formatDate value="${parseDataTerminoIncricao}"
							pattern="dd/MM/yyyy" var="dataTerminoIncricao" type="date" />
						<label for="etapaAnteriorInput">Etapa anterior*</label>
						<select name="prereq" id="etapaPreRequisito"
							class="form-control col-md-8" id="etapaAnteriorInput" required>
							<option value="0" selected="selected" disabled="disabled">Selecione
								a etapa anterior a esta</option>
							<option value="${selecao.inscricao.codEtapa}">${selecao.inscricao.titulo}</option>
							<c:forEach var="etapa" items="${selecao.etapas}">
								<fmt:parseDate value="${etapa.periodo.termino}"
									pattern="yyyy-MM-dd" var="parseDataTermino" type="date" />
								<fmt:formatDate value="${parseDataTermino}" pattern="dd/MM/yyyy"
									var="dataTermino" type="date" />
								<option value="${etapa.codEtapa}">${etapa.titulo}</option>
							</c:forEach>
						</select>
						<div class="invalid-feedback"></div>
					</c:if>
					<c:if test="${empty selecao.etapas}">
						<input type="hidden" value="0" name="prerequisito">
					</c:if>

					<br> <label for="periodoInput">Período da Etapa*</label>
					<div id="sandbox-container">
						<div class="input-daterange input-group col-lg-6 align-left"
							style="padding-left: 0px;" id="datepicker">
							<input type="text" class="form-control text-left"
								placeholder="Digite a data de início desta etapa"
								name="dataInicio" id="dataInicioInput" required /> <span
								class="input-group-addon">até</span> <input type="text"
								class="form-control text-left "
								placeholder="Digite a data de término desta etapa"
								name="dataTermino" id="dataTerminoInput" required />
							<div class="invalid-feedback"></div>
						</div>
						<small id="periodoHelp" class="form-text text-muted">Selecione
							uma data para início e término</small>
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
							<div class="form-row">
								<input type="text" name="documento"
									class="form-control col-md-8" id="documentoInput"
									placeholder="Digite o nome do documento exigido para esta etapa">&nbsp;&nbsp;
								<input type="button" class="btn btn-secondary btn-sm "
									onclick="adicionaDocumento()" value="Adicionar">
							</div>

							<br>
							<ul class="list-group col-md-8 " id="listaDocumentos">
							</ul>
						</div>
					</div>
					<br>

					<!--  -->
					<div class="card">
						<div class="card-header col-auto">
							<label for="documentoInput">Documentação Opcional</label>
						</div>

						<div class="card-body">
							<div class="form-row">
								<input type="text" name="documentoOp"
									class="form-control col-md-8" id="documentoOpInput"
									placeholder="Digite o nome do documento opcional para esta etapa">&nbsp;&nbsp;
								<input type="button" class="btn btn-secondary btn-sm "
									onclick="adicionaDocumentoOp()" value="Adicionar">
							</div>

							<br>
							<ul class="list-group col-md-8 " id="listaDocumentosOp">
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
							<br> <label for="criterioDeAvaliacaoInput">Critério
								de Avaliação*</label> <select name="criterioDeAvaliacao"
								class="form-control col-md-8" id="criterioInput"
								onchange="atualizaCampoNotaMinima()" required>

								<c:if test="${not empty selecao.inscricao}">
									<option value="" selected="selected" disabled="disabled">Selecione
										o critério de avaliação dessa etapa</option>
									<option value="1">Nota</option>
									<option value="2">Aprovação</option>
									<option value="3">Deferimento</option>
								</c:if>

								<c:if test="${empty selecao.inscricao}">
									<option value="3" selected="selected">Deferimento</option>
								</c:if>
							</select> <br> <span id="campoNotaMinima"> </span>

							<div class="invalid-feedback"></div>

							<br> <label for="AvaliadoresInput">Avaliadores</label>
							<div class="form-row">
								<select id="avaliadorInput" class="form-control col-md-8"
									style="margin-left: 3px"
									<c:if test="${not empty selecao.inscricao}"> disabled </c:if>
									onclick="adicionaAvaliador()">
									<option value="" selected="selected" disabled="disabled">Selecione
										os avaliadores desta etapa</option>
									<c:forEach items="${avaliadores}" var="avaliador">
										<option id="avaliadorOption-${avaliador.nome}"
											value="${avaliador.codUsuario}-${avaliador.nome}">${avaliador.nome}</option>
									</c:forEach>
								</select> &nbsp;&nbsp;
								<!-- <input type="button" class="btn btn-secondary btn-sm " onclick="adicionaAvaliador()" value="Adicionar">-->
							</div>
							<br>
							<ul class="list-group col-md-8 " id="listaAvaliadores">
							</ul>
							<br>
						</div>
					</div>
					<br> <a
						href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}"
						class="btn btn-secondary"> Cancelar </a> <input type="button"
						class="btn btn-primary" value="Salvar" data-toggle="modal"
						data-target="#confirmarEtapa">

					<!-- Modal -->
					<div class="modal fade" id="confirmarEtapa" tabindex="-1"
						role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="modalLabel">Confirmar cadastro
										da etapa</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<p>Você deseja confirmar o cadastro da etapa?</p>
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
	<script>
		function habilitaEdicao() {
			document.getElementById('avaliadorInput').disabled = false;

		}

		var datas = [];
		$(function() {
			$("#sandbox-container .input-daterange").datepicker({
				format : "dd/mm/yyyy",
				todayBtn : "linked",
				language : "pt-BR",
				orientation : "top left",
				todayHighlight : true,
				toggleActive : true

			});
			$("#etapaPreRequisito").on(
					"change",
					function() {
						//alert(document.getElementById('dataTerminoEtapaAnt-'+($(this).val())).value);
						$("#sandbox-container .input-daterange").datepicker(
								"startDate",
								document.getElementById('dataTerminoEtapaAnt-'
										+ $(this).val()).value);
					});
		});

		function getListaAvaliadores() {
			ordenaLista();
			var input, filter, ul, li, a, i;
			input = document.getElementById("nomeAvaliador");
			filter = input.value.toUpperCase();
			ul = document.getElementById("listaAvaliadores");
			li = ul.getElementsByTagName("li");
			for (i = 0; i < li.length; i++) {
				a = li[i].getElementsByTagName("span")[0];
				if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
					li[i].getElementsByTagName("span")[0].style.display = "";
					li[i].getElementsByTagName("input")[0].style.display = "";
					li[i].style.display = "";
				} else {
					if (li[i].getElementsByTagName("input")[0].checked === false) {
						li[i].getElementsByTagName("span")[0].style.display = "none";
						li[i].getElementsByTagName("input")[0].style.display = "none";
						li[i].style.display = "none";
					}

				}
			}
		}

		function ordenaLista() {
			var list, i, switching, b, shouldSwitch;
			list = document.getElementById("listaAvaliadores");
			switching = true;
			while (switching) {
				switching = false;
				b = list.getElementsByTagName("li");
				for (i = 0; i < (b.length - 1); i++) {
					shouldSwitch = false;
					if ((b[i + 1].getElementsByTagName("input")[0].checked === true)
							&& (b[i].getElementsByTagName("input")[0].checked === false)) {
						shouldSwitch = true;
						break;
					}
				}
				if (shouldSwitch) {
					b[i].parentNode.insertBefore(b[i + 1], b[i]);
					switching = true;
				}
			}
		}

		var listaDocumentos = [];
		var numDocumentos = 0;
		function adicionaDocumento() {
			var nomeDocumento = document.getElementById("documentoInput").value;
			if (nomeDocumento !== "") {
				listaDocumentos[numDocumentos] = nomeDocumento;
				numDocumentos++;
			}
			document.getElementById("documentoInput").value = "";
			atualizaDocumentos();

		}
		function atualizaDocumentos() {
			var list = document.getElementById("listaDocumentos");
			list.innerHTML = "";
			for (i = 0; i < listaDocumentos.length; i++) {
				if (listaDocumentos[i] !== "") {
					list.innerHTML += '<li class="list-group-item" ><input type="hidden" name="documentosExigidos" value="'+listaDocumentos[i]+'" style="display: none;"> '
							+ listaDocumentos[i]
							+ '<button type="button" class="btn btn-light btn-sm material-icons float-right" style="font-size: 15px;" onclick="removeDocumento(\''
							+ listaDocumentos[i] + '\')">clear</button></li>';
				}
			}
		}
		function removeDocumento(nome) {
			for (i = 0; i < listaDocumentos.length; i++) {
				if (listaDocumentos[i] === nome) {
					listaDocumentos[i] = "";
				}
			}
			atualizaDocumentos();
		}

		//opcionais
		var listaDocumentosOp = [];
		var numDocumentosOp = 0;
		function adicionaDocumentoOp() {
			var nomeDocumentoOp = document.getElementById("documentoOpInput").value;
			if (nomeDocumentoOp !== "") {
				listaDocumentosOp[numDocumentosOp] = nomeDocumentoOp;
				numDocumentosOp++;
			}
			document.getElementById("documentoOpInput").value = "";
			atualizaDocumentosOp();

		}
		function atualizaDocumentosOp() {
			var list = document.getElementById("listaDocumentosOp");
			list.innerHTML = "";
			for (i = 0; i < listaDocumentosOp.length; i++) {
				if (listaDocumentosOp[i] !== "") {
					list.innerHTML += '<li class="list-group-item" ><input type="hidden" name="documentosOpcionais" value="'+listaDocumentosOp[i]+'" style="display: none;"> '
							+ listaDocumentosOp[i]
							+ '<button type="button" class="btn btn-light btn-sm material-icons float-right" style="font-size: 15px;" onclick="removeDocumentoOp(\''
							+ listaDocumentosOp[i] + '\')">clear</button></li>';
				}
			}
		}
		function removeDocumentoOp(nome) {
			for (i = 0; i < listaDocumentosOp.length; i++) {
				if (listaDocumentosOp[i] === nome) {
					listaDocumentosOp[i] = "";
				}
			}
			atualizaDocumentosOp();
		}

		var listaAvaliadores = [];
		var codAvaliadores = [];
		var numAvaliadores = 0;
		function adicionaAvaliador() {
			var avaliadorInput = document.getElementById("avaliadorInput").value;
			var nomeAvaliador = avaliadorInput.substring(avaliadorInput
					.indexOf("-") + 1, avaliadorInput.lenght);
			var codAvaliador = avaliadorInput.substring(0, avaliadorInput
					.indexOf("-"));
			if (nomeAvaliador !== "") {
				listaAvaliadores[numAvaliadores] = nomeAvaliador;
				codAvaliadores[numAvaliadores] = codAvaliador;
				document
						.getElementById("avaliadorOption-" + nomeAvaliador + "").disabled = "disabled";
				numAvaliadores++;
			}
			document.getElementById("avaliadorInput").value = "";
			atualizaAvaliadores();

		}
		function atualizaAvaliadores() {
			var list = document.getElementById("listaAvaliadores");
			list.innerHTML = "";
			for (i = 0; i < listaAvaliadores.length; i++) {
				if (listaAvaliadores[i] !== "") {
					list.innerHTML += '<li class="list-group-item"><input type="hidden" name="codAvaliadores" value="'+codAvaliadores[i]+'" style="display: none;"> '
							+ listaAvaliadores[i]
							+ '<button type="button" class="btn btn-light btn-sm material-icons float-right" style="font-size: 15px;" onclick="removeAvaliador(\''
							+ listaAvaliadores[i] + '\')">clear</button></li>';
				}
			}
		}
		function removeAvaliador(codAvaliador) {
			for (i = 0; i < listaAvaliadores.length; i++) {
				if (listaAvaliadores[i] === codAvaliador) {
					document.getElementById("avaliadorOption-" + codAvaliador
							+ "").disabled = "";
					listaAvaliadores[i] = "";
					codAvaliadores[i] = "";

				}
			}
			atualizaAvaliadores();
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
				document.getElementById("campoNotaMinima").innerHTML = "Nota mínima: <input type='text' name='notaMinima' style='width: 150px' class='form-control' placeholder='Nota miníma requerida' required>";
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
					frame.contentDocument.getElementsByTagName('body')[0].className = 'empty';

					descricao_div.setAttribute('style', 'border-color: red');
				} else {
					descricao_div.setAttribute('style', '');
				}

			}
		}

		function limparTextAreaDescricao() {
			let descricao_div = document.getElementsByClassName('cazary')[0];
			let frame = descricao_div.getElementsByTagName('iframe')[0];
			frame.contentDocument.getElementsByTagName('body')[0].className = 'empty';
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

