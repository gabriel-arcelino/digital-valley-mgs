package br.ufc.russas.n2s.darwin.dao;

import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;
import java.util.List;

/**
 *
 * @author Wallison Carlos
 */
public interface UsuarioDAOIfc {
    /**
     * Método resposável por fazer a persistência de uma etapa.
     * @param etapa
     */
    public UsuarioDarwin adicionaUsuario(UsuarioDarwin usuario);

    /**
     * Método resposável por fazer a atualização de uma etapa.
     * @param etapa
     */
    public UsuarioDarwin atualizaUsuario(UsuarioDarwin usuario);

    /**
     * Método resposável por fazer a remoção de uma etapa.
     * @param etapa
     */
    public void removeUsuario(UsuarioDarwin usuario);

    /**
     * Método resposável por fazer a listagem de todas os usuários.
     * @return List<Etapa>
     */
    public List<UsuarioDarwin> listaUsuarios(UsuarioDarwin usuario);

    /**
     * Método resposável por fazer a listagem de todas os usuários.
     * @return List<Etapa>
     */
    public List<UsuarioDarwin> BuscaUsuariosPorNome(String nome);
    
    /**
     * Método resposável por pegar do banco de dados uma etapa a partir do
     * código informado.
     * @param codEtapa
     * @return Etapa
     */
    public UsuarioDarwin getUsuario(UsuarioDarwin usuario);
    
    /**
     * Método resposável por pegar do banco de dados uma etapa a partir do
     * código informado.
     * @param codEtapa
     * @return Etapa
     */
    public UsuarioDarwin getUsuarioControleDeAcesso(UsuarioDarwin usuario);
    
    
    public List<UsuarioDarwin> ListaEmOdermAlfabetica();
}
