package com.mikaco.foodbama.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikaco.foodbama.R;
import com.mikaco.foodbama.model.MainViewSliderGroupItemModel;
import com.mikaco.foodbama.r8.App;

import java.util.ArrayList;

/**
 * Created by Arash on 1/29/2018.
 */

public class MainSliderItemAdapter extends RecyclerView.Adapter<MainSliderItemAdapter.MyViewHolder> {

    private ArrayList<MainViewSliderGroupItemModel> list;
    class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public RecyclerView stores;
        public MyViewHolder(View itemView) {
            super(itemView);

            // inside of view
            this.img = itemView.findViewById(R.id.imgStoreLogo);
            this.stores= itemView.findViewById(R.id.stores);

        }
    }

    public MainSliderItemAdapter(ArrayList<MainViewSliderGroupItemModel> list) {
        this.list = list;
    }

    public MainSliderItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_slider_card, parent, false));
    }
    public void onBindViewHolder(MainSliderItemAdapter.MyViewHolder holder, int position) {


        MainViewSliderGroupItemModel model = this.list.get(position);

        if (model.img.equals("")){
            holder.img.setImageResource(R.drawable.placeholder_res);
        }

        try{
            Glide.with(App.getAppContext())
                    .load(model.img)
                    .apply( new RequestOptions()
                            .placeholder(R.drawable.placeholder_res)
                    ).into(holder.img);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
