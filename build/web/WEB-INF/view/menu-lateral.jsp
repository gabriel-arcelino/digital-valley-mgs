<%-- 
    Document   : menu-lateral
    Created on : 03/10/2017, 09:50:47
    Author     : Alex Felipe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<ul class="nav nav-stracked text-left">
    <li class="active"><a href="index.html"><span>Início</span></a></li>
    <li><a href="minhasSelecoes.html">Minhas seleções</a></li>
    <li>
        <a data-toggle="collapse" href="#collapse1"><span class="col-sm-12" style="margin-left: -15px;">Assistência estudantil</span> <span class="glyphicon glyphicon-chevron-down dropdown-chevron text-right"></span></a>
        <ul id="collapse1" class="panel-collapse collapse">
            <li><a href="index.html?categoria='Bolsa de Iniciação Acadêmica'">Bolsa de Iniciação Acadêmica</a></li>
            <li><a href="index.html?categoria='Auxílio Moradia'">Auxílio Moradia</a></li>
            <li><a href="index.html?categoria='Auxílio Emergêncial'">Auxílio Emergêncial</a></li>
            <li><a href="index.html?categoria='Isenção do RU'">Isenção do RU</a></li>
        </ul>
    </li>
    <li>
        <a data-toggle="collapse" href="#collapse2"><span class="col-sm-12" style="margin-left: -15px;">Concursos para servidores</span> <span class="glyphicon glyphicon-chevron-down dropdown-chevron text-right"></span></a>
        <ul id="collapse2" class="panel-collapse collapse">
            <li><a href="index.html?categoria='Seleção para Professor Substituto'">Seleção para Professor Substituto</a></li>
            <li><a href="index.html?categoria='Concurso para Professor Efetivo'">Concurso para Professor Efetivo</a></li>
            <li><a href="index.html?categoria='Concurso para Técnicos-Administrativos'">Concurso para Técnicos-Administrativos</a></li>
        </ul>
    </li>
    <li><a href="#">Bolsas</a></li>
    <li><a href="#">Outras seleções</a></li>
    <li><a href="#">Notícias</a></li>
</ul>