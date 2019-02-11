package com.example.mibrahiem.notes.activity.editor;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mibrahiem.notes.api.ApiClient;
import com.example.mibrahiem.notes.api.ApiInterface;
import com.example.mibrahiem.notes.model.Note;
import com.example.mibrahiem.notes.R;
import com.thebluealliance.spectrum.SpectrumPalette;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorActivity extends AppCompatActivity implements EditorView {
EditText et_title, et_note;
ProgressDialog progressDialog;
EditorPresenter presenter;
 SpectrumPalette palette;
int color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        et_title=findViewById(R.id.title);
        et_note=findViewById(R.id.note);
        palette=findViewById(R.id.palette);

        //chose color
        palette.setOnColorSelectedListener(
                clr -> color = clr
        );

        // default color

        palette.setSelectedColor(getResources().getColor(R.color.white));
        color = getResources().getColor(R.color.white);


        //progress dialog

        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Please Wait .");

        presenter=new EditorPresenter(this);

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
                int color = this.color;
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
                           presenter.saveNote(title, note, color);
                        }
                return true;

                default: return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onAddSuccess(String message) {

                        Toast.makeText(EditorActivity.this, message
                                , Toast.LENGTH_SHORT).show();

                        finish();


    }

    @Override
    public void onAddError(String message) {

        Toast.makeText(EditorActivity.this, message
                , Toast.LENGTH_SHORT).show();
    }
}
