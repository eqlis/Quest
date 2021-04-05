package com.example.qu.ui.rating;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qu.R;

public class RatingFragment extends Fragment {

    private RatingViewModel ratingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ratingViewModel =
                new ViewModelProvider(this).get(RatingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rating, container, false);
        final TextView textView = root.findViewById(R.id.text_rating);
        ratingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}