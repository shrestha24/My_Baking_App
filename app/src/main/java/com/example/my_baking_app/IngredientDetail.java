package com.example.my_baking_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.my_baking_app.adapter.AdapterIngredients;
import com.example.my_baking_app.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.my_baking_app.constant.Constant.INGREDIENT;

public class IngredientDetail extends AppCompatActivity {
    ArrayList<Recipe.IngredientsBean> mList;
    @BindView(R.id.ingredientRecycler)
    RecyclerView ingredientRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mList = intent.getParcelableArrayListExtra(INGREDIENT);
        ingredientRecycler.setLayoutManager(new LinearLayoutManager(this));
        ingredientRecycler.setHasFixedSize(true);
        AdapterIngredients adapterIngredients=new AdapterIngredients(this,mList);
       ingredientRecycler.setAdapter(adapterIngredients);
    }
}