package org.jhipster.findelec.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.findelec.domain.LocationTestSamples.*;
import static org.jhipster.findelec.domain.UtilisateurTestSamples.*;

import org.jhipster.findelec.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = getLocationSample1();
        Location location2 = new Location();
        assertThat(location1).isNotEqualTo(location2);

        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);

        location2 = getLocationSample2();
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    void utilisateurTest() throws Exception {
        Location location = getLocationRandomSampleGenerator();
        Utilisateur utilisateurBack = getUtilisateurRandomSampleGenerator();

        location.setUtilisateur(utilisateurBack);
        assertThat(location.getUtilisateur()).isEqualTo(utilisateurBack);

        location.utilisateur(null);
        assertThat(location.getUtilisateur()).isNull();
    }
}
