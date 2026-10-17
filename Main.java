import java.util.Scanner;

/**
 * Main.java
 * 
 * Application Entry Point for Smart Student Performance Tracker.
 * 
 * RESPONSIBILITIES:
 * - Provides an intuitive, menu-driven console user interface.
 * - Handles user input safely with robust validation and exception handling.
 * - Coordinates with StudentService to perform all business operations.
 * - Demonstrates POLYMORPHISM, EXCEPTION HANDLING, and clean UI reporting.
 */
public class Main {

    // Scanner instance for user interaction
    private static final Scanner scanner = new Scanner(System.in);
    // StudentService instance holding business operations
    private static final StudentService studentService = new StudentService();

    public static void main(String[] args) {
        // Pre-populate sample students so the user can immediately test searching, topper, etc.
        preloadSampleData();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readIntChoice("Enter your choice (1-9): ", 1, 9);

            switch (choice) {
                case 1:
                    handleAddStudent();
                    break;
                case 2:
                    handleDisplayAllStudents();
                    break;
                case 3:
                    handleSearchStudent();
                    break;
                case 4:
                    handleDisplayStudentReportCard();
                    break;
                case 5:
                    handleUpdateStudent();
                    break;
                case 6:
                    handleDeleteStudent();
                    break;
                case 7:
                    handleFindClassTopper();
                    break;
                case 8:
                    handleDisplayTotalCount();
                    break;
                case 9:
                    System.out.println("\n=========================================================");
                    System.out.println("  Thank you for using Smart Student Performance Tracker! ");
                    System.out.println("  Exiting system. Have a great day ahead!                ");
                    System.out.println("=========================================================");
                    running = false;
                    break;
                default:
                    System.out.println("\n[!] Invalid choice! Please select an option between 1 and 9.");
            }

            if (running) {
                promptEnterKey();
            }
        }

