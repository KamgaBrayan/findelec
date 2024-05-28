package org.jhipster.findelec.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jhipster.findelec.domain.ParcourTestSamples.*;

import org.jhipster.findelec.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParcourTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parcour.class);
        Parcour parcour1 = getParcourSample1();
        Parcour parcour2 = new Parcour();
        assertThat(parcour1).isNotEqualTo(parcour2);

        parcour2.setId(parcour1.getId());
        assertThat(parcour1).isEqualTo(parcour2);

        parcour2 = getParcourSample2();
        assertThat(parcour1).isNotEqualTo(parcour2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Parcour parcour = new Parcour();
        assertThat(parcour.hashCode()).isZero();

        Parcour parcour1 = getParcourSample1();
        parcour.setId(parcour1.getId());
        assertThat(parcour).hasSameHashCodeAs(parcour1);
    }
}
