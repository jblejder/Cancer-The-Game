package com.kutapps.keyten.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.kutapps.keyten.R;
import com.kutapps.keyten.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    HomeViewModel       viewModel;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = new HomeViewModel();
        binding.setModel(viewModel);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setKeyten();
            }
        });
    }
}
