package com.example.my_baking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.my_baking_app.constant.Constant;
import com.example.my_baking_app.fragments.InstructionFragment;
import com.example.my_baking_app.fragments.SelectedReciepeStepsFragment;
import com.example.my_baking_app.fragments.VideoPlayingFragment;
import com.example.my_baking_app.models.Recipe;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static com.example.my_baking_app.constant.Constant.APPLICATION_ID;
import static com.example.my_baking_app.constant.Constant.ID;
import static com.example.my_baking_app.constant.Constant.INGREDIENT;
import static com.example.my_baking_app.constant.Constant.STEPS;

public class DetailActivity extends AppCompatActivity implements SelectedReciepeStepsFragment.OnSelectRecipie {
    ArrayList<Recipe.IngredientsBean> listOfIngredients;
    ArrayList<Recipe.StepsBean> listofSteps;
    SelectedReciepeStepsFragment selectedReciepeStepsFragment;
    FragmentManager fragmentManager;
    Bundle bundle;
    int id;
    private SharedPreferences sharedPreferences;
    public static boolean mTwoPane;
    private LinearLayout layoutRecipeInfo;
    String name;
    String MTWOPANE = "mTwoPane";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        listOfIngredients = intent.getParcelableArrayListExtra(INGREDIENT);
        listofSteps = intent.getParcelableArrayListExtra(STEPS);
        id = intent.getIntExtra(ID, 0);
        name = intent.getStringExtra(name);
        layoutRecipeInfo = findViewById(R.id.layout_recipie_info);
        bundle = new Bundle();
        bundle.putParcelableArrayList(INGREDIENT, listOfIngredients);
        bundle.putParcelableArrayList(STEPS, listofSteps);



        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        if (findViewById(R.id.tabMode) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                SelectedReciepeStepsFragment selectedReciepeStepsFragment = new SelectedReciepeStepsFragment();

                selectedReciepeStepsFragment.setListOfIngredients(listOfIngredients);
                selectedReciepeStepsFragment.setListofStep(listofSteps);
                fragmentManager.beginTransaction().add(R.id.selectRecipieDetail, selectedReciepeStepsFragment).commit();
                VideoPlayingFragment videoPlayingFragment = new VideoPlayingFragment();

                videoPlayingFragment.setObject(listofSteps.get(0));
                fragmentManager.beginTransaction().add(R.id.playerFragment, videoPlayingFragment).commit();
                InstructionFragment instructionFragment = new InstructionFragment();

                instructionFragment.setObject(listofSteps.get(0));
                fragmentManager.beginTransaction().add(R.id.descriptionFragment, instructionFragment).commit();

            }

        } else {
            mTwoPane = false;
        }


        initialization();


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean(MTWOPANE,mTwoPane);
        outState.putParcelableArrayList(STEPS,listofSteps);
        outState.putParcelableArrayList(INGREDIENT,listOfIngredients);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listofSteps=savedInstanceState.getParcelableArrayList(STEPS);
        listOfIngredients=savedInstanceState.getParcelableArrayList(INGREDIENT);
        mTwoPane=savedInstanceState.getBoolean(MTWOPANE);
    }

    private void initialization() {
        if (mTwoPane == false) {


            selectedReciepeStepsFragment = new SelectedReciepeStepsFragment();
            selectedReciepeStepsFragment.setListofStep(listofSteps);
            selectedReciepeStepsFragment.setListOfIngredients(listOfIngredients);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.frame, selectedReciepeStepsFragment).commit();
        }

    }

    @Override
    public void onRecipieSelected(int position) {
        Toast.makeText(this, "CLICKED ON POSITION " + position, Toast.LENGTH_SHORT).show();
        if (mTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            SelectedReciepeStepsFragment selectRecipeStep = new SelectedReciepeStepsFragment();
            Toast.makeText(this, "CLCIKED" + position, Toast.LENGTH_SHORT).show();


            VideoPlayingFragment mediaPlayerFragment = new VideoPlayingFragment();
            //PASS DATA
            mediaPlayerFragment.setObject(listofSteps.get(position));
            fragmentManager.beginTransaction().replace(R.id.playerFragment, mediaPlayerFragment).commit();
            InstructionFragment instructionFragment = new InstructionFragment();
            // PASS DATA HERE
            instructionFragment.setObject(listofSteps.get(position));
            fragmentManager.beginTransaction().replace(R.id.descriptionFragment, instructionFragment).commit();

        } else {
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putExtra(STEPS, listofSteps.get(position));
            startActivity(intent);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);
        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE);
        menu.findItem(R.id.mi_action_widget).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.mi_action_widget) {
            boolean isRecipeInWidget = (sharedPreferences.getInt(Constant.PREFERENCES_ID, -1) == id);

            // If recipe already in widget, remove it
            if (isRecipeInWidget) {
                sharedPreferences.edit()
                        .remove(Constant.PREFERENCES_ID)
                        .remove(Constant.PREFERENCES_WIDGET_TITLE)
                        .remove(Constant.PREFERENCES_WIDGET_CONTENT)
                        .apply();


                Snackbar.make(layoutRecipeInfo, "Recipe Removed From Widget", Snackbar.LENGTH_SHORT).show();
            }
            // if recipe not in widget, then add it
            else {
                sharedPreferences
                        .edit()
                        .putInt(Constant.PREFERENCES_ID, id)
                        .putString(Constant.PREFERENCES_WIDGET_TITLE, name)
                        .putString(Constant.PREFERENCES_WIDGET_CONTENT, ingredientsString())
                        .apply();


                Snackbar.make(layoutRecipeInfo, "Recipe Added to Widget", Snackbar.LENGTH_SHORT).show();
            }

            // Put changes on the Widget
            ComponentName provider = new ComponentName(this, RecipieWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] ids = appWidgetManager.getAppWidgetIds(provider);
            RecipieWidget widgetsinfo = new RecipieWidget();
            widgetsinfo.onUpdate(this, appWidgetManager, ids);
        }
        return super.onOptionsItemSelected(item);
    }

    /*public void pass(int pos) {
        if (mTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            SelectedReciepeStepsFragment selectRecipeStep = new SelectedReciepeStepsFragment();
            Toast.makeText(this, "CLCIKED" + pos, Toast.LENGTH_SHORT).show();
            // PASS DATA
           *//* selectRecipeStep.setListOfIngredients(listOfIngredients);
            selectRecipeStep.setListofStep(listofSteps);
            fragmentManager.beginTransaction().add(R.id.selectRecipieDetail,selectRecipeStep).commit();*//*

            VideoPlayingFragment videoPlayingFragment = new VideoPlayingFragment();
            //PASS DATA
            videoPlayingFragment.setObject(listofSteps.get(pos));
            fragmentManager.beginTransaction().replace(R.id.playerFragment, videoPlayingFragment).commit();
            InstructionFragment instructionFragment = new InstructionFragment();
            // PASS DATA HERE
            instructionFragment.setObject(listofSteps.get(pos));
            fragmentManager.beginTransaction().replace(R.id.descriptionFragment, instructionFragment).commit();

        } else {
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putExtra(STEPS, listofSteps.get(pos));
            startActivity(intent);

        }

    }*/

    private String ingredientsString() {
        String result = "";
        for (Recipe.IngredientsBean ingredient : listOfIngredients) {
            String str = ingredient.getQuantity() + "  " + ingredient.getMeasure() + "  " + ingredient.getIngredient() + " \n";
            result += str;
        }
        return result;
    }
}
