package br.ufc.russas.n2s.darwin.controller;

import javax.servlet.http.HttpServletRequest;
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
import br.ufc.russas.n2s.darwin.beans.ParticipanteBeans;
import br.ufc.russas.n2s.darwin.model.EnumPermissao;
import br.ufc.russas.n2s.darwin.service.EtapaServiceIfc;
import br.ufc.russas.n2s.darwin.service.ParticipanteServiceIfc;
import br.ufc.russas.n2s.darwin.service.UsuarioServiceIfc;

@Controller("recursoEtapaController")
@RequestMapping("/recursoEtapa")
public class RecursoEtapaController {

	private EtapaServiceIfc etapaServiceIfc;
    private UsuarioServiceIfc usuarioServiceIfc;
    private ParticipanteServiceIfc participanteServiceIfc;
    
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
    public ParticipanteServiceIfc getParticipanteServiceIfc() {
        return participanteServiceIfc;
    }
    @Autowired(required = true)
    public void setParticipanteServiceIfc(@Qualifier("participanteServiceIfc")ParticipanteServiceIfc participanteServiceIfc) {
        this.participanteServiceIfc = participanteServiceIfc;
    }
    
    @RequestMapping(value = "/{codEtapa}/{codParticipante}", method = RequestMethod.GET)
    public String getRecursoDaEtapa(@PathVariable long codEtapa, @PathVariable long codParticipante, Model model, HttpServletRequest request){
		HttpSession session = request.getSession();
        UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
    	if (usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR) || usuario.getPermissoes().contains(EnumPermissao.RESPONSAVEL)) {
	        ParticipanteBeans participante = participanteServiceIfc.getParticipante(codParticipante);
	        EtapaBeans etapa  = etapaServiceIfc.getEtapa(codEtapa);
	        model.addAttribute("avaliacoes", etapaServiceIfc.getAvaliacoesParticipante(participante, etapa.getCodEtapa()));
	        model.addAttribute("participanteEtapa", participante);
	        model.addAttribute("etapa", etapa);
	        SelecaoBeans selecao = etapaServiceIfc.getSelecao(etapa);
	        if (selecao.getResponsaveis().contains(usuario) || usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
	        	model.addAttribute("isResponsavel", true);
	        }
	        return "/recursoEtapa";
    	} else {
    		return "error/404";
    	}
    }
	
	
}
