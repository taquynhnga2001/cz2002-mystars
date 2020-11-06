package view;

import java.util.*;
import javax.swing.*;
import java.awt.*;

import entity.*;

public class TableView {
    /**Display a table from an ArrayList of data */
    public static void displayTable(String frameName, String[] columnHeadings, ArrayList<Object[]> data, String title, int[] frameSize) {
        final JFrame frame = new JFrame(frameName);

        Object[][] rows = new Object[data.size()][];
        for (int i = 0; i<data.size(); i++) {
            rows[i] = data.get(i);
        }
 
        JTable table = new JTable(rows, columnHeadings);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
 
        JLabel lblHeading = new JLabel(title);
        lblHeading.setFont(new Font("Arial",Font.TRUETYPE_FONT, 14));
 
        frame.getContentPane().setLayout(new BorderLayout());
 
        frame.getContentPane().add(lblHeading,BorderLayout.PAGE_START);
        frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(frameSize[0], frameSize[1]);
        frame.setVisible(true);
    }

    /**Display infomation of a Course Index */
    public static void displayCourseInfo(CourseIndex courseIndex) {
        // display course info in table
        String[] columnHeadings = {"Class Type", "Group", "Day", "Time","Venue", "Remark"};
        ArrayList<Object[]> data = new ArrayList<>();
        ArrayList<CourseIndexType> classTypes = courseIndex.getClassTypes();
        Iterator<CourseIndexType> type = classTypes.iterator();

        CourseIndexType firstClass = type.next();
        // no need, let them in the while loop
        Object[] row = {firstClass.getClassType(), firstClass.getGroup(), 
                        firstClass.getDay(), firstClass.getTime(), 
                        firstClass.getVenue(), firstClass.getRemark()};
        data.add(row);
        while (type.hasNext()) {
            CourseIndexType t = type.next();
            Object[] nextRow = {t.getClassType(), t.getGroup(), t.getDay(), 
                t.getTime(), t.getVenue(), t.getRemark()};
            data.add(nextRow);
        }
        int[] frameSize = {900, 145};
        displayTable("Course Index Information", columnHeadings, data, "Index Number " + courseIndex.getIndex() + " - Course " + courseIndex.getCourseCode(), frameSize);
    }


    /**Display information of 2 chosen course indexs */
    public static void display2CourseIndexs(CourseIndex currentIndex, CourseIndex newIndex) {
        // display course info in table
        String[] columnHeadings = {"Class Type", "Group", "Day", "Time","Venue", "Remark"};
        ArrayList<Object[]>[] data = new ArrayList[2]; // store 2 course indexs
        data[0] = new ArrayList<>();
        data[1] = new ArrayList<>();
        // load current index data
        ArrayList<CourseIndexType> classTypes = currentIndex.getClassTypes();
        Iterator<CourseIndexType> type = classTypes.iterator();
        while (type.hasNext()) {
            CourseIndexType t = type.next();
            Object[] nextRow = {t.getClassType(), t.getGroup(), t.getDay(), 
                                t.getTime(), t.getVenue(), t.getRemark()};
            data[0].add(nextRow);
        }
        // load new index data
        classTypes = newIndex.getClassTypes();
        type = classTypes.iterator();
        while (type.hasNext()) {
            CourseIndexType t = type.next();
            Object[] nextRow = {t.getClassType(), t.getGroup(), t.getDay(), 
                                t.getTime(), t.getVenue(), t.getRemark()};
            data[1].add(nextRow);
        }

        // create table frame
        final JFrame frame = new JFrame("Change Index Number of Course");

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JLabel lblHeading = new JLabel("Subject " + currentIndex.getCourseCode());
        lblHeading.setFont(new Font("Arial",Font.BOLD, 16));
        main.add(lblHeading);

        // create label and panel for current index
        JLabel lblHeading1 = new JLabel("Current Index Number: " + currentIndex.getIndex());
        lblHeading1.setFont(new Font("Arial",Font.BOLD, 14));
        //panel
        Object[][] rows = new Object[data[0].size()][];
        for (int i = 0; i<data[0].size(); i++) {
            rows[i] = data[0].get(i);
        }
        JTable tableCurIndex = new JTable(rows, columnHeadings);
        JScrollPane scrollPaneCurIndex = new JScrollPane(tableCurIndex);
        tableCurIndex.setFillsViewportHeight(true);
        main.add(lblHeading1);
        main.add(scrollPaneCurIndex);

        // create label and panel for new index
        JLabel lblHeading2 = new JLabel("New Index Number: " + newIndex.getIndex());
        lblHeading2.setFont(new Font("Arial",Font.BOLD, 14));
        // panel
        rows = new Object[data[1].size()][];
        for (int i = 0; i<data[1].size(); i++) {
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
        int[] frameSize = {910, 300};
        frame.setSize(frameSize[0], frameSize[1]);
        frame.setVisible(true);
    }
}
