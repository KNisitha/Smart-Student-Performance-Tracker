import java.util.Arrays;

/**
 * Student.java
 * 
 * OOP CONCEPTS DEMONSTRATED:
 * 1. CLASSES AND OBJECTS: Defines the blueprint/template for a Student entity.
 * 2. ENCAPSULATION: All internal attributes are 'private' to protect data integrity.
 *    Access is restricted through controlled public getter and setter methods.
 * 3. CONSTRUCTOR OVERLOADING: Multiple constructors allow flexible object creation.
 * 4. METHOD OVERLOADING: Multiple versions of displayDetails() with different parameters.
 */
public class Student {

    // Encapsulated (private) member variables
    private String rollNumber;
    private String name;
    private String department;
    private int[] marks; // Array holding marks for 5 subjects
    private int totalMarks;
    private double average;
    private String grade;
    private boolean isPassed;

    // Subject names for clear reporting
    public static final String[] SUBJECT_NAMES = {
        "Mathematics", 
        "Data Structures", 
        "Java Programming", 
        "Operating Systems", 
        "Computer Networks"
    };

    /**
     * Default / No-Argument Constructor
     * Demonstrates Constructor Overloading (Option 1).
     */
    public Student() {
        this.rollNumber = "";
        this.name = "";
        this.department = "";
        this.marks = new int[5];
        this.totalMarks = 0;
        this.average = 0.0;
        this.grade = "N/A";
        this.isPassed = false;
    }

    /**
     * Parameterized Constructor
     * Demonstrates Constructor Overloading (Option 2).
     * Initializes a Student with basic identification and marks.
     * 
     * @param rollNumber Unique roll number
     * @param name Student's full name
     * @param department Branch/Department (e.g. CSE, IT, ECE)
     * @param marks Array of 5 subject marks
     */
    public Student(String rollNumber, String name, String department, int[] marks) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.department = department;
        setMarks(marks); // Uses setter for validation
    }

    /**
     * Calculates the student's academic result using an implementation of ResultCalculator.
     * Demonstrates POLYMORPHISM & ABSTRACTION: Student does not hardcode calculation formulas;
     * it delegates the computation to the passed ResultCalculator instance.
     * 
     * @param calculator An instance implementing ResultCalculator.
     */
    public void calculateResults(ResultCalculator calculator) {
        if (calculator != null && this.marks != null) {
            this.totalMarks = calculator.calculateTotal(this.marks);
            this.average = calculator.calculateAverage(this.totalMarks, this.marks.length);
            this.isPassed = calculator.checkPassStatus(this.marks);
            this.grade = calculator.calculateGrade(this.average, this.isPassed);
        }
    }

    // =========================================================================
    // METHOD OVERLOADING (Polymorphism at Compile Time)
    // =========================================================================

    /**
     * Method Overload 1: Displays standard summary of the student.
     */
    public void displayDetails() {
        displayDetails(false);
    }

    /**
     * Method Overload 2: Displays student details with optional detailed subject marks breakdown.
     * 
     * @param showSubjectBreakdown If true, prints marks for each individual subject.
     */
    public void displayDetails(boolean showSubjectBreakdown) {
        System.out.println("+---------------------------------------------------------------+");
        System.out.printf("| %-20s : %-38s |\n", "Roll Number", rollNumber);
        System.out.printf("| %-20s : %-38s |\n", "Student Name", name);
        System.out.printf("| %-20s : %-38s |\n", "Department", department);
        System.out.println("+---------------------------------------------------------------+");

        if (showSubjectBreakdown && marks != null) {
            System.out.println("| SUBJECT-WISE SCORECARD:                                       |");
            System.out.println("| ------------------------------------------------------------- |");
            for (int i = 0; i < marks.length; i++) {
                String subName = (i < SUBJECT_NAMES.length) ? SUBJECT_NAMES[i] : "Subject " + (i + 1);
                String subStatus = (marks[i] >= GradeCalculator.PASS_MARK_THRESHOLD) ? "PASS" : "FAIL (Arrear)";
                System.out.printf("| %-25s : %3d / 100   [%-15s] |\n", subName, marks[i], subStatus);
            }
            System.out.println("+---------------------------------------------------------------+");
        }

        System.out.printf("| %-20s : %-38s |\n", "Total Marks", totalMarks + " / 500");
        System.out.printf("| %-20s : %-38.2f |\n", "Average Percentage", average);
        System.out.printf("| %-20s : %-38s |\n", "Final Grade", grade);
        System.out.printf("| %-20s : %-38s |\n", "Result Status", (isPassed ? "PASSED" : "FAILED"));
        System.out.println("+---------------------------------------------------------------+");
    }

    // =========================================================================
    // ENCAPSULATION: GETTERS AND SETTERS
    // =========================================================================

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        if (rollNumber != null && !rollNumber.trim().isEmpty()) {
            this.rollNumber = rollNumber.trim();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department != null && !department.trim().isEmpty()) {
            this.department = department.trim();
        }
    }

    public int[] getMarks() {
        return (marks != null) ? Arrays.copyOf(marks, marks.length) : new int[0];
    }

    /**
     * Validates and sets marks.
     * Ensures all marks are between 0 and 100.
     * 
     * @param marks Array of marks.
     */
    public void setMarks(int[] marks) {
        if (marks != null && marks.length == 5) {
            this.marks = new int[5];
            for (int i = 0; i < 5; i++) {
                if (marks[i] < 0 || marks[i] > 100) {
                    throw new IllegalArgumentException("Mark for subject " + (i + 1) + " must be between 0 and 100.");
                }
                this.marks[i] = marks[i];
            }
        }
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public double getAverage() {
        return average;
    }

    public String getGrade() {
        return grade;
    }

    public boolean isPassed() {
        return isPassed;
    }
}
