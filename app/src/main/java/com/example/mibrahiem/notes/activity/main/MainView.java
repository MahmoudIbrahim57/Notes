package com.example.mibrahiem.notes.activity.main;

import com.example.mibrahiem.notes.model.Note;

import java.util.List;

public interface MainView {
    void showLoading();
    void hideLoading();
    void onGetResult(List<Note> notes);
    void onErrorLoading(String message);
}
