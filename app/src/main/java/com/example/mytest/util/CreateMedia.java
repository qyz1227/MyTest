package com.example.mytest.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


import com.example.mytest.bean.MyImage;
import com.example.mytest.bean.MyMedia;
import com.example.mytest.bean.MyMusic;
import com.example.mytest.bean.MyVideo;

import java.util.List;

public class CreateMedia {


    public static void createMusicList(Context context,List<MyMusic> musicList) {

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        do {
            MyMusic music = new MyMusic();
            music.setType(MyMedia.MEDIA_MUSIC);
            music.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            music.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            music.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            music.setDataPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            music.setLength(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
            int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            //根据专辑ID获取到专辑封面图
            music.setCoverPath(getAlbumArt(albumId,context));

            musicList.add(music);
        } while (cursor.moveToNext());
        cursor.close();

    }

    private static String getAlbumArt(int album_id, Context context) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = context.getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)), projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        return album_art;
    }

//    //    转换歌曲时间的格式
    public static String formatTime(int time) {
        if (time / 1000 % 60 < 10) {
            String tt = time / 1000 / 60 + ":0" + time / 1000 % 60;
            return tt;
        } else {
            String tt = time / 1000 / 60 + ":" + time / 1000 % 60;
            return tt;
        }
    }

    public static void getList(Context context,List<MyVideo> videoList) {

        // MediaStore.Video.Thumbnails.DATA:视频缩略图的文件路径
        String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID};
        // 视频其他信息的查询条件
        String[] mediaColumns = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA, MediaStore.Video.Media.DURATION};

        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media
                        .EXTERNAL_CONTENT_URI,
                mediaColumns, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                MyVideo info = new MyVideo();
                int id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Video.Media._ID));
                Cursor thumbCursor = context.getContentResolver().query(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                        thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
                                + "=" + id, null, null);
                if (thumbCursor.moveToFirst()) {
                    info.setCoverPath(thumbCursor.getString(thumbCursor
                            .getColumnIndex(MediaStore.Video.Thumbnails.DATA)));
                }
                info.setDataPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media
                        .DATA)));
                info.setLength(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video
                        .Media.DURATION)));

                videoList.add(info);
            } while (cursor.moveToNext());
        }
    }

    public static void createImageList(Context context,List<MyImage> imageList) {

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        do {
            MyImage image = new MyImage();
            image.setType(MyMedia.MEDIA_MUSIC);
            image.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE)));
//            music.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
//            music.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            image.setDataPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
//            music.setLength(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
//            int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
//            //根据专辑ID获取到专辑封面图
            image.setCoverPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA)));

            imageList.add(image);
        } while (cursor.moveToNext());
        cursor.close();

    }

}
