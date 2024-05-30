package org.jhipster.findelec.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.jhipster.findelec.domain.Objet;
import org.jhipster.findelec.domain.enumeration.ObjetType;
import org.jhipster.findelec.domain.enumeration.StatutType;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Objet}, with proper type conversions.
 */
@Service
public class ObjetRowMapper implements BiFunction<Row, String, Objet> {

    private final ColumnConverter converter;

    public ObjetRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Objet} stored in the database.
     */
    @Override
    public Objet apply(Row row, String prefix) {
        Objet entity = new Objet();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNom(converter.fromRow(row, prefix + "_nom", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", ObjetType.class));
        entity.setStatut(converter.fromRow(row, prefix + "_statut", StatutType.class));
        entity.setUtilisateurId(converter.fromRow(row, prefix + "_utilisateur_id", Long.class));
        return entity;
    }
}
