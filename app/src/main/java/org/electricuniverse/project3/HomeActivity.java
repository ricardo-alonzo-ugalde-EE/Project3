package org.electricuniverse.project3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Map;

public class HomeActivity extends AppCompatActivity implements ListFragment.OnItemSelectedListener
{
    //search features******************************************************************************
    private RecyclerView recycler_view;
    private MovieData md=new MovieData();
    private final MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(md.getMoviesList());


    //main recycler view****************************************************************************
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


        //search features******************************************************************************
        /**
         * 2.5: PSet your toolbar as the ActionBar in your Activity’s onCreate method.
         * */
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //main recycler view****************************************************************************
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

    //search features*********************************************************************************
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        MenuItem myActionMenuItem = menu.findItem(R.id.search_action);
//        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
//        {
//
//            /**
//             * Part 8: Filtering the list. Complete the SearchView.OnQueryTextListener() implementation to invoke the getFilter
//             * method on your adapter.
//             * */
//            @Override
//            public boolean onQueryTextSubmit(String query)
//            {
//                Toast toast = Toast.makeText(getApplicationContext(), "Query Text =" + query, Toast.LENGTH_SHORT);
//                toast.show();
//                myRecyclerAdapter.getFilter().filter(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText)
//            {
//                myRecyclerAdapter.getFilter().filter(newText);
//                return true;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    /**
//     * Part 5.2: In your onStart initialize the RecyclerView and set its adapter and other adapter attributes. Do not
//     * worry about the onListItemClickListener and simply create a toast message in its functions for
//     * now.
//     * */
//    @Override
//    protected void onStart()
//    {
//        super.onStart();
//        recycler_view = (RecyclerView) findViewById(R.id.mainRecyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        layoutManager.scrollToPosition(0);
//        recycler_view.setLayoutManager(layoutManager);
//        myRecyclerAdapter.setOnItemClickListener(new OnListItemClickListener()
//        {
//            @Override
//            public void onItemClick(View v, int position)
//            {
//                Map hashMap = myRecyclerAdapter.getItem(position);
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance((int)hashMap.get("image"),
//                        hashMap.get("name").toString(),hashMap.get("year").toString(),
//                        Float.parseFloat(hashMap.get("rating").toString()),hashMap.get("description").toString());
//                fragmentTransaction.replace(R.id.detailFragment,movieDetailFragment);
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//                fragmentTransaction.commit();
//
//            }
//
//            @Override
//            public void onItemLongClick(View v, int position)
//            {
//
//            }
//        });
//        recycler_view.setAdapter(myRecyclerAdapter);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//
//
//    }





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