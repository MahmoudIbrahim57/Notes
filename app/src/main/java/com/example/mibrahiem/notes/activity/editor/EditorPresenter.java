package com.example.mibrahiem.notes.activity.editor;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.mibrahiem.notes.api.ApiClient;
import com.example.mibrahiem.notes.api.ApiInterface;
import com.example.mibrahiem.notes.model.Note;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPresenter {

    private  EditorView view;

    public EditorPresenter(EditorView view) {
        this.view = view;
    }

     void saveNote(final  String title,final String note,final int color) {
        //presener go to Model(Note)
            view.showProgress();
       ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Note> call = apiInterface.saveNote(title,note,color);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(@NonNull Call<Note> call, @NonNull Response<Note> response) {
                view.hideProgress();
                if(response.isSuccessful() && response.body() != null){
                    Boolean success=response.body().getSuccess();
                    if(success){
                        view.onAddSuccess(response.body().getMessage());
                    }
                    else {
                        view.onAddError(response.body().getMessage());

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Note> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onAddError(t.getLocalizedMessage());

            }
        });

    }
}
