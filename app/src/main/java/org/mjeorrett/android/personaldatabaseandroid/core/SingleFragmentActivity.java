package org.mjeorrett.android.personaldatabaseandroid.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.mjeorrett.android.personaldatabaseandroid.R;

/**
 * Created by user on 14/11/2016.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {



    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_single_fragment );

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById( R.id.fragment_container_1 );

        if ( fragment == null ) {

            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add( R.id.fragment_container_1, fragment )
                    .commit();
        }
    }
}
