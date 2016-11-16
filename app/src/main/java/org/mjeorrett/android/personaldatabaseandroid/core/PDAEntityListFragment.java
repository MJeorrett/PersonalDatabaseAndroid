package org.mjeorrett.android.personaldatabaseandroid.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mjeorrett.android.personaldatabaseandroid.R;
import org.mjeorrett.android.personaldatabaseandroid.RowAddEditActivity;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAColumn;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntity;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityServer;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;
import org.mjeorrett.android.personaldatabaseandroid.db.PDARow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by user on 14/11/2016.
 */

public class PDAEntityListFragment extends Fragment {

    private static final String TAG = "PDAEntityListFragment";

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
    public static final String ARG_STRUCTURE_EDIT_MODE =
            "com.mjeorrett.android.personal_database_android.allow_adding_children";

    private RecyclerView mRecyclerView;
    private PDAEntityAdapter mAdapter;

    private PDAEntityType mEntityType;
    private PDAEntity mEntity;
    private String mDatabaseName;
    private String mTableName;
    private String mColumnName;
    private Class mNextActivity;
    private boolean mStructureEditMode;
    private boolean mOnTableActivity;

    public static PDAEntityListFragment newInstance(

            PDAEntityType entityType,
            @Nullable String database,
            @Nullable String table,
            @Nullable String column,
            @Nullable UUID row_id,
            @Nullable Class nextActivity,
            boolean allowAddingChildren ) {

        Bundle args = new Bundle();
        args.putSerializable( ARG_ENTITY_TYPE, entityType );
        args.putString(ARG_DATABASE_NAME, database );
        args.putString(ARG_TABLE_NAME, table );
        args.putString(ARG_COLUMN_NAME, column );
        args.putSerializable( ARG_ROW_ID, row_id );
        args.putSerializable( ARG_NEXT_ACTVIITY, nextActivity );
        args.putBoolean(ARG_STRUCTURE_EDIT_MODE, allowAddingChildren );

        PDAEntityListFragment fragment = new PDAEntityListFragment();
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        mEntityType = (PDAEntityType) args.getSerializable( ARG_ENTITY_TYPE );
        mDatabaseName = args.getString( ARG_DATABASE_NAME );
        mTableName = args.getString( ARG_TABLE_NAME );
        mColumnName = args.getString( ARG_COLUMN_NAME );
        mNextActivity = (Class) args.getSerializable( ARG_NEXT_ACTVIITY );
        mStructureEditMode = args.getBoolean(ARG_STRUCTURE_EDIT_MODE);

        mEntity = PDAEntityServer.getPDAEntity(
                getActivity(),
                mEntityType,
                mDatabaseName,
                mTableName,
                null
        );

        mOnTableActivity = mEntity.getType() == PDAEntityType.TABLE;

        if ( mStructureEditMode || mOnTableActivity ) {

            setHasOptionsMenu(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_pdaentity_list, container, false );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.pdaentity_recycler_view );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( this.getActivity() ) );

