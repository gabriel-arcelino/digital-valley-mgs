package br.ufc.russas.n2s.darwin.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;

/**
 *
 * @author Wallison Carlos
 */
public class UsuarioDAOImpl implements UsuarioDAOIfc{

    private DAOIfc<UsuarioDarwin> daoImpl;

    @Autowired
    public void setDAOIfc(@Qualifier("daoImpl")DAOIfc<UsuarioDarwin> dao){
        this.daoImpl = dao;
    }

    @Override
    public UsuarioDarwin adicionaUsuario(UsuarioDarwin usuario) {
        return this.daoImpl.adiciona(usuario);
    }

    @Override
    public UsuarioDarwin atualizaUsuario(UsuarioDarwin usuario) {
        return this.daoImpl.atualiza(usuario);
    }

    @Override
    public void removeUsuario(UsuarioDarwin usuario) {
        this.daoImpl.remove(usuario);
    }

    @Override
    public List<UsuarioDarwin> listaUsuarios(UsuarioDarwin usuario) {
        return this.daoImpl.lista(usuario);
    }

    @Override
    public UsuarioDarwin getUsuario(UsuarioDarwin usuario) {
        return this.daoImpl.getObject(usuario, usuario.getCodUsuario());
    }

    @Override
    public UsuarioDarwin getUsuarioControleDeAcesso(UsuarioDarwin usuario) {
        Session session = this.daoImpl.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            Example example = Example.create(usuario).excludeProperty("recebeEmail").excludeProperty("CPF");
            usuario = (UsuarioDarwin) session.createCriteria(UsuarioDarwin.class).add(example).uniqueResult();
            t.commit();
            return usuario;
        } catch(RuntimeException e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    @Override
    @SuppressWarnings("unchecked")
    public List<UsuarioDarwin> BuscaUsuariosPorNome(String nome) {
    	Session session = this.daoImpl.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List<UsuarioDarwin> usuarios;
            Criteria c = session.createCriteria(UsuarioDarwin.class);
        	c.add(Restrictions.ilike("nome", "%"+nome+"%"));
        	c.addOrder(Order.asc("nome"));
        	usuarios = (List<UsuarioDarwin>) c.list();
            return usuarios;
        } catch(RuntimeException e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<UsuarioDarwin> ListaEmOdermAlfabetica() {
    	Session session = this.daoImpl.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            List<UsuarioDarwin> usuarios = (List<UsuarioDarwin>) session.createCriteria(UsuarioDarwin.class).addOrder(Order.asc("nome")).list();
            t.commit();
            return usuarios;
        } catch(RuntimeException e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
}
