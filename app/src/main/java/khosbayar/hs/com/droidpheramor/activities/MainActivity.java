package khosbayar.hs.com.droidpheramor.activities;

import androidx.fragment.app.FragmentActivity;
import khosbayar.hs.com.droidpheramor.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goRegister(View view) {
        Intent i = new Intent(this, Step1Activity.class);
        startActivity(i);
    }
}
