package org.jhipster.findelec.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.jhipster.findelec.domain.Trajet;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Trajet}, with proper type conversions.
 */
@Service
public class TrajetRowMapper implements BiFunction<Row, String, Trajet> {

    private final ColumnConverter converter;

    public TrajetRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Trajet} stored in the database.
     */
    @Override
    public Trajet apply(Row row, String prefix) {
        Trajet entity = new Trajet();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setVilleDepart(converter.fromRow(row, prefix + "_ville_depart", String.class));
        entity.setVilleArrivee(converter.fromRow(row, prefix + "_ville_arrivee", String.class));
        entity.setDateDepart(converter.fromRow(row, prefix + "_date_depart", Instant.class));
        entity.setNombrePlacesDisponibles(converter.fromRow(row, prefix + "_nombre_places_disponibles", Integer.class));
        entity.setUtilisateurId(converter.fromRow(row, prefix + "_utilisateur_id", Long.class));
        return entity;
    }
}
