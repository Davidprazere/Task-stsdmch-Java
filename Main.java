import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Course {
    String name;
    int hours;

    Course(String name, int hours) {
        this.name = name;
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Курс: " + name + ", Часы: " + hours;
    }
}

class Teacher {
    String fullName;
    int age;
    ArrayList<Course> courses;

    Teacher(String fullName, int age) {
        this.fullName = fullName;
        this.age = age;
        this.courses = new ArrayList<>();
    }

    void addCourse(Course course) {
        courses.add(course);
    }

    void removeCourse(Course course) {
        courses.remove(course);
    }

    @Override
    public String toString() {
        return "Преподаватель: " + fullName + ", Возраст: " + age + ", Курсы: " + courses;
    }
}

public class Main extends JFrame {
    static ArrayList<Teacher> teachers = new ArrayList<>();

    private final JTextArea outputArea;

    public Main() {
        setTitle("Управление преподавателями и курсами");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JButton addTeacherButton = new JButton("Добавить преподавателя");
        JButton addCourseButton = new JButton("Добавить курс");
        JButton removeCourseButton = new JButton("Удалить курс");
        JButton showTeachersButton = new JButton("Показать всех преподавателей");

        addTeacherButton.addActionListener(new AddTeacherAction());
        addCourseButton.addActionListener(new AddCourseAction());
        removeCourseButton.addActionListener(new RemoveCourseAction());
        showTeachersButton.addActionListener(new ShowTeachersAction());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        panel.add(addTeacherButton);
        panel.add(addCourseButton);
        panel.add(removeCourseButton);
        panel.add(showTeachersButton);

        add(panel, BorderLayout.WEST);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private class AddTeacherAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fullName = JOptionPane.showInputDialog("Введите ФИО преподавателя:");
            String ageStr = JOptionPane.showInputDialog("Введите возраст преподавателя:");
            try {
                int age = Integer.parseInt(ageStr);
                teachers.add(new Teacher(fullName, age));
                outputArea.append("Добавлен преподаватель: " + fullName + "\n");
            } catch (NumberFormatException ex) {
                outputArea.append("Ошибка: Введите корректный возраст.\n");
            }
        }
    }

    private class AddCourseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String teacherName = JOptionPane.showInputDialog("Введите ФИО преподавателя:");
            Teacher teacher = findTeacher(teacherName);
            if (teacher != null) {
                String courseName = JOptionPane.showInputDialog("Введите название курса:");
                String hoursStr = JOptionPane.showInputDialog("Введите количество часов:");
                try {
                    int hours = Integer.parseInt(hoursStr);
                    teacher.addCourse(new Course(courseName, hours));
                    outputArea.append("Добавлен курс: " + courseName + " к преподавателю: " + teacherName + "\n");
                } catch (NumberFormatException ex) {
                    outputArea.append("Ошибка: Введите корректное количество часов.\n");
                }
            } else {
                outputArea.append("Преподаватель не найден.\n");
            }
        }
    }

    private class RemoveCourseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String teacherName = JOptionPane.showInputDialog("Введите ФИО преподавателя:");
            Teacher teacher = findTeacher(teacherName);
            if (teacher != null) {
                String courseToRemove = JOptionPane.showInputDialog("Введите название курса для удаления:");
                teacher.removeCourse(new Course(courseToRemove, 0)); // Количество часов не важно для удаления
                outputArea.append("Удален курс: " + courseToRemove + " у преподавателя: " + teacherName + "\n");
            } else {
                outputArea.append("Преподаватель не найден.\n");
            }
        }
    }

    private class ShowTeachersAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            outputArea.setText(""); // Очистка текстовой области
            for (Teacher t : teachers) {
                outputArea.append(t.toString() + "\n");
            }
        }
    }

    private Teacher findTeacher(String fullName) {
        for (Teacher teacher : teachers) {
            if (teacher.fullName.equalsIgnoreCase(fullName)) {
                return teacher;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
