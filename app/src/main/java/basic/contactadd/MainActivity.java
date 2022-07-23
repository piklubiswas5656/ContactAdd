package basic.contactadd;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import basic.contactadd.Adapter.ContacAdapter;
import basic.contactadd.Model.CDel;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private EditText nameEdit, phoneEdit;
    private ImageView imageView;
    private Button btnAdd, btnUpload;
    ArrayList<CDel> data = new ArrayList<>();
    Dialog dialog;
    private ContacAdapter adapter;

    //use on result
    private Uri imageUri;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniit();
        onClick();
    }

    private void iniit() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        floatingActionButton = findViewById(R.id.floating_button);
//        storeDate();


        Cursor cursor = new Dbmanager(this).readable();

        while (cursor.moveToNext()) {
            byte[] bitmapp = cursor.getBlob(3);
            Bitmap imag = BitmapFactory.decodeByteArray(bitmapp, 0, bitmapp.length);
            CDel obj = new CDel(cursor.getString(1), cursor.getString(2), imag);
            data.add(obj);
//            adapter.notifyItemInserted(data.size() - 1);
//            recyclerView.scrollToPosition(data.size() - 1);
        }
        adapter = new ContacAdapter(data, getApplicationContext());
        recyclerView.setAdapter(adapter);

    }

    private void onClick() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.update_contact);
                nameEdit = dialog.findViewById(R.id.name);
                phoneEdit = dialog.findViewById(R.id.phoneNumber);
                btnUpload = dialog.findViewById(R.id.upload_image);
                btnAdd = dialog.findViewById(R.id.addNumber);
                imageView = dialog.findViewById(R.id.image_view);
                btnUpload.setOnClickListener(view1 -> {
                  /*  Intent iGallery = new Intent(Intent.ACTION_PICK);
                    iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(iGallery, PICK_IMAGE);
*/
                    ImagePicker.with(MainActivity.this)
                            .galleryOnly()
                            .crop()
                            .cropSquare()
                            .compress(1024)            //Final image size will be less than 1 MB(Optional)
                            .maxResultSize(512, 512)    //Final image resolution will be less than 1080 x 1080(Optional)
                            .start();


                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = nameEdit.getText().toString();
                        String phone = phoneEdit.getText().toString();
                      /*  //for drawable
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.man);
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                        byte[] img = byteArray.toByteArray();*/


                        //for gallery
                        //TODO:image form galley to sql database
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                        byte[] img = byteArray.toByteArray();

                        String res = new Dbmanager(getApplicationContext()).addrecord(name, phone, img);
                        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();

                        Cursor cursor = new Dbmanager(getApplicationContext()).readable();
                        cursor.moveToLast();
                        //for imagefind
                        byte[] bitmapp = cursor.getBlob(3);
                        Bitmap imag = BitmapFactory.decodeByteArray(bitmapp, 0, bitmapp.length);


                        CDel obj = new CDel(cursor.getString(1), cursor.getString(2), imag);
                        data.add(obj);
                        adapter.notifyItemInserted(data.size() - 1);
                        recyclerView.scrollToPosition(data.size() - 1);


                        dialog.dismiss();
                    }
                });
                dialog.show();

/*
                String name = "joll";
                String phone = "9005055555";
                data.add(new CDel(name, phone));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);*/


            }
        });


     /*   floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.update_contact);
                nameEdit = dialog.findViewById(R.id.name);
                phoneEdit = dialog.findViewById(R.id.phoneNumber);
                add = dialog.findViewById(R.id.addNumber);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = nameEdit.getText().toString();
                        String phone = phoneEdit.getText().toString();
                        data.add(new CDel(name, phone));
                        adapter.notifyItemInserted(data.size() - 1);
                        recyclerView.scrollToPosition(data.size() - 1);
                        dialog.dismiss();
                    }
                });
                dialog.show();


//                String name = "joll";
//                String phone = "9005055555";
//                data.add(new CDel(name, phone));
//                adapter.notifyItemInserted(data.size() - 1);
//                recyclerView.scrollToPosition(data.size() - 1);

                Toast.makeText(getApplicationContext(), "text", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RESULT_OK) {
//            if (requestCode == PICK_IMAGE) {
//                //TODO: action
//                imageView.setImageURI(data.getData());
//            }
//        }
        imageUri = data.getData();
        imageView.setImageURI(imageUri);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}