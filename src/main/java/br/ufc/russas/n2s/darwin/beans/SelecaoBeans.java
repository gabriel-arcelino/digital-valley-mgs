package br.ufc.russas.n2s.darwin.beans;


import br.ufc.russas.n2s.darwin.model.Arquivo;
import br.ufc.russas.n2s.darwin.model.EnumEstadoSelecao;
import br.ufc.russas.n2s.darwin.model.Selecao;
import br.ufc.russas.n2s.darwin.model.Etapa;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;
import br.ufc.russas.n2s.darwin.service.EtapaServiceIfc;
import br.ufc.russas.n2s.darwin.service.EtapaServiceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Gilberto Lima, Wallison Rocha
 */
public class SelecaoBeans implements Beans, Serializable {

	private static final long serialVersionUID = 7834513120454258554L;
	
	private long codSelecao;
    private String titulo;
    private String descricao;
    private List<UsuarioBeans> responsaveis = Collections.synchronizedList(new ArrayList<UsuarioBeans>());
    private EtapaBeans inscricao;
    private List<EtapaBeans> etapas;
    private int vagasRemuneradas;
    private int vagasVoluntarias;
    private String descricaoPreRequisitos;
    private String areaDeConcentracao;
    private String categoria;
    private List<ArquivoBeans> aditivos;
    private List<ArquivoBeans> anexos;
    private ArquivoBeans edital;
    private EnumEstadoSelecao estado;
    private boolean divulgada;
    private boolean divulgadoResultado;
    private boolean deletada;
    private boolean exibirNotas;
    
    public SelecaoBeans(){}

    public long getCodSelecao() {
        return codSelecao;
    }

    public void setCodSelecao(long codSelecao) {
        this.codSelecao = codSelecao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<UsuarioBeans> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(List<UsuarioBeans> usuario) {
        this.responsaveis = usuario;
    }

    public EtapaBeans getInscricao() {
        return inscricao;
    }

    public void setInscricao(EtapaBeans inscricao) {
        this.inscricao = inscricao;
    }
    
    public List<EtapaBeans> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<EtapaBeans> etapas) {
    	EtapaServiceIfc etapaServ = new EtapaServiceImpl();
        this.etapas = etapaServ.ordenaEtapasPorData(etapas);
    }

    public int getVagasRemuneradas() {
        return vagasRemuneradas;
    }

    public void setVagasRemuneradas(int vagasRemuneradas) {
        this.vagasRemuneradas = vagasRemuneradas;
    }

    public int getVagasVoluntarias() {
        return vagasVoluntarias;
    }

    public void setVagasVoluntarias(int vagasVoluntarias) {
        this.vagasVoluntarias = vagasVoluntarias;
    }

    public String getDescricaoPreRequisitos() {
        return descricaoPreRequisitos;
    }

    public void setDescricaoPreRequisitos(String descricaoPreRequisitos) {
        this.descricaoPreRequisitos = descricaoPreRequisitos;
    }

    public String getAreaDeConcentracao() {
        return areaDeConcentracao;
    }

    public void setAreaDeConcentracao(String areaDeConcentracao) {
        this.areaDeConcentracao = areaDeConcentracao;
    }
    
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<ArquivoBeans> getAditivos() {
        return aditivos;
    }

    public void setAditivos(List<ArquivoBeans> aditivos) {
        this.aditivos = aditivos;
    }

