package org.mjeorrett.android.personaldatabaseandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import org.mjeorrett.android.personaldatabaseandroid.core.DoubleFragmentActivity;
import org.mjeorrett.android.personaldatabaseandroid.core.PDAEntityListFragment;
import org.mjeorrett.android.personaldatabaseandroid.core.PDATitleEditFragment;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;

/**
 * Created by user on 15/11/2016.
 */

public class PDATableEditActivity extends DoubleFragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle( "Edit Table");
    }

    @Override
    protected Fragment createTopFragment() {

        Fragment fragment = PDATitleEditFragment.newInstance(
                PDAEntityType.TABLE,
                getDatabaseName(),
                getTableName(),
                null,
                null
        );

        return fragment;
    }

    @Override
    protected Fragment createBottomFragment() {

        Fragment fragment = PDAEntityListFragment.newInstance(
                PDAEntityType.TABLE,
                getDatabaseName(),
                getTableName(),
                null,
                null,
                null
        );

        return fragment;
    }

    private String getDatabaseName() {

        return getExtraForKey( PDAEntityType.DATABASE.getIntentKey() );
    }

    private String getTableName() {

        return getExtraForKey( PDAEntityType.TABLE.getIntentKey() );
    }

    private String getExtraForKey( String key ) {

        Bundle extras = getIntent().getExtras();
        String result = extras.getString( key );
        return result;
    }
}
