package com.mikaco.foodbama.fragment;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * Created by Arash on 1/29/2018.
 */

public abstract class BaseFragment extends Fragment {
    public FragmentNavigation mFragmentNavigation;

    public interface FragmentNavigation {
        void clearStack();

        void popFragment();

        void pushFragment(Fragment fragment);

        void setNotificationCount(String str);

        void setToolbarTitle(String str);

        void showDialogFragment(DialogFragment dialogFragment);

        void showSnackBarDisconnect(Activity activity, String str);

        void switchTab(int i);
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            this.mFragmentNavigation = (FragmentNavigation) context;
        }
    }
}