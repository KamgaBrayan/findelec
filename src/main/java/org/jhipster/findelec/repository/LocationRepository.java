package org.jhipster.findelec.repository;

import org.jhipster.findelec.domain.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends ReactiveCrudRepository<Location, Long>, LocationRepositoryInternal {
    @Query("SELECT * FROM location entity WHERE entity.utilisateur_id = :id")
    Flux<Location> findByUtilisateur(Long id);

    @Query("SELECT * FROM location entity WHERE entity.utilisateur_id IS NULL")
    Flux<Location> findAllWhereUtilisateurIsNull();

    @Override
    <S extends Location> Mono<S> save(S entity);

    @Override
    Flux<Location> findAll();

    @Override
    Mono<Location> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface LocationRepositoryInternal {
    <S extends Location> Mono<S> save(S entity);

    Flux<Location> findAllBy(Pageable pageable);

    Flux<Location> findAll();

    Mono<Location> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Location> findAllBy(Pageable pageable, Criteria criteria);
}
