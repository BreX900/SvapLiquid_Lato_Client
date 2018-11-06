package com.example.android.svapliquid.Activity;

import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.base.BaseR;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.boccetta.BoccettaR;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.liquido.Liquido;
import com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.ricetta.RicettaR;

import java.io.Serializable;

/**
 * Created by Andorid on 07/08/2017.
 */

public class Prodotto implements Serializable, Cloneable {
    private final Liquido liquido;
    private double mgNicotina;
    private BoccettaR boccetta;
    private final BaseR base, baseNicotinata;
    private final RicettaR ricetta;
    private String composizione;
    private final boolean editableComposizione;
    OnActionListener onActionListener;

    public Prodotto(Liquido liquido, int mgNicotina, BoccettaR boccetta, BaseR base, BaseR baseNicotinata, RicettaR ricetta) {
        this(liquido, mgNicotina, boccetta,base, baseNicotinata, ricetta, ricetta.getComposizione());
    }

    public void setComposizione(String composizione) {
        this.composizione = composizione;
    }

    public boolean isEditableComposizione() {
        return editableComposizione;
    }

    public Prodotto(Liquido liquido, int mgNicotina, BoccettaR boccetta, BaseR base, BaseR baseNicotinata, RicettaR ricetta, String composizione) {
        this.liquido = liquido;
        this.mgNicotina = mgNicotina;
        this.boccetta = boccetta;
        this.base = base;
        this.baseNicotinata = baseNicotinata;
        this.ricetta = ricetta;
        this.composizione = composizione;
        this.editableComposizione = composizione.equalsIgnoreCase("Any");

    }
    public double getPrezzo() {
        return Prezzo.getPrezzo(this.boccetta, this.base, this.mgNicotina, this.baseNicotinata, this.ricetta);
    }

    public void setBoccetta(BoccettaR boccetta) {
        this.boccetta = boccetta;
        actionListener();
    }

    public void setNicotina(double mgNicotina) {
        this.mgNicotina = mgNicotina;
        actionListener();
    }

    public Liquido getLiquido() {
        return liquido;
    }

    public BoccettaR getBoccetta() {
        return boccetta;
    }
    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener =  onActionListener;
    }
    private void actionListener() {
         if (this.onActionListener != null) {this.onActionListener.action();}
    }

    @Override
    public Prodotto clone() {
        try {return (Prodotto) super.clone();}
        catch (CloneNotSupportedException exc){
            exc.printStackTrace();
            return null;
        }
    }

    public double getMgNicotina() {
        return mgNicotina;
    }

    public String getMessage(int position) {
        return "--- "+position+". "+this.liquido.getNome()+"\n"+this.composizione+"c "+this.mgNicotina+"n "+this.boccetta.getMl()+"ml "+this.boccetta.getNome();
    }

    @Override
    public String toString() {
        String s = "";
        s += "Nome: "+getLiquido().getNome()+"\n";
        s += "Boccetta: "+getBoccetta().getNome()+" - "+getBoccetta().getMl()+"ml\n";
        s += "Nicotina: "+getMgNicotina();
        return s;
    }

    public String getComposizione() {
        return composizione;
    }
}
