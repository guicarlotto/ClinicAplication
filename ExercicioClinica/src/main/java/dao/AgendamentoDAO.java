package dao;

import factory.ConnectionFactory;
import modelo.Agendamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    private final Connection connection;

    public AgendamentoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adicionarAgendamento(Agendamento agendamento) {
    String sql = "SELECT COUNT(*) FROM agendamentos WHERE data_agendamento = ?";

    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setTimestamp(1, new Timestamp(agendamento.getDataAgendamento().getTime()));
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            if (count > 0) {
                System.out.println("Já existe um agendamento na mesma data e hora. Não é possível agendar a consulta.");
                return;
            }
        }

        rs.close();
        stmt.close();

        sql = "INSERT INTO agendamentos (id_paciente, nome_paciente, data_agendamento) VALUES (?, ?, ?)";
        stmt = connection.prepareStatement(sql);
        stmt.setLong(1, agendamento.getIdPaciente());
        stmt.setString(2, agendamento.getNome());
        stmt.setTimestamp(3, new Timestamp(agendamento.getDataAgendamento().getTime()));
        stmt.executeUpdate();
        stmt.close();

        System.out.println("Agendamento adicionado com sucesso!");

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}


    public List<Agendamento> listarConsultasAgendadas() {
        List<Agendamento> consultasAgendadas = new ArrayList<>();
        String sql = "SELECT * FROM agendamentos";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                long idPaciente = rs.getLong("id_agendamento");
                String nomePaciente = rs.getString("nome_paciente");
                Timestamp timestamp = rs.getTimestamp("data_agendamento");
                Date dataAgendamento = new Date(timestamp.getTime());

                Agendamento consulta = new Agendamento();
                consulta.setIdPaciente(idPaciente);
                consulta.setNome(nomePaciente);
                consulta.setDataAgendamento(dataAgendamento);
                consultasAgendadas.add(consulta);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return consultasAgendadas;
    }

    public boolean removerConsulta(long idConsulta) {
        String sql = "DELETE FROM agendamentos WHERE id_agendamento = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, idConsulta);
            int rowsAffected = stmt.executeUpdate();
            stmt.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}