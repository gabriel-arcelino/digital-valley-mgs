package br.ufc.russas.n2s.darwin.beans;

import java.io.Serializable;

import br.ufc.russas.n2s.darwin.model.Avaliacao;
import br.ufc.russas.n2s.darwin.model.EnumEstadoAvaliacao;
import br.ufc.russas.n2s.darwin.model.Participante;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;

/**
 *
 * @author Lavínia Matoso
 */
public class AvaliacaoBeans implements Beans, Serializable {

	private static final long serialVersionUID = -484502680222142796L;
	
	private long codAvaliacao;
    private ParticipanteBeans avaliado;
    private float nota;
    private boolean aprovado;
    private EnumEstadoAvaliacao estado;
    private String observacao;
    private UsuarioBeans avaliador;

    public long getCodAvaliacao() {
        return codAvaliacao;
    }

    public void setCodAvaliacao(long codAvaliacao) {
        this.codAvaliacao = codAvaliacao;
    }

    public ParticipanteBeans getParticipante() {
        return avaliado;
    }

    public void setParticipante(ParticipanteBeans participante) {
        this.avaliado = participante;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public UsuarioBeans getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(UsuarioBeans avaliador) {
        this.avaliador = avaliador;
    }

    public EnumEstadoAvaliacao getEstado() {
        return estado;
    }

    public void setEstado(EnumEstadoAvaliacao estado) {
        this.estado = estado;
    }
    
    @Override
    public Object toBusiness() {
        Avaliacao avaliacao = new Avaliacao();
        if (this.getCodAvaliacao() > 0) {
            avaliacao.setCodAvaliacao(this.getCodAvaliacao());
        }
        Participante p = (Participante) this.getParticipante().toBusiness();
        avaliacao.setParticipante(p);
        avaliacao.setAvaliador((UsuarioDarwin) this.getAvaliador().toBusiness());
        avaliacao.setAprovado(this.isAprovado());
        avaliacao.setObservacao(this.getObservacao());
        avaliacao.setNota(this.getNota());
        avaliacao.setEstado(this.getEstado());
        return avaliacao;
    }

    @Override
    public Beans toBeans(Object object) {
        if (object != null) {
            if (object instanceof Avaliacao) {
                Avaliacao avaliacao = (Avaliacao) object;
                this.setCodAvaliacao(avaliacao.getCodAvaliacao());
                ParticipanteBeans pb =  new ParticipanteBeans();
                pb.toBeans(avaliacao.getParticipante());
                this.setParticipante(pb);
                UsuarioBeans ub =  new UsuarioBeans();
                ub.toBeans(avaliacao.getAvaliador());
                this.setAvaliador(ub);
                this.setAprovado(avaliacao.isAprovado());
                this.setObservacao(avaliacao.getObservacao());
                this.setNota(avaliacao.getNota());
                this.setEstado(avaliacao.getEstado());
                return this;
            } else {
                throw new IllegalArgumentException("Isso não é uma avaliação!");
            }
        } else {
            throw new NullPointerException("Avaliação informada não pode ser nula!");
        }
    }

}
