import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Grapher extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sine Wave Plot");

        // Defining the x-axis (domain)
        final NumberAxis xAxis = new NumberAxis(-2 * Math.PI, 2 * Math.PI, Math.PI / 4);
        xAxis.setLabel("X");

        // Defining the y-axis (range)
        final NumberAxis yAxis = new NumberAxis(-1, 1, 0.1);
        yAxis.setLabel("sin(X)");

        // Creating the line chart with the defined axes
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Sine Wave");

        // Defining a series to plot
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("sin(X)");

        // Populating the series with data
        for (double x = -2 * Math.PI; x <= 2 * Math.PI; x += 0.1) {
            series.getData().add(new XYChart.Data<>(x, Math.sin(x)));
        }

        // Adding the series to the line chart
        lineChart.getData().add(series);

        // Creating the scene with the line chart
        Scene scene = new Scene(lineChart, 800, 600);
        stage.setScene(scene);

        // Displaying the stage
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
