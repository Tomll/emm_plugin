package com.example.administrator.emmapplication.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;


public class PlayAudioUtils {

    private static PlayAudioUtils playAudioUtils;
    private static MediaPlayer mMediaPlayer = null;

    public static PlayAudioUtils getInstance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        private static  final PlayAudioUtils INSTANCE = new PlayAudioUtils();
    }


    public void play(Context context,Uri uri){
        if(mMediaPlayer == null)
            mMediaPlayer = MediaPlayer.create(context, uri);
        mMediaPlayer.setLooping(true);
        try {
            mMediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            mMediaPlayer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stop(Context context,Uri uri){
        try {
            if(mMediaPlayer == null)
                mMediaPlayer = MediaPlayer.create(context,uri);
            if(mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(mMediaPlayer!=null) {
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }
    }
}
