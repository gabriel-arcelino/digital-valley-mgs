<%-- 
    Document   : menu-superior
    Created on : 06/10/2017, 10:29:03
    Author     : Alex Felipe
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page import="java.util.ArrayList"%>
<%@page import="br.ufc.russas.n2s.darwin.beans.UsuarioBeans"%>
<%@page import="util.Constantes"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="<%=Constantes.getAppUrl() %>/">
        <img src="${pageContext.request.contextPath}/resources/img/logoDarwin.png" width="30" height="30" class="d-inline-block align-top" alt="Logo do módulo Darwin">
        Darwin
    </a>  
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <c:set var="permissoes" value="${sessionScope.usuarioDarwin.permissoes}"></c:set>
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/">Início</a>
            <a class="nav-item nav-link" href="<%=Constantes.getAppGuardiaoUrl()%>">Guardião</a>
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/minhas_Selecoes">Minhas seleções</a>
            <c:if test="${fn:contains(permissoes, 'RESPONSAVEL') or fn:contains(permissoes, 'ADMINISTRADOR')}">
            <div class="btn-group">
                <a href="" class="nav-item nav-link " data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Administração
                </a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/cadastrarSelecao">Cadastrar Seleção</a>
                    
                    <c:if test="${fn:contains(permissoes, 'ADMINISTRADOR')}">
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/permissoes">Gerenciar permissões</a>
                    </c:if>
                </div>
            </div>
            </c:if>
        </div>
    </div>
    <div class="btn-group dropleft" style="margin-right:5%;">
		<button type="button" class="btn btn-link dropdown-toggle" style="color: #fff;margin-right: 1%;width: auto;text-align: center;" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		  ${sessionScope.usuarioDarwin.nome}
		</button>
		<div class="dropdown-menu">
		<c:if test="${sessionScope.usuarioDarwin.recebeEmail}">
	     <a class="dropdown-item" href="${pageContext.request.contextPath}/usuario/recebeEmail">Deixar de receber notificações</a>
	     </c:if>
	     <c:if test="${not sessionScope.usuarioDarwin.recebeEmail}">
	     <a class="dropdown-item" href="${pageContext.request.contextPath}/usuario/recebeEmail">Receber notificações</a>
	     </c:if>
		  <a class="dropdown-item" href="${pageContext.request.contextPath}/sair">Sair</a>
	</div>
</div>
</nav>         