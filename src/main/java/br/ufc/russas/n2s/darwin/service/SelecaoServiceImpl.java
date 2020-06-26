package br.ufc.russas.n2s.darwin.service;

import br.ufc.russas.n2s.darwin.beans.EtapaBeans;
import br.ufc.russas.n2s.darwin.beans.ResultadoParticipanteSelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.beans.UsuarioBeans;
import br.ufc.russas.n2s.darwin.dao.EtapaDAOIfc;
import br.ufc.russas.n2s.darwin.dao.SelecaoDAOIfc;
import br.ufc.russas.n2s.darwin.model.EnumEstadoEtapa;
import br.ufc.russas.n2s.darwin.model.EnumEstadoSelecao;
import br.ufc.russas.n2s.darwin.model.Etapa;
import br.ufc.russas.n2s.darwin.model.EtapaPredicates;
import br.ufc.russas.n2s.darwin.model.Participante;
import br.ufc.russas.n2s.darwin.model.ResultadoParticipanteSelecao;
import br.ufc.russas.n2s.darwin.model.Selecao;
import br.ufc.russas.n2s.darwin.model.SelecaoProxy;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wallison Carlos
 */
@Service("selecaoServiceIfc")
@Transactional
public class SelecaoServiceImpl implements SelecaoServiceIfc {

    private SelecaoDAOIfc selecaoDAOIfc;
    private EtapaDAOIfc etapaDAOIfc;
    private UsuarioBeans usuario;
    
    public SelecaoServiceImpl(){
    }

    public SelecaoDAOIfc getSelecaoDAOIfc() {
        return selecaoDAOIfc;
    }

    @Autowired(required = true)
    public void setSelecaoDAOIfc(@Qualifier("selecaoDAOIfc")SelecaoDAOIfc selecaoDAOIfc) {
        this.selecaoDAOIfc = selecaoDAOIfc;
    }
    
    @Autowired(required = true)
    public void setEtapaDAOIfc(@Qualifier("etapaDAOIfc")EtapaDAOIfc etapaDAOIfc) {
        this.etapaDAOIfc = etapaDAOIfc;
    }

    @Override
    public SelecaoBeans adicionaSelecao(SelecaoBeans selecao) throws IllegalAccessException {
        UsuarioDarwin usuario = (UsuarioDarwin) this.usuario.toBusiness();
        SelecaoProxy sp = new SelecaoProxy(usuario);
        Selecao s = getSelecaoDAOIfc().adicionaSelecao(sp.adicionaSelecao((Selecao) selecao.toBusiness()));
        return (SelecaoBeans) selecao.toBeans(s);
    }

    @Override
    public SelecaoBeans atualizaSelecao(SelecaoBeans selecao) throws IllegalAccessException{
        UsuarioDarwin usuarioD = (UsuarioDarwin) this.usuario.toBusiness();
        SelecaoProxy sp = new SelecaoProxy(usuarioD);
        Selecao s = (Selecao) selecao.toBusiness();       
        s = getSelecaoDAOIfc().atualizaSelecao(sp.atualizaSelecao(s));
        return (SelecaoBeans) new SelecaoBeans().toBeans(s);
    }

    @Override
    @Transactional
    public void removeSelecao(SelecaoBeans selecao) {
        this.getSelecaoDAOIfc().removeSelecao((Selecao) selecao.toBusiness());
    }
    
    @Override
    public void divulgaResultadoSelecao(SelecaoBeans selecao) {
    	this.getSelecaoDAOIfc().divulgaResutadoSelecao((Selecao) selecao.toBusiness());
    }
    
    @Override
    public void atualizaExibirNotas(SelecaoBeans selecao) {
    	this.getSelecaoDAOIfc().atualizaExibirNotas((Selecao) selecao.toBusiness());
    }

