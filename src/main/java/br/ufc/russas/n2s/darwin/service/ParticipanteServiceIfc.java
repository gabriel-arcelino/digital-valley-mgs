/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.service;

import br.ufc.russas.n2s.darwin.beans.ParticipanteBeans;
import java.util.List;

/**
 *
 * @author N2S-PC03
 */
public interface ParticipanteServiceIfc {
     public void adicionaParticipante(ParticipanteBeans participante);
     public void atualizaParticipante(ParticipanteBeans participante);
     public void removeParticipante(ParticipanteBeans participante);
     public List<ParticipanteBeans> listaTodosParticipantes();
     public ParticipanteBeans getParticipante(long codParticipante);
     public List<ParticipanteBeans> ordenarPorNome(List<ParticipanteBeans> participantes);
}
