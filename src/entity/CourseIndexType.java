package src.entity;

import java.io.IOException;
import java.util.ArrayList;

import src.custom_exceptions.*;
import src.text_manager.*;

/**
 * Represents a course index type (class type) in the course
 * 
 * @author Ta Quynh Nga
 */
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


	
	/** 
	 * @return index of the course index that this class type belongs to
	 */
	public String getIndex() {
		return index;
	}
	
	/** 
	 * @return class type
	 */
	public String getClassType() {
		return classType;
	}
	
	/** 
	 * @return group name of this object
	 */
	public String getGroup() {
		return group;
	}
	
	/** 
	 * @return day to study of this object
	 */
	public String getDay() {
		return day;
	}
	
	/** 
	 * @return time to study of this object
	 */
	public String getTime() {
		return time;
	}
	
	/** 
	 * @return the venue where it is held
	 */
	public String getVenue() {
		return venue;
	}
	
	/** 
	 * @return remark of teaching week
	 */
	public String getRemark() {
		return remark;
	}
	
	/** 
	 * @return course code of the course that this object belongs to
	 */
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


	
	/** 
	 * set the index to the index that it belongs to
	 * @param index
	 */
	public void setIndex(String index) {
		this.index = index;
	}
	
	/** 
	 * set the class type to the class type specified
	 * @param type
	 */
	public void setClassType(String type) {
		this.classType = type.toUpperCase();
	}
	
	/** 
	 * set the day to the day specified
	 * @param day
	 */
	public void setDay(String day) {
		this.day = day;
	}
	
	/** 
	 * set the time to the specified time
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	/** 
	 * set the venue to the specified venue
	 * @param venue
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	/** 
	 * set the remark to the specified remark
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/** 
	 * set the course code of the course that it belongs to
	 * @param courseCode
	 */
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
}
