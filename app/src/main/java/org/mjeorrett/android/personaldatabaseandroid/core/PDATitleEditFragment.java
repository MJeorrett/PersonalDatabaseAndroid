package org.mjeorrett.android.personaldatabaseandroid.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    private PDAEntity mEntity;
    private String mDatabaseName;
    private String mTableName;
    private String mColumnName;

    private Button mButton;

    public static PDATitleEditFragment newInstance(

            PDAEntityType entityType,
            @Nullable String database,
            @Nullable String table,
            @Nullable String column ) {

        Bundle args = new Bundle();
        args.putSerializable( ARG_ENTITY_TYPE, entityType );
        args.putString(ARG_DATABASE_NAME, database );
        args.putString(ARG_TABLE_NAME, table );
        args.putString(ARG_COLUMN_NAME, column );

        PDATitleEditFragment fragment = new PDATitleEditFragment();
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu( true );

        Bundle args = getArguments();

        PDAEntityType entityType = (PDAEntityType) args.getSerializable(ARG_ENTITY_TYPE);
        mDatabaseName = args.getString(ARG_DATABASE_NAME);
        mTableName = args.getString(ARG_TABLE_NAME);
        mColumnName = args.getString(ARG_COLUMN_NAME);

        mEntity = PDAEntityServer.getPDAEntity(
                getActivity(),
                entityType,
                mDatabaseName,
                mTableName,
                null );
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_title_display, container, false );

        mButton = (Button) view.findViewById( R.id.fragment_title_display_button );
        mButton.setText( mEntity.getName() );

        return view;
    }

//    @Override
//    public void onSaveInstanceState( Bundle outState ) {
//
//        super.onSaveInstanceState(outState);
//
//        outState.putSerializable( ARG_ENTITY_TYPE, mEntity.getType() );
//        outState.putString( ARG_DATABASE_NAME, mDatabaseName );
//        outState.putString( ARG_TABLE_NAME, mTableName );
//        outState.putString( ARG_COLUMN_NAME, mColumnName );
//    }
//
//    @Override
//    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
//
//        super.onActivityCreated(savedInstanceState);
//
//        if ( savedInstanceState != null ) {
//
//            PDAEntityType entityType = (PDAEntityType) savedInstanceState.getSerializable( ARG_ENTITY_TYPE );
//            mDatabaseName = savedInstanceState.getString( ARG_DATABASE_NAME );
//            mTableName = savedInstanceState.getString( ARG_TABLE_NAME );
//            mColumnName = savedInstanceState.getString( ARG_COLUMN_NAME );
//
//            mEntity = PDAEntityServer.getPDAEntity(
//                    getActivity(),
//                    entityType,
//                    mDatabaseName,
//                    mTableName,
//                    mColumnName );
//        }
//    }
}
