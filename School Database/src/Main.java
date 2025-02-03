import java.util.ArrayList; // Import ArrayList class

// Main class for running program
public class Main {
    public static void main(String[] args) {
        // Add 10 students to student list criteria
        // Create array for each students' field
        String[] studentFNames = {"Ben", "Masaki", "Peter", "Derrick", "Lucas", "Matteo", "Paul", "Constance", "Bob", "Alexa"};
        String[] studentLNames = {"Gibson", "Lau", "Clarke", "Su", "Yung", "Giovanni", "Peng", "Ko", "Hakemi", "Chen"};
        Integer[] studentGrades = {97, 96, 89, 91, 86, 98, 97, 99, 82, 80};

        ArrayList<Student> students = new ArrayList<>(); // Initialize ArrayList of students
        // Add a student to ArrayList of students for every field in studentFNames
        for (int i = 0; i < studentFNames.length; i++) {
            students.add(new Student(studentFNames[i], studentLNames[i], studentGrades[i]));
        }

        //Add 3 teachers to teacher list criteria
        // Create array for each teacher field
        String[] teacherFNames = {"Mara", "Mori", "Stefan"};
        String[] teacherLNames = {"Benson", "Hamilton", "Schmitt"};
        String[] teacherSubjects = {"Math", "Humanities", "Science"};

        // Add a teacher to PointGrey for each index in the arrays (specifically teacherFNames)
        ArrayList<Teacher> teachers = new ArrayList<>(); // Initialize ArrayList of teachers
        for (int i = 0; i < teacherFNames.length; i++) {
            teachers.add(new Teacher(teacherFNames[i], teacherLNames[i], teacherSubjects[i]));
        }

        // Create a school (PointGrey) and add aforementioned teachers and students
        School PointGrey = new School(teachers, students, "Point Grey", "Vancouver", 1929);

        // Display both lists criteria
        PointGrey.showStudents();
        PointGrey.showTeachers();

        // Remove 2 students criteria
        PointGrey.deleteStudent("Alexa", "Chen");
        PointGrey.deleteStudent("Matteo", "Giovanni");

        // Remove 1 teacher criteria
        PointGrey.deleteTeacher("Stefan", "Schmitt");

        // Display both lists again criteria
        PointGrey.showStudents();
        PointGrey.showTeachers();
    }
}
// If you were wondering, yes this is based off my real school and people I know
