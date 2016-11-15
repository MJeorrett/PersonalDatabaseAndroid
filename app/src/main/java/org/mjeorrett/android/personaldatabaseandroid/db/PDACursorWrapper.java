package org.mjeorrett.android.personaldatabaseandroid.db;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 15/11/2016.
 */

public class PDACursorWrapper extends CursorWrapper {

    public PDACursorWrapper( Cursor cursor ) {

        super(cursor);
    }

    public HashMap<String, String> getStringData() {

        HashMap<String, String> row = new HashMap<>();
        String dataPoint;

        for ( String columnName : this.getColumnNames() ) {

            dataPoint = getString( getColumnIndex( columnName ) );
            row.put( columnName, dataPoint );
        }

        return row;
    }

    public String getSingleStringColumn() {

        String dataPoint = getString( 0 );
        return dataPoint;
    }
}
