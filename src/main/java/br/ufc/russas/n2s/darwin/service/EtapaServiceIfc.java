package br.ufc.russas.n2s.darwin.service;

import java.util.List;

import br.ufc.russas.n2s.darwin.beans.AvaliacaoBeans;
import br.ufc.russas.n2s.darwin.beans.DocumentacaoBeans;
import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.ParticipanteBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;

/**
 *
 * @author Wallison Carlos
 */
public interface EtapaServiceIfc extends ServiceIfc {
	public EtapaBeans adicionaEtapa(SelecaoBeans selecao, EtapaBeans etapa) throws IllegalAccessException;

	public EtapaBeans atualizaEtapa(SelecaoBeans selecao, EtapaBeans etapa) throws IllegalAccessException;

	public EtapaBeans atualizaEtapa(EtapaBeans etepa);

	public void removeEtapa(EtapaBeans etapa);

	public List<EtapaBeans> listaTodasEtapas();

	public EtapaBeans getEtapa(long codEtapa);

	public boolean isParticipante(ParticipanteBeans participante);

	public boolean isParticipante(EtapaBeans etapa, UsuarioBeans participante);

	public ParticipanteBeans getParticipante(EtapaBeans etapa, UsuarioBeans usuario);

	public void anexaDocumentacao(EtapaBeans etapa, ParticipanteBeans participante, DocumentacaoBeans documentacao)
			throws IllegalAccessException;

	public void avalia(EtapaBeans etapa, AvaliacaoBeans avaliacao) throws IllegalAccessException;

	public List<Object[]> getParticipantes(EtapaBeans etapa);

	public List<Object[]> getResultado(EtapaBeans etapa);

	public SelecaoBeans getSelecao(EtapaBeans etapa);

	public void participa(EtapaBeans inscricao, ParticipanteBeans participante) throws IllegalAccessException;

	public void participa(EtapaBeans inscricao, ParticipanteBeans participante, DocumentacaoBeans documentacao)
			throws IllegalAccessException;

	public Object[] getSituacao(EtapaBeans etapa, UsuarioBeans usuario);

	public List<EtapaBeans> ordenaEtapasPorData(List<EtapaBeans> etapas);

	public List<AvaliacaoBeans> getAvaliacoesParticipante(ParticipanteBeans participante, long codEtapa);
}
