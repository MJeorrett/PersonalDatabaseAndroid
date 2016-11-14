package org.mjeorrett.android.personaldatabaseandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        mEditDataButton = (Button) view.findViewById( R.id.fragment_home_edit_data_button );

        return view;
    }
}