        scanner.close();
    }

    /**
     * Prints the primary application menu.
     */
    private static void printMainMenu() {
        System.out.println();
        System.out.println("=========================================================");
        System.out.println("         SMART STUDENT PERFORMANCE TRACKER v1.0         ");
        System.out.println("             [Core Java & OOP Demonstration]             ");
        System.out.println("=========================================================");
        System.out.println("  1. Add New Student Record");
        System.out.println("  2. Display All Student Records (Tabular View)");
        System.out.println("  3. Search Student by Roll Number");
        System.out.println("  4. View Individual Student Detailed Report Card");
        System.out.println("  5. Update Student (Details / Marks)");
        System.out.println("  6. Delete Student Record (with Confirmation)");
        System.out.println("  7. Find Class Topper (Highest Total Marks)");
        System.out.println("  8. View Total Registered Student Count");
        System.out.println("  9. Exit Application");
        System.out.println("=========================================================");
    }

    // =========================================================================
    // FEATURE IMPLEMENTATIONS
    // =========================================================================

    /**
     * Feature 1: Add a new student record with input validation and duplicate prevention.
     */
    private static void handleAddStudent() {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("                  ADD NEW STUDENT RECORD                 ");
        System.out.println("---------------------------------------------------------");

        String rollNumber;
        while (true) {
            rollNumber = readNonEmptyString("Enter Roll Number (e.g. 21CS101): ");
            if (studentService.isRollNumberExists(rollNumber)) {
                System.out.printf("[ERROR] Roll number '%s' already exists! Duplicate roll numbers are not allowed.\n", rollNumber);
                System.out.println("Please enter a unique roll number.");
            } else {
                break;
            }
        }

        String name = readNonEmptyString("Enter Student Full Name: ");
        String department = readNonEmptyString("Enter Department / Branch (e.g. CSE, IT, ECE): ");
        int semester = readIntChoice("Enter Semester (1 - 8): ", 1, 8);
        String college = readStringWithDefault("Enter College Name [Press Enter for 'PSG College of Technology']: ", 
                                              "PSG College of Technology");

        System.out.println("\nEnter Marks for 5 Subjects (Each between 0 and 100):");
        int[] marks = new int[5];
        for (int i = 0; i < 5; i++) {
            String subjectName = (i < Student.SUBJECT_NAMES.length) ? Student.SUBJECT_NAMES[i] : "Subject " + (i + 1);
            marks[i] = readIntChoice(String.format("  [%d/5] %-20s: ", (i + 1), subjectName), 0, 100);
        }

        // OOP POLYMORPHISM: Storing subclass (CollegeStudent) reference inside Student base type
        Student newStudent = new CollegeStudent(rollNumber, name, department, semester, college, marks);

        boolean success = studentService.addStudent(newStudent);
        if (success) {
            System.out.println("\n[SUCCESS] Student record added successfully!");
            System.out.printf("  Total: %d/500 | Average: %.2f%% | Grade: %s | Result: %s\n",
                    newStudent.getTotalMarks(), newStudent.getAverage(), newStudent.getGrade(),
                    (newStudent.isPassed() ? "PASS" : "FAIL"));
        } else {
            System.out.println("\n[ERROR] Failed to add student record.");
        }
    }

    /**
     * Feature 2: Display all students.
     */
    private static void handleDisplayAllStudents() {
        studentService.displayAllStudents();
    }

    /**
     * Feature 3: Search for a student by roll number.
     */
    private static void handleSearchStudent() {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("                SEARCH STUDENT BY ROLL NUMBER            ");
        System.out.println("---------------------------------------------------------");

        String rollNumber = readNonEmptyString("Enter Roll Number to search: ");
        Student student = studentService.searchStudent(rollNumber);

        if (student == null) {
            System.out.printf("\n[NOT FOUND] No student found with Roll Number '%s'.\n", rollNumber);
        } else {
            System.out.println("\n[FOUND] Student record found:");
            // Demonstrates Runtime Polymorphism: invokes CollegeStudent's overridden displayDetails()
            student.displayDetails(false);
        }
    }

    /**
     * Feature 4: Display individual student's complete result / report card.
     */
    private static void handleDisplayStudentReportCard() {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("             STUDENT DETAILED REPORT CARD                ");
        System.out.println("---------------------------------------------------------");

        String rollNumber = readNonEmptyString("Enter Roll Number to generate Report Card: ");
        Student student = studentService.searchStudent(rollNumber);

        if (student == null) {
            System.out.printf("\n[NOT FOUND] No student found with Roll Number '%s'.\n", rollNumber);
        } else {
            System.out.println("\nGenerating Official Academic Grade Card...");
            // True parameter triggers detailed subject breakdown (Method Overloading)
            student.displayDetails(true);
        }
    }

    /**
     * Feature 5: Update student details and marks.
     */
    private static void handleUpdateStudent() {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("                 UPDATE STUDENT RECORD                   ");
        System.out.println("---------------------------------------------------------");

        String rollNumber = readNonEmptyString("Enter Roll Number of student to update: ");
        Student student = studentService.searchStudent(rollNumber);

        if (student == null) {
            System.out.printf("\n[NOT FOUND] No student found with Roll Number '%s'.\n", rollNumber);
            return;
        }

        System.out.println("\nCurrent Student Details:");
        student.displayDetails(false);

        System.out.println("\nWhat would you like to update?");
        System.out.println("  1. Personal & Academic Details (Name, Dept, Semester, College)");
        System.out.println("  2. Subject Marks (Recalculates Result & Grade automatically)");
        System.out.println("  3. Cancel Update");

        int updateChoice = readIntChoice("Enter choice (1-3): ", 1, 3);
        switch (updateChoice) {
            case 1:
                System.out.println("\n[Update Details] Leave blank / press Enter to keep current value.");
                System.out.print("New Name [" + student.getName() + "]: ");
                String newName = scanner.nextLine().trim();
                if (newName.isEmpty()) newName = student.getName();

                System.out.print("New Department [" + student.getDepartment() + "]: ");
                String newDept = scanner.nextLine().trim();
                if (newDept.isEmpty()) newDept = student.getDepartment();

                int newSemester = 0;
                String newCollege = "";
                if (student instanceof CollegeStudent) {
                    CollegeStudent cs = (CollegeStudent) student;
                    System.out.printf("New Semester (1-8) [%d]: ", cs.getSemester());
                    String semInput = scanner.nextLine().trim();
                    if (semInput.isEmpty()) {
                        newSemester = cs.getSemester();
                    } else {
                        try {
                            int parsed = Integer.parseInt(semInput);
                            newSemester = (parsed >= 1 && parsed <= 8) ? parsed : cs.getSemester();
                        } catch (NumberFormatException e) {
                            newSemester = cs.getSemester();
                        }
                    }

                    System.out.print("New College [" + cs.getCollegeName() + "]: ");
                    newCollege = scanner.nextLine().trim();
                    if (newCollege.isEmpty()) newCollege = cs.getCollegeName();
                }

                studentService.updateStudentDetails(rollNumber, newName, newDept, newSemester, newCollege);
                System.out.println("\n[SUCCESS] Student details updated successfully!");
                student.displayDetails(false);
                break;

            case 2:
                System.out.println("\nEnter New Marks for 5 Subjects (0 - 100):");
                int[] newMarks = new int[5];
                for (int i = 0; i < 5; i++) {
                    String subjectName = (i < Student.SUBJECT_NAMES.length) ? Student.SUBJECT_NAMES[i] : "Subject " + (i + 1);
                    newMarks[i] = readIntChoice(String.format("  [%d/5] %-20s: ", (i + 1), subjectName), 0, 100);
                }
                studentService.updateStudentMarks(rollNumber, newMarks);
                System.out.println("\n[SUCCESS] Marks updated and results recalculated successfully!");
                student.displayDetails(true);
                break;

            case 3:
                System.out.println("\n[CANCELLED] Update operation cancelled.");
                break;
        }
    }

    /**
     * Feature 6: Delete a student record with confirmation.
     */
    private static void handleDeleteStudent() {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("                 DELETE STUDENT RECORD                   ");
        System.out.println("---------------------------------------------------------");

        String rollNumber = readNonEmptyString("Enter Roll Number to delete: ");
        Student student = studentService.searchStudent(rollNumber);

        if (student == null) {
            System.out.printf("\n[NOT FOUND] No student found with Roll Number '%s'.\n", rollNumber);
            return;
        }

        System.out.printf("\nStudent Found: %s (%s, %s)\n", student.getName(), student.getRollNumber(), student.getDepartment());
        System.out.print("Are you sure you want to PERMANENTLY DELETE this record? (Y/N): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("YES")) {
            boolean deleted = studentService.deleteStudent(rollNumber);
            if (deleted) {
                System.out.printf("\n[SUCCESS] Student record '%s' deleted successfully.\n", rollNumber);
            } else {
                System.out.println("\n[ERROR] Failed to delete student record.");
            }
        } else {
            System.out.println("\n[CANCELLED] Deletion cancelled. Record was kept intact.");
        }
    }

    /**
     * Feature 7: Find the class topper based on total marks.
     */
    private static void handleFindClassTopper() {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("                 CLASS TOPPER (ACADEMIC STAR)            ");
        System.out.println("---------------------------------------------------------");

        Student topper = studentService.findTopper();
        if (topper == null) {
            System.out.println("\n[!] No students registered yet to determine topper.");
        } else {
            System.out.println("\n*** CONGRATULATIONS TO THE CLASS TOPPER! ***\n");
            topper.displayDetails(true);
        }
    }

    /**
     * Feature 8: Display total student count.
     */
    private static void handleDisplayTotalCount() {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("             TOTAL REGISTERED STUDENT COUNT              ");
        System.out.println("---------------------------------------------------------");
        int count = studentService.getTotalStudentCount();
        System.out.printf("Current total registered students in memory: %d\n", count);
    }

    // =========================================================================
    // HELPER & INPUT VALIDATION UTILITIES (Crash-Free Console)
    // =========================================================================

    /**
     * Preloads realistic sample records into memory for immediate testing and presentation.
     */
    private static void preloadSampleData() {
        // Student 1: Grade A Topper
        int[] marks1 = {95, 92, 98, 91, 94}; // Total 470, Avg 94.0%, Grade A
        studentService.addStudent(new CollegeStudent("21CS101", "Arun Kumar", "CSE", 6, "PSG College of Technology", marks1));

        // Student 2: Grade B Passed
        int[] marks2 = {82, 85, 78, 88, 80}; // Total 413, Avg 82.6%, Grade B
        studentService.addStudent(new CollegeStudent("21CS102", "Priya Dharshini", "CSE", 6, "PSG College of Technology", marks2));

        // Student 3: Failed due to 1 subject < 40 despite decent average (Testing rule 5)
        int[] marks3 = {85, 90, 35, 75, 80}; // 35 in Java -> FAIL, Grade F!
        studentService.addStudent(new CollegeStudent("21IT205", "Karthik Raja", "IT", 4, "PSG College of Technology", marks3));
    }

    /**
     * Safely reads an integer within [min, max] range without crashing on non-integer input.
     * Demonstrates EXCEPTION HANDLING.
     */
    private static int readIntChoice(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.printf("[!] Please enter a number between %d and %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input! Please enter a valid whole number.");
            }
        }
    }

    /**
     * Reads a non-empty string prompt from console.
     */
    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("[!] Input cannot be blank. Please try again.");
        }
    }

    /**
     * Reads a string with an optional default value if left blank.
     */
    private static String readStringWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        return input;
    }

    /**
     * Prompts the user to hit Enter before clearing or returning to main menu.
     */
    private static void promptEnterKey() {
        System.out.print("\nPress [Enter] to return to Main Menu...");
        scanner.nextLine();
    }
}
