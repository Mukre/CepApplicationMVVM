package com.example.cepapplicationmvvm.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cepapplicationmvvm.model.Cep;
import com.example.cepapplicationmvvm.request.ViaCep;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchViewModel extends ViewModel implements ViaCep.CepCallback, ViaCep.CepErrorCallback {
    public boolean isSearching = false;
    private MutableLiveData<Cep> cepLiveData = new MutableLiveData<>();
    private ViaCep viaCep;
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public SearchViewModel() {
        this.viaCep = new ViaCep(this::callbackListener, this::errorCallbackListener);
    }

    public boolean searchCep(String cep, Context ctx) {
        if (validateCep(cep)) {
            isSearching = true;
            try {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        viaCep.cepRequest(ctx, cep);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public boolean validateCep(String cep) {
        Pattern p = Pattern.compile("[0-9]{5}[0-9]{3}");
        Matcher m = p.matcher(cep);
        boolean b = m.matches();
        return b;
    }

    @Override
    public void callbackListener(Cep newCep) {
        if (newCep != null) {
            List<Cep> results = Cep.find(Cep.class, "cep = ?", newCep.cep);
            if (!results.isEmpty())
                Cep.delete(results.get(0));
            Cep.save(newCep);

            updateCep(newCep);
        }

        isSearching = false;
    }

    @Override
    public void errorCallbackListener(String error) {
        if (error.contains("No value")) {
            setErrorLiveData("Cep não encontrado, tente um valor válido");
        } else if (error.contains("No connection")) {
            setErrorLiveData("Erro de conexão com o servidor");
        } else {
            setErrorLiveData("Ocorreu um erro ao carregar sua pesquisa, tente novamente mais tarde");
        }
        isSearching = false;
    }

    public void updateCep(Cep cep) {
        cepLiveData.setValue(cep);
    }

    public LiveData<Cep> getLiveDataCep() {
        return cepLiveData;
    }

    public void setErrorLiveData(String error) {
        errorLiveData.setValue(error);
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}
