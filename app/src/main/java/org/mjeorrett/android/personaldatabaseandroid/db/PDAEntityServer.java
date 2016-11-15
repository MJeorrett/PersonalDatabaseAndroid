package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by user on 14/11/2016.
 */

public class PDAEntityServer {

    public static PDAEntity getPDAEntity(
            Context context,
            PDAEntityType entityType,
            @Nullable String databaseName,
            @Nullable String tableName,
            @Nullable String columnName,
            @Nullable UUID rowId ) {

        PDAEntity result = null;

        switch ( entityType ) {

            case INSTANCE:
                result = new PDAInstance( context );
                break;

            case DATABASE:
                result = new PDADatabase( context, databaseName );
                break;
        }

        return result;
    }
}
