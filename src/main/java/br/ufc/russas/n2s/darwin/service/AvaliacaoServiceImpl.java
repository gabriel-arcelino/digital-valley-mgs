/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.service;

import br.ufc.russas.n2s.darwin.beans.AvaliacaoBeans;
import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.dao.AvaliacaoDAOIfc;
import br.ufc.russas.n2s.darwin.model.Avaliacao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wallison Carlos
 */
@Service("avaliacaoServiceIfc")
public class AvaliacaoServiceImpl implements AvaliacaoServiceIfc {

    private AvaliacaoDAOIfc avaliacaoDAOIfc;

    public AvaliacaoDAOIfc getAvaliacaoDAOIfc() {
        return avaliacaoDAOIfc;
    }

    @Autowired(required = true)
    public void setEtapaDAOIfc(@Qualifier("avaliacaoDAOIfc")AvaliacaoDAOIfc avaliacaoDAOIfc) {
        this.avaliacaoDAOIfc = avaliacaoDAOIfc;
    }
    
    
    @Override
    public void setUsuario(UsuarioBeans usuario) {
    }
    
    @Override
	public AvaliacaoBeans adicionaAvaliacao(AvaliacaoBeans avaliacao) {
		Avaliacao a = (Avaliacao) avaliacao.toBusiness();
		return (AvaliacaoBeans) new AvaliacaoBeans().toBeans(avaliacaoDAOIfc.adicionaAvaliacao(a));
	}

    @Override
    public void removeAvaliacao(AvaliacaoBeans avaliacao) {
        this.getAvaliacaoDAOIfc().removeAvaliacao((Avaliacao) avaliacao.toBusiness());
    }

    @Override
    public List<AvaliacaoBeans> listaTodasAvaliacoes() {
        Avaliacao avl = new Avaliacao();
        List<AvaliacaoBeans> avaliacoes = Collections.synchronizedList(new ArrayList<AvaliacaoBeans>());
        List<Avaliacao> result = this.getAvaliacaoDAOIfc().listaAvaliacoes(avl);
        for (Avaliacao avaliacao : result) {
            avaliacoes.add((AvaliacaoBeans) new EtapaBeans().toBeans(avaliacao));
        }
        return avaliacoes;
    }

    @Override
    public AvaliacaoBeans getAvaliacao(long codAvaliacao) {
    	Avaliacao avl = new Avaliacao();
        avl.setCodAvaliacao(codAvaliacao);
        return (AvaliacaoBeans) new AvaliacaoBeans().toBeans(this.getAvaliacaoDAOIfc().getAvaliacao(avl));
    }

	@Override
	public AvaliacaoBeans atualizarAvaliacao(AvaliacaoBeans avaliacao) {
		Avaliacao a = (Avaliacao) avaliacao.toBusiness();
		return (AvaliacaoBeans) new AvaliacaoBeans().toBeans(avaliacaoDAOIfc.atualizaAvaliacao(a));
	}

}
