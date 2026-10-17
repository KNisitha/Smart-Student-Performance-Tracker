/**
 * ResultCalculator.java
 * 
 * OOP CONCEPT DEMONSTRATED: ABSTRACTION (Interface)
 * --------------------------------------------------
 * Why use an interface here?
 * An interface defines a contract (a blueprint of methods) without providing the implementation.
 * Different institutions or grading bodies might have different evaluation policies
 * (e.g., standard grading, relative grading, CGPA system).
 * By defining ResultCalculator as an interface, our Student model and Service layers
 * depend on the abstraction rather than a specific grading formula.
 */
public interface ResultCalculator {

    /**
     * Calculates the sum of marks obtained across all subjects.
     * 
     * @param marks Array of marks scored in subjects.
     * @return Total marks as an integer.
     */
    int calculateTotal(int[] marks);

    /**
     * Calculates the average percentage from the total marks.
     * 
     * @param total The sum of marks.
     * @param subjectCount Number of subjects.
     * @return Average score as a double.
     */
    double calculateAverage(int total, int subjectCount);

    /**
     * Checks if the student meets the passing criterion in all subjects.
     * Passing requires at least 40 marks in every single subject.
     * 
     * @param marks Array of marks.
     * @return true if passed in all subjects, false otherwise.
     */
    boolean checkPassStatus(int[] marks);

    /**
     * Determines the letter grade (A, B, C, D, E, F) based on average and pass status.
     * If the student failed any subject, the grade is automatically 'F'.
     * 
     * @param average The calculated average score.
     * @param isPassed Whether the student passed all individual subjects.
     * @return Letter grade as a String.
     */
    String calculateGrade(double average, boolean isPassed);
}
