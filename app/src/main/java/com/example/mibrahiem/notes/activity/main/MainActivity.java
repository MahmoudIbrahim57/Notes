package com.example.mibrahiem.notes.activity.main;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.mibrahiem.notes.R;
import com.example.mibrahiem.notes.activity.editor.EditorActivity;
import com.example.mibrahiem.notes.model.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {
FloatingActionButton fab;
RecyclerView  recyclerView;
SwipeRefreshLayout swipeRefresh;
MainPresenter presenter;
MainAdapter adapter;
MainAdapter.ItemClickListener itemClickListener;
List<Note> note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab =findViewById(R.id.add);
        fab.setOnClickListener(view ->
        startActivity(new Intent(this,EditorActivity.class))

        );
        presenter =new MainPresenter(this);
        presenter.getData();
        swipeRefresh.setOnRefreshListener(
            () -> presenter.getData()
        );
        itemClickListener =((view, position) ->
        {

        String message=note.get(position).getTitle();
            Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();

        }
        );
    }

    @Override
    public void showLoading() {
swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Note> notes) {

        adapter =new MainAdapter(notes,this,itemClickListener);
        adapter.notifyDataSetChanged();

        recyclerView.setAdapter(adapter);
        note=notes;

    }

    @Override
    public void onErrorLoading(String message) {

    }
}
