package com.github.chandanv89.javafx.jsonvalidator.logic;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The type Json parser.
 */
public class JsonParser {
   private boolean parsedWithErrors;
   private String given;
   private String jsonAsCompressedString;
   private String jsonAsPrettyPrint;
   private JsonObject json;
   private String parseMessage;
   private Exception parseExeption;
   private boolean validated;

   /**
    * Instantiates a new Json parser.
    */
   public JsonParser() {
      this.init();
   }

   /**
    * Parse.
    *
    * @param jsonString the json string
    */
   public void parse(String jsonString) {
      if (jsonString == null || jsonString.isEmpty()) {
         this.parseMessage = "Empty input!";
         this.json = null;
         return;
      }

      com.google.gson.JsonParser parser = new com.google.gson.JsonParser();

      try {
         JsonElement jsonElement = parser.parse(jsonString);
         this.jsonAsCompressedString = jsonElement.toString();
         this.json = jsonElement.getAsJsonObject();
         this.jsonAsPrettyPrint = new GsonBuilder().setPrettyPrinting().create().toJson(this.json);
         this.parseExeption = null;
         this.parseMessage = "Parse successful";
         this.validated = true;
      } catch (Exception e) {
         this.validated = true;
         this.parsedWithErrors = true;
         this.parseMessage = e.getMessage();
         this.parseExeption = e;
         this.jsonAsCompressedString = null;
         this.jsonAsPrettyPrint = null;
         this.json = null;
      }
   }

   /**
    * Init.
    */
   public void init() {
      this.validated = false;
      this.parseMessage = "";
      this.parsedWithErrors = false;
      this.given = "";
      this.jsonAsCompressedString = "";
      this.jsonAsPrettyPrint = "";
      this.json = new JsonObject();
      this.parseExeption = new Exception();
   }

   /**
    * Is validated boolean.
    *
    * @return the boolean
    */
   public boolean isValidated() {
      return this.validated;
   }

   /**
    * Is parsed with errors boolean.
    *
    * @return the boolean
    */
   public boolean isParsedWithErrors() {
      return this.parsedWithErrors;
   }

   /**
    * Gets given.
    *
    * @return the given
    */
   public String getGiven() {
      return this.given;
   }

   /**
    * Gets json as pretty print.
    *
    * @return the json as pretty print
    */
   public String getJsonAsPrettyPrint() {
      return this.jsonAsPrettyPrint;
   }

   /**
    * Gets json as compressed string.
    *
    * @return the json as compressed string
    */
   public String getJsonAsCompressedString() {
      return this.jsonAsCompressedString;
   }

   /**
    * Gets json.
    *
    * @return the json
    */
   public JsonObject getJson() {
      return this.json;
   }

   /**
    * Gets parse exeption.
    *
    * @return the parse exeption
    */
   public Exception getParseExeption() {
      return this.parseExeption;
   }

   /**
    * Gets parse message.
    *
    * @return the parse message
    */
   public String getParseMessage() {
      return parseMessage;
   }
}
