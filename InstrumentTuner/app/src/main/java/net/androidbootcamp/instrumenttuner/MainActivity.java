package net.androidbootcamp.instrumenttuner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

    public void openMetronome(View view){
        final Button toMetronome = findViewById(R.id.toMetronome);
        final Animation bounce = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bounce);
        toMetronome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Metronome.class);
                toMetronome.startAnimation(bounce);
                startActivity(i);
            }
        });
    }

    public void openToneTuner(View view) {
            Button toToneTuner = findViewById(R.id.toToneTuner);
            toToneTuner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ToneTuner.class);
                    startActivity(intent);
                }
            });
    }

    public void openAudioInput(View view)
    {
        Button toAudioInput = findViewById(R.id.toAudioInput);
        toAudioInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AudioInput.class);
                startActivity(intent);
            }
        });

    }
}
