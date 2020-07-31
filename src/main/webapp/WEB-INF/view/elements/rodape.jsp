<%-- 
    Document   : rodape
    Created on : 06/10/2017, 10:35:03
    Author     : Alex Felipe
--%>
<%@page import="java.time.LocalDate"%>
<!--  adicionou margin-botton -3px ou 0rem e o getYear() -->
<nav class="navbar navbar-light bg-light" style="bottom: 0px;">
    <a class="navbar-brand" href="#">Darwin</a>
    <p style="margin-bottom: -3px">© Núcleo de Soluções em Software (N2S), <%=LocalDate.now().getYear()%>.</p>
</nav>