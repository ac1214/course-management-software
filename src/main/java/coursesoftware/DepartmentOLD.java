package coursesoftware;

public class DepartmentOLD {
	private String deptID;

	/**
	 * Constructor for a Department object
	 * 
	 * @param departmentName department name to assign to object
	 */
	public DepartmentOLD(String departmentName) {
		this.deptID = departmentName;
	}

	public String getDeptID() {
		return this.deptID;
	}

	public void setDeptID(String newDeptID) {
		this.deptID = newDeptID;
	}
}
