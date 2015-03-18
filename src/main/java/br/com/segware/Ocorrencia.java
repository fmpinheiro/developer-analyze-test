package br.com.segware;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe que representa o arquivo CSV a ser lido
 * @author Deivide
 */
public class Ocorrencia {

    @Id
    private int id;
    private Cliente idCliente;
    private String codigoEvento;
    private String tipoEvento;
    
    @Column(name = "dtInicio", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInicio;
    
    @Column(name = "dtFim", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtFim;
    
    private Atendente codigoAtendente;

    public Ocorrencia() {

    }

    public Ocorrencia(String parts[]) {
        this.id = Integer.parseInt(parts[0]);
        Cliente c = new Cliente(parts[1], "");
        this.idCliente = c;
        this.codigoEvento = parts[2];

        this.tipoEvento = parts[3];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.dtInicio = format.parse(parts[4]);
            this.dtFim = format.parse(parts[5]);
        } catch (ParseException ex) {
            Logger.getLogger(Ocorrencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        Atendente at = new Atendente(parts[6], "");
        this.codigoAtendente = at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public String getCodigoEvento() {
        return codigoEvento;
    }

    public void setCodigoEvento(String codigoEvento) {
        this.codigoEvento = codigoEvento;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public Atendente getCodigoAtendente() {
        return codigoAtendente;
    }

    public void setCodigoAtendente(Atendente codigoAtendente) {
        this.codigoAtendente = codigoAtendente;
    }

}
