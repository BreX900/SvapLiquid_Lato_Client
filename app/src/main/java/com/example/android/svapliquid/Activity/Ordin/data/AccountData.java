package com.example.android.svapliquid.Activity.Ordin.data;

/**
 * Created by Andorid on 13/11/2017.
 */

public class AccountData {
    private String nome;
    private boolean administrator;

    public AccountData(String nome, boolean administrator) {
        this.nome = nome;
        this.administrator = administrator;
    }
    public AccountData(String nome) {
        this(nome, false);
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public boolean isAdministrator() {
        return administrator;
    }
    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }
}
