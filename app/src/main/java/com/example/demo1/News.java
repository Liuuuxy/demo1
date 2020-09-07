package com.example.demo1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class News {
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("passtime")
    @Expose
    private String passtime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("header")
    @Expose
    private String header;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("video")
    @Expose
    private String video;

    public String getPasstime() {
        return passtime;
    }

    public void setPasstime(String passtime) {
        this.passtime = passtime;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
