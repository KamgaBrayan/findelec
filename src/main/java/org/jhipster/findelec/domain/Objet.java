package org.jhipster.findelec.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.jhipster.findelec.domain.enumeration.ObjetType;
import org.jhipster.findelec.domain.enumeration.StatutType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Objet.
 */
@Table("objet")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Objet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("nom")
    private String nom;

    @Column("description")
    private String description;

    @NotNull(message = "must not be null")
    @Column("type")
    private ObjetType type;

    @NotNull(message = "must not be null")
    @Column("statut")
    private StatutType statut;

    @Transient
    @JsonIgnoreProperties(value = { "trajets", "locations" }, allowSetters = true)
    private Utilisateur utilisateur;

    @Column("utilisateur_id")
    private Long utilisateurId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Objet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Objet nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public Objet description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjetType getType() {
        return this.type;
    }

    public Objet type(ObjetType type) {
        this.setType(type);
        return this;
    }

    public void setType(ObjetType type) {
        this.type = type;
    }

    public StatutType getStatut() {
        return this.statut;
    }

    public Objet statut(StatutType statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutType statut) {
        this.statut = statut;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.utilisateurId = utilisateur != null ? utilisateur.getId() : null;
    }

    public Objet utilisateur(Utilisateur utilisateur) {
        this.setUtilisateur(utilisateur);
        return this;
    }

    public Long getUtilisateurId() {
        return this.utilisateurId;
    }

    public void setUtilisateurId(Long utilisateur) {
        this.utilisateurId = utilisateur;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Objet)) {
            return false;
        }
        return getId() != null && getId().equals(((Objet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Objet{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
}
