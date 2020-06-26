/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Wallison Carlos, Gilberto Lima
 */
@Entity
@Table(name = "etapa")
public class Etapa implements Serializable {
	
	private static final long serialVersionUID = -5159380096393806738L;
	
	@Id
	@Column(name = "codEtapa")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codEtapa;
	
	private String titulo;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "periodo", referencedColumnName = "codPeriodo")
	private Periodo periodo;
	
	@Column(columnDefinition = "TEXT")
	private String descricao;
	
	@ManyToMany(targetEntity = UsuarioDarwin.class, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "avaliadores", joinColumns = {
			@JoinColumn(name = "etapa", referencedColumnName = "codEtapa") }, inverseJoinColumns = {
					@JoinColumn(name = "avaliador", referencedColumnName = "codUsuario") })
	private List<UsuarioDarwin> avaliadores;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@CollectionTable(name = "documentacoes_exigidas", joinColumns = @JoinColumn(name = "codEtapa"))
	@Column(name = "documentacao_exigida")
	private List<String> documentacaoExigida;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@CollectionTable(name = "documentacoes_opcionais", joinColumns = @JoinColumn(name = "codEtapa"))
	@Column(name = "documentacao_opcional")
	private List<String> documentacaoOpcional;
	
	
	@Column(name = "criterio_de_avaliacao")
	@Enumerated(EnumType.ORDINAL)
	private EnumCriterioDeAvaliacao criterioDeAvaliacao;
	
	private float pesoNota = 1;
	
	private boolean criterioDesempate;
	
	private int posiscaoCriterioDesempate;
	
	@ManyToMany(targetEntity = Avaliacao.class, fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "avaliacoes", joinColumns = {
			@JoinColumn(name = "etapa", referencedColumnName = "codEtapa") }, inverseJoinColumns = {
					@JoinColumn(name = "avaliacao", referencedColumnName = "codAvaliacao") })
	private List<Avaliacao> avaliacoes;
	
	@ManyToMany(targetEntity = Documentacao.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "documentacoes", joinColumns = {
			@JoinColumn(name = "etapa", referencedColumnName = "codEtapa") }, inverseJoinColumns = {
					@JoinColumn(name = "documentacao", referencedColumnName = "codDocumentacao") })
	private List<Documentacao> documentacoes;
	
	@Enumerated(EnumType.ORDINAL)
	private EnumEstadoEtapa estado;
	
	@ManyToOne
	@JoinColumn(name = "prerequisito", referencedColumnName = "codEtapa")
	private Etapa prerequisito;
	
	private float notaMinima;
	
	private int limiteClassificados;
	
	private boolean divulgadoResultado;
	
	@ManyToMany(targetEntity = Participante.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL) //tava CascadeType.ALL
	@Fetch(FetchMode.SUBSELECT)
	@JoinTable(name = "participantes_etapa", joinColumns = {
			@JoinColumn(name = "etapa", referencedColumnName = "codEtapa") }, inverseJoinColumns = {
					@JoinColumn(name = "participante", referencedColumnName = "codParticipante") })
	private List<Participante> participantes;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "recurso", referencedColumnName = "codRecurso")
	private Recurso recurso;
	

	public long getCodEtapa() {
		return codEtapa;
	}

	public void setCodEtapa(long codEtapa) {
		if (codEtapa > 0) {
			this.codEtapa = codEtapa;
		}
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		if (titulo == null) {
			throw new NullPointerException("Título não pode ser nulo!");
		} else if (titulo.isEmpty()) {
			throw new NullPointerException("Título não pode ser vazio!");
		} else {
			this.titulo = titulo;
		}
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		if (periodo != null) {
			this.periodo = periodo;
		} else {
			throw new NullPointerException("Período não pode ser nulo!");
		}
	}
	
	public Recurso getRecurso() {
		return this.recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<UsuarioDarwin> getAvaliadores() {
		return avaliadores;
	}

	public void setAvaliadores(List<UsuarioDarwin> avaliadores) {
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
		if (criterioDeAvaliacao != null) {
			this.criterioDeAvaliacao = criterioDeAvaliacao;
		} else {
			throw new NullPointerException("Deve ser selecionado um critério de avaliação!");
		}
	}

	public float getPesoNota() {
		return pesoNota;
	}
	
	public void setPesoNota(float pesoNota) {
		if (pesoNota >= 0) {
			this.pesoNota = pesoNota;
		} else {
			throw new IllegalArgumentException("Peso de nota deve ser maior igual a 1!");
		}
	}
	
	public boolean isCriterioDesempate() {
		return criterioDesempate;
	}
	
	public void setCriterioDesempate(boolean criterioDesempate) {
		this.criterioDesempate = criterioDesempate;
	}
	
	public int getPosiscaoCriterioDesempate() {
		return posiscaoCriterioDesempate;
	}
	
	public void setPosiscaoCriterioDesempate(int posiscaoCriterioDesempate) {
		this.posiscaoCriterioDesempate = posiscaoCriterioDesempate;
	}
	
	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public List<Documentacao> getDocumentacoes() {
		return documentacoes;
	}

	public void setDocumentacoes(List<Documentacao> documentacoes) {
		this.documentacoes = documentacoes;
	}

	public EnumEstadoEtapa getEstado() {
		return estado;
	}

	public void setEstado(EnumEstadoEtapa estado) {
		this.estado = estado;
	}

	public Etapa getPrerequisito() {
		return prerequisito;
	}
	
	

	public float getNotaMinima() {
		return notaMinima;
	}

	public void setNotaMinima(float notaMinima) {
		if (notaMinima >= 0 && notaMinima <= 10) {
			this.notaMinima = notaMinima;
		} else {
			throw new IllegalArgumentException(
					"Nota miníma inválida. Nota miníma deve ser maior igual a zero e menor igual a 10!");
		}
	}

	public int getLimiteClassificados() {
		return limiteClassificados;
	}

	public void setLimiteClassificados(int limiteClassificados) {
		if (limiteClassificados >= 0) {
			this.limiteClassificados = limiteClassificados;
		} else {
			throw new IllegalArgumentException("Limite de Classificados deve ser maior igual a zero!");
		}
	}

	public boolean isDivulgadoResultado() {
		return divulgadoResultado;
	}

	public void setDivulgadoResultado(boolean divulgadoResultado) {
		this.divulgadoResultado = divulgadoResultado;
	}
	
	public List<Participante> getParticipantes() {
		return this.participantes;
	}
	
	public void setParticipantes(List<Participante> participantes) {
		this.participantes = participantes;
	}
	

	/**
	 *
	 * @param prerequisito
	 */
	public void setPrerequisito(Etapa prerequisito) throws IllegalArgumentException {
		if (prerequisito != null && prerequisito.getPeriodo().isAntes(this.getPeriodo())) {
			this.prerequisito = prerequisito;
		} else {
			if (prerequisito != null) {
				throw new IllegalArgumentException(
					"A etapa " + prerequisito.getTitulo() +" não pode ser pré-requisito da etapa " + this.getTitulo() + " pois não ocorre antes!");
			} else {
				throw new NullPointerException("Deve ser adicionada uma etapa de pré-requisito!");
			}
		}
	}

	
	/**
	 *
	 * @param maisDocumentacao
	 */
	public void adicionaDocumentacaoExigida(List<String> maisDocumentacao) {
		if (maisDocumentacao != null && !maisDocumentacao.isEmpty()) {
			this.documentacaoExigida.addAll(maisDocumentacao);
		}
	}
	
	public void adicionaDocumentacaoOpcional(List<String> maisDocumentacao) {
		if (maisDocumentacao != null && !maisDocumentacao.isEmpty()) {
			this.documentacaoOpcional.addAll(maisDocumentacao);
		}
	}

	/**
	 * Adiciona um novo avaliador a etapa.
	 * 
	 * @param usuario
	 */
	public void adicionaAvaliador(UsuarioDarwin usuario) {
		if (this.getAvaliadores() != null) {
			ArrayList<UsuarioDarwin> usuarios = new ArrayList<>();
			List<UsuarioDarwin> sync = Collections.synchronizedList(usuarios);
			this.setAvaliadores(sync);
		}
		if (!this.getAvaliadores().contains(usuario)) {
			Etapa inscricao = this;
			while (inscricao.getPrerequisito() != null) {
				inscricao = inscricao.getPrerequisito();
			}
			if (!inscricao.isParticipante(usuario)) {
				this.getAvaliadores().add(usuario);
			} else  {
				throw new IllegalArgumentException("Este usuário está participando desta seleção. Portanto, não poderá avaliar nenhuma atividade desta seleção!");
			}
		} else {
			throw new IllegalArgumentException("Esse usuário já é avaliador desssa etapa!");
		}
	}

	/**
	 * Método resposável por remover um avaliador desta etapa.
	 * 
	 * @param usuario
	 */
	public void removeAvaliador(UsuarioDarwin usuario) {
		if (this.getAvaliadores() != null) {
			if (this.getAvaliadores().contains(usuario)) {
				this.getAvaliadores().remove(usuario);
			} else {
				throw new IllegalArgumentException("Usuário não é avaliador dessa etapa!");
			}
		} else {
			throw new NullPointerException("Não existe avaliadores cadastrados para esta etapa!");
		}
	}

	public List<Participante> getAprovados() {
		List<Participante> aprovados = Collections.synchronizedList(new ArrayList<Participante>());
		if (getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.APROVACAO
				|| getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.DEFERIMENTO) {
			for (Object[] p : getResultado()) {
				if (p[3] != null && ((Avaliacao) p[3]).isAprovado()) {
					aprovados.add((Participante) p[0]);
				}
			}
		} else if (getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.NOTA) {
			for (Object[] participante : getResultado()) {
				if (((float) participante[3]) >= getNotaMinima()) {
					aprovados.add((Participante) participante[0]);
				}
			}
		}
		return aprovados;
	}

	public List<Object[]> getResultado() {
		List<Object[]> resultado = Collections.synchronizedList(new ArrayList<Object[]>());
		if (getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.APROVACAO
				|| getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.DEFERIMENTO) {
			for (Participante p : listParticipantes()) {
				String situacao = "NÃO AVALIADO";
				Avaliacao avaliacao = null;
				for (Avaliacao a : getAvaliacoes()) {
					if (a.getParticipante().equals(p)) {
						avaliacao = a;
						situacao = "AVALIADO";
					}		
				}
				String status = "Reprovado";
				if (avaliacao != null && avaliacao.isAprovado()) {
					status = "Aprovado";
				}
				Object[] aprovado = {p, situacao, status, avaliacao};
				resultado.add(aprovado);

			}
		} else if (getCriterioDeAvaliacao() == EnumCriterioDeAvaliacao.NOTA) {
			for (Participante participante : listParticipantes()) {
				float media = 0;
				float soma = 0;
				int count = 0;
				String situacao = "NÃO AVALIADO";
				for (Avaliacao avaliacao : getAvaliacoes()) {
					if (avaliacao.getParticipante().equals(participante)) {
						soma += avaliacao.getNota();
						situacao = "AVALIADO";
						count++;
					}
				}
				media = soma / count;
				String status = "Aprovado";
				if (media < getNotaMinima()) {
					status = "Reprovado";
				}
				Object[] aprovado = {participante, situacao, status, media};
				resultado.add(aprovado);
			}
		}
		return resultado;
	}

	public Object[] getSituacao(UsuarioDarwin usuario) {
		for (Object[] participante : getResultado()) {
			if (((Participante) participante[0]).getCandidato().equals(usuario)) {
				return participante;
			}
		}
		return null;
	}

	public List<Participante> listParticipantes() {
		if (this.getPrerequisito() != null) {
			this.setParticipantes(this.getPrerequisito().getAprovados());
			return this.getParticipantes();
		} else {
			return this.getParticipantes();
		}
	}

	public boolean isParticipante(Participante participante) {
		return this.getParticipantes().contains(participante);
	}

	public boolean wasAprovado(Participante participante) {
		if (this.getPrerequisito() != null) {
			return this.getPrerequisito().getAprovados().contains(participante);
		} else {
			return true;
		}
	}

	public boolean isParticipante(UsuarioDarwin participante) {
		for (Participante p : this.getParticipantes()) {
			if (p.getCandidato().equals(participante)) {
				return true;
			}
		}
		return false;
	}

	public Participante getParticipante(UsuarioDarwin usuario) {
		Participante participante = null;
		for (Participante p : this.getParticipantes()) {
			if (p.getCandidato().equals(usuario)) {
				participante = p;
				break;
			}
		}
		return participante;
	}

	public void anexaDocumentacao(Documentacao documentacao) throws IllegalAccessException {
		if (documentacao == null) {
			throw new NullPointerException("Documentacao não pode ser vazia!");
		} else {
			Participante par = documentacao.getCandidato();
			if (!wasAprovado(par)) {
				throw new IllegalAccessException("Você não é um participante desta Etapa");
			} else {
				this.getDocumentacoes().add(documentacao);
			}
		}
	}

	public void avalia(Avaliacao avaliacao) {
		if (this.getAvaliacoes() != null) {
			this.getAvaliacoes().add(avaliacao);
		} else {
			this.setAvaliacoes(Collections.synchronizedList(new ArrayList<Avaliacao>()));
			this.getAvaliacoes().add(avaliacao);
		}
	}
	
	

	/**
	 * Verifica se o usuário passado é um avaliador.
	 * 
	 * @param usuario
	 * @return
	 */
	public boolean isAvaliador(UsuarioDarwin usuario) {
		return this.getAvaliadores().contains(usuario);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + (int) (this.codEtapa ^ (this.codEtapa >>> 32));
		return hash;
	}

	@Override
	public boolean equals(final Object o) {
		if (o != null) {
			return (o instanceof Etapa && this.getCodEtapa() == ((Etapa) o).getCodEtapa());
		} else {
			return false;
		}
	}
	
}
