package br.ufc.russas.n2s.darwin.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufc.russas.n2s.darwin.model.EnumEstadoSelecao;
import br.ufc.russas.n2s.darwin.model.Selecao;

/**
 *
 * @author N2S-PC03
 */
@Repository("selecaoDAOIfc")
@Transactional
public class SelecaoDAOImpl implements SelecaoDAOIfc {

    private DAOIfc<Selecao> daoImpl;

    @Autowired
    public void setDAOIfc(@Qualifier("daoImpl")DAOIfc<Selecao> dao){
        this.daoImpl = dao;
    }

    @Override
    public Selecao adicionaSelecao(Selecao selecao) {
        Selecao s = this.daoImpl.adiciona(selecao);
        return s;
    }

    @Override
    public Selecao atualizaSelecao(Selecao selecao) {
        return this.daoImpl.atualiza(selecao);
    }

    @Override
    public void removeSelecao(Selecao selecao) {
        this.daoImpl.remove(selecao);
    }

    @Override
    public List<Selecao> listaSelecoes(Selecao selecao) {
        return this.daoImpl.lista(selecao);
    }

    @Override
    public Selecao getSelecao(Selecao selecao) {
        return this.daoImpl.getObject(selecao, selecao.getCodSelecao());
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Selecao> getSelecoesDivulgadas(){
	    Session session;
	    session = this.daoImpl.getSessionFactory().openSession();
	    Transaction t = session.beginTransaction();
	    List<Selecao> selecoes = new ArrayList<>();
	    
	    try{
	    	Criteria cr = session.createCriteria(Selecao.class);
		    cr.add(Restrictions.eq("divulgada", true)).add(Restrictions.eq("deletada",false));
		    selecoes = cr.list();
		    t.commit();
	    } catch (RuntimeException e) {
            t.rollback();
            throw e;
        } finally{
        	session.close();
        }
	    return selecoes;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Selecao> listaSelecoesIgnorandoNotas(Selecao selecao) {
	    Session session;
	    session = this.daoImpl.getSessionFactory().openSession();
	    Transaction t = session.beginTransaction();
	    
	    try {
		    Example example = Example.create(selecao).excludeZeroes().ignoreCase();
		    example.excludeProperty("exibirNotas");
		    		example.excludeProperty("divulgadoResultado");
	        List<Selecao> selecoes = (List<Selecao> ) session.createCriteria(selecao.getClass()).add(example).list();
	        t.commit();
	        
		    return selecoes;
        } catch (RuntimeException e) {
            t.rollback();
            throw e;
        } finally{
        	session.close();
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Selecao> listaSelecoesIgnorandoBooleanos() {
	    Session session;
	    session = this.daoImpl.getSessionFactory().openSession();
	    Transaction t = session.beginTransaction();
	    Selecao selecao = new Selecao();
	    try {
		    Example example = Example.create(selecao).excludeZeroes().ignoreCase().excludeProperty("exibirnotas")
		    		.excludeProperty("divulgadoresultado");
		    example.excludeProperty("divulgada");
	        List<Selecao> selecoes = (List<Selecao> ) session.createCriteria(selecao.getClass()).add(example).list();
	        t.commit();
		    
		    
		    for (Selecao s: selecoes) {
		    	System.out.println(s.getCodSelecao());
		    }
		    return selecoes;
           
        } catch (RuntimeException e) {
            t.rollback();
            throw e;
        } finally{
        	session.close();
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Selecao> buscaTodasPorCriteria(boolean divulgada) {
    	Session session = this.daoImpl.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List<Selecao> selecoes;
            Criteria c = session.createCriteria(Selecao.class);
        	c.add(Restrictions.eq("deletada", false));
        	c.add(Restrictions.eq("divulgada", divulgada));
        	c.addOrder(Order.asc("titulo"));
        	selecoes = (List<Selecao>) c.list();
            return selecoes;
        } catch(RuntimeException e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Selecao> buscaTodasPorCriteria() {
    	Session session = this.daoImpl.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List<Selecao> selecoes;
            Criteria c = session.createCriteria(Selecao.class);
        	c.add(Restrictions.eq("deletada", false));
        	c.addOrder(Order.asc("titulo"));
        	selecoes = (List<Selecao>) c.list();
            return selecoes;
        } catch(RuntimeException e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    @Override
    public void divulgaResutadoSelecao(Selecao selecao) {
    	Session session;
    	session = this.daoImpl.getSessionFactory().openSession();
	    Transaction t = session.beginTransaction();    
	    
		try {
			 
			String hql = "UPDATE darwin.selecao SET divulgadoresultado=:verdade WHERE codselecao=:codS";
		  
			SQLQuery query = session.createSQLQuery(hql);
			query.setParameter("verdade", true);
			query.setParameter("codS", selecao.getCodSelecao());
		  
		
			query.executeUpdate();
			t.commit();
		} catch (Throwable tb) {
			t.rollback();
			throw tb;
		} finally {
			session.close();
		}
    }
    
    @Override
    public void atualizaExibirNotas(Selecao selecao) {
    	Session session;
    	session = this.daoImpl.getSessionFactory().openSession();
	    Transaction t = session.beginTransaction();    
	    
	    try {
			String hql = "UPDATE darwin.selecao SET exibirnotas=:exibe WHERE codselecao=:codS";
			  
			SQLQuery query = session.createSQLQuery(hql);
			query.setParameter("exibe", !selecao.isExibirNotas());
			query.setParameter("codS", selecao.getCodSelecao());
			
			query.executeUpdate();
	    	t.commit();
    	} catch (Throwable tb) {
    	  t.rollback();
    	  throw tb;
    	} finally {
    		session.close();
    	}
    }
    
	@Override
	@SuppressWarnings("unchecked")
	public List<Selecao> listaSelecoes(boolean isAdm, String categoria, EnumEstadoSelecao estado, int inicio, int qtd) {
		Session session =  this.daoImpl.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<Selecao> lis = new ArrayList<>();

        try {
        	
        	Criteria cb = session.createCriteria(Selecao.class);
        	if(!isAdm) {
        		cb.add(Restrictions.eq("divulgada", true));	
        	}
    		cb.add(Restrictions.eq("deletada",false));
            cb.addOrder(Order.desc("codSelecao"));
            
            if(estado != null){
            	cb.add(Restrictions.eq("estado", estado));
            }
            if(categoria != null){
            	cb.add(Restrictions.eq("categoria", categoria));
            }
            
            cb.setFirstResult(inicio);
            cb.setMaxResults(qtd);
                        
            lis = cb.list();
            
            t.commit();
            
        } catch (RuntimeException ex) {
            t.rollback();
            throw ex;
        } finally {
            session.close();
        }
        
        return lis;
	}

	@Override
	public Long getQuantidade(boolean isAdm, String categoria, EnumEstadoSelecao estado) {
		Session session =  this.daoImpl.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        long qtd = 0;

        try {
        	Query qry;
        	if(estado != null){
        		String hql = "select count(s) from Selecao s where s.estado = :estado and "; 
        		if(!isAdm) {
        			hql += " s.divulgada = 'true' and ";
        		}
        		hql += " s.deletada = 'false'";
        		
        		if(categoria != null){
        			hql += " and categoria = " + categoria;
                }
        		
        		qry = session.createQuery(hql);
        		qry.setParameter("estado", estado.getEstado());
        		
        	} else {
        		String hql = "select count(s) from Selecao s where ";
        		if(!isAdm) {
        			hql += " s.divulgada = 'true' and ";
        		}
        		hql += " s.deletada = 'false' ";
        		
        		if(categoria != null){
                	hql +=  " and categoria = '" + categoria +"'";
                }
        		qry = session.createQuery(hql);
        	}
        	            
        	try{
        		qtd = (Long) qry.uniqueResult();
        	} catch(Exception e){
        		System.err.println("Sem resultados");
        	}
        	
            t.commit();
            
        } catch (RuntimeException ex) {
            t.rollback();
            throw ex;
        } finally {
            session.close();
        }
        
        return qtd;
	}
    
	@Override
	@SuppressWarnings("unchecked")
    public List<Selecao> buscarSelecoesPorNome(boolean isAdm, String titulo, int inicio, int qtd) {
    	Session session = this.daoImpl.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<Selecao> selecoes = new ArrayList<>();
        try {
            
            Criteria c = session.createCriteria(Selecao.class);
        	c.add(Restrictions.ilike("titulo", "%"+titulo+"%"));
        	if(!isAdm) {
        		c.add(Restrictions.eq("divulgada", true));	
        	}
        	c.add(Restrictions.eq("deletada", false));
        	c.addOrder(Order.desc("codSelecao"));
        	
        	c.setFirstResult(inicio);
            c.setMaxResults(qtd);
            
        	selecoes = c.list();
        	t.commit();
            
        } catch(RuntimeException e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
        return selecoes;
    }
	
	@Override
	public Long getQuantidadePorNome(boolean isAdm, String titulo){
		Session session =  this.daoImpl.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        long qtd = 0;

        try {
        	String sql = "select count(s) from Selecao s where s.titulo ilike '%" + titulo +"%' and ";
        	if(!isAdm) {
        		sql += " s.divulgada = 'true' and ";
        	}
        	sql += " s.deletada = 'false' ";
        	Query qry = session.createQuery(sql);
        	            
        	try{
        		qtd = (Long) qry.uniqueResult();
        	} catch(Exception e){
        		System.err.println("Sem resultados");
        	}
        	
            t.commit();
            
        } catch (RuntimeException ex) {
            t.rollback();
            throw ex;
        } finally {
            session.close();
        }
        
        return qtd;
	}

	@Override
	public List<Selecao> buscarSelecoesAssociada(Long idUsuario, int inicio, int qtd) {
        List<Selecao> selecoes = new ArrayList<>();
        try {
            List<Long> ids = getListaSelecoesAssociada(idUsuario);
            int qtdPorPagina = 0;
            for(int i = inicio; qtdPorPagina < qtd && i < ids.size(); i++){
            	Selecao s = new Selecao();
            	s.setCodSelecao(ids.get(i));
            	selecoes.add(getSelecao(s));
            	qtdPorPagina++;
            }
            
        } catch(RuntimeException e) {
        	throw e;
        }
        return selecoes;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Long> getListaSelecoesAssociada(Long idUsuario) {
		Session session =  this.daoImpl.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        List<Long> selecoesTotal = new ArrayList<>();
        
        try {
        	Query qry;
        	
        	List<Long> selecoesResponsavel = new ArrayList<>();
        	String sqlResponsavelSelecao = 	"select distinct s.codselecao " +
											"from darwin.selecao as s " +
											"inner join darwin.responsaveis_selecao as rs on (s.codselecao = rs.selecao) " +
											"inner join darwin.usuario as u on (u.codusuario = rs.usuario) " +
											"where u.codusuario = ? and s.deletada = 'false';";
        	
        	qry = session.createSQLQuery(sqlResponsavelSelecao).setLong(0, idUsuario);
        													
        	
        	try{
        		List<Object> temp = qry.list();
        		for(Object o : temp){
        			selecoesResponsavel.add(new BigInteger(o.toString()).longValue()); 
        		}
        	} catch(Exception e){
        		System.err.println("Sem resultados");
        	}
        	
        	List<Long> selecoesParticipando = new ArrayList<>();
        	String sqlParticipandoSelecao = "select distinct s.codselecao " +
											"from darwin.selecao as s " +
											"inner join darwin.etapa as e on (s.etapa_inscricao = e.codetapa) " + 
											"inner join darwin.participantes_etapa as pe on (e.codetapa = pe.etapa) " + 
											"inner join darwin.participante as p on (p.codparticipante = pe.participante) " +
											"where p.candidato = ? and s.divulgada = 'true' and s.deletada = 'false';";
        	
        	
        	qry = session.createSQLQuery(sqlParticipandoSelecao).setLong(0, idUsuario);
        	
        	try{
        		List<Object> temp = qry.list();
        		for(Object o : temp){
        			selecoesParticipando.add(new BigInteger(o.toString()).longValue()); 
        		}
        	} catch(Exception e){
        		System.err.println("Sem resultados");
        	}
        	
        	List<Long> selecoesAvaliadores = new ArrayList<>();
        	String sqlAvaliadorSelecaoEtapas = 	"select distinct s.codselecao " + 
												"from darwin.selecao as s " +
												"inner join darwin.etapa as et on (s.etapa_inscricao = et.codetapa) " +
												"left join darwin.etapas_selecao as es on (es.selecao = s.codselecao) " +
												"inner join darwin.avaliadores as ava on (et.codetapa = ava.etapa or es.etapa = ava.etapa) " +
												"where ava.avaliador = ? and s.divulgada = 'true' and s.deletada = 'false';";
        	
        	qry = session.createSQLQuery(sqlAvaliadorSelecaoEtapas).setLong(0, idUsuario);
        	
        	try{
        		List<Object> temp = qry.list();
        		for(Object o : temp){
        			selecoesAvaliadores.add(new BigInteger(o.toString()).longValue()); 
        		}
        	} catch(Exception e){
        		System.err.println("Sem resultados");
        	}
        	
        	for(int i = 0; i < selecoesAvaliadores.size(); i++){
        		if(!selecoesTotal.contains(selecoesAvaliadores.get(i))){
        			selecoesTotal.add(selecoesAvaliadores.get(i));
        		}
        	}
        	for(int i = 0; i < selecoesParticipando.size(); i++){
        		if(!selecoesTotal.contains(selecoesParticipando.get(i))){
        			selecoesTotal.add(selecoesParticipando.get(i));
        		}
        	}
        	for(int i = 0; i < selecoesResponsavel.size(); i++){
        		if(!selecoesTotal.contains(selecoesResponsavel.get(i))){
        			selecoesTotal.add(new BigDecimal(selecoesResponsavel.get(i)).longValue());
        		}
        	}
        	
        	Collections.sort(selecoesTotal, Collections.reverseOrder());
        	
            t.commit();
            
        } catch (RuntimeException ex) {
            t.rollback();
            throw ex;
        } finally {
            session.close();
        }
        
        return selecoesTotal;
	}

	@Override
	public Selecao getSelecao(Long codEtapa) {
		Session session =  this.daoImpl.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        Selecao selecao = null;
        try {
        	String sql=	"select distinct s.codselecao " +
    					"from darwin.selecao as s " +
    					"inner join darwin.etapa as et on (s.etapa_inscricao = et.codetapa) " +
    					"left join darwin.etapas_selecao as es on (s.codselecao = es.selecao) " +
    					"where et.codetapa = ? or es.etapa = ?;";
        	
        	Query qry = session.createSQLQuery(sql).setLong(0, codEtapa)
        											.setLong(1, codEtapa);
        	
        	try{
        		Object resultado = qry.uniqueResult();
        		Long codSelecao = new BigInteger(resultado.toString()).longValue();
        		
        		Selecao temp = new Selecao();
        		temp.setCodSelecao(codSelecao);
        		
        		selecao = getSelecao(temp);
        	} catch(Exception e){
        		System.err.println("Sem resultados");
        	}
        	
            t.commit();
            
        } catch (RuntimeException ex) {
            t.rollback();
            throw ex;
        } finally {
            session.close();
        }
        
        return selecao;
	}
}
