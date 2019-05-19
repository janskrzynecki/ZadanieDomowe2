package com.js.zadaniedomowe2.tasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import com.js.zadaniedomowe2.R;

public class DeleteDialog extends DialogFragment {

    private OnDeleteDialogInteractionListener mListener;

    public DeleteDialog() {
    }
    public static DeleteDialog newInstance(){
        return new DeleteDialog();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach ( context );
        if (context instanceof OnDeleteDialogInteractionListener) {
            mListener = ( OnDeleteDialogInteractionListener ) context;
        } else {
            throw new RuntimeException ( context.toString ()
                    + " must implement OnDeleteDialogInteractionListener" );
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder ( getActivity () );

        builder.setMessage ( getString (R.string.delete_question ) );

        builder.setPositiveButton ( getString ( R.string.dialog_confirm ), new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogPositiveClick ( DeleteDialog.this);
            }
        } );
        builder.setNegativeButton ( getString ( R.string.dialog_cancel ), new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onDialogNegativeClick ( DeleteDialog.this);
            }
        } );
        return builder.create ();
    }
//The AlertDialog.Builder is used to create the dialog. The message of the dialog is set with
//setMessage method and the reaction on the click events of the dialog buttons is set by the
//setPositiveButton and setNegativeButton methods. Both of the methods take the string to
//be displayed as the first argument and a onClickListener object as the second argument.
    @Override
    public void onDetach() {
        super.onDetach ();
        mListener = null;
    }

    public interface OnDeleteDialogInteractionListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);

    }
}