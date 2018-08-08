package khosbayar.hs.com.droidpheramor.activities;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import khosbayar.hs.com.droidpheramor.R;
import khosbayar.hs.com.droidpheramor.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Step3Activity extends FragmentActivity {

    TextInputEditText mFullNameEditText, mZipEditText, mFeetEditText, mInchEditText;
    TextInputLayout mFullNameLayout, mZipLayout, mFeetLayout, mInchLayout;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3);


        mFullNameEditText = findViewById(R.id.tiet_full_name);
        mFullNameLayout = findViewById(R.id.til_full_name);
        mZipEditText = findViewById(R.id.tiet_zip);
        mZipLayout = findViewById(R.id.til_zip);
        mFeetEditText = findViewById(R.id.tiet_feet);
        mFeetLayout = findViewById(R.id.til_feet);
        mInchEditText = findViewById(R.id.tiet_inch);
        mInchLayout = findViewById(R.id.til_inch);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("User");

        if (user.getFullName() != null)
            mFullNameEditText.setText(user.getFullName());
        if (user.getZip() != null)
            mZipEditText.setText(user.getZip());
        if (user.getFeet() != null)
            mFeetEditText.setText(user.getFeet());
        if (user.getInch() != null)
            mInchEditText.setText(user.getInch());
    }

    public void goStep4(View view) {
        if (isInputValid(mFullNameEditText.getText())) {
            mFullNameLayout.setError("Full name can't be empty!");
            mFullNameEditText.requestFocus();
        } else if (isInputValid(mZipEditText.getText())) {
            mZipLayout.setError("Zip code can't be empty!");
            mZipEditText.requestFocus();
            mFullNameLayout.setError(null);
        } else if (isInputValid(mFeetEditText.getText())) {
            mFeetLayout.setError("Feet can't be empty!");
            mFeetEditText.requestFocus();
            mFullNameLayout.setError(null);
            mZipLayout.setError(null);
        } else if (isInputValid(mInchEditText.getText())) {
            mInchLayout.setError("Inch can't be empty!");
            mInchEditText.requestFocus();
            mFullNameLayout.setError(null);
            mZipLayout.setError(null);
            mFeetLayout.setError(null);
        } else {
            mFullNameLayout.setError(null);
            mZipLayout.setError(null);
            mFeetLayout.setError(null);
            mInchLayout.setError(null);

            user.setFeet(mFeetEditText.getText().toString());
            user.setFullName(mFullNameEditText.getText().toString());
            user.setInch(mInchEditText.getText().toString());
            user.setZip(mZipEditText.getText().toString());

            Intent i = new Intent(this, Step4Activity.class);
            i.putExtra("User", user);
            startActivity(i);

        }
    }

    private boolean isInputValid(@Nullable Editable text) {
        return text == null || text.length() <= 0;
    }
}
