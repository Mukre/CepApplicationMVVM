package com.example.cepapplicationmvvm;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cepapplicationmvvm.databinding.ActivitySearchBinding;
import com.example.cepapplicationmvvm.viewmodel.SearchViewModel;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private SearchViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        Intent intent = getIntent();
        if (intent.hasExtra("clicked_cep")) {
            binding.searchEditText.setVisibility(GONE);
            binding.buttonSearch.setVisibility(GONE);
            binding.detailsTextView.setVisibility(View.VISIBLE);
            binding.detailsTextView.setText(intent.getStringExtra("clicked_cep"));

        } else {
            binding.detailsTextView.setVisibility(GONE);
            binding.buttonSearch.setOnClickListener(v -> {
                if (binding.searchEditText.getText().length() < 8) {
                    Toast.makeText(getApplicationContext(), "Deve ter 8 números", Toast.LENGTH_SHORT).show();
                } else if (viewModel.isSearching) {
                    Toast.makeText(getApplicationContext(), "Por gentileza, aguarde até o retorno da pesquisa", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.searchCep(binding.searchEditText.getText().toString(), v.getContext());
                }
            });

            viewModel.getLiveDataCep().observe(this, cep -> {
                binding.searchEditText.setVisibility(GONE);
                binding.buttonSearch.setVisibility(GONE);
                binding.detailsTextView.setVisibility(View.VISIBLE);
                binding.detailsTextView.setText(cep.toString());
            });

            viewModel.getErrorLiveData().observe(this, error -> {
                Toast.makeText(this, viewModel.getErrorLiveData().getValue(), Toast.LENGTH_SHORT).show();
            });
        }
        binding.arrowImageView.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        if (!viewModel.isSearching) {
            super.onBackPressed();
        }
    }
}