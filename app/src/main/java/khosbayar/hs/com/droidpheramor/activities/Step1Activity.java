package khosbayar.hs.com.droidpheramor.activities;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import khosbayar.hs.com.droidpheramor.R;
import khosbayar.hs.com.droidpheramor.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Step1Activity extends FragmentActivity {

    TextInputEditText mEmailEditText;
    TextInputLayout mEmailLayout;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("User");
        if(user==null)
            user = new User();

        mEmailEditText = findViewById(R.id.tiet_email);
        mEmailLayout = findViewById(R.id.til_email);

        mEmailEditText.clearFocus();

        mEmailEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isEmailValid(mEmailEditText.getText())) {
                    mEmailLayout.setError(null);
                }
                return false;
            }
        });

        if (user.getEmail() != null)
            mEmailEditText.setText(user.getEmail());
    }

    public void goStep2(View view) {
        if (!isEmailValid(mEmailEditText.getText())) {
            mEmailEditText.requestFocus();
            mEmailLayout.setError("Email must be valid!");
        } else {
            mEmailLayout.setError(null);
            // Go to next
            user.setEmail(mEmailEditText.getText().toString());

            Intent i = new Intent(this, Step2Activity.class);
            i.putExtra( "User", user);
            startActivity(i);
        }
    }

    private boolean isEmailValid(@Nullable Editable text) {
        if (text == null) return false;
        String input = text.toString().trim();
        return text.length() > 0 && input.matches(emailPattern);
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
