/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.model.exception;

import java.io.Serializable;

/**
 *
 * @author Wallison Carlos
 */
public class IllegalCodeException extends IllegalArgumentException implements Serializable{

	private static final long serialVersionUID = 1L;

	public IllegalCodeException() {

    }

    public IllegalCodeException(String message) {
        super(message);
    }
}
