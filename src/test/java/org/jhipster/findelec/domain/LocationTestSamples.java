package org.jhipster.findelec.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LocationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Location getLocationSample1() {
        return new Location().id(1L).adresse("adresse1").ville("ville1").pays("pays1").description("description1");
    }

    public static Location getLocationSample2() {
        return new Location().id(2L).adresse("adresse2").ville("ville2").pays("pays2").description("description2");
    }

    public static Location getLocationRandomSampleGenerator() {
        return new Location()
            .id(longCount.incrementAndGet())
            .adresse(UUID.randomUUID().toString())
            .ville(UUID.randomUUID().toString())
            .pays(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
