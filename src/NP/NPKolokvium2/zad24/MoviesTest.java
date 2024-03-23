package NP.NPKolokvium2.zad24;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Movie {
    String title;
    List<Integer> ratings;
    double rating;
    double ratingCoef;

    public Movie(String title, int[] ratings) {
        this.title = title;
        this.ratings = new ArrayList<>();
        for (int rating : ratings) {
            this.ratings.add(rating);
        }
        this.rating = (this.ratings.stream().mapToInt(Integer::intValue).sum() * 1.0) / this.ratings.size();
        this.ratingCoef = this.rating * 1.0 * this.ratings.size();
    }

    public String getTitle() {
        return title;
    }

    public int getRatingsSize() {
        return ratings.size();
    }

    public double getRating() {
        return rating;
    }

    public double getRatingCoef() {
        return ratingCoef;
    }

    public void setRatingCoef(double ratingCoef) {
        this.ratingCoef = ratingCoef;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f) of %d ratings", title, rating, ratings.size());
    }
}

class MoviesList {
    List<Movie> movies;
    int totalRatings;

    public MoviesList() {
        this.movies = new ArrayList<>();
        this.totalRatings = 0;
    }

    public void addMovie(String title, int[] ratings) {
        movies.add(new Movie(title, ratings));
        this.totalRatings = Math.max(this.totalRatings, ratings.length);
    }

    public List<Movie> top10ByAvgRating() {
        return movies.stream()
                .sorted(Comparator.comparing(Movie::getRating).reversed().thenComparing(Movie::getTitle))
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Movie> top10ByRatingCoef() {
        movies.forEach(movie -> movie.setRatingCoef(movie.getRatingCoef() / totalRatings));
        return movies.stream()
                .sorted(Comparator.comparing(Movie::getRatingCoef).reversed().thenComparing(Movie::getTitle))
                .limit(10)
                .collect(Collectors.toList());
    }
}

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}