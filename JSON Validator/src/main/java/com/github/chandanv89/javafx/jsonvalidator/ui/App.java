package com.github.chandanv89.javafx.jsonvalidator.ui;

import com.github.chandanv89.javafx.jsonvalidator.logic.JsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The type App.
 */
public class App extends Application {
   private static final double APP_WIDTH = 1024;
   private static final double APP_HEIGHT = 800;
   private static final double TOOLBAR_HEIGHT = 40;
   private static final double INPUT_AREA_HEIGHT = ((APP_HEIGHT - TOOLBAR_HEIGHT) * 40) / 100.0;
   private static final double OUTPUT_AREA_HEIGHT = ((APP_HEIGHT - TOOLBAR_HEIGHT) * 60) / 100.0;
   private static final String DEFAULT_INPUT_TEXTAREA_MSG = "{\"glossary\":{\"title\":\"example glossary\",\"GlossDiv\":" +
           "{\"title\":\"S\",\"GlossList\":{\"GlossEntry\":{\"ID\":\"SGML\",\"SortAs\":\"SGML\",\"GlossTerm\":" +
           "\"Standard Generalized Markup Language\",\"Acronym\":\"SGML\",\"Abbrev\":\"ISO 8879:1986\",\"GlossDef\":{" +
           "\"para\":\"A meta-markup language, used to create markup languages such as DocBook.\",\"GlossSeeAlso\":[" +
           "\"GML\",\"XML\"]},\"GlossSee\":\"markup\"}}}}}";

   private JsonParser parser;

   /**
    * Instantiates a new App.
    */
   public App() {
      super();
      parser = new JsonParser();
   }

   @Override
   public void start(Stage stage) {
      stage.setTitle("JSON Validator");

      // Input TextArea
      TextArea taInput = new TextArea();
      taInput.setText(DEFAULT_INPUT_TEXTAREA_MSG);
      taInput.setWrapText(true);
      taInput.setPrefHeight(INPUT_AREA_HEIGHT);

      // Tooltips for the action buttons
      Tooltip ttValidateBtn = new Tooltip("Parse and validate the input JSON");
      Tooltip ttSortBtn = new Tooltip("TODO: Alphabetically sort the JSON elements");
      Tooltip ttCompressBtn = new Tooltip("Remove whitespaces from the input JSON");

      // Action buttons
      Button btnValidate = new Button("Validate");
      btnValidate.setTooltip(ttValidateBtn);
      btnValidate.setPrefWidth(100);

      Button btnSort = new Button("Sort");
      btnSort.setTooltip(ttSortBtn);
      btnSort.setPrefWidth(100);
      btnSort.setDisable(true); // TODO implement sorting

      Button btnCompress = new Button("Compress");
      btnCompress.setTooltip(ttCompressBtn);
      btnCompress.setPrefWidth(100);

      // Enable/disable action buttons based on the input TextArea
      taInput.textProperty().addListener(event -> {
         if (taInput.getText() == null
                 || taInput.getText().isEmpty()) {
            btnValidate.setDisable(true);
            btnSort.setDisable(true);
            btnCompress.setDisable(true);
         } else {
            btnValidate.setDisable(false);
            btnSort.setDisable(true); //TODO implement sorting
            btnCompress.setDisable(false);
         }
      });

      // HBox layout for the Toolbar
      HBox hBox = new HBox(btnValidate, new Separator(), btnSort, new Separator(), btnCompress);
      hBox.setPrefHeight(TOOLBAR_HEIGHT);
      hBox.setAlignment(Pos.CENTER);

      // Toolbar holds the action buttons
      ToolBar toolBar = new ToolBar(hBox);

      // The output TextArea
      TextArea taResult = new TextArea();
      taResult.setWrapText(true);
      taResult.setEditable(false);
      taResult.setFont(javafx.scene.text.Font.font("Consolas"));
      taResult.setPrefHeight(OUTPUT_AREA_HEIGHT);

      // Define events for the action buttons
      btnValidate.setOnAction(event -> {
         taResult.setText("Validating the input...");
         try {
            parser.init();
            parser.parse(taInput.getText());
            if (parser.isParsedWithErrors())
               taResult.setText(parser.getParseMessage());
            else
               taResult.setText(parser.getJsonAsPrettyPrint());
         } catch (Exception e) {
            taResult.setText("Something went wrong! Please check the input: " + e);
         }
      });

      btnSort.setOnAction(event -> {
         try {
            if (!parser.isValidated())
               parser.parse(taInput.getText());

            if (!parser.isParsedWithErrors()) {

               taResult.setText(new com.google.gson.Gson().toJson(parser.getJson().getAsJsonObject()));
            } else {
               taResult.setText("Failed to parse the input! " + parser.getParseMessage());
            }
            Gson gson = new Gson();
            JsonElement json = gson.fromJson(parser.getJson().getAsJsonObject(), JsonElement.class);
            Iterator<JsonElement> iterator = json.getAsJsonArray().iterator();
            List<JsonElement> jsonMembers = new ArrayList<>();

            while (iterator.hasNext())
               jsonMembers.add(iterator.next());

            taResult.setText(jsonMembers.toString());
         } catch (Exception e) {
            taResult.setText(e.getMessage());
         }
      });

      btnCompress.setOnAction(event -> {
         try {
            if (!parser.isValidated())
               parser.parse(taInput.getText());

            if (!parser.isParsedWithErrors()) {
               taResult.setText(parser.getJsonAsCompressedString());
            } else {
               taResult.setText("Failed to parse the input! " + parser.getParseMessage());
            }
         } catch (Exception e) {
            taResult.setText("Something went wrong! Please check the input: " + e);
         }
      });

      // The VBox contains all the other controls in the App
      VBox vBox = new VBox(taInput, toolBar, taResult);
      vBox.setFillWidth(true);
      vBox.setMaxSize(APP_WIDTH, APP_HEIGHT);

      // Finally, display the UI!
      Scene scene = new Scene(vBox, APP_WIDTH, APP_HEIGHT);

      stage.setResizable(false);
      stage.setScene(scene);
      stage.show();
   }
}
