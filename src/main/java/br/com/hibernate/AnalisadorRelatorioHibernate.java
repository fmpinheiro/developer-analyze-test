
package br.com.hibernate;

import br.com.segware.Atendente;
import br.com.segware.Cliente;
import br.com.segware.IAnalisadorRelatorio;
import br.com.segware.Tipo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 * Classe que implementa os métodos propostos com Hibernate. O banco utilizado
 * foi o MySql com o nome de teste conforme especificado no arquivo
 * hibernate.cfg.xml . Os testes foram realizados no servidor local (localhost)
 *
 * @author Deivide
 */
public class AnalisadorRelatorioHibernate implements IAnalisadorRelatorio {

    
    
    @Override
    public Map<String, Integer> getTotalEventosCliente() {
        Map<String, Integer> resultado = new HashMap<>();
        Session session = Hibernate.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String consulta = "SELECT idCliente,COUNT( * ) as quantidade \n"
                + "FROM Ocorrencia\n"
                + "group by id_cliente";
        List<List<Object>> permission = session.createQuery(consulta)
                .setResultTransformer(Transformers.TO_LIST).list();
        for (List<Object> x : permission) {
            Cliente c1 = (Cliente) x.get(0);
            resultado.put((String) c1.getId(), ((Long) x.get(1)).intValue());
        }
        session.getTransaction().commit();
        return resultado;
    }

    @Override
    public Map<String, Long> getTempoMedioAtendimentoAtendente() {
        Map<String, Long> resultado = new HashMap<>();
        Session session = Hibernate.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String consulta = "SELECT codigoAtendente, "
                + "AVG(TIME_TO_SEC(TIMEDIFF(dtFim, dtInicio))) as media\n"
                + "FROM Ocorrencia\n"
                + "group by id_atendente";

        List<List<Object>> permission = session.createQuery(consulta)
                .setResultTransformer(Transformers.TO_LIST).list();
        for (List<Object> x : permission) {
            Atendente a1 = (Atendente) x.get(0);
            resultado.put((String) a1.getId(), ((Double) x.get(1)).longValue());
        }
        session.getTransaction().commit();
        return resultado;
    }

    @Override
    public List<Tipo> getTiposOrdenadosNumerosEventosDecrescente() {
        Session session = Hibernate.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String consulta = "SELECT tipoEvento, "
                + "COUNT( * ) AS quantidade\n"
                + "FROM Ocorrencia\n"
                + "GROUP BY tipo_evento\n"
                + "ORDER BY quantidade DESC ";

        List<List<Object>> res = session.createQuery(consulta)
                .setResultTransformer(Transformers.TO_LIST).list();
        List<Tipo> resultado = new ArrayList<>();
        for (List<Object> x : res) {
            Tipo tipo = Tipo.valueOf((String) x.get(0));
            resultado.add(tipo);
        }
        session.getTransaction().commit();
        return resultado;
    }

    @Override
    public List<Integer> getCodigoSequencialEventosDesarmeAposAlarme() {
        Session session = Hibernate.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String consulta = "SELECT distinct desarme.id\n"
                + "FROM Ocorrencia as alarme, Ocorrencia as desarme\n"
                + "WHERE alarme.tipoEvento='ALARME' and desarme.tipoEvento='DESARME' and \n"
                + "TIMESTAMPDIFF(SECOND, alarme.dtInicio, desarme.dtInicio) between 0 and 300 "
                + "and alarme.idCliente=desarme.idCliente";

        List<Integer> res = session.createQuery(consulta).list();
        session.getTransaction().commit();
        return res;
    }

    
}
