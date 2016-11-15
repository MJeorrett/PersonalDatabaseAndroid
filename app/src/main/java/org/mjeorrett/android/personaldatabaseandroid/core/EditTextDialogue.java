package org.mjeorrett.android.personaldatabaseandroid.core;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by user on 14/11/2016.
 */

public class EditTextDialogue extends DialogFragment {

    private static final String TAG = "EditTextDialogue";

    public interface OnClickListener {

        void onOKClicked( String enteredText );
    }

    public static void run(
            Context context,
            String title,
            String hint,
            @Nullable String initialText,
            final OnClickListener listener ) {

        AlertDialog.Builder builder = new AlertDialog.Builder( context );
        builder.setTitle( title );

        final EditText editText = new EditText( context );
        editText.setHint( hint );

        if ( initialText != null ) {

            editText.setText(initialText);
        }

        editText.setInputType( InputType.TYPE_CLASS_TEXT );
        builder.setView( editText );

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                listener.onOKClicked( editText.getText().toString() );
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}