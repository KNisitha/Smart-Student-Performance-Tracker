# 🎓 Smart Student Performance Tracker

A beginner-friendly, clean, and robust console-based Student Performance Management system built using **Core Java** and **Object-Oriented Programming (OOP)** principles.

---

## 📌 1. Project Overview

The **Smart Student Performance Tracker** is designed to manage, calculate, evaluate, and track student academic performance in higher education. It provides an automated evaluation engine that computes totals, percentage averages, pass/fail status, and institutional letter grades for students across multiple subjects.

It demonstrates essential real-world software engineering practices for Java learners:
- Clean class division with single responsibilities.
- Strict encapsulation preventing data corruption.
- Polymorphic grading calculations using interfaces.
- Graceful error and exception handling so the console app never crashes on invalid inputs.

---

## 🚀 2. Features

1. **Add Student Record**:
   - Collects Roll Number, Name, Department, Semester, College Name, and 5 subject scores.
   - **Duplicate Roll Number Prevention**: Verifies roll numbers to ensure every student record is unique.
   - **Score Validation**: Enforces mark bounds between `0` and `100` for each subject.
2. **Automated Result Calculation**:
   - Computes Total Marks (out of 500) and Average Percentage.
   - Evaluates Pass/Fail status and assigns letter grades automatically.
3. **Rigorous Grading & Pass/Fail Policy**:
   - **A**: 90% and above
   - **B**: 80% – 89.99%
   - **C**: 70% – 79.99%
   - **D**: 60% – 69.99%
   - **E**: 50% – 59.99%
   - **F**: Below 50%
   - **Individual Subject Passing Rule**: Requires **at least 40 marks in every single subject**. If any subject is below 40, the student fails (Grade `F`, Status `FAILED (ARREARS PENDING)`) regardless of how high their average percentage is!
4. **Tabular Display of All Students**: Neatly formatted ASCII table showing roll number, name, department, total, average, grade, and pass/fail summary.
5. **Search Student**: Fast lookup by roll number.
6. **Detailed Scorecard / Report Card**: Displays official grade report card including institution banner, semester, and subject-wise breakdown with pass/arrear badges.
7. **Update Student Details & Marks**: Update student personal/academic attributes or enter new subject scores with automatic grade recalculation.
8. **Delete Record with Confirmation**: Prevents accidental deletion by requiring explicit `(Y/N)` confirmation.
9. **Class Topper Detection**: Identifies the highest-scoring student in the batch.
10. **Total Enrollment Counter**: Shows active student count in memory.
11. **Crash-Resistant Console Input**: Validates strings, numbers, and menu ranges using `try-catch` blocks and buffer clearing.

---

## 💻 3. Technologies Used

- **Language**: Java (JDK 17+)
- **Architecture**: Object-Oriented Programming (OOP)
- **Data Structure**: `java.util.ArrayList` (Dynamic in-memory storage)
- **I/O Engine**: `java.util.Scanner` with input parsing and exception trapping
- **External Dependencies**: None (Standard Java Standard Edition SDK only)

---

## 📁 4. Project Folder Structure

```text
SmartStudentPerformanceTracker/
│
├── ResultCalculator.java    # Interface defining evaluation contracts (Abstraction)
├── GradeCalculator.java     # Concrete grading & pass/fail engine (Interface Implementation)
├── Student.java             # Base entity class with encapsulation and method overloading
├── CollegeStudent.java      # Subclass adding semester/college details (Inheritance & Overriding)
├── StudentService.java      # Business logic, ArrayList operations, searching, and topper logic
├── Main.java                # Application entry point, interactive menu, and input validation
└── README.md                # Project documentation and presentation guide
```

---

## 🧠 5. Object-Oriented Programming (OOP) Concepts Applied

