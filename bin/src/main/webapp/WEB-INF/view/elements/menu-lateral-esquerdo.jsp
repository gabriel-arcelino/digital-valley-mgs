<%-- 
    Document   : menu-lateral
    Created on : 05/10/2017, 11:50:30
    Author     : Alex Felipe
--%>
<div class="col-8 col-md-3 col-xl-2 bd-sidebar" style="margin-top: 5px;">
    <div class="btn-group-vertical d-flex flex-column" role="group">
    <div class="card">
	<div class="card-header">Categorias</div>
	<div class="card-body p-1">
        <a href="${pageContext.request.contextPath}/categoria/Assistência_Estudantil" class="btn btn-light btn-sm text-left ${categoria == 'Assistência_Estudantil' ? 'active': ''}">Assistência Estudantil</a>
        <a href="${pageContext.request.contextPath}/categoria/Seleção_para_Discentes" class="btn btn-light btn-sm text-left ${categoria == 'Seleção_Para_Discentes' ? 'active': ''}">Seleções para Discentes</a>  
        <a href="${pageContext.request.contextPath}/categoria/Cargos_de_Docente" class="btn btn-light btn-sm text-left ${categoria == 'Cargos_de_Docente' ? 'active': ''}">Cargos de Docente</a>
        <a href="${pageContext.request.contextPath}/categoria/Cargos_de_Técnicos_Admin" class="btn btn-light btn-sm text-left ${categoria == 'Cargos_de_Técnicos_Admin' ? 'active': ''}">Cargos de Técnicos Admin.</a>
        <a href="${pageContext.request.contextPath}/categoria/Professores_Substitutos" class="btn btn-light btn-sm text-left ${categoria == 'Professores_Substitutos' ? 'active': ''}">Professores Substitutos</a>      
    </div>
    </div>
</div>
</div>

<!--<div class="card ml-3">
	<div class="card-header">Categorias</div>
	
		<div class="col-12 col-md-3 col-xl-2 bd-sidebar" style="margin-top: 5px;">
			<div class="btn-group-vertical d-flex flex-column" role="group">
				<a
					href="${pageContext.request.contextPath}/categoria/Assistência_Estudantil"
					class="btn btn-light btn-sm text-left ${categoria == 'Assistência_Estudantil' ? 'active': ''}">Assistência
					Estudantil</a> 
				<a
					href="${pageContext.request.contextPath}/categoria/Seleção_para_Discentes"
					class="btn btn-light btn-sm text-left ${categoria == 'Seleção_Para_Discentes' ? 'active': ''}">Seleções
					para Discentes</a> 
				<a
					href="${pageContext.request.contextPath}/categoria/Cargos_de_Docente"
					class="btn btn-light btn-sm text-left ${categoria == 'Cargos_de_Docente' ? 'active': ''}">Cargos
					de Docente</a> 
				<a
					href="${pageContext.request.contextPath}/categoria/Cargos_de_Técnicos_Admin"
					class="btn btn-light btn-sm text-left ${categoria == 'Cargos_de_Técnicos_Admin' ? 'active': ''}">Cargos
					de Técnicos Admin.</a> 
				<a
					href="${pageContext.request.contextPath}/categoria/Professores_Substitutos"
					class="btn btn-light btn-sm text-left ${categoria == 'Professores_Substitutos' ? 'active': ''}">Professores
					Substitutos
				</a>
			</div>
	
	</div>
</div>-->