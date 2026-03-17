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

    /**
     * Adds an active teacher to the database based on client inputs
     * @param sc Scanner object to avoid repeated scanner creations
     */
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

    /**
     * Updates a teacher in the database based on user inputs
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void updateTeacher_Client(Scanner sc){
        System.out.println("What is your Teacher Number: ");
        int teacherNum = sc.nextInt();
        sc.nextLine();

        System.out.println("Select a column to update by typing their number: ");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Phone Number");
        System.out.println("4. Email");
        System.out.println("5. Street");
        System.out.println("6. Zipcode");
        System.out.println("7. State ID");
        System.out.println("8. isActive");
        int column = sc.nextInt();
        sc.nextLine();

        if (column == 1){
            System.out.println("What is the new First Name: ");
            String firstName = sc.nextLine();

            System.out.println(myDbServer.updateTeacher_Server(teacherNum, String.valueOf(column), firstName));
        } else if (column == 2){
            System.out.println("What is the new Last Name: ");
            String lastName = sc.nextLine();

            System.out.println(myDbServer.updateTeacher_Server(teacherNum, String.valueOf(column), lastName));
        } else if (column == 3){
            System.out.println("What is the new Phone Number: ");
            String phoneNum = sc.nextLine();

            System.out.println(myDbServer.updateTeacher_Server(teacherNum, String.valueOf(column), phoneNum));
        } else if (column == 4){
            System.out.println("What is the new Email: ");
            String email = sc.nextLine();

            System.out.println(myDbServer.updateTeacher_Server(teacherNum, String.valueOf(column), email));
        } else if (column == 5){
            System.out.println("What is the new Street: ");
            String street = sc.nextLine();

            System.out.println(myDbServer.updateTeacher_Server(teacherNum, String.valueOf(column), street));
        } else if (column == 6){
            System.out.println("What is the new Zipcode: ");
            String zipcode = sc.nextLine();

            System.out.println(myDbServer.updateTeacher_Server(teacherNum, String.valueOf(column), zipcode));
        } else if (column == 7){
            System.out.println("What is the new State ID: ");
            String stateId = sc.nextLine();

            System.out.println(myDbServer.updateTeacher_Server(teacherNum, String.valueOf(column), stateId));
        } else if (column == 8){
            System.out.println("Is the teacher active? (true/false): ");
            boolean isActive = sc.nextBoolean();
            sc.nextLine();

            System.out.println(myDbServer.updateTeacher_Server(teacherNum, String.valueOf(column), String.valueOf(isActive)));
        }
    }

    /**
     * Deletes a teacher from the server based on the users inputs
     * @param sc Scanner object to avoid repeated scanner creation
     */
    public void deleteTeacher_Client(Scanner sc){
        System.out.println("What is your Teacher Number: ");
        int teacherNum = sc.nextInt();
        sc.nextLine();

        System.out.println(myDbServer.deleteTeacher_Server(teacherNum));
    }

    /**
     * Creates a course in the database based on user inputs
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void createCourse_Client(Scanner sc) {
        System.out.println("Enter a Course Number: ");
        int courseNum = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter a Course Offering Name: ");
        String courseOfferingName = sc.nextLine();

        System.out.println("Enter a Teacher Number: ");
        int teacherNum = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter a Capacity: ");
        int capacity = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter a Time Slot ID: ");
        String timeSlotID = sc.nextLine();

        System.out.println(myDbServer.createCourse_Server(courseNum, courseOfferingName, teacherNum, capacity, timeSlotID));
    }

    public void deleteCourse_Client(Scanner sc) {
        System.out.println("Enter a Course Number: ");
        int courseNum = sc.nextInt();
        sc.nextLine();
        System.out.println(myDbServer.deleteCourse_Server(courseNum));
    }

    
    public void createGradeCategory(Scanner sc){
        System.out.println("What is the course number?");
        int courseNum = sc.nextInt();
        sc.nextLine();

        System.out.println("What is the name of the grade category?");
        String categoryName = sc.nextLine();

        System.out.println("What is the grade weight?");
        int gradeWeight = sc.nextInt();
        sc.nextLine();

        myDbServer.createGradeCategory(courseNum, categoryName, gradeWeight);
    }

    public void updateGradeCategory(Scanner sc){
        System.out.println("What is the course number?");
        int courseNum = sc.nextInt();
        sc.nextLine();

        System.out.println("What is the name of the grade category?");
        String categoryName = sc.nextLine();

        System.out.println("What is the new grade weight?");
        double newWeight = sc.nextDouble();
        sc.nextLine();

        myDbServer.updateGradeCategory(courseNum, categoryName, newWeight);
    }

    public void deleteGradeCategory(Scanner sc){
        System.out.println("What is the course number?");
        int courseNum = sc.nextInt();
        sc.nextLine();
        System.out.println("What is the name of the grade category?");
        String categoryName = sc.nextLine();
        System.out.println(myDbServer.deleteGradeCategory(courseNum, categoryName));

    }


    public void updateAssignmentGradeForAll(Scanner sc){
        System.out.println("What is the assignment name?");
        String assignmentName = sc.nextLine();

        System.out.println("What is the course number?");
        int courseNum = sc.nextInt();
        sc.nextLine();

        System.out.println("What is the grade?");
        double grade = sc.nextDouble();
        sc.nextLine();

        myDbServer.updateAssignmentGradeForAll(assignmentName, courseNum, grade);
    }

    // studentNum helper
    public int getValidInt(Scanner sc, String prompt) {
        while (true) {
             System.out.println(prompt);
             String input = sc.nextLine().trim();
             try {
                return Integer.parseInt(input);
                }
             catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                }
        }
    }
         // Creates a student
    public void createStudent_Client(Scanner sc) {
        System.out.println("Creating New Student");
        int studentNum = getValidInt(sc, "What is your Student Number?: ");
        System.out.println("What is your First Name?: ");
        String firstName = sc.nextLine();
        System.out.println("What is your Last Name?: ");
        String lastName = sc.nextLine();
        System.out.println("What is your Email?: ");
        String email = sc.nextLine();
        System.out.println("What is your Phone Number?: ");
        String phoneNum = sc.nextLine();
        System.out.println("What is your Street?: ");
        String street = sc.nextLine();
        System.out.println("What is your Zipcode?: ");
        String zipcode = sc.nextLine();
        System.out.println("What is your State ID?: ");
        String stateId = sc.nextLine();
        System.out.println("What is your Class Standing ID?: ");
        String classStandingId = sc.nextLine();
        
        String result = myDbServer.createStudent(studentNum, firstName, lastName, email, phoneNum, street, zipcode, stateId, classStandingId);
        System.out.println("\n" + result);
    }
    // Helper to simplify blank inputs for updateStudent_Client
    public String updateStudentHelper (Scanner sc) {
        String input = sc.nextLine().trim();
        return input.isEmpty() ? null : input;
    }
        
    public void updateStudent_Client(Scanner sc) {
        System.out.println("Updating Student (Leave blank to skip)");
        int studentNum = getValidInt(sc, "Enter the Student Number to modify: ");
        System.out.println("Modifying First Name: ");
        String firstName = updateStudentHelper(sc);
        System.out.println("Modifying Last Name: ");
        String lastName = updateStudentHelper(sc);
        System.out.println("Modifying Email: ");
        String email = updateStudentHelper(sc);
        System.out.println("Modifying Phone Number: ");
        String phoneNum = updateStudentHelper(sc);
        System.out.println("Modifying Street: ");
        String street = updateStudentHelper(sc);
        System.out.println("Modifying Zipcode: ");
        String zipcode = updateStudentHelper(sc);
        System.out.println("Modifying State ID: ");
        String stateId = updateStudentHelper(sc);
        System.out.println("Modifying Class Standing ID: ");
        String classStandingId = updateStudentHelper(sc);
        System.out.println("Activate Student? (y/n/blank): ");
        String choice = sc.nextLine().toLowerCase();
        Boolean isActive = null;
        if (choice.equals("y")) isActive = true;
        if (choice.equals("n")) isActive = false;
        
        String result = myDbServer.updateStudent(studentNum, firstName, lastName, email, phoneNum, street, zipcode, stateId, classStandingId, isActive);
        System.out.println("\n" + result);
    }
    
    // Enrolls a student in a course
    public void enrollStudent_Client(Scanner sc) {
        System.out.println("Enrolling Student");
        int studentNum = getValidInt(sc, "Enter the Student Number?: ");
        int courseNum = getValidInt(sc, "Enter the Course Number?: ");
        
        String result = myDbServer.enrollStudent(studentNum, courseNum);
        System.out.println("\n" + result);
    }

    // Drops (deactivates) a student
    public void dropStudent_Client(Scanner sc) {
        System.out.println("Dropping Student");
        int studentNum = getValidInt(sc, "Enter the Student Number to drop: ");
        
        String result = myDbServer.dropStudent(studentNum);
        System.out.println("\n" + result);
    }

    /**
     * Lists all students enrolled in a specific course
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void listStudentsInCourse_Client(Scanner sc) {
        System.out.println("What is your Course Number?: ");
        int courseNum = sc.nextInt();
        sc.nextLine();
        myDbServer.listStudentsInCourse(courseNum);
    }

    /**
     * Gets the GPA for a specific student
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void getGPA_Client(Scanner sc) {
        System.out.println("What is your Student Number?: ");
        int studentNum = sc.nextInt();
        sc.nextLine();
        myDbServer.getGPA(studentNum);
    }

    /**
     * Searches for students by various criteria
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void searchStudent_Client(Scanner sc) {
        System.out.println("Enter First Name (or press Enter to skip): ");
        String firstName = sc.nextLine();
        if (firstName.isEmpty()) firstName = null;
        
        System.out.println("Enter Last Name (or press Enter to skip): ");
        String lastName = sc.nextLine();
        if (lastName.isEmpty()) lastName = null;
        
        System.out.println("Enter Student Number (or press Enter to skip): ");
        String studentNumStr = sc.nextLine();
        Integer studentNum = studentNumStr.isEmpty() ? null : Integer.parseInt(studentNumStr);
        
        System.out.println("Enter Class Standing ID (or press Enter to skip): ");
        String classStanding = sc.nextLine();
        if (classStanding.isEmpty()) classStanding = null;
        
        myDbServer.searchStudent(firstName, lastName, studentNum, classStanding);
    }

    /**
     * Lists all courses a teacher is teaching
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void allTeacherCourses_Client(Scanner sc) {
        System.out.println("What is your Teacher Number?: ");
        int teacherNum = sc.nextInt();
        sc.nextLine();
        myDbServer.allTeacherCourses(teacherNum);
    }

    /**
     * Lists the grade distribution for a course
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void listGradeDistribution_Client(Scanner sc) {
        System.out.println("What is your Course Number?: ");
        int courseNum = sc.nextInt();
        sc.nextLine();
        myDbServer.listGradeDistribution(courseNum);
    }

    /**
     * Lists all offered courses
     */
    public void offeredCourses_Client(Scanner sc) {
        myDbServer.offeredCourses();
    }

    /**
     * Calculates a student's grade in a specific course
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void calculateCourseGrade_Client(Scanner sc) {
        System.out.println("What is your Student Number?: ");
        int studentNum = sc.nextInt();
        sc.nextLine();
        
        System.out.println("What is your Course Number?: ");
        int courseNum = sc.nextInt();
        sc.nextLine();
        
        myDbServer.calculateCourseGrade(studentNum, courseNum);
    }

    /**
     * Updates a single student's grade on an assignment
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void updateAssignmentGrade_Client(Scanner sc) {
        System.out.println("What is the assignment name?: ");
        String assignmentName = sc.nextLine();
        
        System.out.println("What is the student number?: ");
        int studentNum = sc.nextInt();
        sc.nextLine();
        
        System.out.println("What is the course number?: ");
        int courseNum = sc.nextInt();
        sc.nextLine();
        
        System.out.println("What is the grade?: ");
        double grade = sc.nextDouble();
        sc.nextLine();
        
        myDbServer.updateAssignmentGrade(assignmentName, studentNum, courseNum, grade);
    }

    /**
     * Creates a new course offering
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void createCourseOffering_Client(Scanner sc) {
        System.out.println("Enter Course Offering Name: ");
        String name = sc.nextLine();
        
        System.out.println("Is this course being offered? (true/false): ");
        boolean offered = sc.nextBoolean();
        sc.nextLine();
        
        myDbServer.createCourseOffering(name, offered);
    }

    /**
     * Updates a course offering
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void updateCourseOffering_Client(Scanner sc) {
        System.out.println("Enter Course Offering Name: ");
        String name = sc.nextLine();
        
        System.out.println("Is this course being offered? (true/false): ");
        boolean offered = sc.nextBoolean();
        sc.nextLine();
        
        myDbServer.updateCourseOffering(name, offered);
    }

    /**
     * Deletes a course offering
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void deleteCourseOffering_Client(Scanner sc) {
        System.out.println("Enter Course Offering Name: ");
        String name = sc.nextLine();
        
        myDbServer.deleteCourseOffering(name);
    }

    /**
     * Lists all assignments for a student in a single course
     * @param sc Scanner object to avoid repeated scanner creations
     */
    public void listAssignmentsForStudent_Client(Scanner sc) {
        System.out.println("What is your Student Number?: ");
        int studentNum = sc.nextInt();
        sc.nextLine();
        
        System.out.println("What is your Course Number?: ");
        int courseNum = sc.nextInt();
        sc.nextLine();
        
        myDbServer.listAssignmentsForStudentInSingleCourse(studentNum, courseNum);
    }

}
