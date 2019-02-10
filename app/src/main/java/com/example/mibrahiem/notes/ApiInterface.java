package com.example.mibrahiem.notes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
            @POST("save.php")
            @FormUrlEncoded

    Call<Note>saveNote(

            @Field("title") String title,
            @Field("note") String note,
            @Field("color") int color

            );

}
