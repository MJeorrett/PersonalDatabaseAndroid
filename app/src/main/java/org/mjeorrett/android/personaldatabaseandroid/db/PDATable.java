package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 15/11/2016.
 */

public class PDATable implements PDAEntity {

    private static final String TAG = "PDATable";

    private String mName;
    private PDADatabase mDatabase;
    private List<PDAColumn> mColumns;
    private List<PDARow> mRows;

    PDATable( PDADatabase database, String name ) {

        mDatabase = database;
        mName = name;
        this.loadColumns();
    }

    @Override
    public String getName() {

        return mName;
    }

    @Override
    public List<PDAEntity> getChildEntities() {

        return new ArrayList<PDAEntity>( mColumns );
    }

    @Override
    public String getChildTypeDescription() {

        return "Column";
    }

    @Override
    public PDAEntityType getType() {

        return PDAEntityType.TABLE;
    }

    @Override
    public void createNewChildEntity( String name ) {

        List<String> existingColumnNames = mDatabase.columnNamesForTable( mName );
        String cleanName = PDADbHelper.sanitizeSQLLiteIdentifier( name, existingColumnNames, TAG );

        if ( cleanName != null ) {

            String query = "ALTER TABLE " + mName + " ADD COLUMN " + cleanName + ";";
            mDatabase.executeSql( query );
            PDAColumn newColumn = new PDAColumn( this, cleanName );
            mColumns.add( newColumn );
        }
    }

    private List<String> columnNames() {

        return mDatabase.columnNamesForTable( mName );
    }

    private void loadColumns() {

        mColumns = new ArrayList<>();
        PDAColumn aColumn;

        for ( String columnName : this.columnNames() ) {

            aColumn = new PDAColumn( this, columnName );
            mColumns.add( aColumn );
        }
    }

    private void loadRows() {

        mRows = new ArrayList<>();
        PDARow aRow;

        Cursor cursor = mDatabase.queryTableWhere( mName, null, null, null );
        PDACursorWrapper cursorWrapper = new PDACursorWrapper( cursor );

        List<HashMap<String, String>> data = cursorWrapper.getStringData();



        cursor.close();
    }

    @Override
    public Intent putExtrasInIntent(Intent intent) {

        intent.putExtra( PDAEntityType.DATABASE.getIntentKey(), mDatabase.getName() );
        intent.putExtra( PDAEntityType.TABLE.getIntentKey(), mName );

        return intent;
    }
}
