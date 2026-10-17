/**
 * GradeCalculator.java
 * 
 * OOP CONCEPT DEMONSTRATED: IMPLEMENTATION OF ABSTRACTION & METHOD OVERRIDING
 * -------------------------------------------------------------------------
 * Why is this used?
 * GradeCalculator provides the concrete implementation of the ResultCalculator interface.
 * It encapsulates the specific institutional grading rules and pass/fail logic.
 * Every method annotated with @Override provides the specific behavior promised by
 * the ResultCalculator interface.
 */
public class GradeCalculator implements ResultCalculator {

    // Passing threshold required in EACH subject
    public static final int PASS_MARK_THRESHOLD = 40;

    /**
     * Calculates the total marks from the given array of marks.
     * 
     * @param marks Array containing subject scores.
     * @return Sum of all marks.
     */
    @Override
    public int calculateTotal(int[] marks) {
        if (marks == null || marks.length == 0) {
            return 0;
        }
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return total;
    }

    /**
     * Calculates the average marks.
     * 
     * @param total Sum of marks.
     * @param subjectCount Number of subjects.
     * @return Average percentage rounded to two decimal places.
     */
    @Override
    public double calculateAverage(int total, int subjectCount) {
        if (subjectCount <= 0) {
            return 0.0;
        }
        return (double) total / subjectCount;
    }

    /**
     * Checks if the student passed all individual subjects.
     * Requirement: At least 40 marks in every subject.
     * If even one subject has < 40, returns false.
     * 
     * @param marks Array of marks.
     * @return true if all marks >= 40, false otherwise.
     */
    @Override
    public boolean checkPassStatus(int[] marks) {
        if (marks == null || marks.length == 0) {
            return false;
        }
        for (int mark : marks) {
            if (mark < PASS_MARK_THRESHOLD) {
                return false; // Failed in this subject
            }
        }
        return true; // Passed in all subjects
    }

    /**
     * Calculates the letter grade based on average and pass status:
     * - If student failed any subject (isPassed == false): Grade is "F"
     * - A: 90 and above
     * - B: 80 - 89.99
     * - C: 70 - 79.99
     * - D: 60 - 69.99
     * - E: 50 - 59.99
     * - F: Below 50
     * 
     * @param average Calculated average.
     * @param isPassed Whether student passed all subjects.
     * @return Grade letter.
     */
    @Override
    public String calculateGrade(double average, boolean isPassed) {
        // If the student failed any subject, grade is strictly F regardless of average
        if (!isPassed) {
            return "F";
        }

        if (average >= 90.0) {
            return "A";
        } else if (average >= 80.0) {
            return "B";
        } else if (average >= 70.0) {
            return "C";
        } else if (average >= 60.0) {
            return "D";
        } else if (average >= 50.0) {
            return "E";
        } else {
            return "F";
        }
    }
}
