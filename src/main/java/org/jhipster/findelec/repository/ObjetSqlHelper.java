package org.jhipster.findelec.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ObjetSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nom", table, columnPrefix + "_nom"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("type", table, columnPrefix + "_type"));
        columns.add(Column.aliased("statut", table, columnPrefix + "_statut"));

        columns.add(Column.aliased("utilisateur_id", table, columnPrefix + "_utilisateur_id"));
        return columns;
    }
}
