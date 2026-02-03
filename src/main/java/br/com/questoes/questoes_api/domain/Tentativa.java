package br.com.questoes.questoes_api.domain;

import br.com.questoes.questoes_api.enums.TentativaStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tentativa")
public class Tentativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "criada_em", nullable = false)
    private LocalDateTime criadaEm;

    @Column(name = "finalizada_em")
    private LocalDateTime finalizadaEm;

    @Column(name = "tempo_gasto_em_segundos")
    private Integer tempoGastoEmSegundos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TentativaStatus status;

    @Column
    private Boolean acertou;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessao_estudo_id")
    private SessaoEstudo sessaoEstudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prova_id")
    private Prova prova;

}
