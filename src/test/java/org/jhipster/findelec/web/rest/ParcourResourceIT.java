package org.jhipster.findelec.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.jhipster.findelec.domain.ParcourAsserts.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.findelec.IntegrationTest;
import org.jhipster.findelec.domain.Parcour;
import org.jhipster.findelec.repository.EntityManager;
import org.jhipster.findelec.repository.ParcourRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ParcourResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ParcourResourceIT {

    private static final String ENTITY_API_URL = "/api/parcours";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ParcourRepository parcourRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Parcour parcour;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parcour createEntity(EntityManager em) {
        Parcour parcour = new Parcour();
        return parcour;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parcour createUpdatedEntity(EntityManager em) {
        Parcour parcour = new Parcour();
        return parcour;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Parcour.class).block();
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
        parcour = createEntity(em);
    }

    @Test
    void createParcour() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Parcour
        var returnedParcour = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(parcour))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Parcour.class)
            .returnResult()
            .getResponseBody();

        // Validate the Parcour in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertParcourUpdatableFieldsEquals(returnedParcour, getPersistedParcour(returnedParcour));
    }

    @Test
    void createParcourWithExistingId() throws Exception {
        // Create the Parcour with an existing ID
        parcour.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(parcour))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Parcour in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllParcours() {
        // Initialize the database
        parcourRepository.save(parcour).block();

        // Get all the parcourList
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
            .value(hasItem(parcour.getId().intValue()));
    }

    @Test
    void getParcour() {
        // Initialize the database
        parcourRepository.save(parcour).block();

        // Get the parcour
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, parcour.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(parcour.getId().intValue()));
    }

    @Test
    void getNonExistingParcour() {
        // Get the parcour
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void deleteParcour() {
        // Initialize the database
        parcourRepository.save(parcour).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the parcour
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, parcour.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return parcourRepository.count().block();
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

    protected Parcour getPersistedParcour(Parcour parcour) {
        return parcourRepository.findById(parcour.getId()).block();
    }

    protected void assertPersistedParcourToMatchAllProperties(Parcour expectedParcour) {
        // Test fails because reactive api returns an empty object instead of null
        // assertParcourAllPropertiesEquals(expectedParcour, getPersistedParcour(expectedParcour));
        assertParcourUpdatableFieldsEquals(expectedParcour, getPersistedParcour(expectedParcour));
    }

    protected void assertPersistedParcourToMatchUpdatableProperties(Parcour expectedParcour) {
        // Test fails because reactive api returns an empty object instead of null
        // assertParcourAllUpdatablePropertiesEquals(expectedParcour, getPersistedParcour(expectedParcour));
        assertParcourUpdatableFieldsEquals(expectedParcour, getPersistedParcour(expectedParcour));
    }
}
