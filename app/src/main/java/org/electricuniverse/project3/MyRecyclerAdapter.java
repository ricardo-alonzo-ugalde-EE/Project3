package org.electricuniverse.project3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *Part 3.3.1: Implementing RecyclerView Adapter Class********************************************************************
 * */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> implements Filterable
{
    private List<Map<String, ?>> md;    // list of all the movies
    private List<Map<String, ?>> md_filtered;    //list of all the filtered movies
    private ListFragment.OnItemSelectedListener onListItemClickListener = null;     //call back to the activity
    // ListFragment.onItemSelectedListener clickListener;

    public MyRecyclerAdapter(List<Map<String, ?>> list)     //constructor
    {
        md = md_filtered = list;
    }

    /**
     * Part 3.3.2: Implementing ViewHolder class for the adapter. Each reference view is from cardview
     */
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView movie_name;
        public TextView movie_year;
        public ImageView poster_img;

        public ViewHolder(View view) {
            super(view);
            movie_name = (TextView) view.findViewById(R.id.movie_name);
            movie_year = (TextView) view.findViewById(R.id.movie_year);
            poster_img = (ImageView) view.findViewById(R.id.poster_photo);
        }
    }


    /**
     * Part 3.3.5: Finally implement your getFilter method where you need to create a Filter class and return its
     * instance. This will be called whenever we need to filter out some items from our list. We will
     * see where this is called in the main activity. Read through the code and make sure you
     * understand what we are doing here.
     */
    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                String charString = charSequence.toString();
                if (charString.isEmpty())
                {
                    md_filtered = md;
                } else
                {
                    List<Map<String, ?>> filteredList = new ArrayList<>();
                    for (Map movie : md)
                    {
                        //name match condition this might differ depending on your requirement
                        //here we are looking for name or phone number match
                        if (movie.get("name").toString().toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(movie);
                        }
                    }
                    md_filtered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = md_filtered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                md_filtered = (ArrayList<Map<String, ?>>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public Map getItem(int i)
    {
        return md_filtered.get(i);
    }

    public void setOnItemClickListener(ListFragment.OnItemSelectedListener listener)
    {
        onListItemClickListener = listener;
    }


    /**
     * Part 3.3.3: Implement your onCreateViewHolder method. This is where you need to inflate the view
     * hierarchy from the card view layout and register event listeners to individual views in this
     * hierarchy.
     */
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        final ViewHolder view_holder = new ViewHolder(v);
        return view_holder;

//        v.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v) {
//                if (onListItemClickListener != null) {
//                    onListItemClickListener.onListItemSelected(v,Integer.toString(md_filtered.get()));
//                }
//            }
//        });
////        v.setOnLongClickListener(new View.OnLongClickListener() {
////            @Override
////            public boolean onLongClick(View v) {
////                if (onListItemClickListener != null) {
////                    onListItemClickListener.onItemLongClick(v, view_holder.getAdapterPosition());
////
////                }
////                return true;
////            }
////        });
//        return view_holder;
    }


    /**
     * Part 3.3.4: Implement onBindViewHolder method and set the values of your view objects in the
     * viewholder passed as argument. Note that we are using the filtered list of movies which is
     * initially the same as the list of all movies.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.movie_name.setText(md_filtered.get(position).get("name").toString());
        holder.movie_year.setText(md_filtered.get(position).get("year").toString());
        holder.poster_img.setImageResource(Integer.parseInt(md_filtered.get(position).get("image").toString()));
        ViewCompat.setTransitionName(holder.poster_img, md_filtered.get(position).get("name").toString());
        holder.poster_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(onListItemClickListener != null)
                {
                    onListItemClickListener.onListItemSelected(view, Integer.parseInt(md_filtered.get(position).get("image").toString()),
                            md_filtered.get(position).get("name").toString(), md_filtered.get(position).get("year").toString());
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return md_filtered.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {

    }
}
