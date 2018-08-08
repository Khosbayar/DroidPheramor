package khosbayar.hs.com.droidpheramor.activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import khosbayar.hs.com.droidpheramor.R;
import khosbayar.hs.com.droidpheramor.models.User;

public class Step7Activity extends FragmentActivity {

    ImageView mProfilePicture;
    TextView mProfilePath;
    User user;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 3434;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;
    //Bitmap to get image from gallery
    private Bitmap bitmap;
    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step7);

        mProfilePicture = findViewById(R.id.iv_profile_picture);
        mProfilePath = findViewById(R.id.tv_profile_path);

//        user = ViewModelProviders.of(this).get(UserViewModel.class);
        Intent intent = getIntent();
        user = intent.getParcelableExtra("User");

        if (user.getImg() != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), user.getImg());
                mProfilePicture.setImageBitmap(bitmap);
                mProfilePath.setText(getPath(user.getImg()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Snackbar.make(findViewById(R.id.cl_step7), "Sorry, we need permission to do that.", Snackbar.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    public void goStepConfirm(View view) {
        if (filePath != null) {
            user.setImg(filePath);
            Intent i = new Intent(this, StepConfirm.class);
            i.putExtra("User", user);
            startActivity(i);
        } else {
            Snackbar.make(view, "Please upload your profile picture.", Snackbar.LENGTH_SHORT).show();

        }
    }

    public void uploadImage(View view) {
        requestStoragePermission();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mProfilePicture.setImageBitmap(bitmap);
                mProfilePath.setText(getPath(filePath));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(findViewById(R.id.cl_step7), "Permission granted now you can read the storage.", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(findViewById(R.id.cl_step7), "Oops you just denied the permission.", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        assert cursor != null;
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
}
