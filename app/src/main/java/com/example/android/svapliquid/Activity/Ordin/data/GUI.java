package com.example.android.svapliquid.Activity.Ordin.data;

public abstract class GUI<D extends Data> {
    protected final D data;
    public GUI(D data) {
        this.data = data;
    }

    public D getData() {
        return data;
    }
}
