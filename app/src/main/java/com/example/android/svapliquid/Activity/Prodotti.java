package com.example.android.svapliquid.Activity;

import android.util.Log;

import com.example.android.svapliquid.Activity.Ordin.data.defaultData.List;
import com.example.android.svapliquid.Activity.activity.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Andorid on 07/08/2017.
 */

public class Prodotti extends ArrayList<Prodotto> implements List<Prodotto> {
    public static final String TAG = "ProdottiData - ";
    public void save(String fileName) throws IOException {
        File outFile = new File(Utility.directory+fileName);
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(outFile));
        os.writeObject(this);
        os.close();
    }
    public static Prodotti load(String fileName) throws IOException, ClassNotFoundException{
        Log.i(ILog.LOG_TAG, MainActivity.TAG+ "file: "+fileName);
        FileInputStream fis = new FileInputStream(Utility.directory+fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        Prodotti prodotti = (Prodotti) is.readObject();
        is.close();
        fis.close();
        return prodotti;
    }
    public double getPrezzo() {
        if (!isEmpty()) {
            double prezzo = 0;
            for (int i = 0; i < this.size(); i++) {
                prezzo += this.get(i).getPrezzo();
            }
            double sconto = 0;
            if (this.size() >= 3) {
                if (this.size() <= 5) {
                    sconto = this.size() * 0.3;
                } else {
                    if (this.size() < 10) {
                        sconto = this.size() * 0.5;
                    } else {
                        sconto = this.size() * 0.7;
                    }
                }
            }
            prezzo -= sconto;
            Log.i(ILog.LOG_TAG, TAG+ "castVirgola="+Utility.castVirgola(prezzo, 0.5));
            return Utility.castVirgola(prezzo, 0.5);
        }
        return 0;
    }
    public static boolean removeFile(String nameFile) {
        File file = new File(Utility.directory+nameFile);
        return file.delete();
    }

    public String getMessage(String nomeAccount) {
        String m ="";
        for (int i=0; i<this.size(); i++) {
            if (i != 0) {
                m += "\n";
            }
            m += this.get(i).getMessage(i+1)+"\n";
        }
        return "ProdottoData '"+nomeAccount+"':\n"+m+"\nPrezzoData Totale: "+this.getPrezzo()+"€";
    }
}
