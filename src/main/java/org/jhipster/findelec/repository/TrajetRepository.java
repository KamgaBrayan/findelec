package org.jhipster.findelec.repository;

import org.jhipster.findelec.domain.Trajet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Trajet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrajetRepository extends ReactiveCrudRepository<Trajet, Long>, TrajetRepositoryInternal {
    @Query("SELECT * FROM trajet entity WHERE entity.utilisateur_id = :id")
    Flux<Trajet> findByUtilisateur(Long id);

    @Query("SELECT * FROM trajet entity WHERE entity.utilisateur_id IS NULL")
    Flux<Trajet> findAllWhereUtilisateurIsNull();

    @Override
    <S extends Trajet> Mono<S> save(S entity);

    @Override
    Flux<Trajet> findAll();

    @Override
    Mono<Trajet> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface TrajetRepositoryInternal {
    <S extends Trajet> Mono<S> save(S entity);

    Flux<Trajet> findAllBy(Pageable pageable);

    Flux<Trajet> findAll();

    Mono<Trajet> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Trajet> findAllBy(Pageable pageable, Criteria criteria);
}
