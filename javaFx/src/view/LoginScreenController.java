package view;

import controller.MainApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modell.Person;
import sample.Main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Observable;


/**
 * Created by Dhruv Sagar on 21-Apr-16.
 */
public class LoginScreenController {
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private TextField name;
    @FXML
    private TextField registerUsername;
    @FXML
    private ChoiceBox majorChoiceBox;
    @FXML
    private PasswordField firstPassword;
    @FXML
    private PasswordField secondPassword;
    @FXML
    private Label registerErrorMessage;
    @FXML
    private Label loginErrorMessage;

    private String major = null;

    private MainApp mainApp;


    @FXML
    private void  initialize() {
        loginUsername.setPromptText("Enter Username");
        loginPassword.setPromptText("Enter Password");
        name.setPromptText("Your Name Here");
        registerUsername.setPromptText("A Unique Username Here");
        firstPassword.setPromptText("Password length must be greater than 4");
        majorChoiceBox.setTooltip(new Tooltip("Select a major"));
        registerErrorMessage.setVisible(false);
        loginErrorMessage.setVisible(false);

        majorChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                major = MainApp.majorArray[newValue.intValue()];
            }
        });
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;


        majorChoiceBox.setItems(mainApp.getMajors());
    }

    @FXML
    private void handleLoginButton() {
        if (loginUsername.getText().isEmpty()) {
            loginErrorMessage.setText("Username field required");
            loginErrorMessage.setVisible(true);
            handleCancel();
            return;
        }
        if (loginPassword.getText().isEmpty()) {
            loginErrorMessage.setText("Password field required");
            loginErrorMessage.setVisible(true);
            handleCancel();
            return;
        }
        String username = loginUsername.getText();
        String password = loginPassword.getText();
        System.out.println(MainApp.db.toString());
        if (MainApp.db.getPerson(username)== null) {
            loginErrorMessage.setText("There is no such username. \nPlease create an account");
            loginErrorMessage.setVisible(true);
            handleCancel();
            return;
        }
        if (!MainApp.db.getPerson(username).getPassword().equals(password)) {
            loginErrorMessage.setText("Username or password is incorrect");
            loginErrorMessage.setVisible(true);
            handleCancel();
            return;
        }
        MainApp.currentPerson = MainApp.db.getPerson(username);
        mainApp.setMainScene();
    }

    @FXML
    public void handleRegister() {
        if (registerUsername.getText().isEmpty()) {
            registerErrorMessage.setText("Username is required");
            registerErrorMessage.setVisible(true);
            handleCancel();
            return;
        }
        if (name.getText().isEmpty()) {
            registerErrorMessage.setText("Name is required");
            registerErrorMessage.setVisible(true);
            handleCancel();
            return;
        }
        if (major == null) {
            registerErrorMessage.setText("Please choose a major");
            registerErrorMessage.setVisible(true);
            handleCancel();
            return;
        }
        if(firstPassword.getText().isEmpty() || firstPassword.getText().length() < 5) {
            registerErrorMessage.setText("Password too short");
            registerErrorMessage.setVisible(true);
            handleCancel();
            return;
        }
        if (!secondPassword.getText().equals(firstPassword.getText())) {
            registerErrorMessage.setText("Passwords didn't match");
            registerErrorMessage.setVisible(true);
            handleCancel();
            return;
        }
        if (MainApp.db.getPerson(registerUsername.getText()) != null) {
            registerErrorMessage.setText("This username is already taken");
            registerErrorMessage.setVisible(true);
            handleCancel();
            return;
        }
        MainApp.currentPerson = new Person(name.getText().toString(), major.toString(),
                registerUsername.getText().toString(), firstPassword.getText().toString());
        MainApp.db.addPerson(mainApp.currentPerson, registerUsername.getText().toString());
        saveDatabase();
        System.out.println(MainApp.currentPerson.getName().toString());
        System.out.println(MainApp.currentPerson.getMajor());
        mainApp.setMainScene();
    }

    private void saveDatabase() {
        try {
            // Assume default encoding.
            FileWriter fileWriter =  new FileWriter("database.txt");

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            Map users = MainApp.db.getUsers();
            ArrayList<Person>  people = new ArrayList<>(users.values());
            for (Person person : people) {
                bufferedWriter.write(person.getName() + "|||" + person.getMajor() + "|||" + person.getUsername());
                bufferedWriter.write("|||" + person.getPassword());
                bufferedWriter.newLine();
            }

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() {
        loginPassword.clear();
        loginUsername.clear();
        name.clear();
        registerUsername.clear();
        firstPassword.clear();
        secondPassword.clear();
    }
}
