package com.example.my_baking_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.my_baking_app.adapter.RecipeAdapter;
import com.example.my_baking_app.adapter.RecipeAdapter;
import com.example.my_baking_app.models.Recipe;
import com.example.my_baking_app.networking.ApiInterface;
import com.example.my_baking_app.networking.RetrofitRequests;
import com.example.my_baking_app.tester.SimpleIdlingresource;
import com.example.my_baking_app.utility.NetworkChecker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.my_baking_app.constant.Constant.ID;
import static com.example.my_baking_app.constant.Constant.INGREDIENT;
import static com.example.my_baking_app.constant.Constant.RECIPIENAME;
import static com.example.my_baking_app.constant.Constant.STEPS;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener {

    @BindView(R.id.recyclerMain)
    RecyclerView recyclerMain;
    private RecipeAdapter recipieAdapter;
    private List<Recipe> listofRecipe;
    private ApiInterface apiInterface;
    private String TAG=MainActivity.class.getSimpleName();

    @Nullable
    private SimpleIdlingresource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        IdlingRes.setIdleResourceTo(false);
        initialization();
        getRecipie();
        populateRecycler();
    }

    private void populateRecycler() {

    }

    private void getRecipie() {
        if (NetworkChecker.connected(this)!=false){
            apiInterface.getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    listofRecipe=response.body();
                    recipieAdapter.setRecipie(response.body());
                    IdlingRes.setIdleResourceTo(true);
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.e(TAG,t.getMessage());
                    IdlingRes.setIdleResourceTo(false);
                }
            });

        }


    }

    private void initialization() {
        /*listofRecipe=new ArrayList<>();*/
        apiInterface= RetrofitRequests.getRetrofitInstance().create(ApiInterface.class);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,calculateNoOfColumns(this));
        recyclerMain.setLayoutManager(gridLayoutManager);
        recyclerMain.setHasFixedSize(true);
        recipieAdapter=new RecipeAdapter(this,this::onListItemClick);
        recyclerMain.setAdapter(recipieAdapter);
    }
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    @Override
    public void onListItemClick(int clickedIndex) {
        Intent intent=new Intent(MainActivity.this,DetailActivity.class);
        List<Recipe.IngredientsBean> list1=listofRecipe.get(clickedIndex).getIngredients();
        List<Recipe.StepsBean> list2=listofRecipe.get(clickedIndex).getSteps();
        intent.putParcelableArrayListExtra(INGREDIENT, (ArrayList<? extends Parcelable>) list1);
        intent.putParcelableArrayListExtra(STEPS, (ArrayList<? extends Parcelable>) list2);
        intent.putExtra(ID,listofRecipe.get(clickedIndex).getId());
        intent.putExtra(RECIPIENAME,listofRecipe.get(clickedIndex).getName());

        startActivity(intent);




    }

}