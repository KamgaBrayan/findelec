package org.jhipster.findelec.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.jhipster.findelec.domain.Utilisateur;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Utilisateur}, with proper type conversions.
 */
@Service
public class UtilisateurRowMapper implements BiFunction<Row, String, Utilisateur> {

    private final ColumnConverter converter;

    public UtilisateurRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Utilisateur} stored in the database.
     */
    @Override
    public Utilisateur apply(Row row, String prefix) {
        Utilisateur entity = new Utilisateur();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNom(converter.fromRow(row, prefix + "_nom", String.class));
        entity.setPrenom(converter.fromRow(row, prefix + "_prenom", String.class));
        entity.setEmail(converter.fromRow(row, prefix + "_email", String.class));
        entity.setMotDePasse(converter.fromRow(row, prefix + "_mot_de_passe", String.class));
        return entity;
    }
}
