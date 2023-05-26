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
import org.example.ElementCoordinates;
import org.example.HungarianMethod;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

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

        Label sizeLabel = new Label("Напишіть кількість проектів та команд:");
        TextField sizeTextField = new TextField();

        Button createButton = new Button("Створити матрицю");
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
        Scene scene = new Scene(gridPane, 550, 250);

        // Встановлення бірюзового фону
        BackgroundFill backgroundFill = new BackgroundFill(Color.AZURE, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        gridPane.setBackground(background);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createMatrix(GridPane gridPane, int size) {
        gridPane.getChildren().clear();

        Label nameLabel = new Label();
        gridPane.add(nameLabel, 0, 0, size + 1, 1);

        textFields = new TextField[size][size];
        rowNameFields = new TextField[size];

        // Додавання міток K1, K2, ..., Kn над стовпцями матриці
        for (int j = 0; j < size; j++) {
            Label label = new Label("Команда № " + (j + 1));
            gridPane.add(label, j + 1, 1);
        }

        for (int i = 0; i < size; i++) {
            TextField rowNameField = new TextField("Введіть назву проекту");
            rowNameFields[i] = rowNameField;
            gridPane.add(rowNameField, 0, i + 2);

            for (int j = 0; j < size; j++) {
                TextField textField = new TextField();
                textField.setPrefWidth(50); // Фіксована ширина поля
                textFields[i][j] = textField;
                gridPane.add(textField, j + 1, i + 2);
            }
        }

        Button processButton = new Button("Розрахувати");
        processButton.setOnAction(e -> processMatrix(size));

        gridPane.add(processButton, 0, size + 2);

        Label resultLabel = new Label();
        gridPane.add(resultLabel, 0, size + 3, size + 1, 1);

        GridPane.setColumnSpan(resultLabel, size + 1);

        RadioButton radioButton1 = new RadioButton("Мінімальні витрати (год)");
        radioButton1.fire();
        String currencyCode = "UAH";

        Currency currency = Currency.getInstance(currencyCode);

        String currencySymbol = currency.getSymbol(Locale.getDefault());

        RadioButton radioButton2 = new RadioButton("Максимальний дохід (" + currencySymbol + ")");

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
        ArrayList<ElementCoordinates> cords = new ArrayList<>();
        try {
             cords = HungarianMethod.calculateCosts(matrix, isMaximalBenefit);
        }
        catch (RuntimeException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Недійсний розмір матриці");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        StringBuilder result = new StringBuilder();
        result.append("Для оптимального варіанту повинний бути такий розводіл проектів, де:\n");
        for (int i = 0; i < size; i++) {
            String rowName = rowNameFields[i].getText();
            result.append("Проект ").append(rowName).append(" бере команда №").append(cords.get(i).getJ() + 1).append(";");
            result.append("\n");
        }

        double minimalOrMaximal = HungarianMethod.sumZeroElements(matrix,cords);

        if(isMaximalBenefit) {
            Currency currency = Currency.getInstance("UAH");

            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
            currencyFormatter.setCurrency(currency);

            String formattedAmount = currencyFormatter.format(minimalOrMaximal);
            result.append("Максимальний дохід при такому розподіленню проектів: ").append(formattedAmount);
        }
        else {
            result.append("Мінімальна кількість годин при такому розподіленню проектів: ").append(minimalOrMaximal + " год");
        }

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