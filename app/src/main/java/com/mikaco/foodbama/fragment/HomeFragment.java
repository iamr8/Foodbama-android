package com.mikaco.foodbama.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikaco.foodbama.R;
import com.mikaco.foodbama.adapter.MainSlidersAdapter;
import com.mikaco.foodbama.model.MainViewSliderGroupModel;
import com.mikaco.foodbama.model.MainViewTopSliderModel;
import com.mikaco.foodbama.r8.App;
import com.mikaco.foodbama.r8.PreferencesHelper;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<MainViewSliderGroupModel> sliders = PreferencesHelper.getInstance().getMainConfig().sliders;
        if (sliders != null) {
            MainSlidersAdapter mainSlidersAdapter = new MainSlidersAdapter(sliders);

            RecyclerView mainList = v.findViewById(R.id.list);
            mainList.setLayoutManager(new LinearLayoutManager(getActivity()));
            mainList.setAdapter(mainSlidersAdapter);

            ImageView topSlider = v.findViewById(R.id.topSlider);
            ImageView event = v.findViewById(R.id.event);

            MainViewTopSliderModel topSliderImg = PreferencesHelper.getInstance().getMainConfig().topSliders.get(0);
            if (topSliderImg != null){
                topSlider.setVisibility(View.VISIBLE);

                if (topSliderImg.id != 0) {
                   topSlider.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Toast.makeText(App.getAppContext(),String.valueOf(App.getAppContext().getResources().getString(R.string.PleaseWait)),Toast.LENGTH_LONG).show();
                       }
                   });
                }

                try {
                    Glide.with(App.getAppContext())
                            .load(topSliderImg.img)
                            .apply(new RequestOptions()
                                    .placeholder(R.color.white)
                            ).into(topSlider);

                }catch (Exception ex){
                    ex.printStackTrace();
                    topSlider.setVisibility(View.INVISIBLE);

                }

            }else{
                topSlider.setVisibility(View.INVISIBLE);
            }

            try {
                MainViewTopSliderModel eventImg = PreferencesHelper.getInstance().getMainConfig().topSliders.get(1);
                if (eventImg != null) {
                    event.setVisibility(View.VISIBLE);
                    try {
                        Glide.with(App.getAppContext())
                                .load(eventImg.img)
                                .apply(new RequestOptions()
                                        .placeholder(R.color.white)
                                ).into(event);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    event.setVisibility(View.INVISIBLE);
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
                event.setVisibility(View.INVISIBLE);

            }


    }
        return v;
    }

}

