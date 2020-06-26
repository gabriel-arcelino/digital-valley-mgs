package br.ufc.russas.n2s.darwin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import br.ufc.russas.n2s.darwin.beans.ArquivoBeans;
import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.Email;
import br.ufc.russas.n2s.darwin.model.EnumPermissao;
import br.ufc.russas.n2s.darwin.model.FileManipulation;
import br.ufc.russas.n2s.darwin.model.Log;
import br.ufc.russas.n2s.darwin.model.Selecao;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;
import br.ufc.russas.n2s.darwin.model.exception.AvaliadorException;
import br.ufc.russas.n2s.darwin.service.LogServiceIfc;
import br.ufc.russas.n2s.darwin.service.SelecaoServiceIfc;
import br.ufc.russas.n2s.darwin.service.UsuarioServiceIfc;
import util.Constantes;

/**
 *
 * @author Gilberto, Wallison
 */
@Controller("editarSelecaoController")
@RequestMapping("/editarSelecao")
public class EditarSelecaoController { 

    private SelecaoServiceIfc selecaoServiceIfc;
    private UsuarioServiceIfc usuarioServiceIfc;
    private LogServiceIfc logServiceIfc;
    
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

    @RequestMapping(value = "/{codSelecao}", method = RequestMethod.GET)
    public String getIndex(@PathVariable long codSelecao, Model model, HttpServletRequest request){
    	UsuarioBeans usuario = (UsuarioBeans) request.getSession().getAttribute("usuarioDarwin");
        SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
        if (selecao.getResponsaveis().contains(usuario) || usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
	        List<UsuarioBeans> usuarios = this.getUsuarioServiceIfc().listaUsuariosComPermissao(EnumPermissao.RESPONSAVEL);
	        List<UsuarioBeans> responsaveis = selecao.getResponsaveis();
	        
	        List<Long> codResponsaveis = Collections.synchronizedList(new ArrayList<>());
	        List<String> nomeResponsaveis = Collections.synchronizedList(new ArrayList<>());
	        
	        for (UsuarioBeans responsavel : selecao.getResponsaveis()) {
	        	codResponsaveis.add(responsavel.getCodUsuario());
	        	nomeResponsaveis.add(responsavel.getNome());
	        }
	        String json = new Gson().toJson(nomeResponsaveis);
	        model.addAttribute("codResponsaveis", codResponsaveis);
	        model.addAttribute("nomeResponsaveis", json);
	        model.addAttribute("selecao", selecao);
	        model.addAttribute("usuarios", usuarios);
	        model.addAttribute("responsaveis", responsaveis);
	        return "editar-selecao";
        } else {return "error/404";}
    }
    
    @RequestMapping(value = "/{codSelecao}", method = RequestMethod.POST)
    public String atualiza(@PathVariable long codSelecao, @ModelAttribute("selecao") @Valid SelecaoBeans selecao, BindingResult result, @RequestParam("file") String file, @RequestParam("categoria") String categoria, Model model, HttpServletResponse response, HttpServletRequest request) throws IOException, IllegalAccessException, EmailException {
        SelecaoBeans selecaoBeans = this.getSelecaoServiceIfc().getSelecao(codSelecao);
        HttpSession session = request.getSession();
        try{
            selecaoBeans.setTitulo(selecao.getTitulo());
            selecaoBeans.setDescricao(selecao.getDescricao());
            selecaoBeans.setDescricaoPreRequisitos(selecao.getDescricaoPreRequisitos());
            selecaoBeans.setAreaDeConcentracao(selecao.getAreaDeConcentracao());
            selecaoBeans.setCategoria(categoria);
            selecaoBeans.setVagasRemuneradas(selecao.getVagasRemuneradas());
            selecaoBeans.setVagasVoluntarias(selecao.getVagasVoluntarias());
            
            String[] nomeAnexos = request.getParameterValues("listaNomeAnexo");
            String[] nomeAditivos = request.getParameterValues("listaNomeAditivo");
            String[] linkAnexos = request.getParameterValues("listaLinkAnexo");
            String[] linkAditivos = request.getParameterValues("listaLinkAditivo");
            
            File dir = new File(Constantes.getDocumentsDir()+File.separator+"Selecao_"+selecao.getCodSelecao()+File.separator);
            dir.mkdir();
            
            if (file != null && !file.isEmpty()) {
            	ArquivoBeans edital = new ArquivoBeans();
                edital.setTitulo("Edital para ".concat(selecao.getTitulo()));
                File temp = File.createTempFile(Constantes.getDocumentsDir()+File.separator+file, ".pdf", dir);
                System.out.println(Constantes.getDocumentsDir()+File.separator+file);
                InputStream input = new URL(file).openStream();
                OutputStream output = new FileOutputStream(temp);
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = input.read(bytes)) != -1) {
                    output.write(bytes, 0, read);
                }
                
                selecaoBeans.getEdital().setArquivo(temp);
                selecaoBeans.getEdital().setData(LocalDateTime.now());
                output.close();
            }
            
