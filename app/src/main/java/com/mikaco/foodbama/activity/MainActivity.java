package com.mikaco.foodbama.activity;

import android.content.Intent;
import android.graphics.Typeface;

import com.google.android.material.tabs.TabLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikaco.foodbama.adapter.PagerViewAdapter;
import com.mikaco.foodbama.R;
import com.mikaco.foodbama.r8.PreferencesHelper;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            toolbar.setTitle(getString(R.string.app_name));
//        }

        if (SDK_INT < LOLLIPOP){
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }

        String token = PreferencesHelper.getInstance().getToken();
        if (token.isEmpty() ||  token.equals("")){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }

        final TabLayout bottomNavigation = findViewById(R.id.toolbars);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        // Create item
        bottomNavigation.addTab(bottomNavigation.newTab().setText(R.string.Home));
        bottomNavigation.addTab(bottomNavigation.newTab().setText(R.string.Search));
        bottomNavigation.addTab(bottomNavigation.newTab().setText(R.string.Profile));
        bottomNavigation.addTab(bottomNavigation.newTab().setText(R.string.app_name));

        for (int i = 0; i < bottomNavigation.getTabCount(); i++){

            TabLayout.Tab tab = bottomNavigation.getTabAt(i);
            if (tab != null){

                TextView tabTextView = new TextView(this);
                tab.setCustomView(tabTextView);

                tabTextView.getLayoutParams().width = WRAP_CONTENT;
                tabTextView.getLayoutParams().height = WRAP_CONTENT;

                tabTextView.setText(tab.getText());

                tabTextView.setTextSize(14);

                if (i == 0){

                    tabTextView.setTypeface(null, Typeface.BOLD);
                    tabTextView.setTextColor(getResources().getColor(R.color.FoodBaMaPrimary));
                    tabTextView.setTypeface(ResourcesCompat.getFont(this,R.font.iransans_bold));
                }else{
                    tabTextView.setTypeface(null, Typeface.NORMAL);
                    tabTextView.setTextColor(getResources().getColor(R.color.FoodBaMaGrayDark));
                    tabTextView.setTypeface(ResourcesCompat.getFont(this,R.font.iransans));
                }
            }
        }

        bottomNavigation.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);

                ViewGroup vg = (ViewGroup) bottomNavigation.getChildAt(0);
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(tab.getPosition());

                int tabsChildrenCount = vgTab.getChildCount();
                for (int i = 0; i < tabsChildrenCount;i++){
                    View tabViewChild = vgTab.getChildAt(i);
                    if (tabViewChild instanceof TextView){

                        TextView myTab = (TextView) tabViewChild;

                        myTab.setTypeface(null, Typeface.BOLD);
                        myTab.setTextColor(getResources().getColor(R.color.FoodBaMaPrimary));
                        myTab.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.iransans_bold));

                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                ViewGroup vg = (ViewGroup) bottomNavigation.getChildAt(0);
                ViewGroup vgTab = (ViewGroup) vg.getChildAt(tab.getPosition());

                int tabsChildrenCount = vgTab.getChildCount();
                for (int i = 0; i < tabsChildrenCount;i++){
                    View tabViewChild = vgTab.getChildAt(i);
                    if (tabViewChild instanceof TextView){

                        TextView myTab = (TextView) tabViewChild;

                        myTab.setTypeface(null, Typeface.NORMAL);
                        myTab.setTextColor(getResources().getColor(R.color.FoodBaMaGrayDark));
                        myTab.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.iransans));

                    }
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        PagerViewAdapter adapter = new PagerViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

    }

}
