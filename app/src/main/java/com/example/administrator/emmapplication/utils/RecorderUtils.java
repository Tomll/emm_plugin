package com.example.administrator.emmapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

/**
 * Created by admine on 2018/4/19.
 */

public class RecorderUtils {

    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mMediaPlayer;
    private static boolean isPlaying;

    public static void startRecording(String filePath) {
        LogUtils.d("filePath....................." + filePath);
        File file = new File(filePath);
        if (file.exists()) {//判断文件目录是否存在
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);   //录音文件保存格式，这里保存为mp4
        mediaRecorder.setOutputFile(filePath);                              //保存路径
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioChannels(1);
        mediaRecorder.setAudioSamplingRate(44100);
        mediaRecorder.setAudioEncodingBitRate(192000);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            LogUtils.d("media recorder prepare failed");
        }
    }

    public static void stopRecording() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    public static void play(Context context, String filePath) {
        if (!isPlaying) {
            if (mMediaPlayer == null) {
                startPlaying(filePath);
            } else {
                resumePlaying();
            }
        } else {
            pausePlaying();
        }
    }

    private static void startPlaying(String filePath) {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(filePath);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlayer.start();
                }
            });

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlaying();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d("media player prepare failed...................");
        }
    }

    private static void pausePlaying() {
        if (mMediaPlayer != null)
            mMediaPlayer.pause();
    }

    private static void resumePlaying() {
        if (mMediaPlayer != null)
            mMediaPlayer.start();
    }

    private static void stopPlaying() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;
        isPlaying = !isPlaying;
    }


}
