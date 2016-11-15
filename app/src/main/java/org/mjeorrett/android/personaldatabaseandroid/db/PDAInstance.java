package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public class PDAInstance implements PDAEntity {

    private static final String TAG = "PDAInstance";
    private static final String TITLE = "Instance";

    private List<PDADatabase> mDatabases;
    private Context mAppContext;

    PDAInstance(Context context) {

        mAppContext = context.getApplicationContext();
        this.loadDatabases( mAppContext );
    }

    public String getName() {

        return TITLE;
    }

    @Override
    public List<PDAEntity> getChildEntities() {

        return new ArrayList<PDAEntity>( mDatabases );
    }

    @Override
    public Intent putExtrasInIntent( Intent intent ) {

        return intent;
    }

    @Override
    public void createNewChildEntity( String title ) {

        List<String> databaseNames = PDADbHelper.getUserDatabaseNames( mAppContext );

        String cleanTitle = PDADbHelper.sanitizeSQLLiteIdentifier( title, databaseNames, TAG );

        if ( cleanTitle != null ) {

            PDADatabase newDatabase = new PDADatabase(mAppContext, title);
            mDatabases.add(newDatabase);
        }
    }

    private void loadDatabases( Context context ) {

        mDatabases = new ArrayList<>();
        List<String> databaseNames = PDADbHelper.getUserDatabaseNames( mAppContext );

        if ( databaseNames != null ) {

            for (String databaseName : databaseNames) {
                PDADatabase database = new PDADatabase(context, databaseName);
                mDatabases.add(database);
            }
        }
    }
}
