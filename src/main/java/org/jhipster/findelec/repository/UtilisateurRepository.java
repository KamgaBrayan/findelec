package org.jhipster.findelec.repository;

import org.jhipster.findelec.domain.Utilisateur;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Utilisateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UtilisateurRepository extends ReactiveCrudRepository<Utilisateur, Long>, UtilisateurRepositoryInternal {
    @Override
    <S extends Utilisateur> Mono<S> save(S entity);

    @Override
    Flux<Utilisateur> findAll();

    @Override
    Mono<Utilisateur> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface UtilisateurRepositoryInternal {
    <S extends Utilisateur> Mono<S> save(S entity);

    Flux<Utilisateur> findAllBy(Pageable pageable);

    Flux<Utilisateur> findAll();

    Mono<Utilisateur> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Utilisateur> findAllBy(Pageable pageable, Criteria criteria);
}
