package org.jhipster.findelec.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.findelec.domain.LocationTestSamples.*;
import static org.jhipster.findelec.domain.TrajetTestSamples.*;
import static org.jhipster.findelec.domain.UtilisateurTestSamples.*;

import java.util.HashSet;
import java.util.Set;
import org.jhipster.findelec.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UtilisateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Utilisateur.class);
        Utilisateur utilisateur1 = getUtilisateurSample1();
        Utilisateur utilisateur2 = new Utilisateur();
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);

        utilisateur2.setId(utilisateur1.getId());
        assertThat(utilisateur1).isEqualTo(utilisateur2);

        utilisateur2 = getUtilisateurSample2();
        assertThat(utilisateur1).isNotEqualTo(utilisateur2);
    }

    @Test
    void trajetsTest() throws Exception {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Trajet trajetBack = getTrajetRandomSampleGenerator();

        utilisateur.addTrajets(trajetBack);
        assertThat(utilisateur.getTrajets()).containsOnly(trajetBack);
        assertThat(trajetBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.removeTrajets(trajetBack);
        assertThat(utilisateur.getTrajets()).doesNotContain(trajetBack);
        assertThat(trajetBack.getUtilisateur()).isNull();

        utilisateur.trajets(new HashSet<>(Set.of(trajetBack)));
        assertThat(utilisateur.getTrajets()).containsOnly(trajetBack);
        assertThat(trajetBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.setTrajets(new HashSet<>());
        assertThat(utilisateur.getTrajets()).doesNotContain(trajetBack);
        assertThat(trajetBack.getUtilisateur()).isNull();
    }

    @Test
    void locationsTest() throws Exception {
        Utilisateur utilisateur = getUtilisateurRandomSampleGenerator();
        Location locationBack = getLocationRandomSampleGenerator();

        utilisateur.addLocations(locationBack);
        assertThat(utilisateur.getLocations()).containsOnly(locationBack);
        assertThat(locationBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.removeLocations(locationBack);
        assertThat(utilisateur.getLocations()).doesNotContain(locationBack);
        assertThat(locationBack.getUtilisateur()).isNull();

        utilisateur.locations(new HashSet<>(Set.of(locationBack)));
        assertThat(utilisateur.getLocations()).containsOnly(locationBack);
        assertThat(locationBack.getUtilisateur()).isEqualTo(utilisateur);

        utilisateur.setLocations(new HashSet<>());
        assertThat(utilisateur.getLocations()).doesNotContain(locationBack);
        assertThat(locationBack.getUtilisateur()).isNull();
    }
}
