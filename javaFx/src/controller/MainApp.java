package controller;/**
 * Created by Dhruv Sagar on 20-Apr-16.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import modell.DataBase;
import modell.Person;
import view.LoginScreenController;
import view.MainScreenController;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    public static DataBase db;
    public static Person currentPerson;

    private ObservableList<String> majors = FXCollections.observableArrayList();

    public static String[] majorArray = {"Architecture", "Industrial Design", "Computational Media", "Computer Science",
            "Aerospace Engineering", "Biomedical Engineering", "Chemical and Biomolecular Engineering", "Civil Engineering",
            "Computer Engineering", "Electrical Engineering", "Environmental Engineering", "Industrial Engineering",
            "Materials Science and Engineering", "Mechanical Engineering", "Nuclear and Radiological Engineering", "Applied Mathematics",
            "Applied Physics", "Biochemistry", "Biology", "Chemistry", "Discrete Mathematics", "Earth and Atmospheric Sciences", "Physics", "Psychology", "Applied Languages and Intercultural Studies", "Computational Media",
            "Economics", "Economics and International Affairs", "Global Economics and Modern Languages", "History, Technology, and Society", "International Affairs",
            "International Affairs and Modern Language", "Literature, Media, and Communication", "Public Policy", "Business Administration"};

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Spotlight");

        //db = new DataBase();
        loadDatabase();

        showLoginScreen();
    }

    public MainApp() {
        majors.addAll(Arrays.asList(majorArray));

    }

    public ObservableList<String> getMajors() {
        return majors;
    }


    public void loadDatabase() {
        db = new DataBase();
        try {
            String line;
            FileReader fileReader = new FileReader("database.txt");
            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);


            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String[] elements = line.split(Pattern.quote("|||"));
                for (int i =0; i< elements.length; i++)
                    System.out.println(elements[i]);
                Person p = new Person(elements[0], elements[1], elements[2], elements[3]);
                System.out.println(p.toString());
                db.addPerson(p, p.getUsername().toString());
                System.out.println(db.getPerson(p.getUsername()).toString());
            }

            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Shows the person overview inside the root layout.
     */
    public void showLoginScreen() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginScreen.fxml"));
            AnchorPane loginScreen = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            Scene scene = new Scene(loginScreen);
            primaryStage.setScene(scene);
            primaryStage.show();

            LoginScreenController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
            AnchorPane newScreen = (AnchorPane) loader.load();

            Scene scene = new Scene(newScreen);
            primaryStage.setScene(scene);
            primaryStage.show();

            MainScreenController controller = loader.getController();
            //controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
