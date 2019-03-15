import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ColorTrap extends Application
{
    private Scene scene;
    private BorderPane borderPane;
    private Text txtCountDown = new Text();
    private Timeline timeline;
    private final int TIMER = 15;
    private int count = 0;

    private Text trapWord = new Text();
    private FlowPane fPane = new FlowPane();
    private Region bottomRegion = new Region();
    private Region bottomRegionL = new Region();
    private HBox hBoxTop = new HBox();
    private HBox bottomBox = new HBox();
    private final ColorsEnum[] colors = ColorsEnum.values();
    private Random randomNumber = new Random();
    private Text score;
    private int currentScore = 0;
    private Timeline backgroundTimeline;

    private Image correct = new Image("correct.png");
    private ImageView imageView = new ImageView();
    private Image wrong = new Image("wrong.png");
    private ArrayList<Text> wordArray = new ArrayList<Text>();
    private String[] flashCol = {"PINK", "BEIGE", "BURLYWOOD", "CYAN", "GOLD", "LAVENDER" };

    @Override
    public void start(Stage primaryStage)
    {
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightgrey");
        scene = new Scene(borderPane, 600, 300);
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(600);
        initializeGame();
        startPlay();

        primaryStage.setTitle("Color Trap");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startPlay()
    {
        chooseTrapWordAndColor();
        colorNameOptions();

        count = TIMER;
        txtCountDown.setText(TIMER + "");
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000), e -> {


                    if(count >= 0)
                    {
                        txtCountDown.setText(count + "");
                        count--;
                    }
                    else
                    {
                        endOfGame();
                    }
                }));
        timeline.setCycleCount(TIMER + 2);
        timeline.play();

        backgroundTimeline = new Timeline(new KeyFrame(Duration.millis(250), event -> borderPane.setStyle("-fx-background-color: " + flashCol[randomNumber.nextInt(flashCol.length)])));
        backgroundTimeline.setCycleCount(Timeline.INDEFINITE);
        backgroundTimeline.play();

    }
    
    public void endOfGame()
    {
            backgroundTimeline.pause();
            borderPane.setTop(null);
            borderPane.setCenter(null);
            borderPane.setBottom(null);

            fPane.getChildren().clear();
            wordArray.clear();

            VBox vbox = new VBox();
            Text userScore = new Text("Your score: " + currentScore);
            userScore.setFont(new Font(30));
            Button btn = new Button("Play again");
            vbox.getChildren().addAll(userScore,btn);
            vbox.setAlignment(Pos.CENTER);
            vbox.setSpacing(20);
            borderPane.setCenter(vbox);

            btn.setOnAction(event -> {
                borderPane.setCenter(null);
                borderPane.setBottom(bottomBox);
                borderPane.setTop(hBoxTop);
                borderPane.setCenter(fPane);
                currentScore = 0;
                score.setText("Score: " + currentScore);
                imageView.setImage(null);

                startPlay();
            });
    }


    public void checkChoice(Text choice)
    {
        if(trapWord.getFill().equals(Color.web(Color.valueOf(choice.getText()) +"")))
        {
            currentScore++;
            score.setText("Score: " + currentScore);
            imageView.setImage(correct);
        }
        else
        {
            imageView.setImage(wrong);
        }
        fPane.getChildren().clear();
        wordArray.clear();

        chooseTrapWordAndColor();
        colorNameOptions();
    }

    public void chooseTrapWordAndColor()
    {
        trapWord.setText(colors[randomNumber.nextInt(colors.length)] + "");
        trapWord.setFont(new Font("Marker Felt", 60));
        trapWord.setFill(Color.valueOf(colors[randomNumber.nextInt(colors.length)] + ""));
        trapWord.setTextAlignment(TextAlignment.CENTER);
        borderPane.setTop(hBoxTop);
    }
    
    public void colorNameOptions()
    {
        // Create new text objects with the color array names
        // Add a listener to each text object
        for(int i = 0; i < colors.length; i++)
        {
            Text text = new Text(colors[i] + "");
            text.setFont(new Font("Marker Felt", 40));
            wordArray.add(text);
            text.setOnMouseClicked(event -> {
                checkChoice(text);
            });
        }

        // shuffle the arrayList and add the whole list to the flow pane
        Collections.shuffle(wordArray);
        for (Text t: wordArray) {
            fPane.getChildren().add(t);
        }

        // shuffle the arrayList so that you can get different color fills
        Collections.shuffle(wordArray);
        for(int i = 0; i < colors.length; i++)
        {
            wordArray.get(i).setFill(Color.valueOf(colors[i] + ""));
        }
    }

    public void initializeGame()
    {
        // Setting up flow pane for the center region
        fPane.setHgap(10);
        fPane.setOrientation(Orientation.HORIZONTAL);
        fPane.setAlignment(Pos.CENTER);
        fPane.setPrefHeight((borderPane.getHeight()*55)/100);
        fPane.setPadding(new Insets(0,40,0,40));

        hBoxTop.getChildren().add(trapWord);
        hBoxTop.setAlignment(Pos.CENTER);
        hBoxTop.setPrefHeight((borderPane.getHeight()*35)/100);

        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        // Setting up the bottom for the timer and the score
        score = new Text("Score: " + currentScore);
        score.setFont(new Font(20));

        txtCountDown.setFont(new Font(20));
        Text time = new Text("Timer: ");
        time.setFont(new Font(20));
        bottomBox.getChildren().addAll(score, bottomRegionL, imageView, bottomRegion, time, txtCountDown);
        bottomBox.setHgrow(bottomRegion, Priority.ALWAYS);
        bottomBox.setHgrow(bottomRegionL, Priority.ALWAYS);
        bottomBox.setPadding(new Insets(0,5,0,5));
        bottomBox.setPrefHeight((borderPane.getHeight()*10)/100);


        borderPane.setTop(hBoxTop);
        borderPane.setMargin(trapWord, new Insets(10));
        borderPane.setCenter(fPane);
        borderPane.setBottom(bottomBox);
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}