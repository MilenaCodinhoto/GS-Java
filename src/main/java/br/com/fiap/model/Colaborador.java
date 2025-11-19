package br.com.fiap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "colaborador",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<RegistroHumor> humores;

    @Column(name = "SENHA", nullable = false)
    private String senha;

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

    public String getEmailCorp() {
        return emailCorp;
    }
    public void setEmailCorp(String emailCorp) {
        this.emailCorp = emailCorp;
    }

    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<RegistroHumor> getHumores() {
        return humores;
    }
    public void setHumores(List<RegistroHumor> humores) {
        this.humores = humores;
    }
}