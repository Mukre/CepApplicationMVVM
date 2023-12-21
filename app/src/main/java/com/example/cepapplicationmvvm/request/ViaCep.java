package com.example.cepapplicationmvvm.request;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cepapplicationmvvm.model.Cep;

import org.json.JSONException;
import org.json.JSONObject;

public class ViaCep {
    public interface CepCallback {
        void callbackListener(Cep cep);
    }

    public interface CepErrorCallback {
        void errorCallbackListener(String error);
    }

    CepCallback cepCallback;
    CepErrorCallback errorCallback;

    public ViaCep(CepCallback cepCallback, CepErrorCallback errorCallback) {
        this.cepCallback = cepCallback;
        this.errorCallback = errorCallback;
    }

    public void cepRequest(Context ctx, String cep) {
        RequestQueue volleyQueue = Volley.newRequestQueue(ctx);
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        Cep newCep = new Cep(response.getString("cep"));
                        newCep.complemento = response.getString("complemento");
                        newCep.bairro = response.getString("bairro");
                        newCep.ddd = response.getString("ddd");
                        newCep.gia = response.getString("gia");
                        newCep.ibge = response.getString("ibge");
                        newCep.localidade = response.getString("localidade");
                        newCep.logradouro = response.getString("logradouro");
                        newCep.uf = response.getString("uf");
                        newCep.siafi = response.getString("siafi");

                        cepCallback.callbackListener(newCep);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("HttpRequests", "loadCepError: ${error.localizedMessage}");
                        errorCallback.errorCallbackListener("No value");
                    }
                },

                (Response.ErrorListener) error -> {
                    Log.e("HttpRequests", "loadCepError: ${error.localizedMessage}");
                    if (error.networkResponse == null) {
                        errorCallback.errorCallbackListener("No connection");
                    } else {
                        errorCallback.errorCallbackListener("Generic");
                    }

                }
        );
        volleyQueue.add(jsonObjectRequest);
    }
}
