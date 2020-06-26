/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.service;

import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.EnumPermissao;
import java.util.List;

/**
 *
 * @author Wallison Carlos
 */
public interface UsuarioServiceIfc extends ServiceIfc{
    public UsuarioBeans adicionaUsuario(UsuarioBeans usuario);
    public UsuarioBeans atualizaUsuario(UsuarioBeans usuario);
    public void removeUsuario(UsuarioBeans usuario);
    public List<UsuarioBeans> listaTodosUsuarios();
    public List<UsuarioBeans> BuscaUsuariosPorNome(String nome);
    public UsuarioBeans getUsuario(long codUsuario, long codUsuarioControleDeAcesso);
    public UsuarioBeans getUsuarioControleDeAcesso(long codUsuarioControleDeAcesso);
    public void adicionaNivel(UsuarioBeans usuario, EnumPermissao permissao) throws IllegalAccessException;
    public void atualizaNiveis(UsuarioBeans usuario, List<EnumPermissao> permissoes) throws IllegalAccessException;
    public void removeNivel(UsuarioBeans usuario, EnumPermissao permissao) throws IllegalAccessException;
    public List<UsuarioBeans> listaUsuariosComPermissao(EnumPermissao permisssao);
}
