package br.ufc.russas.n2s.darwin.dao;

import br.ufc.russas.n2s.darwin.model.Avaliacao;

import java.util.List;

/**
 *
 * @author Wallison Carlos
 */
public interface AvaliacaoDAOIfc {

	public Avaliacao adicionaAvaliacao(Avaliacao avaliacao);

	public Avaliacao atualizaAvaliacao(Avaliacao avaliacao);

	public void removeAvaliacao(Avaliacao avaliacao);

	public List<Avaliacao> listaAvaliacoes(Avaliacao avaliacao);

	public Avaliacao getAvaliacao(Avaliacao avaliacao);

}
