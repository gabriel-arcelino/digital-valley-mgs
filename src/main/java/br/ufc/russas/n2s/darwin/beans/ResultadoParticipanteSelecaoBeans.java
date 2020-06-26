package br.ufc.russas.n2s.darwin.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ufc.russas.n2s.darwin.model.Etapa;
import br.ufc.russas.n2s.darwin.model.Participante;
import br.ufc.russas.n2s.darwin.model.ResultadoParticipanteSelecao;

public class ResultadoParticipanteSelecaoBeans implements Beans, Serializable{
	
	private static final long serialVersionUID = -8523441695215586777L;
	
	private int colocacao;
	private ParticipanteBeans participante;
	private List<EtapaBeans> etapas = Collections.synchronizedList(new ArrayList<EtapaBeans>());
	private List<Float> notasEtapas = Collections.synchronizedList(new ArrayList<Float>());
	private float mediaGeral;
	private boolean aprovado;

	public int getColocacao() {
		return colocacao;
	}
	
	public void setColocacao(int colocacao) {
		this.colocacao = colocacao;
	}
	
	public ParticipanteBeans getParticipante() {
		return participante;
	}
	
	public void setParticipante(ParticipanteBeans participante) {
		this.participante = participante;
	}
	
	public List<EtapaBeans> getEtapas() {
		return etapas;
	}
	
	public void setEtapas(List<EtapaBeans> etapas) {
		this.etapas = etapas;
	}
	
	public List<Float> getNotasEtapas() {
		return notasEtapas;
	}
	
	public void setNotasEtapas(List<Float> notasEtapas) {
		this.notasEtapas = notasEtapas;
	}
	
	
	public float getMediaGeral() {
		return mediaGeral;
	}
	
	public void setMediaGeral(float mediaGeral) {
		this.mediaGeral = mediaGeral;
	}
	
	public boolean isAprovado() {
		return aprovado;
	}
	
	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}

	@Override
	public Object toBusiness() {
		ResultadoParticipanteSelecao resultado = new ResultadoParticipanteSelecao();
		
		if (this.getParticipante() != null) {
			resultado.setParticipante((Participante)this.getParticipante().toBusiness());
		}
		
		resultado.setColocacao(this.getColocacao());
		
		for (EtapaBeans etapa : this.getEtapas()) {
			resultado.getEtapas().add((Etapa) etapa.toBusiness());	
		}
		
		for (Float nota : this.getNotasEtapas()) {
			resultado.getNotasEtapas().add(nota);
		}
		
		resultado.setMediaGeral(this.getMediaGeral());
		
		resultado.setAprovado(this.isAprovado());
		
		return resultado;
	}

	@Override
	public Beans toBeans(Object object) {
		if (object != null) {
			if (object instanceof ResultadoParticipanteSelecao) {
				ResultadoParticipanteSelecao resultado = (ResultadoParticipanteSelecao) object;
				if (resultado.getParticipante() != null) {
					this.setParticipante((ParticipanteBeans) new ParticipanteBeans().toBeans(resultado.getParticipante()));
				}
				
				this.setColocacao(resultado.getColocacao());
				
				for (Etapa etapa : resultado.getEtapas()) {
					this.getEtapas().add((EtapaBeans) new EtapaBeans().toBeans(etapa));	
				}
				
				for (Float nota : resultado.getNotasEtapas()) {
					this.getNotasEtapas().add(nota);
				}
				
				this.setMediaGeral(resultado.getMediaGeral());
				
				this.setAprovado(resultado.isAprovado());
				return this;
			} else {
				throw new IllegalArgumentException("Era esperado um ResultadoParticipanteSelecao!");
			}			
		} else {
			throw new NullPointerException("Resultado Participante Seleção não pode ser vazio!");			
		}
	}
}
