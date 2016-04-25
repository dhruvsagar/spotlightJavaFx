package view;

import controller.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import modell.Movie;
import modell.Person;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by Dhruv Sagar on 21-Apr-16.
 */
public class MainScreenController {

    private  MainApp mainApp;

    private HashMap<String, Movie> movieDatabase;

    private ObservableList<Movie> recommendationsMovieList;

    @FXML
    private ListView<Movie> recommendations;
    @FXML
    private Label movieTitleLabel;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label synopsisLabel;
    @FXML
    private Label yourRatingLabel;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        loadMovieDatabase();
        populateRecommendations();


        recommendations.setItems(recommendationsMovieList);

        recommendations.setCellFactory(new Callback<ListView<Movie>, ListCell<Movie>>() {

            @Override
            public ListCell<Movie> call(ListView<Movie> p) {

                ListCell<Movie> cell = new ListCell<Movie>() {

                    @Override
                    protected void updateItem(Movie t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName() + " : " + t.getRating() + "/10");
                        }
                    }

                };

                return cell;
            }
        });

        showMovieDetails(recommendationsMovieList.get(0));

        recommendations.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showMovieDetails(newValue));
    }

    private void loadMovieDatabase() {
        movieDatabase = new HashMap<>();
        try {
            String line;
            FileReader fileReader = new FileReader("movies.txt");
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);


            while((line = bufferedReader.readLine()) != null) {
                String[] elements = line.split(Pattern.quote("|||"));
                double rating = Double.parseDouble(elements[2]);
                Movie m = new Movie(elements[0], elements[1], rating);
                movieDatabase.put(elements[0], m);
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateRecommendations() {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        for (Movie movie : movieDatabase.values()) {
            movieArrayList.add(movie);
        }
        Collections.sort(movieArrayList);
        movieArrayList = new ArrayList<Movie>(movieArrayList.subList(0, 13));
        recommendationsMovieList =  FXCollections.observableArrayList(movieArrayList);
    }

    private void showMovieDetails(Movie movie) {

        if (movie != null) {
            movieTitleLabel.setText(movie.getName());
            ratingLabel.setText(Double.toString(movie.getRating()));
            synopsisLabel.setText(makeLines(movie.getSynopsis()));
            synopsisLabel.setWrapText(true);
            synopsisLabel.setTextAlignment(TextAlignment.JUSTIFY);
        } else {
            movieTitleLabel.setText("No Movie Selected");
            ratingLabel.setText("N/A");
            synopsisLabel.setText("");
        }
    }

    private String makeLines(String s) {

        StringBuilder sb = new StringBuilder(s);

        int i = 0;
        while ((i = sb.indexOf(" ", i + 50)) != -1) {
            sb.replace(i, i + 1, "\n");
        }
        return sb.toString();
    }
}
