package dao;
import factory.ConnectionFactory;
import modelo.TelefonePacientes;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;


public class PacienteDAO {
    
    private final Connection connection;
        
    public PacienteDAO(){ 
        this.connection = new ConnectionFactory().getConnection();
    } 
    
    public List<TelefonePacientes> consultaListaPaciente() {
    List<TelefonePacientes> pacientes = new ArrayList<>();
    String sql = "SELECT * FROM paciente";

    try {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            TelefonePacientes paciente = new TelefonePacientes();
            paciente.setId(rs.getLong("id"));
            paciente.setNome(rs.getString("nome"));
            paciente.setTelefone(rs.getString("telefone"));
            pacientes.add(paciente);
        }

        rs.close();
        stmt.close();

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    return pacientes;
    }
    
    public List<TelefonePacientes> consultaTelefonePacientes(String telefone) {
    List<TelefonePacientes> telefonePacientes = new ArrayList<>();
    String sql = "SELECT * FROM paciente WHERE telefone = ?";
    
    try {
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, telefone);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            TelefonePacientes paciente = new TelefonePacientes();
            paciente.setId(rs.getLong("id"));
            paciente.setNome(rs.getString("nome"));
            paciente.setTelefone(rs.getString("telefone"));
            telefonePacientes.add(paciente);
        }
        
        rs.close();
        stmt.close();
        
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    
    return telefonePacientes;
}
    
    public String adiciona(TelefonePacientes paciente){ 
                
        String msg = "";
        
        List<TelefonePacientes> pacientes = consultaTelefonePacientes(paciente.getTelefone());

        if (!pacientes.isEmpty()) {
            msg = "\nO telefone \"" + paciente.getTelefone() + "\" já está cadastrado.";
        }

        
        if (pacientes.isEmpty()) {

            String sql = "INSERT INTO paciente(nome, telefone) VALUES(?,?)";
            try {

                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, paciente.getNome());
                stmt.setString(2, paciente.getTelefone());
                stmt.execute();
                stmt.close();
                
                msg = "\nPaciente \"" + paciente.getNome() + "\" inserido com sucesso!";
                
            } catch (SQLException u) {
                throw new RuntimeException(u);
            }
        }
        
        return msg;
        
        }
    
        public void adicionarAgendamento(long idPaciente, Date dataAgendamento) {
        String sql = "INSERT INTO agendamento (id_paciente, data_agendamento) VALUES (?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, idPaciente);
            stmt.setDate(2, new java.sql.Date(dataAgendamento.getTime()));
            stmt.executeUpdate();
            stmt.close();

            System.out.println("Agendamento adicionado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
