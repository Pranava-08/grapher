
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Grapher extends Application {

    private LineChart<Number, Number> lineChart;
    private ComboBox<String> functionComboBox;
    private TextField inputField;
    private Label resultLabel;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private Slider xRangeSlider;
    private Slider yRangeSlider;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Function Plotter");

        // Defining the x-axis (domain)
        xAxis = new NumberAxis(-2 * Math.PI, 2 * Math.PI, Math.PI / 4);
        xAxis.setLabel("X");

        // Defining the y-axis (range)
        yAxis = new NumberAxis(-1, 1, 0.1);
        yAxis.setLabel("Y");

        // Creating the line chart with the defined axes
        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Grapher");

        // Creating a ComboBox for function selection
        functionComboBox = new ComboBox<>();
        functionComboBox.getItems().addAll("Sine", "Cosine", "Tangent");
        functionComboBox.setValue("Sine");
        functionComboBox.setOnAction(event -> updatePlot());

        // Creating a TextField for input
        inputField = new TextField();
        inputField.setPromptText("Enter x value in degrees");

        // Creating a Label to display the result
        resultLabel = new Label("Y(x) = ");

        // Creating a Button to calculate the function value
        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(event -> calculateFunctionValue());

        // Creating sliders for adjusting axis bounds
        xRangeSlider = createRangeSlider(0, 3 * Math.PI, "X Range");
        yRangeSlider = createRangeSlider(0, 1.5, "Y Range");

        xRangeSlider.valueProperty().addListener((obs, oldVal, newVal) -> updateAxisBounds());
        yRangeSlider.valueProperty().addListener((obs, oldVal, newVal) -> updateAxisBounds());

        // Creating an HBox to hold the input field and button
        HBox inputBox = new HBox(10, inputField, calculateButton, resultLabel);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setPadding(new Insets(10));

        // Creating a VBox for the dropdown menu and chart
        VBox topBox = new VBox(10, functionComboBox, lineChart);
        topBox.setAlignment(Pos.TOP_LEFT);
        topBox.setPadding(new Insets(10));

        // Creating an HBox for the sliders
        VBox slidersBox = new VBox(10, createSliderBox("X Axis Range", xRangeSlider),
                createSliderBox("Y Axis Range", yRangeSlider));
        slidersBox.setAlignment(Pos.CENTER);
        slidersBox.setPadding(new Insets(10));

        // Creating a BorderPane as the root layout
        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(slidersBox);
        root.setBottom(inputBox);

        // Creating the scene with the BorderPane
        Scene scene = new Scene(root, 1000, 700);
        stage.setScene(scene);

        // Displaying the stage
        stage.show();

        // Initial plot update
        updatePlot();
    }

    private Slider createRangeSlider(double min, double max, String label) {
        Slider slider = new Slider(min, max, (max - min) / 2);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit((max - min) / 5);
        slider.setBlockIncrement((max - min) / 10);
        slider.setSnapToTicks(true);
        Label sliderLabel = new Label(label);
        VBox sliderBox = new VBox(sliderLabel, slider);
        sliderBox.setAlignment(Pos.CENTER);
        return slider;
    }

    private VBox createSliderBox(String labelText, Slider slider) {
        Label label = new Label(labelText);
        VBox box = new VBox(5, label, slider);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void updatePlot() {
        lineChart.getData().clear();
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        String selectedFunction = functionComboBox.getValue();

        switch (selectedFunction) {
            case "Sine":
                series.setName("sin(X)");
                for (double x = xAxis.getLowerBound(); x <= xAxis.getUpperBound(); x += 0.1) {
                    series.getData().add(new XYChart.Data<>(x, Math.sin(x)));
                }
                break;
            case "Cosine":
                series.setName("cos(X)");
                for (double x = xAxis.getLowerBound(); x <= xAxis.getUpperBound(); x += 0.1) {
                    series.getData().add(new XYChart.Data<>(x, Math.cos(x)));
                }
                break;
            case "Tangent":
                series.setName("tan(X)");
                for (double x = xAxis.getLowerBound(); x <= xAxis.getUpperBound(); x += 0.1) {
                    series.getData().add(new XYChart.Data<>(x, Math.tan(x)));
                }
                break;
        }

        lineChart.getData().add(series);
    }

    private void calculateFunctionValue() {
        try {
            double degrees = Double.parseDouble(inputField.getText());
            double radians = Math.toRadians(degrees);
            double value = 0;
            String selectedFunction = functionComboBox.getValue();

            switch (selectedFunction) {
                case "Sine":
                    value = Math.sin(radians);
                    resultLabel.setText("sin(" + degrees + "°) = " + value);
                    break;
                case "Cosine":
                    value = Math.cos(radians);
                    resultLabel.setText("cos(" + degrees + "°) = " + value);
                    break;
                case "Tangent":
                    value = Math.tan(radians);
                    resultLabel.setText("tan(" + degrees + "°) = " + value);
                    break;
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input, please enter a number");
        }
    }

    private void updateAxisBounds() {
        double xRange = xRangeSlider.getValue();
        double yRange = yRangeSlider.getValue();
        xAxis.setLowerBound(-xRange);
        xAxis.setUpperBound(xRange);
        yAxis.setLowerBound(-yRange);
        yAxis.setUpperBound(yRange);
        updatePlot();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
