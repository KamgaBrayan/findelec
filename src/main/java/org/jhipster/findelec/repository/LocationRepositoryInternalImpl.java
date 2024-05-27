package org.jhipster.findelec.repository;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.jhipster.findelec.domain.Location;
import org.jhipster.findelec.repository.rowmapper.LocationRowMapper;
import org.jhipster.findelec.repository.rowmapper.UtilisateurRowMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the Location entity.
 */
@SuppressWarnings("unused")
class LocationRepositoryInternalImpl extends SimpleR2dbcRepository<Location, Long> implements LocationRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final UtilisateurRowMapper utilisateurMapper;
    private final LocationRowMapper locationMapper;

    private static final Table entityTable = Table.aliased("location", EntityManager.ENTITY_ALIAS);
    private static final Table utilisateurTable = Table.aliased("utilisateur", "utilisateur");

    public LocationRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        UtilisateurRowMapper utilisateurMapper,
        LocationRowMapper locationMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Location.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.utilisateurMapper = utilisateurMapper;
        this.locationMapper = locationMapper;
    }

    @Override
    public Flux<Location> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Location> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = LocationSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(UtilisateurSqlHelper.getColumns(utilisateurTable, "utilisateur"));
        SelectFromAndJoinCondition selectFrom = Select.builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(utilisateurTable)
            .on(Column.create("utilisateur_id", entityTable))
            .equals(Column.create("id", utilisateurTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Location.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Location> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Location> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Location process(Row row, RowMetadata metadata) {
        Location entity = locationMapper.apply(row, "e");
        entity.setUtilisateur(utilisateurMapper.apply(row, "utilisateur"));
        return entity;
    }

    @Override
    public <S extends Location> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
