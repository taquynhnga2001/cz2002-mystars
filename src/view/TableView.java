package view;

import java.util.*;
import javax.swing.*;
import java.awt.*;

import entity.*;

public class TableView {
    /** Display a table from an ArrayList of data */
    public static void displayTable(String frameName, String[] columnHeadings, ArrayList<Object[]> data, String[] title,
            int[] frameSize) {
        final JFrame frame = new JFrame(frameName);
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        Object[][] rows = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            rows[i] = data.get(i);
        }

        JTable table = new JTable(rows, columnHeadings);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // JLabel lblHeading = new JLabel(title);
        // lblHeading.setFont(new Font("Arial",Font.TRUETYPE_FONT, 14));

        frame.getContentPane().setLayout(new BorderLayout());

        for (String t : title) {
            JLabel lblHeading = new JLabel(t);
            lblHeading.setFont(new Font("Arial", Font.BOLD, 14));
            main.add(lblHeading);
        }
        main.add(scrollPane);
        frame.add(main);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(frameSize[0], frameSize[1]);
        frame.setVisible(true);
    }

    /** Display infomation of a Course Index */
    public static void displayCourseIndexInfo(CourseIndex courseIndex) {
        // display course info in table
        String[] columnHeadings = { "Class Type", "Group", "Day", "Time", "Venue", "Remark" };
        String[] title = { "Index Number " + courseIndex.getIndex() + " - Course " + courseIndex.getCourseCode() + " "
                + courseIndex.getCourseName() };
        ArrayList<Object[]> data = new ArrayList<>();
        ArrayList<CourseIndexType> classTypes = courseIndex.getClassTypes();
        Iterator<CourseIndexType> type = classTypes.iterator();

        CourseIndexType firstClass = type.next();
        // no need, let them in the while loop
        Object[] row = { firstClass.getClassType(), firstClass.getGroup(), firstClass.getDay(), firstClass.getTime(),
                firstClass.getVenue(), firstClass.getRemark() };
        data.add(row);
        while (type.hasNext()) {
            CourseIndexType t = type.next();
            Object[] nextRow = { t.getClassType(), t.getGroup(), t.getDay(), t.getTime(), t.getVenue(), t.getRemark() };
            data.add(nextRow);
        }
        int[] frameSize = { 900, 145 };
        displayTable("Course Index Information", columnHeadings, data, title, frameSize);
    }

    /** Display information of 2 chosen course indexs */
    public static void display2CourseIndexs(CourseIndex index1, CourseIndex index2, String frameTitle, String label1,
            String label2) {
        // display course info in table
        String[] columnHeadings = { "Class Type", "Group", "Day", "Time", "Venue", "Remark" };
        ArrayList<Object[]>[] data = new ArrayList[2]; // store 2 course indexs
        data[0] = new ArrayList<>();
        data[1] = new ArrayList<>();
        // load current index data
        ArrayList<CourseIndexType> classTypes = index1.getClassTypes();
        Iterator<CourseIndexType> type = classTypes.iterator();
        while (type.hasNext()) {
            CourseIndexType t = type.next();
            Object[] nextRow = { t.getClassType(), t.getGroup(), t.getDay(), t.getTime(), t.getVenue(), t.getRemark() };
            data[0].add(nextRow);
        }
        // load new index data
        classTypes = index2.getClassTypes();
        type = classTypes.iterator();
        while (type.hasNext()) {
            CourseIndexType t = type.next();
            Object[] nextRow = { t.getClassType(), t.getGroup(), t.getDay(), t.getTime(), t.getVenue(), t.getRemark() };
            data[1].add(nextRow);
        }

        // create table frame
        final JFrame frame = new JFrame(frameTitle);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JLabel lblHeading = new JLabel("Subject " + index1.getCourseCode());
        lblHeading.setFont(new Font("Arial", Font.BOLD, 16));
        main.add(lblHeading);

        // create label and panel for current index
        JLabel lblHeading1 = new JLabel(label1 + index1.getIndex());
        lblHeading1.setFont(new Font("Arial", Font.BOLD, 14));
        // panel
        Object[][] rows = new Object[data[0].size()][];
        for (int i = 0; i < data[0].size(); i++) {
            rows[i] = data[0].get(i);
        }
        JTable tableCurIndex = new JTable(rows, columnHeadings);
        JScrollPane scrollPaneCurIndex = new JScrollPane(tableCurIndex);
        tableCurIndex.setFillsViewportHeight(true);
        main.add(lblHeading1);
        main.add(scrollPaneCurIndex);

        // create label and panel for new index
        JLabel lblHeading2 = new JLabel(label2 + index2.getIndex());
        lblHeading2.setFont(new Font("Arial", Font.BOLD, 14));
        // panel
        rows = new Object[data[1].size()][];
        for (int i = 0; i < data[1].size(); i++) {
            rows[i] = data[1].get(i);
        }
        JTable tableNewIndex = new JTable(rows, columnHeadings);
        JScrollPane scrollPaneNewIndex = new JScrollPane(tableNewIndex);
        tableNewIndex.setFillsViewportHeight(true);
        main.add(lblHeading2);
        main.add(scrollPaneNewIndex);

        // add main panel to frame
        frame.add(main);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int[] frameSize = { 910, 300 };
        frame.setSize(frameSize[0], frameSize[1]);
        frame.setVisible(true);
    }

    /** Display studentDB */
    public static void displayStudentDB(ArrayList<Student> studentDB) {
        // student info in table
        String[] columnHeadings = { "No.", "Name", "Matric Num", "Gender", "Nationality" };
        ArrayList<Object[]> data = new ArrayList<>();
        // add student info to data
        int no = 0;
        for (Student student : studentDB) {
            String name = student.getName();
            String matricNum = student.getMatricNum();
            String gender = student.getGender();
            String nationality = student.getNationality();
            Object[] row = { ++no, name, matricNum, gender, nationality };
            data.add(row);
        }
        int[] frameSize = { 900, 500 };
        String[] title = {};
        displayTable("List of Students", columnHeadings, data, title, frameSize);
    }

    /** Display StudentDB with new Student marking */
    public static void displayStudentDB(ArrayList<Student> studentDB, Student newStudent) {
        // student info in table
        String[] columnHeadings = { "No.", "Name", "Matric Num", "Gender", "Nationality" };
        ArrayList<Object[]> data = new ArrayList<>();
        // add student info to data
        int no = 0;
        for (Student student : studentDB) {
            String name = student.getName();
            String matricNum = student.getMatricNum();
            String status = "";
            if (newStudent.getMatricNum().equalsIgnoreCase(matricNum))
                status = " [NEW]"; // mark new student
            String gender = student.getGender();
            String nationality = student.getNationality();
            Object[] row = { ++no + status, name, matricNum, gender, nationality };
            data.add(row);
        }
        String[] title = {};
        int[] frameSize = { 700, 500 };
        displayTable("List of Students", columnHeadings, data, title, frameSize);
    }

        /** Display StudentDB with new Student marking */
        public static void displayStudentSorting(Comparable[] studentDB, String by) {
            // student info in table
            String[] columnHeadings = { "No.", "Name", "Matric Num", "Gender", "Nationality" };
            ArrayList<Object[]> data = new ArrayList<>();
            // add student info to data
            int no = 0;
            for (Comparable s : studentDB) {
                Student student = (Student)s;
                String name = student.getName();
                String matricNum = student.getMatricNum();
                String gender = student.getGender();
                String nationality = student.getNationality();
                Object[] row = { ++no, name, matricNum, gender, nationality };
                data.add(row);
            }
            String[] title = { "List of Students enrolled in: " + by };
            int[] frameSize = { 800, 300 };
            displayTable("List of Students", columnHeadings, data, title, frameSize);
        }

    /** Display CourseDB with new Course marking after adding or updating */
    public static void displayCourseAfterAdding(ArrayList<Course> courseDB, Course newCourse, String choiceOfDisplay) {
        // data table 1: courses
        // course info in table
        String[] columnHeadings1 = { "No.", "Course Code", "Course Name", "School", "AU" };
        ArrayList<Object[]> dataCourse = new ArrayList<>();
        // add course info to dataCourse
        int no = 0;
        for (Course course : courseDB) {
            String courseCode = course.getCourseCode();
            String status = "";
            if (newCourse.getCourseCode().equalsIgnoreCase(courseCode)) {
                if (choiceOfDisplay.equalsIgnoreCase("A"))
                    status = " [NEW]"; // mark new course
                else if (choiceOfDisplay.equalsIgnoreCase("U"))
                    status = " [UPDATED]";
            }

            String courseName = course.getCourseName();
            String school = course.getSchool();
            String AU = "" + course.getAU();
            Object[] row = { ++no + status, courseCode, courseName, school, AU };
            dataCourse.add(row);
        }

        // data table 2: course index
        String[] columnHeadings2 = { "Index Number", "Capacity", "Class Type", "Group", "Day", "Time", "Venue",
                "Remark" };
        ArrayList<Object[]> dataIndex = new ArrayList<>();
        // display registered courses
        for (CourseIndex courseIndex : newCourse.getCourseIndexs()) {
            String index = courseIndex.getIndex();
            int capacity = courseIndex.getCapacity();
            ArrayList<CourseIndexType> classTypes = courseIndex.getClassTypes();
            Iterator<CourseIndexType> type = classTypes.iterator();

            CourseIndexType firstClass = type.next();
            Object[] row = { index, capacity, firstClass.getClassType(), firstClass.getGroup(), firstClass.getDay(),
                    firstClass.getTime(), firstClass.getVenue(), firstClass.getRemark() };
            dataIndex.add(row);

            while (type.hasNext()) {
                CourseIndexType t = type.next();
                Object[] nextRow = { "", "", t.getClassType(), t.getGroup(), t.getDay(), t.getTime(), t.getVenue(),
                        t.getRemark() };
                dataIndex.add(nextRow);
            }
        }

        // create table frame
        final JFrame frame = new JFrame("List of Courses");

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        // create label and panel for table 1
        JLabel lblHeading1 = new JLabel("Courses");
        lblHeading1.setFont(new Font("Arial", Font.BOLD, 14));
        // panel
        Object[][] rows = new Object[dataCourse.size()][];
        for (int i = 0; i < dataCourse.size(); i++) {
            rows[i] = dataCourse.get(i);
        }
        JTable table1 = new JTable(rows, columnHeadings1);
        JScrollPane panel1 = new JScrollPane(table1);
        table1.setFillsViewportHeight(true);
        main.add(lblHeading1);
        main.add(panel1);

        // create label and panel for table 2
        String adjactive = "";
        if (choiceOfDisplay.equalsIgnoreCase("A"))
            adjactive = "new"; // mark new course
        else
            adjactive = "updated";
        JLabel lblHeading2 = new JLabel("Index Numbers in the " + adjactive + " Course " + newCourse.getCourseCode()
                + " " + newCourse.getCourseName());
        lblHeading2.setFont(new Font("Arial", Font.BOLD, 14));
        // panel
        rows = new Object[dataIndex.size()][];
        for (int i = 0; i < dataIndex.size(); i++) {
            rows[i] = dataIndex.get(i);
        }
        JTable table2 = new JTable(rows, columnHeadings2);
        JScrollPane panel2 = new JScrollPane(table2);
        table2.setFillsViewportHeight(true);
        main.add(lblHeading2);
        main.add(panel2);

        // add main panel to frame
        frame.add(main);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int[] frameSize = { 910, 500 };
        frame.setSize(frameSize[0], frameSize[1]);
        frame.setVisible(true);
    }

    /** Display course before updating */
    public static void displayCourseB4Updating(Course updateCourse) {
        String[] columnHeadings = { "Index Number", "Capacity", "Class Type", "Group", "Day", "Time", "Venue", "Remark" };
        String[] title = new String[3];
        title[0] = updateCourse.getCourseCode() + " " + updateCourse.getCourseName();
        title[1] = "School: " + updateCourse.getSchool();
        title[2] = "AUs: " + String.valueOf(updateCourse.getAU());
        ArrayList<Object[]> data = new ArrayList<>();
        // display course index
        for (CourseIndex ci : updateCourse.getCourseIndexs()) {
            String index = ci.getIndex();
            ArrayList<CourseIndexType> classTypes = ci.getClassTypes();
            Iterator<CourseIndexType> type = classTypes.iterator();
            int capacity = ci.getCapacity();

            CourseIndexType firstClass = type.next();
            Object[] row = { index, capacity, firstClass.getClassType(), firstClass.getGroup(),
                    firstClass.getDay(), firstClass.getTime(), firstClass.getVenue(), firstClass.getRemark() };
            data.add(row);

            while (type.hasNext()) {
                CourseIndexType t = type.next();
                Object[] nextRow = { "", "", t.getClassType(), t.getGroup(), t.getDay(), t.getTime(),
                        t.getVenue(), t.getRemark() };
                data.add(nextRow);
            }
        }
        int[] frameSize = { 900, 500 };
        TableView.displayTable("Course Information", columnHeadings, data, title, frameSize);
    }
}