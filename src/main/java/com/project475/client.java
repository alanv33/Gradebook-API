/**
 * This class is the client side for the gradebook API
 * This class utilizes the Server Class to access the database for changes
 */
package com.project475;

import java.util.Scanner;

public class client {

    private server myDbServer;

    // Connects the client to the server
    public client() {
        this.myDbServer = new server();
    }
    /**
     * Adds an assignment to the server based on client inputs
     * @param sc Scanner object to avoid repeated scanner creations
     */
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

    /**
     * Updates an assignment from the server
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void updateAssigment_Client(Scanner sc) {
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

            System.out.println(myDbServer.updateAssigment_Server(name, courseNum, String.valueOf(column), gradeCatName));
        } else {
            System.out.println("What is the new value: ");
            String value = sc.nextLine();

            System.out.println(myDbServer.updateAssigment_Server(name, courseNum, String.valueOf(column), value));
        }
    }

    /**
     * Deletes an assignment from the server based on the users inputs
     * @param sc Scanner object to avoid repeated scanner creation
     */
    public void deleteAssignment_Client(Scanner sc) {
        System.out.println("What is your assignment name: ");
        String name = sc.nextLine();

        System.out.println("What is your course number: ");
        int courseNum = sc.nextInt();
        sc.nextLine();

        System.out.println(myDbServer.deleteAssignment_Server(name, courseNum));
    }

    /**
     * Provides a list of all assignments in a course
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void listAllCourseAssignments_Client(Scanner sc) {
        System.out.println("What is your Course Number?: ");
        int courseNum = sc.nextInt();
        sc.nextLine();
        System.out.println(myDbServer.listAllCourseAssignments_Server(courseNum));
    }

    /**
     * List all courses a student is enrolled in
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void listCoursesForStudent_Client(Scanner sc) {
        System.out.println("What is your Student Number?: ");
        int studentNum = sc.nextInt();
        sc.nextLine();
        System.out.println(myDbServer.listCoursesForStudent_Server(studentNum));
    }

    public void createTeacher_Client(Scanner sc){
        System.out.println("Enter a new Teacher Number: ");
        int teacherNum = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter a First Name: ");
        String firstName = sc.nextLine();

        System.out.println("Enter a Last Name: ");
        String lastName = sc.nextLine();

        System.out.println("Enter a Phone Number: ");
        String phoneNum = sc.nextLine();

        System.out.println("Enter an Email: ");
        String email = sc.nextLine();

        System.out.println("Enter a Street: ");
        String street = sc.nextLine();

        System.out.println("Enter a Zipcode: ");
        String zipcode = sc.nextLine();

        System.out.println("Enter a State ID: ");
        String stateId = sc.nextLine();

        System.out.println(myDbServer.createTeacher_Server(teacherNum, firstName, lastName, phoneNum, email, street, zipcode, stateId));
    }

    public void createGradeCategory(Scanner sc){
        System.out.println("What is the course number?");
        int courseNum = sc.nextInt();
        System.out.println("What is the name of the grade category?");
        String categoryName = sc.nextLine();
        System.out.println("What is the grade weight?");
        double gradeWeight = sc.nextDouble();
        System.out.println(myDbServer.createGradeCategory(courseNum, categoryName, gradeWeight));

    }

    public void updateGradeCategory(Scanner sc){
        System.out.println("What is the course number?");
        int courseNum = sc.nextInt();
        System.out.println("What is the name of the grade category?");
        String categoryName = sc.nextLine();
        System.out.println("What is the new grade weight?");
        double newWeight = sc.nextDouble();
        System.out.println(myDbServer.updateGradeCategory(courseNum, categoryName, newWeight));

    }

    public void deleteGradeCategory(Scanner sc){
        System.out.println("What is the course number?");
        int courseNum = sc.nextInt();
        System.out.println("What is the name of the grade category?");
        String categoryName = sc.nextLine();
        System.out.println(myDbServer.deleteGradeCategory(courseNum, categoryName));

    }


    public void updateAssignmentGradeForAll(Scanner sc){
        System.out.println("What is the assignment name?");
        String assignmentName = sc.nextLine();
        System.out.println("What is the course number?");
        int courseNum = sc.nextInt();
        System.out.println("What is the grade?");
        double grade = sc.nextDouble();
        System.out.println(myDbServer.updateAssignmentGradeForAll(assignmentName, courseNum, grade));


    }
}
