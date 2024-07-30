package dto;

public class MemberInfoDto {
	String id;
	String password;
	String name;
	int point;
	
	public MemberInfoDto(String id, String password, String name, int point) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.point = point;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	
	
}
