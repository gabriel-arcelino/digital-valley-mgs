/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.service;

import br.ufc.russas.n2s.darwin.beans.ParticipanteBeans;
import br.ufc.russas.n2s.darwin.dao.ParticipanteDAOIfc;
import br.ufc.russas.n2s.darwin.model.Participante;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author N2S-PC03
 */
public class ParticipanteServiceImpl implements ParticipanteServiceIfc {

     private ParticipanteDAOIfc participanteDAOIfc;

    public ParticipanteServiceImpl() {
    }

    public final ParticipanteDAOIfc getParticipanteDAOIfc() {
        return participanteDAOIfc;
    }

    @Autowired(required = true)
    public void setParticipanteDAOIfc(@Qualifier("participanteDAOIfc") ParticipanteDAOIfc participanteDAOIfc) {
        this.participanteDAOIfc = participanteDAOIfc;
    }

    @Override
    public final void adicionaParticipante(final ParticipanteBeans participante) {
        this.participanteDAOIfc.adicionaParticipante((Participante) participante.toBusiness());
    }

    @Override
    public final void atualizaParticipante(final ParticipanteBeans participante) {
        this.participanteDAOIfc.atualizaParticipante((Participante) participante.toBusiness());
    }

    @Override
    public final void removeParticipante(final ParticipanteBeans participante) {
        this.participanteDAOIfc.removeParticipante((Participante) participante.toBusiness());
    }

    @Override
    public final List<ParticipanteBeans> listaTodosParticipantes() {
        Participante participante = new Participante();
        List<ParticipanteBeans> participantes = Collections.synchronizedList(new ArrayList<ParticipanteBeans>());
        List<Participante> resultados = this.participanteDAOIfc.listaParticipantes(participante);
        for (Participante p : resultados) {
            participantes.add((ParticipanteBeans) new ParticipanteBeans().toBeans(p));
        }
        return participantes;
    }

    @Override
    public final ParticipanteBeans getParticipante(final long codParticipante) {
        Participante participante = new Participante();
        participante.setCodParticipante(codParticipante);
        return (ParticipanteBeans) new ParticipanteBeans().toBeans(this.participanteDAOIfc.getParticipante(participante));
    }
    
    //EM producao ainda
    @Override
    public  List<ParticipanteBeans> ordenarPorNome(List<ParticipanteBeans> participantes) {
    	if (participantes != null) {
    		
    		if (participantes.size() == 1) return participantes;
	    	List<ParticipanteBeans> p = new ArrayList<>();
	    	ParticipanteBeans menor = participantes.get(0);
	    	int tam = 0;
	    	tam = participantes.size();
	    	
	    	for (int j=0;j<tam;j++) {
		    	for (int i=1;i < tam; i++) {
		    		if ((participantes.get(i).getCandidato().getNome().compareToIgnoreCase(menor.getCandidato().getNome())) <= -1) {
		    			menor = participantes.get(i);
		    		}
		    	}
		    	p.add(menor);
	    	}
	    	return p;
    	
    	} else {return null;}
    	
    }

}
