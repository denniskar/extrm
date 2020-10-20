package pojos;

public class LoansInService {


  private String  loanSerialNumber ;
  private  String loanType ;
  private String   memberNumber;


    public String getLoanSerialNumber() {
        return loanSerialNumber;
    }

    public void setLoanSerialNumber(String loanSerialNumber) {
        this.loanSerialNumber = loanSerialNumber;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    @Override
    public String toString() {
        return "LoansInService{" +
                "loanSerialNumber='" + loanSerialNumber + '\'' +
                ", loanType='" + loanType + '\'' +
                ", memberNumber='" + memberNumber + '\'' +
                '}';
    }
}
