/**Name : Suman Kafle
 * CS 351 Project 1 : CirclePoints
 *
 * Project Description:
 * Different method like makeCircle, pointDivider, pointConnector are made to make the circle in
 * 2D draphics named as gc. AnimationTimer are used to provide animation on the circle as well as text are provided
 * to make easy to know what should be entered in textLayout. Two slider are made one is to change the speed of the
 * colors being change and other slider fps of increment rate given by the user. Start and Stop button  is associated
 * with the increment rate while the pause and play button are for the colors.
 *
 * Instructions:
 * Press enter on every Layout to update the number
 *  **/

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Slider;
import javafx.stage.Stage;


public class CirclePoints extends Application {

    // global variables are declared and some of them are initialized with certain values
    // Center X coordinate
    double centerX = 400;

    // Center Y coordinate
    double centerY = 400;

    // radius for the circle
    double radius = 200;
    int numPoints = 0;
    double incrementPoints = 0.0;

    // array to store the x coordinates
    double[] pointX = new double[1000];

    // array to store the y coordinates
    double[] pointY = new double[1000];
    double []multiplier = new double[1];

    //color are provided and saved in array of the Color
    final Color[] predefinedColors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PURPLE,
            Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.BROWN, Color.GRAY, Color.PINK, Color.LIME, Color.TEAL,
            Color.OLIVE,Color.SLATEBLUE,Color.DARKMAGENTA, Color.NAVY};
    int currentColorIndex = 0;
    long lastColorChangeTime = 0;
    long colorChangeInterval = 1000000000L;
    long finalFrame = 0;
    double frameTime = 1.0;
    double pace = 1.0;
    AnimationTimer animationTimer = null;
    Canvas canvas = new Canvas(800,800);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    public void start(Stage primaryStage) {
        // makeCircle is called to make the circle
        makeCircle(gc);
        // pointsDivider is called
        pointsDivider(gc);
        //overlayPane is created
        Pane overlayPane = new Pane();
        // textField named as pointsNeeded is created
        //certain x and y coordinates are provided and promptText is provided
        TextField pointsNeeded = new TextField();
        pointsNeeded.setPromptText("Enter number of points");
        pointsNeeded.setLayoutX(170);
        pointsNeeded.setLayoutY(150);

        // textField named as multiNeeded is created
        //certain x and y coordinates are provided and promptText is provided
        TextField multiNeeded = new TextField();
        multiNeeded.setPromptText("Enter the multiplier");
        multiNeeded.setLayoutX(170);
        multiNeeded.setLayoutY(100);

        // textField named as incrementNeeded is created
        //certain x and y coordinates are provided and promptText is provided
        TextField incrementNeeded = new TextField();
        incrementNeeded.setPromptText("Enter any numbers");
        incrementNeeded.setLayoutX(170);
        incrementNeeded.setLayoutY(50);

        // play and pause button are created to pause and play the color
        Button playButton = new Button("Play");
        Button pauseButton = new Button("Pause");
        playButton.setLayoutX(510);
        playButton.setLayoutY(50);
        pauseButton.setLayoutX(560);
        pauseButton.setLayoutY(50);

        // start and stop button are created to start and stop the increment rate
        Button startbutton = new Button("Start");
        Button stopbutton = new Button("Stop");
        startbutton.setLayoutX(340);
        startbutton.setLayoutY(50);
        stopbutton.setLayoutX(390);
        stopbutton.setLayoutY(50);

        //colorChangingSpeedSlider is created and provided with initial value of 5 and x and y coordinates are provided
        Slider colorChangingSpeed = new Slider(1,10 , 5);
        colorChangingSpeed.setShowTickLabels(true);
        colorChangingSpeed.setShowTickMarks(true);
        colorChangingSpeed.setLayoutX(100);
        colorChangingSpeed.setLayoutX(500);

        //fpsSlider is created and provided with initial value of 25 and x and y coordinates are provided
        Slider fpsSlider = new Slider(1,50 , 25);
        fpsSlider.setShowTickLabels(true);
        fpsSlider.setShowTickMarks(true);
        fpsSlider.setLayoutX(100);
        fpsSlider.setLayoutX(150);

        //label are created to with proper x and y coordinates
        Label label = new Label("Rate of increment goes here ->");
        label.setLayoutX(0);
        label.setLayoutY(50);

        Label label1 = new Label("FPS slider ->");
        label1.setLayoutX(20);
        label1.setLayoutY(0);

        Label label2 = new Label("Multiplier goes here ->");
        label2.setLayoutX(20);
        label2.setLayoutY(100);

        Label label3 = new Label("Number of points goes here ->");
        label3.setLayoutX(0);
        label3.setLayoutY(150);

        Label label4 = new Label("Pause and play for the color only");
        label4.setLayoutX(500);
        label4.setLayoutY(90);

        Label label5 = new Label("Slider to change the speed of colorChanging");
        label5.setLayoutX(500);
        label5.setLayoutY(120);


        //button, slider, label and layout are being added to the pane
        overlayPane.getChildren().addAll(pointsNeeded,multiNeeded,incrementNeeded);
        overlayPane.getChildren().addAll(playButton,pauseButton,stopbutton,startbutton);
        overlayPane.getChildren().addAll(colorChangingSpeed,fpsSlider);
        overlayPane.getChildren().addAll(label,label1,label2,label3,label4,label5);

        //when the user input the value in pointsNeeded textLayout the value is converted into the integer
        //then it call the  makeCircle to make circle and pointConnector to connect the points
        // as well as pointsDivider to divide the circle into equal part
        // if the wrong format is given it catch the error and provide message to the user

        pointsNeeded.setOnAction(event -> {
            try {
                numPoints = Integer.parseInt(pointsNeeded.getText());
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                makeCircle(gc);
                pointsDivider(gc);
                gc.setFill(Color.WHITE);
                pointConnector(gc);
                System.out.println("Num of points: " + numPoints);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid integers.");
            }

        });

        //when the user input the value in multiNeeded textLayout the value is converted into the double
        //then it call the  makeCircle to make circle and pointConnector to connect the points
        // if the wrong format is given it catch the error and provide message to the user

        multiNeeded.setOnAction(event -> {
            try {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                makeCircle(gc);
                multiplier[0] = Double.parseDouble(multiNeeded.getText());
                pointConnector(gc);
            } catch (NumberFormatException e){
                System.out.println("Invalid number");
            }
        });

        //when the user input the value in incrementNeeded textLayout the value is converted into the double
        //then it call the startAnimation to start the animation
        // if the wrong format is given it catch the error and provide message to the user
        incrementNeeded.setOnAction(event -> {
            try {
                incrementPoints = Double.parseDouble(incrementNeeded.getText());
                System.out.println("Num of points: " + incrementPoints);
                startAnimation();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter valid decimals.");
            }
        });

        //colorChanger animationTimer is created to change the color of the lines in the circle
        AnimationTimer colorChanger = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                //changeColors method is called if the condition matched
                if (currentTime - lastColorChangeTime >= colorChangeInterval) {
                    changeColors();
                    // lastColorChangeTime is set equal to the currentTime
                    lastColorChangeTime = currentTime;
                    pointConnector(gc);
                }
            }
        };

        //to keep changing the color when the playButton is clicked
        playButton.setOnAction(event -> colorChanger.start());
        //to pause changing the color when the playButton is clicked
        pauseButton.setOnAction(event -> colorChanger.stop());
        //to keep running the increasing rate when the startButton is clicked
        startbutton.setOnAction(startEvent -> startAnimation());
        //to stop running the increasing rate when the stopButton is clicked
        stopbutton.setOnAction(stopEvent -> {
            if (animationTimer != null) {
                animationTimer.stop();
            }
        });

        //value of being updated for the colorChangingSlider everytime it is slided
        colorChangingSpeed.valueProperty().addListener((observable, oldValue, newValue) -> {
            double speed = newValue.doubleValue();
            colorChangeInterval = (long) (2000000000L / speed);
        });

        //value of being updated for the fpsSlider everytime it is slided
        fpsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double fpsValue = newValue.doubleValue();
            //  pace is updated to control animation speed
            pace = frameTime * 60.0 / fpsValue;
        });

        //title is set for the primary stage
        primaryStage.setTitle("Circle Points");
        //canvas and overlayPane are added on the scene with the help of stackPane
        primaryStage.setScene(new javafx.scene.Scene(new javafx.scene.layout.StackPane(canvas,overlayPane)));
        primaryStage.show();

        // makeCircle pointConnector and colorChanger method are called
        makeCircle(gc);
        pointConnector(gc);
        colorChanger.start();

    }

    /* makeCircle method is made
     * circle border are initially provided with red color
     * inside the circle white color is provided*/
    public void  makeCircle(GraphicsContext gc){
        Circle circle = new Circle(centerX, centerY, radius);
        circle.setStroke(Color.RED);
        circle.setFill(Color.WHITE);
        gc.strokeOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }

    /* pointsDivider function is made
     * for loop keeps running until it is smaller than the numPoints which is the input from user
     * double angle is made to divide the circle in equal angles
     * double x and y are created to store the x and y coordinates of the points to be connected, they are
     * stored in array pointX and pointY
     * black color is provides to them*/
    public void pointsDivider (GraphicsContext gc) {
        for (int i = 0; i <= numPoints; i++) {
            double angle = 2 * Math.PI*i / numPoints;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            pointX[i] = x;
            pointY[i] = y;
            gc.setFill(Color.BLACK);
            gc.fillOval(x - 2, y - 2, 4, 4);

        }
    }

    /* pointConnector method is made
     * for loop is running till  it is smaller than the numPoints which is global variable
     * nextPoint to be added is multiplied by the multiplier which we get form the user
     * if condition is provided to check if there are possible points to be added on the circle
     * if not they will be added to the modulos of the input number from the user
     * color is then provided to the lines joining the points of the circle
     * their width is set
     * X and Y coordinates of the points if circle is provided to join them */
    public  void pointConnector(GraphicsContext gc) {
        for (int j = 0; j < numPoints; j++) {
            double nexpoint = j * multiplier[0];
            if (nexpoint > numPoints) {
                nexpoint = nexpoint % numPoints;
            }
            gc.setStroke(predefinedColors[currentColorIndex]);
            gc.setLineWidth(2);
            gc.strokeLine(pointX[j],pointY[j], pointX[(int)nexpoint],pointY[(int)nexpoint]);
        }
    }

    /* changeColors function is made used to change the color of the lines joining the circles
     * current color index is being added to get the new color from the array of the color*/
    public void changeColors() {
        currentColorIndex = (currentColorIndex + 1) % predefinedColors.length;
    }

    /* startAnimation method is made to start the animation
     * a new animation timer is set and double value show is made that depend upon the global variables
     * to keep running the animation a true condition is provided where
     *  double show is greater or equal to  frameTime / speedFactor
     * the previous structure are being cleared
     * the increment value is being added to the multiplier, it goes on increasing every single time
     *  the new circle with points are being connected with the help of the function called here
     * finally the animation is started */
    public void startAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double show = (now - finalFrame) / 1e9;
                if (show >= frameTime / pace) {
                    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    multiplier[0] += incrementPoints;
                    makeCircle(gc);
                    pointsDivider(gc);
                    gc.setFill(Color.WHITE);
                    pointConnector(gc);
                    finalFrame = now;
                }
            }
        };
        animationTimer.start();
    }

    /* main method is used to run the program */
    public static void main(String[] args) {
        launch(args);
    }
}

