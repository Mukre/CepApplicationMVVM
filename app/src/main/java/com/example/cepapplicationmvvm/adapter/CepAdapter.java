package com.example.cepapplicationmvvm.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cepapplicationmvvm.R;
import com.example.cepapplicationmvvm.model.Cep;

import java.util.Collections;
import java.util.List;

public class CepAdapter extends RecyclerView.Adapter<CepHolder> {
    public interface CepListCallback {
        void onClickCep(Cep cep);
    }

    private List<Cep> cepList;
    final CepListCallback callback;

    public CepAdapter(CepListCallback callback) {
        this.callback = callback;
    }


    @NonNull
    @Override
    public CepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CepHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CepHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.cep_textview.setText(cepList.get(position).cep);

        holder.button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickCep(cepList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return cepList != null ? cepList.size() : 0;
    }

    public void addCep(Cep cep) {
        cepList.add(0, cep);
        notifyItemInserted(0);
    }

    public void removeCep(Cep cep) {
        int position = cepList.indexOf(cep);
        cepList.remove(position);
        notifyDataSetChanged();
    }

    public void updateRecycler(List<Cep> cepList) {
        Collections.reverse(cepList);
        this.cepList = cepList;
        notifyDataSetChanged();
    }
}

class CepHolder extends RecyclerView.ViewHolder {
    public TextView cep_textview;
    public ImageButton button_search;

    public CepHolder(@NonNull View itemView) {
        super(itemView);
        cep_textview = itemView.findViewById(R.id.cep_text_view);
        button_search = itemView.findViewById(R.id.search_button);
    }
}
