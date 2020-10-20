package pojos;


public class MemberStatement {
	private String period;
	private String peference_Name;
	private String activityRef;
	private String document_Date;
	private String document_Number;
	private String reference_Code;
	private String scheme;
	private String  payroll_No;
	private String  employer_Code;
	private String  sysCode;
	private String trans_Date;
	private String Interest_Dr;
	private String loan_Bal;
	private String loan_Cr;
	private String loan_Dr;
	private String share_Bal;
	private String share_Cr;
	private String  share_Dr;
	private String interest_Cr ;
	private String  interest_Bal;
	private String insurance_Dr ;
	private String insurance_Cr ;
	private String  penalty_Dr;
	private String  penalty_Cr;
	private String entryTime ;

	public String getRunningBalance() {
		return runningBalance;
	}

	public void setRunningBalance(String runningBalance) {
		this.runningBalance = runningBalance;
	}

	private String runningBalance;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPeference_Name() {
		return peference_Name;
	}

	public void setPeference_Name(String peference_Name) {
		this.peference_Name = peference_Name;
	}

	public String getActivityRef() {
		return activityRef;
	}

	public void setActivityRef(String activityRef) {
		this.activityRef = activityRef;
	}

	public String getDocument_Date() {
		return document_Date;
	}

	public void setDocument_Date(String document_Date) {
		this.document_Date = document_Date;
	}

	public String getDocument_Number() {
		return document_Number;
	}

	public void setDocument_Number(String document_Number) {
		this.document_Number = document_Number;
	}

	public String getReference_Code() {
		return reference_Code;
	}

	public void setReference_Code(String reference_Code) {
		this.reference_Code = reference_Code;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getPayroll_No() {
		return payroll_No;
	}

	public void setPayroll_No(String payroll_No) {
		this.payroll_No = payroll_No;
	}

	public String getEmployer_Code() {
		return employer_Code;
	}

	public void setEmployer_Code(String employer_Code) {
		this.employer_Code = employer_Code;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	public String getTrans_Date() {
		return trans_Date;
	}

	public void setTrans_Date(String trans_Date) {
		this.trans_Date = trans_Date;
	}

	public String getInterest_Dr() {
		return Interest_Dr;
	}

	public void setInterest_Dr(String interest_Dr) {
		Interest_Dr = interest_Dr;
	}

	public String getLoan_Bal() {
		return loan_Bal;
	}

	public void setLoan_Bal(String loan_Bal) {
		this.loan_Bal = loan_Bal;
	}

	public String getLoan_Cr() {
		return loan_Cr;
	}

	public void setLoan_Cr(String loan_Cr) {
		this.loan_Cr = loan_Cr;
	}

	public String getLoan_Dr() {
		return loan_Dr;
	}

	public void setLoan_Dr(String loan_Dr) {
		this.loan_Dr = loan_Dr;
	}

	public String getShare_Bal() {
		return share_Bal;
	}

	public void setShare_Bal(String share_Bal) {
		this.share_Bal = share_Bal;
	}

	public String getShare_Cr() {
		return share_Cr;
	}

	public void setShare_Cr(String share_Cr) {
		this.share_Cr = share_Cr;
	}

	public String getShare_Dr() {
		return share_Dr;
	}

	public void setShare_Dr(String share_Dr) {
		this.share_Dr = share_Dr;
	}

	public String getInterest_Cr() {
		return interest_Cr;
	}

	public void setInterest_Cr(String interest_Cr) {
		this.interest_Cr = interest_Cr;
	}

	public String getInterest_Bal() {
		return interest_Bal;
	}

	public void setInterest_Bal(String interest_Bal) {
		this.interest_Bal = interest_Bal;
	}

	public String getInsurance_Dr() {
		return insurance_Dr;
	}

	public void setInsurance_Dr(String insurance_Dr) {
		this.insurance_Dr = insurance_Dr;
	}

	public String getInsurance_Cr() {
		return insurance_Cr;
	}

	public void setInsurance_Cr(String insurance_Cr) {
		this.insurance_Cr = insurance_Cr;
	}

	public String getPenalty_Dr() {
		return penalty_Dr;
	}

	public void setPenalty_Dr(String penalty_Dr) {
		this.penalty_Dr = penalty_Dr;
	}

	public String getPenalty_Cr() {
		return penalty_Cr;
	}

	public void setPenalty_Cr(String penalty_Cr) {
		this.penalty_Cr = penalty_Cr;
	}

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	@Override
	public String toString() {
		return "MemberStatement{" +
				"document_Date='" + document_Date + '\'' +
				", document_Number='" + document_Number + '\'' +
				'}';
	}
}
