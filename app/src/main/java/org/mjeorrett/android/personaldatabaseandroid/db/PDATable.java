package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by user on 15/11/2016.
 */

public class PDATable implements PDAEntity {

    private static final String TAG = "PDATable";

    private PDADatabase mDatabase;
    private String mName;

    PDATable( PDADatabase database, String name ) {

        mDatabase = database;
        mName = name;
    }

    @Override
    public String getTitle() {

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

}
