package br.ufc.russas.n2s.darwin.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufc.russas.n2s.darwin.beans.ArquivoBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.EnumEstadoSelecao;
import br.ufc.russas.n2s.darwin.model.EnumPermissao;
import br.ufc.russas.n2s.darwin.model.FileManipulation;
import br.ufc.russas.n2s.darwin.model.Log;
import br.ufc.russas.n2s.darwin.model.Selecao;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;
import br.ufc.russas.n2s.darwin.service.LogServiceIfc;
import br.ufc.russas.n2s.darwin.service.SelecaoServiceIfc;
import br.ufc.russas.n2s.darwin.service.UsuarioServiceIfc;
import util.Constantes;
/**
 *
 * @author Wallison Carlos, Gilberto Lima
 */
@Controller("cadastrarSelecaoController")
@RequestMapping("/cadastrarSelecao")
public class CadastrarSelecaoController { 

    private SelecaoServiceIfc selecaoServiceIfc;
    private UsuarioServiceIfc usuarioServiceIfc;
    private LogServiceIfc logServiceIfc;
    
    List<UsuarioBeans> responsaveis = new ArrayList<UsuarioBeans>();
    
    public SelecaoServiceIfc getSelecaoServiceIfc() {
        return selecaoServiceIfc;
    }

    @Autowired(required = true)
    public void setSelecaoServiceIfc(@Qualifier("selecaoServiceIfc")SelecaoServiceIfc selecaoServiceIfc){
        this.selecaoServiceIfc = selecaoServiceIfc;
    }

    public UsuarioServiceIfc getUsuarioServiceIfc() {
        return usuarioServiceIfc;
    }
    @Autowired(required = true)
    public void setUsuarioServiceIfc(@Qualifier("usuarioServiceIfc")UsuarioServiceIfc usuarioServiceIfc) {
        this.usuarioServiceIfc = usuarioServiceIfc;
    }
    
    public LogServiceIfc getLogServiceIfc() {
    	return logServiceIfc;
    }
    @Autowired
    public void setLogServiceIfc(@Qualifier("logServiceIfc")LogServiceIfc logServiceIfc) {
    	this.logServiceIfc = logServiceIfc;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String getIndex(Model model, HttpServletRequest request) {
    	UsuarioBeans usuario = (UsuarioBeans) request.getSession().getAttribute("usuarioDarwin");
    	if (usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR) || usuario.getPermissoes().contains(EnumPermissao.RESPONSAVEL)) {
    		responsaveis = usuarioServiceIfc.listaUsuariosComPermissao(EnumPermissao.RESPONSAVEL);
            model.addAttribute("responsaveis", responsaveis);
            return "cadastrar-selecao";
    	} else {return "error/404";}
    }
    
