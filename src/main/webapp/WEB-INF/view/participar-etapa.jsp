<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="util.Constantes" %>
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
                	<a class="breadcrumb-item active" href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}">${etapa.titulo}</a>
                </nav>
                <c:set var="mensagem" value="${sessionScope.mensagem}"></c:set>
                <c:if test="${not empty mensagem}">
                    <div class="alert alert-${status} alert-dismissible fade show" role="alert">
                        ${mensagem}
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <c:set scope="session" var="mensagem" value=""></c:set>
                    <c:set scope="session" var="status" value=""></c:set>
                </c:if>               
                <h1>Enviar documentação</h1>
                
                <br>
                <c:if test="${empty etapa.documentacaoExigida}">
                <p>Atenção: esta seleção não solicita nenhuma documentação para ser enviada! Por favor, apenas confirme sua inscrição.</p>
                <br>
                </c:if>
                <c:if test="${not empty etapa.documentacaoExigida}">
                <h2> Documentação Obrigatória</h2>
                <p>Atenção: Os campos abaixo (*) são de preenchimento obrigatório</p>
                </c:if>
                <div class="form-group">
                    <form method="POST" action="" enctype="multipart/form-data" id="formID">
                        <c:set var = "i" value = "${0}"/>
                    <c:forEach var="documento" items="${etapa.documentacaoExigida}">
                        <input type="hidden" value="${etapa.codEtapa}" name="etapa">
                        <label for="${documento}Input">${documento}</label>
                        <input type="file" onchange="tamanho(this)" name="arquivos" class="form-control" id="arquivoInput" aria-describedby="${documento}Help" accept="application/pdf" required>
                        <input type="hidden" name="nomeDocumento" value="${documento}" class="form-control">
                        <small id="tituloHelp" class="form-text text-muted">Tipo de arquivo .PDF</small>
                        <br>
                        <c:set var = "i" value = "${i + 1}"/>
                    </c:forEach>
                    
                    <c:if test="${not empty etapa.documentacaoOpcional}">
                    	<hr>
		                <h2> Documentação Opcional</h2>
		                <p>Atenção: Os campos abaixo (*) NÃO são de preenchimento obrigatório</p>
	                </c:if>
	                 <c:forEach var="documentoOp" items="${etapa.documentacaoOpcional}">
                        <input type="hidden" value="${etapa.codEtapa}" name="etapa">
                        <label for="${documentoOp}Input">${documentoOp}</label>
                        <input type="file" onchange="tamanho(this)" name="arquivos" class="form-control" id="arquivoInput" aria-describedby="${documentoOp}Help" accept="application/pdf">
                        <input type="hidden" name="nomeDocumento" value="${documentoOp}" class="form-control">
                        <small id="tituloHelp" class="form-text text-muted">Tipo de arquivo .PDF</small>
                        <br>
                    </c:forEach>
                    
                    
                        <a href="${pageContext.request.contextPath}/selecao/${selecao.codSelecao}" class="btn btn-secondary btn-sm">
                            Cancelar
                        </a>
                        <c:if test="${not isParticipante}">
	                        <c:if test="${empty etapa.documentacaoExigida}">
	                        	<input type="button" value="Confirmar" id="enviar" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#participarEtapa" >
	                        </c:if>
	                        <c:if test="${not empty etapa.documentacaoExigida}">
	                        	<input type="button" value="Enviar" id="enviar" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#participarEtapa" >
	                        </c:if>
	                    </c:if>
	                    <c:if test="${isParticipante}">
	                    	<button disabled class="btn btn-secondary btn-sm" role="button" aria-pressed="true"><i class="fas fa-user-check"></i> Inscrito</button>
	                    </c:if>
	                    
                        <!-- Modal -->
                        <div class="modal fade" id="participarEtapa" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modalLabel">Confirmar participação na etapa</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p>Você deseja confirmar sua participação na etapa?</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary btn-sm" data-dismiss="modal">Cancelar</button>
                                        <button type="submit" class="btn btn-primary btn-sm" id="send" onclick="$('#needs-validation').submit()">Confirmar</button>
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
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.js" ></script>
	<script>
		var formID = document.getElementById("formID");
		var send = $("#send");
	
		$(formID).submit(function(event){
		  if (formID.checkValidity()) {
		    send.attr('disabled', 'disabled');
		  }
		});
		
		function tamanho(elemento){
	        var tamanhoArquivo = elemento.files[0].size;
	        if(tamanhoArquivo > ${Constantes.getSizeFile()} ){
	      		alert("Tamanho do arquivo excede o permitido (" + ${Math.floor(Constantes.getSizeFile()/(1024*1024))} +"MB)!");
	      		elemento.value = '';
	        }
	    };
	   
	</script>
</body>
</html>