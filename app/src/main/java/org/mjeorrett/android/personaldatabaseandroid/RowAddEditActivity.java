package org.mjeorrett.android.personaldatabaseandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.mjeorrett.android.personaldatabaseandroid.core.SingleFragmentActivity;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;

import java.util.UUID;

/**
 * Created by user on 16/11/2016.
 */

public class RowAddEditActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        Fragment fragment = RowAddEditFragment.newInstance(getDatabaseName(), getTableName(), getRowIdString() );
        return fragment;
    }

    private String getDatabaseName() {

        return getExtraForKey( PDAEntityType.DATABASE.getIntentKey() );
    }

    private String getTableName() {

        return getExtraForKey( PDAEntityType.TABLE.getIntentKey() );
    }

    private String getRowIdString() {

        return getExtraForKey( PDAEntityType.ROW.getIntentKey() );
    }

    private String getExtraForKey( String key ) {

        Bundle extras = getIntent().getExtras();
        String result = extras.getString( key );
        return result;
    }
}