   @RequestMapping(method = RequestMethod.POST)
    public String adiciona(@ModelAttribute("selecao") @Valid SelecaoBeans selecao, BindingResult result, @RequestParam("file") String file, Model model, HttpServletResponse response, HttpServletRequest request) throws IOException, IllegalAccessException {
        HttpSession session = request.getSession();
        UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
        this.selecaoServiceIfc.setUsuario(usuario);
        String[] codResponsaveis = request.getParameterValues("codResponsaveis");
              
        
        String[] nomeAnexos = request.getParameterValues("listaNomeAnexo");
        String[] linkAnexos = request.getParameterValues("listaLinkAnexo");
        String[] nomeAditivos = request.getParameterValues("listaNomeAditivo");
        String[] linkAditivos = request.getParameterValues("listaLinkAditivo");
        
        ArrayList<UsuarioBeans> responsaveis = new ArrayList<>();
        if (codResponsaveis != null) {
            for (String cod : codResponsaveis) {
                UsuarioBeans u = this.getUsuarioServiceIfc().getUsuario(Long.parseLong(cod),0);
                if (u != null) {
                    responsaveis.add(u);
                }
            }
        }
        selecao.setEstado(EnumEstadoSelecao.EMEDICAO);
        selecao = this.getSelecaoServiceIfc().adicionaSelecao(selecao);
        try {
        	File dir = new File(Constantes.getDocumentsDir()+File.separator+"Selecao_"+selecao.getCodSelecao()+File.separator);
            dir.mkdir();
            if (file != null && !file.isEmpty()) { // para o edital
                ArquivoBeans edital = new ArquivoBeans();
                edital.setTitulo("Edital para ".concat(selecao.getTitulo()));
                File temp = File.createTempFile(Constantes.getDocumentsDir()+File.separator+file, ".pdf", dir);
                InputStream input = new URL(file).openStream();
                OutputStream output = new FileOutputStream(temp);
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = input.read(bytes)) != -1) {
                    output.write(bytes, 0, read);
                }
                edital.setArquivo(temp);
                edital.setData(LocalDateTime.now());
                selecao.setEdital(edital);
                output.close();
            }
            if (nomeAnexos != null && linkAnexos != null) { // para anexos
                ArrayList<ArquivoBeans> anexos = new ArrayList<>();
                for (int i=0; i < nomeAnexos.length; i++) {
                    ArquivoBeans anexo = new ArquivoBeans();
                    anexo.setTitulo(nomeAnexos[i]);
                    File temp = File.createTempFile(Constantes.getDocumentsDir()+File.separator+nomeAnexos[i], ".pdf", dir);
                    InputStream input = FileManipulation.getStreamFromURL(linkAnexos[i]);
                    OutputStream output = new FileOutputStream(temp);
                    int read = 0;
                    byte[] bytes = new byte[1024];
                    while ((read = input.read(bytes)) != -1) {
                        output.write(bytes, 0, read);
                    }
                    anexo.setArquivo(temp);
                    anexo.setData(LocalDateTime.now());
                    anexos.add(anexo);
                    output.close();
                }
                selecao.setAnexos(anexos);
            }
            if (nomeAditivos != null && linkAditivos != null) {  // para aditivos
                ArrayList<ArquivoBeans> aditivos = new ArrayList<>();
                for (int i=0; i < nomeAditivos.length; i++) {
                    ArquivoBeans aditivo = new ArquivoBeans();
                    aditivo.setTitulo(nomeAditivos[i]);
                    File temp = File.createTempFile(Constantes.getDocumentsDir()+File.separator+nomeAditivos[i], ".pdf", dir);
                    InputStream input = FileManipulation.getStreamFromURL(linkAditivos[i]);
                    OutputStream output = new FileOutputStream(temp);
                    int read = 0;
                    byte[] bytes = new byte[1024];
                    while ((read = input.read(bytes)) != -1) {
                        output.write(bytes, 0, read);
                    }
                    aditivo.setArquivo(temp);
                    aditivo.setData(LocalDateTime.now());
                    aditivos.add(aditivo);
                    output.close();
                }
                selecao.setAditivos(aditivos);
            }
           
            if(!usuario.getPermissoes().contains(EnumPermissao.RESPONSAVEL)){
                usuario.getPermissoes().add(EnumPermissao.RESPONSAVEL);
            }
            selecao.setResponsaveis(responsaveis);
            selecao.getResponsaveis().add(usuario);
            selecao = this.getSelecaoServiceIfc().atualizaSelecao(selecao);
            this.getLogServiceIfc().adicionaLog(new Log(LocalDate.now(),(UsuarioDarwin)usuario.toBusiness(), (Selecao) selecao.toBusiness(), "O(A) usuario(a) "+ usuario.getNome()+" criou a seleção "+selecao.getTitulo()+" em "+LocalDate.now()+"."));
            session.setAttribute("mensagem", "Seleção cadastrada com sucesso!");
            session.setAttribute("status", "success");
            return ("redirect:selecao/" + selecao.getCodSelecao());
        } catch (FileNotFoundException e) {
            model.addAttribute("novaSelecao", selecao);
            model.addAttribute("responsaveis", responsaveis);
            model.addAttribute("mensagem", "Verifique se o caminho do arquivo "+e.getMessage()+" está correto!");
            model.addAttribute("status", "danger");
            return ("cadastrar-selecao");
        }  catch (IllegalArgumentException | NullPointerException | IllegalAccessException e) {
            e.printStackTrace();
            model.addAttribute("novaSelecao", selecao);
            model.addAttribute("responsaveis", responsaveis);
            model.addAttribute("mensagem", e.getMessage());
            model.addAttribute("status", "danger");
            return ("cadastrar-selecao");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("novaSelecao", selecao);
            model.addAttribute("responsaveis", responsaveis);
            model.addAttribute("mensagem", e.getMessage());
            model.addAttribute("status", "danger");
            return ("cadastrar-selecao");
        }
    }
}
