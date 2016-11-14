package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Pair;

import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public class PDAInstance {

    private static final String TITLE = "Instance";
    private static final String MASTER_DATABASE_NAME = "pda_database";

    private PDADatabase mPDADatabase;

    public PDAInstance( Context context ) {

        mPDADatabase = new PDADatabase( context, MASTER_DATABASE_NAME );
    }

    public String getTitle() {

        return TITLE;
    }

    @Nullable
    private List<String> getDatabaseNames() {

        List<Pair<String, String>> attachedDatabases = mPDADatabase.exec().getAttachedDbs();
        return null;
    }
}
