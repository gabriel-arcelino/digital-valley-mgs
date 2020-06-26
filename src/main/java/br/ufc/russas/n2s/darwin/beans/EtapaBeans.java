package br.ufc.russas.n2s.darwin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufc.russas.n2s.darwin.model.Avaliacao;
import br.ufc.russas.n2s.darwin.model.Documentacao;
import br.ufc.russas.n2s.darwin.model.EnumCriterioDeAvaliacao;
import br.ufc.russas.n2s.darwin.model.EnumEstadoEtapa;
import br.ufc.russas.n2s.darwin.model.Etapa;
import br.ufc.russas.n2s.darwin.model.Participante;
import br.ufc.russas.n2s.darwin.model.Periodo;
import br.ufc.russas.n2s.darwin.model.Recurso;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;

/**
 *
 * @author Wallison Carlos, Gilberto
 */
public class EtapaBeans implements Beans, Serializable {

	private static final long serialVersionUID = -882881109373083344L;

	private long codEtapa;
	private String titulo;
	private PeriodoBeans periodo;
	private String descricao;
	private List<UsuarioBeans> avaliadores;
	private List<String> documentacaoExigida;
	private List<String> documentacaoOpcional;
	private EnumCriterioDeAvaliacao criterioDeAvaliacao;
	private List<AvaliacaoBeans> avaliacoes;
	private List<DocumentacaoBeans> documentacoes;
	private EnumEstadoEtapa estado;
	private EtapaBeans prerequisito;
	private float notaMinima;
	private int limiteClassificados;
	private boolean divulgadoResultado;
	private float pesoNota = 1;
	private boolean criterioDesempate;
	private int posicaoCriterioDesempate = 1;
	private List<ParticipanteBeans> participantes;
	private Recurso recurso;

	public EtapaBeans() {
	}

	public long getCodEtapa() {
		return codEtapa;
	}

