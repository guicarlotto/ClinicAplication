package modelo;

public class TelefonePacientes {
    
        Long id;
        String nome, telefone; 
    
    public TelefonePacientes(String nome, String telefone){
        this.nome = nome;
        this.telefone = telefone;
    }

    public TelefonePacientes() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
}
