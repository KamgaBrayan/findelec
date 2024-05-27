package org.jhipster.findelec.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class TrajetSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("ville_depart", table, columnPrefix + "_ville_depart"));
        columns.add(Column.aliased("ville_arrivee", table, columnPrefix + "_ville_arrivee"));
        columns.add(Column.aliased("date_depart", table, columnPrefix + "_date_depart"));
        columns.add(Column.aliased("nombre_places_disponibles", table, columnPrefix + "_nombre_places_disponibles"));

        columns.add(Column.aliased("utilisateur_id", table, columnPrefix + "_utilisateur_id"));
        return columns;
    }
}
