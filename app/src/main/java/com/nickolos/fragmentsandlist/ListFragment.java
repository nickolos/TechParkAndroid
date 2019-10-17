package com.nickolos.fragmentsandlist;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ListFragment extends Fragment {

    private static final int COUNT_PORTRAIT =3 ;

    private static final int COUNT_LANDSCAPE = 4;

    private static final int MAX_NUMBER = 100;

    private int currentNumber = MAX_NUMBER;

    private MyAdapter adapter;


    private ItemClickHandler itemClickHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("First_fragment", "fragment created");

        if (savedInstanceState != null) {
            currentNumber = savedInstanceState.getInt("MAX_NUMBER");
            Log.d("First_fragment", "restored "+currentNumber);
        } else {
            Log.d("First_fragment", "nothing to restore");
            currentNumber = MAX_NUMBER;
        }

        ArrayList<String> listNumbers = new ArrayList<>();
        generateList(listNumbers);
        adapter = new MyAdapter(listNumbers);

    }


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("First_Fragment", "onCreateView");
        View v = inflater.inflate(R.layout.first_fragment, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.rv_list_numbers);

        boolean isPortrait = (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int spanCount = (isPortrait) ? COUNT_PORTRAIT : COUNT_LANDSCAPE;

        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), spanCount));

        recyclerView.setAdapter(adapter);

        v.findViewById(R.id.button_add_item).setOnClickListener(v1 -> adapter.addItem());
        return v;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("MAX_NUMBER", currentNumber);
        Log.d("First_Fragment", "saved new add="+currentNumber);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        itemClickHandler = (ItemClickHandler) context;
        Log.d("First_Fragment", "onAttach");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("First_Fragment", "onDetach");
    }

    @Override
    public void onDestroy() {
        Log.d("First_fragment", "fragment destroyed");
        super.onDestroy();
    }

    private void generateList(List<String> list) {
        for (int i = 1; i <= currentNumber; i++) {
            list.add(i + "");
        }
    }


    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private List<String> mData;

      //  private LayoutInflater mInflater;


        MyAdapter(List<String> data) {
            this.mData = data;
       //     this.mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View v = inflater.inflate(R.layout.number_list_item, viewGroup, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
            String str = mData.get(position);
            myViewHolder.mTextView.setText(str);
            if (Integer.parseInt(str) % 2 == 0) {
                myViewHolder.mTextView.setTextColor(Color.RED);
            } else {
                myViewHolder.mTextView.setTextColor(Color.BLUE);
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        void addItem() {
            currentNumber++;
            mData.add(String.valueOf(currentNumber));
            adapter.notifyItemInserted(mData.size() - 1);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_number_item);
            mTextView.setOnClickListener(view -> {
                String number = ((TextView) view).getText().toString();
               int color = ((TextView) view).getCurrentTextColor();
               itemClickHandler.selectNumber(number,color);
            });
        }

    }


}
