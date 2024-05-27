package org.jhipster.findelec.repository;

import org.jhipster.findelec.domain.Objet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Objet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ObjetRepository extends ReactiveCrudRepository<Objet, Long>, ObjetRepositoryInternal {
    @Query("SELECT * FROM objet entity WHERE entity.utilisateur_id = :id")
    Flux<Objet> findByUtilisateur(Long id);

    @Query("SELECT * FROM objet entity WHERE entity.utilisateur_id IS NULL")
    Flux<Objet> findAllWhereUtilisateurIsNull();

    @Override
    <S extends Objet> Mono<S> save(S entity);

    @Override
    Flux<Objet> findAll();

    @Override
    Mono<Objet> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ObjetRepositoryInternal {
    <S extends Objet> Mono<S> save(S entity);

    Flux<Objet> findAllBy(Pageable pageable);

    Flux<Objet> findAll();

    Mono<Objet> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Objet> findAllBy(Pageable pageable, Criteria criteria);
}
