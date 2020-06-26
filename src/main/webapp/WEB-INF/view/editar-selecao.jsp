<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
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
				<nav aria-label="breadcrumb" role="navigation">
				<ol class="breadcrumb">
					<li class="breadcrumb-item">Você está em:</li>
					<li class="breadcrumb-item" aria-current="page"><a
						href="${pageContext.request.contextPath}/">Início</a></li>
					<li class="breadcrumb-item" aria-current="page"><a
						href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}">${selecao.titulo}</a></li>
					<li class="breadcrumb-item active" aria-current="page">Editar
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
				<h1>Editar Seleção</h1>
				<p>Selecione apenas os campos que você deseja editar</p>
				<br>

				<div class="form-group">
					<form method="POST" action="" accept-charset="UTF-8"
						enctype="multipart/form-data" id="needs-validation" novalidate>
						<label for="tituloInput">Título*</label> <input type="text"
							name="titulo" value="${selecao.titulo}" class="form-control"
							id="tituloInput" aria-describedby="tituloHelp"
							placeholder="Digite um título para a seleção" required> <small
							id="tituloHelp" class="form-text text-muted">Exemplo:
							Iniciação à Docência - 2018.1</small>
						<div class="invalid-feedback"></div>
						<br> <label for="descricaoInput">Descrição*</label>
						<textarea class="form-control" rows="6" name="descricao"
							id="descricaoInput"
							placeholder="Digite uma breve descrição sobre a seleção" required>${selecao.descricao}</textarea>
						<div class="invalid-feedback"></div>
						<br> <label for="preRequisitosInput"> Pré Requisitos</label>
						<textarea name="descricaoPreRequisitos" class="form-control"
							id="preRequisitosInput"
							placeholder="Digite uma breve descrição sobre os pré requisitos para participar da seleção"
							readonly="true">${selecao.descricaoPreRequisitos}</textarea>
						<br> <label for="categoriaInput">Categoria*</label> <select
							type="text" name="categoria" class="form-control custom-select"
							id="categoriaInput" required>
							<option
								${selecao.categoria eq 'Assistência Estudantil' ? 'selected="selected"' : ''}>Assistência
								Estudantil</option>
							<option
								${selecao.categoria eq 'Seleção para Discentes' ? 'selected="selected"' : ''}>Seleção
								para Discentes</option>
							<option
								${selecao.categoria eq 'Cargos de Docente' ? 'selected="selected"' : ''}>Cargos
								de Docente</option>
							<option
								${selecao.categoria eq 'Cargos de Técnicos Admin' ? 'selected="selected"' : ''}>Cargos
								de Técnicos Admin</option>
							<option
								${selecao.categoria eq 'Professores Substitutos' ? 'selected="selected"' : ''}>Professores
								Substitutos</option>
						</select> <br> <br> <label for="areaDeConcentracaoInput">Área
							de Concentração</label> <input type="text" name="areaDeConcentracao"
							value="${selecao.areaDeConcentracao}" class="form-control"
							id="areaDeConcentracaoInput" aria-describedby="tituloHelp"
							placeholder="Digite o nome da área de concentração"> <small
							id="tituloHelp" class="form-text text-muted">Exemplo:
							Computação, Engenharia Mecânica, LINCE</small>
						<div class="invalid-feedback"></div>
						<br>

						<div class="card">
							<div class="card-header col-auto">
								<span class="custom-control-description"
									style="margin-top: 4px;">Definir o número de vagas</span>
							</div>

							<div class="card-body">
								<label for="vagasRemuneradasInput">Número de vagas
									remuneradas</label> <input type="number" name="vagasRemuneradas"
									value="${selecao.vagasRemuneradas}"
									class="form-control col-sm-2 disabled"
									id="vagasRemuneradasInput" value="0" min="0" max="100">
								<div class="invalid-feedback"></div>
								<br> <label for="vagasVoluntariasInput">Número de
									vagas voluntárias</label> <input type="number" name="vagasVoluntarias"
									value="${selecao.vagasVoluntarias}"
									class="form-control col-sm-2 disabled"
									id="vagasVoluntariasInput" value="0" min="0" max="100">
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

								<br> <label for="editalInput">Substituir link do
									edital</label> <input type="text" name="file" class="form-control"
									id="editalInput" aria-describedby="editalHelp"
									placeholder="Adicione o link atual para o edital da seleção"
									accept="application/pdf"> <small id="tituloHelp"
									class="form-text text-muted"> Ex.
									http://www.campusrussas.ufc.br/editais-e-selecoes.php</small>
								<div class="invalid-feedback"></div>
								<br> <label for="anexoInput">Anexos</label>
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
								<div class="form-row">
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
						<div class="card">
							<div class="card-header col-auto">
								<label for="ResponsaveisInput">Responsáveis</label>
							</div>
							<div class="card-body">
								<div class="form-row">
									<select id="responsavelInput" class="form-control col-md-8"
										style="margin-left: 3px" onclick="adicionaResponsavel()">
										<option selected="selected" disabled="disabled">Selecione
											os responsáveis por esta seleção</option>
										<c:forEach items="${usuarios}" var="usuario">
											<c:if
												test="${usuario.codUsuario != usuarioDarwin.codUsuario}">
												<option id="responsavelOption-${usuario.codUsuario}"
													value="${usuario.codUsuario}">${usuario.nome}</option>
											</c:if>
										</c:forEach>
									</select> &nbsp;&nbsp;
									<!-- <input type="button" class="btn btn-secondary btn-sm " onclick="adicionaResponsavel()" value="Adicionar"/>-->
								</div>
								<br>
								<ul class="list-group col-md-8" id="listaResponsaveis">
									<c:forEach items="${selecao.responsaveis}" var="responsavel">
										<li class="list-group-item"><input type="hidden"
											name="codResponsaveis" value="${responsavel.codUsuario}"
											style="display: none;" /> ${responsavel.nome}
											<button type="button"
												class="btn btn-light btn-sm material-icons float-right"
												style="font-size: 15px;"
												onclick="removeResponsavel('${responsavel.nome}');">clear</button>
										</li>
									</c:forEach>
								</ul>
								<br>

							</div>
						</div>
						<br> <a
							href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}"
							id="enviar" class="btn btn-secondary"> Cancelar </a> <input
							type="button" class="btn btn-primary" value="Salvar"
							data-toggle="modal" data-target="#confirmarSelecao">

						<!-- Modal -->
						<div class="modal fade" id="confirmarSelecao" tabindex="-1"
							role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="modalLabel">Confirmar edição
											da seleção</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<p>Você deseja confirmar as atualizações realizadas na
											seleção?</p>
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
	<!-- Include JS file. -->
	<script type='text/javascript'
		src='https://cdnjs.cloudflare.com/ajax/libs/froala-editor/2.8.5/js/froala_editor.min.js'></script>
	<script>
		function habilitaCampoVagas() {
			if (!document.getElementById('isVagasLimitadasInput').checked) {
				document.getElementById('vagasRemuneradasInput').disabled = true;
				document.getElementById('vagasVoluntariasInput').disabled = true;
			} else {
				document.getElementById('vagasRemuneradasInput').disabled = false;
				document.getElementById('vagasVoluntariasInput').disabled = false;
			}
		}

		function habilitaEdicao(id) {
			var input = $("#" + id);
			if (document.getElementById(id).getAttribute('readonly')) {
				input.removeAttr('readonly');
			} else {
				input.attr('readonly', true);
			}
		}

		var listaNomeAnexo = [];
		var listaLinkAnexo = [];
		var numAnexo = 0;
		function adicionaAnexo() {
			var nomeAnexo = document.getElementById("nomeAnexoInput").value;
			var linkAnexo = document.getElementById("linkAnexoInput").value;
			if (nomeAnexo !== "" && linkAnexo !== "") {
				listaNomeAnexo[numAnexo] = nomeAnexo;
				listaLinkAnexo[numAnexo] = linkAnexo;
				numAnexo++;
			}
			document.getElementById("nomeAnexoInput").value = "";
			document.getElementById("linkAnexoInput").value = "";
			atualizaAnexo();
		}
		function atualizaAnexo() {
			var list = document.getElementById("listaAnexos");
			list.innerHTML = "";
			for (i = 0; i < listaNomeAnexo.length; i++) {
				if (listaNomeAnexo[i] !== "") {
					list.innerHTML += '<li class="list-group-item" ><input type="hidden" name="listaNomeAnexo" value="'+listaNomeAnexo[i]+'" style="display: none;"> <input type="hidden" name="listaLinkAnexo" value="'+listaLinkAnexo[i]+'" style="display: none;"> <a href="'+listaLinkAnexo[i]+'" target="_blank">'
							+ listaNomeAnexo[i]
							+ '</a><button type="button" class="btn btn-light btn-sm material-icons float-right" style="font-size: 15px;" onclick="removeAnexo(\''
							+ listaNomeAnexo[i] + '\')">clear</button></li>';
				}
			}
		}
		function removeAnexo(nome) {
			for (i = 0; i < listaNomeAnexo.length; i++) {
				if (listaNomeAnexo[i] === nome) {
					listaNomeAnexo[i] = "";
					listaLinkAnexo[i] = "";
				}
			}
			atualizaAnexo();
		}

		var listaNomeAditivo = [];
		var listaLinkAditivo = [];
		var numAditivo = 0;
		function adicionaAditivo() {
			var nomeAditivo = document.getElementById("nomeAditivoInput").value;
			var linkAditivo = document.getElementById("linkAditivoInput").value;
			if (nomeAditivo !== "" && linkAditivo !== "") {
				listaNomeAditivo[numAditivo] = nomeAditivo;
				listaLinkAditivo[numAditivo] = linkAditivo;
				numAditivo++;
			}
			document.getElementById("nomeAditivoInput").value = "";
			document.getElementById("linkAditivoInput").value = "";
			atualizaAditivo();
		}
		function atualizaAditivo() {
			var list = document.getElementById("listaAditivos");
			list.innerHTML = "";
			for (i = 0; i < listaNomeAditivo.length; i++) {
				if (listaNomeAditivo[i] !== "") {
					list.innerHTML += '<li class="list-group-item" ><input type="hidden" name="listaNomeAditivo" value="'+listaNomeAditivo[i]+'" style="display: none;"> <input type="hidden" name="listaLinkAditivo" value="'+listaLinkAditivo[i]+'" style="display: none;"> <a href="'+listaLinkAditivo[i]+'" target="_blank">'
							+ listaNomeAditivo[i]
							+ '</a><button type="button" class="btn btn-light btn-sm material-icons float-right" style="font-size: 15px;" onclick="removeAditivo(\''
							+ listaNomeAditivo[i] + '\')">clear</button></li>';
				}
			}
		}
		function removeAditivo(nome) {
			for (i = 0; i < listaNomeAditivo.length; i++) {
				if (listaNomeAditivo[i] === nome) {
					listaNomeAditivo[i] = "";
					listaLinkAditivo[i] = "";
				}
			}
			atualizaAditivo();
		}
	</script>
	<script>
		var listaCodResponsaveis = $
		{
			codResponsaveis
		};
		var codigoResponsavelSelecao = listaCodResponsaveis

		var listaNomeResponsaveis = $
		{
			nomeResponsaveis
		};
		var nomeResponsaveis = [];//${responsaveis};
		var codResponsaveis = [];
		var numResponsaveis = 0;

		$(document)
				.ready(
						function() {
							for (i = 0; i < listaCodResponsaveis.length; i++) {
								document.getElementById("responsavelOption-"
										+ listaCodResponsaveis[i] + "").disabled = "disabled";
								codResponsaveis.push(listaCodResponsaveis[i]);
								nomeResponsaveis.push(listaNomeResponsaveis[i]);
								numResponsaveis++;
							}
						});
		function adicionaResponsavel() {
			var codResponsavel = document.getElementById("responsavelInput").value;
			//var nomeResponsavel = $("#responsavelOption-"+codResponsavel+"").text();
			var temp = document.getElementById("responsavelInput");
			var nomeResponsavel = temp.options[temp.selectedIndex].text;

			if (nomeResponsavel !== "") {
				codResponsaveis.push(Number(codResponsavel));
				nomeResponsaveis.push(nomeResponsavel);
				document.getElementById("responsavelOption-" + codResponsavel).disabled = "disabled";
				numResponsaveis++;
			}
			document.getElementById("responsavelInput").value = "";
			atualizaResponsaveis();

		}
		function atualizaResponsaveis() {
			var list = document.getElementById("listaResponsaveis");
			list.innerHTML = "";
			for (i = 0; i < codResponsaveis.length; i++) {
				if (codResponsaveis[i] !== "") {
					list.innerHTML += '<li class="list-group-item"><input type="hidden" name="codResponsaveis" value="'+codResponsaveis[i]+'" style="display: none;"> '
							+ nomeResponsaveis[i]
							+ '<button type="button" class="btn btn-light btn-sm material-icons float-right" style="font-size: 15px;" onclick="removeResponsavel(\''
							+ codResponsaveis[i] + '\')">clear</button></li>';
				}
			}
		}
		function removeResponsavel(codResponsavel) {
			codResponsavel = Number(codResponsavel);
			for (i = 0; i < codResponsaveis.length; i++) {
				if (codResponsaveis[i] === codResponsavel) {
					document.getElementById("responsavelOption-"
							+ codResponsavel + "").disabled = "";
					codResponsaveis[i] = "";
				}
			}
			atualizaResponsaveis();
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
					descricao_div.setAttribute('style', 'border-color: red');
					frame.contentDocument.getElementsByTagName('body')[0].className = 'empty';
				} else {
					descricao_div.setAttribute('style', '');
				}

			}
		}
	</script>
</body>
</html>
