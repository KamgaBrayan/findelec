package org.jhipster.findelec.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ParcourTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Parcour getParcourSample1() {
        return new Parcour().id(1L);
    }

    public static Parcour getParcourSample2() {
        return new Parcour().id(2L);
    }

    public static Parcour getParcourRandomSampleGenerator() {
        return new Parcour().id(longCount.incrementAndGet());
    }
}
