
package br.com.hibernate;

import br.com.segware.Ocorrencia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.hibernate.Session;

/**
 *
 * @author Deivide
 */
public class DBHelper {
   
    private BufferedReader bufReader;
    
    public DBHelper(){
        try {
            this.bufReader = new BufferedReader(new FileReader(new File(br.com.segware.AnalisadorRelatorio.FILEPATH)));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insere no banco de dados todos os Eventos do arquivo CSV (Hibernate).
     */
    public void adicionaOcorrencias() {

        try {
            String line;
            while ((line = bufReader.readLine()) != null) {
                Session session = Hibernate.getSessionFactory().getCurrentSession();
                String parts[] = line.split(",");
                session.beginTransaction();
                Ocorrencia ac = new Ocorrencia(parts);
                session.save(ac);
                session.getTransaction().commit();
            }
            

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
