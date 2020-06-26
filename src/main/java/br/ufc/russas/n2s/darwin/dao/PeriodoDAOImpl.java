package br.ufc.russas.n2s.darwin.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import br.ufc.russas.n2s.darwin.model.Periodo;

/**
 *
 * @author Wallison Carlos
 */
public class PeriodoDAOImpl implements PeriodoDAOIfc{
    
    private DAOIfc<Periodo> daoImpl;
    
    @Autowired
    public void setDAOIfc(@Qualifier("daoImpl")DAOIfc<Periodo> dao){
        this.daoImpl = dao;
    }
    
    public PeriodoDAOImpl(){}

    @Override
    public void adicionaPeriodo(Periodo periodo) {
        this.daoImpl.adiciona(periodo);
    }

    @Override
    public void atualizaPeriodo(Periodo periodo) {
        this.daoImpl.atualiza(periodo);
    }

    @Override
    public void removePeriodo(Periodo periodo) {
        this.daoImpl.remove(periodo);
    }

    @Override
    public List<Periodo> listaPeriodos(Periodo  periodo) {
        return this.daoImpl.lista(periodo);
    }

    @Override
    public Periodo getPeriodo(Periodo  periodo) {
       return this.daoImpl.getObject(periodo, periodo.getCodPeriodo());
    }


}
