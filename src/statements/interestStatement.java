package statements;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pojos.LoanTransactions;
import pojos.Member;
import pojos.MemberStatement;
import pojos.SchemeTransactions;

import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class interestStatement {
    static Font dark= new Font(Font.FontFamily.COURIER,10);
    static Font font= new Font(Font.FontFamily.HELVETICA,4);
    static Font bold= new Font(Font.FontFamily.TIMES_ROMAN,5);
    public static boolean writePdf(List<SchemeTransactions> schemeTransactions, Member member, List<LoanTransactions> memberLoans) {
        //public static final PdfNumber LANDSCAPE = new PdfNumber(90);

        Document document=new Document();
        Rectangle rect =new Rectangle(PageSize.A4);
        rect.setBackgroundColor(new BaseColor(22, 233, 233));
        document.setPageSize(rect);


//		Scanner myObj = new Scanner(System.in);
//		int statementNo = Integer.parseInt(myObj.nextLine());



        //Detailed Statement pdf writer


        // if(statementNo==1) {
        try {
            String file_name = "C:\\Users\\simon\\Desktop\\generation\\statements.pdf";

            PdfWriter.getInstance(document, new FileOutputStream(file_name));
            document.open();
//				   Paragraph linebreak = new Paragraph("-------------------------------------------------------------------");
            Paragraph space = new Paragraph("   ");
            Paragraph StatementType= new Paragraph("INTEREST STATEMENT");
            StatementType.setAlignment(Paragraph.ALIGN_CENTER);
            Paragraph Surname = new Paragraph("Name:"+member.getFirstName() +" "+ member.getSurname() + " " +member.getOtherNames(), dark);
            Paragraph PhoneNo = new Paragraph("Phone Number:" + member.getPhoneNo(), dark);
            Paragraph idNo = new Paragraph("ID Number:" + member.getIdNo(), dark);
            Paragraph memberNo = new Paragraph("Member Number:" + member.getMemberNo(), dark);
            memberNo.setAlignment(Paragraph.ALIGN_RIGHT);
            Paragraph payrollno = new Paragraph("Payroll Number:" + member.getPayrollNo(), dark);
            payrollno.setAlignment(Paragraph.ALIGN_RIGHT);
            Paragraph email =new Paragraph("Email Address:"+member.getEmailaddress(),dark);
            email.setAlignment(Paragraph.ALIGN_RIGHT);

            document.add(StatementType);
            document.add(Surname);
            document.add(PhoneNo);
            document.add(idNo);
            document.add(payrollno);
            document.add(memberNo);
            document.add(email);
//				   document.add(space);
//				   document.add(space);
//				   document.add(space);


            //add table



//
//            for (SchemeTransactions schemeTransaction:schemeTransactions) {
//                PdfPTable table = createTable();
//
//                document.add(new Paragraph("Scheme:" +schemeTransaction.getScheme().getSchemeCode()+":"+schemeTransaction.getScheme().getSchemeName(), dark));
//                document.add(space);
//
//                for (int i = 0; i < schemeTransaction.getMemberStatements().size(); i++) {
//                    MemberStatement statement = schemeTransaction.getMemberStatements().get(i);
//                    table.addCell(new Phrase(statement.getDocument_Date(), font));
//                    table.addCell(new Phrase(statement.getActivityRef(), font));
//                    table.addCell(new Phrase(statement.getDocument_Number(), font));
//                    table.addCell(new Phrase(statement.getPeference_Name(), font));
//                    table.addCell(new Phrase(statement.getShare_Dr(), font));
//                    table.addCell(new Phrase(statement.getShare_Cr(), font));
//                    table.addCell(new Phrase(statement.getRunningBalance(), font));
//                    table.addCell(new Phrase(statement.getShare_Dr(), font));
//                    table.addCell(new Phrase(statement.getShare_Dr(), font));
//                    table.addCell(new Phrase(statement.getShare_Bal(), font));
//                }
//
//                document.add(table);
////                       document.add(space);
//
//                //document.add(linebreak);
//                //document.add(space);
//
//            }
            //Section  2
            //add table
            document.add(space);



            for (LoanTransactions loanTransactions:memberLoans) {
                PdfPTable tableLoans = createTable();



                document.add(new Paragraph("Loan:" + loanTransactions.getLoansInService().getLoanSerialNumber() + " " + loanTransactions.getLoanTypes().getLoanTypeName(), dark));
                document.add(space);

                AtomicReference<Double> loanTotals= new AtomicReference<>(0.0);
                AtomicReference<Double> interestTotals= new AtomicReference<>(0.0);
                loanTransactions.getMemberStatements().stream().forEach(statement -> {
                    tableLoans.addCell(new Phrase(statement.getDocument_Date(), font));
                    tableLoans.addCell(new Phrase(statement.getActivityRef(), font));
                    tableLoans.addCell(new Phrase(statement.getDocument_Number(), font));
                    tableLoans.addCell(new Phrase(statement.getPeference_Name(), font));
                    tableLoans.addCell(new Phrase(statement.getLoan_Dr(), font));
                    tableLoans.addCell(new Phrase(statement.getLoan_Cr(), font));
                    tableLoans.addCell(new Phrase(statement.getRunningBalance(), font));
                    tableLoans.addCell(new Phrase(statement.getInterest_Dr(), font));
                    tableLoans.addCell(new Phrase(statement.getInterest_Cr(), font));
                    tableLoans.addCell(new Phrase(statement.getInterest_Bal(), font));
                    loanTotals.updateAndGet(v -> new Double((double) (v + Double.parseDouble(statement.getRunningBalance()))));
                    interestTotals.updateAndGet(v -> new Double((double) (v + Double.parseDouble(statement.getInterest_Bal()))));
                });

                tableLoans.addCell(new Phrase("Totals:", bold));
                tableLoans.addCell(new Phrase("", font));
                tableLoans.addCell(new Phrase("", font));
                tableLoans.addCell(new Phrase("", font));
                tableLoans.addCell(new Phrase("", font));
                tableLoans.addCell(new Phrase("", font));
                tableLoans.addCell(new Phrase(String.valueOf(loanTotals),bold));
                tableLoans.addCell(new Phrase("", font));
                tableLoans.addCell(new Phrase("", font));
                tableLoans.addCell(new Phrase(String.valueOf(interestTotals), bold));
                document.add(tableLoans);


            }



            document.close();
            System.out.println("finished");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    public static  PdfPTable createTable(){
        PdfPTable table = new PdfPTable(10);//three columns

        PdfPCell c1 = new PdfPCell(new Phrase("Date", font));
        c1.setColspan(1);
        c1.setBackgroundColor(new BaseColor(104, 236, 234));
        c1.setFixedHeight((float) 0.1);
        table.addCell(c1);
        PdfPCell c11 = new PdfPCell(new Phrase("Activity", font));
        c11.setBackgroundColor(new BaseColor(78, 89, 0));
        c11.setColspan(1);
        table.addCell(c11);
        PdfPCell c12 = new PdfPCell(new Phrase("DocumentNo", font));
        c12.setBackgroundColor(new BaseColor(78, 89, 0));
        c12.setColspan(1);
        table.addCell(c12);

        PdfPCell c14 = new PdfPCell(new Phrase("Description", font));
        c14.setBackgroundColor(new BaseColor(78, 89, 0));
        c14.setColspan(1);
        table.addCell(c14);
        PdfPCell c13 = new PdfPCell(new Phrase("PaidIn", font));
        c13.setBackgroundColor(new BaseColor(78, 89, 0));
        c13.setColspan(1);
        table.addCell(c13);
        PdfPCell c15 = new PdfPCell(new Phrase("PaidOut", font));
        c15.setBackgroundColor(new BaseColor(78, 89, 0));
        c15.setColspan(1);
        table.addCell(c15);
        PdfPCell c16 = new PdfPCell(new Phrase("Running Bal", font));
        c16.setBackgroundColor(new BaseColor(78, 89, 0));
        c16.setColspan(1);
        table.addCell(c16);
        PdfPCell c17 = new PdfPCell(new Phrase("PaidOut", font));
        c17.setBackgroundColor(new BaseColor(78, 89, 0));
        c17.setColspan(1);
        table.addCell(c17);
        PdfPCell c18 = new PdfPCell(new Phrase("PaidOut", font));
        c18.setBackgroundColor(new BaseColor(78, 89, 0));
        c18.setColspan(1);
        table.addCell(c18);
        PdfPCell c19 = new PdfPCell(new Phrase("Running Bal", font));
        c19.setBackgroundColor(new BaseColor(78, 89, 0));
        c19.setColspan(1);
        table.addCell(c19);

        table.setHeaderRows(1);
        table.setWidthPercentage(102);

        return table;
    }


}
