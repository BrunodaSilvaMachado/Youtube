package com.cursoandroid.youtube.api;

import com.cursoandroid.youtube.model.Results;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {
    /*https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&maxResults=20&key=AIzaSyBMTIGZ_XhCN7Q7zn6BiGxQT_jb2wfnGo0&channelId=UCVHFbqXqoYvEWM1Ddxl0QDg*/
    @GET("search")
    Call<Results> recuperarVideos(@Query("part") String part, @Query("order") String order,
                                  @Query("maxResults") String maxResults, @Query("key") String key,
                                  @Query("channelId") String channelId, @Query("q") String q);
}
