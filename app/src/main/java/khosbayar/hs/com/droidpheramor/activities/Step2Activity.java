package khosbayar.hs.com.droidpheramor.activities;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import khosbayar.hs.com.droidpheramor.R;
import khosbayar.hs.com.droidpheramor.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Step2Activity extends FragmentActivity {


    TextInputLayout mPasswordLayout, mPasswordConfirmLayout;
    TextInputEditText mPasswordEditText, mPasswordConfirmEditText;

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("User");
        mPasswordConfirmEditText = findViewById(R.id.tiet_password_conf);
        mPasswordConfirmLayout = findViewById(R.id.til_password_conf);
        mPasswordEditText = findViewById(R.id.tiet_password);
        mPasswordLayout = findViewById(R.id.til_password);

        mPasswordConfirmEditText.clearFocus();

        mPasswordConfirmEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(mPasswordConfirmEditText.getText()) && arePasswordsSame(mPasswordConfirmEditText.getText(), mPasswordEditText.getText())) {
                    mPasswordConfirmLayout.setError(null);
                }
                return false;
            }
        });

        mPasswordEditText.clearFocus();

        mPasswordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(mPasswordEditText.getText()))
                    mPasswordLayout.setError(null);
                return false;
            }
        });

        if(user.getPassword()!=null){
            mPasswordEditText.setText(user.getPassword());
            mPasswordConfirmEditText.setText(user.getPassword());
        }
    }

    public void goStep3(View view) {
        if (!isPasswordValid(mPasswordEditText.getText())) {
            mPasswordLayout.setError("Password must contain at least 8 characters.");
            mPasswordEditText.requestFocus();
        } else if (!isPasswordValid(mPasswordConfirmEditText.getText())) {
            mPasswordLayout.setError(null);
            mPasswordConfirmLayout.setError("Password must contain at least 8 characters.");
            mPasswordConfirmEditText.requestFocus();
        } else if (!arePasswordsSame(mPasswordEditText.getText(), mPasswordConfirmEditText.getText())) {
            mPasswordLayout.setError(null);
            mPasswordConfirmLayout.setError("Passwords must be same.");
            mPasswordConfirmEditText.requestFocus();
        } else {
            mPasswordConfirmLayout.setError(null);
            mPasswordLayout.setError(null);
            //Go to next

            user.setPassword(mPasswordEditText.getText().toString());

            Intent i = new Intent(this,Step3Activity.class);
            i.putExtra("User",user);
            startActivity(i);
        }
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }

    private boolean arePasswordsSame(@Nullable Editable pass1, @Nullable Editable pass2) {
        if (pass1 == null || pass2 == null) return false;
        return pass1.toString().equals(pass2.toString());
    }
}
