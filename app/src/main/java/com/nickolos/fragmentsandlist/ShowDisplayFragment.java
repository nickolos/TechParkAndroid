package com.nickolos.fragmentsandlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ShowDisplayFragment extends Fragment {


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.second_fragment, container, false);
        if (getArguments() != null) {
            TextView textView = v.findViewById(R.id.text);
            textView.setTextColor(getArguments().getInt("color"));
            textView.setText(getArguments().getString("number"));
        }
        return v;
    }
}
