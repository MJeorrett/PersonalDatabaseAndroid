package org.mjeorrett.android.personaldatabaseandroid.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mjeorrett.android.personaldatabaseandroid.R;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntity;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityServer;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;

import java.util.UUID;

/**
 * Created by user on 15/11/2016.
 */

public class PDATitleEditFragment extends Fragment {

    private static final String TAG = "PDATitleEditFragment";

    private static final String ARG_ENTITY_TYPE =
            "com.mjeorrett.android.personal_database_android.entity_type";
    private static final String ARG_DATABASE_NAME =
            "com.mjeorrett.android.personal_database_android.database_name";
    private static final String ARG_TABLE_NAME =
            "com.mjeorrett.android.personal_database_android.table_name";
    private static final String ARG_COLUMN_NAME =
            "com.mjeorrett.android.personal_database_android.column_name";
    private static final String ARG_ROW_ID =
            "com.mjeorrett.android.personal_database_android.row_id";
    private static final String ARG_NEXT_ACTVIITY =
            "com.mjeorrett.android.personal_database_android.next_activity";

    PDAEntity mEntity;
    String mDatabaseName;
    String mTableName;
    String mColumnName;
    UUID mRowId;

    public static PDATitleEditFragment newInstance(

            PDAEntityType entityType,
            @Nullable String database,
            @Nullable String table,
            @Nullable String column,
            @Nullable UUID row_id ) {

        Bundle args = new Bundle();
        args.putSerializable( ARG_ENTITY_TYPE, entityType );
        args.putString(ARG_DATABASE_NAME, database );
        args.putString(ARG_TABLE_NAME, table );
        args.putString(ARG_COLUMN_NAME, column );
        args.putSerializable( ARG_ROW_ID, row_id );

        PDATitleEditFragment fragment = new PDATitleEditFragment();
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu( true );

        PDAEntityType entityType = (PDAEntityType) getArguments().getSerializable( ARG_ENTITY_TYPE );
        mDatabaseName = getArguments().getString( ARG_DATABASE_NAME );
        mTableName = getArguments().getString( ARG_TABLE_NAME );
        mColumnName = getArguments().getString( ARG_COLUMN_NAME );
        mRowId = (UUID) getArguments().getSerializable( ARG_ROW_ID );

        mEntity = PDAEntityServer.getPDAEntity(
                getActivity(),
                entityType,
                mDatabaseName,
                mTableName,
                mColumnName,
                mRowId );
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_title_display, container, false );
        return view;
    }
}
