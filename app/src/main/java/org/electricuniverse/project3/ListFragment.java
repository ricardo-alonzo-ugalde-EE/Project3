package org.electricuniverse.project3;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFragment extends Fragment implements QueryListener
{


    @Override
    public void Filter(String query) {
        myRecyclerAdapter.getFilter().filter(query);
    }

    public interface OnItemSelectedListener{
        public void onListItemSelected(View sharedView, PostModel movie);
    }
    OnItemSelectedListener clickListener;

    private MovieData md = new MovieData();
    private MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.activity_list_fragment, container,false);
        RecyclerView rv = rooView.findViewById(R.id.mainRecyclerView);
        myRecyclerAdapter.setRecyclerView(rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(myRecyclerAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
        loadMovies();
        return rooView;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            clickListener=(OnItemSelectedListener)context;
            myRecyclerAdapter.setOnItemClickListener(clickListener);


        }
        catch (ClassCastException ex)
        {
            throw new ClassCastException(context.toString()+"must implement EventTrack");
        }
    }

   private void loadMovies ()
   {
       DatabaseReference movieRef = FirebaseDatabase.getInstance().getReference();
       List<Map<String, ?>> movieList = md.getMoviesList();
       for (Map movie : movieList) {
           HashMap<String, String> regMap = new HashMap<>();

           regMap.put("title", movie.get("name").toString());
           regMap.put("year", movie.get("year").toString());
           regMap.put("director", movie.get("director").toString());
           regMap.put("length", movie.get("length").toString());
           regMap.put("cast", movie.get("stars").toString());
           regMap.put("rating", movie.get("rating").toString());
           regMap.put("description", movie.get("description").toString());
           regMap.put("imageUrl", movie.get("url").toString());
           movieRef.child("Movie").push().setValue(movie);
       }
   }
}

































/**
 * Step 4.2: Implement ListFragment. This fragment has nothing but a RecyclerView in it.
 * */
//public class ListFragment extends Fragment implements QueryListener
//{
//    @Override
//    public void Filter(String query) {
//        myRecyclerAdapter.getFilter().filter(query);
//    }
//
//    public interface OnItemSelectedListener
//    {
//        public void onListItemSelected(View sharedView, int imageResourceID, String title, String year);
//    }
//    OnItemSelectedListener clickListener;
//    private MovieData md = new MovieData();
//    private final MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(md.getMoviesList());
//
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
//    {
//        View rooView = inflater.inflate(R.layout.activity_list_fragment, container, false);
//        RecyclerView rv = rooView.findViewById(R.id.mainRecyclerView);
//        StaggeredGridLayoutManager layoutManager =
//                new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        layoutManager.scrollToPosition(0);
//        rv.setLayoutManager(layoutManager);
//        rv.setAdapter(myRecyclerAdapter);
//        rv.setItemAnimator(new DefaultItemAnimator());
//        return rooView;
//    }
//
//    @Override
//    public void onAttach(@NonNull Context context)
//    {
//        super.onAttach(context);
//        try
//        {
//            clickListener = (OnItemSelectedListener)context;
//            myRecyclerAdapter.setOnItemClickListener(clickListener);
//        }
//        catch (ClassCastException ex)
//        {
//            throw new ClassCastException(context.toString() + "must implement EventTrack");
//        }
//    }
//}