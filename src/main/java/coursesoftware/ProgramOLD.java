package coursesoftware;

public class ProgramOLD {
	private String progID;

	/**
	 * Constructor for program object that will be used to display all of a courses
	 * attributes in a table
	 * 
	 * @param programName name of program
	 */
	public ProgramOLD(String programName) {
		this.progID = programName;
	}

	public String getProgID() {
		return this.progID;
	}

	public void setProgID(String newProgID) {
		this.progID = newProgID;
	}
}
