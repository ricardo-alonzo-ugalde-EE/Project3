package org.electricuniverse.project3;

public class PostModel
{

    public String name;
    public String description;
    public String url;
    public String rating;
    public String key;
    public String year;
    public String length;
    public String director;
    public String stars;

    public PostModel(String name, String description, String url, String rating, String key, String year, String length, String director, String stars) {
        this.name = name;
        this.description=description;
        this.url = url;
        this.rating=rating;
        this.key= key;
        this.year=year;
        this.length=length;
        this.director=director;
        this.stars= stars;
    }
}


