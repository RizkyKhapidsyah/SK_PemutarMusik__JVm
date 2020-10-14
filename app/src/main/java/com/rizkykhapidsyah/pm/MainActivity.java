package com.rizkykhapidsyah.pm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    private ImageButton Tombol_Mainkan;
    private ImageButton Tombol_Pause;
    private ImageButton Tombol_Berhenti;
    private MediaPlayer PemutarMusik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Tombol_Mainkan = findViewById(R.id.play);
        Tombol_Pause = findViewById(R.id.pause);
        Tombol_Berhenti = findViewById(R.id.stop);

        Tombol_Mainkan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainkanMedia();
            }
        });

        Tombol_Pause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Pausekan_Media();
            }
        });

        Tombol_Berhenti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Berhentikan_Media();
            }
        });

        setup();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (Tombol_Berhenti.isEnabled()) {
            Berhentikan_Media();
        }
    }

    public void onCompletion(MediaPlayer mp) {
        Berhentikan_Media();
    }

    private void MainkanMedia() {
        PemutarMusik.start();

        Tombol_Mainkan.setEnabled(false);
        Tombol_Pause.setEnabled(true);
        Tombol_Berhenti.setEnabled(true);
    }

    private void Berhentikan_Media() {
        PemutarMusik.stop();
        Tombol_Pause.setEnabled(false);
        Tombol_Berhenti.setEnabled(false);

        try {
            PemutarMusik.prepare();
            PemutarMusik.seekTo(0);
            Tombol_Mainkan.setEnabled(true);
        } catch (Throwable t) {
            goBlooey(t);
        }
    }

    private void Pausekan_Media() {
        PemutarMusik.pause();

        Tombol_Mainkan.setEnabled(true);
        Tombol_Pause.setEnabled(false);
        Tombol_Berhenti.setEnabled(true);
    }

    private void Muat_Clip() {
        try {
            PemutarMusik = MediaPlayer.create(this, R.raw.a_time_for_us);
            PemutarMusik.setOnCompletionListener(this);
        } catch (Throwable t) {
            goBlooey(t);
        }
    }

    private void setup() {
        Muat_Clip();
        Tombol_Mainkan.setEnabled(true);
        Tombol_Pause.setEnabled(false);
        Tombol_Berhenti.setEnabled(false);
    }

    private void goBlooey(Throwable t) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exception!").setMessage(t.toString())
                .setPositiveButton("OK", null).show();
    }
}

