package com.example.my_baking_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_baking_app.IngredientDetail;
import com.example.my_baking_app.R;
import com.example.my_baking_app.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class AdapterIngredients extends RecyclerView.Adapter<AdapterIngredients.AdapterIngredientsViewHolder>{
    private static final String TAG=AdapterIngredients.class.getSimpleName();
    private Context context;

    private List<Recipe.IngredientsBean> mList;

    public AdapterIngredients(Context context, List<Recipe.IngredientsBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public AdapterIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.ingredient_item,parent,false);
        return new AdapterIngredientsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterIngredientsViewHolder holder, int position) {
        Recipe.IngredientsBean object=mList.get(position);
        String quantity=Float.toString(object.getQuantity())+object.getMeasure();
        holder.qty.setText(quantity);
        holder.description.setText(object.getIngredient());


    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class AdapterIngredientsViewHolder extends RecyclerView.ViewHolder{
        TextView qty,description;
        public AdapterIngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            qty=itemView.findViewById(R.id.tv_ingredient_dose);
            description=itemView.findViewById(R.id.tv_ingredient_name);

        }
    }
}
