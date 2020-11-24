package entity;

import java.io.IOException;
import java.util.ArrayList;

import custom_exceptions.*;
import text_manager.*;

public class CourseIndexType {					
	// course index type information
	private String index;
	private String classType;
	private String group;
	private String day;
	private String time;
	private String venue;
	private String remark;

	private String courseCode;

	private final int groupIdx = 0;
	private final int timeIdx = 1;
	private final int venueIdx = 2;
	private final int remarkIdx = 3;
	
	public CourseIndexType(String index, String classType, String day){
		this.index = index;
		this.classType = classType.toUpperCase();
		this.day = day.toUpperCase();
		try {
			ArrayList<String> attributes = CourseIndexTypeTextMng.readCourseIndexType(index, classType, day);
			this.group = attributes.get(groupIdx);
			this.time = attributes.get(timeIdx);
			this.venue = attributes.get(venueIdx);
			if (attributes.size() > this.remarkIdx) {
				this.remark = attributes.get(remarkIdx);	
			} else this.remark = "";
			
		} catch (WrongCourseIndex e) {
			System.out.println("Error: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public String getIndex() {
		return index;
	}
	public String getClassType() {
		return classType;
	}
	public String getGroup() {
		return group;
	}
	public String getDay() {
		return day;
	}
	public String getTime() {
		return time;
	}
	public String getVenue() {
		return venue;
	}
	public String getRemark() {
		return remark;
	}
	public String getCourseCode() {
		// courseCode of a courseIndexType is set when contructing a course
		return courseCode;
	}

	/**Return an array of week learning for this index */
	public ArrayList<Integer> getWeek() {
		ArrayList<Integer> result = new ArrayList<>();
		if (remark.equals("")) {
			for (int i = 1; i<=13; i++) {
				result.add(i);
			}
			return result;
		}
		String weeks = remark.split(" ")[1].substring(2).trim();
		if (weeks.contains("-")) {
			String[] fromto = weeks.split("-"); 
			int fromWeek = Integer.parseInt(fromto[0]);
			int toWeek = Integer.parseInt(fromto[1]);
			for (int i = fromWeek; i<=toWeek; i++) {
				result.add(i);
			}
			return result;
		}
		if (weeks.contains(",")) {
			String[] fromto = weeks.split(","); 
			for (String week : fromto) {
				result.add(Integer.parseInt(week));
			}
			return result;
		}
		return result;
	}


	public void setIndex(String index) {
		this.index = index;
	}
	public void setClassType(String type) {
		this.classType = type.toUpperCase();
	}
	public void setDay(String day) {
		this.day = day;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
}
