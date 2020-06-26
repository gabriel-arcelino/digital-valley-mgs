package br.ufc.russas.n2s.darwin.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Wallison Carlos, Gilberto Lima
 * @param <T>
 */
@Repository("daoImpl")
public class DAOImpl<T> implements DAOIfc<T> {

    private SessionFactory sessionFactory;

    /**
     * Método Construtor padrão da classe DAOImpl.
     */
    public DAOImpl() {

    }

    @Override
    public SessionFactory getSessionFactory()  {
        return sessionFactory;
    }

    /**
     *
     * @param sf
     */
    @Autowired
    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    public T adiciona(T object) {
        Session session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            if (object != null) {
            	session.persist(object);
                t.commit();
                return object;
            } else {
                throw new NullPointerException("Objeto não pode ser nulo!");
            }
        } catch (RuntimeException e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public T atualiza(T object) {
        Session session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            if (object != null) {
                session.merge(object);
                t.commit();
                return object;
            } else {
                throw new NullPointerException("Objeto não pode ser nulo!");
            }
        } catch (RuntimeException e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(T object) {
        Session session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            if (object != null) {
                session.delete(object);
                t.commit();
            } else {
                throw new NullPointerException("Objeto não pode ser nulo!");
            }
        } catch (RuntimeException e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> lista(T object) {
        Session session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
            Example example = Example.create(object).excludeZeroes().ignoreCase();
            List<T> objetos = session.createCriteria(object.getClass()).add(example).list();
            t.commit();
            return objetos;
        } catch (RuntimeException e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject(T object, long codObject) {
        Session session = getSessionFactory().openSession();
        Transaction t = session.beginTransaction();
        try {
           T o = (T) session.get(object.getClass(), codObject);
           t.commit();
           return o;
        } catch (RuntimeException e) {
            t.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
