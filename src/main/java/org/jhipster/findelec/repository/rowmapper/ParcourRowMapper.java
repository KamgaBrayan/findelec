package org.jhipster.findelec.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.jhipster.findelec.domain.Parcour;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Parcour}, with proper type conversions.
 */
@Service
public class ParcourRowMapper implements BiFunction<Row, String, Parcour> {

    private final ColumnConverter converter;

    public ParcourRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Parcour} stored in the database.
     */
    @Override
    public Parcour apply(Row row, String prefix) {
        Parcour entity = new Parcour();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        return entity;
    }
}
