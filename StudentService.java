import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * StudentService.java
 * 
 * OOP CONCEPTS DEMONSTRATED:
 * 1. COLLECTIONS FRAMEWORK (ArrayList): Used to store and manage student objects dynamically in memory.
 * 2. POLYMORPHISM & ABSTRACTION: Holds a reference to the ResultCalculator interface.
 *    Any valid grading engine can be plugged in without changing service logic.
 * 3. METHOD OVERLOADING: Multiple searchStudent() methods with different signatures.
 * 4. ENCAPSULATION: Internal list of students is private; operations are exposed through public methods.
 */
public class StudentService {

    // In-memory list to store all student records
    private ArrayList<Student> studentList;

    // Polymorphic reference to ResultCalculator
    private ResultCalculator resultCalculator;

    /**
     * Constructor initializes the ArrayList and the ResultCalculator engine.
     */
    public StudentService() {
        this.studentList = new ArrayList<>();
        this.resultCalculator = new GradeCalculator(); // Polymorphism: Interface ref = Concrete instance
    }

    /**
     * Checks if a roll number already exists in the system (Case-Insensitive).
     * 
     * @param rollNumber Roll number to verify.
     * @return true if duplicate exists, false otherwise.
     */
    public boolean isRollNumberExists(String rollNumber) {
        if (rollNumber == null || rollNumber.trim().isEmpty()) {
            return false;
        }
        for (Student student : studentList) {
            if (student.getRollNumber().equalsIgnoreCase(rollNumber.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a new student record to the system after checking for duplicate roll number.
     * Automatically triggers result computation (total, average, grade, pass status).
     * 
     * @param student The student object to add.
     * @return true if successfully added, false if duplicate or invalid.
     */
    public boolean addStudent(Student student) {
        if (student == null) {
            return false;
        }

        // Prevent duplicate roll numbers
        if (isRollNumberExists(student.getRollNumber())) {
            return false;
        }

        // Automatically calculate total, average, grade, and pass/fail status
        student.calculateResults(resultCalculator);

        // Store in ArrayList
        studentList.add(student);
        return true;
    }

    // =========================================================================
    // METHOD OVERLOADING (Compile-time Polymorphism)
    // =========================================================================

    /**
     * Overload 1: Searches for a student by roll number only.
     * 
     * @param rollNumber The roll number to look for.
     * @return Student object if found, null otherwise.
     */
    public Student searchStudent(String rollNumber) {
        if (rollNumber == null || rollNumber.trim().isEmpty()) {
            return null;
        }
        for (Student student : studentList) {
            if (student.getRollNumber().equalsIgnoreCase(rollNumber.trim())) {
                return student;
            }
        }
        return null;
    }

    /**
     * Overload 2: Searches for a student by roll number AND department.
     * Demonstrates Method Overloading by changing parameter list.
     * 
     * @param rollNumber The roll number.
     * @param department The department name.
     * @return Student object if both match, null otherwise.
     */
    public Student searchStudent(String rollNumber, String department) {
        if (rollNumber == null || department == null) {
            return null;
        }
        for (Student student : studentList) {
            if (student.getRollNumber().equalsIgnoreCase(rollNumber.trim()) 
                    && student.getDepartment().equalsIgnoreCase(department.trim())) {
                return student;
            }
        }
        return null;
    }

    /**
     * Updates an existing student's personal and academic details.
     * 
     * @param rollNumber Roll number of the student to update.
     * @param newName New student name.
     * @param newDept New department.
     * @param newSemester New semester (for CollegeStudent).
     * @param newCollege New college name (for CollegeStudent).
     * @return true if updated, false if student not found.
     */
    public boolean updateStudentDetails(String rollNumber, String newName, String newDept, int newSemester, String newCollege) {
        Student student = searchStudent(rollNumber);
        if (student == null) {
            return false;
        }

        if (newName != null && !newName.trim().isEmpty()) {
            student.setName(newName);
        }
        if (newDept != null && !newDept.trim().isEmpty()) {
            student.setDepartment(newDept);
        }

        // If the instance is a CollegeStudent, update college-specific fields
        if (student instanceof CollegeStudent) {
            CollegeStudent cs = (CollegeStudent) student;
            if (newSemester > 0) {
                cs.setSemester(newSemester);
            }
            if (newCollege != null && !newCollege.trim().isEmpty()) {
                cs.setCollegeName(newCollege);
            }
        }
        return true;
    }

    /**
     * Updates a student's marks and recalculates total, average, grade, and status.
     * 
     * @param rollNumber Roll number of the student.
     * @param newMarks Array of 5 updated marks.
     * @return true if updated, false if student not found or invalid marks.
     */
    public boolean updateStudentMarks(String rollNumber, int[] newMarks) {
        Student student = searchStudent(rollNumber);
        if (student == null) {
            return false;
        }

        try {
            student.setMarks(newMarks);
            student.calculateResults(resultCalculator);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Deletes a student record by roll number.
     * 
     * @param rollNumber Roll number of the student to delete.
     * @return true if deleted, false if not found.
     */
    public boolean deleteStudent(String rollNumber) {
        Student student = searchStudent(rollNumber);
        if (student != null) {
            return studentList.remove(student);
        }
        return false;
    }

    /**
     * Displays all students in a neatly formatted ASCII tabular view.
     */
    public void displayAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("\n[!] No student records found. Add students first using Option 1.");
            return;
        }

        System.out.println("\n==========================================================================================");
        System.out.println("                                ALL REGISTERED STUDENTS                                   ");
        System.out.println("==========================================================================================");
        System.out.printf("| %-12s | %-20s | %-10s | %-7s | %-8s | %-6s | %-8s |\n",
                "ROLL NO", "NAME", "DEPT", "TOTAL", "AVERAGE", "GRADE", "STATUS");
        System.out.println("+--------------+----------------------+------------+---------+----------+--------+----------+");

        for (Student student : studentList) {
            System.out.printf("| %-12s | %-20s | %-10s | %3d/500 | %6.2f %% |   %-4s | %-8s |\n",
                    student.getRollNumber(),
                    truncate(student.getName(), 20),
                    truncate(student.getDepartment(), 10),
                    student.getTotalMarks(),
                    student.getAverage(),
                    student.getGrade(),
                    (student.isPassed() ? "PASS" : "FAIL"));
        }
        System.out.println("==========================================================================================");
        System.out.printf("Total Enrolled Students: %d\n", studentList.size());
    }

    /**
     * Finds and returns the class topper based on total marks.
     * 
     * @return Student object of topper, or null if list is empty.
     */
    public Student findTopper() {
        if (studentList.isEmpty()) {
            return null;
        }

        Student topper = studentList.get(0);
        for (int i = 1; i < studentList.size(); i++) {
            if (studentList.get(i).getTotalMarks() > topper.getTotalMarks()) {
                topper = studentList.get(i);
            }
        }
        return topper;
    }

    /**
     * Returns the total count of students currently stored.
     * 
     * @return Total count.
     */
    public int getTotalStudentCount() {
        return studentList.size();
    }

    /**
     * Helper method to truncate long strings for clean table formatting.
     */
    private String truncate(String value, int maxLength) {
        if (value == null) return "";
        if (value.length() <= maxLength) return value;
        return value.substring(0, maxLength - 2) + "..";
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }
}
