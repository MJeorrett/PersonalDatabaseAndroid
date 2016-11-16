package org.mjeorrett.android.personaldatabaseandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import org.mjeorrett.android.personaldatabaseandroid.core.DoubleFragmentActivity;
import org.mjeorrett.android.personaldatabaseandroid.core.PDAEntityListFragment;
import org.mjeorrett.android.personaldatabaseandroid.core.PDATitleEditFragment;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;

/**
 * Created by user on 15/11/2016.
 */

public class PDADatabaseActivity extends DoubleFragmentActivity {

    private boolean mStructureEditMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        mStructureEditMode = PDAEntityListFragment.getAllowAddingChildren( getIntent().getExtras() );

        super.onCreate(savedInstanceState);
        this.setTitle( "Edit Database" );

        if ( !mStructureEditMode ) {

            setTitle( "Select Table" );
            hideTopContainer();
        }
    }

    @Nullable
    @Override
    protected String getTopTitle() {

        return "Database Name";
    }

    @Nullable
    @Override
    protected String getBottomTitle() {

        return "Tables";
    }

    @Override
    protected Fragment createTopFragment() {

        Fragment fragment = PDATitleEditFragment.newInstance(
                PDAEntityType.DATABASE,
                getDatabaseName(),
                null,
                null
        );

        return fragment;
    }

    @Override
    protected Fragment createBottomFragment() {

        Fragment fragment = PDAEntityListFragment.newInstance(
                PDAEntityType.DATABASE,
                getDatabaseName(),
                null,
                null,
                null,
                PDATableActivity.class,
                mStructureEditMode);

        return fragment;
    }

    private String getDatabaseName() {

        Bundle extras = getIntent().getExtras();
        String databaseName = extras.getString( PDAEntityType.DATABASE.getIntentKey() );

        return databaseName;
    }
}
