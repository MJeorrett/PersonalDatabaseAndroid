package org.mjeorrett.android.personaldatabaseandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.mjeorrett.android.personaldatabaseandroid.core.PDAEntityListFragment;

/**
 * Created by user on 14/11/2016.
 */

public class HomeFragment extends Fragment {

    private Button mEditStructureButton;
    private Button mEditDataButton;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_home, container, false );

        mEditStructureButton = (Button) view.findViewById( R.id.fragment_home_edit_structure_button );
        mEditStructureButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View view ) {
                Intent intent = PDAInstanceEditActivity.newIntent( getActivity(), true );
                startActivity( intent );
            }
        });

        mEditDataButton = (Button) view.findViewById( R.id.fragment_home_edit_data_button );
        mEditDataButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = PDAInstanceEditActivity.newIntent( getActivity(), false );
                startActivity( intent );
            }
        });

        return view;
    }
}
