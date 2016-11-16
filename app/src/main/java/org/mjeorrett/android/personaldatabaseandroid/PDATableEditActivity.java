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

public class PDATableEditActivity extends DoubleFragmentActivity {

    private Fragment mTopFragment;
    private Fragment mBottomFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.setTitle( "Edit Table");
    }

    @Nullable
    @Override
    protected String getTopTitle() {

        return "Table Name";
    }

    @Nullable
    @Override
    protected String getBottomTitle() {

        return "Columns";
    }

    @Override
    protected Fragment createTopFragment() {

        mTopFragment = PDATitleEditFragment.newInstance(
                PDAEntityType.TABLE,
                getDatabaseName(),
                getTableName(),
                null);

        return mTopFragment;
    }

    @Override
    protected Fragment createBottomFragment() {

        mBottomFragment = PDAEntityListFragment.newInstance(
                PDAEntityType.TABLE,
                getDatabaseName(),
                getTableName(),
                null,
                null,
                null,
                true );

        return mBottomFragment;
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
