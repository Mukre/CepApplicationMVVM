package com.example.cepapplicationmvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cepapplicationmvvm.adapter.CepAdapter;
import com.example.cepapplicationmvvm.databinding.ActivityMainBinding;
import com.example.cepapplicationmvvm.model.Cep;

public class MainActivity extends AppCompatActivity implements CepAdapter.CepListCallback {
    ActivityMainBinding binding;
    RecyclerView recyclerView;
    CepAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchFab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        recyclerView = binding.recyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CepAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.getItemCount();
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getBaseContext(), DividerItemDecoration.HORIZONTAL));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateRecycler(Cep.listAll(Cep.class));
    }

    @Override
    public void onClickCep(Cep cep) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("clicked_cep", cep.toString());
        startActivityForResult(intent, 1);
    }
}