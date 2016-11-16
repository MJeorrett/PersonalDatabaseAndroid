package org.mjeorrett.android.personaldatabaseandroid.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.mjeorrett.android.personaldatabaseandroid.R;

/**
 * Created by user on 15/11/2016.
 */

public abstract class DoubleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createTopFragment();
    protected abstract Fragment createBottomFragment();

    @Nullable protected abstract String getTopTitle();
    @Nullable protected abstract String getBottomTitle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_double_fragment );

        String topTitle = getTopTitle();
        TextView topTitleTextView = (TextView) findViewById(R.id.activity_double_fragment_top_title);

        if ( topTitle == null ) {

            topTitleTextView.setVisibility( View.GONE );

        } else {

            topTitleTextView.setText( topTitle );
        }

        String bottomTitle = getBottomTitle();
        TextView bottomTitleTextView = (TextView) findViewById( R.id.activity_double_fragment_bottom_title );

        if ( bottomTitle == null ) {

            bottomTitleTextView.setVisibility( View.GONE );

        } else {

            bottomTitleTextView.setText( bottomTitle );
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment topFragment = fragmentManager.findFragmentById( R.id.activity_double_fragment_top_container );
        Fragment bottomFragment = fragmentManager.findFragmentById( R.id.activity_double_fragment_bottom_container );

        if ( topFragment == null ) {

            topFragment = createTopFragment();
            fragmentManager.beginTransaction()
                    .add( R.id.activity_double_fragment_top_container, topFragment )
                    .commit();
        }

        if ( bottomFragment == null ) {

            bottomFragment = createBottomFragment();
            fragmentManager.beginTransaction()
                    .add( R.id.activity_double_fragment_bottom_container, bottomFragment )
                    .commit();
        }
    }
}
