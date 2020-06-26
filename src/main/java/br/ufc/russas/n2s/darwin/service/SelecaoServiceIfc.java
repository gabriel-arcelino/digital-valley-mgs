package br.ufc.russas.n2s.darwin.service;

import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.ResultadoParticipanteSelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.model.EnumEstadoSelecao;
import br.ufc.russas.n2s.darwin.model.Selecao;

import java.util.List;

/**
 *
 * @author Wallison Carlos
 */
public interface SelecaoServiceIfc extends ServiceIfc{

    public SelecaoBeans adicionaSelecao(SelecaoBeans selecao) throws IllegalAccessException;
    
    public SelecaoBeans atualizaSelecao(SelecaoBeans selecao) throws IllegalAccessException;

    public void removeSelecao(SelecaoBeans selecao);

    public List<SelecaoBeans> listaTodasSelecoes();
    
    public List<SelecaoBeans> listaSelecoes(Selecao selecao);
    
    public List<SelecaoBeans> listaSelecoesAssociada(UsuarioBeans usuario);
    
    public SelecaoBeans getSelecao(long codSelecao);
    
    public EtapaBeans getEtapaAtual(SelecaoBeans selecao);
    
    public EtapaBeans getUltimaEtapa(SelecaoBeans selecao);

    public List<EtapaBeans> getEtapasNota(SelecaoBeans selecao);
    
    public List<SelecaoBeans> ordenaSelecoesPorData(List<SelecaoBeans> selecoes);
    
    public List<ResultadoParticipanteSelecaoBeans> getResultado(SelecaoBeans selecoes)  throws IllegalAccessException;

    public List<SelecaoBeans> listaTodasSelecoesDoBanco();
    
    public void divulgaResultadoSelecao(SelecaoBeans selecao);
    
    public void atualizaExibirNotas(SelecaoBeans selecao);
    
    public List<SelecaoBeans> listaSelecoesIgnorandoNotas(Selecao selecao);
    
    //Novos
    public List<SelecaoBeans> listaSelecoes(boolean isAdm, String categoria, EnumEstadoSelecao estado, int inicio, int qtd);
    public Long getQuantidade(boolean isAdm, String categoria, EnumEstadoSelecao estado);
    
    public List<SelecaoBeans> buscarSelecoesPorNome(boolean isAdm, String titulo, int inicio, int qtd);
    public Long getQuantidadePorNome(boolean isAdm, String titulo);
    
    public List<SelecaoBeans> buscarSelecoesAssociada(UsuarioBeans usuario, int inicio, int qtd);
    public Long getQuantidadeAssociada(UsuarioBeans usuario);
    
    public SelecaoBeans getSelecaoDaEtapa(Long codEtapa);
    
}
