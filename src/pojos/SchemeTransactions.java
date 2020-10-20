package pojos;

import pojos.MemberStatement;
import pojos.Scheme;

import java.util.List;

public class SchemeTransactions {

    private List<MemberStatement> memberStatements;
    private Scheme scheme;
    private  String runningBalance;

    public List<MemberStatement> getMemberStatements() {
        return memberStatements;
    }

    public void setMemberStatements(List<MemberStatement> memberStatements) {
        this.memberStatements = memberStatements;
    }

    public Scheme getScheme() {
        return scheme;
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public String getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(String runningBalance) {
        this.runningBalance = runningBalance;
    }

    @Override
    public String toString() {
        return "SchemeTransactions{" +
                "memberStatements=" + memberStatements +
                ", scheme=" + scheme +
                ", runningBalance='" + runningBalance + '\'' +
                '}';
    }
}

