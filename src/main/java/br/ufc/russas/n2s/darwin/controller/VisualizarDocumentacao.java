/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.controller;

import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.dao.EtapaDAOIfc;
import br.ufc.russas.n2s.darwin.model.Arquivo;
import br.ufc.russas.n2s.darwin.model.Documentacao;
import br.ufc.russas.n2s.darwin.model.Etapa;
import br.ufc.russas.n2s.darwin.service.EtapaServiceIfc;
import br.ufc.russas.n2s.darwin.service.SelecaoServiceIfc;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Wallison Carlos
 */
@WebServlet(name = "VisualizarDocumentacao", urlPatterns = {"/visualizarDocumentacao"})
public class VisualizarDocumentacao extends HttpServlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SelecaoServiceIfc selecaoServiceIfc;
    private EtapaServiceIfc etapaServiceIfc;
    private EtapaDAOIfc etapaDaoIfc;
    
    @Autowired(required = true)
    public void setSelecaoServiceIfc(@Qualifier("selecaoServiceIfc")SelecaoServiceIfc selecaoServiceIfc){
        this.selecaoServiceIfc = selecaoServiceIfc;
    }
    
    @Autowired(required = true)
    public void setEtapaServiceIfc(@Qualifier("etapaServiceIfc")EtapaServiceIfc etapaServiceIfc){
        this.etapaServiceIfc = etapaServiceIfc;
    }
    
    @Autowired(required = true)
    public void setEtapaDaoIfc(@Qualifier("etapaDAOIfc")EtapaDAOIfc etapaDaoIfc){
        this.etapaDaoIfc = etapaDaoIfc;
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long codSelecao = Long.parseLong(request.getParameter("selecao"));
        long codEtapa = Long.parseLong(request.getParameter("etapa"));
        long codArquivo = Long.parseLong(request.getParameter("documento"));
        SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
        Etapa etapa = (Etapa) etapaServiceIfc.getEtapa(codEtapa).toBusiness();
        etapa = etapaDaoIfc.getEtapa(etapa);
        
        File file = null;
        for (Documentacao documentacao: etapa.getDocumentacoes()) {
        	for(Arquivo documento:documentacao.getDocumentos()) {
	        	if (documento.getCodArquivo()==codArquivo) {
	        		file = documento.getArquivo();
	        	}
        	}
        }
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=" + selecao.getEdital().getTitulo()+".pdf");
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
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    @Override
    public void init(ServletConfig arg0) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
        arg0.getServletContext());		
    }
}
