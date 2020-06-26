package br.ufc.russas.n2s.darwin.controller.filter;

import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.EnumPermissao;
import br.ufc.russas.n2s.darwin.service.UsuarioServiceIfc;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Pessoa;
import model.Usuario;
import dao.DAOFactory;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import util.Constantes;

import util.Facade;
/**
 *
 * @author Wallison Carlos
 */
public class AutenticadoFiltro implements Filter {
        
    private UsuarioServiceIfc usuarioServiceIfc;

    public UsuarioServiceIfc getUsuarioServiceIfc() {
        return usuarioServiceIfc;
    }

    @Autowired(required = true)
    public void setUsuarioServiceIfc(@Qualifier("usuarioServiceIfc")UsuarioServiceIfc usuarioServiceIfc){
        this.usuarioServiceIfc = usuarioServiceIfc;
    }
    
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
            String path = ((HttpServletRequest) request).getServletPath();
            if (path.endsWith("/autentica")){
                    chain.doFilter(request, response);
            }else{
                    HttpSession session = ((HttpServletRequest) request).getSession();
                    if(request.getParameter("token") != null && request.getParameter("id") != null){
                            String token = request.getParameter("token");
                            int id = Integer.parseInt(request.getParameter("id"));
                            Pessoa user = Facade.buscarPessoaPorId(id);
                            if (token.equals(user.getUsuario().getTokenUsuario()) && id == user.getId() && !token.equals("null")) {
                                    session.setAttribute("usuario", user.getUsuario());
                                    UsuarioBeans u = new UsuarioBeans();
                                    if(this.getUsuarioServiceIfc().getUsuarioControleDeAcesso(user.getId()) == null){
                                        u.setCodUsuarioControleDeAcesso(user.getId());
                                        u.setNome(user.getNome());
                                        ArrayList<EnumPermissao> permissoes = new ArrayList<>();
                                        permissoes.add(EnumPermissao.PARTICIPANTE);
                                        
                                        if (user.getUsuario().getLogin().equalsIgnoreCase("admin")) {
                                        	permissoes.add(EnumPermissao.ADMINISTRADOR);
                                        	permissoes.add(EnumPermissao.RESPONSAVEL);
                                        }
                                        
                                        u.setPermissoes(permissoes);
                                        u.setEmail(user.getEmail());
                                        u.setCPF(user.getCpf());
                                        this.getUsuarioServiceIfc().adicionaUsuario(u);
                                    }
                                    session.setAttribute("usuarioDarwin", this.getUsuarioServiceIfc().getUsuarioControleDeAcesso(user.getId()));
                                    chain.doFilter(request, response);
                            }else {
                                ((HttpServletResponse) response).sendRedirect(Constantes.getAppUrl());
                            }
                    }else if(session.getAttribute("usuarioDarwin") != null && session.getAttribute("usuario") != null && 
                    		DAOFactory.criarUsuarioDAO().buscarTokenTemp(((Usuario)session.getAttribute("usuario")).getPessoa().getId())!=null 
                    		&& ((Usuario)session.getAttribute("usuario")).getTokenUsuario().equals(DAOFactory.criarUsuarioDAO().buscarTokenTemp(((Usuario)session.getAttribute("usuario")).getPessoa().getId()))){
                    	chain.doFilter(request, response);
                    }else {
                            ((HttpServletResponse) response).sendRedirect(Constantes.getAppGuardiaoUrl());
                    }
            }				
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
            arg0.getServletContext());		
	}


}