package com.example.android.svapliquid.Activity.fragment.Containers;

import com.example.android.svapliquid.Activity.databases.svapliquid_db.RisultatiRicerca;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.categoria.Categorie;

/**
 * Created by Android on 21/07/2017.
 */

public class CategoriaTabelleAdapter  extends Gruppi<Categorie> {
    public CategoriaTabelleAdapter(RisultatiRicerca rr, int idTipoTiro) {
        super(rr.getTableCategoriaByIdTipoTiro(idTipoTiro));
    }

    @Override
    public String addStringGroup(int position) {
        return this.tabella.getRecordByPosition(position).getNome();
    }

    @Override
    protected int addIdGroup(int position) {
        return this.tabella.getRecordByPosition(position).getId();
    }
}
