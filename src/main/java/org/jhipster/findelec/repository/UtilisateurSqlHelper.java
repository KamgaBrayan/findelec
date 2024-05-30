package org.jhipster.findelec.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class UtilisateurSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nom", table, columnPrefix + "_nom"));
        columns.add(Column.aliased("prenom", table, columnPrefix + "_prenom"));
        columns.add(Column.aliased("email", table, columnPrefix + "_email"));
        columns.add(Column.aliased("mot_de_passe", table, columnPrefix + "_mot_de_passe"));

        return columns;
    }
}
