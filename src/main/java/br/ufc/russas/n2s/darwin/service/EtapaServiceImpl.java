package br.ufc.russas.n2s.darwin.service;

import br.ufc.russas.n2s.darwin.beans.AvaliacaoBeans;
import br.ufc.russas.n2s.darwin.beans.DocumentacaoBeans;
import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.ParticipanteBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.dao.EtapaDAOIfc;
import br.ufc.russas.n2s.darwin.model.Avaliacao;
import br.ufc.russas.n2s.darwin.model.Documentacao;
import br.ufc.russas.n2s.darwin.model.Etapa;
import br.ufc.russas.n2s.darwin.model.EtapaProxy;
import br.ufc.russas.n2s.darwin.model.Participante;
import br.ufc.russas.n2s.darwin.model.Selecao;
import br.ufc.russas.n2s.darwin.model.SelecaoProxy;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wallison Carlos, Gilberto Lima
 */
@Service("etapaServiceIfc")
public class EtapaServiceImpl implements EtapaServiceIfc {

	private EtapaDAOIfc etapaDAOIfc;
	private SelecaoServiceIfc selecaoServiceIfc;

	private UsuarioBeans usuario;

	public EtapaDAOIfc getEtapaDAOIfc() {
		return etapaDAOIfc;
	}

	@Autowired(required = true)
	public void setEtapaDAOIfc(@Qualifier("etapaDAOIfc") EtapaDAOIfc etapaDAOIfc) {
		this.etapaDAOIfc = etapaDAOIfc;
	}

	@Autowired(required = true)
	public void setSelecaoServiceIfc(@Qualifier("selecaoServiceIfc") SelecaoServiceIfc selecaoServiceIfc) {
		this.selecaoServiceIfc = selecaoServiceIfc;
	}

	@Override
	public void setUsuario(UsuarioBeans usuario) {
		this.usuario = usuario;
	}

	@Override
	public EtapaBeans adicionaEtapa(SelecaoBeans selecao, EtapaBeans etapa) throws IllegalAccessException {
		UsuarioDarwin u = (UsuarioDarwin) usuario.toBusiness();
		SelecaoProxy sp = new SelecaoProxy(u);
		Selecao s = (Selecao) selecao.toBusiness();
		Etapa e = (Etapa) etapa.toBusiness();
		e = this.etapaDAOIfc.adicionaEtapa(e);
		sp.adicionaEtapa(s, e);

		this.selecaoServiceIfc.setUsuario(usuario);
		this.selecaoServiceIfc.atualizaSelecao((SelecaoBeans) selecao.toBeans(s));
		return etapa;
	}

	@Override
	public EtapaBeans atualizaEtapa(SelecaoBeans selecao, EtapaBeans etapa) throws IllegalAccessException {
		UsuarioDarwin u = (UsuarioDarwin) usuario.toBusiness();
		SelecaoProxy sp = new SelecaoProxy(u);
		Etapa e = sp.atualizaEtapa((Selecao) selecao.toBusiness(), (Etapa) etapa.toBusiness());
		return (EtapaBeans) etapa.toBeans(e);
	}

	@Override
	public EtapaBeans atualizaEtapa(EtapaBeans etapa) {
		Etapa e = (Etapa) etapa.toBusiness();
		etapaDAOIfc.atualizaEtapa(e);
		return (EtapaBeans) etapa.toBeans(e);
	}

	@Override
	public void removeEtapa(EtapaBeans etapa) {
		this.getEtapaDAOIfc().removeEtapa((Etapa) etapa.toBusiness());
	}

	@Override
	public List<EtapaBeans> listaTodasEtapas() {
		Etapa etp = new Etapa();
		List<EtapaBeans> etapas = Collections.synchronizedList(new ArrayList<EtapaBeans>());
		List<Etapa> result = this.getEtapaDAOIfc().listaEtapas(etp);
		for (Etapa etapa : result) {
			etapas.add((EtapaBeans) new EtapaBeans().toBeans(etapa));
		}
		return this.ordenaEtapasPorData(etapas);
	}

	@Override
	public EtapaBeans getEtapa(long codEtapa) {
		Etapa etp = new Etapa();
		etp.setCodEtapa(codEtapa);
		Etapa e = this.getEtapaDAOIfc().getEtapa(etp);
		if (e != null) {
			return (EtapaBeans) new EtapaBeans().toBeans(e);
		} else {
			return null;
		}
	}

