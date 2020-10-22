package statements;

import java.util.List;
import java.util.Scanner;

import pojos.*;
import com.itextpdf.text.Font;


public class WritePdf {

    //
//	Scanner myObj = new Scanner(System.in);
//	String statementNo = myObj.nextLine();
    static Font dark = new Font(Font.FontFamily.COURIER, 10);
    static Font font = new Font(Font.FontFamily.HELVETICA, 4);
    static Font bold= new Font(Font.FontFamily.TIMES_ROMAN,5);


    public static boolean writePdf(List<SchemeTransactions> schemeTransactions, Member member, List<LoanTransactions> memberLoans) {
        //public static final PdfNumber LANDSCAPE = new PdfNumber(90);


        Scanner myObj = new Scanner(System.in);
        int statementNo = Integer.parseInt(myObj.nextLine());


        if (statementNo == 1) {

            boolean b = MainStatement.writePdf(schemeTransactions, member, memberLoans);
            return b;

        }else if(statementNo == 2){

            boolean b = DetailedInsuranceStatement.writePdf(schemeTransactions, member, memberLoans);
            return  b;
        }
        else if(statementNo == 3){
            boolean b = CombinedStatement.writePdf(schemeTransactions, member, memberLoans);
            return  b;
        }
        else if(statementNo == 4){
            boolean b= insuranceStatement.writePdf(schemeTransactions, member, memberLoans);
            return  b;
        }
        else if(statementNo == 5){
            boolean b = interestStatement.writePdf(schemeTransactions, member, memberLoans);
            return  b;
        }
        else{
            return false;
        }

    }
}



