package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "receita_segue_etapas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_receita", "id_etapas", "id_passos"})
})
public class ReceitaSegueEtapas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_receita_segue_etapas")
    private Long id_receita_segue_etapas;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita receita;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_etapas", referencedColumnName = "id_etapas", insertable = false, updatable = false),
            @JoinColumn(name = "id_passos", referencedColumnName = "id_passos", insertable = false, updatable = false)
    })
    private Etapas etapas;

    public ReceitaSegueEtapas() {}

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Long getIdReceita(Receita receita){return receita.getIdReceita();}

    public void setIdReceita(Long id_receita){this.receita.setIdReceita(id_receita);}

    public Etapas getEtapas() {
        return etapas;
    }

    public void setEtapas(Etapas etapas) {
        this.etapas = etapas;
    }

    public Long getIdEtapas(Etapas etapas){return etapas.getIdEtapas();}

    public void setIdEtapas(Long id_etapas){this.etapas.setIdEtapas(id_etapas);}

    public Long getIdReceitaSegueEtapas() {
        return id_receita_segue_etapas;
    }

    public void setId_receita_segue_etapas(Long id_receita_segue_etapas) {
        this.id_receita_segue_etapas = id_receita_segue_etapas;
    }
}
