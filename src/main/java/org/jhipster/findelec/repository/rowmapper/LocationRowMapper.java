package org.jhipster.findelec.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.jhipster.findelec.domain.Location;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Location}, with proper type conversions.
 */
@Service
public class LocationRowMapper implements BiFunction<Row, String, Location> {

    private final ColumnConverter converter;

    public LocationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Location} stored in the database.
     */
    @Override
    public Location apply(Row row, String prefix) {
        Location entity = new Location();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setAdresse(converter.fromRow(row, prefix + "_adresse", String.class));
        entity.setVille(converter.fromRow(row, prefix + "_ville", String.class));
        entity.setPays(converter.fromRow(row, prefix + "_pays", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setPrixParNuit(converter.fromRow(row, prefix + "_prix_par_nuit", Double.class));
        entity.setUtilisateurId(converter.fromRow(row, prefix + "_utilisateur_id", Long.class));
        return entity;
    }
}
