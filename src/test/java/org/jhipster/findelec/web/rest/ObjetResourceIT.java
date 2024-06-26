package org.jhipster.findelec.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.jhipster.findelec.domain.ObjetAsserts.*;
import static org.jhipster.findelec.web.rest.TestUtil.createUpdateProxyForBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.jhipster.findelec.IntegrationTest;
import org.jhipster.findelec.domain.Objet;
import org.jhipster.findelec.domain.enumeration.ObjetType;
import org.jhipster.findelec.domain.enumeration.StatutType;
import org.jhipster.findelec.repository.EntityManager;
import org.jhipster.findelec.repository.ObjetRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link ObjetResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ObjetResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ObjetType DEFAULT_TYPE = ObjetType.ELECTRONIQUE;
    private static final ObjetType UPDATED_TYPE = ObjetType.MAISON;

    private static final StatutType DEFAULT_STATUT = StatutType.DISPONIBLE;
    private static final StatutType UPDATED_STATUT = StatutType.VOLE;

    private static final String ENTITY_API_URL = "/api/objets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ObjetRepository objetRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Objet objet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Objet createEntity(EntityManager em) {
        Objet objet = new Objet().nom(DEFAULT_NOM).description(DEFAULT_DESCRIPTION).type(DEFAULT_TYPE).statut(DEFAULT_STATUT);
        return objet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Objet createUpdatedEntity(EntityManager em) {
        Objet objet = new Objet().nom(UPDATED_NOM).description(UPDATED_DESCRIPTION).type(UPDATED_TYPE).statut(UPDATED_STATUT);
        return objet;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Objet.class).block();
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
        objet = createEntity(em);
    }

    @Test
    void createObjet() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Objet
        var returnedObjet = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(objet))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(Objet.class)
            .returnResult()
            .getResponseBody();

        // Validate the Objet in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertObjetUpdatableFieldsEquals(returnedObjet, getPersistedObjet(returnedObjet));
    }

    @Test
    void createObjetWithExistingId() throws Exception {
        // Create the Objet with an existing ID
        objet.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(objet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Objet in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        objet.setNom(null);

        // Create the Objet, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(objet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        objet.setType(null);

        // Create the Objet, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(objet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStatutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        objet.setStatut(null);

        // Create the Objet, which fails.

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(objet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllObjetsAsStream() {
        // Initialize the database
        objetRepository.save(objet).block();

        List<Objet> objetList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(Objet.class)
            .getResponseBody()
            .filter(objet::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(objetList).isNotNull();
        assertThat(objetList).hasSize(1);
        Objet testObjet = objetList.get(0);

        // Test fails because reactive api returns an empty object instead of null
        // assertObjetAllPropertiesEquals(objet, testObjet);
        assertObjetUpdatableFieldsEquals(objet, testObjet);
    }

    @Test
    void getAllObjets() {
        // Initialize the database
        objetRepository.save(objet).block();

        // Get all the objetList
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
            .value(hasItem(objet.getId().intValue()))
            .jsonPath("$.[*].nom")
            .value(hasItem(DEFAULT_NOM))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE.toString()))
            .jsonPath("$.[*].statut")
            .value(hasItem(DEFAULT_STATUT.toString()));
    }

    @Test
    void getObjet() {
        // Initialize the database
        objetRepository.save(objet).block();

        // Get the objet
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, objet.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(objet.getId().intValue()))
            .jsonPath("$.nom")
            .value(is(DEFAULT_NOM))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE.toString()))
            .jsonPath("$.statut")
            .value(is(DEFAULT_STATUT.toString()));
    }

    @Test
    void getNonExistingObjet() {
        // Get the objet
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingObjet() throws Exception {
        // Initialize the database
        objetRepository.save(objet).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the objet
        Objet updatedObjet = objetRepository.findById(objet.getId()).block();
        updatedObjet.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION).type(UPDATED_TYPE).statut(UPDATED_STATUT);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedObjet.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(updatedObjet))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Objet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedObjetToMatchAllProperties(updatedObjet);
    }

    @Test
    void putNonExistingObjet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objet.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, objet.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(objet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Objet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchObjet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(objet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Objet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamObjet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(objet))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Objet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateObjetWithPatch() throws Exception {
        // Initialize the database
        objetRepository.save(objet).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the objet using partial update
        Objet partialUpdatedObjet = new Objet();
        partialUpdatedObjet.setId(objet.getId());

        partialUpdatedObjet.type(UPDATED_TYPE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedObjet.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedObjet))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Objet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertObjetUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedObjet, objet), getPersistedObjet(objet));
    }

    @Test
    void fullUpdateObjetWithPatch() throws Exception {
        // Initialize the database
        objetRepository.save(objet).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the objet using partial update
        Objet partialUpdatedObjet = new Objet();
        partialUpdatedObjet.setId(objet.getId());

        partialUpdatedObjet.nom(UPDATED_NOM).description(UPDATED_DESCRIPTION).type(UPDATED_TYPE).statut(UPDATED_STATUT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedObjet.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedObjet))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Objet in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertObjetUpdatableFieldsEquals(partialUpdatedObjet, getPersistedObjet(partialUpdatedObjet));
    }

    @Test
    void patchNonExistingObjet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objet.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, objet.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(objet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Objet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchObjet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(objet))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Objet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamObjet() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        objet.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(objet))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Objet in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteObjet() {
        // Initialize the database
        objetRepository.save(objet).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the objet
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, objet.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return objetRepository.count().block();
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

    protected Objet getPersistedObjet(Objet objet) {
        return objetRepository.findById(objet.getId()).block();
    }

    protected void assertPersistedObjetToMatchAllProperties(Objet expectedObjet) {
        // Test fails because reactive api returns an empty object instead of null
        // assertObjetAllPropertiesEquals(expectedObjet, getPersistedObjet(expectedObjet));
        assertObjetUpdatableFieldsEquals(expectedObjet, getPersistedObjet(expectedObjet));
    }

    protected void assertPersistedObjetToMatchUpdatableProperties(Objet expectedObjet) {
        // Test fails because reactive api returns an empty object instead of null
        // assertObjetAllUpdatablePropertiesEquals(expectedObjet, getPersistedObjet(expectedObjet));
        assertObjetUpdatableFieldsEquals(expectedObjet, getPersistedObjet(expectedObjet));
    }
}