        updateView();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        if ( mStructureEditMode || mOnTableActivity ) {

            inflater.inflate(R.menu.fragment_pdaentity_list, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemId = item.getItemId();
        boolean returnValue;

        switch ( menuItemId ) {

            case R.id.fragment_pdaentity_list_add_entity:

                if ( mOnTableActivity && !mStructureEditMode ) {

                    this.goToRowAddEditActivity();

                } else {

                    this.createNewChildEntity();
                }

                returnValue = true;
                break;

            default:
                returnValue = super.onOptionsItemSelected( item );
                break;

        }

        return returnValue;
    }

    private void createNewChildEntity() {

        String entityType = mEntity.getChildTypeDescription();

        EditTextDialogue.run(
                getActivity(),
                "New " + entityType,
                "enter name",
                null,
                new EditTextDialogue.OnClickListener() {

            @Override
            public void onOKClicked( String enteredText ) {

                mEntity.createNewChildEntity( getActivity(), enteredText );
                updateView();
            }
        });
    }

    private void goToRowAddEditActivity() {

        Intent intent = new Intent( getActivity(), RowAddEditActivity.class );
        intent = mEntity.putExtrasInIntent( intent );
        startActivity( intent );
    }

    @Override
    public void onResume() {

        super.onResume();
        updateView();
    }

    private void updateView() {

        mEntity = PDAEntityServer.getPDAEntity(
                getActivity(),
                mEntityType,
                mDatabaseName,
                mTableName,
                null
        );

        List<PDAEntity> childEntities;

        if ( mEntityType == PDAEntityType.TABLE ) {

            if (mStructureEditMode) {
                List<PDAEntity> temp = mEntity.getChildEntities( PDAEntityType.COLUMN );
                childEntities = new ArrayList<>();
                PDAColumn aColumn;
                String aColumnName;

                for ( PDAEntity aEntity : temp ) {

                    aColumn = (PDAColumn) aEntity;
                    aColumnName = aColumn.getName();

                    if ( !aColumnName.equals( "_id" ) && !aColumnName.equals( "uuid" ) ) {

                        childEntities.add( aColumn );
                    }
                }
            } else {
                childEntities = mEntity.getChildEntities( PDAEntityType.ROW );
            }

        } else {

            childEntities = mEntity.getChildEntities( null );
        }


        if ( mAdapter == null ) {

            mAdapter = new PDAEntityAdapter(childEntities);
            mRecyclerView.setAdapter(mAdapter);

        } else {

            mAdapter.setPDAEntities( childEntities );
            mAdapter.notifyDataSetChanged();
        }

    }

    public static boolean getAllowAddingChildren( Bundle extras ) {

        boolean allowAddingChildren = extras.getBoolean( ARG_STRUCTURE_EDIT_MODE );
        return allowAddingChildren;
    }

    private class PDAEntityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private PDAEntity mEntity;

        public PDAEntityHolder( View itemView ) {

            super(itemView);
            mTitleTextView = (TextView) itemView;
            itemView.setOnClickListener( this );
        }

        @Override
        public void onClick(View view) {

            if ( mNextActivity != null ) {
                Intent intent = new Intent(getActivity(), mNextActivity);
                mEntity.putExtrasInIntent(intent);
                intent.putExtra(ARG_STRUCTURE_EDIT_MODE, mStructureEditMode);

                startActivity(intent);
            }
        }

        public void bindPDAEntity( PDAEntity entity ) {

            mTitleTextView.setText( entity.getName() );
            mEntity = entity;
        }

    }

    private class PDAEntityAdapter extends RecyclerView.Adapter<PDAEntityHolder> {

        private List<PDAEntity> mPDAEntities;

        public PDAEntityAdapter( List<PDAEntity> pdaEntities ) {

            mPDAEntities = new ArrayList<>( pdaEntities );
        }

        public void setPDAEntities( List<PDAEntity> pdaEntities ) {

            mPDAEntities = new ArrayList<>( pdaEntities );
        }

        @Override
        public PDAEntityHolder onCreateViewHolder( ViewGroup parent, int viewType ) {

            LayoutInflater layoutInflater = LayoutInflater.from( getActivity() );
            View view = layoutInflater.inflate( android.R.layout.simple_list_item_1, parent, false );

            PDAEntityHolder holder = new PDAEntityHolder( view );
            return holder;
        }

        @Override
        public void onBindViewHolder( PDAEntityHolder holder, int position ) {

            PDAEntity entity = mPDAEntities.get( position );
            holder.bindPDAEntity( entity );
        }

        @Override
        public int getItemCount() {

            return mPDAEntities.size();
        }

//        public void setEntities( List<PDAEntity> newEntities ) {
//
//            mPDAEntities.clear();
//            mPDAEntities.addAll( newEntities );
//            notifyDataSetChanged();
//        }
    }

//    @Override
//    public void onSaveInstanceState( Bundle outState ) {
//
//        super.onSaveInstanceState( outState );
//        outState.putSerializable( ARG_ENTITY_TYPE, mEntity.getType() );
//        outState.putString( ARG_DATABASE_NAME, mDatabaseName );
//        outState.putString( ARG_TABLE_NAME, mTableName );
//        outState.putString( ARG_COLUMN_NAME, mColumnName );
//        outState.putSerializable( ARG_NEXT_ACTVIITY, mNextActivity );
//
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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
