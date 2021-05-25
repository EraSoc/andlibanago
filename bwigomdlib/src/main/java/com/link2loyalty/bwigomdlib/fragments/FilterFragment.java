package com.link2loyalty.bwigomdlib.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.link2loyalty.bwigomdlib.R;
import com.xw.repo.BubbleSeekBar;


public class FilterFragment extends DialogFragment {

    private BubbleSeekBar mSeekBarDays, mSeekBarKms;
    private TextView tvDays, tvKms;
    private static int days, kms;

    Activity activity;




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    /*public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }*/

    public static FilterFragment newInstance(int num){

        FilterFragment dialogFragment = new FilterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("days", days);
        dialogFragment.setArguments(bundle);

        return dialogFragment;

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_filter, null);


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView( view )
                // Add action buttons
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try{
                            ((OnNewsItemSelectedListener) activity).onNewsItemPicked(days, kms);
                        }catch (ClassCastException cce){

                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FilterFragment.this.getDialog().cancel();
                    }
                });

        tvDays = view.findViewById( R.id.tv_days );
        tvKms = view.findViewById( R.id.tv_kms );

        mSeekBarDays = view.findViewById( R.id.seek_bar_days );
        mSeekBarDays.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                tvDays.setText( String.valueOf(progress) + " dias" );
                days = progress;
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
        });

        mSeekBarKms = view.findViewById( R.id.seek_bar_kms );
        mSeekBarKms.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                tvKms.setText( String.valueOf(progress) + " Kms." );
                kms = progress;
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
        });

        return builder.create();
    }

    public interface OnNewsItemSelectedListener{
        public void onNewsItemPicked(int days, int kms);
    }



}
