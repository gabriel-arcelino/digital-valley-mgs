package br.ufc.russas.n2s.darwin.model.exception;

import java.util.List;

public class AvaliadorException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private List<List<String> > etapasSemAvaliadores;
	private String msg;
	
	public AvaliadorException(String msg, List<List<String> > etapasSemAvaliadores) {
		this.msg = msg;
		this.etapasSemAvaliadores = etapasSemAvaliadores;
	}

	public List<List<String>> getEtapasSemAvaliadores() {
		return etapasSemAvaliadores;
	}

	public String getMsg() {
		return msg;
	}
	
}
