package com.link2loyalty.bwigomdlib.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.link2loyalty.bwigomdlib.R;
import com.link2loyalty.bwigomdlib.api.ServerCallback;
import com.link2loyalty.bwigomdlib.models2.User;
import com.link2loyalty.bwigomdlib.models2.usrFacebook.ResUpdFacebook;
import com.link2loyalty.bwigomdlib.models2.usrFacebook.ValorFacebook;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkFacebookFragment extends DialogFragment {


//    private Toolbar lfToolbar;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private User mUser;


    public LinkFacebookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        mUser = new User(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_link_facebook, container, false);




        /*lfToolbar = view.findViewById(R.id.lf_toolbar);

        lfToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        lfToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        lfToolbar.setTitle("");
        */

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) view.findViewById(R.id.login_button);

        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("loginFacebook", "Login hecho papu :D");
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                //Log.v("loginFacebook", response.toString());
                                // Application code
                                try {
                                    String id = object.getString("id");
                                    ValorFacebook vf = new ValorFacebook();
                                    vf.setIdfac(id);

                                    mUser.linkFacebook(mUser.getSes(), vf , new ServerCallback() {
                                        @Override
                                        public void onSuccess(String result) {
                                            Gson mGson = new Gson();
                                            ResUpdFacebook mRes = mGson.fromJson(result, ResUpdFacebook.class);
                                            Toast.makeText(getActivity(), mRes.getMen(), Toast.LENGTH_SHORT).show();
                                            mUser.setLoginFacebookCount(4);
                                            dismiss();
                                        }

                                        @Override
                                        public void onError(VolleyError err) {

                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,age_range");
                request.setParameters(parameters);
                request.executeAsync();
                //goHome();
            }

            @Override
            public void onCancel() {
                Log.d("loginFacebook", "Login CNCELADO papu :D");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("loginFacebook", "Login con error :(");
                // App code
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {


        callbackManager.onActivityResult(requestCode, resultCode, data);
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
}
