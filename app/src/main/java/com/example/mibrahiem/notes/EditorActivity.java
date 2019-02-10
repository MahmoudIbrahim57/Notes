package com.example.mibrahiem.notes;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorActivity extends AppCompatActivity {
EditText et_title, et_note;
ProgressDialog progressDialog;
ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        et_title=findViewById(R.id.title);
        et_note=findViewById(R.id.note);
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Please Wait .");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater=getMenuInflater();
        Inflater.inflate(R.menu.menue_editor,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.save :
                String title=et_title.getText().toString().trim();
                String note=et_note.getText().toString().trim();
                int color = -2184710;
                if(title.isEmpty())
                {
                    et_title.setError("Enter The Title");
                }
                    else
                        if(note.isEmpty())

                            {
                                et_note.setError("Enter The Note");
                        }
                    else {
                            saveNote(title, note, color);
                        }
                return true;

                default: return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote(final  String title,final String note,final int color) {

        progressDialog.show();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Note> call = apiInterface.saveNote(title,note,color);
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(@NonNull Call<Note> call, @NonNull Response<Note> response) {
                progressDialog.dismiss();
                if(response.isSuccessful() && response.body() != null){
                Boolean success=response.body().getSuccess();
                if(success){
                    Toast.makeText(EditorActivity.this, ""+response
                            .body().getMessage()
                            , Toast.LENGTH_SHORT).show();

                    finish();
                }
                else {
                    Toast.makeText(EditorActivity.this, ""+response
                                    .body().getMessage()
                            , Toast.LENGTH_SHORT).show();

                 }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Note> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorActivity.this,  t.getLocalizedMessage()
                        , Toast.LENGTH_LONG).show();
              }
        });

    }

}
