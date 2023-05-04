package com.example.pdffiles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ListView pdflistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pdflistView = (ListView) findViewById(R.id.PDFLIST);

        String[] pdffiles = {"1.Mypdf","2.Project","3.Skill Report"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pdffiles) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView myText = (TextView) view.findViewById(android.R.id.text1);
                return view;
            }
        };
    pdflistView.setAdapter(adapter);
    pdflistView.setOnItemClickListener((parent, view, position, id) -> {
        String item=pdflistView.getItemAtPosition(position).toString();
        Intent start=new Intent(getApplicationContext(),PDFOPENER.class);
        start.putExtra("pdfFilename",item);
        startActivity(start);
    });
    }
}