	public void setCodEtapa(long codEtapa) {
		this.codEtapa = codEtapa;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public PeriodoBeans getPeriodo() {
		return periodo;
	}

	public void setPeriodo(PeriodoBeans periodo) {
		this.periodo = periodo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<UsuarioBeans> getAvaliadores() {
		return avaliadores;
	}

	public void setAvaliadores(List<UsuarioBeans> avaliadores) {
		this.avaliadores = avaliadores;
	}

	public List<String> getDocumentacaoExigida() {
		return documentacaoExigida;
	}

	public void setDocumentacaoExigida(List<String> documentacao) {
		this.documentacaoExigida = documentacao;
	}

	public List<String> getDocumentacaoOpcional() {
		return documentacaoOpcional;
	}

	public void setDocumentacaoOpcional(List<String> documentacao) {
		this.documentacaoOpcional = documentacao;
	}

	public EnumCriterioDeAvaliacao getCriterioDeAvaliacao() {
		return criterioDeAvaliacao;
	}

	public void setCriterioDeAvaliacao(EnumCriterioDeAvaliacao criterioDeAvaliacao) {
		this.criterioDeAvaliacao = criterioDeAvaliacao;
	}

	public float getPesoNota() {
		return pesoNota;
	}

	public void setPesoNota(float pesoNota) {
		this.pesoNota = pesoNota;
	}

	public boolean isCriterioDesempate() {
		return criterioDesempate;
	}

	public void setCriterioDesempate(boolean criterioDesempate) {
		this.criterioDesempate = criterioDesempate;
	}

	public int getPosicaoCriterioDesempate() {
		return posicaoCriterioDesempate;
	}

	public void setPosicaoCriterioDesempate(int posicaoCriterioDesempate) {
		this.posicaoCriterioDesempate = posicaoCriterioDesempate;
	}

	public List<AvaliacaoBeans> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<AvaliacaoBeans> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public List<DocumentacaoBeans> getDocumentacoes() {
		return documentacoes;
	}

	public void setDocumentacoes(List<DocumentacaoBeans> documentacoes) {
		this.documentacoes = documentacoes;
	}

	public EnumEstadoEtapa getEstado() {
		return estado;
	}

	public void setEstado(EnumEstadoEtapa estado) {
		this.estado = estado;
	}

	public EtapaBeans getPrerequisito() {
		return prerequisito;
	}

	public void setPrerequisito(EtapaBeans prerequisito) {
		this.prerequisito = prerequisito;
	}

	public float getNotaMinima() {
		return notaMinima;
	}

	public void setNotaMinima(float notaMinima) {
		this.notaMinima = notaMinima;
	}

	public int getLimiteClassificados() {
		return limiteClassificados;
	}

	public void setLimiteClassificados(int limiteClassificados) {
		this.limiteClassificados = limiteClassificados;
	}

	public boolean isDivulgadoResultado() {
		return divulgadoResultado;
	}

	public void setDivulgaResultado(boolean divulgadoResultado) {
		this.divulgadoResultado = divulgadoResultado;
	}

	public List<ParticipanteBeans> getParticipantes() {
		return this.participantes;
	}

	public void setParticipantes(List<ParticipanteBeans> participantes) {
		this.participantes = participantes;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	@Override
	public Object toBusiness() {
		Etapa etapa = new Etapa();
		if (this.getCodEtapa() > 0) {
			etapa.setCodEtapa(this.getCodEtapa());
		}
		etapa.setTitulo(this.getTitulo());
		etapa.setDescricao(this.getDescricao());
		etapa.setDocumentacaoExigida(this.getDocumentacaoExigida());
		etapa.setDocumentacaoOpcional(this.getDocumentacaoOpcional());
		etapa.setCriterioDeAvaliacao(this.getCriterioDeAvaliacao());
		etapa.setEstado(this.getEstado());
		etapa.setPeriodo((Periodo) this.getPeriodo().toBusiness());
		if (this.getRecurso() != null) {
			etapa.setRecurso(this.getRecurso());
		} else {
			etapa.setRecurso(null);
		}

		if (this.getPrerequisito() != null) {
			Etapa prerequisito = (Etapa) this.getPrerequisito().toBusiness();
			etapa.setPrerequisito(prerequisito);
		}
		List<UsuarioDarwin> avaliadores = Collections.synchronizedList(new ArrayList<UsuarioDarwin>());
		if (this.getAvaliadores() != null) {
			for (UsuarioBeans avaliador : this.getAvaliadores()) {
				avaliadores.add((UsuarioDarwin) avaliador.toBusiness());
			}
		}
		etapa.setAvaliadores(avaliadores);

		List<Participante> participantes = Collections.synchronizedList(new ArrayList<Participante>());
		if (this.getParticipantes() != null) {
			for (ParticipanteBeans participante : this.getParticipantes()) {
				participantes.add((Participante) participante.toBusiness());
			}
		}
		etapa.setParticipantes(participantes);

		List<Avaliacao> avaliacoes = Collections.synchronizedList(new ArrayList<Avaliacao>());
		if (this.getAvaliacoes() != null) {
			for (AvaliacaoBeans avaliacao : this.getAvaliacoes()) {
				avaliacoes.add((Avaliacao) avaliacao.toBusiness());
			}
		}
		etapa.setAvaliacoes(avaliacoes);

		List<Documentacao> documentacoes = Collections.synchronizedList(new ArrayList<Documentacao>());
		if (this.getDocumentacoes() != null) {
			for (DocumentacaoBeans documentacao : this.getDocumentacoes()) {
				documentacoes.add((Documentacao) documentacao.toBusiness());
			}
		}
		etapa.setDocumentacoes(documentacoes);
		etapa.setCriterioDesempate(this.isCriterioDesempate());
		etapa.setPesoNota(this.getPesoNota());
		etapa.setPosiscaoCriterioDesempate(this.getPosicaoCriterioDesempate());
		etapa.setNotaMinima(this.getNotaMinima());
		etapa.setLimiteClassificados(this.getLimiteClassificados());
		etapa.setDivulgadoResultado(this.isDivulgadoResultado());
		return etapa;
	}

	@Override
	public Beans toBeans(Object object) {
		if (object != null) {
			if (object instanceof Etapa) {
				Etapa etapa = (Etapa) object;
				this.setCodEtapa(etapa.getCodEtapa());
				this.setTitulo(etapa.getTitulo());
				this.setDescricao(etapa.getDescricao());
				this.setDocumentacaoExigida(etapa.getDocumentacaoExigida());
				this.setDocumentacaoOpcional(etapa.getDocumentacaoOpcional());
				this.setEstado(etapa.getEstado());

				PeriodoBeans pb = null;
				if (etapa.getPeriodo() != null) {
					pb = (PeriodoBeans) (new PeriodoBeans().toBeans(etapa.getPeriodo()));
				}
				this.setPeriodo(pb);

				if (etapa.getRecurso() != null) {
					this.setRecurso(etapa.getRecurso());
				} else {
					this.setRecurso(null);
				}

				this.setCriterioDeAvaliacao(etapa.getCriterioDeAvaliacao());

				if (etapa.getPrerequisito() != null) {
					this.setPrerequisito((EtapaBeans) new EtapaBeans().toBeans(etapa.getPrerequisito()));
				}

				List<UsuarioBeans> avaliadoresBeans = Collections.synchronizedList(new ArrayList<UsuarioBeans>());
				UsuarioBeans ubs;
				if (etapa.getAvaliadores() != null) {
					for (UsuarioDarwin usuario : etapa.getAvaliadores()) {
						ubs = (UsuarioBeans) (new UsuarioBeans().toBeans(usuario));
						avaliadoresBeans.add(ubs);
					}
				}
				this.setAvaliadores(avaliadoresBeans);

				List<ParticipanteBeans> participantes = Collections
						.synchronizedList(new ArrayList<ParticipanteBeans>());
				ParticipanteBeans p;
				if (etapa.getParticipantes() != null) {
					for (Participante participante : etapa.getParticipantes()) {
						p = (ParticipanteBeans) (new ParticipanteBeans().toBeans(participante));
						participantes.add(p);
					}
				}
				this.setParticipantes(participantes);

				List<AvaliacaoBeans> avaliacoesBeans = Collections.synchronizedList(new ArrayList<AvaliacaoBeans>());
				AvaliacaoBeans ab = null;
				if (etapa.getAvaliacoes() != null) {
					for (Avaliacao avaliacao : etapa.getAvaliacoes()) {
						ab = (AvaliacaoBeans) (new AvaliacaoBeans().toBeans(avaliacao));
						avaliacoesBeans.add(ab);
					}
				}
				this.setAvaliacoes(avaliacoesBeans);

				List<DocumentacaoBeans> documentacoes = Collections
						.synchronizedList(new ArrayList<DocumentacaoBeans>());
				DocumentacaoBeans db;
				if (etapa.getDocumentacoes() != null) {
					for (Documentacao documentacao : etapa.getDocumentacoes()) {
						db = (DocumentacaoBeans) (new DocumentacaoBeans().toBeans(documentacao));
						documentacoes.add(db);
					}
				}
				this.setDocumentacoes(documentacoes);
				this.setNotaMinima(etapa.getNotaMinima());
				this.setLimiteClassificados(etapa.getLimiteClassificados());
				this.setDivulgaResultado(etapa.isDivulgadoResultado());
				this.setPesoNota(etapa.getPesoNota());
				this.setCriterioDesempate(etapa.isCriterioDesempate());
				this.setPosicaoCriterioDesempate(etapa.getPosiscaoCriterioDesempate());
				return this;
			} else {
				throw new IllegalArgumentException("O objeto a ser adicionado não é uma Etapa!");
			}
		} else {
			throw new NullPointerException("Etapa não pode ser nula!");
		}

	}

	@Override
	public boolean equals(final Object o) {
		if (o != null) {
			return (this.getCodEtapa() == ((EtapaBeans) o).getCodEtapa());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codEtapa ^ (codEtapa >>> 32));
		return result;
	}

}
