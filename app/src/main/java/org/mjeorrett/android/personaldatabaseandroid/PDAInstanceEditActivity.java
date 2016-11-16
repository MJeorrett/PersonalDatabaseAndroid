package org.mjeorrett.android.personaldatabaseandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.mjeorrett.android.personaldatabaseandroid.core.PDAEntityListFragment;
import org.mjeorrett.android.personaldatabaseandroid.core.SingleFragmentActivity;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;

/**
 * Created by user on 14/11/2016.
 */

public class PDAInstanceEditActivity extends SingleFragmentActivity {

    private Fragment mFragment;
    private boolean mAllowAddingChildren;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        mAllowAddingChildren = PDAEntityListFragment.getAllowAddingChildren( getIntent().getExtras() );

        super.onCreate(savedInstanceState);
        this.setTitle( "Databases" );
    }

    @Override
    protected Fragment createFragment() {

        mFragment = PDAEntityListFragment.newInstance(
                PDAEntityType.INSTANCE,
                null,
                null,
                null,
                null,
                PDADatabaseEditActivity.class,
                mAllowAddingChildren );

        return mFragment;
    }
}
