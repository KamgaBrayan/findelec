package org.jhipster.findelec.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TrajetTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Trajet getTrajetSample1() {
        return new Trajet().id(1L).villeDepart("villeDepart1").villeArrivee("villeArrivee1").nombrePlacesDisponibles(1);
    }

    public static Trajet getTrajetSample2() {
        return new Trajet().id(2L).villeDepart("villeDepart2").villeArrivee("villeArrivee2").nombrePlacesDisponibles(2);
    }

    public static Trajet getTrajetRandomSampleGenerator() {
        return new Trajet()
            .id(longCount.incrementAndGet())
            .villeDepart(UUID.randomUUID().toString())
            .villeArrivee(UUID.randomUUID().toString())
            .nombrePlacesDisponibles(intCount.incrementAndGet());
    }
}
