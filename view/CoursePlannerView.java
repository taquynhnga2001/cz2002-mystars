package view;

import java.util.*;
import entity.*;
import constants.Color;

public class CoursePlannerView {
    public static void view(ArrayList<ArrayList<CourseIndex>> plans) {
        int i = 0; 
        for (ArrayList<CourseIndex> plan : plans) {
            PrintColor.println("+-------------------------------------------------------------+", "CYAN");
            PrintColor.println("|" + (i+1) + "                                                           ", "CYAN");
            for (CourseIndex index : plan) {
                System.out.println(Color.CYAN + "|" + Color.RESET + index.toString() + Color.CYAN + "" + Color.RESET);
            }
            PrintColor.println("+-------------------------------------------------------------+", "CYAN");
            System.out.println();
            i++;
        }
        if (plans.size() == 0) {
            PrintColor.println("There are 0 combinations", "RED");
        } else {
            PrintColor.println("There are " + plans.size() + " combinations", "GREEN");
        }
    }
}
