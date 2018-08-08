package khosbayar.hs.com.droidpheramor.activities;

import androidx.fragment.app.FragmentActivity;
import khosbayar.hs.com.droidpheramor.R;
import khosbayar.hs.com.droidpheramor.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class Step6Activity extends FragmentActivity {

    Spinner mRace, mReligion;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step6);

        mRace = findViewById(R.id.s_race);
        mReligion = findViewById(R.id.s_religion);

        ArrayAdapter<CharSequence> mRaceAdapter = ArrayAdapter.createFromResource(this,
                R.array.race_array, R.layout.spinner_item);
        ArrayAdapter<CharSequence> mReligionAdapter = ArrayAdapter.createFromResource(this,
                R.array.religion_array, R.layout.spinner_item);

        mRaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mReligionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mRace.setAdapter(mRaceAdapter);
        mReligion.setAdapter(mReligionAdapter);

        Intent intent = getIntent();
        user = intent.getParcelableExtra("User");

        if (user.getRace() != 0)
            mRace.setSelection(user.getRace());
        if (user.getReligion() != 0)
            mReligion.setSelection(user.getReligion());

    }

    public void goStep7(View view) {
        if (mRace.getSelectedItemPosition() == 0) {
            Snackbar.make(view, "Please choose your race option.", Snackbar.LENGTH_SHORT).show();
            mRace.requestFocus();
        } else if (mReligion.getSelectedItemPosition() == 0) {
            Snackbar.make(view, "Please choose your religion option.", Snackbar.LENGTH_SHORT).show();
            mReligion.requestFocus();
        } else {
            user.setRace(mRace.getSelectedItemPosition());
            user.setReligion(mReligion.getSelectedItemPosition());
            Intent i = new Intent(this, Step7Activity.class);
            i.putExtra("User", user);
            startActivity(i);
        }
    }
}
