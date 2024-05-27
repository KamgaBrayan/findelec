package org.jhipster.findelec.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Trajet.
 */
@Table("trajet")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Trajet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("ville_depart")
    private String villeDepart;

    @NotNull(message = "must not be null")
    @Column("ville_arrivee")
    private String villeArrivee;

    @NotNull(message = "must not be null")
    @Column("date_depart")
    private Instant dateDepart;

    @NotNull(message = "must not be null")
    @Column("nombre_places_disponibles")
    private Integer nombrePlacesDisponibles;

    @Transient
    @JsonIgnoreProperties(value = { "trajets", "locations" }, allowSetters = true)
    private Utilisateur utilisateur;

    @Column("utilisateur_id")
    private Long utilisateurId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Trajet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVilleDepart() {
        return this.villeDepart;
    }

    public Trajet villeDepart(String villeDepart) {
        this.setVilleDepart(villeDepart);
        return this;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getVilleArrivee() {
        return this.villeArrivee;
    }

    public Trajet villeArrivee(String villeArrivee) {
        this.setVilleArrivee(villeArrivee);
        return this;
    }

    public void setVilleArrivee(String villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    public Instant getDateDepart() {
        return this.dateDepart;
    }

    public Trajet dateDepart(Instant dateDepart) {
        this.setDateDepart(dateDepart);
        return this;
    }

    public void setDateDepart(Instant dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Integer getNombrePlacesDisponibles() {
        return this.nombrePlacesDisponibles;
    }

    public Trajet nombrePlacesDisponibles(Integer nombrePlacesDisponibles) {
        this.setNombrePlacesDisponibles(nombrePlacesDisponibles);
        return this;
    }

    public void setNombrePlacesDisponibles(Integer nombrePlacesDisponibles) {
        this.nombrePlacesDisponibles = nombrePlacesDisponibles;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.utilisateurId = utilisateur != null ? utilisateur.getId() : null;
    }

    public Trajet utilisateur(Utilisateur utilisateur) {
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
        if (!(o instanceof Trajet)) {
            return false;
        }
        return getId() != null && getId().equals(((Trajet) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trajet{" +
            "id=" + getId() +
            ", villeDepart='" + getVilleDepart() + "'" +
            ", villeArrivee='" + getVilleArrivee() + "'" +
            ", dateDepart='" + getDateDepart() + "'" +
            ", nombrePlacesDisponibles=" + getNombrePlacesDisponibles() +
            "}";
    }
}
