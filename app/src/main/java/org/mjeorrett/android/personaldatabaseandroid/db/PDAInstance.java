package org.mjeorrett.android.personaldatabaseandroid.db;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Pair;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public class PDAInstance implements PDAEntity {

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
        List<String> result = new ArrayList<>();

        for ( Pair<String, String> dbInfo : attachedDatabases ) {

            String fullPath = dbInfo.second;
            int dbNameStartIndex = fullPath.lastIndexOf( '/' ) + 1;
            String dbFile = fullPath.substring( dbNameStartIndex );
            dbFile = FilenameUtils.getBaseName( dbFile );
            result.add( dbFile );
        }

        return result;
    }

    @Override
    public List<PDAEntity> getChildEntites( Context context ) {

        List<PDAEntity> children = new ArrayList<>();
        List<String> databaseNames = this.getDatabaseNames();

        for ( String databaseName : databaseNames ) {
            PDADatabase database = new PDADatabase( context, databaseName );
            children.add( database );
        }

        return children;
    }
}
