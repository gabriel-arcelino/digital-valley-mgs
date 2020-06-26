/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.model;

/**
 *
 * @author Wallison Carlos
 */
public class EtapaProxy extends Etapa{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -3448530419349667202L;
	
	private UsuarioDarwin usuario;
    
    public EtapaProxy(UsuarioDarwin usuario) {
        setUsuario(usuario);
    }

    public UsuarioDarwin getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDarwin usuario) {
        if (usuario != null) {
            this.usuario = usuario;
        } else {
            throw new NullPointerException("Usuário do Darwin não pode ser vazio!");
        }
    }
    
    public void avalia(Etapa etapa, Avaliacao avaliacao) throws IllegalAccessException{
        if (this.getUsuario().getPermissoes().contains(EnumPermissao.AVALIADOR) && etapa.isAvaliador(usuario)) {
            etapa.avalia(avaliacao);
        } else {
            throw new IllegalAccessException("Você não é um avaliador da etapa: ".concat(etapa.getTitulo()));
        }
    }
    
    public void adicionaAvaliador(Selecao selecao, Etapa etapa, UsuarioDarwin usuario) throws IllegalAccessException {
       System.out.println("ENTROU NO METODO");
    	if ((getUsuario().getPermissoes().contains(EnumPermissao.RESPONSAVEL) && selecao.isResponsavel(getUsuario())) || (getUsuario().getPermissoes().contains(EnumPermissao.ADMINISTRADOR))) {
           etapa.adicionaAvaliador(usuario);
           System.out.println("ENTROU NO IF");
       } else {
           throw new IllegalAccessException("Você não é o responsável por esta seleção: <b> ".concat(selecao.getTitulo()).concat("</b>"));
       }
    }
     
    public void removeAvaliador(Selecao selecao, Etapa etapa, UsuarioDarwin usuario) throws IllegalAccessException{
        if ((getUsuario().getPermissoes().contains(EnumPermissao.RESPONSAVEL) && selecao.isResponsavel(getUsuario())) || (getUsuario().getPermissoes().contains(EnumPermissao.ADMINISTRADOR))) {
            etapa.removeAvaliador(usuario);
        } else {
            throw new IllegalAccessException("Você não é o responsável por esta seleção: <b> ".concat(selecao.getTitulo()).concat("</b>"));
        }
    }
    
}
