package com.example.curtis.memorymaker;

        import android.app.Activity;
        import android.content.Intent;
        import android.database.Cursor;
        import android.graphics.BitmapFactory;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.Toast;

        import java.io.File;

public class add_memory_image extends AppCompatActivity {
    private static int SELECT_PICTURE = 1;
    private static int REQUEST_IMAGE_CAPTURE = 1;
    private String selectedImagePath;
    File destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory_image);


        FloatingActionButton addTxtFab = (FloatingActionButton) findViewById(R.id.btn_continue_mem_txt);
        addTxtFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), add_memory_text.class);;
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
                selectedImagePath = getPath(selectedImageUri);

                ImageView imgView = (ImageView) findViewById(R.id.pic_new_memory);
                // Set the Image in ImageView after decoding the String
                imgView.setImageURI(selectedImageUri);
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

