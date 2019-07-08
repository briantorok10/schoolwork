package net.androidbootcamp.instrumenttuner;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


public class Metronome extends AppCompatActivity {

     MediaPlayer mp;
     Timer mainTimer;
     TimerTask mainTimerTask;
     int bpm;
     EditText bpm_num;
     AnimationDrawable metroAnim;
     ImageView metroImage;

    public void play(){

        bpm_num = findViewById(R.id.bpm_num);
        bpm = Integer.parseInt(bpm_num.getText().toString());

        mainTimer = new Timer();
        mainTimerTask = new MyTimerTask();
        mainTimer.schedule(mainTimerTask,0, 60000/bpm);

    }

    class MyTimerTask extends TimerTask{
        @Override
        public void run(){
            playSound();

        }
    }
    private void playSound(){
        mp = MediaPlayer.create(Metronome.this, R.raw.metronome_low);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
            }
        });
    }

    public void stop(){
        mainTimerTask.cancel();
        mainTimer.purge();
        mainTimer.cancel();

    }
    public void startMetronome() {
        final Button startMetronome = findViewById(R.id.startMetronome);
        final Animation bounce = AnimationUtils.loadAnimation(Metronome.this, R.anim.bounce);
        startMetronome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
                startMetronome.startAnimation(bounce);
                metroAnim.start();
            }
        });
    }

    public void stopMetronome() {
        final Button stopMetronome = findViewById(R.id.stopMetronome);
        final Animation bounce = AnimationUtils.loadAnimation(Metronome.this, R.anim.bounce);

        stopMetronome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
                stopMetronome.startAnimation(bounce);
                metroAnim.stop();
            }
        });
    }

    public void animation(){
        metroImage = findViewById(R.id.metro_img);
        metroImage.setBackgroundResource(R.drawable.metronome_animation);
        metroAnim = (AnimationDrawable) metroImage.getBackground();
    }

    public void openTuner(){
        final Button toTuner = findViewById(R.id.toTuner);
        final Animation bounce = AnimationUtils.loadAnimation(Metronome.this, R.anim.bounce);
        toTuner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Metronome.this, AudioInput.class);
                toTuner.startAnimation(bounce);
                startActivity(i);
            }
        });
    }
    public void openToner(){
        final Button toToner = findViewById(R.id.toToner);
        final Animation bounce = AnimationUtils.loadAnimation(Metronome.this, R.anim.bounce);
        toToner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Metronome.this, ToneTuner.class);
                toToner.startAnimation(bounce);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);
        startMetronome();
        stopMetronome();
        openTuner();
        openToner();
        animation();
        metroImage.setImageDrawable(null);

    }
}