    @Override
    @Transactional
    public List<SelecaoBeans> listaTodasSelecoes() {
        List<SelecaoBeans> selecoes = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
        List<Selecao> resultado = this.getSelecaoDAOIfc().getSelecoesDivulgadas();
        for (Selecao s : resultado) {
            if (s.getInscricao() != null) {
                s.getInscricao().setEstado(s.getInscricao().getEstado().execute(s.getInscricao()));
                etapaDAOIfc.atualizaEtapa(s.getInscricao());
                if (s.getEstado().execute(s).compareTo(s.getEstado()) != 0) {
                    this.atualizaEstado(s, s.getEstado().execute(s));
                }
                for (Etapa etapa : s.getEtapas()) {
                    etapa.setEstado(etapa.getEstado().execute(etapa));
                    etapaDAOIfc.atualizaEtapa(etapa);
                }
            }
            selecoes.add((SelecaoBeans) new SelecaoBeans().toBeans(s));
        }
        return this.ordenaSelecoesPorData(selecoes);
    }
    
    
    
    @Override
    @Transactional
    public List<SelecaoBeans> listaSelecoes(Selecao selecao) {
        selecao.setDivulgada(true);
        selecao.setDivulgadoResultado(false);
        selecao.setDeletada(false);
        List<SelecaoBeans> selecoes = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
        List<Selecao> resultado = this.getSelecaoDAOIfc().listaSelecoes(selecao);
        
        selecao.setDivulgadoResultado(true);
        resultado.addAll(this.getSelecaoDAOIfc().listaSelecoes(selecao));
        for (Selecao s : resultado) {
            selecoes.add((SelecaoBeans) new SelecaoBeans().toBeans(s));
        }
        return selecoes;
    }
    
    @Override
    @Transactional
    public List<SelecaoBeans> listaSelecoesIgnorandoNotas(Selecao selecao) {
        selecao.setDivulgada(true);
        selecao.setDeletada(false);
        List<SelecaoBeans> selecoes = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
        List<Selecao> resultado = this.getSelecaoDAOIfc().listaSelecoesIgnorandoNotas(selecao);
        System.out.println(resultado.size());
        for (Selecao s : resultado) {
            selecoes.add((SelecaoBeans) new SelecaoBeans().toBeans(s));
        }
        return selecoes;
    }

    @Override
    @Transactional(readOnly = true)
    public SelecaoBeans getSelecao(long codSelecao) {
    	Selecao selecao = new Selecao();
    	selecao.setCodSelecao(codSelecao);
    	
    	selecao = this.getSelecaoDAOIfc().getSelecao(selecao);
   
       	EnumEstadoSelecao novoEstado = selecao.getEstado().execute(selecao);
		if(novoEstado != selecao.getEstado()){
			this.atualizaEstado(selecao, novoEstado);
		}
		
		if(selecao.getInscricao() != null){
			EnumEstadoEtapa estadoEtapaIns = selecao.getInscricao().getEstado().execute(selecao.getInscricao());
			if(estadoEtapaIns != selecao.getInscricao().getEstado()){
				selecao.getInscricao().setEstado(estadoEtapaIns);
				etapaDAOIfc.atualizaEtapa(selecao.getInscricao());
			}
		}
		
		List<Etapa> etapas = selecao.getEtapas();
		for(Etapa e : etapas){
			EnumEstadoEtapa estadoEtapa = e.getEstado().execute(e);
			if(e.getEstado() != estadoEtapa){
				e.setEstado(estadoEtapa);
				etapaDAOIfc.atualizaEtapa(e);
			}
		}
		
		return (SelecaoBeans) new SelecaoBeans().toBeans(selecao);
    }

    @Override
    public void setUsuario(UsuarioBeans usuario) {
        this.usuario = usuario;
    }

