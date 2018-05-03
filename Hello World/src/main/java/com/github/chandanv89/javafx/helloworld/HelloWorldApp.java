package com.github.chandanv89.javafx.helloworld;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

public class HelloWorldApp extends Application {

   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) {
      AtomicInteger count = new AtomicInteger();
      Button btn = new Button();
      btn.setText("Say Hello!");
      btn.setOnAction(event -> {
         System.out.println("Hello World!");
         switch (count.get()) {
            case 0:
               btn.setText("Hi!");
               break;
            case 1:
               btn.setText("Umm...");
               break;
            case 2:
               btn.setText("OK!");
               break;
            case 3:
               btn.setText("That's enough!");
               btn.setDisable(true);
               break;
         }
         count.getAndIncrement();
      });

      StackPane root = new StackPane();
      root.getChildren().add(btn);

      Scene scene = new Scene(root, 300, 250);

      primaryStage.setTitle("Hello World!");
      primaryStage.setScene(scene);
      primaryStage.show();
   }
}
