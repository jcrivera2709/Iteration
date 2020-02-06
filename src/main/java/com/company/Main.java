package com.company;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

  private static final String CSV_FILE = "src//main//resources//SEOExample.csv";

  public static void main(String[] args) throws IOException, CsvValidationException {
    CSVReader reader = new CSVReader(new FileReader(CSV_FILE));

    // Read Line by Line
    String[] read;
    while ((read = reader.readNext()) != null) {
      System.out.println("Name: " + read[0]);
      System.out.println("Date: " + read[1]);
      System.out.println("EntryPoint: " + read[2]);
      System.out.println("Region: " + read[3]);
      System.out.println("Location: " + read[4]);
      System.out.println("Accepted: " + read[5]);
      System.out.println("Enrolled: " + read[6]);
      System.out.println("SearchTerms: " + read[7]);
      System.out.println("Engine: " + read[8]);
      System.out.println("Reason: " + read[9]);

    }
  }
}
