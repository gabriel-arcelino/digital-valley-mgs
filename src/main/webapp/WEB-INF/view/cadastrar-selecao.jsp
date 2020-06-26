<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML">
<html>
<head>
<meta charset="utf-8" />
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
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.4.1/css/all.css"
	integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz"
	crossorigin="anonymous">
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
				<nav aria-label="breadcrumb" role="navigation">
					<ol class="breadcrumb">
						<li class="breadcrumb-item">Você está em:</li>
						<li class="breadcrumb-item" aria-current="page"><a
							href="${pageContext.request.contextPath}/">Início</a></li>
						<li class="breadcrumb-item active" aria-current="page">Cadastrar
							Seleção</li>
					</ol>
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
				<h1>Cadastrar Seleção</h1>
				<p>Atenção: Os campos abaixo (*) são de preenchimento
					obrigatório</p>
				<br>
				<div class="form-group">
					<form method="POST" action="cadastrarSelecao"
						accept-charset="UTF-8" enctype="multipart/form-data"
						id="needs-validation" novalidate>
						<label for="tituloInput">Título*</label> <input type="text"
							name="titulo" class="form-control" id="tituloInput"
							aria-describedby="tituloHelp"
							placeholder="Digite um título para a seleção"
							value="${not (novaSelecao.titulo eq '') ? novaSelecao.titulo : ''}"
							required> <small id="tituloHelp"
							class="form-text text-muted">Exemplo: Iniciação à
							Docência - 2018.1</small>
						<div class="invalid-feedback"></div>
						<br> <label for="descricaoInput">Descrição*</label>
						<textarea class="form-control" name="descricao"
							id="descricaoInput"
							placeholder="Digite uma breve descrição sobre a seleção" required>${not (novaSelecao.descricao eq '') ? novaSelecao.descricao : ''}</textarea>
						<div class="invalid-feedback"></div>
						<br> <label for="preRequisitosInput">Pré Requisitos</label>
						<textarea name="descricaoPreRequisitos" class="form-control"
							id="preRequisitosInput"
							placeholder="Digite uma breve descrição sobre os pré requisitos para participar da seleção">${not (novaSelecao.descricaoPreRequisitos eq '') ? novaSelecao.descricaoPreRequisitos : ''}</textarea>
						<br> <label for="categoriaInput">Categoria*</label> <select
							type="text" name="categoria" class="form-control custom-select"
							id="categoriaInput" required>
							<option value=""
								${(not novaSelecao.categoria) ? 'selected="selected"' : ''}
								disabled="disabled">Selecione a categoria da seleção</option>
							<option
								${novaSelecao.categoria eq 'Assitência Estudantil' ? 'selected="selected"' : ''}>Assistência
								Estudantil</option>
							<option
								${novaSelecao.categoria eq 'Seleção para Discentes' ? 'selected="selected"' : ''}>Seleção
								para Discentes</option>
							<option
								${novaSelecao.categoria eq 'Cargos de Docente' ? 'selected="selected' : ''}>Cargos
								de Docente</option>
							<option
								${novaSelecao.categoria eq 'Cargos de Técnicos Admin' ? 'selected="selected"' : ''}>Cargos
								de Técnicos Admin</option>
							<option
								${novaSelecao.categoria eq 'Professores Substitutos' ? 'selected="selected"' : ''}>Professores
								Substitutos</option>
						</select> <br> <br> <label for="areaDeConcentracaoInput">Área
							de Concentração</label> <input type="text" name="areaDeConcentracao"
							class="form-control" id="areaDeConcentracaoInput"
							aria-describedby="tituloHelp"
							placeholder="Digite o nome da área de concentração"
							value="${not (novaSelecao.areaDeConcentracao eq '') ? novaSelecao.areaDeConcentracao : ''}">
						<small id="tituloHelp" class="form-text text-muted">Exemplo:
							Computação, Engenharia Mecânica, LINCE</small>
						<div class="invalid-feedback"></div>
						<br>

						<div class="card">
							<div class="card-header col-auto">

								<label
									class="custom-control custom-checkbox mb-2 mr-sm-2 mb-sm-0"
									for="isVagasLimitadasInput"> <input type="checkbox"
									class="custom-control-input" id="isVagasLimitadasInput"
									onclick="habilitaCampoVagas()" alt="Definir número de vagas" />
									<span class="custom-control-indicator"></span> <span
									class="custom-control-description" style="margin-top: 4px;">Definir
										o número de vagas</span>
								</label>
							</div>

							<div class="card-body">
								<label for="vagasRemuneradasInput">Número de vagas
									remuneradas</label> <input type="number" name="vagasRemuneradas"
									class="form-control col-sm-2 disabled"
									id="vagasRemuneradasInput"
									value="${not (novaSelecao.vagasRemuneradas eq '0') ? novaSelecao.vagasRemuneradas : '0'}"
									min="0" max="100" disabled>
								<div class="invalid-feedback"></div>
								<br> <label for="vagasVoluntariasInput">Número de
									vagas voluntárias</label> <input type="number" name="vagasVoluntarias"
									class="form-control col-sm-2 disabled"
									id="vagasVoluntariasInput"
									value="${not (novaSelecao.vagasVoluntarias eq '0') ? novaSelecao.vagasVoluntarias : '0'}"
									min="0" max="100" disabled>
								<div class="invalid-feedback"></div>
							</div>
						</div>
						<br>
						<div class="card">
							<div class="card-header col-auto">
								<span class="custom-control-description"
									style="margin-top: 4px;">Documentos da seleção</span>
							</div>

							<div class="card-body">
								<label for="editalInput">Edital*</label> <input type="text"
									name="file" class="form-control" id="arquivoInput"
									aria-describedby="editalHelp"
									placeholder="Adicione aqui o link para o edital da seleção"
									accept="application/pdf" required
									value="${not (novaSelecao.edital eq '') ? novaSelecao.edital : ''}">
								<small id="tituloHelp" class="form-text text-muted">Ex.
									http://www.campusrussas.ufc.br/docs/edital.pdf</small>
								<div class="invalid-feedback"></div>
								<br> <br> <label for="anexoInput">Anexos</label>
								<div class="form-row" style="margin-left: 2px;">
									<input type="text" class="form-control col-md-4"
										id="nomeAnexoInput"
										placeholder=" Digite o titulo do anexo da seleção">&nbsp;
									&nbsp; <input type="text" class="form-control col-md-6"
										id="linkAnexoInput"
										placeholder=" Adicione aqui o link para acesso deste anexo">&nbsp;
									&nbsp; <input type="button" class="btn btn-secondary btn-sm "
										onclick="adicionaAnexo()" value="Adicionar">
								</div>

								<br>
								<ul class="list-group col-md-8 " id="listaAnexos">
								</ul>
								<br> <label for="aditivosInput">Aditivos</label>
								<div class="form-row" style="margin-left: 2px;">
									<input type="text" class="form-control col-md-4"
										id="nomeAditivoInput"
										placeholder=" Digite o titulo do aditivo da seleção">&nbsp;
									&nbsp; <input type="text" class="form-control col-md-6"
										id="linkAditivoInput"
										placeholder=" Adicione aqui o link para acesso deste aditivo">&nbsp;
									&nbsp; <input type="button" class="btn btn-secondary btn-sm "
										onclick="adicionaAditivo()" value="Adicionar">
								</div>

								<br>
								<ul class="list-group col-md-8 " id="listaAditivos">
								</ul>
								<br>
							</div>
						</div>
						<br>
						<hr />
						<label for="responsavelInput">Responsáveis</label>
						<div class="form-row">
							<select id="responsavelInput" class="form-control col-md-8"
								style="margin-left: 3px" onclick="adicionaResponsavel()">
								<option value="" selected="selected" disabled="disabled">Selecione
									os responsáveis por esta seleção</option>
								<c:forEach items="${responsaveis}" var="responsavel">
									<c:if
										test="${responsavel.codUsuario != usuarioDarwin.codUsuario}">
										<option id="responsavelOption-${responsavel.codUsuario}"
											value="${responsavel.codUsuario}-${responsavel.nome}">${responsavel.nome}</option>
									</c:if>
								</c:forEach>
							</select> &nbsp;&nbsp;
							<!-- <input type="button" class="btn btn-secondary btn-sm " onclick="adicionaResponsavel()" value="Adicionar"> -->
						</div>
						<br>
						<ul class="list-group col-md-8" id="listaResponsaveis">
						</ul>

						<br> <a href="${pageContext.request.contextPath}" id="enviar"
							class="btn btn-secondary"> Cancelar </a> <input type="button"
							class="btn btn-primary" value="Salvar e Continuar"
							data-toggle="modal" data-target="#confirmarSelecao">

						<!-- Modal -->
						<div class="modal fade" id="confirmarSelecao" tabindex="-1"
							role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="modalLabel">Confirmar
											cadastro da seleção</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<p>Você deseja confirmar o cadastro da seleção?</p>
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
	<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>

	<script
		src="${pageContext.request.contextPath}/resources/js/scriptCadastrarSelecao.js"></script>
	<!-- Include JS file. -->
	<script type='text/javascript'
		src='https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.8.5/js/froala_editor.min.js'></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/cazary.min.js"></script>
	<script type="text/javascript">
		(function($, window) {
			$(function($) {
				$("textarea#descricaoInput").cazary({
					commands : "FULL"
				});

				$("textarea#preRequisitosInput").cazary({
					commands : "FULL"
				});
			});
		})(jQuery, window);

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
	</script>
</body>
</html>
