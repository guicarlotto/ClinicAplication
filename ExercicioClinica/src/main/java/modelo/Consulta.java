package modelo;

import java.util.Date;

public class Consulta {
    private long id;
    private long idPaciente;
    private String nomePaciente;
    private Date dataAgendamento;

    public Consulta() {
    }

    public Consulta(long id, long idPaciente, String nomePaciente, Date dataAgendamento) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.nomePaciente = nomePaciente;
        this.dataAgendamento = dataAgendamento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
}
