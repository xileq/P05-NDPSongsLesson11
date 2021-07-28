package com.myapplicationdev.android.p05_ndpsongs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

	ListView lv;
    ArrayList<Song> songList;
	//ArrayAdapter<Song> adapter;
	String moduleCode;
    Button btn5Stars;
    CustomAdapter customAdapter;

    ArrayList<String> years;
    Spinner spinner;
    ArrayAdapter<String> spinnerAdapter;


    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(this);
        songList.clear();
        songList.addAll(dbh.getAllSongs());
        customAdapter.notifyDataSetChanged();

        years.clear();
        years.addAll(dbh.getYears());
//        songList.add(new Song(1,"Home","Dick Lee",1998,5));
//        songList.add(new Song(2,"Singapore Town","The Sidaislers",1997,5));
//        songList.add(new Song(3,"Singapore Town part 2","The Sidaislers",2020,3));
        spinnerAdapter.notifyDataSetChanged();

    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);
        spinner = (Spinner) this.findViewById(R.id.spinnerYear);

        DBHelper dbh = new DBHelper(this);
        songList = dbh.getAllSongs();
        years = dbh.getYears();
        dbh.close();

        customAdapter = new CustomAdapter(this, R.layout.row, songList);

        lv.setAdapter(customAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("song", songList.get(position));
                startActivity(i);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                songList.clear();
                songList.addAll(dbh.getAllSongsByStars(5));
                customAdapter.notifyDataSetChanged();
            }
        });

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, years);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                songList.clear();
                songList.addAll(dbh.getAllSongsByYear(Integer.valueOf(years.get(position))));
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
