package pojos;

import java.util.List;

public class LoanTransactions {

    private List<MemberStatement> memberStatements;
    private LoansInService loansInService;
    private LoanTypes loanTypes;

    public LoanTypes getLoanTypes() {
        return loanTypes;
    }

    public void setLoanTypes(LoanTypes loanTypes) {
        this.loanTypes = loanTypes;
    }

    public List<MemberStatement> getMemberStatements() {
        return memberStatements;
    }

    public void setMemberStatements(List<MemberStatement> memberStatements) {
        this.memberStatements = memberStatements;
    }

    public LoansInService getLoansInService() {
        return loansInService;
    }

    public void setLoansInService(LoansInService loansInService) {
        this.loansInService = loansInService;
    }
}

