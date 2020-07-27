package com.example.my_baking_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.my_baking_app.fragments.InstructionFragment;
import com.example.my_baking_app.fragments.SelectedReciepeStepsFragment;
import com.example.my_baking_app.fragments.VideoPlayingFragment;
import com.example.my_baking_app.models.Recipe;

import static com.example.my_baking_app.constant.Constant.STEPS;

public class RecipeStepActivity extends AppCompatActivity {
    private static final String TAG=RecipeStepActivity.class.getSimpleName();
    VideoPlayingFragment videoPlayingFragment;
    InstructionFragment instructionFragment;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        Intent intent=getIntent();
        Recipe.StepsBean object=intent.getParcelableExtra(STEPS);

        Log.e(TAG,"DETAIL OF OBJECT :"+object.getVideoURL()+"\n"+object.getThumbnailURL()+"\n"+object.getDescription());

        videoPlayingFragment = new VideoPlayingFragment();
        videoPlayingFragment.setObject(object);
        instructionFragment = new InstructionFragment();
        instructionFragment.setObject(object);
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.playerFragment,videoPlayingFragment).commit();
        fragmentManager.beginTransaction().add(R.id.descriptionFragment, instructionFragment).commit();







    }

}