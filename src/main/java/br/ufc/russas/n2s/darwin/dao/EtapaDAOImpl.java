package br.ufc.russas.n2s.darwin.dao;

import br.ufc.russas.n2s.darwin.model.Etapa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wallison Carlos
 */
@Repository("etapaDAOIfc")
@Transactional
public class EtapaDAOImpl implements EtapaDAOIfc{

    private DAOIfc<Etapa> daoImpl;

    @Autowired(required = true)
    public void setDAOIfc(@Qualifier("daoImpl")DAOIfc<Etapa> dao){
        this.daoImpl = dao;
    }
    
    @Override
    public Etapa adicionaEtapa(Etapa etapa) {
        return this.daoImpl.adiciona(etapa);
    }

    @Override
    public Etapa atualizaEtapa(Etapa etapa) {
        return this.daoImpl.atualiza(etapa);
    }

    @Override
    public void removeEtapa(Etapa etapa) {
        this.daoImpl.remove(etapa);
    }

    @Override
    public List<Etapa> listaEtapas(Etapa etapa) {
        return this.daoImpl.lista(etapa);
    }

    @Override
    public Etapa getEtapa(Etapa etapa) {
        return this.daoImpl.getObject(etapa, etapa.getCodEtapa());
    }
    
}
