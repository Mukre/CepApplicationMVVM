package com.example.cepapplicationmvvm.model;

import com.orm.SugarRecord;

public class Cep extends SugarRecord {
    public String cep;
    public String logradouro;
    public String complemento;
    public String bairro;
    public String localidade;
    public String uf;
    public String ibge;
    public String gia;
    public String ddd;
    public String siafi;

    public Cep(String cep) {
        this.cep = cep;
    }
    public Cep() {
    }

    @Override
    public String toString() {
        return "CEP: " + cep + '\n' +
                "LOGRADOURO: " + logradouro + '\n' +
                "COMPLEMENTO: " + complemento + '\n' +
                "BAIRRO: " + bairro + '\n' +
                "LOCALIDADE: " + localidade + '\n' +
                "UF: " + uf + '\n' +
                "IBGE: " + ibge + '\n' +
                "GIA: " + gia + '\n' +
                "DDD: " + ddd + '\n' +
                "SIAFI: " + siafi;
    }
}
