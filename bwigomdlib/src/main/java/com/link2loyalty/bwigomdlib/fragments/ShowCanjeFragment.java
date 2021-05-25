package com.link2loyalty.bwigomdlib.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.canje.LovBoletos;
import com.link2loyalty.bwigomdlib.models2.user.ValorMultitenancy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowCanjeFragment extends DialogFragment {

    private Toolbar tbCanje;

    private User mUser;
    private Context mContext;
    private ValorMultitenancy mMultitenancy;

    public static String TAG = "FullScreenDialog";

    private ImageView ivCode;
    private TextView tvVigencia;
    private LovBoletos boleto;


    public ShowCanjeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_canje, container, false);
        this.mContext = getActivity();
        this.mUser = new User(mContext);
        this.mMultitenancy = this.mUser.getMultitenancy();
        this.boleto = (LovBoletos) getArguments().getSerializable("boleto");
        this.tbCanje = view.findViewById(R.id.tb_canje);
        this.ivCode = view.findViewById(R.id.iv_code);
        this.tvVigencia = view.findViewById(R.id.tv_vigencia);
        tbCanje.setNavigationIcon(R.drawable.ic_close_white_24dp);
        tbCanje.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tbCanje.setTitle("Canje");



        ((AppCompatActivity) getActivity()).setSupportActionBar(tbCanje);

        setHasOptionsMenu(true);

        //Convertir data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedCurrentDate = null;
        try {
            convertedCurrentDate = sdf.parse(boleto.getVigencia());
            String date=sdf.format(convertedCurrentDate );
            this.tvVigencia.setText( date );

        } catch (ParseException e) {
            e.printStackTrace();
        }



        Glide.with(mContext)
                .load(boleto.getImgcod())
                //.placeholder(R.drawable.default_pleaceholder)
                //.dontTransform()
                .into(this.ivCode);


        //getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //Config multitenancy
        if( mMultitenancy.getColpri() != null ){
            tbCanje.setBackgroundColor(Color.parseColor( "#"+mMultitenancy.getColpri() ));
            //myToolbar.setTitle( mMultitenancy.getDes() );

        }
        return view;
    }

    //Procedimiento para mostrar el documento PDF generado
    public void mostrarPDF() {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+boleto.getPdf()));
        startActivity(browserIntent);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_show_canje, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_show_pdf) {
            // handle confirmation button click here
            mostrarPDF();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
