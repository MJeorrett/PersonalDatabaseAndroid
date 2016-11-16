package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
//        this.loadColumns();
//        this.loadRows();
    }

    @Override
    public String getName() {

        return mName;
    }

    @Override
    public List<PDAEntity> getChildEntities( @Nullable PDAEntityType type ) {

        if ( type == PDAEntityType.COLUMN ) {

            if ( mColumns == null ) loadColumns();

            return new ArrayList<PDAEntity>( mColumns );
        }

        if ( type == PDAEntityType.ROW ) {

            if ( mRows == null ) loadRows();

            return  new ArrayList<PDAEntity>( mRows );
        }

        return null;
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
    public void createNewChildEntity( Context context, String name ) {

        List<String> existingColumnNames = mDatabase.columnNamesForTable( mName );
        String cleanName = PDADbHelper.sanitizeSQLLiteIdentifier( context, name, existingColumnNames, TAG );

        if ( cleanName != null ) {

            String query = "ALTER TABLE " + mName + " ADD COLUMN " + cleanName + ";";
            mDatabase.executeSql( query );
            PDAColumn newColumn = new PDAColumn( this, cleanName );

            if ( mColumns == null ) loadColumns();

            mColumns.add( newColumn );
        }
    }

    public PDARow newRow() {

        if ( mRows == null ) loadRows();

        PDARow newRow = new PDARow( this, columnNames() );
        ContentValues contentValues = newRow.getContentValues();
        mDatabase.exec().insert( mName, null, contentValues );
        mRows.add( newRow );

        return newRow;
    }

    public void updateRow( PDARow row ) {

        String uuidString = row.getId().toString();
        String[] whereArgs = { uuidString };
        String whereClause = "uuid = ?";
        ContentValues contentValues = row.getContentValues();
        mDatabase.exec().update(mName, contentValues, whereClause, whereArgs );
    }

    public PDARow getRow( UUID id ) {

        if ( mRows == null ) loadRows();

        for ( PDARow aRow : mRows ) {

            if ( aRow.getId().equals( id ) ) {
                return aRow;
            }
        }

        return null;
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

        for ( HashMap<String, String> dataRow : data ) {

            aRow = new PDARow( this, dataRow );
            mRows.add( aRow );
        }

        cursor.close();
    }

    @Override
    public Intent putExtrasInIntent(Intent intent) {

        intent.putExtra( PDAEntityType.DATABASE.getIntentKey(), mDatabase.getName() );
        intent.putExtra( PDAEntityType.TABLE.getIntentKey(), mName );

        return intent;
    }
}
