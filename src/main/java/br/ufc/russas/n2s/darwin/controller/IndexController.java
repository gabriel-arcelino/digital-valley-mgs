package br.ufc.russas.n2s.darwin.controller;

import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.EnumEstadoSelecao;
import br.ufc.russas.n2s.darwin.model.EnumPermissao;
import br.ufc.russas.n2s.darwin.model.Selecao;
import br.ufc.russas.n2s.darwin.service.SelecaoServiceIfc;
import util.Constantes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Wallison Carlos
 */
@Controller("indexController")
@RequestMapping("/")
public class IndexController { 

    private SelecaoServiceIfc selecaoServiceIfc;
    
    public SelecaoServiceIfc getSelecaoServiceIfc(){
        return selecaoServiceIfc;
    }
    
    @Autowired(required = true)
    public void setSelecaoServiceIfc(@Qualifier("selecaoServiceIfc")SelecaoServiceIfc selecaoServiceIfc){
        this.selecaoServiceIfc = selecaoServiceIfc;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String getIndex(@RequestParam(required=false, defaultValue = "0") int pag, Model model, HttpServletRequest request) {
    	HttpSession session = request.getSession();
        UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
        List<SelecaoBeans> selecoes = null;
        Long qtdSelecoes;
        if(usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
        	selecoes = this.getSelecaoServiceIfc().listaSelecoes(true, null, null, ((pag - 1) * 5), 5);
        	qtdSelecoes = this.getSelecaoServiceIfc().getQuantidade(true, null, null);
        }else {
        	selecoes = this.getSelecaoServiceIfc().listaSelecoes(false, null, null, ((pag - 1) * 5), 5);
        	qtdSelecoes = this.getSelecaoServiceIfc().getQuantidade(false, null, null);
        }
        
        HashMap<Long, EtapaBeans> etapasAtuais = new  HashMap<>();
        for (SelecaoBeans s : selecoes) {
            etapasAtuais.put(s.getCodSelecao(), this.getSelecaoServiceIfc().getEtapaAtual(s));
        }
        
        model.addAttribute("categoria", "Início");
        model.addAttribute("estado", "início");
        model.addAttribute("selecoes", selecoes); 
        model.addAttribute("etapasAtuais", etapasAtuais);
        model.addAttribute("qtdSelecoes", qtdSelecoes);
        return "index";
    }
    
    @RequestMapping(value="/categoria/{categoria}", method = RequestMethod.GET)
    public String getIndex(@RequestParam(required=false, defaultValue = "0") int pag, Model model, @PathVariable String categoria, HttpServletRequest request) {
        Selecao selecao = new Selecao();
        selecao.setCategoria(categoria.replace("_", " "));
        HttpSession session = request.getSession();
        UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
        List<SelecaoBeans> selecoes = null;
        Long qtdSelecoes;
        if(usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
        	selecoes = this.getSelecaoServiceIfc().listaSelecoes(true, selecao.getCategoria(), null, ((pag - 1) * 5), 5);
        	qtdSelecoes = this.getSelecaoServiceIfc().getQuantidade(true, selecao.getCategoria(), null);
        }else {
        	selecoes = this.getSelecaoServiceIfc().listaSelecoes(false, selecao.getCategoria(), null, ((pag - 1) * 5), 5);
        	qtdSelecoes = this.getSelecaoServiceIfc().getQuantidade(false, selecao.getCategoria(), null);
        }
        
        HashMap<Long, EtapaBeans> etapasAtuais = new  HashMap<>();
        for (SelecaoBeans s : selecoes) {
            etapasAtuais.put(s.getCodSelecao(), this.getSelecaoServiceIfc().getEtapaAtual(s));
        }
        
        model.addAttribute("categoria", "categoria/" + categoria);
       
        model.addAttribute("selecoes", selecoes);
        model.addAttribute("etapasAtuais", etapasAtuais); 
        model.addAttribute("qtdSelecoes", qtdSelecoes);
        return "index";
    }
    
    @RequestMapping(value="/pesquisa", method = {RequestMethod.POST, RequestMethod.GET})
    public String getPorTitulo(	@RequestParam(required=false, defaultValue = "0") int pag,
    							@RequestParam(required=false, defaultValue = " ") String titulo,
    							Model model, HttpServletRequest request) {
    	
    	this.getSelecaoServiceIfc().setUsuario((UsuarioBeans)request.getSession().getAttribute("usuarioDarwin"));
    	HttpSession session = request.getSession();
        UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
        List<SelecaoBeans> selecoes = null;
        Long qtdSelecoes;
        if(usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
        	selecoes = this.getSelecaoServiceIfc().buscarSelecoesPorNome(true, titulo, ((pag - 1) * 5), 5) ;
        	qtdSelecoes = this.getSelecaoServiceIfc().getQuantidadePorNome(true, titulo);
        }else {
        	selecoes = this.getSelecaoServiceIfc().buscarSelecoesPorNome(false, titulo, ((pag - 1) * 5), 5) ;
        	qtdSelecoes = this.getSelecaoServiceIfc().getQuantidadePorNome(false, titulo);
        }
        
        HashMap<Long, EtapaBeans> etapasAtuais = new  HashMap<>();
        for (SelecaoBeans s : selecoes) {
            etapasAtuais.put(s.getCodSelecao(), this.getSelecaoServiceIfc().getEtapaAtual(s));
        }
        
        model.addAttribute("categoria", "pesquisa/?titulo=" + titulo + "&");
        model.addAttribute("qtdSelecoes", qtdSelecoes);
        model.addAttribute("selecoes", selecoes);
        model.addAttribute("etapasAtuais", etapasAtuais); 
        return "index";
    }
    
    
    @RequestMapping(value = "/estado/{estado}", method = RequestMethod.GET)
    public String getEstados(@RequestParam(required=false, defaultValue = "0") int pag, Model model, @PathVariable String estado, HttpServletRequest request){
        EnumEstadoSelecao e = null;

        if(estado.equals("emedicao")){
        	e = EnumEstadoSelecao.EMEDICAO;
        	model.addAttribute("categoria", "estado/" + estado);
        } else if (estado.equals("aberta")){
            e = EnumEstadoSelecao.ABERTA;
            model.addAttribute("categoria", "estado/" + estado);
        } else if( estado.equals("andamento")) {
            e = EnumEstadoSelecao.ANDAMENTO;
            model.addAttribute("categoria", "estado/" + estado);
        } else if (estado.equals("finalizada")) {
            e = EnumEstadoSelecao.FINALIZADA;
            model.addAttribute("categoria", "estado/" + estado);
        } else {
        	 e = EnumEstadoSelecao.ESPERA;
             model.addAttribute("categoria", "estado/espera");
        }
        HttpSession session = request.getSession();
        UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
        List<SelecaoBeans> selecoes = null;
        Long qtdSelecoes;
        if(usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
        	selecoes = this.getSelecaoServiceIfc().listaSelecoes(true, null, e, ((pag - 1) * 5), 5);
        	qtdSelecoes = this.getSelecaoServiceIfc().getQuantidade(true, null, e);
        }else {
        	selecoes = this.getSelecaoServiceIfc().listaSelecoes(false, null, e, ((pag - 1) * 5), 5);
        	qtdSelecoes = this.getSelecaoServiceIfc().getQuantidade(false, null, e);
        }
        
        HashMap<Long, EtapaBeans> etapasAtuais = new  HashMap<>();
        for (SelecaoBeans s : selecoes) {
            etapasAtuais.put(s.getCodSelecao(), this.getSelecaoServiceIfc().getEtapaAtual(s));
        }
        
        model.addAttribute("estado", "estado");
        model.addAttribute("selecoes", selecoes);
        model.addAttribute("etapasAtuais", etapasAtuais);
        model.addAttribute("qtdSelecoes", qtdSelecoes);
        return "index";
    }
    
    @RequestMapping(value="/minhas_Selecoes", method = RequestMethod.GET)
    public String getMinhasSelecoes(@RequestParam(required=false, defaultValue = "1") int pag, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
        
        Long qtdSelecoes = 0l;
        
        List<SelecaoBeans> selecoes;
        if (usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
        	selecoes = this.getSelecaoServiceIfc().listaSelecoes(true, null, null, ((pag - 1) * 5), 5);
        	qtdSelecoes = this.getSelecaoServiceIfc().getQuantidade(true, null, null);
        } else {
        	if(pag <= 0){pag = 1;}
        	selecoes = this.getSelecaoServiceIfc().buscarSelecoesAssociada(usuario, ((pag - 1) * 5), 5);
        	qtdSelecoes = this.getSelecaoServiceIfc().getQuantidadeAssociada(usuario);
        }
        HashMap<Long, EtapaBeans> etapasAtuais = new  HashMap<>();
        for (SelecaoBeans s : selecoes) {
            etapasAtuais.put(s.getCodSelecao(), this.getSelecaoServiceIfc().getEtapaAtual(s));
        }
        
        model.addAttribute("qtdSelecoes", qtdSelecoes);
        model.addAttribute("categoria", "minhas_Selecoes");
        model.addAttribute("selecoes", selecoes);
        model.addAttribute("agora", LocalDate.now());
        LocalDate.now().toEpochDay();
        model.addAttribute("etapasAtuais", etapasAtuais);
        return "index";
    }
    
    @RequestMapping(value = "/sair", method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	HttpSession session = request.getSession();
        session.removeAttribute("usuario");
        response.sendRedirect(Constantes.getAppGuardiaoUrl()+"/logout");
    }
           
}
