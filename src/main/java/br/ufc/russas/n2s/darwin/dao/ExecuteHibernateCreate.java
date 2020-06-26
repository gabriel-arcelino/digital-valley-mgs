package br.ufc.russas.n2s.darwin.dao;

import br.ufc.russas.n2s.darwin.model.Arquivo;
import br.ufc.russas.n2s.darwin.model.Documentacao;
import br.ufc.russas.n2s.darwin.model.Etapa;
import br.ufc.russas.n2s.darwin.model.Periodo;
import br.ufc.russas.n2s.darwin.model.Selecao;
import br.ufc.russas.n2s.darwin.model.UsuarioDarwin;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 *
 * @author Wallison Carlos
 */
public class ExecuteHibernateCreate {
    public static void main(int argc, String[] args){
        Configuration conf = new Configuration();
    	conf.configure("/WEB-INF/hibernate.cfg.xml");
    	conf.addAnnotatedClass(Arquivo.class);
        conf.addAnnotatedClass(Documentacao.class);
        conf.addAnnotatedClass(Periodo.class);
        conf.addAnnotatedClass(UsuarioDarwin.class);
        conf.addAnnotatedClass(Etapa.class);
        conf.addAnnotatedClass(Selecao.class);
        SchemaExport se = new SchemaExport(conf);
        se.create(true, true);
    }
}
