package com.mikaco.foodbama.activity;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikaco.foodbama.r8.App;
import com.mikaco.foodbama.r8.PreferencesHelper;
import com.mikaco.foodbama.R;
import com.mikaco.foodbama.adapter.RetroAdapter;
import com.mikaco.foodbama.r8.RetroInterface;
import com.mikaco.foodbama.model.MainViewModel;
import com.wang.avi.AVLoadingIndicatorView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

public String tokenSpread = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_view);

        final TextView txtStatus = findViewById(R.id.txtStatus);
        final AVLoadingIndicatorView avLoading = findViewById(R.id.avi);
        final ImageView imgStatus = findViewById(R.id.imgStatus);

        imgStatus.setVisibility(View.INVISIBLE);
        avLoading.setVisibility(View.VISIBLE);

        if(App.isNetworkAvailable()) {
            final String token = PreferencesHelper.getInstance().getToken();
            final String version = String.valueOf(App.version());

            if (token.equals("")) {
                tokenSpread = "";
                // has no token and navigate to login activity

                txtStatus.setText(R.string.Hello);
                imgStatus.setVisibility(View.INVISIBLE);
                avLoading.setVisibility(View.INVISIBLE);

                new Handler().postDelayed(() -> {
                    App.vibrate(500);
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }, 3000);
            } else {
                tokenSpread = token;
                    RetroAdapter retroAdapter = new RetroAdapter();
                    RetroInterface retroInterface = retroAdapter.getInterface();
                    retroInterface.getMainConfig(tokenSpread, version).enqueue(new Callback<MainViewModel>() {
                        @Override
                        public void onResponse(Call<MainViewModel> call, Response<MainViewModel> response) {
                            if (response.isSuccessful()) {

                                int forceUpdate = response.body().forceUpdate;
                                String updateLink = response.body().updateLink;
//                                if (forceUpdate == 1) {
                                    // prevent open app
                                    // show update dialog w/ close button
//                                } else {
                                    // new update in not forced update
                                    // show update dialog with close button
                                    // token found and app is starting
                                    if (response.body().status == 1) {
                                        // connected
                                        PreferencesHelper.getInstance().setUserfullname(response.body().name);
                                        PreferencesHelper.getInstance().setUsername(response.body().phone);
                                        PreferencesHelper.getInstance().setMainConfig(response.body());
                                        App.openActivity(MainActivity.class);
                                        finish();
                                    } else {
                                        // show unexpected error
                                    }
//                                }
                            } else {
                                // error on retrieve data from server
                                App.vibrate(500);
                                App.openActivity(LoginActivity.class);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<MainViewModel> call, Throwable t) {
                            retry("");

                        }
                    });

            }

        }else{
          retry("");
        }


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//                finish();
//            }
//        }, 3000);

    }

    public void retry(String error){

        final TextView txtStatus = findViewById(R.id.txtStatus);
        final AVLoadingIndicatorView avLoading = findViewById(R.id.avi);
        final ImageView imgStatus = findViewById(R.id.imgStatus);

        imgStatus.setVisibility(View.VISIBLE);
          avLoading.setVisibility(View.INVISIBLE);
          String errMsg = "";
          if (error.equals(""))
          {
           errMsg = getString(R.string.Retry);
          }else{
              errMsg = error;
          }
            txtStatus.setText(errMsg);
            txtStatus.setOnClickListener(view -> {
                // startMainView();
            });
    }

public  void startLoginActivity(){
    App.vibrate(500);
    App.openActivity(LoginActivity.class);
    finish();
}
}
