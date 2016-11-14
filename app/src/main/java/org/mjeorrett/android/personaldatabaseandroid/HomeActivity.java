package org.mjeorrett.android.personaldatabaseandroid;

import android.support.v4.app.Fragment;

import org.mjeorrett.android.personaldatabaseandroid.core.SingleFragmentActivity;

/**
 * Created by user on 14/11/2016.
 */

public class HomeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        return new HomeFragment();
    }
}
