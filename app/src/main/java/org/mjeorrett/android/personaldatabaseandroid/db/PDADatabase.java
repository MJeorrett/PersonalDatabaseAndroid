package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public class PDADatabase extends SQLiteOpenHelper implements PDAEntity {

    private static final String TAG = "PDADatabase";
    private static final int VERSION = 1;

    private String mName;
    private SQLiteDatabase mDatabase;
    private List<PDATable> mTables;

    PDADatabase( Context context, String name ) {

        super( context, name + ".db", null, VERSION );

        mName = name;

//        if the database does not exist this creates it and calls onCreate() in PDADatabaseHelper
//        also calls 'onUpgrade if versions is lower
        mDatabase = this.getWritableDatabase();
//        this.loadTables();
    }

    @Override
    public String getName() {

        return mName;
    }

    @Override
    public List<PDAEntity> getChildEntities( @Nullable PDAEntityType type ) {

        if ( mTables == null ) loadTables();

        return new ArrayList<PDAEntity>( mTables );
    }

    @Override
    public String getChildTypeDescription() {

        return "Table";
    }

    @Override
    public PDAEntityType getType() {

        return PDAEntityType.DATABASE;
    }

    @Override
    public Intent putExtrasInIntent(Intent intent) {

        intent.putExtra( PDAEntityType.DATABASE.getIntentKey(), mName );

        return intent;
    }

    @Override
    public void createNewChildEntity( Context context, String name ) {

        ArrayList<String> tableNames = (ArrayList<String>) tableNames();

        String cleanName = PDADbHelper.sanitizeSQLLiteIdentifier( context, name, tableNames, TAG );

        if ( cleanName != null ) {

            String sql = "CREATE TABLE " + cleanName + " ( "
                    + "_id INTEGER primary key autoincrement, "
                    + " uuid )";

            mDatabase.execSQL( sql );
            PDATable newTable = new PDATable( this, cleanName );

            if ( mTables == null ) loadTables();

            mTables.add(newTable);
        }
    }

    private void loadTables() {

        mTables = new ArrayList<>();
        PDATable newTable;

        for ( String tableName : tableNames() ) {

            if ( !tableName.equals( "android_metadata" ) && !tableName.equals( "sqlite_sequence" ) ) {

                newTable = new PDATable(this, tableName);
                mTables.add(newTable);
            }
        }
    }

    PDACursorWrapper queryTableWhere( String tableName, String[] columns, String whereClause, String[] whereArgs ) {

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
        List<String> tableNames;

        tableNames = tableNamesCursor.getSingleColumnStringData();

        tableNamesCursor.close();

        return tableNames;
    }

    public List<String> columnNamesForTable(String tableName ) {

        Cursor cursor = mDatabase.query( tableName, null, null, null, null, null, null, "1" );
        String[] columnNamesArray = cursor.getColumnNames();

        cursor.close();

        List columnNamesList = Arrays.asList( columnNamesArray );

        return columnNamesList;
    }

    protected void executeSql( String sql ) {

        mDatabase.execSQL( sql );
    }

    protected SQLiteDatabase exec() {

        return mDatabase;
    }

    @Override
    public void onCreate( SQLiteDatabase sqLiteDatabase ) {
//        intentionally left blank ( all user databases will be created empty )
    }

    @Override
    public void onUpgrade( SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion ) {
//        intentionally left blank
    }

}
