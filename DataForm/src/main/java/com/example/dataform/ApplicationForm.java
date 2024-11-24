package com.example.dataform;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class ApplicationForm extends Application {

    private ObservableList<String[]> submittedData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Application Form - Pink Theme");

        // Banner
        BorderPane bannerPane = new BorderPane();
        bannerPane.setStyle("-fx-background-color: #FFC0CB; -fx-padding: 15px;");

        Text bannerText = new Text("Application Form");
        bannerText.setFont(Font.font("Georgia", FontWeight.BOLD, 28));
        bannerText.setFill(Color.DEEPPINK);
        bannerPane.setCenter(bannerText);

        // Form layout
        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(20));
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setStyle("-fx-background-color: #FFF0F5; -fx-border-color: #FFB6C1; -fx-border-radius: 10px; -fx-padding: 20px;");

        Label nameLabel = new Label("Name:");
        styleLabel(nameLabel);
        TextField nameField = new TextField();
        styleInput(nameField);

        Label fatherNameLabel = new Label("Father's Name:");
        styleLabel(fatherNameLabel);
        TextField fatherNameField = new TextField();
        styleInput(fatherNameField);

        Label emailLabel = new Label("Email:");
        styleLabel(emailLabel);
        TextField emailField = new TextField();
        styleInput(emailField);

        Label cityLabel = new Label("City:");
        styleLabel(cityLabel);
        TextField cityField = new TextField();
        styleInput(cityField);

        Label addressLabel = new Label("Address:");
        styleLabel(addressLabel);

        TextArea addressArea = new TextArea();
        addressArea.setPrefRowCount(3);
        styleInput(addressArea);

        Label genderLabel = new Label("Gender:");
        styleLabel(genderLabel);
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton maleButton = new RadioButton("Male");
        RadioButton femaleButton = new RadioButton("Female");
        styleRadioButton(maleButton);
        styleRadioButton(femaleButton);
        HBox genderBox = new HBox(10, maleButton, femaleButton);

        Label imageLabel = new Label("Upload Image:");
        styleLabel(imageLabel);
        Button uploadButton = new Button("Choose File");
        styleButton(uploadButton);
        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        imageView.setStyle("-fx-border-color: #FFB6C1;");
        HBox imageBox = new HBox(10, uploadButton, imageView);

        FileChooser fileChooser = new FileChooser();
        uploadButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageView.setImage(image);
            }
        });

        formGrid.addRow(0, nameLabel, nameField);
        formGrid.addRow(1, fatherNameLabel, fatherNameField);
        formGrid.addRow(2, emailLabel, emailField);
        formGrid.addRow(3, cityLabel, cityField);
        formGrid.addRow(4, addressLabel, addressArea);
        formGrid.addRow(5, genderLabel, genderBox);
        formGrid.addRow(6, imageLabel, imageBox);

        // Submit button
        Button submitButton = new Button("Submit");
        styleButton(submitButton);
        submitButton.setOnAction(e -> {
            String name = nameField.getText();
            String fatherName = fatherNameField.getText();
            String email = emailField.getText();
            String city = cityField.getText();
            String address = addressArea.getText();
            String gender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "Not Selected");

            if (name.isEmpty() || fatherName.isEmpty() || email.isEmpty() || city.isEmpty() || address.isEmpty() || gender.equals("Not Selected")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields!");
                alert.showAndWait();
                return;
            }

            submittedData.add(new String[]{name, fatherName, email, city, address, gender});
            showDataScreen();
        });

        // Layout
        VBox mainLayout = new VBox(10, bannerPane, formGrid, submitButton);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #FFF5F8;");

        primaryStage.setScene(new Scene(mainLayout, 600, 600));
        primaryStage.show();
    }

    private void showDataScreen() {
        Stage dataStage = new Stage();
        VBox dataLayout = new VBox(10);
        dataLayout.setPadding(new Insets(20));
        dataLayout.setStyle("-fx-background-color: #FFC0CB;");

        Label dataLabel = new Label("Submitted Data:");
        dataLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: deeppink;");
        dataLayout.getChildren().add(dataLabel);

        for (String[] entry : submittedData) {
            String entryString = String.format(
                    "Name: %s\nFather's Name: %s\nEmail: %s\nCity: %s\nAddress: %s\nGender: %s\n",
                    entry[0], entry[1], entry[2], entry[3], entry[4], entry[5]
            );
            Label entryLabel = new Label(entryString);
            entryLabel.setStyle("-fx-background-color: #FFB6C1; -fx-text-fill: white; -fx-padding: 10px;");
            dataLayout.getChildren().add(entryLabel);
        }

        Scene dataScene = new Scene(dataLayout, 400, 400);
        dataStage.setScene(dataScene);
        dataStage.show();
    }

    private void styleLabel(Label label) {
        label.setStyle("-fx-text-fill: deeppink; -fx-font-weight: bold;");
    }

    private void styleInput(Control control) {
        control.setStyle("-fx-background-color: #FFB6C1; -fx-text-fill: deeppink; -fx-border-color: #FFC0CB;");
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: deeppink; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5px;");
    }

    private void styleRadioButton(RadioButton radioButton) {
        radioButton.setStyle("-fx-text-fill: deeppink;");
    }
}
