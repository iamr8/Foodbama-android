package com.mikaco.foodbama.r8;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.mikaco.foodbama.R;
import com.mikaco.foodbama.activity.LoginActivity;
import com.mikaco.foodbama.activity.MainActivity;
import com.mikaco.foodbama.activity.VerifyActivity;
import com.mikaco.foodbama.activity.WebViewActivity;
import com.mikaco.foodbama.model.LoginModel;
import com.mikaco.foodbama.model.MainViewModel;
import com.mikaco.foodbama.r8.Enums;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
    public static void openActivity(final Class<? extends Activity> ActivityToOpen, String title){
        startActivity(new Intent(getAppContext(),ActivityToOpen).putExtra("Title",title),getAppContext());
    }

    public static void openActivity(final Class<? extends Activity> ActivityToOpen){
        startActivity(new Intent(getAppContext(),ActivityToOpen),getAppContext());
    }
    public static void openWebView(String url){
        startActivity(new Intent(getAppContext(),WebViewActivity.class).putExtra("Url",url),getAppContext());
    }
    public static void openUrl(String url){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)),getAppContext());
    }



    public String downloadAndParseJSON(String url){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        MediaType json =MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = null;

        try{
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(json,"");
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            Log.e("response",response.body().string());

            return response.body().string();



        }catch(Exception ex) {
            Log.e("log_tag","Error in http connection "+ex.toString());
            return null;
        }
    }
    public static long  calculateTime(int minute){
        return (long) (minute * 60000);
    }
    public static final String BaseURL = "https://www.foodbama.ir/api/v1/";
    public static String versionBuild(){
        String version = "";
        try {
            PackageInfo pInfo =  getAppContext().getPackageManager().getPackageInfo(getAppContext().getPackageName(), 0);
            version = pInfo.versionName + "." + pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    public static String version(){
        String version = "";
        try {
            PackageInfo pInfo = getAppContext().getPackageManager().getPackageInfo(getAppContext().getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static void openEmailClient(String to, String subject, String text){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[] { to});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, text);
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, getAppContext().getString(R.string.ChooseEmailClient)),getAppContext());
    }
    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)App.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

    public static void login(Activity activity, RetroInterface retroInterface ){
        login(activity, retroInterface, PreferencesHelper.getInstance().getUsername(), PreferencesHelper.getInstance().getPassword());
    }

    public static Dialog progressDialog(Activity activity) {
        Dialog progressDialog = new Dialog(activity);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.r8_progress);
        final TextView progressText = progressDialog.findViewById(R.id.lblLoading);
        progressText.setText(String.valueOf(activity.getString(R.string.PleaseWait)));
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        return progressDialog;

    }
    public static Dialog progressDialog(Activity activity, String message) {
       Dialog progressDialog = new Dialog(activity);
       progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       progressDialog.setContentView(R.layout.r8_progress);
       final TextView progressText = progressDialog.findViewById(R.id.lblLoading);
       progressText.setText(String.valueOf(message));
       progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       progressDialog.show();
       return progressDialog;

    }

    public static void login(Activity activity, RetroInterface retroInterface, final String userName, final String passWord){
        if (App.isNetworkAvailable()) {
            final Dialog progressDialog = App.progressDialog(activity);

            retroInterface.getLogin(String.valueOf(userName), String.valueOf(passWord), String.valueOf(App.deviceId())).enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, retrofit2.Response<LoginModel> response) {
                    if (response.isSuccessful()) {
                        progressDialog.cancel();
                        progressDialog.hide();

                        if (response.body().status == 1) {
                            PreferencesHelper.getInstance().setPassword(passWord);
                            PreferencesHelper.getInstance().setUsername(userName);
                            App.openActivity(VerifyActivity.class);
                        }
                        App.showToast(response.body().message);

                    }else{
                        App.showToastServerError();
                        progressDialog.cancel();
                        progressDialog.hide();
                    }

                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    App.showToastServerError();
                    progressDialog.cancel();
                    progressDialog.hide();

                }
            });
        }
    }

    private static Enums.mainEnum mainResult = Enums.mainEnum.NetError;

    public static Enums.mainEnum checkMainView(RetroInterface retroInterface){

        final String token = PreferencesHelper.getInstance().getToken();
        final String version = String.valueOf(App.version());

        if (token == null || token.equals("")) {

            // has no token and navigate to login activity

            App.vibrate(500);
            App.openActivity(LoginActivity.class);
            mainResult = Enums.mainEnum.NeedLogin;

        }else
        {

        retroInterface.getMainConfig(String.valueOf(token), String.valueOf(version)).enqueue(new Callback<MainViewModel>() {
            @Override
            public void onResponse(Call<MainViewModel> call, retrofit2.Response<MainViewModel> response) {
                if (response.isSuccessful()){

                    int forceUpdate= response.body().forceUpdate;
                    String updateLink = response.body().updateLink;
                    if (forceUpdate == 1){
//
//                        // prevent open app
//                        // show update dialog w/ close button
//
                    }else{
                        // new update in not forced update
                        // show update dialog with close button

                            // token found and app is starting

//                            if (response.body().status == 1){
//                                // connected
//                                PreferencesHelper.getInstance().setUserfullname(response.body().name);
//                                PreferencesHelper.getInstance().setUsername(response.body().phone);
//                                PreferencesHelper.getInstance().setMainConfig(response.body());
//                                App.openActivity(MainActivity.class);
//                                mainResult = Enums.mainEnum.Connected;
//
//                            }else{
//
//                                // show unexpected error
//
//                            }

                    }
                }else
                {

                    // error on retrieve data from server

                    App.vibrate(500);
                    App.openActivity(LoginActivity.class);
                    mainResult = Enums.mainEnum.NetError;
                }
            }

            @Override
            public void onFailure(Call<MainViewModel> call, Throwable t) {

                App.vibrate(500);
                App.openActivity(LoginActivity.class);
                mainResult = Enums.mainEnum.NetError;

            }
        });
        }


        return mainResult;
    }
    public static void resetData(){
        PreferencesHelper.getInstance().setUserfullname("");
        PreferencesHelper.getInstance().setPassword("");
        PreferencesHelper.getInstance().setToken("");
        PreferencesHelper.getInstance().setMainConfig(null);

    }
    public static void Logout(){
        resetData();
        openActivity(LoginActivity.class);
    }
public static void vibrate(int miliseconds){
    ((Vibrator) getAppContext().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(miliseconds);
}
public static String deviceId(){
    return Settings.Secure.getString(getAppContext().getContentResolver(),"android_id");
}
    public static void showToast(String message){
        Toast.makeText(getAppContext(),message,Toast.LENGTH_LONG).show();
    }
    public static void showToastServerError(){
        Toast.makeText(getAppContext(),R.string.ServerError,Toast.LENGTH_LONG).show();
        vibrate(300);
    }
    private static void startActivity(Intent intent, Context appContext){
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.mContext.startActivity(intent);
    }
}