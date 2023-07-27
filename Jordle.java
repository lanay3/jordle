import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Translate;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import java.util.Random;


public class Jordle extends Application {

    Label[][] labelrows = new Label[6][];
    Rectangle[][] rectanglesrows = new Rectangle[6][];
    int column = 0;
    int row = 0;
    int correctlettercount = 0;
    
    
    public static void main(String[] args) {
        launch(args);
    }

    public static GridPane grid(Label[][] labelrows, Rectangle[][] rectanglesrows) {
        GridPane gridpane = new GridPane();
        for (int j = 1; j <= 6; j++) {
            Label[] row = new Label[5];
            Rectangle[] rectrow = new Rectangle[5];
            for (int i = 1; i <= 5; i++) {
                Label label = new Label("");
                StackPane stackpane = new StackPane();
                Rectangle rectangle = new Rectangle(0, 0, 82, 84);
                rectangle.setStroke(Color.GAINSBORO);
                rectangle.setFill(Color.WHITE);
                label.setMaxHeight(50);
                label.setFont(new Font("Helvetica", 55));
                label.setAlignment(Pos.CENTER);
                stackpane.getChildren().add(rectangle);
                stackpane.getChildren().add(label);
                stackpane.setAlignment(Pos.CENTER);
                row[i - 1] = label;
                rectrow[i - 1] = rectangle;
                gridpane.add(stackpane, i, j);
            }
            labelrows[j - 1] = row;
            rectanglesrows[j - 1] = rectrow;
        }
        gridpane.setHgap(8);
        gridpane.setVgap(8);
        gridpane.setAlignment(Pos.CENTER);
        return gridpane;
    }

    public void start(Stage primaryStage) {
        String[] wordbank = {"alert", "bound", "break", "clear", "close", "codes", "enums", "false", "files", "final", "float", "index",
        "inset", "logic", "mouse", "nodes", "pixel", "print", "scope", "short", "stack", "stage", "super", "throw",
        "token", "value", "while", "world", "write"};
        Random random = new Random();
        String word = wordbank[random.nextInt(wordbank.length)];
        char[] chararray = word.toCharArray();


        GridPane gridpane = grid(labelrows, rectanglesrows);
        gridpane.setAlignment(Pos.CENTER);
        VBox vbox = new VBox();
        StackPane stackpane = new StackPane();
        Text text = new Text(10, 10, "Jordle");
        text.setFont(Font.font("Gothic", FontWeight.BOLD, 50));
        stackpane.getChildren().add(text);
        stackpane.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(stackpane, gridpane);
        gridpane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 800, 800);
        primaryStage.setTitle("Jordle");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed((KeyEvent e) -> {
            if (correctlettercount < 5) {
                switch(e.getCode()) {
                    case ENTER:
                        if (column == 5) {
                            column = 0;
                            int[] freq = new int[labelrows[row].length];
                            int[] countletter = new int[chararray.length];
                            for (int i = 0; i < labelrows[row].length; i++) {
                                freq[i] = 1;
                                for (int j = 0; j < chararray.length; j++) {
                                    System.out.println("Guess: " + labelrows[row][i].getText().toLowerCase().charAt(0));
                                    System.out.println("Index of Guess: " + i);
                                    System.out.println("char: " + (chararray[j]));
                                    System.out.println("Index of char: " + j);
                                    if (labelrows[row][i].getText().toLowerCase().charAt(0) == (chararray[j])) {
                                        if (i == j) {
                                            rectanglesrows[row][i].setFill(Color.GREEN);
                                            correctlettercount++;
                                        } else {
                                            for (int n = 0; n < countletter.length; n++) {
                                                if (freq[i] == countletter[j]) {
                                                    rectanglesrows[row][i].setFill(Color.YELLOW);
                                                }
                                            }
                                        }
                                        freq[i]++;
                                        countletter[j]++;
                                    } else {
                                        if (rectanglesrows[row][i].getFill().equals(Color.GREEN)) {
                                            continue;
                                        } else if (rectanglesrows[row][i].getFill().equals(Color.YELLOW)) {
                                            continue;
                                        } else {
                                            rectanglesrows[row][i].setFill(Color.GREY);
                                        }
                                    }

                                labelrows[row][i].setTextFill(Color.WHITE);
                                }
                            }
                                
                        }
                            labelrows[row][column].requestFocus();
                            row++;
                        if (correctlettercount != 5) {
                            correctlettercount = 0;
                        }
                        break;
                    case BACK_SPACE:
                        if (column > 0) {
                            column--;
                            labelrows[row][column].setText("");
                            labelrows[row][column].requestFocus();
                        }
                        break;
                    default:
                        if (Character.isLetter(e.getText().charAt(0))) {
                            if (column < 5) {
                                labelrows[row][column].setText(e.getText().toUpperCase());
                                labelrows[row][column].requestFocus();
                                column++;
                            }
                        }             
                    } 
            }
        });   
            
    }

}