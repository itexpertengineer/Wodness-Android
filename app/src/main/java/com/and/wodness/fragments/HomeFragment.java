package com.and.wodness.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.and.wodness.R;
import com.and.wodness.spark.SparkAdapter;
import com.and.wodness.spark.SparkView;

import java.util.Random;


/**
 * Created by GangXian on 8/29/2017.
 */
public class HomeFragment extends Fragment {
    private RandomizedAdapter adapter, adapter1;
    private TextView scrubInfoTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_profile_c1, container, false);

        SparkView sparkView = (SparkView) view.findViewById(R.id.sparkview);
        SparkView sparkView1 = (SparkView) view.findViewById(R.id.sparkview1);

        adapter = new RandomizedAdapter();
        sparkView.setAdapter(adapter);
        adapter1 = new RandomizedAdapter();
        sparkView1.setAdapter(adapter1);


        return view;

    }

    public static class RandomizedAdapter extends SparkAdapter {
        private final float[] yData;
        private final Random random;

        public RandomizedAdapter() {
            random = new Random();
            yData = new float[5];
            randomize();
        }

        public void randomize() {
            for (int i = 0, count = yData.length; i < count; i++) {
                yData[i] = random.nextFloat();
            }


            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return yData.length;
        }

        @Override
        public Object getItem(int index) {
            return yData[index];
        }

        @Override
        public float getY(int index) {
            return yData[index];
        }
    }
}