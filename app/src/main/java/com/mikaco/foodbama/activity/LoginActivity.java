package com.mikaco.foodbama.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mikaco.foodbama.R;
import com.mikaco.foodbama.adapter.RetroAdapter;
import com.mikaco.foodbama.r8.App;
import com.mikaco.foodbama.r8.PreferencesHelper;
import com.mikaco.foodbama.r8.RetroInterface;
import com.vicmikhailau.maskededittext.MaskedFormatter;


public class LoginActivity extends AppCompatActivity {
    RetroAdapter retroAdapter = new RetroAdapter();
    RetroInterface retroInterface = this.retroAdapter.getInterface();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        final EditText txtUsername = findViewById(R.id.txtUsername);
        String savedUsername =PreferencesHelper.getInstance().getUsername();
        txtUsername.setText(String.valueOf(savedUsername));

        final EditText txtPassword = findViewById(R.id.txtPassword);
        txtPassword.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && event.getKeyCode() == 66) || actionId == 6) {
login();
            }
            return false;
        });

        final Button btnSignIn = findViewById(R.id.btnSignin);
        btnSignIn.setOnClickListener(view -> login());

        final Button btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),RegisterActivity.class)));
    }
//    public void getReadSMSPermission() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            int hasSMSPermission = checkSelfPermission(Manifest.permission.RECEIVE_SMS);
//            List<String> permissions = new ArrayList();
//            if (hasSMSPermission != 0) {
//                permissions.add(Manifest.permission.RECEIVE_SMS);
//            }
//            if (permissions.isEmpty()) {
//                setupCodeReceiver();
//                return;
//            } else {
//                explainPermission();
//                return;
//            }
//        }
//        setupCodeReceiver();
//    }
//
//    public void explainPermission() {
//        List<String> permissions = new ArrayList();
//        permissions.add(Manifest.permission.RECEIVE_SMS);
//        if (Build.VERSION.SDK_INT >= 23) {
//            LoginActivity.this.requestPermissions( permissions.toArray(new String[permissions.size()]), 1234);
//        }
//    }
//    private void setupCodeReceiver() {
//        try {
//            this.receiver = new BroadcastReceiver() {
//                public void onReceive(Context context, Intent intent) {
//                    if (intent.getExtras() != null) {
//                        final String code = intent.getStringExtra(SmsReceiver.INTENT_CODE);
//                        LoginActivity.this.editActivationCode.setText(code);
//                        new Handler().postDelayed(new Runnable() {
//                            public void run() {
//                                LoginActivity.this.sendActivationCode = code;
//                                LoginActivity.this.activationCode();
//                            }
//                        }, 300);
//                    }
//                }
//            };
//            LocalBroadcastManager.getInstance(this).registerReceiver(this.receiver, new IntentFilter(SmsReceiver.CODE_BROADCAST));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
public void login() {
    final EditText txtUsername = findViewById(R.id.txtUsername);
    final EditText txtPassword = findViewById(R.id.txtPassword);

    String user = (new MaskedFormatter("#### ### ####")).formatString(String.valueOf(txtUsername.getText())).getUnMaskedString();
    String pass = String.valueOf(txtPassword.getText());
    boolean checkedUser = String.valueOf(user).matches("(09)([0-9]){9}");
    boolean checkedPass = String.valueOf(pass).length() >= 6;


    if (checkedPass && checkedUser) {

        App.login(this, retroInterface, user, pass);

    } else {
        App.showToast(getString(R.string.RetryAfterCheck));
    }
}
}
