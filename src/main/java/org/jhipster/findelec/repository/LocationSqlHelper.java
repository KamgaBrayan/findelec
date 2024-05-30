package org.jhipster.findelec.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class LocationSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("adresse", table, columnPrefix + "_adresse"));
        columns.add(Column.aliased("ville", table, columnPrefix + "_ville"));
        columns.add(Column.aliased("pays", table, columnPrefix + "_pays"));
        columns.add(Column.aliased("description", table, columnPrefix + "_description"));
        columns.add(Column.aliased("prix_par_nuit", table, columnPrefix + "_prix_par_nuit"));

        columns.add(Column.aliased("utilisateur_id", table, columnPrefix + "_utilisateur_id"));
        return columns;
    }
}
