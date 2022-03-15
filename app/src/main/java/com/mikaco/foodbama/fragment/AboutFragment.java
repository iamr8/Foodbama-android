package com.mikaco.foodbama.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mikaco.foodbama.R;
import com.mikaco.foodbama.r8.App;


public class AboutFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        final View v =inflater.inflate(R.layout.fragment_about, container, false);
        TextView txtVersion = v.findViewById(R.id.txtVersion);
        txtVersion.setText(getString(R.string.Version) + " " + String.valueOf(App.versionBuild()));

        Button btnCall = v.findViewById(R.id.btnSupportPhone);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall("09165104120");
            }
        });

        final Button btnMail = v.findViewById(R.id.btnSupportEmail);
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               App.openEmailClient("info@foodbama.ir",getString(R.string.RequestSupport),"");
            }
        });

        final TextView btnFeedback = v.findViewById(R.id.btnFeedback);
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.openWebView("https://www.foodbama.ir/Home/Critics");
            }
        });

        final TextView btnTerm = v.findViewById(R.id.btnTermAndConditions);
        btnTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.openWebView( "https://www.foodbama.ir/Home/TermsAndCondition");
            }
        });


        return v;
    }


    public void makeCall(String s)
    {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + s));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

            requestForCallPermission();

        } else {
            startActivity(intent);

        }
    }
    public void requestForCallPermission()
    {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CALL_PHONE))
        {
        }
        else {

            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall("100");
                }
                break;
        }
    }

}
