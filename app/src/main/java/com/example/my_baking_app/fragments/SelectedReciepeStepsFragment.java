package com.example.my_baking_app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_baking_app.IngredientDetail;
import com.example.my_baking_app.R;
import com.example.my_baking_app.RecipeStepActivity;
import com.example.my_baking_app.adapter.StepsRecipeAdapter;
import com.example.my_baking_app.models.Recipe;

import java.util.ArrayList;

import static com.example.my_baking_app.DetailActivity.mTwoPane;
import static com.example.my_baking_app.constant.Constant.INGREDIENT;
import static com.example.my_baking_app.constant.Constant.STEPS;


public class SelectedReciepeStepsFragment extends Fragment implements StepsRecipeAdapter.ListItemClickListener {

    ArrayList<Recipe.IngredientsBean> listOfIngredients;
    ArrayList<Recipe.StepsBean> listofStep;

    TextView ingredient;
    String LISTOFINGREDIENT="listofingredient";
    String LISTOFSTEP="listofstep";
    CardView ingredientCard;

    RecyclerView recyclerView;
    Context context;
    OnSelectRecipie mCallback;

    public ArrayList<Recipe.IngredientsBean> getListOfIngredients() {
        return listOfIngredients;
    }

    public void setListOfIngredients(ArrayList<Recipe.IngredientsBean> listOfIngredients) {
        this.listOfIngredients = listOfIngredients;
    }

    public ArrayList<Recipe.StepsBean> getListofStep() {
        return listofStep;
    }

    public void setListofStep(ArrayList<Recipe.StepsBean> listofStep) {
        this.listofStep = listofStep;
    }

    public SelectedReciepeStepsFragment() {
        this.listofStep = new ArrayList<>();
        this.listOfIngredients = new ArrayList<>();
    }

    public interface OnSelectRecipie {
        void onRecipieSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            listOfIngredients=savedInstanceState.getParcelableArrayList(LISTOFINGREDIENT);
            listofStep=savedInstanceState.getParcelableArrayList(LISTOFSTEP);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_selected_reciepe_steps, container, false);
        context=getContext();
        ingredient=rootView.findViewById(R.id.ingredient);
        ingredientCard=rootView.findViewById(R.id.ingredientCard);
        recyclerView=rootView.findViewById(R.id.selectRecipie);

        ingredient.setText("INGREDIENT");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        Toast.makeText(context, "NO OF OBJECT"+listofStep.size(), Toast.LENGTH_SHORT).show();
        StepsRecipeAdapter stepsRecipeAdapter=new StepsRecipeAdapter(context,listofStep, this::onListItemClick);
        recyclerView.setAdapter(stepsRecipeAdapter);


        ingredientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), IngredientDetail.class);
                intent.putParcelableArrayListExtra(INGREDIENT,listOfIngredients);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onListItemClick(int clickedIndex) {
        if (mTwoPane!=true) {
            Intent intent = new Intent(getContext(), RecipeStepActivity.class);
            intent.putExtra(STEPS, listofStep.get(clickedIndex));
            mCallback.onRecipieSelected(clickedIndex);
            startActivity(intent);
        }else {
            pass(clickedIndex);

        }

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback= (OnSelectRecipie) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"MUST IMPLEMENT onSelectRecipie");
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LISTOFINGREDIENT,listOfIngredients);
        outState.putParcelableArrayList(LISTOFSTEP,listofStep);
    }

    private void pass(int pos) {
        if (mTwoPane) {
            FragmentManager fragmentManager = getFragmentManager();

            Toast.makeText(getContext(), "CLCIKED" + pos, Toast.LENGTH_SHORT).show();

            VideoPlayingFragment videoPlayingFragment = new VideoPlayingFragment();
            //PASS DATA
            videoPlayingFragment.setObject(listofStep.get(pos));
            fragmentManager.beginTransaction().replace(R.id.playerFragment, videoPlayingFragment).commit();
            InstructionFragment instructionFragment = new InstructionFragment();
            // PASS DATA HERE
            instructionFragment.setObject(listofStep.get(pos));
            fragmentManager.beginTransaction().replace(R.id.descriptionFragment, instructionFragment).commit();

        }
    }
}