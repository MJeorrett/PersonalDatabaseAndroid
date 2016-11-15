package org.mjeorrett.android.personaldatabaseandroid.core;

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
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntity;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityServer;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;

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
            "com.mjeorrett.android.personal_database_android.entity_identifier";
    private static final String ARG_TABLE_NAME =
            "com.mjeorrett.android.personal_database_android.entity_identifier";
    private static final String ARG_COLUMN_NAME =
            "com.mjeorrett.android.personal_database_android.entity_identifier";
    private static final String ARG_ROW_ID =
            "com.mjeorrett.android.personal_database_android.entity_identifier";

    private PDAEntityType mPDAEntityType;
    private String mDatabaseName;
    private String mTableName;
    private String mColumnName;
    private UUID mRowId;

    private PDAEntity mPDAEntity;

    private RecyclerView mRecyclerView;
    private PDAEntityAdapter mAdapter;


    public static PDAEntityListFragment newInstance(
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

        PDAEntityListFragment fragment = new PDAEntityListFragment();
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu( true );

        mPDAEntityType = (PDAEntityType) getArguments().getSerializable( ARG_ENTITY_TYPE );
        mDatabaseName = getArguments().getString( ARG_DATABASE_NAME );
        mTableName = getArguments().getString( ARG_TABLE_NAME );
        mColumnName = getArguments().getString( ARG_COLUMN_NAME );
        mRowId = (UUID) getArguments().getSerializable( ARG_ROW_ID );

        mPDAEntity = PDAEntityServer.getPDAEntity(
                getActivity(),
                mPDAEntityType,
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

        View view = inflater.inflate( R.layout.fragment_pdaentity_list, container, false );

        mRecyclerView = (RecyclerView) view.findViewById( R.id.pdaentity_recycler_view );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( this.getActivity() ) );

        mAdapter = new PDAEntityAdapter( mPDAEntity.getChildEntities() );
        mRecyclerView.setAdapter( mAdapter );

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate( R.menu.fragment_pdaentity_list, menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch ( item.getItemId() ) {

            case R.id.fragment_pdaentity_list_add_entity:
                this.createNewChildEntity();
                return true;

            default:
                return super.onOptionsItemSelected( item );

        }
    }

    private void createNewChildEntity() {

        EditTextDialogue.run(
                getActivity(),
                "new_database",
                "Database Name",
                new EditTextDialogue.OnClickListener() {

            @Override
            public void onOKClicked( String enteredText ) {

                mPDAEntity.createNewChildEntity( enteredText );
                mAdapter.setEntities( mPDAEntity.getChildEntities() );
            }
        });
    }

    private class PDAEntityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;

        public PDAEntityHolder( View itemView ) {

            super(itemView);
            mTitleTextView = (TextView) itemView;
        }

        @Override
        public void onClick(View view) {

        }
    }

    private class PDAEntityAdapter extends RecyclerView.Adapter<PDAEntityHolder> {

        private List<PDAEntity> mPDAEntities;

        public PDAEntityAdapter( List<PDAEntity> pdaEntities ) {

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
        public void onBindViewHolder(PDAEntityHolder holder, int position) {

            PDAEntity entity = mPDAEntities.get( position );
            holder.mTitleTextView.setText( entity.getTitle() );
        }

        @Override
        public int getItemCount() {

            return mPDAEntities.size();
        }

        public void setEntities( List<PDAEntity> newEntities ) {

            mPDAEntities.clear();
            mPDAEntities.addAll( newEntities );
            notifyDataSetChanged();
        }
    }

}
