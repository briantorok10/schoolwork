package net.androidbootcamp.instrumenttuner;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.HashMap;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;

public class AudioInput extends AppCompatActivity {

    private static DecimalFormat df = new DecimalFormat("00000.00");
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private final int sampleRate = 44100;
    private final double sample[] = new double[sampleRate];
    private float freqOfTone; // hz
    private final byte generatedSnd[] = new byte[2 * sampleRate];
    private String note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("TAG", "Class file loaded, creating instance");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_input);

        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.RECORD_AUDIO);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_CODE_ASK_PERMISSIONS);
        }

        Log.i("TAG", "Permissions granted. Loading AudioDispatcher.");

        // AudioDispatcher object comes with the TarsosDSP library.
        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        // AudioProcessor object accepts PitchProcessor, which accepts PitchEstimationAlgorithm
        // PitchEstimationAlgorithm uses FFT, or "Fast-Fourier Transform" to estimate frequency values.
        dispatcher.addAudioProcessor(new PitchProcessor(PitchEstimationAlgorithm.FFT_YIN, 22050, 1024,
                new PitchDetectionHandler() {
                    @Override
                    public void handlePitch(final PitchDetectionResult pitchDetectionResult,
                                            AudioEvent audioEvent) {
                        final float pitchInHz = pitchDetectionResult.getPitch();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Double centsFromNote = 0.0;
                                TextView text = (TextView) findViewById(R.id.tvFreq);
                                TextView noteLetter = (TextView) findViewById(R.id.noteLetter);
                                ProgressBar pb = (ProgressBar) findViewById(R.id.pb);

                                text.setText("Pitch: " + df.format(pitchInHz));
                                noteLetter.setText("Note: " + note);

                                if (pitchInHz >0 && pitchInHz <= 16.79)
                                {
                                    centsFromNote = getCents(16.35,pitchInHz);
                                    note = "C";
                                }
                                else if (pitchInHz > 16.76 && pitchInHz <= 17.835)
                                {
                                    centsFromNote = getCents(17.32,pitchInHz);
                                    note = "C#";
                                }
                                else if (pitchInHz > 17.835 && pitchInHz <= 18.35)
                                {
                                    centsFromNote = getCents(18.35,pitchInHz);
                                    note = "D";
                                }
                                else if (pitchInHz > 18.35 && pitchInHz <= 20.02)
                                {
                                    centsFromNote = getCents(19.44,pitchInHz);
                                    note = "D#";
                                }
                                else if (pitchInHz > 20.02 && pitchInHz <= 21.21)
                                {
                                    centsFromNote = getCents(20.60,pitchInHz);
                                    note = "E";
                                }
                                else if (pitchInHz < 20.60 && pitchInHz <= 21.215)
                                {
                                    centsFromNote = getCents(21.83,pitchInHz);
                                    note = "F";
                                }
                                else if (pitchInHz < 21.215 && pitchInHz <= 23.805)
                                {
                                    centsFromNote = getCents(23.12,pitchInHz);
                                    note = "F#";
                                }
                                else if (pitchInHz>23.805 && pitchInHz <= 25.22)
                                {
                                    centsFromNote = getCents(24.49,pitchInHz);
                                    note = "G";
                                }
                                else if (pitchInHz > 25.22 && pitchInHz <= 26.725)
                                {
                                    centsFromNote = getCents(25.95,pitchInHz);
                                    note = "G#";
                                }
                                else if (pitchInHz > 26.725 && pitchInHz <= 28.315)
                                {
                                    centsFromNote = getCents(27.50,pitchInHz);
                                    note = "A";
                                }
                                else if (pitchInHz > 28.315 && pitchInHz <= 29.995)
                                {
                                    centsFromNote = getCents(29.13,pitchInHz);
                                    note = "A#";
                                }
                                else if (pitchInHz > 29.995 && pitchInHz <= 31.78)
                                {
                                    centsFromNote = getCents(30.86,pitchInHz);
                                    note = "B";
                                }
                                else if (pitchInHz > 31.78 && pitchInHz <= 33.67)
                                {
                                    centsFromNote = getCents(32.70,pitchInHz);
                                    note = "C";
                                }
                                else if (pitchInHz > 33.67 && pitchInHz == 35.67)
                                {
                                    centsFromNote = getCents(34.64,pitchInHz);
                                    note = "C#";
                                }
                                else if (pitchInHz > 35.67 && pitchInHz == 36.70)
                                {
                                    centsFromNote = getCents(36.70,pitchInHz);
                                    note = "D";
                                }
                                else if (pitchInHz > 37.795 && pitchInHz <= 40.05)
                                {
                                    centsFromNote = getCents(38.89,pitchInHz);
                                    note = "D#";
                                }
                                else if (pitchInHz > 40.05 && pitchInHz <= 42.425)
                                {
                                    centsFromNote = getCents(41.20,pitchInHz);
                                    note = "E";
                                }
                                else if (pitchInHz > 42.425 && pitchInHz <= 44.945)
                                {
                                    centsFromNote = getCents(43.65,pitchInHz);
                                    note = "F";
                                }
                                else if (pitchInHz > 44.945 && pitchInHz <= 47.615)
                                {
                                    centsFromNote = getCents(46.24,pitchInHz);
                                    note = "F#";
                                }
                                else if (pitchInHz > 47.615 && pitchInHz == 48.99)
                                {
                                    centsFromNote = getCents(48.99,pitchInHz);
                                    note = "G";
                                }
                                else if (pitchInHz > 48.99 && pitchInHz <= 50.025)
                                {
                                    centsFromNote = getCents(51.91,pitchInHz);
                                    note = "G#";
                                }
                                else if (pitchInHz > 50.025 && pitchInHz == 56.635)
                                {
                                    centsFromNote = getCents(55.00,pitchInHz);
                                    note = "A";
                                }
                                else if (pitchInHz > 56.635 && pitchInHz == 60.00)
                                {
                                    centsFromNote = getCents(58.27,pitchInHz);
                                    note = "A#";
                                }
                                else if (pitchInHz > 60 && pitchInHz <= 63.565)
                                {
                                    centsFromNote = getCents(61.73,pitchInHz);
                                    note = "B";
                                }
                                else if (pitchInHz > 63.565 && pitchInHz <= 67.345)
                                {
                                    centsFromNote = getCents(65.40,pitchInHz);
                                    note = "C";
                                }
                                else if (pitchInHz > 67.345 && pitchInHz <= 71.35)
                                {
                                    centsFromNote = getCents(69.29,pitchInHz);
                                    note = "C#";
                                }
                                else if (pitchInHz > 71.35 && pitchInHz <= 75.595)
                                {
                                    centsFromNote = getCents(73.41,pitchInHz);
                                    note = "D";
                                }
                                else if (pitchInHz > 75.595 && pitchInHz <= 80.09)
                                {
                                    centsFromNote = getCents(77.78,pitchInHz);
                                    note = "D#";
                                }
                                else if (pitchInHz > 80.09 && pitchInHz <= 84.85)
                                {
                                    centsFromNote = getCents(82.40,pitchInHz);
                                    note = "E";
                                }
                                else if (pitchInHz > 84.85 && pitchInHz <= 89.895)
                                {
                                    centsFromNote = getCents(87.30,pitchInHz);
                                    note = "F";
                                }
                                else if (pitchInHz > 89.895 && pitchInHz <= 95.24)
                                {
                                    centsFromNote = getCents(92.49,pitchInHz);
                                    note = "F#";
                                }
                                else if (pitchInHz > 95.24 && pitchInHz <= 100.905)
                                {
                                    centsFromNote = getCents(97.99,pitchInHz);
                                    note = "G";
                                }
                                else if (pitchInHz > 100.905 && pitchInHz <= 106.91)
                                {
                                    centsFromNote = getCents(103.82,pitchInHz);
                                    note = "G#";
                                }
                                else if (pitchInHz > 106.91 && pitchInHz <= 113.27)
                                {
                                    centsFromNote = getCents(110.00,pitchInHz);
                                    note = "A";
                                }
                                else if (pitchInHz > 113.27 && pitchInHz == 120.005)
                                {
                                    centsFromNote = getCents(116.54,pitchInHz);
                                    note = "A#";
                                }
                                else if (pitchInHz > 120.005 && pitchInHz <= 127.14)
                                {
                                    centsFromNote = getCents(123.47,pitchInHz);
                                    note = "B";
                                }
                                else if (pitchInHz > 127.14 && pitchInHz <= 134.7)
                                {
                                    centsFromNote = getCents(130.81,pitchInHz);
                                    note = "C";
                                }
                                else if (pitchInHz > 134.7 && pitchInHz <= 142.71)
                                {
                                    centsFromNote = getCents(138.59,pitchInHz);
                                    note = "C#";
                                }
                                else if (pitchInHz > 142.71 && pitchInHz <= 151.195)
                                {
                                    centsFromNote = getCents(146.83,pitchInHz);
                                    note = "D";
                                }
                                else if (pitchInHz > 151.195 && pitchInHz <= 160.185)
                                {
                                    centsFromNote = getCents(155.56,pitchInHz);
                                    note = "D#";
                                }
                                else if (pitchInHz > 160.185 && pitchInHz <= 169.71)
                                {
                                    centsFromNote = getCents(164.81,pitchInHz);
                                    note = "E";
                                }
                                else if (pitchInHz > 169.71 && pitchInHz <= 179.8)
                                {
                                    centsFromNote = getCents(174.61,pitchInHz);
                                    note = "F";
                                }
                                else if (pitchInHz > 179.8 && pitchInHz <= 190.49)
                                {
                                    centsFromNote = getCents(184.99,pitchInHz);
                                    note = "F#";
                                }
                                else if (pitchInHz > 190.49 && pitchInHz <= 201.82)
                                {
                                    centsFromNote = getCents(195.99,pitchInHz);
                                    note = "G";
                                }
                                else if (pitchInHz > 201.82 && pitchInHz <= 213.825)
                                {
                                    centsFromNote = getCents(207.65,pitchInHz);
                                    note = "G#";
                                }
                                else if (pitchInHz > 213.825 && pitchInHz <= 226.54)
                                {
                                    centsFromNote = getCents(220.00,pitchInHz);
                                    note = "A";
                                }
                                else if (pitchInHz > 226.54 && pitchInHz <= 239.785)
                                {
                                    centsFromNote = getCents(233.08,pitchInHz);
                                    note = "A#";
                                }
                                else if (pitchInHz > 239.785 && pitchInHz <= 254.28)
                                {
                                    centsFromNote = getCents(246.94,pitchInHz);
                                    note = "B";
                                }
                                else if (pitchInHz > 254.8 && pitchInHz <= 269.4)
                                {
                                    centsFromNote = getCents(261.62,pitchInHz);
                                    note = "C";
                                }
                                else if (pitchInHz > 269.4 && pitchInHz <= 285.42)
                                {
                                    centsFromNote = getCents(277.18,pitchInHz);
                                    note = "C#";
                                }
                                else if (pitchInHz > 285.42 && pitchInHz <= 302.39)
                                {
                                    centsFromNote = getCents(293.66,pitchInHz);
                                    note = "D";
                                }
                                else if (pitchInHz > 302.39 && pitchInHz <= 320.37)
                                {
                                    centsFromNote = getCents(311.12,pitchInHz);
                                    note = "D#";
                                }
                                else if (pitchInHz > 320.37 && pitchInHz <= 339.42)
                                {
                                    centsFromNote = getCents(329.62,pitchInHz);
                                    note = "E";
                                }
                                else if (pitchInHz > 339.42 && pitchInHz <= 359.255)
                                {
                                    centsFromNote = getCents(349.22,pitchInHz);
                                    note = "F";
                                }
                                else if (pitchInHz > 359.255 && pitchInHz <= 380.99)
                                {
                                    centsFromNote = getCents(369.99,pitchInHz);
                                    note = "F#";
                                }
                                else if (pitchInHz == 391.99)
                                {
                                    centsFromNote = getCents(391.99,pitchInHz);
                                    note = "G";
                                }
                                else if (pitchInHz > 380.99 && pitchInHz <= 247.65)
                                {
                                    centsFromNote = getCents(415.30,pitchInHz);
                                    note = "G#";
                                }
                                else if (pitchInHz > 427.65 && pitchInHz <= 453.08)
                                {
                                    centsFromNote = getCents(440.00,pitchInHz);
                                    note = "A";
                                }
                                else if (pitchInHz > 453.08 && pitchInHz <= 493.88)
                                {
                                    centsFromNote = getCents(466.16,pitchInHz);
                                    note = "A#";
                                }
                                else if (pitchInHz > 493.88 && pitchInHz <= 508.565)
                                {
                                    centsFromNote = getCents(493.88,pitchInHz);
                                    note = "B";
                                }
                                else if (pitchInHz > 508.565 && pitchInHz <= 538.805)
                                {
                                    centsFromNote = getCents(523.25,pitchInHz);
                                    note = "B#";
                                }
                                else if (pitchInHz > 538.805 && pitchInHz <= 570.845)
                                {
                                    centsFromNote = getCents(554.36,pitchInHz);
                                    note = "C";
                                }
                                else if (pitchInHz > 570.845 && pitchInHz <= 604.79)
                                {
                                    centsFromNote = getCents(587.33,pitchInHz);
                                    note = "D";
                                }
                                else if (pitchInHz > 604.79 && pitchInHz <=640.75 )
                                {
                                    centsFromNote = getCents(622.25,pitchInHz);
                                    note = "D#";
                                }
                                else if (pitchInHz > 640.75 && pitchInHz <= 639.645)
                                {
                                    centsFromNote = getCents(659.25,pitchInHz);
                                    note = "E";
                                }
                                else if (pitchInHz > 639.645 && pitchInHz <= 719.225)
                                {
                                    centsFromNote = getCents(698.46,pitchInHz);
                                    note = "F";
                                }
                                else if (pitchInHz > 719.225 && pitchInHz <= 761.99)
                                {
                                    centsFromNote = getCents(739.99,pitchInHz);
                                    note = "F#";
                                }
                                else if (pitchInHz > 761.99 && pitchInHz <= 807.3)
                                {
                                    centsFromNote = getCents(783.99,pitchInHz);
                                    note = "G";
                                }
                                else if (pitchInHz > 807.33 && pitchInHz <= 855.305)
                                {
                                    centsFromNote = getCents(830.61,pitchInHz);
                                    note = "G#";
                                }
                                else if (pitchInHz > 855.305 && pitchInHz <= 906.165)
                                {
                                    centsFromNote = getCents(880.00,pitchInHz);
                                    note = "A";
                                }
                                else if (pitchInHz > 906.165 && pitchInHz <= 960.05)
                                {
                                    centsFromNote = getCents(932.33,pitchInHz);
                                    note = "A#";
                                }
                                else if (pitchInHz > 960.05 && pitchInHz <= 1017.135)
                                {
                                    centsFromNote = getCents(987.77,pitchInHz);
                                    note = "B";
                                }
                                else if (pitchInHz > 1017.135 && pitchInHz <= 1077.615)
                                {
                                    centsFromNote = getCents(1046.50,pitchInHz);
                                    note = "B#";
                                }
                                else if (pitchInHz > 1077.165 && pitchInHz <= 1141.695)
                                {
                                    centsFromNote = getCents(1108.73,pitchInHz);
                                    note = "C";
                                }
                                else if (pitchInHz > 1141.695 && pitchInHz <= 1209.585)
                                {
                                    centsFromNote = getCents(1174.66,pitchInHz);
                                    note = "D";
                                }
                                else if (pitchInHz > 1209.585 && pitchInHz <= 1281.51)
                                {
                                    centsFromNote = getCents(1244.51,pitchInHz);
                                    note = "D#";
                                }
                                else if (pitchInHz > 1281.51 && pitchInHz <= 1357.71)
                                {
                                    centsFromNote = getCents(1318.51,pitchInHz);
                                    note = "E";
                                }

                                pb.setProgress(50 - (int)Math.round(centsFromNote));

                                // tell computer here's the number, check what note it is
                                // computer should have a bunch of notes with values

                                // if pitchInHz < 16 then go higher
                                // if pitchInHz == 16.35 then note = C0
                                // if pitchInHz > 16 && < 17.32 then go lower

                                // class: note itself, range in between highest note and lowest note

                            }
                        });
                    }
                }));
        new Thread(dispatcher, "Audio Dispatcher").start();

    }

    public double getCents(double target, double current)
    {
        return 1200 * (Math.log10(target/current) / Math.log10(2));
    }

    public String genNote(float pitchInHz)
    {
        //HashMap<Float, String> notes = new HashMap<Float, String>();
        //notes.put(16.35160f, )
        HashMap<Integer, String> notes = new HashMap<Integer, String>();
        notes.put(0, "C\u2080");
        notes.put(1, "C#\u2080");

        return "";
    }
}