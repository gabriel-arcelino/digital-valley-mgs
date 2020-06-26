package br.ufc.russas.n2s.darwin.dao;

import br.ufc.russas.n2s.darwin.model.Documentacao;
import java.util.List;

/**
 *
 * @author Wallison Carlos
 */
public interface DocumentacaoDAOIfc {

    /**
     * Método resposável por fazer a persistência de uma documentacao.
     * @param documentacao
     * @return
     */
    public Documentacao adicionaDocumentacao(Documentacao documentacao);

    /**
     * Método resposável por fazer a atualização de uma documentacao.
     * @param documentacao
     * @return
     */
    public Documentacao atualizaDocumentacao(Documentacao documentacao);

    /**
     * Método resposável por fazer a remoção de uma documentacao.
     * @param documentacao
     */
    public void removeDocumentacao(Documentacao documentacao);

    /**
     * Método resposável por fazer a listagem de todas as documentações.
     * @return List<Documentacao>
     */
    public List<Documentacao> listaDocumentacoes(Documentacao documentacao);

    /**
     * Método resposável por pegar do banco de dados uma documentacao a
     * partir do código informado.
     * @param codDocumentacao
     * @return Documentacao
     */
    public Documentacao getDocumentacao(Documentacao documentacao);
}
