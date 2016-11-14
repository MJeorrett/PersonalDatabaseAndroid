package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;

/**
 * Created by user on 14/11/2016.
 */

public class PDAEntityServer {

    public static PDAEntity getPDAEntity( Context context, PDAEntityType entityType, String identifier ) {

        PDAEntity result = null;

        switch ( entityType ) {

            case INSTANCE:
                result = new PDAInstance( context );
                break;

            case DATABASE:
                result = new PDADatabase( context, identifier );
                break;
        }

        return result;
    }
}
