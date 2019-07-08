package net.androidbootcamp.instrumenttuner;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

public class ToneTuner extends AppCompatActivity
{
    private final int duration = 30; // seconds
    private final int sampleRate = 44100;
    private final int numSamples = duration * sampleRate;
    private final double sample[] = new double[numSamples];
    private double freqOfTone = 440; // hz
    private final byte generatedSnd[] = new byte[2 * numSamples];
    private final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
            sampleRate, AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
            AudioTrack.MODE_STATIC);
    private Spinner notesList;
    private String notes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tone_tuner);

        notesList = findViewById(R.id.notesSpinner);
        Button button = findViewById(R.id.playTone);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                setFrequency();
                genTone();
                if(audioTrack.getPlayState() != AudioTrack.PLAYSTATE_PLAYING)
                {
                    playSound();
                }
            }
        });
        Button stop = findViewById(R.id.stopTone);
        stop.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(audioTrack.getPlayState() != AudioTrack.PLAYSTATE_STOPPED)
                {
                    audioTrack.stop();
                }
            }
        });
    }

    public void genTone()
    {
        // fill out the array
        for (int i = 0; i < numSamples; ++i)
        {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
        }
        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample)
        {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
    }

    public void setFrequency()
    {
        notes = notesList.getSelectedItem().toString();
        if(notes.equals("A"))
        {
            freqOfTone = 440;
        }else if(notes.equals("A#"))
        {
            freqOfTone = 466.16;
        }else if(notes.equals("B"))
        {
            freqOfTone = 493.88;
        }else if(notes.equals("C"))
        {
            freqOfTone = 523.25;
        }else if(notes.equals("C#"))
        {
            freqOfTone = 554.37;
        }else if(notes.equals("D"))
        {
            freqOfTone = 587.33;
        }else if(notes.equals("D#"))
        {
            freqOfTone = 622.25;
        }else if(notes.equals("E"))
        {
            freqOfTone = 659.25;
        }else if(notes.equals("F"))
        {
            freqOfTone = 698.46;
        }else if(notes.equals("F#"))
        {
            freqOfTone = 739.99;
        }else if(notes.equals("G"))
        {
            freqOfTone = 783.99;
        }else if(notes.equals("G#"))
        {
            freqOfTone = 830.61;
        }
    }

    public void playSound()
    {
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();
    }

    public void openMetronome(View view) {
        Intent intent = new Intent(this, Metronome.class);
        startActivity(intent);
    }

    public void openMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openAudioInput(View view)
    {
        Intent intent = new Intent(this, AudioInput.class);
        startActivity(intent);
    }
}
