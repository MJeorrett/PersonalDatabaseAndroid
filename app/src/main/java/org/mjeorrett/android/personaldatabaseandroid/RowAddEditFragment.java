package org.mjeorrett.android.personaldatabaseandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.mjeorrett.android.personaldatabaseandroid.db.PDADatabase;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityServer;
import org.mjeorrett.android.personaldatabaseandroid.db.PDAEntityType;
import org.mjeorrett.android.personaldatabaseandroid.db.PDARow;
import org.mjeorrett.android.personaldatabaseandroid.db.PDATable;

import java.util.List;
import java.util.UUID;

/**
 * Created by user on 16/11/2016.
 */

public class RowAddEditFragment extends Fragment {

    private static final String ARG_DATABASE_NAME =
            "com.mjeorrett.android.personal_database_android.database_name";
    private static final String ARG_TABLE_NAME =
            "com.mjeorrett.android.personal_database_android.table_name";
    private static final String ARG_ROW_ID =
            "com.mjeorrett.android.personal_database_android.row_id";

    private String mDatabaseName;
    private String mTableName;
    private UUID mRowId;
    private PDARow mRow;
    private RecyclerView mRecyclerView;
    private FieldAdapter mAdapter;

    public static RowAddEditFragment newInstance(
            String database,
            String table,
            @Nullable String row_id ) {

        Bundle args = new Bundle();
        args.putString(ARG_DATABASE_NAME, database );
        args.putString(ARG_TABLE_NAME, table );
        args.putString( ARG_ROW_ID, row_id );

        RowAddEditFragment fragment = new RowAddEditFragment();
        fragment.setArguments( args );

        return fragment;
    }

    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {

        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        mDatabaseName = args.getString( ARG_DATABASE_NAME );
        mTableName = args.getString( ARG_TABLE_NAME );
        String rowIdString = args.getString( ARG_ROW_ID );

        mRowId = null;
        if ( rowIdString != null ) mRowId = UUID.fromString( rowIdString );

        mRow = (PDARow) PDAEntityServer.getPDAEntity(
                getActivity(),
                PDAEntityType.ROW,
                mDatabaseName,
                mTableName,
                mRowId );

        mRowId = mRow.getId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_row_add_edit, container, false );
        mRecyclerView = (RecyclerView) view.findViewById( R.id.fragment_row_add_edit_recycler_view );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );

        updateUI();
        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        updateUI();
    }

    private void updateUI() {

        mRow = (PDARow) PDAEntityServer.getPDAEntity(
                getActivity(),
                PDAEntityType.ROW,
                mDatabaseName,
                mTableName,
                mRowId );

        if ( mAdapter == null ) {

            mAdapter = new FieldAdapter( mRow );
            mRecyclerView.setAdapter( mAdapter );

        } else {

            mAdapter.setRow( mRow );
            mAdapter.notifyDataSetChanged();
        }
    }

    private class FieldHolder extends RecyclerView.ViewHolder {

        private PDARow mRow;
        private TextView mFieldTitleTextView;
        private EditText mFieldEditText;

        public FieldHolder( View itemView ) {

            super(itemView);

            mFieldTitleTextView = (TextView) itemView.findViewById( R.id.list_item_row_field_field_name_text_view );
            mFieldEditText = (EditText) itemView.findViewById( R.id.list_item_row_field_field_name_edit_text );
            mFieldEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    intentionally blank
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    mRow.setField( mFieldTitleTextView.getText().toString(), mFieldEditText.getText().toString() );
                }

                @Override
                public void afterTextChanged(Editable editable) {
//                    intentionally blank
                }
            });
        }

        public void bindField( PDARow row, String name, String value ) {

            mRow = row;
            mFieldTitleTextView.setText( name );
            mFieldEditText.setText( value );
        }
    }

    private class FieldAdapter extends RecyclerView.Adapter<FieldHolder> {

        private PDARow mRow;
        private List<Pair<String, String>> mFields;

        public FieldAdapter( PDARow row ) {

            mRow = row;
            mFields = row.getFields();
        }

        public void setRow( PDARow row ) {

            mRow = row;
            mFields = row.getFields();
        }

        @Override
        public FieldHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from( getActivity() );
            View view = inflater.inflate( R.layout.list_item_row_field, parent, false );
            return new FieldHolder( view );
        }

        @Override
        public void onBindViewHolder(FieldHolder holder, int position) {

            Pair<String, String> field = mFields.get( position);
            holder.bindField( mRow, field.first, field.second );
        }

        @Override
        public int getItemCount() {

            return mFields.size();
        }
    }
}