    @Override
    public List<SelecaoBeans> listaSelecoesAssociada(UsuarioBeans usuario) {
        Selecao selecao = new Selecao();
        selecao.setDeletada(false);
        selecao.setExibirNotas(true);
        UsuarioDarwin user = (UsuarioDarwin) usuario.toBusiness();
        List<SelecaoBeans> selecoes = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
        List<Selecao> resultadoNaoDivulgadas = this.getSelecaoDAOIfc().buscaTodasPorCriteria(false);
        List<Selecao> resultadoDivulgadas = this.getSelecaoDAOIfc().buscaTodasPorCriteria(true);

        for (Selecao s : resultadoNaoDivulgadas) {
            if (s.getResponsaveis().contains(user)) {
                selecoes.add((SelecaoBeans) new SelecaoBeans().toBeans(s));
            } 
        }
        
        UsuarioDarwin u = (UsuarioDarwin) usuario.toBusiness();
        for (Selecao s : resultadoDivulgadas) {
        	boolean isParticipante = false;
        	for (Participante p : s.getInscricao().getParticipantes()) {
        		if (p.getCandidato().getCodUsuario() == usuario.getCodUsuario()) {
        			isParticipante = true;
        			break;
        		}
        	}
            if (s.getResponsaveis().contains(u) || isParticipante) {
            	selecoes.add((SelecaoBeans) new SelecaoBeans().toBeans(s));
            }
        }
        return this.ordenaSelecoesPorData(selecoes);
    }
    
    @Override
    public List<SelecaoBeans> listaTodasSelecoesDoBanco() {
        Selecao selecao = new Selecao();
        selecao.setDeletada(false);
        selecao.setDivulgada(false);
        List<SelecaoBeans> selecoes = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
        selecao.setDivulgada(true);
        List<Selecao> resultadoTodas =  this.getSelecaoDAOIfc().buscaTodasPorCriteria();
        
        
        for (int i=0;i<resultadoTodas.size();i++) {
            selecoes.add((SelecaoBeans) new SelecaoBeans().toBeans(resultadoTodas.get(i)));
        }
        return this.ordenaSelecoesPorData(selecoes);
    }
    
    
    

    @Override
    public EtapaBeans getEtapaAtual(SelecaoBeans selecao) {
        Selecao s = (Selecao) selecao.toBusiness();
        Etapa etapa = s.getEtapaAtual();
        if (etapa != null) {
            return (EtapaBeans) new EtapaBeans().toBeans(etapa);
        } else {
            return null;
        }
    }
    
    private Selecao atualizaEstado(Selecao selecao, EnumEstadoSelecao estado) {
        selecao.setEstado(estado);
        return this.selecaoDAOIfc.atualizaSelecao(selecao);
    }

    @Override
    public List<SelecaoBeans> ordenaSelecoesPorData(List<SelecaoBeans> selecoes) {
        List<SelecaoBeans> selecoesSemDatas = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
        List<SelecaoBeans> selecoesFinalizadas = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
        List<SelecaoBeans> selecoesEmAndamento = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
        List<SelecaoBeans> selecoesEmEspera = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
        int p=0;
        while( p<selecoes.size()) {
            if (selecoes.get(p).getInscricao() == null) {
                selecoesSemDatas.add(selecoes.get(p));
            } else if (selecoes.get(p).getEstado().compareTo(EnumEstadoSelecao.ANDAMENTO) == 0) {
                selecoesEmAndamento.add(selecoes.get(p));
            } else if (selecoes.get(p).getEstado().compareTo(EnumEstadoSelecao.ESPERA) == 0) {
                selecoesEmEspera.add(selecoes.get(p));
            } else if (selecoes.get(p).getEstado().compareTo(EnumEstadoSelecao.FINALIZADA) == 0) {
                selecoesFinalizadas.add(selecoes.get(p));
            }
            p++;
        }
        selecoes.removeAll(selecoesFinalizadas);
        selecoes.removeAll(selecoesSemDatas);
        selecoes.removeAll(selecoesEmEspera);
        selecoes.removeAll(selecoesEmAndamento);
        selecoes = this.ordena(selecoes);
        selecoes.addAll(this.ordena(selecoesEmEspera));
        selecoes.addAll(this.ordena(selecoesEmAndamento));
        selecoes.addAll(this.ordena(selecoesFinalizadas));
        selecoes.addAll(selecoesSemDatas);
        return selecoes;
    }
    
