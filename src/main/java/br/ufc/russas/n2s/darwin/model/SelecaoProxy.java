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
public class SelecaoProxy extends Selecao{
    
    private UsuarioDarwin usuario;
    
    public SelecaoProxy(UsuarioDarwin usuario) {
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
    
    public Etapa adicionaEtapa(Selecao selecao, Etapa etapa) throws IllegalAccessException {
        if ((this.getUsuario().getPermissoes().contains(EnumPermissao.RESPONSAVEL) && selecao.getResponsaveis().contains(this.getUsuario())) || this.getUsuario().getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
            return selecao.adicionaEtapa(etapa);
        } else {
            throw new IllegalAccessException("Você não é o responsável por esta seleção: <b> ".concat(selecao.getTitulo()).concat("</b>"));
        }
    }
    
    public Etapa atualizaEtapa(Selecao selecao, Etapa etapa) throws IllegalAccessException {
        if ((this.getUsuario().getPermissoes().contains(EnumPermissao.RESPONSAVEL) && selecao.getResponsaveis().contains(this.getUsuario())) || this.getUsuario().getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
            return selecao.atualizaEtapa(etapa);
        } else {
            throw new IllegalAccessException("Você não é o responsável por esta seleção: <b> ".concat(selecao.getTitulo()).concat("</b>"));
        }
    }
     
    public Selecao adicionaSelecao(Selecao selecao) throws IllegalAccessException {
        if (this.getUsuario().getPermissoes().contains(EnumPermissao.RESPONSAVEL) || this.getUsuario().getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
            return selecao.adicionaSelecao();
        } else {
            throw new IllegalAccessException("Você não é o responsável por esta seleção: <b> ".concat(selecao.getTitulo()).concat("</b>"));
        }
    }
    
    public Selecao atualizaSelecao(Selecao selecao) throws IllegalAccessException {
        if (this.getUsuario().getPermissoes().contains(EnumPermissao.RESPONSAVEL) || selecao.isResponsavel(getUsuario()) || this.getUsuario().getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
            return selecao.atualizaSelecao();
        } else {
            throw new IllegalAccessException("Você não é o responsável por esta seleção: <b> ".concat(selecao.getTitulo()).concat("</b>"));
        }
    }
    
    public void adicionaResponsavel(Selecao selecao, UsuarioDarwin responsavel) throws IllegalAccessException {
        if ((getUsuario().getPermissoes().contains(EnumPermissao.RESPONSAVEL) && selecao.isResponsavel(getUsuario())) || this.getUsuario().getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
            selecao.adicionaResponsavel(responsavel);
        } else {
            throw new IllegalAccessException("Você não é o responsável por esta seleção: <b> ".concat(selecao.getTitulo()).concat("</b>"));
        }
    }
    
    public void removeResponsavel(Selecao selecao, UsuarioDarwin responsavel) throws IllegalAccessException {
        if ((getUsuario().getPermissoes().contains(EnumPermissao.RESPONSAVEL) && selecao.isResponsavel(getUsuario())) || this.getUsuario().getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
            selecao.removeResponsavel(responsavel);
        } else {
            throw new IllegalAccessException("Você não é o responsável por esta seleção: <b> ".concat(selecao.getTitulo()).concat("</b>"));
        }
    }
    
    public void removeEtapa(Selecao selecao, Etapa etapa) throws IllegalAccessException {
        if ((getUsuario().getPermissoes().contains(EnumPermissao.RESPONSAVEL) && selecao.isResponsavel(getUsuario())) || this.getUsuario().getPermissoes().contains(EnumPermissao.ADMINISTRADOR)) {
            selecao.removeEtapa(etapa);
        } else {
            throw new IllegalAccessException("Você não é o responsável por esta seleção: <b> ".concat(selecao.getTitulo()).concat("</b>"));
        }
    }
    
}