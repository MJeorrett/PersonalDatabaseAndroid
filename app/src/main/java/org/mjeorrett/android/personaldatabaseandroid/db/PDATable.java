package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Intent;
import android.util.Log;

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
        return null;
    }

    private List<String> columnNames() {

        return mDatabase.columnNamesForTable( mName );
    }

    @Override
    public void createNewChildEntity( String title) {

        Log.i( TAG, String.format( "createNewChildEntity( %s ) called.", title ) );
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
