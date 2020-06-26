package br.ufc.russas.n2s.darwin.dao;

import java.util.List;

import br.ufc.russas.n2s.darwin.model.EnumEstadoSelecao;
import br.ufc.russas.n2s.darwin.model.Selecao;

/**
 *
 * @author N2S-PC03
 */
public interface SelecaoDAOIfc {

    /**
     * Método resposável por fazer a persistência de uma seleção.
     * @param selecao
     * @return Selecao
     */
    public Selecao adicionaSelecao(Selecao selecao);

    /**
     * Método resposável por fazer a atualização de uma seleção.
     * @param selecao
     * @return Selecao
     */
    public Selecao atualizaSelecao(Selecao selecao);

    /**
     * Método resposável por fazer a remoção de uma seleção.
     * @param selecao
     */
    public void removeSelecao(Selecao selecao);

    /**
     * Método resposável por fazer a listagem de todos as as seleções.
     * @return List<Selecao>
     */
    public List<Selecao> listaSelecoes(Selecao selecao);

    /**
     * Método resposável por pegar do banco de dados uma
     * seleção a partir do código informadao.
     * @param codigo
     * @return
     */
    public Selecao getSelecao(Selecao selecao);
    
    public List<Selecao> getSelecoesDivulgadas();
    
    public void divulgaResutadoSelecao(Selecao selecao);
    
    public void atualizaExibirNotas(Selecao selecao);
    
    public List<Selecao> listaSelecoesIgnorandoNotas(Selecao selecao);
    public List<Selecao> buscaTodasPorCriteria(boolean divulgada);
    public List<Selecao> buscaTodasPorCriteria();
    public List<Selecao> listaSelecoesIgnorandoBooleanos();
    
    //Novos
    public List<Selecao> listaSelecoes(boolean isAdm, String categoria, EnumEstadoSelecao estado, int inicio, int qtd);
    public Long getQuantidade(boolean isAdm, String categoria, EnumEstadoSelecao estado);
    
    public List<Selecao> buscarSelecoesPorNome(boolean isAdm, String titulo, int inicio, int qtd);
    public Long getQuantidadePorNome(boolean isAdm, String titulo);
    
    public List<Selecao> buscarSelecoesAssociada(Long usuario, int inicio, int qtd);
    public List<Long> getListaSelecoesAssociada(Long usuario);
    
    public Selecao getSelecao(Long codEtapa);
}