            if (nomeAnexos != null && linkAnexos != null) {
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
                    selecaoBeans.getAnexos().add(anexo);
                    output.close();
                }
            }
            
            if (nomeAditivos != null && linkAditivos != null) {
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
                    selecaoBeans.getAditivos().add(aditivo);
                    output.close();
                }
            }

            String[] codResponsaveis = request.getParameterValues("codResponsaveis");
            ArrayList<UsuarioBeans> responsaveis = new ArrayList<>();
            if (codResponsaveis != null && codResponsaveis.length > 0) {
            	System.out.println(codResponsaveis.length);
                for (String cod : codResponsaveis) {
                	if (cod.contains("-")) {
                		cod = cod.substring(0,cod.indexOf("-"));
                	}
                    UsuarioBeans u = this.getUsuarioServiceIfc().getUsuario(Long.parseLong(cod),0);
                    if (u != null) {
                    	responsaveis.add(u);
                    }
                }
            } else {throw new IllegalAccessException("A seleção deve possuir no minimo um responsável.");}
            selecaoBeans.setResponsaveis(responsaveis);
            UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
            this.getSelecaoServiceIfc().setUsuario(usuario);
            selecaoBeans = this.getSelecaoServiceIfc().atualizaSelecao(selecaoBeans);
            session.setAttribute("selecao", selecaoBeans);
            this.getLogServiceIfc().adicionaLog(new Log(LocalDate.now(),(UsuarioDarwin)usuario.toBusiness(), (Selecao) selecaoBeans.toBusiness(), "O(A) usuario(a) "+ usuario.getNome()+" editou a seleção "+selecao.getTitulo()+" em "+LocalDate.now()+"."));
            session.setAttribute("mensagem", "Seleção atualizada com sucesso!");
            session.setAttribute("status", "success");
            return "redirect:/selecao/" + selecaoBeans.getCodSelecao();
        } catch (IOException | IllegalAccessException e) {
        	e.printStackTrace();
        	
        	session.setAttribute("mensagem", e.getMessage());
        	session.setAttribute("status", "warning");
            return ("redirect:/editarSelecao/"+codSelecao);
        } catch (Exception e) {
        	e.printStackTrace();
        	session.setAttribute("mensagem", e.getMessage());
        	session.setAttribute("status", "warning");
            return ("redirect:/editarSelecao/"+codSelecao);
        }
    }
    
    @RequestMapping(value = "/divulga/{codSelecao}", method = RequestMethod.GET)
    public String divulga(@PathVariable long codSelecao, Model model, HttpServletRequest request) {
        SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
        HttpSession session = request.getSession();
        UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
        if (selecao.getResponsaveis().contains(usuario) || usuario.getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
	        try {
	        	if (selecao.getInscricao().getPeriodo().getTermino().isBefore(LocalDate.now())) {
	        		session.setAttribute("mensagem", "Esta Seleção não pode ser divulgada! Verifique o periodo de inscrição.");
	                session.setAttribute("status", "warning");
	        		return "redirect:/selecao/" + selecao.getCodSelecao();
	        	} else {
	        		List<List<String>> etapasSemAvaliadores = new ArrayList<>();
	        		
	        		if (selecao.getInscricao().getAvaliadores().size() == 0) {
	        			etapasSemAvaliadores.add(Arrays.asList(selecao.getInscricao().getTitulo(), Long.toString(selecao.getInscricao().getCodEtapa())));
	        		}
	        		for (EtapaBeans eb : selecao.getEtapas()) {
	        			if (eb.getAvaliadores().size() <= 0) {
	        				etapasSemAvaliadores.add(Arrays.asList(eb.getTitulo(), Long.toString(eb.getCodEtapa())));
	        			} 	
	        		}
	        		
	        		if(!etapasSemAvaliadores.isEmpty()) {
	        			String msg = "Não foi possível divulgar a seleção, verifique a(s) etapa(s): ";
	        			for(int i = 0; i < etapasSemAvaliadores.size(); i++) {
	        				if(i != etapasSemAvaliadores.size()-1) {
	        					msg += etapasSemAvaliadores.get(i).get(0) + ", ";
	        				} else {
	        					msg += etapasSemAvaliadores.get(i).get(0) + ".";
	        				}
	        			}
	        			
	        			throw new AvaliadorException(msg, etapasSemAvaliadores);
	        		}
	        		
		            selecaoServiceIfc.setUsuario(usuario);
		            selecao.setDivulgada(true);
		            selecao = selecaoServiceIfc.atualizaSelecao(selecao);
		            Thread sendEmail = new Thread(new Email(usuarioServiceIfc.listaTodosUsuarios(), "Nova seleção divulgada!", "Seleção "+selecao.getTitulo(), selecao.getDescricao()));
		            sendEmail.start();
		            Set<UsuarioBeans> avaliadoresSet = Collections.synchronizedSet(new HashSet<UsuarioBeans>());
		            for (int i =0;i < selecao.getEtapas().size();i++) {
		            	EtapaBeans e = selecao.getEtapas().get(i);
		            	avaliadoresSet.addAll(e.getAvaliadores());
		            }
		            List<Thread> threadsEmail = Collections.synchronizedList(new ArrayList<Thread>());
		            for(Iterator<UsuarioBeans> iter = avaliadoresSet.iterator(); iter.hasNext();) {
		            	UsuarioBeans u = iter.next();
	                  	if (u.isRecebeEmail()) {
	                  		String etapasAvaliador = "";
	                  		for (int i = 0;i < selecao.getEtapas().size();i++) {
	                  			etapasAvaliador += "<b>"+selecao.getEtapas().get(i).getTitulo()+"</b><br />";
	                  		}
	                  		threadsEmail.add(new Thread(new Email(u, "Avaliador de Etapa!", "Avaliador de Etapa", "Você é avaliador de etapas da <b>Selção"+selecao.getTitulo()+"</b>:"+etapasAvaliador)));
	                  		threadsEmail.get(threadsEmail.size()-1).start();
	                  	}
		            }
		            this.getLogServiceIfc().adicionaLog(new Log(LocalDate.now(),(UsuarioDarwin)usuario.toBusiness(), (Selecao) selecao.toBusiness(), "O(A) usuario(a) "+ usuario.getNome()+" divulgou a seleção "+selecao.getTitulo()+" em "+LocalDate.now()+"."));
    	            session.setAttribute("selecao", selecao);
		            session.setAttribute("mensagem", "Seleção divulgada com sucesso!");
	                session.setAttribute("status", "success");
		            return "redirect:/selecao/" + selecao.getCodSelecao();
	        	}
	        } catch(IllegalAccessException e) {
	            session.setAttribute("mensagem", e.getMessage());
                session.setAttribute("status", "warning");
	            return "redirect:/selecao/" + selecao.getCodSelecao();
	        }
            catch(AvaliadorException e) {
	            session.setAttribute("mensagem", e.getMsg());
	            session.setAttribute("etapasComErro", e.getEtapasSemAvaliadores().stream().map(etapa -> etapa.get(1)).collect(Collectors.toList()));
                session.setAttribute("status", "warning");
	            return "redirect:/selecao/" + selecao.getCodSelecao();
	        } catch(Exception e) {
	            session.setAttribute("mensagem", e.getMessage());
                session.setAttribute("status", "warning");
	            return "redirect:/selecao/" + selecao.getCodSelecao();
	        }
        } else {return "error/404";}
    }

}
