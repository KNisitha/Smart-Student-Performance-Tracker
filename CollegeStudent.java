/**
 * CollegeStudent.java
 * 
 * OOP CONCEPTS DEMONSTRATED:
 * 1. INHERITANCE: CollegeStudent 'is-a' specialized Student.
 *    It inherits core properties (rollNumber, name, department, marks, calculation logic)
 *    from Student base class while adding higher-education specifics (collegeName, semester).
 * 2. REUSABILITY & 'super' KEYWORD: Uses super(...) to invoke the parent class constructor
 *    and avoid redundant code.
 * 3. METHOD OVERRIDING (@Override): Customizes displayDetails() to produce a 
 *    college-specific report card including institution name and semester.
 */
public class CollegeStudent extends Student {

    // Additional attributes specific to college-level students
    private String collegeName;
    private int semester;

    /**
     * Default Constructor
     */
    public CollegeStudent() {
        super();
        this.collegeName = "Autonomous Engineering College";
        this.semester = 1;
    }

    /**
     * Parameterized Constructor
     * Demonstrates 'super' keyword to initialize inherited base class fields.
     * 
     * @param rollNumber Unique roll number
     * @param name Full name
     * @param department Academic department
     * @param semester Current semester (e.g. 1 to 8)
     * @param collegeName Name of the institution
     * @param marks Array of 5 marks
     */
    public CollegeStudent(String rollNumber, String name, String department, int semester, String collegeName, int[] marks) {
        super(rollNumber, name, department, marks); // Reusing parent class constructor
        this.semester = semester;
        this.collegeName = (collegeName != null && !collegeName.trim().isEmpty()) 
                           ? collegeName.trim() 
                           : "Government College of Technology";
    }

    // =========================================================================
    // METHOD OVERRIDING (Runtime Polymorphism)
    // =========================================================================

    /**
     * Overrides displayDetails() from Student class to include college and semester details.
     */
    @Override
    public void displayDetails() {
        displayDetails(false);
    }

    /**
     * Overrides overloaded displayDetails(boolean) from Student class.
     * Renders a comprehensive, college-branded grade card.
     */
    @Override
    public void displayDetails(boolean showSubjectBreakdown) {
        System.out.println("=================================================================");
        System.out.printf("  INSTITUTION : %-48s\n", collegeName.toUpperCase());
        System.out.printf("  SEMESTER    : %d | DEPARTMENT: %-34s\n", semester, getDepartment().toUpperCase());
        System.out.println("=================================================================");
        System.out.printf("| %-20s : %-38s |\n", "Roll Number", getRollNumber());
        System.out.printf("| %-20s : %-38s |\n", "Student Name", getName());
        System.out.printf("| %-20s : %-38s |\n", "Semester", "Semester " + semester);
        System.out.printf("| %-20s : %-38s |\n", "College Name", collegeName);
        System.out.println("+---------------------------------------------------------------+");

        int[] marks = getMarks();
        if (showSubjectBreakdown && marks != null && marks.length > 0) {
            System.out.println("| SUBJECT-WISE SCORECARD:                                       |");
            System.out.println("| ------------------------------------------------------------- |");
            for (int i = 0; i < marks.length; i++) {
                String subName = (i < SUBJECT_NAMES.length) ? SUBJECT_NAMES[i] : "Subject " + (i + 1);
                String subStatus = (marks[i] >= GradeCalculator.PASS_MARK_THRESHOLD) ? "PASS" : "FAIL (Arrear)";
                System.out.printf("| %-25s : %3d / 100   [%-15s] |\n", subName, marks[i], subStatus);
            }
            System.out.println("+---------------------------------------------------------------+");
        }

        System.out.printf("| %-20s : %-38s |\n", "Total Marks", getTotalMarks() + " / 500");
        System.out.printf("| %-20s : %-38.2f |\n", "Average Percentage", getAverage());
        System.out.printf("| %-20s : %-38s |\n", "Final Grade", getGrade());
        System.out.printf("| %-20s : %-38s |\n", "Result Status", (isPassed() ? "PASSED (PROMOTED)" : "FAILED (ARREARS PENDING)"));
        System.out.println("=================================================================");
    }

    // =========================================================================
    // GETTERS AND SETTERS FOR COLLEGE-SPECIFIC FIELDS
    // =========================================================================

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        if (collegeName != null && !collegeName.trim().isEmpty()) {
            this.collegeName = collegeName.trim();
        }
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        if (semester > 0 && semester <= 8) {
            this.semester = semester;
        } else {
            throw new IllegalArgumentException("Semester must be between 1 and 8.");
        }
    }
}
