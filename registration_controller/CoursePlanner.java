package registration_controller;

import java.util.*;

import custom_exceptions.WrongCourseCode;
import entity.*;
import text_manager.*;
import view.PrintColor;

public class CoursePlanner {
    public static ArrayList<ArrayList<CourseIndex>> plan(String[] courseCode, ArrayList<Course> courseDB) {
        ArrayList<Course> chosenCourses = new ArrayList<>();
        for (String c : courseCode) {
            try {
                Course course = CourseTextMng.getCourse(courseDB, c);
                chosenCourses.add(course);
            } catch (WrongCourseCode e) {
                PrintColor.println(">>> " + e.getMessage(), "RED");
            }
        }

        HashMap<CourseIndex, ArrayList<CourseIndex>> adjList = getAdjacencyList(chosenCourses);
        return bfs(chosenCourses, adjList);
    }

    private static ArrayList<ArrayList<CourseIndex>> bfs(ArrayList<Course> chosenCourses,
            HashMap<CourseIndex, ArrayList<CourseIndex>> adjList) {
        int total = chosenCourses.size();
        int p = 0; // order in paths
        ArrayList<ArrayList<CourseIndex>> paths = new ArrayList<>();
        ArrayList<ArrayList<CourseIndex>> result = new ArrayList<>();
        Queue<Object[]> L = new LinkedList<>();

        Course firstCourse = chosenCourses.get(0);
        for (CourseIndex index : firstCourse.getCourseIndexs()) {
            Object[] node = new Object[2];
            node[0] = index;
            node[1] = p;
            L.add(node);
            paths.add(new ArrayList<>());
            paths.get(p).add((CourseIndex) node[0]);
            p++;
        }

        while (!L.isEmpty()) {
            Object[] v = L.remove();
            CourseIndex index = (CourseIndex) v[0];
            for (CourseIndex neighbor : adjList.get(index)) {
                if (!isClashed(paths.get((int) v[1]), neighbor) && !contains(paths.get((int) v[1]), neighbor)) {
                    Object[] w = new Object[2];
                    w[0] = neighbor;
                    w[1] = p;
                    L.add(w);
                    ArrayList<CourseIndex> path_w = new ArrayList<>();
                    path_w = (ArrayList) paths.get((int) v[1]).clone();
                    path_w.add(neighbor);
                    paths.add(path_w);
                    if (path_w.size() == total)
                        result.add(path_w);
                    p++;
                }
            }
        }
        return result;
    }

    private static HashMap<CourseIndex, ArrayList<CourseIndex>> getAdjacencyList(ArrayList<Course> chosenCourses) {
        HashMap<CourseIndex, ArrayList<CourseIndex>> adjList = new HashMap<>();
        for (int i=0; i<chosenCourses.size()-1; i++) {
            Course nextCourse = chosenCourses.get(i+1);
            for (CourseIndex index : chosenCourses.get(i).getCourseIndexs()) {
                adjList.put(index, nextCourse.getCourseIndexs());
                // remove clashed index
                for (CourseIndex n : nextCourse.getCourseIndexs()) {
                    CourseIndex clashedIndex;
                    do {
                        clashedIndex = StudentController.clashWith(adjList.get(index), n);
                        if (clashedIndex == null)
                            break;
                        adjList.get(index).remove(n);
                    } while (clashedIndex != null);
                }
            }
        }
        // let adj of last course be empty
        for (CourseIndex last : chosenCourses.get(chosenCourses.size()-1).getCourseIndexs()) {
            adjList.put(last, new ArrayList<>());
        }
        return adjList;
    }

    private static boolean contains(ArrayList<CourseIndex> courseIndexs, CourseIndex index) {
        for (CourseIndex c : courseIndexs) {
            if (index.getCourseCode().equals(c.getCourseCode()))
                return true;
        }
        return false;
    }

    private static boolean isClashed(ArrayList<CourseIndex> path, CourseIndex courseIndex) {
        if (StudentController.clashWith(path, courseIndex) == null)
            return false;
        else
            return true;
    }
}