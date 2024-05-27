package org.jhipster.findelec.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.findelec.domain.TrajetTestSamples.*;
import static org.jhipster.findelec.domain.UtilisateurTestSamples.*;

import org.jhipster.findelec.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrajetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trajet.class);
        Trajet trajet1 = getTrajetSample1();
        Trajet trajet2 = new Trajet();
        assertThat(trajet1).isNotEqualTo(trajet2);

        trajet2.setId(trajet1.getId());
        assertThat(trajet1).isEqualTo(trajet2);

        trajet2 = getTrajetSample2();
        assertThat(trajet1).isNotEqualTo(trajet2);
    }

    @Test
    void utilisateurTest() throws Exception {
        Trajet trajet = getTrajetRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        trajet.setUtilisateur(utilisateurBack);
        assertThat(trajet.getUtilisateur()).isEqualTo(utilisateurBack);

        trajet.utilisateur(null);
        assertThat(trajet.getUtilisateur()).isNull();
    }
}
