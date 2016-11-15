package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public class PDADatabase extends SQLiteOpenHelper implements PDAEntity {

    private static final String TAG = "PDADatabase";

    private static final int VERSION = 1;

    private String mName;
    private SQLiteDatabase mDatabase;
    private List<PDAEntity> mTables;
    private Context mAppContext;

    PDADatabase( Context context, String name ) {

        super( context, name + ".db", null, VERSION );

        mName = name;
        mAppContext = context.getApplicationContext();

//        if the database does not exist this creates it and calls onCreate() in PDADatabaseHelper
//        also calls 'onUpgrade if versions is lower
        mDatabase = this.getWritableDatabase();
        this.loadTables();
    }

    @Override
    public String getTitle() {

        return mName;
    }

    @Override
    public List<PDAEntity> getChildEntities() {

        return new ArrayList<>( mTables );
    }

    @Override
    public Intent putExtrasInIntent(Intent intent) {

        intent.putExtra( PDAEntityType.DATABASE.getIntentKey(), mName );

        return intent;
    }

    @Override
    public void createNewChildEntity( String title ) {

        ArrayList<String> tableNames = (ArrayList<String>) tableNames();

        if ( !tableNames.contains( title ) ) {

            String sql = "CREATE TABLE " + title + " ();";
            mDatabase.execSQL( sql );
            PDATable newTable = new PDATable( this, title );
        }

    }

    private void loadTables() {

        mTables = new ArrayList<>();
    }

    private PDACursorWrapper queryTableWhere( String tableName, String[] columns, String whereClause, String[] whereArgs ) {

        Cursor cursor = mDatabase.query(
                tableName,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                null );

        return new PDACursorWrapper( cursor );
    }

    public List<String> tableNames() {

        String[] columns = { "name" };
        PDACursorWrapper tableNamesCursor = this.queryTableWhere( "sqlite_master", columns, null, null );
        ArrayList<String> tableNames = new ArrayList<>();
        String aTableName;

        tableNamesCursor.moveToFirst();
        while ( !tableNamesCursor.isAfterLast() ) {

            aTableName = tableNamesCursor.getSingleStringColumn();
            tableNames.add( aTableName );
        }

        return tableNames;
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
