package br.ufc.russas.n2s.darwin.dao;

import br.ufc.russas.n2s.darwin.model.Avaliacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wallison Carlos
 */
@Repository("avaliacaoDAOIfc")
public class AvaliacaoDAOImpl implements AvaliacaoDAOIfc {

	private DAOIfc<Avaliacao> daoImpl;

	@Autowired(required = true)
	public void setDAOIfc(@Qualifier("daoImpl") DAOIfc<Avaliacao> dao) {
		this.daoImpl = dao;
	}

	@Override
	public Avaliacao adicionaAvaliacao(Avaliacao avaliacao) {
		return this.daoImpl.adiciona(avaliacao);
	}

	@Override
	public Avaliacao atualizaAvaliacao(Avaliacao avaliacao) {
		removeAvaliacao(avaliacao); // Unica forma, pois a atualização não ta funcionando
		adicionaAvaliacao(avaliacao);
		return avaliacao;
	}

	@Override
	public void removeAvaliacao(Avaliacao avaliacao) {
		this.daoImpl.remove(avaliacao);
	}

	@Override
	public List<Avaliacao> listaAvaliacoes(Avaliacao avaliacao) {
		return this.daoImpl.lista(avaliacao);
	}

	@Override
	public Avaliacao getAvaliacao(Avaliacao avaliacao) {
		return this.daoImpl.getObject(avaliacao, avaliacao.getCodAvaliacao());
	}

}
