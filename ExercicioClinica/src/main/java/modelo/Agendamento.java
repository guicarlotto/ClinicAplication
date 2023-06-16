package modelo;

import java.util.Date;

public class Agendamento {
    private long idPaciente;
    private String Nome;
    private Date dataAgendamento;
    private int hora;
    private int minuto;
   
    public Agendamento() {
        
    }
        
    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
}