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

import java.util.List;

/**
 * Created by user on 14/11/2016.
 */

public class PDAEntityListFragment extends Fragment {

    private static final String ARG_ENTITY_TYPE =
            "com.mjeorrett.android.personal_database_android.entity_type";

    private static final String ARG_ENTITY_IDENTIFIER =
            "com.mjeorrett.android.personal_database_android.entity_identifier";

    private PDAEntityType mPDAEntityType;
    private String mPDAEntityIdentifier;
    private RecyclerView mRecyclerView;
    private PDAEntityAdapter mAdapter;
    private PDAEntity mPDAEntity;
    private List<PDAEntity> mChildPDAEntities;

    public static PDAEntityListFragment newInstance( PDAEntityType entityType, @Nullable String entityIdentifier ) {

        Bundle args = new Bundle();
        args.putSerializable( ARG_ENTITY_TYPE, entityType );
        args.putString( ARG_ENTITY_IDENTIFIER, entityIdentifier );

        PDAEntityListFragment fragment = new PDAEntityListFragment();
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu( true );

        mPDAEntityType = (PDAEntityType) getArguments().getSerializable( ARG_ENTITY_TYPE );
        mPDAEntityIdentifier = getArguments().getString( ARG_ENTITY_IDENTIFIER );
        mPDAEntity = PDAEntityServer.getPDAEntity( getActivity(), mPDAEntityType, mPDAEntityIdentifier);
        mChildPDAEntities = mPDAEntity.getChildEntites( getActivity() );
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

        this.updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate( R.menu.fragment_pdaentity_list, menu );
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    private void updateUI() {

        mPDAEntity = PDAEntityServer.getPDAEntity( getActivity(), mPDAEntityType, mPDAEntityIdentifier );
        mChildPDAEntities = mPDAEntity.getChildEntites( getActivity() );
        mAdapter = new PDAEntityAdapter( mChildPDAEntities );
        mRecyclerView.setAdapter( mAdapter );
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

            mPDAEntities = pdaEntities;
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
    }

}
