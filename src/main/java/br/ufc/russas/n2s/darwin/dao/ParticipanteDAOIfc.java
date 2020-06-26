package br.ufc.russas.n2s.darwin.dao;

import br.ufc.russas.n2s.darwin.model.Participante;
import java.util.List;

/**
 *
 * @author N2S-PC03
 */
public interface ParticipanteDAOIfc {

    /**
     * Método resposável por fazer a persistência de um participante.
     * @param participante
     */
    public void adicionaParticipante(Participante participante);

    /**
     * Método resposável por fazer a atualização de um participante.
     * @param participante
     */
    public void atualizaParticipante(Participante participante);

    /**
     * Método resposável por fazer a remoção de um participante.
     * @param participante
     */
    public void removeParticipante(Participante participante);

    /**
     * Método resposável por fazer a listagem de todos os participantes.
     * @return
     */
    public List<Participante> listaParticipantes(Participante participante);

    /**
     * Método resposável por pegar do banco de dados um
     * participante a partir do código informadao.
     * @param codigo
     * @return
     */
    public Participante getParticipante(Participante participante);
    
    
    public List<Participante> listaParticipantesPorEtapa(int codEtapa);
    
}
