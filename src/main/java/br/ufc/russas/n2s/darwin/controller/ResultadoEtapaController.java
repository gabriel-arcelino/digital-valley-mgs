package br.ufc.russas.n2s.darwin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

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

import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.EnumPermissao;
import br.ufc.russas.n2s.darwin.service.EtapaServiceIfc;
import br.ufc.russas.n2s.darwin.service.UsuarioServiceIfc;
import br.ufc.russas.n2s.darwin.util.Facade;

@Controller("resultadoEtapaController")
@RequestMapping("/resultadoEtapa")
public class ResultadoEtapaController {
	private EtapaServiceIfc etapaServiceIfc;
    private UsuarioServiceIfc usuarioServiceIfc;
    
    public EtapaServiceIfc getEtapaServiceIfc() {
        return etapaServiceIfc;
    }
    @Autowired(required = true)
    public void setEtapaServiceIfc(@Qualifier("etapaServiceIfc")EtapaServiceIfc etapaServiceIfc) {
        this.etapaServiceIfc = etapaServiceIfc;
    }
    
    public UsuarioServiceIfc getUsuarioServiceIfc() {
        return usuarioServiceIfc;
    }
    @Autowired(required = true)
    public void setUsuarioServiceIfc(@Qualifier("usuarioServiceIfc")UsuarioServiceIfc usuarioServiceIfc) {
        this.usuarioServiceIfc = usuarioServiceIfc;
    }
	@RequestMapping(value = "/{codEtapa}", method = RequestMethod.GET)
    public String resultadoDaEtapa(@PathVariable long codEtapa, Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
        UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
        EtapaBeans etapa  = etapaServiceIfc.getEtapa(codEtapa);
        SelecaoBeans selecao = etapaServiceIfc.getSelecao(etapa);
        if (etapa.isDivulgadoResultado() || etapa.getAvaliadores().contains(usuario) || selecao.getResponsaveis().contains(usuario) || usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
	        model.addAttribute("participantesEtapa", etapaServiceIfc.getResultado(etapa));
	        model.addAttribute("etapa", etapa);
	        if (selecao.getResponsaveis().contains(usuario) || usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
	        	model.addAttribute("isResponsavel", true);
	        }
	        return "/resultadoEtapa";
        } else {
        	return "error/404";
        }
    }
	@RequestMapping(value = "/{codEtapa}/imprimir", method = RequestMethod.GET)
    public String imprimiresultadoDaEtapa(@PathVariable long codEtapa, Model model, HttpServletRequest request, HttpServletResponse response) {
        EtapaBeans etapa  = etapaServiceIfc.getEtapa(codEtapa);
        try {
	        String caminho = Facade.gerarPDFDosResultados(etapa, etapaServiceIfc.getResultado(etapa), ((SelecaoBeans)request.getSession().getAttribute("selecao")));
	        File file = new File(caminho);
	        response.setContentType("application/pdf");
	        response.addHeader("Content-Disposition", "attachment; filename=" + file.getName()+".pdf");
	        response.setContentLength((int) file.length());
	        FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
	        OutputStream responseOutputStream = response.getOutputStream();
	        int bytes;
	        while ((bytes = fileInputStream.read()) != -1) {
	                responseOutputStream.write(bytes);
	        }
	        fileInputStream.close();
	        responseOutputStream.flush();
	        responseOutputStream.close();
	        response.flushBuffer();
	        
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        	model.addAttribute("mensagem", "Arqivo não pode ser gerado!");
            model.addAttribute("status", "danger");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("mensagem", e.getMessage());
            model.addAttribute("status", "danger");
		}
        
        model.addAttribute("participantesEtapa", etapaServiceIfc.getResultado(etapa));
        model.addAttribute("etapa", etapa);
        model.addAttribute("mensagem", "Resultado gerado com sucesso!");
        model.addAttribute("status", "success");
        return "/resultadoEtapa";
    }
	
}
