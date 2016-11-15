package org.mjeorrett.android.personaldatabaseandroid.db;

/**
 * Created by user on 14/11/2016.
 */

public enum PDAEntityType {

    INSTANCE("instance"),
    DATABASE("database"),
    TABLE("table"),
    COLUMN("column"),
    ROW("row");

    private String mIntentKey;

    PDAEntityType( String intentKey ) {

        mIntentKey = intentKey;
    }

    String getIntentKey() {

        return mIntentKey;
    }
}
