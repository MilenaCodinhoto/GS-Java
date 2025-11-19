package br.com.fiap.model;

import jakarta.persistence.*;

@Entity
@Table(name = "COLABORADORES")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "EMAIL_CORP", nullable = false, unique = true)
    private String emailCorp;

    @Column(name = "CARGO", nullable = false)
    private String cargo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;

    // 🆕 Campo de senha simples (pra GS tá ok assim)
    @Column(name = "SENHA", nullable = false)
    private String senha;

    // GETTERS e SETTERS

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmailCorp() { return emailCorp; }
    public void setEmailCorp(String emailCorp) { this.emailCorp = emailCorp; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}