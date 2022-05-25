package com.example.pushkinapp.view;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pushkinapp.R;
import com.example.pushkinapp.databinding.ExpoPageBinding;
import com.example.pushkinapp.viewmodel.MuseumSharedViewModel;

/**
 * The type Expo page fragment.
 */
public class ExpoPageFragment extends Fragment {

    private ExpoPageBinding expoPageBinding;
    private Button backButton;

    private SeekBar seekBar;                    //  Progress bar
    private Object obj = new Object();      //  Object lock. When the playback thread is paused, let the progress bar thread enter the waiting state.
    private Thread seekThread;            //    thread
    private boolean isRun = false;      //  Progress bar thread control
    private boolean suspended = false;  //  Progress bar thread wait state control

    private Button pauseButton;
    private Button playButton;

    private int receivedAudio;

    private TextView startTime, endTime;

    /**
     * The Music.
     */
    MediaPlayer music = null;

    /**
     * Instantiates a new Expo page fragment.
     */
    public ExpoPageFragment(){

    }

    @Override
    public void onStop() {
        pause();
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        expoPageBinding = DataBindingUtil.inflate(inflater, R.layout.expo_page, container, false);
        seekBar = expoPageBinding.audioLine;

        startTime = expoPageBinding.startTimeIndicator;
        endTime = expoPageBinding.finishTimeIndicator;

        backButton = expoPageBinding.backButton;
        backButton.setOnClickListener(
                v -> {
                    getActivity().onBackPressed();
                    stop();
                });

        pauseButton = expoPageBinding.pauseButton;
        pauseButton.setOnClickListener(
                v -> {
                    pause();
                }
        );
        playButton = expoPageBinding.playButton;
        setupPlayer();
        playButton.setOnClickListener(
                v -> {
                    if (music == null){           //  If there is no pause
                        play();     //  Call play method to play
                    }else{
                        if (!music.isPlaying()){             //   If it is paused
                            int progress = seekBar.getProgress();   //  Get the progress of SeekBar
                            int currentPosition = music.getCurrentPosition();     //  Get current location
                            startTime.setText(getAudioFileLength(currentPosition));
                            continuePlay(progress,currentPosition);      //  It is used when resuming playing from the paused state. In addition to continuing playing music, the waiting progress bar drawing thread needs to be woken up.
                        }
                    }
                });

        return expoPageBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MuseumSharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(MuseumSharedViewModel.class);
        System.out.println(viewModel.getSelectedExpo().getValue());
        viewModel.getSelectedExpo().observe(getViewLifecycleOwner(), item -> {
            expoPageBinding.expoPic.setImageResource(item.getImgid());
            expoPageBinding.expoName.setText(item.getTitle());
            expoPageBinding.expoInfoMain.setText(item.getInfoText());
            receivedAudio = item.getSound();
        });
    }

    private void setupPlayer(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {              //  Listening event for progress bar
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pause();        //  Before the progress bar starts, call pause()
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {              //  When the progress bar starts, call pause()
                if (music != null && !music.isPlaying()){
                    int progress2 = seekBar.getProgress();
                    int currentPosition2 = music.getDuration()*progress2/100;
                    continuePlay(progress2,currentPosition2);
                }
            }
        });
    }

    /**
     * The type Seek thread.
     */
    class SeekThread extends Thread{
        /**
         * The Duration.
         */
        int duration = music.getDuration();   //Total length of current music
        /**
         * The Current position.
         */
        int currentPosition = 0;
        public void run(){
            while (isRun){
                currentPosition = music.getCurrentPosition(); //  Get where the current music is playing
                seekBar.setProgress(currentPosition*100 / duration);
                try {
                    Thread.sleep(300);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startTime.setText(getAudioFileLength(currentPosition));
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (Exception ex)
                {
                    ex.getSuppressed();
                }
                synchronized (obj){
                    while (suspended){
                        try {
                            obj.wait();     //  Pause progress bar thread when music pauses
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (currentPosition == duration) {
                    isRun = false;
                }
            }
        }
    }

    private void play() {
        music = MediaPlayer.create(getActivity().getApplicationContext(), receivedAudio);    //  It can be understood directly as getting the audio resource file.
        endTime.setText("/"+getAudioFileLength(music.getDuration()));
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {    //  End of listening and playing events (stop, pause, play a piece of Music)
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (music != null){           //  In case of suspension
                    stop();             //  Call pause method
                }
            }
        });
        isRun = true;                 //  Progress bar thread control, ture is to pause the drawing of progress bar thread
        seekThread = new SeekThread();  //  Instantiate a thread object and start to work
        music.start();            //  Start music
        seekThread.start();             //  Startup thread
    }

    /**
     * Stop.
     */
    public void stop() {
        if (music != null){        //     As long as there are resources
            seekBar.setProgress(0);      //     Progress bar back to 0
            isRun = false;               //     thread
            music.stop();          //     Stop playing music
            music.release();       //     Release resources
            music = null;          //     Destroy music object
            seekThread = null;           //     Destruction thread
        }
    }

    /**
     * Pause.
     */
    public void pause() {
        if (music != null && music.isPlaying()){    //  If the music object has resources and the music is playing
            music.pause();         //     suspend
            suspended = true;           //  Progress bar thread wait state control
        }
    }

    //  It is used when resuming playing from the paused state. In addition to continuing playing music, the waiting progress bar drawing thread needs to be woken up.
    private void continuePlay(int progress, int currentPosition) {
        music.seekTo(currentPosition);           //   Jump to the corresponding time to play
        music.start();                //  Start (pause is also a start, just like playing, but pause is to return to the previous position to continue playing)
        seekBar.setProgress(progress);      //  Set it back to the previous position. This progress is the progress played before!
        suspended = false;               //  Progress bar thread wait state control
        synchronized (obj){           //  Object lock. When the playback thread is paused, let the progress bar thread enter the waiting state.
            obj.notify();       //  Wake up thread and start drawing progress bar
        }
    }

    /**
     * Gets audio file length.
     *
     * @param duration the duration
     * @return the audio file length
     */
    public String getAudioFileLength(int duration) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            int millSecond = duration;
            if (millSecond < 0) return String.valueOf(0); // if some error then we say duration is zero
            int hours, minutes, seconds = millSecond / 1000;
            hours = (seconds / 3600);
            minutes = (seconds / 60) % 60;
            seconds = seconds % 60;
            if (hours > 0 && hours < 10) stringBuilder.append("0").append(hours).append(":");
            else if (hours > 0) stringBuilder.append(hours).append(":");
            if (minutes < 10) stringBuilder.append("0").append(minutes).append(":");
            else stringBuilder.append(minutes).append(":");
            if (seconds < 10) stringBuilder.append("0").append(seconds);
            else stringBuilder.append(seconds);
        }catch (Exception e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}

