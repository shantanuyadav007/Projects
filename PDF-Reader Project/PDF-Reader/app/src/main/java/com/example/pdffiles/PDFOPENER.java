package com.example.pdffiles;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class PDFOPENER extends AppCompatActivity {
PDFView myPDFViewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfopener);
        myPDFViewer=(PDFView) findViewById(R.id.pdfviewer);
        String getItem=getIntent().getStringExtra("pdfFilename");

        if(getItem.equals("1.Mypdf")){
            myPDFViewer.fromAsset("html.pdf").load();

        }if(getItem.equals("2.Project")){
            myPDFViewer.fromAsset("projectfinal.pdf").load();

        }
//        if(getItem.equals("3.Presentation")){
//            myPDFViewer.fromAsset("html.pdf").load();
//
//        }
//        if(getItem.equals("4.Interview Skills")){
//            myPDFViewer.fromAsset("Interview Skills.pdf").load();
//
//        }
        if(getItem.equals("3.Skill Report")){
            myPDFViewer.fromAsset("skill report.pdf").load();

        }
//        if(getItem.equals("6.My pdf")){
//            myPDFViewer.fromAsset("my d.pdf").load();
//
//        }


    }
}