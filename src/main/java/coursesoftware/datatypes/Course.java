package coursesoftware.datatypes;

public class Course {
	private String courseID;
	private String courseDep;
	private String courseNum;
	private String courseName;
	private String prerequisites;
	private String antirequisites;

	public String getDescription() {
		return description;
	}

	private String description;

	/**
	 * Constructor for course object that will be used to display all of a courses
	 * attributes in a table
	 * 
	 * @param courseInfo Array containing course attributes
	 */
	public Course(String[] courseInfo) {
		courseID = courseInfo[0];
		courseDep = courseInfo[1];
		courseNum = courseInfo[2];
		courseName = courseInfo[3];
		if (courseInfo[4].equals("NONE")) {
			prerequisites = "";
		} else {
			prerequisites = courseInfo[4];

		}
		if (courseInfo[5].equals("NONE")) {
			antirequisites = "";
		} else {
			antirequisites = courseInfo[5];

		}
	}

	public Course(String courseID, String deptID, int courseNum, String courseName, String prerequisites, String antirequisites, String description) {
		this.courseID = courseID;
		this.courseDep = deptID;
		this.courseNum = courseNum + "";
		this.courseName = courseName;
		this.prerequisites = prerequisites;
		this.antirequisites = antirequisites;
		this.description = description;
	}

	public Course(String courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String newName) {
		this.courseName = newName;
	}

	public String getCourseID() {
		return courseID;
	}

	public String getCourseDep() {
		return courseDep;
	}

	public String getCourseNum() {
		return courseNum;
	}

	public String getPrerequisites() {
		return prerequisites;
	}

	public String getAntirequisites() {
		return antirequisites;
	}
}