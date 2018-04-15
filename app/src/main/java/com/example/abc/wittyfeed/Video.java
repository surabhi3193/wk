package com.example.abc.wittyfeed;

/**
 * Created by abc on 15/04/2018.
 */

public class Video {
    private String videoUrl;
    private String video_content;

    public Video(String videoUrl, String video_content) {
        this.videoUrl = videoUrl;
        this.video_content = video_content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideo_content() {
        return video_content;
    }

    public void setVideo_content(String video_content) {
        this.video_content = video_content;
    }
}
