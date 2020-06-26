package br.ufc.russas.n2s.darwin.dao;

import java.util.List;


import br.ufc.russas.n2s.darwin.model.Arquivo;

public interface ArquivoDAOIfc {

    public Arquivo adicionaArquivo(Arquivo arquivo);

    public Arquivo atualizaArquivo(Arquivo arquivo);

    public void removeArquivo(Arquivo arquivo);

    public List<Arquivo> listaArquivos(Arquivo arquivo);

    public Arquivo getArquivos(Arquivo arquivo);
}
