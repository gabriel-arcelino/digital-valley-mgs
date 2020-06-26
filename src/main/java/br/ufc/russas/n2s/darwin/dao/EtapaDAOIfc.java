package br.ufc.russas.n2s.darwin.dao;

import br.ufc.russas.n2s.darwin.model.Etapa;
import java.util.List;

/**
 *
 * @author Wallison Carlos
 */
public interface EtapaDAOIfc {

    /**
     * Método resposável por fazer a persistência de uma etapa.
     * @param etapa
     * @return Etapa
     */
	public Etapa adicionaEtapa(Etapa etapa);

    /**
     * Método resposável por fazer a atualização de uma etapa.
     * @param etapa
     */
	public Etapa atualizaEtapa(Etapa etapa);

    /**
     * Método resposável por fazer a remoção de uma etapa.
     * @param etapa
     */
	public void removeEtapa(Etapa etapa);

    /**
     * Método resposável por fazer a listagem de todas as etapas.
     * @return List<Etapa>
     */
	public List<Etapa> listaEtapas(Etapa etapa);

    /**
     * Método resposável por pegar do banco de dados uma etapa a partir do
     * código informado.
     * @param codEtapa
     * @return Etapa
     */
	public Etapa getEtapa(Etapa etapa);
    
}
