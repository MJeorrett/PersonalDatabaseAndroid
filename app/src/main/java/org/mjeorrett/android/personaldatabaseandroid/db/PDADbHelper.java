package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 15/11/2016.
 */

public class PDADbHelper {

    private static final String TAG = "PDADbHelper";

    public static String sanitizeSQLLiteIdentifier(String uncleanName, List<String> existingNames, String callingTag ) {

        String cleanName = uncleanName.replaceAll( " ", "_" );
        cleanName = cleanName.toLowerCase();

        if ( existingNames != null && existingNames.contains( cleanName ) ) {

            String message =
                    String.format( "User tried to duplicate SQLLite name '%s' " +
                            "so ignoring till I figure out something better to do", cleanName );
            Log.i( callingTag, message );
            return null;
        }

        if ( cleanName.equals( "" ) ) {
            String message =
                    "User tried to create SQLLite entity with no name" +
                            " so ignoring till I figure out something better to do";
            Log.i( callingTag, message );
            return null;
        }

        return cleanName;
    }

    @Nullable
    public static List<String> getUserDatabaseNames( Context context ) {

        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
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
}
