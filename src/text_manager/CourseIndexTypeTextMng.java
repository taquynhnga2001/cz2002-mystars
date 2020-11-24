package text_manager;

import java.io.*;
import java.util.*;

import custom_exceptions.*;
import entity.*;
import constants.*;

public class CourseIndexTypeTextMng extends TextManager {

    private final static String FILEPATH = FilePath.COURSE_INDEX_TYPE;
    private static final String SEPERATOR = ",";

    /** Read String from text file and return list of CourseIndexType objects of a CourseIndex */
    public static ArrayList<CourseIndexType> readClassTypesOfCourseIndex(String courseIndex) throws IOException {
        ArrayList<String> stringArray = read(FILEPATH);
        ArrayList<CourseIndexType> alr = new ArrayList<CourseIndexType>();// to store CourseIndex data

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","

            String index = star.nextToken().trim(); // first token: courseIndex
            String classType = star.nextToken().trim().toUpperCase(); // second token: classType
            star.nextToken(); // 3rd token: group
            String day = star.nextToken().trim().toUpperCase(); // 4th token: day
            if (index.equals(courseIndex)) {
                CourseIndexType courseIndexType = new CourseIndexType(index, classType, day);
                alr.add(courseIndexType);
            }  
        }
        return alr; // list of classTypes
    }

    // /** Read string from text file and return CourseIndex object */
    // public CourseIndexType returnCourseIndexType(String index) throws IOException, WrongCourseIndex {
    //     ArrayList stringArray = (ArrayList) read(FILEPATH);
    //     String index_;

    //     for (int i = 1; i < stringArray.size(); i++) {
    //         String st = (String) stringArray.get(i);
    //         StringTokenizer star = new StringTokenizer(st, this.SEPERATOR); // pass in the string to the string
    //                                                                         // tokenizer // using delimiter ","
    //         index_ = star.nextToken().trim(); // first token

    //         if (index_ == index) {
    //             return new CourseIndexType(index_);
    //         }
    //     }
    //     throw new WrongCourseIndex();
    // }

    /** Read String from text file and return CourseIndex attributes */
    public static ArrayList<String> readCourseIndexType(String index, String classType, String day) throws IOException, WrongCourseIndex{
        ArrayList<String> stringArray = read(FILEPATH);
        ArrayList<String> alr = new ArrayList<String>();// to store CourseIndex's attributes
        String index_;
        String classType_;
        String group;
        String day_;
        String time;
        String venue;
        String remark;

        for (int i = 1; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);
            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPERATOR); // pass in the string to the string
                                                                            // tokenizer // using delimiter ","
            index_ = star.nextToken().trim(); // first token
            classType_ = star.nextToken().trim().toUpperCase(); //second token
            group = star.nextToken().trim();
            day_ = star.nextToken().trim().toUpperCase();

            if (index_.equals(index) && classType_.equals(classType) && day_.equalsIgnoreCase(day)) {
                // classType = star.nextToken().trim(); // second token...
                // group = star.nextToken().trim();
                // day = star.nextToken().trim();
                time = star.nextToken().trim();
                venue = star.nextToken().trim();
                // alr.add(index_);
                // alr.add(classType);
                alr.add(group);
                // alr.add(day);
                alr.add(time);
                alr.add(venue);
                if (star.hasMoreTokens()) {
                    remark = star.nextToken().trim().replace("|", ",");
                    alr.add(remark);
                }
                return alr;
            }
        }
        throw new WrongCourseIndex();
    }



    /** Save a list of CourseIndexType objects to database */
    public static void saveCourseIndexTypes(ArrayList<Course> courseDB) throws IOException {
        List<String> al = new ArrayList<>(); // to store CourseIndex data
        String HEADING = "index,classType,group,day,time,venue,remark";
        al.add(HEADING);

        for (Course course : courseDB) {
            for (CourseIndex index : course.getCourseIndexs()) {
                for (CourseIndexType classType : index.getClassTypes()) {
                    StringBuilder st = new StringBuilder();
                    st.append(classType.getIndex().trim());
                    st.append(SEPERATOR);
                    st.append(classType.getClassType().trim());
                    st.append(SEPERATOR);
                    st.append(classType.getGroup().trim());
                    st.append(SEPERATOR);
                    st.append(classType.getDay().trim());
                    st.append(SEPERATOR);
                    st.append(classType.getTime().trim());
                    st.append(SEPERATOR);
                    st.append(classType.getVenue().trim());
                    st.append(SEPERATOR);
                    st.append(classType.getRemark().trim().replace(",", "|"));
                    al.add(st.toString());
                }
            }
        }
        write(FILEPATH, al); 
    }

    /**Save CourseIndexType in database after adding */
    public static void addCourseIndexType(String index, String classType, String group, String day, String time, String venue, String remark)
            throws IOException {
        ArrayList<String> stringArray = read(FILEPATH); // retrieve the old database
        StringBuilder st = new StringBuilder();
        st.append(index).append(SEPERATOR);
        st.append(classType).append(SEPERATOR);
        st.append(group).append(SEPERATOR);
        st.append(day).append(SEPERATOR);
        st.append(time).append(SEPERATOR);
        st.append(venue).append(SEPERATOR);
        st.append(remark.replace(",", "|"));
        stringArray.add(st.toString());
        write(FILEPATH, stringArray);
    }
    
}
