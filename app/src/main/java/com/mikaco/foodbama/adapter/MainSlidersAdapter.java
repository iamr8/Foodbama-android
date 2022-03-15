package com.mikaco.foodbama.adapter;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikaco.foodbama.R;
import com.mikaco.foodbama.model.MainViewSliderGroupItemModel;
import com.mikaco.foodbama.model.MainViewSliderGroupModel;
import com.mikaco.foodbama.r8.App;

import java.util.ArrayList;

public class MainSlidersAdapter extends Adapter<MainSlidersAdapter.MyViewHolder> {
    private ArrayList<MainViewSliderGroupModel> list;

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView moreItem;
        public RecyclerView icons;
        public CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);

            // inside of view
            this.title = itemView.findViewById(R.id.txtSliderTitle);
            this.moreItem = itemView.findViewById(R.id.btnMoreItem);
            this.icons = itemView.findViewById(R.id.stores);
            this.card = itemView.findViewById(R.id.subjectCard);

            moreItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(App.getAppContext(),String.valueOf(App.getAppContext().getResources().getString(R.string.PleaseWait)),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public MainSlidersAdapter(ArrayList<MainViewSliderGroupModel> list) {
        this.list = list;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_slider, parent, false));
    }
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MainViewSliderGroupModel model = this.list.get(position);
        holder.title.setText(model.subject);
        holder.moreItem.setText(R.string.ShowList);

        if (model.subject.contains("تخفیف")){
            holder.card.setCardBackgroundColor(App.getAppContext().getResources().getColor(R.color.FoodBaMaPrimary));
            holder.title.setTextColor(App.getAppContext().getResources().getColor(R.color.DiscountTitle));
            holder.moreItem.setTextColor(App.getAppContext().getResources().getColor(R.color.FoodBaMaGray));
        }

        ArrayList<MainViewSliderGroupItemModel> finalItems = countItems(findNiceItems(model.items));


        MainSliderItemAdapter itemAdapter = new MainSliderItemAdapter(finalItems);
        GridLayoutManager mLayoutManager = new GridLayoutManager(App.getAppContext(),SpanCount(finalItems.size()));


        holder.icons.setLayoutManager(mLayoutManager);
        holder.icons.setAdapter(itemAdapter);
    }

    public int SpanCount(int itemsCount){

        // 8 items : 4x2
        // 6 items : 3x2
        // 4 items : 2x2
        // 2 items : 1x2

        if (itemsCount >= 8){
            return 4;
        }else if (itemsCount>= 6){
            return 3;
        }else {
            return 2;
        }
    }

    public ArrayList<MainViewSliderGroupItemModel> findNiceItems(ArrayList<MainViewSliderGroupItemModel> knownItems){
        ArrayList<MainViewSliderGroupItemModel> validItems = new ArrayList<>();
        for (MainViewSliderGroupItemModel store: knownItems) {
            if (!store.img.equals("")){
                validItems.add(store);
            }
        }
        return validItems;
    }
    public ArrayList<MainViewSliderGroupItemModel> countItems(ArrayList<MainViewSliderGroupItemModel> knownitems){
        ArrayList<MainViewSliderGroupItemModel> finalItems = new ArrayList<>();

        // 8 items : 4x2
        // 6 items : 3x2
        // 4 items : 2x2
        // 2 items : 1x2

        int limit;
        int knownSize = knownitems.size();

        if (knownSize >= 8){
            limit = 8;
        }else if (knownSize >= 6){
            limit = 6;
        }else if (knownSize >= 4){
            limit = 4;
        }else{
            limit= 2;
        }

            for (int x = 0; x < limit; x++){
                finalItems.add(knownitems.get(x));
            }
        return finalItems;
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

}
