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
                        <a class="breadcrumb-item" href="${etapa.codEtapa}">${etapa.titulo}</a>
                        <a class="breadcrumb-item active" href="#">Resultado</a>
                    </nav>
                <c:if test="${not empty mensagem}">
                    <div class="alert alert-${status} alert-dismissible fade show" role="alert">
                        ${mensagem}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
                <div class="row" style="padding-left: 15px;">
                    <h1>Resultado</h1><!-- <a href="${etapa.codEtapa}/imprimir">Imprimir</a>  -->
                    <a href="${etapa.codEtapa}/imprimir" class="btn btn-primary btn-sm" style="height: 33px;margin-left: 30px;margin-top: -4px;" >
                        <span>Gerar PDF</span>
                    </a>
                    
                   </div>
                    <br>
                    <table class="table table-responsive">
                        <thead>
                            <tr>
                            	<th scope="col">CPF</th>
                                <th scope="col">Candidato</th>
                                <th scope="col">Situação</th>
	                            <c:if test="${(etapa.criterioDeAvaliacao.criterio == 1)}">
	                                <th scope="col">Nota</th>
	                            </c:if>
	                            	<th scope="col">Resultado</th>
	                            <c:if test="${(not empty etapa.recurso) and (isResponsavel)}">
	                            	<th scope="col">Recurso</th>
	                            </c:if>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="participante" items="${participantesEtapa}">
                            <c:set var = "avaliacaoParticipante" value = "${null}"/>
                            <tr>
                            	<td>${fn:replace(participante[0].candidato.CPF, fn:substring(participante[0].candidato.CPF,3,9),"******")}</td>
                                <td>${participante[0].candidato.nome}</td>
                                                <td>${participante[1]}</td>
                                                <c:if test="${(etapa.criterioDeAvaliacao.criterio == 1)}">
                                             	<td>${participante[3]}</td>
                                             	<td>${participante[2]}</td>
                                                </c:if>
                                                <c:if test="${(etapa.criterioDeAvaliacao.criterio == 2)}">
                                                    <td>${participante[2]}</td>
                                                </c:if>
                                                <c:if test="${(etapa.criterioDeAvaliacao.criterio == 3)}">
                                                    <td>${participante[2]}</td>
                                                </c:if>
                                                <c:if test="${not empty etapa.recurso and isResponsavel}">
							                    	<td> <a href="${pageContext.request.contextPath}/recursoEtapa/${etapa.codEtapa}/${participante[0].codParticipante}" class="btn btn-primary btn-sm" style="height: 33px;margin-left: 30px;margin-top: -4px;" >
							                        	<span>Recurso</span>
							                    	</a> </td>
							                    </c:if>
                                <c:if test="${empty etapa.avaliacoes}">
                                    <td>Aguardando avaliação</td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
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