    private List<SelecaoBeans> ordena(List<SelecaoBeans> selecoes) {
        SelecaoBeans aux;
         for (int i=0;i<selecoes.size()-1;i++) {
            for (int j=i;j<selecoes.size()-1;j++) {
                if (selecoes.get(j).getInscricao().getPeriodo().getInicio().isAfter(selecoes.get(j+1).getInscricao().getPeriodo().getInicio())) {
                    aux = selecoes.get(j);
                    selecoes.set(j, selecoes.get(j+1));
                    selecoes.set(j+1, aux);
                }
            }
        }
        return selecoes;
    }
    
    @Override
    public EtapaBeans getUltimaEtapa(SelecaoBeans selecao) {
        Selecao s = (Selecao) selecao.toBusiness();
        Etapa etapa = s.getUltimaEtapa();
        if (etapa != null) {
            return (EtapaBeans) new EtapaBeans().toBeans(etapa);
        } else {
            return null;
        }
    }

	@Override
	public List<EtapaBeans> getEtapasNota(SelecaoBeans selecao) {
		Selecao s = (Selecao) selecao.toBusiness();
		List<Etapa> porNotas = s.getEtapas().stream().filter( EtapaPredicates.isNota()).collect(Collectors.<Etapa>toList());
		List<EtapaBeans> etapas = Collections.synchronizedList(new ArrayList<EtapaBeans>());
		for (int i = 0;i < porNotas.size();i++) {
			etapas.add((EtapaBeans) new EtapaBeans().toBeans(porNotas.get(i)));
		}
		return ordenaPorPrioridade(etapas);
	}

	private List<EtapaBeans> ordenaPorPrioridade(List<EtapaBeans> etapas) {
		EtapaBeans aux;
		for (int i=0;i<etapas.size()-1;i++) {
			if (etapas.get(i).getPosicaoCriterioDesempate() > etapas.get(i+1).getPosicaoCriterioDesempate()) {
				aux = etapas.get(i);
				etapas.set(i,etapas.get(i+1));
				etapas.set(i+1, aux);
			}
		}
		return etapas;
	}
	
	@Override
	public List<ResultadoParticipanteSelecaoBeans> getResultado(SelecaoBeans selecao) throws IllegalAccessException {
		Selecao s = (Selecao) selecao.toBusiness();
		List<ResultadoParticipanteSelecao> resultado = s.resultado();
		List<ResultadoParticipanteSelecaoBeans> rb = Collections.synchronizedList(new ArrayList<ResultadoParticipanteSelecaoBeans>());
		for (ResultadoParticipanteSelecao r : resultado) {
			rb.add((ResultadoParticipanteSelecaoBeans) new ResultadoParticipanteSelecaoBeans().toBeans(r));
		}
		return rb;
	}
	
	@Override
	public List<SelecaoBeans> listaSelecoes(boolean isAdm, String categoria, EnumEstadoSelecao estado, int inicio, int qtd){
		List<Selecao> result = this.getSelecaoDAOIfc().listaSelecoes(isAdm, categoria, estado, inicio, qtd);
	    List<SelecaoBeans> selecoes = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
	    for (Selecao s : result) {
	    	
	    	EnumEstadoSelecao novoEstado = s.getEstado().execute(s);
	    	if(novoEstado != s.getEstado()){
	    		this.atualizaEstado(s, novoEstado);
	    	}
	    	Etapa etapa = s.getInscricao();
	    	if(etapa != null) {
	    		EnumEstadoEtapa estadoEtapaIns = s.getInscricao().getEstado().execute(s.getInscricao());	
		    	if(estadoEtapaIns != s.getInscricao().getEstado()){
		    		s.getInscricao().setEstado(estadoEtapaIns);
		    		etapaDAOIfc.atualizaEtapa(s.getInscricao());
		    	}
	    	}
	    	List<Etapa> etapas = s.getEtapas();
	    	for(Etapa e : etapas){
	    		EnumEstadoEtapa estadoEtapa = e.getEstado().execute(e);
	    		if(e.getEstado() != estadoEtapa){
	    			e.setEstado(estadoEtapa);
	    			etapaDAOIfc.atualizaEtapa(e);
	    		}
	    	}

	    	selecoes.add((SelecaoBeans) new SelecaoBeans().toBeans(s));
	    }
	    return selecoes;
	}
	
