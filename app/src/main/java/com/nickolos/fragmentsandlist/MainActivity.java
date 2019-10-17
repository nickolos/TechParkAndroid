package com.nickolos.fragmentsandlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements ItemClickHandler {

    public static final String LIST_FRAGMENT = "LIST_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MAinActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT);
        if (fragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fl_fragment_container, new ListFragment(), LIST_FRAGMENT);
            transaction.commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MAinActivity", "activity destroyed");
    }


    @Override
    public void selectNumber(String number, int color) {
        Bundle bundle = new Bundle();
        bundle.putString("number", number);
        bundle.putInt("color", color);
        Fragment fragment = new ShowDisplayFragment();
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}