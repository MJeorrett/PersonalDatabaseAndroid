package org.mjeorrett.android.personaldatabaseandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.mjeorrett.android.personaldatabaseandroid.core.DoubleFragmentActivity;
import org.mjeorrett.android.personaldatabaseandroid.core.PDAEntityListFragment;
import org.mjeorrett.android.personaldatabaseandroid.core.PDATitleEditFragment;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;

/**
 * Created by user on 15/11/2016.
 */

public class PDADatabaseEditActivity extends DoubleFragmentActivity {

    private static final String TOP_FRAGMENT_KEY = "top_fragment";
    private static final String BOTTOM_FRAGMENT_KEY = "bottom_fragment";

    private Fragment mTopFragment;
    private Fragment mBottomFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setTitle( "Edit Database" );
    }

    @Override
    protected Fragment createTopFragment() {

        mTopFragment = PDATitleEditFragment.newInstance(
                PDAEntityType.DATABASE,
                getDatabaseName(),
                null,
                null
        );

        return mTopFragment;
    }

    @Override
    protected Fragment createBottomFragment() {

        mBottomFragment = PDAEntityListFragment.newInstance(
                PDAEntityType.DATABASE,
                getDatabaseName(),
                null,
                null,
                null,
                PDATableEditActivity.class
        );

        return mBottomFragment;
    }

    private String getDatabaseName() {

        Bundle extras = getIntent().getExtras();
        String databaseName = extras.getString( PDAEntityType.DATABASE.getIntentKey() );

        return databaseName;
    }
}
