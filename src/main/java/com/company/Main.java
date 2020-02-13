package com.company;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

  static Connection conn;
  private static final String CSV_FILE = "src/Data/bookstore_report2.csv";

  public Main() throws FileNotFoundException {
  }

  public static void main(String[] args) throws IOException, CsvValidationException {
    // Literally just calls our parser right now (....and is called for tests)
    connectDB();
    CsvParser csvP = new CsvParser("src/Data/bookstore_report2.csv");
    csvP.printCsv();

    // Load the json
        /*
        1. Create instance of GSON
        2. Create a JsonReader object using FileReader
        3. Array of class instances of AuthorParser, assign data from our JsonReader
        4. foreach loop to check data
         */
    Gson gson = new Gson();
    JsonReader jread = new JsonReader(new FileReader("src/Data/authors.json"));
    AuthorParser[] authors = gson.fromJson(jread, AuthorParser[].class);

    for (var element : authors) {
      String tempName = element.getName();
      String tempEmail = element.getEmail();
      String tempUrl = element.getUrl();

      try {
        String SQL_INSERT = "INSERT INTO author (author_name,author_email,author_url) VALUES (?,?,?)";
        PreparedStatement prpStmt = conn.prepareStatement(SQL_INSERT);
        prpStmt.setString(1, tempName);
        prpStmt.setString(2, tempEmail);
        prpStmt.setString(3, tempUrl);

        prpStmt.executeUpdate();

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    CSVReader reader = new CSVReader(new FileReader(CSV_FILE));

    // Read Line by Line
    String[] read;
    while ((read = reader.readNext()) != null) {
      // Print out results.
      String tempISBN = read[0];
      String tempTitle = read[1];
      String tempAuthor = read[2];
      String tempPublisher = read[3];

      // No place to store yet.
      String tempStore = read[4];
      String tempLocation = read[5];

      try {
        // Prepared Statement that inserts information about the book from the bookstore_report2.csv file
        String SQL_INSERT = "INSERT INTO book (isbn, book_title, author_name, publisher_name) VALUES (?,?,?,?)";
        PreparedStatement prpStmt = conn.prepareStatement(SQL_INSERT);
        prpStmt.setString(1, tempISBN);
        prpStmt.setString(2, tempTitle);
        prpStmt.setString(3, tempAuthor);
        prpStmt.setString(4, tempPublisher);

        prpStmt.executeUpdate();

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static void connectDB() {
    conn = null;
    try {
      // db parameters
      String url = "jdbc:sqlite:src/Data/BookStore.db";
      // create a connection to the database
      conn = DriverManager.getConnection(url);

      System.out.println("Connection to SQLite has been established.");

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

}
