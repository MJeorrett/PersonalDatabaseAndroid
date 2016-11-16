package org.mjeorrett.android.personaldatabaseandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import org.mjeorrett.android.personaldatabaseandroid.core.DoubleFragmentActivity;
import org.mjeorrett.android.personaldatabaseandroid.core.PDAEntityListFragment;
import org.mjeorrett.android.personaldatabaseandroid.core.PDATitleEditFragment;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;
import org.w3c.dom.Text;

/**
 * Created by user on 15/11/2016.
 */

public class PDADatabaseEditActivity extends DoubleFragmentActivity {

    private Fragment mTopFragment;
    private Fragment mBottomFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setTitle( "Edit Database" );
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
                PDATableEditActivity.class,
                true );

        return mBottomFragment;
    }

    private String getDatabaseName() {

        Bundle extras = getIntent().getExtras();
        String databaseName = extras.getString( PDAEntityType.DATABASE.getIntentKey() );

        return databaseName;
    }
}
