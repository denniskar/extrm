import Connector.DBConnection;
import pojos.*;
import statements.Utils;
import statements.WritePdf;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main (String[]args){
        try{


            Scanner myObj = new Scanner(System.in);
            System.out.println("Payroll number");

            String payroll = myObj.nextLine();

            Member member = Utils.fetchMember(payroll);

            if(member != null) {
                List<SchemeTransactions> schemeTransactions = new ArrayList<>();

                List<LoanTransactions> memberLoans = new ArrayList<>();
                for (Scheme scheme : Utils.fetchSchemes()) {
                    List<MemberStatement> memberStatements = Utils.fetchMemberStatement(scheme.getSchemeCode(), member);

                    if (memberStatements.size() > 0) {
                        Double runningB = 0.0;
                        Double loanbalance=0.0;

                        for (MemberStatement memberSts : memberStatements ) {
                            Double shareCr = Double.parseDouble(memberSts.getShare_Cr());
                            Double shareDr = Double.parseDouble(memberSts.getShare_Dr());
                            Double value = shareCr + shareDr;
                            runningB += value;
                            System.out.println(memberSts.getDocument_Number());
                            //System.out.println(value);
                       }
//
//                        for (MemberStatement memberSts : memberStatements) {
//                            Double shareCr = Double.parseDouble(memberSts.getLoan_Cr());
//                            Double shareDr = Double.parseDouble(memberSts.getLoan_Dr());
//                            Double value = shareCr - shareDr;
//                            loanbalance += value;
//                            System.out.println(memberSts.getDocument_Number());
//                            //System.out.println(value);
//                        }


                        SchemeTransactions schemeTransactions1 = new SchemeTransactions();
                        schemeTransactions1.setMemberStatements(memberStatements);
                        schemeTransactions1.setScheme(scheme);
                        schemeTransactions1.setRunningBalance(String.valueOf(runningB));
                        //print pdf here

                        schemeTransactions.add(schemeTransactions1);

                        //Fetch Member Loans
                    }

                }

                memberLoans= Utils.fetchMemberLoans(member,"10");


                if (WritePdf.writePdf(schemeTransactions, member, memberLoans)) {
                    System.out.println("Document Generated !!!! ");
                }else{
                    System.out.println("Document  Not Generated !!!! ");
                }


            }else{
                System.out.println("Member Doesn't exist");
            }


        }
        catch (Exception e){
            System.err.println(e);

        }}
}
