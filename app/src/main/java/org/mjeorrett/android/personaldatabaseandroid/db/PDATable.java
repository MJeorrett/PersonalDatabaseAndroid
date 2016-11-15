package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Intent;
import android.util.Log;

import java.util.List;

/**
 * Created by user on 15/11/2016.
 */

public class PDATable implements PDAEntity {

    private static final String TAG = "PDATable";

    private String mName;
    private PDADatabase mDatabase;

    PDATable( PDADatabase database, String name ) {

        mDatabase = database;
        mName = name;
    }

    @Override
    public String getName() {

        return mName;
    }

    @Override
    public List<PDAEntity> getChildEntities() {

        return null;
    }

    @Override
    public void createNewChildEntity( String title) {

        Log.i( TAG, String.format( "createNewChildEntity( %s ) called.", title ) );
    }

    @Override
    public Intent putExtrasInIntent(Intent intent) {

        intent.putExtra( PDAEntityType.DATABASE.getIntentKey(), mDatabase.getName() );
        intent.putExtra( PDAEntityType.TABLE.getIntentKey(), mName );

        return intent;
    }
}
