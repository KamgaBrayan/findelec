package org.jhipster.findelec.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.findelec.domain.ObjetTestSamples.*;
import static org.jhipster.findelec.domain.UtilisateurTestSamples.*;

import org.jhipster.findelec.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObjetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Objet.class);
        Objet objet1 = getObjetSample1();
        Objet objet2 = new Objet();
        assertThat(objet1).isNotEqualTo(objet2);

        objet2.setId(objet1.getId());
        assertThat(objet1).isEqualTo(objet2);

        objet2 = getObjetSample2();
        assertThat(objet1).isNotEqualTo(objet2);
    }

    @Test
    void utilisateurTest() throws Exception {
        Objet objet = getObjetRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        objet.setUtilisateur(utilisateurBack);
        assertThat(objet.getUtilisateur()).isEqualTo(utilisateurBack);

        objet.utilisateur(null);
        assertThat(objet.getUtilisateur()).isNull();
    }
}
