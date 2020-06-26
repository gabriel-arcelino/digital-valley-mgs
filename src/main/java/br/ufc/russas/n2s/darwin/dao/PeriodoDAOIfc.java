package br.ufc.russas.n2s.darwin.dao;

import br.ufc.russas.n2s.darwin.model.Periodo;
import java.util.List;

/**
 *
 * @author Wallison Carlos
 */
public interface PeriodoDAOIfc{

    /**
     * Método resposável por fazer a persistência de um período.
     * @param periodo
     */
    public void adicionaPeriodo(Periodo  periodo);

    /**
     * Método resposável por fazer a atualização de um período.
     * @param periodo
     */
    public void atualizaPeriodo(Periodo  periodo);

    /**
     * Método resposável por fazer a remoção de um período.
     * @param periodo
     */
    public void removePeriodo(Periodo  periodo);

    /**
     * Método resposável por fazer a listagem de todos os períodos.
     * @return List<Periodo>
     */
    public List<Periodo> listaPeriodos(Periodo  periodo);

    /**
     * Método resposável por pegar do banco de dados um
     * período a partir do código informadao.
     * @param codPeriodo
     * @return Periodo
     */
    public Periodo getPeriodo(Periodo  periodo);
    
}