	@Override
	public boolean isParticipante(ParticipanteBeans participante) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	@Override
	public boolean isParticipante(EtapaBeans etapa, UsuarioBeans participante) {
		for (ParticipanteBeans p : etapa.getParticipantes()) {
			if (p.getCandidato().getCodUsuario() == participante.getCodUsuario()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ParticipanteBeans getParticipante(EtapaBeans etapa, UsuarioBeans usuario) {
		Etapa e = (Etapa) etapa.toBusiness();
		return (ParticipanteBeans) new ParticipanteBeans()
				.toBeans(e.getParticipante((UsuarioDarwin) usuario.toBusiness()));
	}

	@Override
	public void anexaDocumentacao(EtapaBeans etapa, ParticipanteBeans participante, DocumentacaoBeans documentacao)
			throws IllegalAccessException {
		Etapa e = (Etapa) etapa.toBusiness();
		Documentacao d = (Documentacao) documentacao.toBusiness();
		Participante p = (Participante) participante.toBusiness();
		if (p.getCodParticipante() != 0) {
			d.setCandidato(p);
			e.anexaDocumentacao(d);
			getEtapaDAOIfc().atualizaEtapa(e);
		} else {
			e.anexaDocumentacao(d);
			getEtapaDAOIfc().atualizaEtapa(e);
		}

	}

	@Override
	public void avalia(EtapaBeans etapa, AvaliacaoBeans avaliacao) throws IllegalAccessException {
		Etapa e = (Etapa) etapa.toBusiness();
		Avaliacao a = (Avaliacao) avaliacao.toBusiness();
		EtapaProxy ep = new EtapaProxy((UsuarioDarwin) usuario.toBusiness());
		ep.avalia(e, a);
		this.etapaDAOIfc.atualizaEtapa(e);
	}

	@Override
	public List<Object[]> getParticipantes(EtapaBeans etapa) {
		List<Object[]> p = null;
		Etapa e = (Etapa) etapa.toBusiness();
		p = e.getResultado();
		return p;
	}

	@Override
	public SelecaoBeans getSelecao(EtapaBeans etapa) {
		return selecaoServiceIfc.getSelecaoDaEtapa(etapa.getCodEtapa());
	}

	@Override
	public void participa(EtapaBeans inscricao, ParticipanteBeans participante) throws IllegalAccessException {
		Etapa i = (Etapa) inscricao.toBusiness();
		i.getParticipantes().add((Participante) participante.toBusiness());
		this.etapaDAOIfc.atualizaEtapa(i);
	}

	@Override
	public void participa(EtapaBeans inscricao, ParticipanteBeans participante, DocumentacaoBeans documentacao)
			throws IllegalAccessException {
		Etapa i = (Etapa) inscricao.toBusiness();
		Documentacao d = (Documentacao) documentacao.toBusiness();
		Participante p = (Participante) participante.toBusiness();
		i.getParticipantes().add(p);
		d.setCandidato(p);
		i.anexaDocumentacao(d);
		this.etapaDAOIfc.atualizaEtapa(i);
	}

	@Override
	public List<EtapaBeans> ordenaEtapasPorData(List<EtapaBeans> etapas) {
		EtapaBeans aux;
		for (int i = 0; i < etapas.size(); i++) {
			for (int j = 0; j < etapas.size() - 1; j++) {
				if (etapas.get(j).getPeriodo().getInicio().isAfter(etapas.get(j + 1).getPeriodo().getInicio())) {
					aux = etapas.get(j);
					etapas.set(j, etapas.get(j + 1));
					etapas.set(j + 1, aux);
				}
			}
		}
		return etapas;
	}

	@Override
	public Object[] getSituacao(EtapaBeans etapa, UsuarioBeans usuario) {
		Etapa e = (Etapa) etapa.toBusiness();
		UsuarioDarwin u = (UsuarioDarwin) usuario.toBusiness();
		Object[] situacao = e.getSituacao(u);
		if (situacao != null) {
			Participante s1 = (Participante) situacao[0];
			situacao[0] = (ParticipanteBeans) new ParticipanteBeans().toBeans(s1);
		}
		return situacao;
	}

	@Override
	public List<Object[]> getResultado(EtapaBeans etapa) {
		Etapa e = (Etapa) etapa.toBusiness();
		List<Object[]> resultado = e.getResultado();
		for (int i = 0; i < resultado.size(); i++) {
			Object[] r = resultado.get(i);
			r[0] = (ParticipanteBeans) (new ParticipanteBeans().toBeans((Participante) r[0]));
			resultado.set(i, r);
		}
		return resultado;
	}

	@Override
	public List<AvaliacaoBeans> getAvaliacoesParticipante(ParticipanteBeans participante, long codEtapa) {
		EtapaBeans eb = this.getEtapa(codEtapa);
		List<AvaliacaoBeans> lista = eb.getAvaliacoes();
		List<AvaliacaoBeans> listaAvaliacoes = new ArrayList<>();
		for (AvaliacaoBeans a : lista) {
			if (a.getParticipante().getCodParticipante() == participante.getCodParticipante()) {
				listaAvaliacoes.add(a);
			}
		}
		return listaAvaliacoes;
	}

}
