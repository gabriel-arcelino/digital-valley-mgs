<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="n2s">
        <link rel="icon" href="favicon.ico">
        <title>Darwin - Sistema de Gerenciamento de Seleções</title>


        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/design.css" />
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.css" />
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.standalone.css" />
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css" integrity="sha384-5sAR7xN1Nv6T6+dT2mhtzEpVJvfS3NScPQTrOxhwjIuvcA67KV2R5Jz6kr4abQsz" crossorigin="anonymous">
        
    </head>
    <body>
        <c:import url="elements/menu-superior.jsp" charEncoding="UTF-8"></c:import>
        <div class="container-fluid">
            <div class="row row-offcanvas row-offcanvas-right">
                <c:import url="elements/menu-lateral-esquerdo.jsp" charEncoding="UTF-8"></c:import>
                <div class="col-sm-8">
                    <nav class="breadcrumb">
                        <span class="breadcrumb-item">Você está em:</span> 
                        <a class="breadcrumb-item" href="${pageContext.request.contextPath}/">Início</a>
                        <a class="breadcrumb-item" href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}">${selecao.titulo}</a>
                        <a class="breadcrumb-item" href="${pageContext.request.contextPath}/resultadoEtapa/${etapa.codEtapa}">${etapa.titulo}</a>
                        <a class="breadcrumb-item active" href="#">Recurso</a>
                    </nav>
                <c:if test="${not empty mensagem}">
                    <div class="alert alert-${status} alert-dismissible fade show" role="alert">
                        ${mensagem}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>  
                    <h1>Recurso da etapa - ${etapa.titulo}</h1>
                    <br>
                    <table class="table table-responsive">
                        <thead>
                            <tr>
                            	<th scope="col">CPF</th>
                                <th scope="col">Candidato</th>
                                <th scope="col">Avaliador</th>
                            	<c:if test="${(etapa.criterioDeAvaliacao.criterio == 1)}">
                                	<th scope="col">Nota</th>
                                </c:if>
                                <c:if test="${not (etapa.criterioDeAvaliacao.criterio == 1)}">
                                	<th scope="col">Situação</th>
                                </c:if>
                                <th scope="col">Ação</th>
                            </tr>
                        </thead>
                        <tbody>
                           
                            <tr>
                                <td>${participanteEtapa.candidato.CPF}</td>
                                <td>${participanteEtapa.candidato.nome}</td>
                                <c:if test="${(etapa.criterioDeAvaliacao.criterio == 1)}">
	                                <c:forEach var="avaliacao" items="${avaliacoes}">
	                                	<form method="POST" action="${pageContext.request.contextPath}/avaliar/recurso/etapa/${etapa.codEtapa}/avaliacao/${avaliacao.codAvaliacao}" accept-charset="UTF-8">                              	
		                                	<td scope="col" style="text-align:center">${avaliacao.avaliador.nome}</td>
		                                	<td class="text-center" scope="col"><input class="form-control" style="width: 150px"type="number" step="0.01" value="${avaliacao.nota}" name="nota"/> </td>
	                                		<td scope="col" ><input type="submit" class="btn btn-primary btn-sm" value="Salvar"/> </td>
	                                	</form>
	                                </c:forEach>
                                </c:if>
                                <c:if test="${(etapa.criterioDeAvaliacao.criterio == 2)}">
                                <c:forEach var="avaliacao" items="${avaliacoes}"> 
                                	<td scope="col" style="text-align:center">${avaliacao.avaliador.nome}</td>
                                	<form method="POST" action="${pageContext.request.contextPath}/avaliar/recurso/etapa/${etapa.codEtapa}/avaliacao/${avaliacao.codAvaliacao}" accept-charset="UTF-8">                              	
		                                <td class="text-center" scope="col">
		                                <select class="form-control" style="width: 150px"type="number" value="${avaliacao.nota}" name="estado">
	                                		<option ${(avaliacao.aprovado == true) ? "selected='selected'" : ""} value="1">Aprovado</option>
	                                		<option ${(avaliacao.aprovado == false) ? "selected='selected'" : ""} value="0">Reprovado</option>
                                		</select> 
                                		</td>
                                		<td scope="col" ><input type="submit" class="btn btn-primary btn-sm" value="Salvar"/> </td>
                                	</form>
                               	</c:forEach>
                                </c:if>
                                <c:if test="${(etapa.criterioDeAvaliacao.criterio == 3)}">
                                <c:forEach var="avaliacao" items="${avaliacoes}"> 
                                	<td scope="col" style="text-align:center">${avaliacao.avaliador.nome}</td>
                                	<form method="POST" action="${pageContext.request.contextPath}/avaliar/recurso/etapa/${etapa.codEtapa}/avaliacao/${avaliacao.codAvaliacao}" accept-charset="UTF-8">                              	
		                               <td class="text-center" scope="col">
		                               <select class="form-control" style="width: 150px"type="number" value="${avaliacao.nota}" name="estado">
	                                		<option ${(avaliacao.aprovado == true) ? "selected='selected'" : ""} value="1">Deferido</option>
	                                		<option ${(avaliacao.aprovado == false) ? "selected='selected'" : ""} value="0">Indeferido</option>
                                		</select>
                                		</td>
                                		<td scope="col" ><input type="submit" class="btn btn-primary btn-sm" value="Salvar"/> </td>
                                	</form>
                               	</c:forEach>
                                </c:if>
                                </tr>
                      
                        </tbody>
                    </table>
                    <br>
		                   
                </div>
            </div>
        </div>
        <c:import url="elements/rodape.jsp" charEncoding="UTF-8"></c:import>  
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/resources/js/script.js" ></script>
    </body>
</html>