| OOP Concept | Where It Appears | Why It Is Used |
| :--- | :--- | :--- |
| **Classes & Objects** | All files (`Student`, `CollegeStudent`, `GradeCalculator`, `StudentService`, `Main`) | Models real-world entities (students, grading rules, report generation) with clean state and behavior. |
| **Encapsulation** | `Student.java`, `CollegeStudent.java`, `StudentService.java` | All member variables (e.g. `rollNumber`, `marks`, `studentList`) are declared `private`. Access is granted strictly via getters/setters with built-in validation (e.g., marks between 0 and 100). |
| **Constructors** | `Student.java`, `CollegeStudent.java` | Parameterized and default constructors initialize valid objects. `CollegeStudent` uses `super(...)` to invoke the parent constructor without repeating code. |
| **Inheritance** | `CollegeStudent extends Student` | Establishes an "is-a" relationship. Generic student attributes (name, marks, calculation logic) are reused, while college-specific attributes (`semester`, `collegeName`) are added cleanly. |
| **Method Overloading** | `Student.java` (`displayDetails()`), `StudentService.java` (`searchStudent(...)`) | Provides flexible methods with the same name but different parameters (e.g., summary view vs detailed subject scorecard; search by roll number vs search by roll number + department). |
| **Method Overriding** | `GradeCalculator.java`, `CollegeStudent.java` | `GradeCalculator` overrides interface methods (`@Override`). `CollegeStudent` overrides `displayDetails()` to print a specialized college banner and semester info. |
| **Abstraction** | `ResultCalculator.java` (Interface) | Separates the definition of what needs to be calculated from how it is calculated. If the grading policy changes, only `GradeCalculator` changes; `Student` and `StudentService` remain untouched. |
| **Polymorphism** | `StudentService.java`, `Main.java` | **Runtime Polymorphism**: Storing `CollegeStudent` instances inside a `Student` reference list (`ArrayList<Student>`) and dynamically calling overridden methods. Using `ResultCalculator` interface reference to invoke `GradeCalculator`. |
| **Exception Handling** | `Main.java`, `Student.java` | Uses `try-catch` with `NumberFormatException` to ensure the console never crashes when users type letters instead of menu numbers or marks. Throws `IllegalArgumentException` on invalid marks. |

---

## 🛠️ 6. How to Compile and Run

### Step 1: Open Terminal / Command Prompt
Navigate to the project folder:
```bash
cd "SmartStudentPerformanceTracker"
```

### Step 2: Compile All Java Files
```bash
javac *.java
```

### Step 3: Run the Application
```bash
java Main
```

*(Note: On systems where JDK is installed in a custom location, ensure `java` and `javac` are available in your system's PATH).*

---

## 📊 7. Sample Input & Output Walkthrough

### Main Menu
```text
=========================================================
         SMART STUDENT PERFORMANCE TRACKER v1.0         
             [Core Java & OOP Demonstration]             
=========================================================
  1. Add New Student Record
  2. Display All Student Records (Tabular View)
  3. Search Student by Roll Number
  4. View Individual Student Detailed Report Card
  5. Update Student (Details / Marks)
  6. Delete Student Record (with Confirmation)
  7. Find Class Topper (Highest Total Marks)
  8. View Total Registered Student Count
  9. Exit Application
=========================================================
Enter your choice (1-9): 
```

### Feature: Tabular View (Option 2)
```text
==========================================================================================
                                ALL REGISTERED STUDENTS                                   
==========================================================================================
| ROLL NO      | NAME                 | DEPT       | TOTAL   | AVERAGE  | GRADE  | STATUS   |
+--------------+----------------------+------------+---------+----------+--------+----------+
| 21CS101      | Arun Kumar           | CSE        | 470/500 |  94.00 % |   A    | PASS     |
| 21CS102      | Priya Dharshini      | CSE        | 413/500 |  82.60 % |   B    | PASS     |
| 21IT205      | Karthik Raja         | IT         | 365/500 |  73.00 % |   F    | FAIL     |
==========================================================================================
Total Enrolled Students: 3
```

### Feature: Detailed Scorecard / Arrear Check (Option 4)
```text
=================================================================
  INSTITUTION : PSG COLLEGE OF TECHNOLOGY                       
  SEMESTER    : 4 | DEPARTMENT: IT                                
=================================================================
| Roll Number          : 21IT205                                |
| Student Name         : Karthik Raja                           |
| Semester             : Semester 4                             |
| College Name         : PSG College of Technology              |
+---------------------------------------------------------------+
| SUBJECT-WISE SCORECARD:                                       |
| ------------------------------------------------------------- |
| Mathematics               :  85 / 100   [PASS           ]     |
| Data Structures           :  90 / 100   [PASS           ]     |
| Java Programming          :  35 / 100   [FAIL (Arrear)  ]     |
| Operating Systems         :  75 / 100   [PASS           ]     |
| Computer Networks         :  80 / 100   [PASS           ]     |
+---------------------------------------------------------------+
| Total Marks          : 365 / 500                              |
| Average Percentage   : 73.00                                  |
| Final Grade          : F                                      |
| Result Status        : FAILED (ARREARS PENDING)               |
=================================================================
```

---

## ⚠️ 8. Known Limitations (Version 1)

1. **In-Memory Storage**: Data persists only while the program is running. When the program exits, records reset (Database/File persistence planned for Version 2).
2. **Fixed Subject Count**: Configured for 5 standard subjects per student for academic consistency.
3. **Console Interface**: Runs in text terminal (GUI with JavaFX / Swing or Web UI planned for future versions).
