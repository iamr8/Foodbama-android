package com.mikaco.foodbama.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mikaco.foodbama.activity.AddressesActivity;
import com.mikaco.foodbama.activity.ChangePasswordActivity;
import com.mikaco.foodbama.activity.OrdersHistoryActivity;
import com.mikaco.foodbama.activity.PendingOrdersActivity;
import com.mikaco.foodbama.R;
import com.mikaco.foodbama.r8.App;
import com.mikaco.foodbama.r8.PreferencesHelper;


public class ProfileFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView txtName = v.findViewById(R.id.lblName);
        TextView txtPhone = v.findViewById(R.id.lblPhone);
        txtName.setText(PreferencesHelper.getInstance().getUserfullname());
        txtPhone.setText(PreferencesHelper.getInstance().getUsername());

        final Button btnPending = v.findViewById(R.id.btnPendingOrders);
        final String PendingTitle =getString(R.string.PendingOrders);
        btnPending.setText(PendingTitle);
        btnPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.openActivity(PendingOrdersActivity.class,String.valueOf(PendingTitle));
            }
        });

        final Button btnHistory = v.findViewById(R.id.btnOrdersHistory);
        final String HistoryTitle =getString(R.string.OrdersHistory);
        btnHistory.setText(HistoryTitle);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.openActivity(OrdersHistoryActivity.class,String.valueOf(HistoryTitle));
            }
        });

        final Button btnChangePass = v.findViewById(R.id.btnChangePassword);
        final String ChangePassword =getString(R.string.ChangePassword);
        btnChangePass.setText(ChangePassword);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.openActivity(ChangePasswordActivity.class,String.valueOf(ChangePassword));
            }
        });

        final Button btnAddresses = v.findViewById(R.id.btnAddressesHistory);
        final String Addresses =getString(R.string.AddressesHistory);
        btnAddresses.setText(Addresses);
        btnAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.openActivity(AddressesActivity.class,String.valueOf(Addresses));
            }
        });

        final Button btnLogout = v.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                App.Logout();
            }
        });











        return v;
    }

}
