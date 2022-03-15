package com.mikaco.foodbama.r8;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mikaco.foodbama.activity.LoginActivity;
import com.mikaco.foodbama.activity.MainActivity;
import com.mikaco.foodbama.activity.SplashScreenActivity;
import com.mikaco.foodbama.model.MainViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreferencesHelper {

    private static PreferencesHelper INSTANCE = null;
    public static final String SETTING = "setting";
    public static final String MAINCONFIG = "main_config";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "user_password";
    public static final String TOKEN = "user_token";
    public static final String USERFULLNAME = "user_fullname";
    private static Editor mEditor;
    private SharedPreferences mSettings;

    public static synchronized PreferencesHelper getInstance() {

        PreferencesHelper preferencesHelper;
        synchronized (PreferencesHelper.class) {
            if (INSTANCE == null) {
                INSTANCE = new PreferencesHelper();
            }
            preferencesHelper = INSTANCE;
        }
        return preferencesHelper;
    }

    public synchronized void init() {
        if (this.mSettings == null) {
            this.mSettings = App.getAppContext().getSharedPreferences(SETTING, 0);
            mEditor = this.mSettings.edit();
        }
    }



    public void setMainConfig(MainViewModel configuration) {
        try {

            mEditor.putString(MAINCONFIG, new Gson().toJson((Object) configuration));
            mEditor.commit();
        } catch (Exception e) {
        }
    }

    public MainViewModel getMainConfig() {
        try {
            String rawString = this.mSettings.getString(MAINCONFIG, "");
            if (rawString.equals("")) {
                return null;
            }
            return (MainViewModel) new Gson().fromJson(rawString, MainViewModel.class);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public synchronized void setToken(String token) {
        mEditor.putString(TOKEN, token);
        mEditor.commit();
    }

    public synchronized String getToken() {
        init();
        return this.mSettings.getString(TOKEN, "");
    }

    public synchronized void setUsername(String userName) {
        mEditor.putString(USERNAME, userName);
        mEditor.commit();
    }

    public synchronized String getUsername() {
        init();
        return this.mSettings.getString(USERNAME, "");
    }

    public synchronized void setPassword(String pass) {
        mEditor.putString(PASSWORD, pass);
        mEditor.commit();
    }

    public synchronized String getPassword() {
        init();
        return this.mSettings.getString(PASSWORD, "");
    }

    public synchronized void setUserfullname(String pass) {
        mEditor.putString(USERFULLNAME, pass);
        mEditor.commit();
    }

    public synchronized String getUserfullname() {
        init();
        return this.mSettings.getString(USERFULLNAME, "");
    }
}
