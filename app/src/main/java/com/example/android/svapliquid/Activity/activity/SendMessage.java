package com.example.android.svapliquid.Activity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SendMessage implements DialogInterface.OnClickListener {
    final int app, WhatsApp=0, Telegram=1, TelegramX=2;
    final String text;
    final AppCompatActivity activity;
    public SendMessage(int app, String text, AppCompatActivity activity) {
        this.app = app;
        this.text = text;
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Intent sendMessageIntent = new Intent(Intent.ACTION_SEND);
        sendMessageIntent.setType("text/plain");
            /*switch (app){
                case WhatsApp:
                    sendMessageIntent.setPackage("com.whatsapp");
                    break;
                case Telegram:
                    sendMessageIntent.setPackage("org.telegram.messenger");
                    break;
                case TelegramX:
                    sendMessageIntent.setPackage("org.thunderdog.challegram");
                    break;
            }*/
        sendMessageIntent.putExtra(Intent.EXTRA_TEXT, text);
        try {
            activity.startActivity(Intent.createChooser(sendMessageIntent, "Send message"));
        } catch (android.content.ActivityNotFoundException ex) {
            if (app==WhatsApp) Toast.makeText(activity, "Whatsapp have not been installed.", Toast.LENGTH_LONG).show();
            else {
                try {
                    sendMessageIntent.setPackage("org.thunderdog.challegram");
                    activity.startActivity(Intent.createChooser(sendMessageIntent, "Send message"));
                } catch (android.content.ActivityNotFoundException e) {
                    Toast.makeText(activity, "Telegram have not been installed.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
