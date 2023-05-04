package com.example.goldenproject;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {
Button upload;
    private static final int PICK_VIDEO_REQUEST = 1;
    private static final int MANAGE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 101;
    private static final String MANAGE_EXTERNAL_STORAGE = "android.permission.MANAGE_EXTERNAL_STORAGE";

    String  EXTRACTED_AUDIO_FILE_PATH  = Environment.getExternalStorageDirectory().getAbsolutePath() + "/extracted_audio.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upload=findViewById(R.id.upload);
        if (shouldAskPermissions()) {
            askPermissions();
        }

        upload.setOnClickListener(atul->{
                selectVideoFile();
        });


    }
    private void selectVideoFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent, PICK_VIDEO_REQUEST);
    }
    private void requestManageExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        } else {
            // On Android 10 and below, this permission is granted automatically
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                requestManageExternalStoragePermission();
                extractAudioFromVideo(uri);
                // Do something with the video file path
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void extractAudioFromVideo(Uri videoUri) {
        try {
            // Create a new MediaExtractor and set its data source to the video file
            MediaExtractor extractor = new MediaExtractor();
            extractor.setDataSource(getApplicationContext(), videoUri, null);

            // Find the first audio track in the video file
            int audioTrackIndex = -1;
            for (int i = 0; i < extractor.getTrackCount(); i++) {
                MediaFormat format = extractor.getTrackFormat(i);
                String mime = format.getString(MediaFormat.KEY_MIME);
                if (mime.startsWith("audio/")) {
                    audioTrackIndex = i;
                    break;
                }
            }

            if (audioTrackIndex == -1) {
                // No audio track found in the video file
                return;
            }

            // Select the audio track in the extractor
            extractor.selectTrack(audioTrackIndex);

            // Create a new MediaMuxer and add a new audio track to it
            MediaMuxer muxer = new MediaMuxer(EXTRACTED_AUDIO_FILE_PATH, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int audioTrack = muxer.addTrack(extractor.getTrackFormat(audioTrackIndex));

            // Start the muxer and write the audio data to the new file
            muxer.start();
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            int sampleSize;
            while ((sampleSize = extractor.readSampleData(buffer, 0)) > 0) {
                bufferInfo.offset = 0;
                bufferInfo.size = sampleSize;
                bufferInfo.presentationTimeUs = extractor.getSampleTime();
                bufferInfo.flags = MediaCodec.BUFFER_FLAG_KEY_FRAME;
                muxer.writeSampleData(audioTrack, buffer, bufferInfo);
                extractor.advance();
            }

            // Stop and release the muxer and extractor
            muxer.stop();
            muxer.release();
            extractor.release();
            Toast.makeText(this,"Audio Extracted Successfully",Toast.LENGTH_SHORT).show();
            // Audio extraction complete, the new audio file is stored at EXTRACTED_AUDIO_FILE_PATH
        } catch (IOException e) {
            Log.d("error",e.getMessage());
            Toast.makeText(this,"Error\n"+e.getMessage(),Toast.LENGTH_SHORT).show();

        }
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }







}