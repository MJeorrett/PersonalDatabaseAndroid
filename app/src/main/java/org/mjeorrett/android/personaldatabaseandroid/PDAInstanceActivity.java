package org.mjeorrett.android.personaldatabaseandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import org.mjeorrett.android.personaldatabaseandroid.core.PDAEntityListFragment;
import org.mjeorrett.android.personaldatabaseandroid.core.SingleFragmentActivity;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;

/**
 * Created by user on 14/11/2016.
 */

public class PDAInstanceActivity extends SingleFragmentActivity {

    private static final String KEY_STRUCTURE_EDIT_MODE =
            "org.mjeorrett.android.personaldatabaseandroid.structure_edit_mode";

    private Fragment mFragment;
    private boolean mStructureEditMode;

    public static Intent newIntent( Context context, boolean structureEditMode ) {

        Intent intent = new Intent( context, PDAInstanceActivity.class );
        intent.putExtra( KEY_STRUCTURE_EDIT_MODE, structureEditMode );
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        mStructureEditMode = getIntent().getExtras().getBoolean( KEY_STRUCTURE_EDIT_MODE );

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
                PDADatabaseActivity.class,
                mStructureEditMode );

        return mFragment;
    }
}
