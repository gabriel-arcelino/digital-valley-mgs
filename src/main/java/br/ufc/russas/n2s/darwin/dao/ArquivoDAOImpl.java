package br.ufc.russas.n2s.darwin.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import br.ufc.russas.n2s.darwin.model.Arquivo;

public class ArquivoDAOImpl implements ArquivoDAOIfc {
	
	private DAOIfc<Arquivo> daoImpl;

    @Autowired
    public void setDAOIfc(@Qualifier("daoImpl")DAOIfc<Arquivo> dao) {
        this.daoImpl = dao;
    }
    
    @Override
    public Arquivo adicionaArquivo(Arquivo arquivo) {
        return this.daoImpl.adiciona(arquivo);
    }

    @Override
    public Arquivo atualizaArquivo(Arquivo arquivo) {
        return this.daoImpl.atualiza(arquivo);
    }

    @Override
    public void removeArquivo(Arquivo arquivo) {
        this.daoImpl.remove(arquivo);
    }

    @Override
    public List<Arquivo> listaArquivos(Arquivo arquivo) {
        return this.daoImpl.lista(arquivo);
    }

    @Override
    public Arquivo getArquivos(Arquivo arquivo) {
        return this.daoImpl.getObject(arquivo, arquivo.getCodArquivo());
    }
}
