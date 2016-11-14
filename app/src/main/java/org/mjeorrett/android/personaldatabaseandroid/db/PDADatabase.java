package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public class PDADatabase extends SQLiteOpenHelper implements PDAEntity {

    private static final String TAG = "PDADatabase";

    private static final int VERSION = 1;

    private String mName;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public PDADatabase( Context context, String name ) {

        super( context, name + ".db", null, VERSION );

        mName = name;
        mContext = context.getApplicationContext();

//        if the database does not exist this creates it and calls onCreate() in PDADatabaseHelper
//        also calls 'onUpgrade if versions do not match
        mDatabase = this.getWritableDatabase();
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
    public void createNewChildEntity( Context context, String title ) {

        Log.i( TAG, String.format( "createNewChildEntity( %s ) called", title ) );
    }

    public SQLiteDatabase exec() {

        return  mDatabase;
    }

    @Override
    public void onCreate( SQLiteDatabase sqLiteDatabase ) {
//        intentionally left blank as all user databases will be created empty
    }

    @Override
    public void onUpgrade( SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion ) {
//        intentionally left blank
    }

}
