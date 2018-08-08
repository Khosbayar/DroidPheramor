package khosbayar.hs.com.droidpheramor.activities;

import androidx.fragment.app.FragmentActivity;
import khosbayar.hs.com.droidpheramor.R;
import khosbayar.hs.com.droidpheramor.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.material.snackbar.Snackbar;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

public class Step5Activity extends FragmentActivity {

    RangeSeekBar<Integer> mAgeRange;
    CheckBox mMale, mFemale;
    Integer minAge, maxAge;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step5);

        mAgeRange = findViewById(R.id.rsb_age_range);
        mAgeRange.setRangeValues(1, 100);
        mMale = findViewById(R.id.cb_male);
        mFemale = findViewById(R.id.cb_female);


        mAgeRange.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                minAge = minValue;
                maxAge = maxValue;
            }
        });

        Intent intent = getIntent();
        user = intent.getParcelableExtra("User");
        if (user.isInterestMale())
            mMale.setChecked(true);
        if (user.isInterestFemale())
            mFemale.setChecked(true);
        if (user.getAgeRangeMax() != 0)
            mAgeRange.setSelectedMaxValue(user.getAgeRangeMax());
        if (user.getAgeRangeMin() != 0)
            mAgeRange.setSelectedMinValue(user.getAgeRangeMin());
    }

    public void goStep6(View view) {
        if (!isInterestChoosen(mMale, mFemale))
            Snackbar.make(view, "Please choose at least one from interest.", Snackbar.LENGTH_SHORT).show();
        else if (minAge == null || maxAge == null)
            Snackbar.make(view, "Please select the age range.", Snackbar.LENGTH_SHORT).show();
        else {
            user.setAgeRangeMax(maxAge);
            user.setAgeRangeMin(minAge);
            user.setInterestFemale(mFemale.isChecked());
            user.setInterestMale(mMale.isChecked());

            Intent i = new Intent(this, Step6Activity.class);
            i.putExtra("User", user);
            startActivity(i);
        }
    }

    private boolean isInterestChoosen(CheckBox cb, CheckBox cb2) {
        return cb.isChecked() || cb2.isChecked();
    }
}
