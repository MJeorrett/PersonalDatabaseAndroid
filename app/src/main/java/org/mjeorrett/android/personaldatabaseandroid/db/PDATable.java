package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 15/11/2016.
 */

public class PDATable implements PDAEntity {

    private static final String TAG = "PDATable";

    private String mName;
    private PDADatabase mDatabase;
    private ArrayList<PDAEntity> mColumns;

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

        return new ArrayList<>( mColumns );
    }

    @Override
    public String getChildTypeDescription() {

        return "Column";
    }

    @Override
    public PDAEntityType getType() {

        return PDAEntityType.TABLE;
    }

    private List<String> columnNames() {

        return mDatabase.columnNamesForTable( mName );
    }

    @Override
    public void createNewChildEntity( String name ) {

        List<String> existingColumnNames = mDatabase.columnNamesForTable( mName );
        String cleanColumnName = PDADbHelper.sanitizeSQLLiteIdentifier( name, existingColumnNames, TAG );

        if ( cleanColumnName != null ) {

            String query = "ALTER TABLE " + mName + " ADD COLUMN " + cleanColumnName + ";";
            mDatabase.executeSql( query );
            PDAColumn newColumn = new PDAColumn( this, cleanColumnName );
            mColumns.add( newColumn );
        }
    }

    private void loadColumns() {

        mColumns = new ArrayList<>();
        PDAColumn aColumn;

        for ( String columnName : this.columnNames() ) {

            aColumn = new PDAColumn( this, columnName );
            mColumns.add( aColumn );
        }
    }

    @Override
    public Intent putExtrasInIntent(Intent intent) {

        intent.putExtra( PDAEntityType.DATABASE.getIntentKey(), mDatabase.getName() );
        intent.putExtra( PDAEntityType.TABLE.getIntentKey(), mName );

        return intent;
    }
}
