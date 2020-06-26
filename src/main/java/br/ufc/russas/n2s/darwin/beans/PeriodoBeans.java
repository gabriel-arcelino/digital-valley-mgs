package br.ufc.russas.n2s.darwin.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import br.ufc.russas.n2s.darwin.model.Periodo;

/**
 *
 * @author Lavínia Matoso
 */
public class PeriodoBeans implements Beans, Serializable{

	private static final long serialVersionUID = -4072483774813824199L;
	
	private long codPeriodo;
    private LocalDate inicio;
    private LocalDate termino;
    
    public PeriodoBeans(){}
    public PeriodoBeans(long codPeriodo, LocalDate inicio, LocalDate termino) {
        this.codPeriodo = codPeriodo;
        this.inicio = inicio;
        this.termino = termino;
    }
     
    public long getCodPeriodo() {
        return codPeriodo;
    }

    public void setCodPeriodo(long codPeriodo) {
        this.codPeriodo = codPeriodo;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getTermino() {
        return termino;
    }

    public void setTermino(LocalDate termino) {
        this.termino = termino;
    }
    
    public String getDataInicio(){
        return inicio.format(DateTimeFormatter.ofPattern("dd MMMM uuuu", Locale.getDefault()));
    }
    public String getDataTermino(){
        return termino.format(DateTimeFormatter.ofPattern("dd MMMM uuuu", Locale.getDefault()));
    }   
    
    @Override
    public Object toBusiness() {
        Periodo periodo = new Periodo();
        
        if(this.getCodPeriodo() > 0){
            periodo.setCodPeriodo(this.getCodPeriodo());
        }
        periodo.setInicio(this.getInicio());
        periodo.setTermino(this.getTermino());
        
        return periodo;
    }

    @Override
    public Beans toBeans(Object object) {
        if(object != null){
            if(object instanceof Periodo){
                Periodo periodo = (Periodo) object;
                this.setCodPeriodo(periodo.getCodPeriodo());
                this.setInicio(periodo.getInicio());
                this.setTermino(periodo.getTermino());
                
                return this;
            }else{
                throw new IllegalArgumentException("O objeto a ser adicionado não é um Periodo!");
            }
        }else{
            throw new NullPointerException("Periodo não pode ser nula!");
        }
        
    }
    
    
}
