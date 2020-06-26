package br.ufc.russas.n2s.darwin.controller;

import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.PeriodoBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.EnumCriterioDeAvaliacao;
import br.ufc.russas.n2s.darwin.model.EnumEstadoEtapa;
import br.ufc.russas.n2s.darwin.service.EtapaServiceIfc;
import br.ufc.russas.n2s.darwin.service.UsuarioServiceIfc;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author N2S-PC03
 */

@Controller("etapaController")
@RequestMapping(value = "/etapa")
public class EtapaController {
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
    public String getIndex(@PathVariable long codEtapa, Model model){
        EtapaBeans etapa  = etapaServiceIfc.getEtapa(codEtapa);
        model.addAttribute("etapa", etapa);
        List<UsuarioBeans> avaliadores = this.getUsuarioServiceIfc().listaTodosUsuarios();
        model.addAttribute("avaliadores", avaliadores);
        return "/editar-etapa";
    }
    
    @RequestMapping(value = "/{codEtapa}",method = RequestMethod.POST)
    public String atualiza(@PathVariable long codSelecao, EtapaBeans etapa, BindingResult result, Model model, HttpServletRequest request) {
        SelecaoBeans selecao = (SelecaoBeans) request.getSession().getAttribute("selecao");
        model.addAttribute("selecao", selecao);
        String[] codAvaliadores = request.getParameterValues("codAvaliadores");
        int criterio = Integer.parseInt(request.getParameter("criterioDeAvaliacao"));
        if (criterio == 1) {
            etapa.setCriterioDeAvaliacao(EnumCriterioDeAvaliacao.NOTA);
        } else if(criterio == 2) {
            etapa.setCriterioDeAvaliacao(EnumCriterioDeAvaliacao.APROVACAO);
        } else if(criterio == 3) {
            etapa.setCriterioDeAvaliacao(EnumCriterioDeAvaliacao.DEFERIMENTO);
        }
        etapa.setEstado(EnumEstadoEtapa.ESPERA);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        etapa.setPeriodo(new PeriodoBeans(0, LocalDate.parse(request.getParameter("dataInicio"), formatter), LocalDate.parse(request.getParameter("dataTermino"), formatter)));
        ArrayList<UsuarioBeans> avaliadores = new ArrayList<>();
        if(codAvaliadores != null){
            for(String cod : codAvaliadores){
                UsuarioBeans u = this.getUsuarioServiceIfc().getUsuario(Long.parseLong(cod),0);
                if(u != null){
                    avaliadores.add(u);
                }
            }
        }
        etapa.setAvaliadores(avaliadores);
        try {
            this.getEtapaServiceIfc().atualizaEtapa(selecao, etapa);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(EtapaController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return "editar-etapa";

    }


}
