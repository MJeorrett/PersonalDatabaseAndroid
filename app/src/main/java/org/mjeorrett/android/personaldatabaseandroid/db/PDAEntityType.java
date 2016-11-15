package org.mjeorrett.android.personaldatabaseandroid.db;

/**
 * Created by user on 14/11/2016.
 */

public enum PDAEntityType {

    INSTANCE( "org.mjeorrett.android.personaldatabaseandroid.instance" ),
    DATABASE("org.mjeorrett.android.personaldatabaseandroid.database"),
    TABLE("org.mjeorrett.android.personaldatabaseandroid.table"),
    COLUMN("org.mjeorrett.android.personaldatabaseandroid.column"),
    ROW("org.mjeorrett.android.personaldatabaseandroid.row");

    private String mIntentKey;

    PDAEntityType( String intentKey ) {

        mIntentKey = intentKey;
    }

    public String getIntentKey() {

        return mIntentKey;
    }
}
