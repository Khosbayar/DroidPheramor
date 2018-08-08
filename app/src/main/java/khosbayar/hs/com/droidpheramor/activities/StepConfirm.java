package khosbayar.hs.com.droidpheramor.activities;

import androidx.fragment.app.FragmentActivity;
import khosbayar.hs.com.droidpheramor.R;
import khosbayar.hs.com.droidpheramor.models.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.MessageFormat;

public class StepConfirm extends FragmentActivity {

    User user;

    ImageView mProfilePicture;
    TextView mEmail, mFullName, mZip, mHeight, mGender, mDob, mInterested, mAgeRange, mRace, mReligion;

    protected Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_confirm);

        mProfilePicture = findViewById(R.id.iv_profile_picture);
        mEmail = findViewById(R.id.tv_email);
        mFullName = findViewById(R.id.tv_full_name);
        mZip = findViewById(R.id.tv_zip);
        mHeight = findViewById(R.id.tv_height);
        mGender = findViewById(R.id.tv_gender);
        mDob = findViewById(R.id.tv_dob);
        mInterested = findViewById(R.id.tv_interested);
        mAgeRange = findViewById(R.id.tv_age_range);
        mRace = findViewById(R.id.tv_race);
        mReligion = findViewById(R.id.tv_religion);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("User");


        populateUserInfo();
    }

    private void populateUserInfo() {
        if (user.getImg() != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), user.getImg());
                mProfilePicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mEmail.setText(user.getEmail());
        mFullName.setText(user.getFullName());
        mZip.setText(user.getZip());
        mHeight.setText(MessageFormat.format("{0}{1}{2}", user.getFeet(), getString(R.string.height_separator), user.getInch()));
        mGender.setText(user.getGender() == 1 ? "Male" : "Female");
        mDob.setText(user.getDob());
        if (user.isInterestMale() && user.isInterestFemale())
            mInterested.setText(R.string.both_label);
        else if (user.isInterestFemale() || user.isInterestMale())
            mInterested.setText(user.isInterestFemale() ? "Female" : "Male");
        mAgeRange.setText(MessageFormat.format("From {0} to {1}", user.getAgeRangeMin(), user.getAgeRangeMax()));

        mRace.setText(getResources().getStringArray(R.array.race_array)[user.getRace()]);
        mReligion.setText(getResources().getStringArray(R.array.religion_array)[user.getReligion()]);
    }

    public void send(View view) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        try {
            sendRequestToApi(json);
        } catch (IOException e) {
            e.printStackTrace();
            Snackbar.make(view, "Something bad happened!", Snackbar.LENGTH_SHORT).show();
        }
    }


    void sendRequestToApi(String json) throws IOException {
        final MediaType JSON
                = MediaType.parse(getString(R.string.media_type_string));
        final String url = getString(R.string.api_server_url);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                assert response.body() != null;
                final String myResponse = response.body().string();

                StepConfirm.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(findViewById(R.id.cl_step_confirm), myResponse, Snackbar.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

}
