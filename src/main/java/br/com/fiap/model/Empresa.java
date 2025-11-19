package br.com.fiap.model;

import jakarta.persistence.*;

@Entity
@Table(name = "EMPRESAS")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // <- ESSENCIAL para e.id funcionar no HTML

    @Column(name = "CNPJ", nullable = false, unique = true)
    private String cnpj;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "SETOR_ATUACAO")
    private String setorAtuacao;

    // GETTERS E SETTERS

    public Long getId() {        // <- ESSENCIAL
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {    // <- usado no HTML como e.nome
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSetorAtuacao() {
        return setorAtuacao;
    }

    public void setSetorAtuacao(String setorAtuacao) {
        this.setorAtuacao = setorAtuacao;
    }
}