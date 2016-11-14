package org.mjeorrett.android.personaldatabaseandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import org.mjeorrett.android.personaldatabaseandroid.core.PDAEntityListFragment;
import org.mjeorrett.android.personaldatabaseandroid.core.SingleFragmentActivity;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;

/**
 * Created by user on 14/11/2016.
 */

public class PDAInstanceEditActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {

        Fragment fragment = PDAEntityListFragment.newInstance( PDAEntityType.INSTANCE, null );
        return fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setTitle( "Databases" );
    }
}