	@Override
    public Long getQuantidade(boolean isAdm, String categoria, EnumEstadoSelecao estado){
		return this.getSelecaoDAOIfc().getQuantidade(isAdm, categoria, estado);
	}
	
	@Override
    public List<SelecaoBeans> buscarSelecoesPorNome(boolean isAdm, String titulo, int inicio, int qtd){
    	List<Selecao> result = this.getSelecaoDAOIfc().buscarSelecoesPorNome(isAdm, titulo, inicio, qtd);
	    List<SelecaoBeans> selecoes = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
	    for (Selecao s : result) {
	    	
	    	EnumEstadoSelecao novoEstado = s.getEstado().execute(s);
	    	if(novoEstado != s.getEstado()){
	    		this.atualizaEstado(s, novoEstado);
	    	}
	    	
	    	Etapa etapa = s.getInscricao();
	    	if(etapa != null) {
	    		EnumEstadoEtapa estadoEtapaIns = s.getInscricao().getEstado().execute(s.getInscricao());	
		    	if(estadoEtapaIns != s.getInscricao().getEstado()){
		    		s.getInscricao().setEstado(estadoEtapaIns);
		    		etapaDAOIfc.atualizaEtapa(s.getInscricao());
		    	}
	    	}
	    	List<Etapa> etapas = s.getEtapas();
	    	for(Etapa e : etapas){
	    		EnumEstadoEtapa estadoEtapa = e.getEstado().execute(e);
	    		if(e.getEstado() != estadoEtapa){
	    			e.setEstado(estadoEtapa);
	    			etapaDAOIfc.atualizaEtapa(e);
	    		}
	    	}

	    	selecoes.add((SelecaoBeans) new SelecaoBeans().toBeans(s));
	    }
	    return selecoes;
    }
	
	@Override
	public Long getQuantidadePorNome(boolean isAdm, String titulo){
		return this.getSelecaoDAOIfc().getQuantidadePorNome(isAdm, titulo);
	}

	@Override
	public List<SelecaoBeans> buscarSelecoesAssociada(UsuarioBeans usuario, int inicio, int qtd) {
		List<Selecao> result = this.getSelecaoDAOIfc().buscarSelecoesAssociada(usuario.getCodUsuario(), inicio, qtd); 
	    List<SelecaoBeans> selecoes = Collections.synchronizedList(new ArrayList<SelecaoBeans>());
	    for (Selecao s : result) {
	    	
	    	EnumEstadoSelecao novoEstado = s.getEstado().execute(s);
	    	if(novoEstado != s.getEstado()){
	    		this.atualizaEstado(s, novoEstado);
	    	}
	    	
	    	if(s.getInscricao() != null){
	    		EnumEstadoEtapa estadoEtapaIns = s.getInscricao().getEstado().execute(s.getInscricao());
		    	if(estadoEtapaIns != s.getInscricao().getEstado()){
		    		s.getInscricao().setEstado(estadoEtapaIns);
		    		etapaDAOIfc.atualizaEtapa(s.getInscricao());
		    	}
	    	}
	    	
	    	
	    	List<Etapa> etapas = s.getEtapas();
	    	for(Etapa e : etapas){
	    		EnumEstadoEtapa estadoEtapa = e.getEstado().execute(e);
	    		if(e.getEstado() != estadoEtapa){
	    			e.setEstado(estadoEtapa);
	    			etapaDAOIfc.atualizaEtapa(e);
	    		}
	    	}

	    	selecoes.add((SelecaoBeans) new SelecaoBeans().toBeans(s));
	    }
	    return selecoes;
	}

	@Override
	public Long getQuantidadeAssociada(UsuarioBeans usuario) {
		Integer qtd = this.getSelecaoDAOIfc().getListaSelecoesAssociada(usuario.getCodUsuario()).size();
		return qtd.longValue();
	}

	@Override
	public SelecaoBeans getSelecaoDaEtapa(Long codEtapa) {
		Selecao s = this.getSelecaoDAOIfc().getSelecao(codEtapa);
		return (SelecaoBeans) new SelecaoBeans().toBeans(s);
	}
}
