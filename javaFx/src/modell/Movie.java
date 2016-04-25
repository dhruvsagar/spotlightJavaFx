package modell;

/**
 * Created by Dhruv Sagar on 22-Apr-16.
 */
public class Movie implements Comparable<Movie>{
    private String name;
    private String synopsis;
    private double rating;

    public Movie(String name, String synopsis, double rating) {
        this.name = name;
        this.synopsis = synopsis;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getRating() {
        return  rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void  setName(String name) {
        this.name = name;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public int compareTo(Movie m) {
        return (int) ((m.getRating()-this.rating)*100);
    }

    @Override
    public String toString() {
        return this.name + " " + this.rating;
    }
}
