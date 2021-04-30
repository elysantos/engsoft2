package br.edu.ifma.engsoft2.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Table(name = "CLIENTES")
public class Cliente {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "O nome não deve estar vazio")
    @Column(name = "NOME_CLIENTE")
    private String nome;

    @NotEmpty(message = "O cpf não deve estar vazio")
    @Column(name = "CPF")
    private String cpf;

    @Column(name = "TELEFONE1")
    private String telefone1;

    @Column(name = "TELEFONE2")
    private String telefone2;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DT_NASCIMENTO")
    private LocalDate dataNascimento;

    public Cliente() {
    }

    public Cliente(String nome, String cpf, String telefone1, String telefone2, String email, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }

    public Cliente(int id, String nome, String cpf, String telefone1, String telefone2, String email, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }


    public String getNome() {
        return nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone1='" + telefone1 + '\'' +
                ", telefone2='" + telefone2 + '\'' +
                ", email='" + email + '\'' +
                ", dataNascimento=" + dataNascimento +
                '}';
    }
}
