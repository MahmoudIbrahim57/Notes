package com.example.mibrahiem.notes.activity.editor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mibrahiem.notes.R;
import com.thebluealliance.spectrum.SpectrumPalette;

public class EditorActivity extends AppCompatActivity implements EditorView {
EditText et_title, et_note;
ProgressDialog progressDialog;
EditorPresenter presenter;
 SpectrumPalette palette;
int color,id;
String title,note;
Menu actionMenue;
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

        //progress dialog
        progressDialog =new ProgressDialog(this);
        progressDialog.setMessage("Please Wait .");

        presenter=new EditorPresenter(this);
        Intent intent=getIntent();
             id  = intent.getIntExtra("id" ,0);
             title  = intent.getStringExtra("title");
             note  = intent.getStringExtra("notes");
             color  = intent.getIntExtra("color",0);
             setDataFromIntentExtra();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater Inflater=getMenuInflater();
        Inflater.inflate(R.menu.menue_editor,menu);
        actionMenue =menu;
        if(id != 0){

            actionMenue.findItem(R.id.edit).setVisible(true);
            actionMenue.findItem(R.id.delete).setVisible(true);
            actionMenue.findItem(R.id.save).setVisible(false);
            actionMenue.findItem(R.id.update).setVisible(false);
        }
        else{
            actionMenue.findItem(R.id.edit).setVisible(false);
            actionMenue.findItem(R.id.delete).setVisible(false);
            actionMenue.findItem(R.id.save).setVisible(true);
            actionMenue.findItem(R.id.update).setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title=et_title.getText().toString().trim();
        String note=et_note.getText().toString().trim();
        int color = this.color;
        switch (item.getItemId()){
            case R.id.save :

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

            case R.id.edit :
                editMode();
                actionMenue.findItem(R.id.edit).setVisible(false);
                actionMenue.findItem(R.id.delete).setVisible(false);
                actionMenue.findItem(R.id.save).setVisible(false);
                actionMenue.findItem(R.id.update).setVisible(true);
                return true;

            case R.id.update :

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
                    //update method
                    presenter.updateNote(id,title,note,color);

                                 }
                return true;

            case R.id.delete:
                AlertDialog.Builder dialog= new AlertDialog.Builder(this);
                dialog.setTitle("Confirm");
                dialog.setMessage("are you sure !");
                dialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    presenter.deleteNode(id);
                    }
                });
                dialog.setPositiveButton("Cansel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                    dialog.show();
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
    public void onRequestSuccess(String message) {

                        Toast.makeText(EditorActivity.this, message
                                , Toast.LENGTH_SHORT).show();

                        finish();


    }

    @Override
    public void onRequestError(String message) {

        Toast.makeText(EditorActivity.this, message
                , Toast.LENGTH_SHORT).show();
    }
            private void setDataFromIntentExtra() {

        if(id!=0){
            et_title.setText(title);
            et_note.setText(note);
             palette.setSelectedColor(color);
            getSupportActionBar().setTitle("Update Note");
            readMode();
        }else
        {

            palette.setSelectedColor(getResources().getColor(R.color.white));
            color = getResources().getColor(R.color.white);
            editMode();
            

        }

            }

    private void editMode() {
        et_note.setFocusableInTouchMode(true);
        et_title.setFocusableInTouchMode(true);
        palette.setEnabled(true);
    }

    private void readMode() {
        et_note.setFocusableInTouchMode(false);
        et_title.setFocusableInTouchMode(false);
        et_note.setFocusable(false);
        et_title.setFocusable(false);
        palette.setEnabled(false);
    }

}
