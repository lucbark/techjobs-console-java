package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);

    public static void main (String[] args) { //method 1

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>(); // hashmap of the columns in the .csv file
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");




        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>(); //hashmap that prompts user to search or select from list
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");
        //actionChoices.put("random", "Random"); practicing how to add additional choice options to the hashmap

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices); //searchField parameter in Line 68

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();// User enters their searchTerm and is a parameter in Line 68

                if (searchField.equals("all")) {
                    //System.out.println("Search all fields not yet implemented."); Search all fields is implemented on the line below
                    printJobs(JobData.findByValue(searchTerm)); // I want findByValue to go here
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm)); //printJobs method is being used here

                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) { //method 2 takes 2 parameters a String and a Hashmap with key values equalling strings

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> relevantJobs) { //method 3

        //System.out.println("****"); //nested loop for the hashmap
        if(relevantJobs.size() == 0) {
            System.out.println("Does not exist. Try again...");
        }

        for (HashMap<String, String> specificJob : relevantJobs ) {
            System.out.println("****");
            for (String jobKey : specificJob.keySet()) {
                System.out.println(jobKey + ": " + specificJob.get(jobKey));


            }
            System.out.println("****");
        }
        //System.out.println("****");
    }
}
