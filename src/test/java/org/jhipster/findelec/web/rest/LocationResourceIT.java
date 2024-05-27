package org.jhipster.findelec.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.jhipster.findelec.domain.LocationAsserts.*;
import static org.jhipster.findelec.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.findelec.IntegrationTest;
import org.jhipster.findelec.domain.Location;
import org.jhipster.findelec.repository.EntityManager;
import org.jhipster.findelec.repository.LocationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link LocationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class LocationResourceIT {

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_PAR_NUIT = 1D;
    private static final Double UPDATED_PRIX_PAR_NUIT = 2D;

    private static final String ENTITY_API_URL = "/api/locations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Location location;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
            .adresse(DEFAULT_ADRESSE)
            .ville(DEFAULT_VILLE)
            .pays(DEFAULT_PAYS)
            .description(DEFAULT_DESCRIPTION)
            .prixParNuit(DEFAULT_PRIX_PAR_NUIT);
        return location;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createUpdatedEntity(EntityManager em) {
        Location location = new Location()
            .adresse(UPDATED_ADRESSE)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .description(UPDATED_DESCRIPTION)
            .prixParNuit(UPDATED_PRIX_PAR_NUIT);
        return location;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Location.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        location = createEntity(em);
    }

    @Test
    void createLocation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Location
        var returnedLocation = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Location.class)
            .returnResult()
            .getResponseBody();

        // Validate the Location in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertLocationUpdatableFieldsEquals(returnedLocation, getPersistedLocation(returnedLocation));
    }

    @Test
    void createLocationWithExistingId() throws Exception {
        // Create the Location with an existing ID
        location.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkAdresseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setAdresse(null);

        // Create the Location, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkVilleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setVille(null);

        // Create the Location, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPaysIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setPays(null);

        // Create the Location, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkPrixParNuitIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        location.setPrixParNuit(null);

        // Create the Location, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllLocationsAsStream() {
        // Initialize the database
        locationRepository.save(location).block();

        List<Location> locationList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Location.class)
            .getResponseBody()
            .filter(location::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(locationList).isNotNull();
        assertThat(locationList).hasSize(1);
        Location testLocation = locationList.get(0);

        // Test fails because reactive api returns an empty object instead of null
        // assertLocationAllPropertiesEquals(location, testLocation);
        assertLocationUpdatableFieldsEquals(location, testLocation);
    }

    @Test
    void getAllLocations() {
        // Initialize the database
        locationRepository.save(location).block();

        // Get all the locationList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(location.getId().intValue()))
            .jsonPath("$.[*].adresse")
            .value(hasItem(DEFAULT_ADRESSE))
            .jsonPath("$.[*].ville")
            .value(hasItem(DEFAULT_VILLE))
            .jsonPath("$.[*].pays")
            .value(hasItem(DEFAULT_PAYS))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].prixParNuit")
            .value(hasItem(DEFAULT_PRIX_PAR_NUIT.doubleValue()));
    }

    @Test
    void getLocation() {
        // Initialize the database
        locationRepository.save(location).block();

        // Get the location
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, location.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(location.getId().intValue()))
            .jsonPath("$.adresse")
            .value(is(DEFAULT_ADRESSE))
            .jsonPath("$.ville")
            .value(is(DEFAULT_VILLE))
            .jsonPath("$.pays")
            .value(is(DEFAULT_PAYS))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.prixParNuit")
            .value(is(DEFAULT_PRIX_PAR_NUIT.doubleValue()));
    }

    @Test
    void getNonExistingLocation() {
        // Get the location
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingLocation() throws Exception {
        // Initialize the database
        locationRepository.save(location).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location
        Location updatedLocation = locationRepository.findById(location.getId()).block();
        updatedLocation
            .adresse(UPDATED_ADRESSE)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .description(UPDATED_DESCRIPTION)
            .prixParNuit(UPDATED_PRIX_PAR_NUIT);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedLocation.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(updatedLocation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLocationToMatchAllProperties(updatedLocation);
    }

    @Test
    void putNonExistingLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, location.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.save(location).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation.description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedLocation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Location in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLocation, location), getPersistedLocation(location));
    }

    @Test
    void fullUpdateLocationWithPatch() throws Exception {
        // Initialize the database
        locationRepository.save(location).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the location using partial update
        Location partialUpdatedLocation = new Location();
        partialUpdatedLocation.setId(location.getId());

        partialUpdatedLocation
            .adresse(UPDATED_ADRESSE)
            .ville(UPDATED_VILLE)
            .pays(UPDATED_PAYS)
            .description(UPDATED_DESCRIPTION)
            .prixParNuit(UPDATED_PRIX_PAR_NUIT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedLocation.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedLocation))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Location in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLocationUpdatableFieldsEquals(partialUpdatedLocation, getPersistedLocation(partialUpdatedLocation));
    }

    @Test
    void patchNonExistingLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, location.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLocation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        location.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(location))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Location in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLocation() {
        // Initialize the database
        locationRepository.save(location).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the location
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, location.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return locationRepository.count().block();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Location getPersistedLocation(Location location) {
        return locationRepository.findById(location.getId()).block();
    }

    protected void assertPersistedLocationToMatchAllProperties(Location expectedLocation) {
        // Test fails because reactive api returns an empty object instead of null
        // assertLocationAllPropertiesEquals(expectedLocation, getPersistedLocation(expectedLocation));
        assertLocationUpdatableFieldsEquals(expectedLocation, getPersistedLocation(expectedLocation));
    }

    protected void assertPersistedLocationToMatchUpdatableProperties(Location expectedLocation) {
        // Test fails because reactive api returns an empty object instead of null
        // assertLocationAllUpdatablePropertiesEquals(expectedLocation, getPersistedLocation(expectedLocation));
        assertLocationUpdatableFieldsEquals(expectedLocation, getPersistedLocation(expectedLocation));
    }
}
