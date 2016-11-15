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

    public List<HashMap<String, String>> getStringData() {

        List<HashMap<String, String>> results = new ArrayList<>();
        HashMap<String, String> row;
        String dataPoint;
        moveToFirst();

        while ( !isAfterLast() ) {

            row = new HashMap<>();

            for (String columnName : this.getColumnNames()) {

                dataPoint = getString(getColumnIndex(columnName));
                row.put(columnName, dataPoint);
            }

            results.add( row );
            moveToNext();
        }

        return results;
    }

    public List<String> getSingleColumnStringData() {

        List<String> results = new ArrayList<>();
        String dataPoint;

        moveToFirst();

        while ( !isAfterLast() ) {

            dataPoint = getString( 0 );
            results.add( dataPoint );
            moveToNext();
        }

        return results;
    }
}
