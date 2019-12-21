package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    ListView mylist;
    List<Song> list;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mylist = (ListView) findViewById(R.id.mylist);

        list = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(Main3Activity.this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Main3Activity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},1);
        }
        if (ContextCompat.checkSelfPermission(Main3Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Main3Activity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

        list = Utils.getmusic(this);

        MyAdapter myAdapter = new MyAdapter(this, list);
        mylist.setAdapter(myAdapter);

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String p = list.get(i).path;//获得歌曲的地址
                play(p);
            }
        });

    }


    class MyAdapter extends BaseAdapter {

        Context context;
        List<Song> list;

        public MyAdapter(Main3Activity mainActivity, List<Song> list) {
            this.context = mainActivity;
            this.list = list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            Myholder myholder;

            if (view == null) {
                myholder = new Myholder();
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.test, null);

                myholder.t_position = view.findViewById(R.id.t_postion);
                myholder.t_song = view.findViewById(R.id.t_song);
                myholder.t_singer = view.findViewById(R.id.t_singer);
                myholder.t_duration = view.findViewById(R.id.t_duration);

                view.setTag(myholder);

            } else {
                myholder = (Myholder) view.getTag();
            }

            myholder.t_song.setText(list.get(i).song);
            myholder.t_singer.setText(list.get(i).singer);
            String time = Utils.formatTime(list.get(i).duration);

            myholder.t_duration.setText(time);
            myholder.t_position.setText(i + 1 + "");


            return view;
        }


        class Myholder {
            TextView t_position, t_song, t_singer, t_duration;
        }


    }

    public void play(String path) {

        try {
            //        重置音频文件，防止多次点击会报错
            mediaPlayer.reset();
//        调用方法传进播放地址
            mediaPlayer.setDataSource(path);
//            异步准备资源，防止卡顿
            mediaPlayer.prepareAsync();
//            调用音频的监听方法，音频准备完毕后响应该方法进行音乐播放
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
