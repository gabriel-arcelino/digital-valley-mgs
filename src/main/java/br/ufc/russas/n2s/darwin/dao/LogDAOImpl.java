package br.ufc.russas.n2s.darwin.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import br.ufc.russas.n2s.darwin.model.Log;

@Repository("logDAOIfc")
public class LogDAOImpl implements LogDAOIfc{

	private DAOIfc<Log> daoImpl;
	  
	@Autowired
    public void setDAOIfc(@Qualifier("daoImpl")DAOIfc<Log> dao){
        this.daoImpl = dao;
    }
	 
	@Override
	public void adicionaLog(Log log) {
		this.daoImpl.adiciona(log);
	}

}
