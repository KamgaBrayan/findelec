package org.jhipster.findelec.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Utilisateur.
 */
@Table("utilisateur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("nom")
    private String nom;

    @NotNull(message = "must not be null")
    @Column("prenom")
    private String prenom;

    @NotNull(message = "must not be null")
    @Column("email")
    private String email;

    @NotNull(message = "must not be null")
    @Column("mot_de_passe")
    private String motDePasse;

    @Transient
    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    private Set<Trajet> trajets = new HashSet<>();

    @Transient
    @JsonIgnoreProperties(value = { "utilisateur" }, allowSetters = true)
    private Set<Location> locations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Utilisateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Utilisateur nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Utilisateur prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return this.email;
    }

    public Utilisateur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return this.motDePasse;
    }

    public Utilisateur motDePasse(String motDePasse) {
        this.setMotDePasse(motDePasse);
        return this;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Set<Trajet> getTrajets() {
        return this.trajets;
    }

    public void setTrajets(Set<Trajet> trajets) {
        if (this.trajets != null) {
            this.trajets.forEach(i -> i.setUtilisateur(null));
        }
        if (trajets != null) {
            trajets.forEach(i -> i.setUtilisateur(this));
        }
        this.trajets = trajets;
    }

    public Utilisateur trajets(Set<Trajet> trajets) {
        this.setTrajets(trajets);
        return this;
    }

    public Utilisateur addTrajets(Trajet trajet) {
        this.trajets.add(trajet);
        trajet.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeTrajets(Trajet trajet) {
        this.trajets.remove(trajet);
        trajet.setUtilisateur(null);
        return this;
    }

    public Set<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(Set<Location> locations) {
        if (this.locations != null) {
            this.locations.forEach(i -> i.setUtilisateur(null));
        }
        if (locations != null) {
            locations.forEach(i -> i.setUtilisateur(this));
        }
        this.locations = locations;
    }

    public Utilisateur locations(Set<Location> locations) {
        this.setLocations(locations);
        return this;
    }

    public Utilisateur addLocations(Location location) {
        this.locations.add(location);
        location.setUtilisateur(this);
        return this;
    }

    public Utilisateur removeLocations(Location location) {
        this.locations.remove(location);
        location.setUtilisateur(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utilisateur)) {
            return false;
        }
        return getId() != null && getId().equals(((Utilisateur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", email='" + getEmail() + "'" +
            ", motDePasse='" + getMotDePasse() + "'" +
            "}";
    }
}
