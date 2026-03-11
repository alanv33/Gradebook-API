package com.project475;

import java.util.ArrayList;
import java.util.Scanner;

public class client {

    private server myDbServer;

    public client() {
        this.myDbServer = new server();
    }

    public void addAssignments_Client(Scanner sc) {
        System.out.println("Enter an assignment Name: ");
        String name = sc.nextLine();

        System.out.println("Enter a Course Number: ");
        int courseNum = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter a Grade Category Name: ");
        String gradeCatName = sc.nextLine();

        System.out.println("Enter a Due Date: ");
        String dueDate = sc.nextLine();

        System.out.println(myDbServer.addAssignments_Server(name, gradeCatName, dueDate, courseNum));
    }

    public void updateAssigment_Client(Scanner sc) {
        ArrayList<String> newVal = new ArrayList<String>();
        System.out.println("What is your assignment name: ");
        String name = sc.nextLine();

        System.out.println("What is your course number: ");
        int courseNum = sc.nextInt();
        sc.nextLine();

        System.out.println("Select a column to update by typing their number: ");
        System.out.println("1. Name");
        System.out.println("2. Category");
        System.out.println("3. Due Date");
        int column = sc.nextInt();
        sc.nextLine();

        if (column == 2) {
            System.out.println("What is the name of the category your changing to: ");
            String gradeCatName = sc.nextLine();
            newVal.add(gradeCatName);

            System.out.println(myDbServer.updateAssigment_Server(name, courseNum, String.valueOf(column), newVal));
        } else {
            System.out.println("What is the new value: ");
            String value = sc.nextLine();
            newVal.add(value);

            System.out.println(myDbServer.updateAssigment_Server(name, courseNum, String.valueOf(column), newVal));
        }
    }

    public void deleteAssignment_Client(Scanner sc) {
        System.out.println("What is your assignment name: ");
        String name = sc.nextLine();

        System.out.println("What is your course number: ");
        int courseNum = sc.nextInt();
        sc.nextLine();

        System.out.println(myDbServer.deleteAssignment_Server(name, courseNum));
    }

    public void listAllCourseAssignments_Client(Scanner sc) {
        System.out.println("What is your Course Number?: ");
        int courseNum = sc.nextInt();
        sc.nextLine();
        System.out.println(myDbServer.listAllCourseAssignments_Server(courseNum));
    }

    public void listCoursesForStudent_Client(Scanner sc) {
        System.out.println("What is your Student Number?: ");
        int studentNum = sc.nextInt();
        sc.nextLine();
        System.out.println(myDbServer.listCoursesForStudent_Server(studentNum));
    }
}