    public List<ArquivoBeans> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<ArquivoBeans> anexos) {
        this.anexos = anexos;
    }

    public ArquivoBeans getEdital() {
        return edital;
    }

    public void setEdital(ArquivoBeans edital) {
        this.edital = edital;
    }

    public EnumEstadoSelecao getEstado() {
        return estado;
    }

    public void setEstado(EnumEstadoSelecao estado) {
        this.estado = estado;
    }

    public boolean isDivulgada() {
        return divulgada;
    }

    public void setDivulgada(boolean divulgada) {
        this.divulgada = divulgada;
    }
    
    public boolean isDivulgadoResultado() {
		return divulgadoResultado;
	}
    
    public void setDivulgadoResultado(boolean divulgadoResultado) {
		this.divulgadoResultado = divulgadoResultado;
	}
    
    public boolean isDeletada() {
		return deletada;
	}

	public void setDeletada(boolean deletada) {
		this.deletada = deletada;
	}
	
	public boolean isExibirNotas() {
		return exibirNotas;
	}

	public void setExibirNotas(boolean exibirNotas) {
		this.exibirNotas = exibirNotas;
	}
    
    @Override
    public Object toBusiness() { //Esse método transforma uma Beans em um objeto seleção
        Selecao selecao = new Selecao();
        
        if(this.getCodSelecao() > 0){
            selecao.setCodSelecao(this.getCodSelecao());
        }
        selecao.setTitulo(this.getTitulo());
        selecao.setDescricao(this.getDescricao());
        if (this.getInscricao() != null) {
            selecao.setInscricao((Etapa) this.getInscricao().toBusiness());
        }
        if(this.vagasRemuneradas >= 0) selecao.setVagasRemuneradas(this.getVagasRemuneradas());
        if(this.vagasVoluntarias >= 0) selecao.setVagasVoluntarias(this.getVagasVoluntarias());
        selecao.setDescricaoPreRequisitos(this.getDescricaoPreRequisitos());
        selecao.setAreaDeConcentracao(this.getAreaDeConcentracao());
        selecao.setCategoria(this.getCategoria());
        selecao.setEstado(this.getEstado());
        selecao.setDivulgadoResultado(this.isDivulgadoResultado());
        selecao.setDivulgada(this.isDivulgada());
        selecao.setDeletada(this.isDeletada());
        selecao.setExibirNotas(this.isExibirNotas());
        if (this.getEdital() != null) {
            selecao.setEdital((Arquivo) this.getEdital().toBusiness());
        }
       // selecao.setEstado(this.getEstado());
        
        List<UsuarioDarwin> responsaveis = Collections.synchronizedList(new ArrayList<UsuarioDarwin>());
        //Ajeitar
        if(this.getResponsaveis()!=null && this.getResponsaveis().size()>0 ){
            for(UsuarioBeans u : this.getResponsaveis()){
                responsaveis.add((UsuarioDarwin) u.toBusiness());
            }
        }
        selecao.setResponsavel(responsaveis);
        
        List<Etapa> etapas = Collections.synchronizedList(new ArrayList<Etapa>()); 
        if(this.getEtapas()!=null){
            for(int i=0;i<this.getEtapas().size();i++){
                etapas.add((Etapa) this.getEtapas().get(i).toBusiness());
            }
        }
        selecao.setEtapas(etapas);
        
        List<Arquivo> aditivos = Collections.synchronizedList(new ArrayList<Arquivo>());
        if (this.getAditivos()!=null) {
            for (int i=0;i<this.getAditivos().size();i++) {
                aditivos.add((Arquivo) this.getAditivos().get(i).toBusiness());
            }
        }
        selecao.setAditivos(aditivos);
        
        List<Arquivo> listaAnexos = Collections.synchronizedList(new ArrayList<Arquivo>());
        if (this.getAnexos()!=null) {
            for (int i=0;i<this.getAnexos().size();i++) {
                listaAnexos.add((Arquivo) this.getAnexos().get(i).toBusiness());
            }
        }
        selecao.setAnexos(listaAnexos);
        return selecao;
    }

    @Override
    public Beans toBeans(Object object) { //Esse método transforma um objeto Seleção em uma Beans
        if(object != null){
            if(object instanceof Selecao){
                Selecao selecao = (Selecao) object;
                this.setCodSelecao(selecao.getCodSelecao());
                this.setTitulo(selecao.getTitulo());
                this.setDescricao(selecao.getDescricao());
                this.setVagasRemuneradas(selecao.getVagasRemuneradas());
                this.setVagasVoluntarias(selecao.getVagasVoluntarias());
                this.setDescricaoPreRequisitos(selecao.getDescricaoPreRequisitos());
                this.setAreaDeConcentracao(selecao.getAreaDeConcentracao());
                this.setCategoria(selecao.getCategoria());
                this.setEstado(selecao.getEstado());
                this.setDivulgada(selecao.isDivulgada());
                this.setDivulgadoResultado(selecao.isDivulgadoResultado());
                this.setDeletada(selecao.isDeletada());
                this.setExibirNotas(selecao.isExibirNotas());
                EtapaBeans eb = null;
                if(selecao.getInscricao()!=null){
                   eb = (EtapaBeans) (new EtapaBeans().toBeans(selecao.getInscricao()));
                }
                this.setInscricao(eb);
                ArquivoBeans ab = null;
                if(selecao.getEdital()!=null){
                   ab = (ArquivoBeans) (new ArquivoBeans().toBeans(selecao.getEdital()));
                }
                this.setEdital(ab);
                List<UsuarioBeans> responsaveis = Collections.synchronizedList(new ArrayList<UsuarioBeans>());
                if (selecao.getResponsaveis() != null) {
                for (UsuarioDarwin u : selecao.getResponsaveis())
                    responsaveis.add((UsuarioBeans) new UsuarioBeans().toBeans(u));
                }
                this.setResponsaveis(responsaveis);
                List<ArquivoBeans> aditivos = Collections.synchronizedList(new ArrayList<ArquivoBeans>());
                if (selecao.getAditivos()!= null) {
                for (Arquivo a : selecao.getAditivos())
                    aditivos.add((ArquivoBeans) new ArquivoBeans().toBeans(a));
                }
                this.setAditivos(aditivos);
                List<ArquivoBeans> anexos = Collections.synchronizedList(new ArrayList<ArquivoBeans>());
                if (selecao.getAditivos()!= null) {
                for (Arquivo a : selecao.getAnexos())
                    anexos.add((ArquivoBeans) new ArquivoBeans().toBeans(a));
                }
                this.setAnexos(anexos);
                List<EtapaBeans> etapas = Collections.synchronizedList(new ArrayList<EtapaBeans>());
                for (Etapa etapa : selecao.getEtapas()) {
                    etapas.add((EtapaBeans) new EtapaBeans().toBeans(etapa));
                }
                this.setEtapas(etapas);
                return this;
            }else{
                throw new IllegalArgumentException("O objeto a ser adicionado não é uma Seleção!");
            }
        }else{
            throw new NullPointerException("Seleção não pode ser nula!");
        }
    }
    
}


