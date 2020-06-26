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
                        <a class="breadcrumb-item" href="${selecao.codSelecao}">${selecao.titulo}</a>
                        <a class="breadcrumb-item" href="${etapa.codEtapa}">${etapa.titulo}</a>
                        <a class="breadcrumb-item active" href="#">Avaliar participantes</a>
                    </nav>
                <c:if test="${not empty mensagem}">
                    <div class="alert alert-${status} alert-dismissible fade show" role="alert">
                        ${mensagem}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>                           
                    <h1>Avaliar participantes</h1>
                    <br>
                    <div class="form-group">
                        <form method="POST" action="avaliar" accept-charset="UTF-8" enctype="multipart/form-data" id="needs-validation" novalidate>
                            <table class="table table-responsive">
                                <thead>
                                    <tr>
                                        <th scope="col">Candidato</th>
                                        <th scope="col">Avaliação</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="participante" items="${participantesEtapa}">
                                    <tr>
                                        <td>${participante.nome}</td>
                                    </tr>
                                    <tr>
                                    <c:if test="${(etapa.criterioDeAvaliacao.criterio == 1)}">
                                    <input type="number" name="nota" class="form-control col-sm-2 disabled" id="notaInput" value="0" min="0" max="10">
                                    </c:if>
                                    <c:if test="${(etapa.criterioDeAvaliacao.criterio == 2)}">
                                        <div class="form-check form-check-inline">
                                            <label class="form-check-label">
                                                <input class="form-check-input" type="radio" name="aprovadoOpcao" id="aprovadoOpcao" value="Aprovado"> Aprovado
                                            </label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <label class="form-check-label">
                                                <input class="form-check-input" type="radio" name="reprovadoOpcao" id="reprovadoOpcao" value="Reprovado"> Reprovado
                                            </label>
                                        </div>
                                    </c:if>
                                    <c:if test="${(etapa.criterioDeAvaliacao.criterio == 3)}">
                                        <div class="form-check form-check-inline">
                                            <label class="form-check-label">
                                                <input class="form-check-input" type="radio" name="deferidoOpcao" id="deferidoOpcao" value="Deferido"> Deferido
                                            </label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <label class="form-check-label">
                                                <input class="form-check-input" type="radio" name="indeferidoOpcao" id="indeferidoOpcao" value="Indeferido"> Indeferido
                                            </label>
                                        </div>
                                    </c:if>
                                    </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <a href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}" class="btn btn-secondary btn-sm">
                                Cancelar
                            </a>
                            <input type="submit" class="btn btn-primary btn-sm" value="Salvar">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>