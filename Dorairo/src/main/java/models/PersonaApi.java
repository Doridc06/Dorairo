package models;

public class PersonaApi {

	private int id;

	private String known_for_department;

	private String name;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the knownForDepartment
	 */
	public String getKnownForDepartment() {
		return known_for_department;
	}

	/**
	 * @param knownForDepartment the knownForDepartment to set
	 */
	public void setKnownForDepartment(String knownForDepartment) {
		this.known_for_department = knownForDepartment;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
