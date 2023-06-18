package console;

import dao.PacienteDAO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import modelo.TelefonePacientes;
import dao.AgendamentoDAO;
import java.util.Calendar;
import modelo.Agendamento;

public class Main {

    public static void main(String[] args) {
        int opc;
        String nome, telefone;
        Scanner teclado = new Scanner(System.in);
        PacienteDAO pacienteDAO = new PacienteDAO();

        do {

            System.out.println("---------- Menu -----------------");
            System.out.println("\n1. Cadastrar Paciente");
            System.out.println("2. Agendar Consulta");
            System.out.println("3. Cancelar Consulta");
            System.out.println("\n0. Sair");

            opc = teclado.nextInt();
            teclado.nextLine();

            switch (opc) {

                case 1 -> {

                    System.out.println("\nPreencha os dados abaixo:");
                    System.out.println("\nNome:");
                    nome = teclado.nextLine();
                    System.out.print("\nTelefone: ");
                    telefone = teclado.nextLine();

                    TelefonePacientes novoPaciente = new TelefonePacientes(nome, telefone);
                    String mensagem = pacienteDAO.adiciona(novoPaciente);
                    System.out.println(mensagem);
                }

                case 2 -> {

                    List<TelefonePacientes> pacientes = pacienteDAO.consultaListaPaciente();

                    System.out.println("\nLista de Pacientes:");
                    for (TelefonePacientes paciente : pacientes) {
                        System.out.println("\nID: " + paciente.getId() + " | Nome: " + paciente.getNome() + " | Telefone: " + paciente.getTelefone());
                    }

                    System.out.print("\nSelecione o ID do paciente para agendar a consulta: ");
                    long idPacienteSelecionado = teclado.nextLong();
                    teclado.nextLine(); // Limpa o buffer de entrada

                    TelefonePacientes pacienteSelecionado = null;
                    for (TelefonePacientes paciente : pacientes) {
                        if (paciente.getId() == idPacienteSelecionado) {
                            pacienteSelecionado = paciente;
                        }
                    }
                    if (pacienteSelecionado != null) {
                        System.out.print("\nDigite a data da consulta (formato dd/mm/aaaa): ");
                        String dataConsulta = teclado.nextLine();
                        System.out.print("\nDigite a hora da consulta (formato hh:mm): ");
                        String horaConsulta = teclado.nextLine();
                        System.out.print("\nDigite o nome do paciente: ");
                        String nomeConsulta = teclado.nextLine();
                        System.out.print("\nDigite a especialidade: ");
                        String especialidadeConsulta = teclado.nextLine();

                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        Date dataAgendamento;
                        int hora, minuto;

                        try {
                            String dataAgendamentoStr = dataConsulta + " " + horaConsulta;
                            dataAgendamento = sdf.parse(dataAgendamentoStr);

                            Calendar dataHoraAtual = Calendar.getInstance();
                            Calendar dataHoraAgendamento = Calendar.getInstance();
                            dataHoraAgendamento.setTime(dataAgendamento);

                            hora = dataHoraAgendamento.get(Calendar.HOUR_OF_DAY);
                            minuto = dataHoraAgendamento.get(Calendar.MINUTE);

                            if (dataHoraAgendamento.before(dataHoraAtual)) {
                                System.out.println("\nNão é possível agendar uma consulta para uma data passada.");
                                break;
                            }

                            Agendamento novoAgendamento = new Agendamento();
                            novoAgendamento.setIdPaciente(pacienteSelecionado.getId());
                            novoAgendamento.setNome(nomeConsulta);
                            novoAgendamento.setEspecialidade(especialidadeConsulta);
                            novoAgendamento.setDataAgendamento(dataAgendamento);
                            novoAgendamento.setHora(hora);
                            novoAgendamento.setMinuto(minuto);

                            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
                            agendamentoDAO.adicionarAgendamento(novoAgendamento);

                        } catch (ParseException e) {
                            System.out.println("\nData ou hora inválida!");

                        }
                    }
                    break;
                }
                case 3 -> {
                    // Listar as consultas agendadas
                    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
                    List<Agendamento> consultasAgendadas = agendamentoDAO.listarConsultasAgendadas();

                    System.out.println("\nConsultas Agendadas:");
                    for (Agendamento consulta : consultasAgendadas) {
                        System.out.println("ID: " + consulta.getIdPaciente() + " | Nome Paciente: " + consulta.getNome() + " | Especialidade: " + consulta.getEspecialidade() + " | Data: " + consulta.getDataAgendamento());
                    }

                    System.out.print("Selecione o ID do paciente para cancelar a consulta: ");
                    long idConsultaSelecionada = teclado.nextLong();
                    teclado.nextLine(); // Limpar o buffer de entrada

                    // Remover a consulta agendada
                    boolean consultaRemovida = agendamentoDAO.removerConsulta(idConsultaSelecionada);
                    if (consultaRemovida) {
                        System.out.println("\nConsulta cancelada com sucesso!");
                    } else {
                        System.out.println("\nFalha ao cancelar a consulta. Verifique o ID informado.");
                    }
                    break;
                }

                case 0 -> {

                    System.out.println("\nSaindo do sistema...");
                    break;
                }

                default -> {
                    System.out.println("Digite uma opção valida.");
                    break;
                }
            }

        } while (opc != 0);
    }
}
