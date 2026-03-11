package com.project475;

import java.util.ArrayList;
import java.util.Scanner;

public class client {

    private server myDbServer;

    public client() {
        this.myDbServer = new server();
    }

    public void addAssignments_Client() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter an assignment Name: ");
        String name = sc.nextLine();
        System.out.println("Enter a Course Number: ");
        int courseNum = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter a Grade Category Name: ");
        String gradeCatName = sc.nextLine();
        System.out.println("Enter a Due Date: ");
        String dueDate = sc.nextLine();

        myDbServer.addAssignments(name, gradeCatName, dueDate, courseNum);

        sc.close();
    }

    public void updateAssigment_Client() {
        ArrayList<String> newVal = new ArrayList<String>();
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your assignment name: ");
        String name = sc.nextLine();
        System.out.println("Select a column to update by typing their number: ");
        System.out.println("1. Name");
        System.out.println("2. Category");
        System.out.println("3. Due Date");
        int column = sc.nextInt();
        if (column == 2) {
            System.out.println("What is your course number: ");
            int courseNum = sc.nextInt();
            System.out.println("What is the name of the category your changing to: ");
            String gradeCatName = sc.nextLine();
            newVal.add(gradeCatName);
            newVal.add(String.valueOf(courseNum));
            myDbServer.updateAssigment(name, String.valueOf(column), newVal);
        } else {
            System.out.println("What is the new value: ");
            String value = sc.nextLine();
            newVal.add(value);
            myDbServer.updateAssigment(name, String.valueOf(column), newVal);
        }
        sc.close();
    }

    public void deleteAssignment() {
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your assignment name: ");
        String name = sc.nextLine();
        System.out.println("What is your course name: ");
        int courseNum = sc.nextInt();
        myDbServer.deleteAssignment(name, courseNum);
        sc.close();
    }

    public void listAllCourseAssignments() {
        Scanner sc = new Scanner(System.in);
    }
}
