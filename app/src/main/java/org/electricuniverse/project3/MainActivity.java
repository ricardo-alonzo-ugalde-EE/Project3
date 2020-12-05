package org.electricuniverse.project3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements ListFragment.OnItemSelectedListener
{
    private boolean twoPane;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(savedInstanceState ==null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new ListFragment()).commit();
        }
        twoPane = false;
        if(findViewById(R.id.detail_container)!=null)
        {
            twoPane = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onListItemSelected(View sharedView, int imageResourceID, String title, String year)
    {
        Bundle args = new Bundle();
        args.putInt("img_id", imageResourceID);
        args.putString("mtitle",title);
        args.putString("myear",year);
        Fragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);

        if (twoPane)
        {
            detailFragment.setEnterTransition(new Slide(Gravity.TOP));
            detailFragment.setEnterTransition(new Slide(Gravity.BOTTOM));
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,detailFragment)
                    .addToBackStack(null).commit();
        }
        else
        {
            detailFragment.setSharedElementEnterTransition(new DetailsTransition());
            detailFragment.setEnterTransition(new Fade());
            detailFragment.setExitTransition(new Fade());
            detailFragment.setSharedElementReturnTransition(new DetailsTransition());
            getSupportFragmentManager().beginTransaction().addSharedElement(sharedView,
                    ViewCompat.getTransitionName(sharedView)).replace(R.id.main_container,detailFragment)
                    .addToBackStack(null).commit();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        new CountDownTimer(3000, 1000)
        {

            public void onTick(long millisUntilFinished)
            {

            }

            public void onFinish()
            {
                if(currentUser==null)
                {
                    Toast.makeText(MainActivity.this, "No user found", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,SignupLogin.class));
                    finish();
                }
                else
                {
                    if(currentUser.isEmailVerified())
                    {
                        //Toast.makeText(MainActivity.this, "User already signed in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Please verify your email and login.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SignupLogin.class));
                        finish();
                    }
                }

            }
        }.start();
    }
}