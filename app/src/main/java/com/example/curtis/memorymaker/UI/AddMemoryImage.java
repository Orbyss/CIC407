package com.example.curtis.memorymaker.UI;

        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.drawable.BitmapDrawable;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;

        import com.example.curtis.memorymaker.Models.Memory;
        import com.example.curtis.memorymaker.R;

        import java.io.File;
        import java.io.IOException;

public class AddMemoryImage extends AppCompatActivity {
    private static int SELECT_PICTURE = 1;
    private static int REQUEST_IMAGE_CAPTURE = 1;

    private Memory mMemory;
    private String mSelectedImagePath;
    File mDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory_image);

        FloatingActionButton addTxtFab = (FloatingActionButton) findViewById(R.id.btn_continue_mem_txt);

        addTxtFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddMemoryText.class);
                intent.putExtra("MemoryData", mMemory);
                startActivity(intent);
            }
        });
    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    public void loadImagefromCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(takePictureIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                mSelectedImagePath = getPath(selectedImageUri);

                ImageView imgView = (ImageView) findViewById(R.id.pic_new_memory);
                // Set the Image in ImageView after decoding the String
                imgView.setImageURI(selectedImageUri);

                mMemory = new Memory(selectedImageUri.toString(), null, null, null);
            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            return path;
        }

        cursor.close();
        // this is our fallback here
        return uri.getPath();
    }

}

