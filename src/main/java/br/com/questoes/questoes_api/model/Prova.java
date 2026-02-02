package br.com.questoes.questoes_api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import br.com.questoes.questoes_api.domain.Banca;
import br.com.questoes.questoes_api.domain.Disciplina;
import br.com.questoes.questoes_api.domain.Questao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prova")
public class Prova {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ObjetivoProva objetivo;

    @Column(nullable = false)
    private Integer tempoEmMinutos;

    @Column(nullable = false)
    private Boolean ativa = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadaEm;

    @Column(nullable = false)
    private LocalDateTime atualizadaEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banca_id")
    private Banca banca;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "prova_disciplina",
        joinColumns = @JoinColumn(name = "prova_id"),
        inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    private Set<Disciplina> disciplinas;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "prova_questao",
        joinColumns = @JoinColumn(name = "prova_id"),
        inverseJoinColumns = @JoinColumn(name = "questao_id")
    )
    private Set<Questao> questoes;

    @PrePersist
    protected void onCreate() {
        criadaEm = LocalDateTime.now();
        atualizadaEm = criadaEm;
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadaEm = LocalDateTime.now();
    }

    public boolean isEditavel() {
        // Implementação futura: verificar se já foi utilizada
        return ativa;
    }
}

