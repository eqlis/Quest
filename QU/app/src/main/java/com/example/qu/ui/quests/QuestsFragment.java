package com.example.qu.ui.quests;

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

public class QuestsFragment extends Fragment {

    private QuestsViewModel questsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        questsViewModel =
                new ViewModelProvider(this).get(QuestsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quests, container, false);
        final TextView textView = root.findViewById(R.id.text_quests);
        questsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}