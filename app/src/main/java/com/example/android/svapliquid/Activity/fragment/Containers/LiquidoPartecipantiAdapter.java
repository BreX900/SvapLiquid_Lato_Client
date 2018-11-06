package com.example.android.svapliquid.Activity.fragment.Containers;

import com.example.android.svapliquid.Activity.databases.svapliquid_db.*;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.liquido.Liquidi;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Android on 21/07/2017.
 */

public class LiquidoPartecipantiAdapter extends Partecipanti {
    private final Liquidi liquidi;
    private final int idTipoTiro;
    public LiquidoPartecipantiAdapter(RisultatiRicerca rr, int idTipoTiro)  {
        this.liquidi = rr.getLiquidi();
        this.idTipoTiro = idTipoTiro;
    }


    @Override
    public HashMap<Integer, ArrayList<Partecipante>> getPartecipanti() {
        HashMap<Integer, ArrayList<Partecipante>> partecipanti = new HashMap<>();
        for (int a=0; a<this.liquidi.getNumberRecords(); a++) {
            if (this.liquidi.getRecordByPosition(a).getIdTipoTiro() == idTipoTiro) {
                ArrayList<Partecipante> partecipantes;
                if (partecipanti.containsKey(this.liquidi.getRecordByPosition(a).getIdCategoria())) {
                    partecipantes = partecipanti.get(this.liquidi.getRecordByPosition(a).getIdCategoria());
                } else {
                    partecipantes = new ArrayList<>();
                    partecipanti.put(this.liquidi.getRecordByPosition(a).getIdCategoria(), partecipantes);
                }
                partecipantes.add(new Partecipante(this.liquidi.getRecordByPosition(a).getId(), this.liquidi.getRecordByPosition(a).getNome()));
            }
        }
        return partecipanti;
    }
}
