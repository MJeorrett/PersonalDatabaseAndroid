package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FilenameFilter;
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

    public String getTitle() {

        return TITLE;
    }

    @Nullable
    private List<String> getUserDatabaseNames() {

        PackageManager packageManager = mAppContext.getPackageManager();
        String packageName = mAppContext.getPackageName();
        PackageInfo packageInfo = null;

        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch ( PackageManager.NameNotFoundException ex ) {
            Log.w( TAG, "Error package name not found ", ex );
        }

        List<String> results = null;

        if (packageInfo != null ) {

            String dataDirectory = packageInfo.applicationInfo.dataDir;
            String databasesPath = dataDirectory + "/databases";

            results = new ArrayList<>();

            File dir = new File( databasesPath );
            File[] databaseFiles = dir.listFiles( new FilenameFilter() {

                @Override
                public boolean accept( File dir, String name ) {

                    return name.endsWith(".db");
                }
            });

            if ( databaseFiles != null ) {

                for (File databaseFile : databaseFiles) {

                    String fullPath = databaseFile.getPath();
                    int dbNameStartIndex = fullPath.lastIndexOf('/') + 1;
                    String dbFile = fullPath.substring(dbNameStartIndex);
                    dbFile = FilenameUtils.getBaseName(dbFile);

                    results.add(dbFile);
                }
            }
        }

        return results;
    }

    @Override
    public List<PDAEntity> getChildEntities() {

        return new ArrayList<PDAEntity>( mDatabases );
    }

    @Override
    public void createNewChildEntity( String title ) {

        List<String> databaseNames = this.getUserDatabaseNames();

        if ( databaseNames != null && databaseNames.contains( title ) ) {

            Log.i( TAG, "User tried to duplicate database name so ignoring till I figure out something better to do");
            return;
        }

        if ( title.equals( "" ) ) {
            Log.i( TAG, "User tried to create database with no name so ignoring till I figure out something better to do");
            return;
        }

        PDADatabase newDatabase = new PDADatabase( mAppContext, title);
        mDatabases.add( newDatabase );
    }

    private void loadDatabases( Context context ) {

        mDatabases = new ArrayList<>();
        List<String> databaseNames = this.getUserDatabaseNames();

        if ( databaseNames != null ) {

            for (String databaseName : databaseNames) {
                PDADatabase database = new PDADatabase(context, databaseName);
                mDatabases.add(database);
            }
        }
    }
}
