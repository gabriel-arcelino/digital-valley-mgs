/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufc.russas.n2s.darwin.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.ufc.russas.n2s.darwin.dao.EtapaDAOIfc;
import br.ufc.russas.n2s.darwin.dao.EtapaDAOImpl;
import br.ufc.russas.n2s.darwin.dao.SelecaoDAOIfc;
import br.ufc.russas.n2s.darwin.dao.SelecaoDAOImpl;

/**
 *
 * @author N2S-PC03
 */
@Entity
@Converter(autoApply = true)
@Table(name = "selecao")
public class Selecao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codSelecao")
    private long codSelecao;
    
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @ManyToMany(targetEntity = UsuarioDarwin.class, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "responsaveis_selecao", joinColumns = {@JoinColumn(name = "selecao", referencedColumnName = "codSelecao")},
    inverseJoinColumns = {@JoinColumn(name = "usuario", referencedColumnName = "codUsuario")})
    private List<UsuarioDarwin> responsaveis;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "periodo", referencedColumnName = "codPeriodo")
    private Periodo periodo;
    
    @ManyToOne(targetEntity = Etapa.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "etapa_inscricao", referencedColumnName = "codEtapa")
    private Etapa inscricao;
    
    //@ManyToMany(targetEntity = Etapa.class,  cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ManyToMany(targetEntity = Etapa.class,  cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "etapas_selecao", joinColumns = {@JoinColumn(name = "selecao", referencedColumnName = "codSelecao")},
    inverseJoinColumns = {@JoinColumn(name = "etapa", referencedColumnName = "codEtapa")})
    private List<Etapa> etapas;
    private int vagasRemuneradas;
    private int vagasVoluntarias;
    @Column(columnDefinition = "TEXT")
    private String descricaoPreRequisitos;
    private String areaDeConcentracao;
    private String categoria;
    @ManyToMany(targetEntity = Arquivo.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name="aditivos_selecao", joinColumns = {@JoinColumn(name = "selecao", referencedColumnName = "codSelecao")},
    inverseJoinColumns = {@JoinColumn(name = "arquivo", referencedColumnName = "codArquivo")})
    private List<Arquivo> aditivos;
    @ManyToMany(targetEntity = Arquivo.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name="anexos_selecao", joinColumns = {@JoinColumn(name = "selecao", referencedColumnName = "codSelecao")},
    inverseJoinColumns = {@JoinColumn(name = "arquivo", referencedColumnName = "codArquivo")})
    private List<Arquivo> anexos;
    @ManyToOne(targetEntity = Arquivo.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "edital", referencedColumnName = "codArquivo")
    private Arquivo edital;
    @Enumerated(EnumType.ORDINAL)
    private EnumEstadoSelecao estado;
    private boolean divulgada;
    private boolean divulgadoResultado;
    private boolean deletada;
    private boolean exibirNotas;
    

	public Selecao() {
    }

    public long getCodSelecao() {
        return codSelecao;
    }

    public void setCodSelecao(long codSelecao) {
        if (codSelecao > 0) {
            this.codSelecao = codSelecao;
        }
    }

    public final String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo != null) {
            this.titulo = titulo;
        }
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao != null) {
            this.descricao = descricao;
        } else {
            throw new IllegalArgumentException("Descrição da seleção não deve ser vazia!");
        }
    }

    public List<UsuarioDarwin> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsavel(List<UsuarioDarwin> responsaveis) {
        if (responsaveis != null) {
            this.responsaveis = responsaveis;
        } else {
            throw new IllegalArgumentException("Lista de responsaveis não pode ser nula!");
        }
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        if (periodo != null) {
            this.periodo = periodo;
        } else {
            throw new IllegalArgumentException("Deve ser selecionado um periodo para a seleção!");
        }
    }

    public Etapa getInscricao() {
        return inscricao;
    }

    public void setInscricao(Etapa inscricao) {
        if (inscricao != null) {
            this.inscricao = inscricao;
        } else {
            throw new IllegalArgumentException("Etapa de inscrição não pode ser nula!");
        }
    }

    public List<Etapa> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<Etapa> etapas) {
        if (etapas != null) {
            this.etapas = etapas;
        } else {
            throw new IllegalArgumentException("Lista de etapas não pode ser nula!");
        }
    }

    public int getVagasRemuneradas() {
        return vagasRemuneradas;
    }

    public void setVagasRemuneradas(int vagasRemuneradas) {
        if (vagasRemuneradas >= 0) {
            this.vagasRemuneradas = vagasRemuneradas;
        } else {
            throw new IllegalArgumentException("O número de vagas remuneradas não pode ser menor que zero!");
        }
    }

    public int getVagasVoluntarias() {
        return vagasVoluntarias;
    }

    public void setVagasVoluntarias(int vagasVoluntarias) {
        if (vagasVoluntarias >= 0){
            this.vagasVoluntarias = vagasVoluntarias;
        } else {
            throw new IllegalArgumentException("O número de vagas voluntarias não pode ser menor que zero!");
        }
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

    public void setAreaDeConcentracao(final String areaDeConcentracao) {
        if (areaDeConcentracao != null) {
            this.areaDeConcentracao = areaDeConcentracao;
        } else {
            throw new IllegalArgumentException("Área de concentração não pode ser vazia!");
        }
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        if (categoria != null) {
            this.categoria = categoria;
        } else {
            throw new IllegalArgumentException("Categoria da seleção não pode ser nula!");
        }
    }

    public List<Arquivo> getAditivos() {
        return aditivos;
    }

    public void setAditivos(List<Arquivo> aditivos) {
        this.aditivos = aditivos;
    }

    public List<Arquivo> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Arquivo> anexos) {
        this.anexos = anexos;
    }

    public Arquivo getEdital() {
        return edital;
    }

    public void setEdital(Arquivo edital) {
        this.edital = edital;
    }

    public EnumEstadoSelecao getEstado() {
        return estado;
    }

    public void setEstado(EnumEstadoSelecao estado) {
        if (estado != null) {
            this.estado = estado;
        } else {
            throw new IllegalArgumentException("Estado da seleção não pode ser nulo!");
        }
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
	
    
    public Selecao adicionaSelecao() {
        return this;
    }
    
    public Selecao atualizaSelecao() {
        return this;
    }
    
    public Etapa adicionaEtapa(Etapa etapa) {
        if (etapa != null) {
            etapas.add(etapa);
            return etapa;
        } else {
            throw new IllegalArgumentException("Etapa adicionada não pode ser nula!");
        }
    }
    
    public Etapa atualizaEtapa(Etapa etapa) {
        if (etapa != null) {            
            EtapaDAOIfc etapaDAOIfc = new EtapaDAOImpl();
            return etapaDAOIfc.atualizaEtapa(etapa);
        } else {
            throw new IllegalArgumentException("Etapa para atualizar não pode ser nula!");
        }
    }
    
    public void adicionaResponsavel(UsuarioDarwin responsavel) {
        SelecaoDAOIfc selecaoDAOIfc = new SelecaoDAOImpl();
        if (responsaveis.isEmpty()) {
            if (responsavel != null) {
                this.responsaveis.add(responsavel);
                selecaoDAOIfc.atualizaSelecao(this);
            } else {
                throw new IllegalArgumentException("Deve ser selecionado um responsável para a seleção!");
            }
        } else {
            if (responsavel != null) {
                this.responsaveis.add(responsavel);
                selecaoDAOIfc.atualizaSelecao(this);
            } else {
                throw new IllegalArgumentException("Responsável não pode ser nulo!");
            }
        }
    }
    
    public boolean isResponsavel(UsuarioDarwin responsavel) {
        if (responsavel != null) {
            if (this.responsaveis.contains(responsavel)) {
                return true;
            }
        } else {
            throw new IllegalArgumentException("Responsavel não pode ser nulo!");
        }
        return false;
    }
    
    public void removeResponsavel(UsuarioDarwin responsavel) {    
        if (this.getResponsaveis().size() > 1) {
            if (responsavel != null) {
                this.responsaveis.remove(responsavel);
                SelecaoDAOIfc selecaoDAOIfc = new SelecaoDAOImpl();
                selecaoDAOIfc.atualizaSelecao(this);
            } else {
                throw new IllegalArgumentException("Responsavel não pode ser nulo!");
            }
        } else {
            throw new IllegalArgumentException("Não é possível remover responsável, pois só existe um na lista de responsáveis!");
        }
    }

    public void removeEtapa(Etapa etapa){
        if (etapa != null) {
            if (!etapa.getEstado().equals(EnumEstadoEtapa.ANDAMENTO)) {
                this.etapas.remove(etapa);
            } else {
                throw new IllegalArgumentException("Etapa em andamento, não sendo possível remove-la!");
            }
        } else {
            throw new IllegalArgumentException("Etapa não pode ser nula!");
        }
    }

    public Etapa getEtapaAtual() {
    	Etapa etapa = this.getUltimaEtapa();
    	if (this.getInscricao() != null && 
    			(((this.getInscricao().getPeriodo().getInicio().isBefore(LocalDate.now())
    					|| this.getInscricao().getPeriodo().getInicio().isEqual(LocalDate.now())) 
    					&& (this.getInscricao().getPeriodo().getTermino().isAfter(LocalDate.now()) 
    							|| this.getInscricao().getPeriodo().getTermino().isEqual(LocalDate.now()))) 
    			|| this.getInscricao().getPeriodo().getInicio().isAfter(LocalDate.now()))) {
    		etapa = this.getInscricao();
    	} else {
	        if (this.etapas != null && !this.etapas.isEmpty()) {
	            for (Etapa e: etapas) {
	                if ((e.getPeriodo().getInicio().isBefore(LocalDate.now()) || e.getPeriodo().getInicio().isEqual(LocalDate.now())) && (e.getPeriodo().getTermino().isAfter(LocalDate.now()) || e.getPeriodo().getTermino().isEqual(LocalDate.now()))) {
	                	etapa = e;
	                	break;
	                }
	            }
	        } 
    	}
        return etapa;
    }
    
    public Etapa getUltimaEtapa() {
        Etapa etapa = this.getInscricao();
        if (etapa != null) {
            if (this.etapas != null && !this.etapas.isEmpty()) {
                etapa = this.getEtapas().get(0);
                for (Etapa e: etapas) {
                    if (e.getPeriodo().getInicio().isAfter(etapa.getPeriodo().getInicio())) {
                        etapa = e;
                    }
                }
            }
        } else {
            if (getInscricao() == null) {
                
            } else {
                return getInscricao();
            }
        }
        return etapa;
    }
    /**
     * Método resposável por calcular o resultado de uma seleção 
     * @param 
     * @return List<List<Object>>
     * List<List<Object>> - Uma tabela (ou matriz) usando a estrutura de dados List,
     * sendo cada linha o resultado de um participante onde as colunas são: 
     * Objeto Participante | (float) Nota etapa 1 com nota | (float) Nota etapa 2 com nota | ... | (float) Nota etapa N com nota | (float) Média geral | (int) Colocação | (String) Situação
     */        
    public List<ResultadoParticipanteSelecao> resultado () throws IllegalAccessException {
    	Etapa ultima = this.getUltimaEtapa();
    	if (ultima.getPeriodo().getTermino().isBefore(LocalDate.now())) {
    		List<Object[]> resultadoEtapaFinal = ultima.getResultado();
    		List<ResultadoParticipanteSelecao> resultadoSelecao = Collections.synchronizedList(new ArrayList<ResultadoParticipanteSelecao>());
    		List<Etapa> porNotas = this.getEtapas().stream()
                    .filter( EtapaPredicates.isNota())
                    .collect(Collectors.<Etapa>toList());
    		
    		if (porNotas.size() > 0) {
	    		for (int i =0 ;i < porNotas.size()-1;i++) {
	    			Etapa e = porNotas.get(i);
	    			int menor = i;
	    			for (int j = i+1;j<porNotas.size();j++) {
	    				if (porNotas.get(j).getPosiscaoCriterioDesempate() < e.getPosiscaoCriterioDesempate()) {
	    					menor = j;
	    				}
	    			}
	    			Etapa aux = porNotas.get(menor);
	    			porNotas.set(menor, e);
					porNotas.set(i, aux);
	    		}
	    		for (int i = 0;i < resultadoEtapaFinal.size();i++) {
	    			Object[] r = resultadoEtapaFinal.get(i);
	    			Participante p = (Participante) r[0];
	    			
	    			float sumGeral = 0;
	    			float contadorGeral = 0;
	    			ResultadoParticipanteSelecao resultadoParticipanteFinal = new ResultadoParticipanteSelecao();
	    			resultadoParticipanteFinal.setParticipante(p);
	    			for (int j = 0;j < porNotas.size();j++) {
	    				Etapa etapa = porNotas.get(j);
	    				for (int k = 0;k < etapa.getResultado().size();k++) {
	    					Object[] resultadoParticipante = etapa.getResultado().get(k);
	    					if (p.equals((Participante) resultadoParticipante[0])) {
	    						resultadoParticipanteFinal.getNotasEtapas().add((float) resultadoParticipante[3]);
	    						sumGeral += (float) resultadoParticipante[3] * etapa.getPesoNota();
	    						break;
	    					}
	    				}
	    				resultadoParticipanteFinal.getEtapas().add(etapa);
	    				contadorGeral +=  etapa.getPesoNota();
	    			}
	    			resultadoParticipanteFinal.setMediaGeral(sumGeral/contadorGeral);
	    			resultadoSelecao.add(resultadoParticipanteFinal);
	    		}
	    		
	    		for (int i = 0;i < resultadoSelecao.size();i++) {
	    			
	    			int im = i;
	    			for (int j = i+1;j<resultadoSelecao.size();j++) {
	    				if ((float)resultadoSelecao.get(im).getMediaGeral() < (float)resultadoSelecao.get(j).getMediaGeral()) {
	    					im = j;
	    				}
	    			}
	    			ResultadoParticipanteSelecao extra = resultadoSelecao.get(i);
	    			resultadoSelecao.set(i, resultadoSelecao.get(im));
	    			resultadoSelecao.set(im,extra);
	    		}
	    		
	    		int p = 1;
	    		for (int i = 0;i<resultadoSelecao.size()-1;i++) {
	    			ResultadoParticipanteSelecao r1 = resultadoSelecao.get(i);
	    			ResultadoParticipanteSelecao r2 = resultadoSelecao.get(i+1);
	    			int k = 1;
	    			if ((float) r1.getMediaGeral() == (float) r2.getMediaGeral()) {  
	    				if (r1.getEtapas().size() > 1) {
			    			for (int j = 0;j < r1.getEtapas().size();j++) {
			    				if (r1.getEtapas().get(j).isCriterioDesempate() && (float) r1.getNotasEtapas().get(j) < (float) r2.getNotasEtapas().get(j)) {
			    					resultadoSelecao.set(i, r2);
			    					resultadoSelecao.set(i+1, r1);
			    					break;
			    				}
			    				k++;
			    			}
	    				} else {
	    					if ((float)r1.getMediaGeral() < (float) r2.getMediaGeral()) {
	    						resultadoSelecao.set(i, r2);
		    					resultadoSelecao.set(i+1, r1);
	    					}
	    				}
	    			} else {
	    				if ((float)r1.getMediaGeral() < (float) r2.getMediaGeral()) {
							resultadoSelecao.set(i, r2);
	    					resultadoSelecao.set(i+1, r1);
						}
	    			}
	    			if (k == porNotas.size()) {
	    				resultadoSelecao.get(i).setColocacao(p);
	    				resultadoSelecao.get(i+1).setColocacao(p+1);
	    			} else {
	    				resultadoSelecao.get(i).setColocacao(p);
	    				resultadoSelecao.get(i+1).setColocacao(p+1);
	    			}
	    			p++;
	    		}
	    		for (int i = 0; i< resultadoSelecao.size();i++) {
	    			
	    			ResultadoParticipanteSelecao r = resultadoSelecao.get(i);
	    			if ((float)r.getMediaGeral() >= this.getUltimaEtapa().getNotaMinima()) {
	    				resultadoSelecao.get(i).setAprovado(true);
	    			} else  {
	    				resultadoSelecao.get(i).setAprovado(false);
	    			} 
	    			
	
	    			
	    			
				}
    		} else {
    			for (int i = 0;i < resultadoEtapaFinal.size();i++) {
	    			Object[] r = resultadoEtapaFinal.get(i);
	    			Participante p = (Participante) r[0];
	    			String status = (String) r[2];
	   
	    			ResultadoParticipanteSelecao resultadoParticipanteFinal = new ResultadoParticipanteSelecao();
	    			resultadoParticipanteFinal.setParticipante(p);
	    			if (status.toUpperCase().equals("APROVADO")) {
	    				resultadoParticipanteFinal.setAprovado(true);
	    			} else {
	    				resultadoParticipanteFinal.setAprovado(false);
	    			}
	    			resultadoSelecao.add(resultadoParticipanteFinal);
	    		}
    			
    		}
    		return resultadoSelecao;
    	} else {
    		throw new IllegalAccessException("A seleção ainda não terminou! Só será possível divulgar o resultado após o termino da última etapa!");
    	}
    }
    
    public String toString() {
    	return (getCodSelecao()) +"\n"+
    	(getTitulo())+"\n"+
    	(getDescricao())+"\n"+
    	(getInscricao())+"\n"+
    	(getResponsaveis());

    }
}
