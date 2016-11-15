package org.mjeorrett.android.personaldatabaseandroid.db;

import android.util.Log;

import java.util.List;

/**
 * Created by user on 15/11/2016.
 */

public class PDADbHelper {

    public static String sanitizeSQLLiteIdentifier(String uncleanName, List<String> existingNames, String callingTag ) {

        if ( existingNames != null && existingNames.contains( uncleanName ) ) {

            String message =
                    String.format( "User tried to duplicate SQLLite name '%s' " +
                            "so ignoring till I figure out something better to do", uncleanName );
            Log.i( callingTag, message );
            return null;
        }

        if ( uncleanName.equals( "" ) ) {
            String message =
                    "User tried to create SQLLite entity with no name" +
                            " so ignoring till I figure out something better to do";
            Log.i( callingTag, message );
            return null;
        }

        String cleanString = uncleanName.replaceAll( " ", "_" );
        cleanString = cleanString.toLowerCase();

        return cleanString;
    }
}
