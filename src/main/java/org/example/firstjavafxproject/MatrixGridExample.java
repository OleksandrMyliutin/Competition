package org.example.firstjavafxproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.HungarianMethod;

public class MatrixGridExample extends Application {

    private TextField[][] textFields;
    private TextField[] rowNameFields;
    private boolean isMaximalBenefit;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label sizeLabel = new Label("Впишіть розмір поля для данних:");
        TextField sizeTextField = new TextField();

        Button createButton = new Button("Create Matrix");
        createButton.setOnAction(e -> {
            int size = Integer.parseInt(sizeTextField.getText());
            if (size >= 2) {
                createMatrix(gridPane, size);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Недійсний розмір матриці");
                alert.setContentText("Розмір матриці має бути не менше 2.");
                alert.showAndWait();
            }
        });

        gridPane.add(sizeLabel, 0, 0);
        gridPane.add(sizeTextField, 1, 0);
        gridPane.add(createButton, 2, 0);
        Scene scene = new Scene(gridPane, 500, 250);

        // Встановлення бірюзового фону
        BackgroundFill backgroundFill = new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        gridPane.setBackground(background);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createMatrix(GridPane gridPane, int size) {
        gridPane.getChildren().clear();

        Label nameLabel = new Label("Команада №:");
        gridPane.add(nameLabel, 0, 0, size + 1, 1);

        textFields = new TextField[size][size];
        rowNameFields = new TextField[size];

        // Додавання міток K1, K2, ..., Kn над стовпцями матриці
        for (int j = 0; j < size; j++) {
            Label label = new Label("K" + (j + 1));
            gridPane.add(label, j + 1, 1);
        }

        for (int i = 0; i < size; i++) {
            TextField rowNameField = new TextField("Введіть назву проекту");
            rowNameFields[i] = rowNameField;
            gridPane.add(rowNameField, 0, i + 2);

            for (int j = 0; j < size; j++) {
                TextField textField = new TextField();
                textField.setPrefWidth(50);
                textFields[i][j] = textField;
                gridPane.add(textField, j + 1, i + 2);
            }
        }

        Button processButton = new Button("Process");
        processButton.setOnAction(e -> processMatrix(size));

        gridPane.add(processButton, 0, size + 2);

        Label resultLabel = new Label();
        gridPane.add(resultLabel, 0, size + 3, size + 1, 1);

        GridPane.setColumnSpan(resultLabel, size + 1);

        RadioButton radioButton1 = new RadioButton("Minimal cost");
        radioButton1.fire();
        RadioButton radioButton2 = new RadioButton("Maximal benefit");

        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);

        // Обробка подій радіокнопок
        radioButton1.setOnAction(event -> {
            isMaximalBenefit = false;
        });

        radioButton2.setOnAction(event -> {
            isMaximalBenefit = true;
        });

        gridPane.add(radioButton1, 0, size + 3);
        gridPane.add(radioButton2, 1, size + 3);
    }

    private void processMatrix(int size) {
        double[][] matrix = new double[size][size];
        boolean isMatrixFilled = true;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                String input = textFields[i][j].getText();
                if (input.isEmpty()) {
                    isMatrixFilled = false;  // Матриця не повністю заповнена
                    break;
                }
                try {
                    double value = Double.parseDouble(input);
                    if (value < 0) {
                        isMatrixFilled = false;  // Значення менше 0 - неприпустиме, матриця не заповнена
                        break;
                    }
                    matrix[i][j] = value;
                } catch (NumberFormatException ex) {
                    isMatrixFilled = false;  // Недопустиме значення, матриця не заповнена
                    break;
                }
            }
        }

        if (!isMatrixFilled) {
            // Виведення повідомлення про неправильне заповнення матриці
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Matrix");
            alert.setContentText("Матриця містить недійсні або порожні значення.");
            alert.showAndWait();
            return;
        }

        double[] cords = HungarianMethod.calculateCosts(matrix, isMaximalBenefit);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            String rowName = rowNameFields[i].getText();
            result.append(rowName).append(": ");
            result.append(cords[i]);
            result.append("\n");
        }

        double minimalOrMaximal = HungarianMethod.sumZeroElements(cords);

        result.append("cost of hungarian method: ").append(minimalOrMaximal);

        Stage resultStage = new Stage();
        Label resultLabel = new Label(result.toString());
        Scene resultScene = new Scene(resultLabel);
        resultStage.setScene(resultScene);
        resultStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}