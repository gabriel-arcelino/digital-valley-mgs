<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8" />
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/font-awesome/css/font-awesome.min.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/design.css" />
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
				<nav class="breadcrumb"> 
				<span class="breadcrumb-item">Você está em:</span>
				<a class="breadcrumb-item"
					href="${pageContext.request.contextPath}/">Início
				</a>
				<a class="breadcrumb-item" 
				href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}">${selecao.titulo}
				</a>
				<a class="breadcrumb-item" 
				href="${pageContext.request.contextPath}/editarEtapa/${selecao.codSelecao}/${etapa.codEtapa}">${etapa.titulo}
				</a>
				<a class="breadcrumb-item active"
					href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}/${etapa.codEtapa}/pendencias">Pendências
				</a> 
				</nav>
				<c:set var="mensagem" value="${sessionScope.mensagem}"></c:set>
				<c:if test="${not empty mensagem}">
					<div class="alert alert-${status} alert-dismissible fade show"
						role="alert">
						${mensagem}
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<c:set scope="session" var="mensagem" value=""></c:set>
					<c:set scope="session" var="status" value=""></c:set>
				</c:if>
				<div class="row text-center">
					<div class="col-sm-12">
						<h1>Pendências de Avaliação</h1>
					</div>
					<div class="col-sm-12">
						<h3>Estes são os avalidores alocados para esta etapa seguido pelo número de pendências de avaliação</h3>
					</div>
					
					<div class="col-sm-12">
						<ul class="list-group">
							<c:forEach var="avaliador" items="${etapa.avaliadores}">
								<li class="list-group-item d-flex justify-content-between align-items-center">
								    ${avaliador.nome}
								    <c:set var="pendente" value="0"></c:set>
								    <c:forEach var="avaliacao" items="${etapa.avaliacoes}">
								    		<c:if test="${avaliacao.avaliador.codUsuario eq avaliador.codUsuario}">
								    			<c:set var="pendente" value="${pendente + 1}"></c:set>
								    		</c:if>
								    </c:forEach>
								    <c:if test="${fn:length(etapa.participantes) - pendente eq 0}">
								     	<span class= "badge badge-pill badge-success">
								    		${fn:length(etapa.participantes) - pendente}
								    	</span>
								    </c:if>
								    <c:if test="${fn:length(etapa.participantes) - pendente ne 0}">
								     	<span class= "badge badge-pill badge-danger">
								    		${fn:length(etapa.participantes) - pendente}
								    	</span>
								    </c:if>
							 	 </li>
							</c:forEach>
						</ul>
					</div>
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
</body>
</html>
