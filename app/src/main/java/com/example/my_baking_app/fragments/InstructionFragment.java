package com.example.my_baking_app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.my_baking_app.R;
import com.example.my_baking_app.models.Recipe;


public class InstructionFragment extends Fragment {

    Recipe.StepsBean object;
    TextView title,description;
    String OBJECT="object";
    public Recipe.StepsBean getObject() {
        return object;
    }

    public void setObject(Recipe.StepsBean object) {
        this.object = object;
    }

    public InstructionFragment() {
        // Required empty public constructor
    }


    public static InstructionFragment newInstance(String param1, String param2) {
        InstructionFragment fragment = new InstructionFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            object=savedInstanceState.getParcelable(OBJECT);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(OBJECT,object);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_instruction, container, false);

        title=rootView.findViewById(R.id.shortDescription);
        description=rootView.findViewById(R.id.longDescription);
        title.setText(object.getShortDescription());
        description.setText(object.getDescription());
        return rootView;

    }
}