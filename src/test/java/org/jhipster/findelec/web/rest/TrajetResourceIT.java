package org.jhipster.findelec.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.jhipster.findelec.domain.TrajetAsserts.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.findelec.IntegrationTest;
import org.jhipster.findelec.domain.Trajet;
import org.jhipster.findelec.repository.EntityManager;
import org.jhipster.findelec.repository.TrajetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link TrajetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TrajetResourceIT {

    private static final String ENTITY_API_URL = "/api/trajets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Trajet trajet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trajet createEntity(EntityManager em) {
        Trajet trajet = new Trajet();
        return trajet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trajet createUpdatedEntity(EntityManager em) {
        Trajet trajet = new Trajet();
        return trajet;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Trajet.class).block();
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
        trajet = createEntity(em);
    }

    @Test
    void createTrajet() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Trajet
        var returnedTrajet = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(trajet))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Trajet.class)
            .returnResult()
            .getResponseBody();

        // Validate the Trajet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTrajetUpdatableFieldsEquals(returnedTrajet, getPersistedTrajet(returnedTrajet));
    }

    @Test
    void createTrajetWithExistingId() throws Exception {
        // Create the Trajet with an existing ID
        trajet.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(trajet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Trajet in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTrajetsAsStream() {
        // Initialize the database
        trajetRepository.save(trajet).block();

        List<Trajet> trajetList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Trajet.class)
            .getResponseBody()
            .filter(trajet::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(trajetList).isNotNull();
        assertThat(trajetList).hasSize(1);
        Trajet testTrajet = trajetList.get(0);

        // Test fails because reactive api returns an empty object instead of null
        // assertTrajetAllPropertiesEquals(trajet, testTrajet);
        assertTrajetUpdatableFieldsEquals(trajet, testTrajet);
    }

    @Test
    void getAllTrajets() {
        // Initialize the database
        trajetRepository.save(trajet).block();

        // Get all the trajetList
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
            .value(hasItem(trajet.getId().intValue()));
    }

    @Test
    void getTrajet() {
        // Initialize the database
        trajetRepository.save(trajet).block();

        // Get the trajet
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, trajet.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(trajet.getId().intValue()));
    }

    @Test
    void getNonExistingTrajet() {
        // Get the trajet
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void deleteTrajet() {
        // Initialize the database
        trajetRepository.save(trajet).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the trajet
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, trajet.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trajetRepository.count().block();
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

    protected Trajet getPersistedTrajet(Trajet trajet) {
        return trajetRepository.findById(trajet.getId()).block();
    }

    protected void assertPersistedTrajetToMatchAllProperties(Trajet expectedTrajet) {
        // Test fails because reactive api returns an empty object instead of null
        // assertTrajetAllPropertiesEquals(expectedTrajet, getPersistedTrajet(expectedTrajet));
        assertTrajetUpdatableFieldsEquals(expectedTrajet, getPersistedTrajet(expectedTrajet));
    }

    protected void assertPersistedTrajetToMatchUpdatableProperties(Trajet expectedTrajet) {
        // Test fails because reactive api returns an empty object instead of null
        // assertTrajetAllUpdatablePropertiesEquals(expectedTrajet, getPersistedTrajet(expectedTrajet));
        assertTrajetUpdatableFieldsEquals(expectedTrajet, getPersistedTrajet(expectedTrajet));
    }
}
