package org.electricuniverse.project3;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class DetailMaster extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.activity_detail_fragment, container, false);
        ImageView imageView = rooView.findViewById(R.id.img_poster);
        TextView title = rooView.findViewById(R.id.title);
        TextView year = rooView.findViewById(R.id.year);
        RatingBar ratingBar = rooView.findViewById(R.id.movie_rating);
        TextView description = rooView.findViewById(R.id.description);
        TextView director = rooView.findViewById(R.id.director);
        TextView length = rooView.findViewById(R.id.length);
        TextView cast = rooView.findViewById(R.id.cast);
        Bundle args = getArguments();
        Picasso.get().load(args.getString("img_id")).into(imageView);
        title.setText(args.getString("mtitle"));
        year.setText(args.getString("myear"));
        ratingBar.setRating(args.getFloat("rating"));
        description.setText(args.getString("description"));
        director.setText(args.getString("director"));
        length.setText(args.getString("length"));
        cast.setText(args.getString("cast"));
        return rooView;
    }
}
