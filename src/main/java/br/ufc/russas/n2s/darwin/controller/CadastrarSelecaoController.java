/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.controller;

import br.ufc.russas.n2s.darwin.beans.ArquivoBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.dao.DocumentacaoDAOImpl;
import br.ufc.russas.n2s.darwin.model.FileManipulation;
import br.ufc.russas.n2s.darwin.service.SelecaoServiceIfc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Wallison Carlos
 */
@Controller("cadastrarSelecaoController")
@RequestMapping("/cadastrarSelecao")
public class CadastrarSelecaoController { 

    private SelecaoServiceIfc selecaoServiceIfc;

    public SelecaoServiceIfc getSelecaoServiceIfc() {
        return selecaoServiceIfc;
    }

    @Autowired(required = true)
    public void setSelecaoServiceIfc(@Qualifier("selecaoServiceIfc")SelecaoServiceIfc selecaoServiceIfc){
        this.selecaoServiceIfc = selecaoServiceIfc;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String getIndex() {
        return "cadastrar-selecao";
    }

<<<<<<< HEAD
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String adiciona(@ModelAttribute("selecao") @Valid SelecaoBeans selecao, BindingResult result, @RequestParam("file") MultipartFile file) throws IOException {

        if (result.hasErrors()) {
=======
    public @ResponseBody String adiciona(@Valid SelecaoBeans selecao, BindingResult result, @RequestParam("file") MultipartFile file) throws IOException {


        if (result.hasErrors() ) {

>>>>>>> 7732332fcbb97d74092b09f8ce1e7aa29ce650cc
            System.out.println("\n\nde novo!!!\n\n");
            //response.sendRedirect("");
            return "cadastrar-selecao";
        }

        selecao.getResponsaveis().add(new UsuarioBeans());
        System.out.println(file);
        if (!file.isEmpty()) {

            ArquivoBeans edital = new ArquivoBeans();
            
            File convFile = new File(file.getOriginalFilename());
            convFile.createNewFile(); 
            FileOutputStream fos = new FileOutputStream(convFile); 
            fos.write(file.getBytes());
            fos.close(); 
            
            edital.setTitulo("Edital para ".concat(selecao.getTitulo()));
            edital.setData(LocalDateTime.now());
            edital.setArquivo(convFile);
            
            //edital.setArquivo(FileManipulation.getFileStream(file.getInputStream(), ".pdf"));

            //System.out.println("\n\neu aqui1!!!\n\n");

            selecao.setEdital(edital);
            System.out.println(selecao.getEdital().getTitulo());
            System.out.println(selecao.getEdital().getArquivo());

            System.out.println(selecao.getCodSelecao());
            System.out.println(selecao.getDescricao());
            System.out.println(selecao.getTitulo());
            System.out.println(selecao.getCategoria());
        }
<<<<<<< HEAD

        
        
        System.out.println("\n\neu aqui!!!\n\n");

        selecao = this.getSelecaoServiceIfc().adicionaSelecao(selecao);

        return "forward:/selecao/"+selecao.getCodSelecao();
=======
        


        selecao = this.getSelecaoServiceIfc().adicionaSelecao(selecao);
<<<<<<< HEAD
        
        return "forward:/selecao/"+selecao.getCodSelecao();
=======
        return "selecao/"+selecao.getCodSelecao();
>>>>>>> 6abed7d0cae1afc687f53983ea0c2bbcc0a7ae3c
>>>>>>> 7732332fcbb97d74092b09f8ce1e7aa29ce650cc
    }
    
   /* 
    @PostMapping("/uploadAction")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        File out = new File("outputfile.pdf");
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(out);

            // Writes bytes from the specified byte array to this file output stream 
            fos.write(file.getBytes());
            System.out.println("Upload and writing output file ok");
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while writing file " + ioe);
        } finally {
            // close the streams using close method
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }

            //storageService.store(file);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");

            return "redirect:/Darwin";
        }
    }
    */
    
    
}
