package org.jhipster.findelec.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TrajetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Trajet getTrajetSample1() {
        return new Trajet().id(1L);
    }

    public static Trajet getTrajetSample2() {
        return new Trajet().id(2L);
    }

    public static Trajet getTrajetRandomSampleGenerator() {
        return new Trajet().id(longCount.incrementAndGet());
    }
}
