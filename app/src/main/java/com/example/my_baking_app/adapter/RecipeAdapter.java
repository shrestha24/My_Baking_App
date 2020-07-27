package com.example.my_baking_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_baking_app.R;
import com.example.my_baking_app.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipieViewHolder> {
    private static final String TAG=RecipeAdapter.class.getSimpleName();
    private Context context;
    final private ListItemClickListener mOnClickListener;
    private List<Recipe> mList;

    public RecipeAdapter(Context context, ListItemClickListener mOnClickListener) {
        this.context = context;
        this.mOnClickListener = mOnClickListener;
        mList=new ArrayList<>();
    }
    public void setRecipie(List<Recipe> mRecipe){
        mList.addAll(mRecipe);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.items_recycler, parent, false);
        return new RecipieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipieViewHolder holder, int position) {
        Recipe recipe=mList.get(position);
        holder.textView.setText(recipe.getName());

    }

    @Override
    public int getItemCount() {
        if (mList==null){

            return 0;

        }else {

            return mList.size();
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedIndex);
    }

    public class RecipieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;

        public RecipieViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition=getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }
    }

}

