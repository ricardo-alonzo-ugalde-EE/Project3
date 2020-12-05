package org.electricuniverse.project3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class HomeActivity extends AppCompatActivity implements ListFragment.OnItemSelectedListener
{
    private boolean twoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Window window = HomeActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        window.setStatusBarColor(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimaryDark));
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


}