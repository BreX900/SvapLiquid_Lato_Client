package com.example.android.svapliquid.Activity.databases.svapliquid_db.tabel_record.Query;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.svapliquid.Activity.ILog;
import com.example.android.svapliquid.Activity.databases.CursorS;
import com.example.android.svapliquid.Activity.databases.DB;

/**
 * Created by Android on 20/07/2017.
 */

public class QueryString {
    private String qs, qf, qw, qo, qg;

    public QueryString() {}

    public QueryString(String qs) {
        this.addSelect(qs);
    }

    public QueryString addSelect (String q) {
        if (this.qs == null) {qs = q;}
        else {this.qs += ", "+q;}
        return this;
    }
    public QueryString addSelectAll() {
        return this.addSelect("*");
    }
    public QueryString addFrom (String q) {
        if (this.qf == null) {qf = q;}
        else {this.qf += ", "+q;}
        return this;
    }
    public QueryString addFromAs (String q, String a) {
        return addFrom(q+" AS "+a);
    }
    private QueryString addWhere(String q, String logic) {
        if (this.qw == null) {qw = q;}
        else {this.qw += " "+logic+" "+q;}
        return this;
    }
    public QueryString addWhereAnd(String q) {
        return this.addWhere(q, "AND");
    }
    public QueryString addWhereOr(String q) {
        return this.addWhere(q, "OR");
    }
    public QueryString addWhereAndLike(String q, String l) {
        return this.addWhereAnd(q+" LIKE '%"+l+"%'");
    }
    public QueryString addWhereAndLikeRestrict(String q, String l) {
        return this.addWhereAnd(q+" LIKE '"+l+"'");
    }
    public void searchAddWhereAndLike (String table, String search) {
        if (!(search == null || search.equalsIgnoreCase("Any") || search.isEmpty())) {
            this.addWhereAndLike(table, search);
        }
    }
    private QueryString addOrder(String q) {
        if (this.qo == null) {qo = q;}
        else {this.qo += ", "+q;}
        return this;
    }
    public QueryString addOrderAsc(String s) {
        return this.addOrder(s+" ASC");
    }
    public QueryString addOrderDesc(String s) {
        return this.addOrder(s+" DESC");
    }

    private String getQs() {
        if (this.qs == null) { return "";}
        else { return "SELECT "+this.qs+" "; }
    }
    private String getQf() {
        if (this.qf == null) { return "";}
        else { return "FROM "+this.qf+" "; }
    }
    private String getQw() {
        if (this.qw == null) { return "";}
        else {return "WHERE ("+this.qw+") ";}
    }
    private String getQo() {
        if (this.qo == null) { return "";}
        else {return "ORDER BY "+this.qo+" ";}
    }
    private String getQg() {
        if (this.qg == null) { return "";}
        else {return "GROUP BY "+this.qg+" ";}
    }
    @Override
    public String toString() {
        String s = "";
        if (this.qs!=null) s += this.qs;
        if (this.qf!=null) s += this.qf;
        if (this.qw!=null) s += this.qw;
        return s;
    }
    public String getQuery() {
        return this.getQs()+this.getQf()+this.getQw()+this.getQg()+this.getQo();
    }

    public QueryString addGroup(String s) {
        if (this.qg == null) {qg = s;}
        else {this.qg += ", "+s;}
        return this;
    }

    public CursorS run(SQLiteDatabase db) {
        Log.i(ILog.LOG_TAG, getQuery());
        return new CursorS(db.rawQuery(getQuery(), null));
    }
    public CursorS run(DB.Database database) {
        return run(database.getDatabase());
    }
    public static QueryString getDefaultQuery(String nomeTabella) {
        return new QueryString().addSelectAll().addFrom(nomeTabella);
    }
    public CursorS runDefault(SQLiteDatabase db, String nomeTabella) {
        return new QueryString().addSelectAll().addFrom(nomeTabella).run(db);
    }
    public CursorS runDefault(SQLiteDatabase db, String nomeTabella, String indexId, int id) {
        return new QueryString().addSelect(indexId+"="+id).addFrom(nomeTabella).run(db);
    }


}
