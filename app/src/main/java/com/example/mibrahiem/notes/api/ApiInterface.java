package com.example.mibrahiem.notes.api;

import com.example.mibrahiem.notes.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
            @FormUrlEncoded
            @POST("save.php")

    Call<Note>saveNote(

            @Field("title") String title,
            @Field("note") String note,
            @Field("color") int color

            );

    @POST("notes.php")
    Call<List<Note>> getNotes();

}
