/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.controller;

import br.ufc.russas.n2s.darwin.beans.ArquivoBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.service.SelecaoServiceIfc;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.io.File;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Wallison Carlos
 */
@WebServlet(name = "VisualizarEdital", urlPatterns = {"/visualizarArquivo"})
public class VisualizarEdital extends HttpServlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SelecaoServiceIfc selecaoServiceIfc;
    
    @Autowired(required = true)
    public void setSelecaoServiceIfc(@Qualifier("selecaoServiceIfc")SelecaoServiceIfc selecaoServiceIfc){
        this.selecaoServiceIfc = selecaoServiceIfc;
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
        SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
        File file = null;
        String tipo = request.getParameter("tipo");
        HttpSession session = request.getSession();
        UsuarioBeans usuario = (UsuarioBeans) session.getAttribute("usuarioDarwin");
        if (tipo != null) {
            if (tipo.equals("edital")) {
                file = selecao.getEdital().getArquivo();
            } else if (tipo.equals("anexo")) {
                if (request.getParameter("anexo") != null) {
                    long codAnexo = Long.parseLong(request.getParameter("anexo"));
                    List<ArquivoBeans> anexos = selecao.getAnexos();
                    for (ArquivoBeans anexo : anexos) {
                        if (anexo.getCodArquivo() == codAnexo) {
                            file = anexo.getArquivo();
                            break;
                        }
                    }
                } else {
                    response.sendRedirect("/404");
                }
            } else if (tipo.equals("aditivo")) {
                if (request.getParameter("aditivo") != null) {
                    long codAditivo = Long.parseLong(request.getParameter("aditivo"));
                    List<ArquivoBeans> aditivos = selecao.getAditivos();
                    for (ArquivoBeans aditivo : aditivos) {
                        if (aditivo.getCodArquivo() == codAditivo) {
                            file = aditivo.getArquivo();
                            break;
                        }
                    }
                } else {
                    response.sendRedirect("/404");
                }
            } else if (tipo.equals("dccumentacao")) {
                 if (selecao.getResponsaveis().contains(usuario)) {
                 
                 }
            } else {
                response.sendRedirect("/404");
            }
        } else {
            response.sendRedirect("/404");
        }
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=" + selecao.getEdital().getTitulo()+".pdf");
        if (file != null) {
        	response.setContentLength((int) file.length());
        } else {
        	response.setContentLength(0);
        }
        if (file != null) {
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
    }

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
