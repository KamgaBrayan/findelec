package org.jhipster.findelec.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.jhipster.findelec.domain.Parcour;
import org.jhipster.findelec.repository.ParcourRepository;
import org.jhipster.findelec.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.ForwardedHeaderUtils;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link org.jhipster.findelec.domain.Parcour}.
 */
@RestController
@RequestMapping("/api/parcours")
@Transactional
public class ParcourResource {

    private final Logger log = LoggerFactory.getLogger(ParcourResource.class);

    private static final String ENTITY_NAME = "parcour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParcourRepository parcourRepository;

    public ParcourResource(ParcourRepository parcourRepository) {
        this.parcourRepository = parcourRepository;
    }

    /**
     * {@code POST  /parcours} : Create a new parcour.
     *
     * @param parcour the parcour to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parcour, or with status {@code 400 (Bad Request)} if the parcour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<Parcour>> createParcour(@RequestBody Parcour parcour) throws URISyntaxException {
        log.debug("REST request to save Parcour : {}", parcour);
        if (parcour.getId() != null) {
            throw new BadRequestAlertException("A new parcour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return parcourRepository
            .save(parcour)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/parcours/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code GET  /parcours} : get all the parcours.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parcours in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Parcour>>> getAllParcours(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Parcours");
        return parcourRepository
            .count()
            .zipWith(parcourRepository.findAllBy(pageable).collectList())
            .map(
                countWithEntities ->
                    ResponseEntity.ok()
                        .headers(
                            PaginationUtil.generatePaginationHttpHeaders(
                                ForwardedHeaderUtils.adaptFromForwardedHeaders(request.getURI(), request.getHeaders()),
                                new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                            )
                        )
                        .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /parcours/:id} : get the "id" parcour.
     *
     * @param id the id of the parcour to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parcour, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Parcour>> getParcour(@PathVariable("id") Long id) {
        log.debug("REST request to get Parcour : {}", id);
        Mono<Parcour> parcour = parcourRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parcour);
    }

    /**
     * {@code DELETE  /parcours/:id} : delete the "id" parcour.
     *
     * @param id the id of the parcour to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteParcour(@PathVariable("id") Long id) {
        log.debug("REST request to delete Parcour : {}", id);
        return parcourRepository
            .deleteById(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
