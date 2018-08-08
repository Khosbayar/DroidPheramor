package khosbayar.hs.com.droidpheramor.activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.MessageFormat;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import khosbayar.hs.com.droidpheramor.R;
import khosbayar.hs.com.droidpheramor.models.User;

public class Step4Activity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {


    TextInputEditText mDobEditText;
    TextInputLayout mDobLayout;
    RadioButton mMale;
    RadioButton mFemale;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day;


    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step4);

        mDobEditText = findViewById(R.id.tiet_dob);
        mDobLayout = findViewById(R.id.til_dob);
        mMale = findViewById(R.id.rb_male);
        mFemale = findViewById(R.id.rb_female);


        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        mDobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = DatePickerDialog.newInstance(Step4Activity.this, Year, Month, Day);

                datePickerDialog.setThemeDark(true);

                datePickerDialog.showYearPickerFirst(false);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    datePickerDialog.setAccentColor(getColor(R.color.colorAccent));
                } else {
                    datePickerDialog.setAccentColor(Color.parseColor("#32D458"));
                }

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);


            }
        });

        Intent intent = getIntent();
        user = intent.getParcelableExtra("User");

        if (user.getDob() != null) {
            mDobEditText.setText(user.getDob());
        }
        if (user.getGender() == 1)
            mMale.setChecked(true);
        else if (user.getGender() == 2)
            mFemale.setChecked(true);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (monthOfYear == 12)
            monthOfYear = 1;
        else
            monthOfYear++;
        mDobEditText.setText(MessageFormat.format("{0}{1}{2}{3}{4}", monthOfYear, getString(R.string.date_separator), dayOfMonth, getString(R.string.date_separator), year));
    }

    public void goStep5(View view) {
        if (!isInputValid(mDobEditText.getText())) {
            mDobLayout.setError("Date of birth can't be empty!");
            mDobEditText.requestFocus();
        } else if (!isGenderChoosen(mMale, mFemale)) {
            mDobLayout.setError(null);
            mDobEditText.clearFocus();
            Snackbar.make(view, "Please choose your gender", Snackbar.LENGTH_SHORT).show();
        } else {
            user.setDob(mDobEditText.getText().toString());
            if (mMale.isChecked())
                user.setGender(1);
            else
                user.setGender(2);
            Intent i = new Intent(this, Step5Activity.class);
            i.putExtra("User",user);
            startActivity(i);

        }

    }

    private boolean isGenderChoosen(RadioButton rb, RadioButton rb2) {
        return rb.isChecked() || rb2.isChecked();
    }

    private boolean isInputValid(@Nullable Editable text) {
        return text != null && text.length() > 0;
    }
}
