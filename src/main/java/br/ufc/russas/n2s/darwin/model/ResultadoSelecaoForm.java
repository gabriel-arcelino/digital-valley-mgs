package br.ufc.russas.n2s.darwin.model;

import java.util.List;

import br.ufc.russas.n2s.darwin.beans.EtapaBeans;

public class ResultadoSelecaoForm {

	private List<EtapaBeans> etapas;
	
	public ResultadoSelecaoForm( ) {
	}
	
	public List<EtapaBeans> getEtapas () {
		return this.etapas;
	}
	
	public void setEtapas(List<EtapaBeans> listaEtapas) {
		this.etapas = listaEtapas;
	}
}

