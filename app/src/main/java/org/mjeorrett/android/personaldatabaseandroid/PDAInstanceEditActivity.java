package org.mjeorrett.android.personaldatabaseandroid;

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
}
