package pojos;

public class Member {
	private  String FirstName;
	private String OtherNames;
	private  String Surname;
	private  String PhoneNo ;
	private  String payrollNo;
	private String memberNo;
	private String idNo;
	private String emailaddress;
	private String Homeaddress;

	public String getHomeaddress() {
		return Homeaddress;
	}

	public void setHomeaddress(String homeaddress) {
		Homeaddress = homeaddress;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getOtherNames() {
		return OtherNames;
	}


	public void setOtherNames(String otherNames) {
		OtherNames = otherNames;
	}


	public String getSurname() {
		return Surname;
	}


	public void setSurname(String Surname) {
		this.Surname = Surname;
	} 
	
	 
	public String getPhoneNo() {
		return PhoneNo;
	}


	public void setPhoneNo(String PhoneNo) {
		this.PhoneNo = PhoneNo;
	}
	
	
		
	public String getPayrollNo() {
		return payrollNo;
	}


	public void setPayrollNo(String payrollNo) {
		this.payrollNo = payrollNo;
	}
	
	
	
	
	
	

	public String getMemberNo() {
		return memberNo;
	}

	


	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	
	@Override
	public String toString() {
		return "Member [OtherNames=" + OtherNames + ", Surname=" + Surname + ", PhoneNo=" + PhoneNo + ", payrollNo="
				+ payrollNo + ", memberNo=" + memberNo + ", idNo=" + idNo + "]";
	}
	
	
	
	
	
}
