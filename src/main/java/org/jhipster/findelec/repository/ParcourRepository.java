package org.jhipster.findelec.repository;

import org.jhipster.findelec.domain.Parcour;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Parcour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParcourRepository extends ReactiveCrudRepository<Parcour, Long>, ParcourRepositoryInternal {
    Flux<Parcour> findAllBy(Pageable pageable);

    @Override
    <S extends Parcour> Mono<S> save(S entity);

    @Override
    Flux<Parcour> findAll();

    @Override
    Mono<Parcour> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ParcourRepositoryInternal {
    <S extends Parcour> Mono<S> save(S entity);

    Flux<Parcour> findAllBy(Pageable pageable);

    Flux<Parcour> findAll();

    Mono<Parcour> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Parcour> findAllBy(Pageable pageable, Criteria criteria);
}
