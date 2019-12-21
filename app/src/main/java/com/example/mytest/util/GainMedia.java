package com.example.mytest.util;

import com.example.mytest.bean.MyImage;
import com.example.mytest.bean.MyMedia;
import com.example.mytest.bean.MyMusic;
import com.example.mytest.bean.MyVideo;

import java.util.LinkedList;
import java.util.List;

public class GainMedia {

    private final List<Number> mediaList = new LinkedList<>();
    private final List<MyVideo> videoList = new LinkedList<>();
    private final List<MyMusic> musicList = new LinkedList<>();
    private final List<MyImage> imageList = new LinkedList<>();

    public class Number{
        public int type;
        public int index;
        public Number(){

        }
        public Number(int type, int index){
            this.type = type;
            this.index = index;
        }
    }

    private GainMedia(){ }

    public MyMedia get(int index){
        if( index<0 || index>=videoList.size() ){
            return null;
        } else {
            Number number = mediaList.get(index);
            switch (number.type){
                case MyMedia.MEDIA_VIDEO:{
                    return getVideo(number.index);
                }
                case MyMedia.MEDIA_IMAGE:{
                    return getImage(number.index);
                }
                case MyMedia.MEDIA_MUSIC:{
                    return getMusic(number.index);
                }
                default:{
                    return null;
                }
            }
        }
    }
    public MyVideo getVideo(int index) {
        if( index<0 || index>=videoList.size() ){
            return null;
        }
        return videoList.get(index);
    }

    public MyImage getImage(int index){
        if( index<0 || index>=imageList.size() ){
            return null;
        }
        return imageList.get(index);
    }

    public MyMusic getMusic(int index){
        if( index<0 || index>=musicList.size() ){
            return null;
        }
        return musicList.get(index);
    }

    public int add(MyMedia myMedia){
        int index = -1;
        switch (myMedia.getType()){
            case MyMedia.MEDIA_VIDEO:{
                index = addVideo((MyVideo)myMedia);
            }
            case MyMedia.MEDIA_IMAGE:{
                index = addImage((MyImage)myMedia);
            }
            case MyMedia.MEDIA_MUSIC:{
                index = addMusic((MyMusic)myMedia);
            }
            default:{
                index = -1;
            }
        }
        return index;
    }

    public int addVideo(MyVideo myVideo){
        if( myVideo != null ){
            videoList.add(myVideo);
            mediaList.add(new Number(MyMedia.MEDIA_VIDEO,videoList.size()-1));
            return videoList.size()-1;
        } else {
            return -1;
        }
    }

    public int addMusic(MyMusic myMusic){
        if( myMusic != null ){
            musicList.add(myMusic);
            mediaList.add(new Number(MyMedia.MEDIA_MUSIC,musicList.size()-1));
            return musicList.size()-1;
        } else {
            return -1;
        }
    }

    public int addImage(MyImage myImage){
        if( myImage != null ){
            imageList.add(myImage);
            mediaList.add(new Number(MyMedia.MEDIA_IMAGE,imageList.size()-1));
            return imageList.size()-1;
        } else {
            return -1;
        }
    }

}
