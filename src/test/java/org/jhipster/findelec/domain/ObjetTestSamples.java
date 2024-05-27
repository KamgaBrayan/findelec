package org.jhipster.findelec.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ObjetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Objet getObjetSample1() {
        return new Objet().id(1L).nom("nom1").description("description1");
    }

    public static Objet getObjetSample2() {
        return new Objet().id(2L).nom("nom2").description("description2");
    }

    public static Objet getObjetRandomSampleGenerator() {
        return new Objet().id(longCount.incrementAndGet()).nom(UUID.randomUUID().toString()).description(UUID.randomUUID().toString());
    }
}
