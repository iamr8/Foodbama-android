package com.mikaco.foodbama.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mikaco.foodbama.R;
import com.mikaco.foodbama.adapter.RetroAdapter;
import com.mikaco.foodbama.model.VerifyModel;
import com.mikaco.foodbama.r8.App;
import com.mikaco.foodbama.r8.PreferencesHelper;
import com.mikaco.foodbama.r8.RetroInterface;
import com.vicmikhailau.maskededittext.MaskedFormatter;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifyActivity extends AppCompatActivity {
    RetroAdapter retroAdapter = new RetroAdapter();
    RetroInterface retroInterface = this.retroAdapter.getInterface();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        final EditText txtVerify = findViewById(R.id.txtVerifyCode);


        txtVerify.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && event.getKeyCode() == 66) || actionId == 6) {
                checkVerify();
            }
            return false;
        });

        final Button btnResend = findViewById(R.id.btnResend);
        btnResend.setEnabled(false);
        ;
        btnResend.setOnClickListener(view -> App.login(VerifyActivity.this, retroInterface));

        final TextView txtCountDown = findViewById(R.id.txtCountdown);
        new CountDownTimer(App.calculateTime(1), 1000) {
            public void onTick(long millisUntilFinished) {
                Object count = String.format("%02d:%02d", new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))});
                txtCountDown.setText(getString(R.string.EstimatedTimeToVerify) + " " + count);
            }

            public void onFinish() {
                btnResend.setEnabled(true);
                ;
                txtCountDown.setVisibility(View.INVISIBLE);
            }
        }.start();

        final Button btnVerify = findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(view -> {
            if (App.isNetworkAvailable()) {
                checkVerify();

            }
        });
    }
            public void checkVerify() {
                final EditText txtVerify = findViewById(R.id.txtVerifyCode);
                String verify = (new MaskedFormatter("# # # # # #")).formatString(String.valueOf(txtVerify.getText())).getUnMaskedString();
                boolean checkedCode = String.valueOf(verify).matches("[0-9]{6}");

                if (checkedCode) {
                    final Dialog progress =  App.progressDialog(this);
                    retroInterface.getVerify(PreferencesHelper.getInstance().getUsername(), verify, 0, Build.VERSION.RELEASE, App.deviceId(), App.version()).enqueue(new Callback<VerifyModel>() {
                        @Override
                        public void onResponse(Call<VerifyModel> call, Response<VerifyModel> response) {
                            progress.cancel();
                            progress.hide();
                            if (response.isSuccessful()) {


                                if (response.body().status == 1) {
                                    PreferencesHelper.getInstance().setToken(response.body().token);

                                    App.checkMainView(retroInterface);
                                    App.openActivity(MainActivity.class);
                                    finish();
                                }
                                App.showToast(response.body().message);

                            }
                        }

                        @Override
                        public void onFailure(Call<VerifyModel> call, Throwable t) {
                            App.showToastServerError();
                            progress.cancel();
                            progress.hide();
                        }

                    });
                }else{
                    App.showToast(getString(R.string.RetryAfterCheck));
                }
            }
}
