package statements;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Connector.DBConnection;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import pojos.*;


public class Utils {

	static DBConnection obJBDCConnection =new DBConnection();
	
	public static Member fetchMember(String payrollNo) throws SQLException {
		try {
			Connection connMain=obJBDCConnection.getConnection("main");
			
			  Member member = new Member();
			 PreparedStatement ps=null;
	           ResultSet rs=null;

	           String query="select * from membersmasterfile where PayrollNo = "+payrollNo;
	           ps=connMain.prepareStatement(query);
	           rs=ps.executeQuery();
	           while (rs.next()){
	               
	        	 
	        	   member.setIdNo(rs.getString("IDNumber"));
	        	   member.setMemberNo(rs.getString("MemberNo"));
	        	   member.setPayrollNo(rs.getString("PayrollNo"));
	        	   member.setPhoneNo(rs.getString("PhoneNo"));
	        	   member.setSurname(rs.getString("Surname"));
	        	   member.setOtherNames(rs.getString("OtherNames"));
	        	   
	        	   
	           }
	           rs.close();
	           ps.close();
	           connMain.close();
	  
			
			System.out.println(member);

	        
			return member;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	


	public  static  List<Scheme> fetchSchemes(){

		    Connection mainConn = null;
			try {
				mainConn = obJBDCConnection.getConnection("main");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	        try{

	            java.sql.Statement statement = mainConn.createStatement();
	            // Result set get the result of the SQL query
	            ResultSet resultSet = statement
	                    .executeQuery("select * from savingschemes");

	            List<Scheme> schemes = new ArrayList<>();
	            while (resultSet.next()) {
	                Scheme scheme = new Scheme();
	                scheme.setSchemeCode(resultSet.getString("SchemeCode"));
	                scheme.setSchemeName(resultSet.getString("SchemeName"));
	                schemes.add(scheme);
	            }
	            resultSet.close();
	            statement.close();
	            return   schemes ;

	        }catch (Exception e){
	            e.printStackTrace();
	            return  new ArrayList<>();
	        }

	    }


	public static  List<LoansInService> fetchMemberLoanInService(Member member){
		Connection mainConn = null;
		try {
			mainConn = obJBDCConnection.getConnection("main");

			java.sql.Statement statement = mainConn.createStatement();
			// Result set get the result of the SQL query
			ResultSet resultSet = statement
					.executeQuery("select * from loansinservice where MemberNumber="+member.getMemberNo());

			List<LoansInService> loansInServices = new ArrayList<>();
			while (resultSet.next()) {

				LoansInService loansInService = new LoansInService();
				loansInService.setLoanSerialNumber(resultSet.getString("LoanSerialNumber"));
				loansInService.setLoanType(resultSet.getString("LoanType"));
				loansInService.setMemberNumber(resultSet.getString("MemberNumber"));
				loansInServices.add(loansInService);
			}
			resultSet.close();
			statement.close();
			return   loansInServices ;

		}catch (Exception e){
			e.printStackTrace();
			return  new ArrayList<>();
		}
	}


	
	public static  List<LoanTransactions> fetchMemberLoans(Member member ,String dbPrefix){

		try {
			Connection connectionbosa000010=obJBDCConnection.getConnection("bosa0000"+dbPrefix);

		   List<LoansInService> loansInServices = fetchMemberLoanInService(member);

		   List<LoanTransactions> loanTransactions = new ArrayList<>();
			for (LoansInService loansInService:loansInServices) {

				System.out.println(loansInService);
				List<MemberStatement> loansStatements = fetchActualLoan(connectionbosa000010, loansInService, member);
				LoanTransactions loanTransaction = new LoanTransactions();
				loanTransaction.setLoansInService(loansInService);
				loanTransaction.setMemberStatements(loansStatements);
				loanTransactions.add(loanTransaction);
			}

			return  loanTransactions;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return  new ArrayList<>();
		}





         
         //Another List
	}


	public static List<MemberStatement> fetchActualLoan(Connection connectionbosa000010, LoansInService loansInService, Member member){
		try {

			PreparedStatement ps=null;
			ResultSet rs=null;

			String query="SELECT * FROM ( SELECT * FROM BOSA000010.MEM_STATEMENT01 WHERE Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"' AND Reference_Code='"+loansInService.getLoanSerialNumber()+"' AND " +
					"(Loan_Dr <> 0 OR Loan_Cr <> 0 OR ActivityRef = 'B/F'  OR Interest_Dr <> 0 OR Interest_Cr <> 0   )" +
					" UNION ALL  SELECT * FROM BOSA000010.MEM_STATEMENT02 WHERE Employer_Code='001' AND " +
					"Payroll_No='"+member.getPayrollNo()+"' AND Reference_Code='"+loansInService.getLoanSerialNumber()+"' AND (Loan_Dr <> 0 OR Loan_Cr <> 0 " +
					"OR ActivityRef = 'B/F'  OR Interest_Dr <> 0 OR Interest_Cr <> 0   ) " +
					"UNION ALL  SELECT * FROM BOSA000010.MEM_STATEMENT03 WHERE Employer_Code='001' AND " +
					"Payroll_No='"+member.getPayrollNo()+"' AND Reference_Code='"+loansInService.getLoanSerialNumber()+"' AND (Loan_Dr <> 0 OR Loan_Cr <> 0 OR" +
					" ActivityRef = 'B/F'  OR Interest_Dr <> 0 OR Interest_Cr <> 0   ) " +
					"UNION ALL  SELECT * FROM BOSA000010.MEM_STATEMENT04 WHERE Employer_Code='001' AND " +
					"Payroll_No='"+member.getPayrollNo()+"' AND Reference_Code='"+loansInService.getLoanSerialNumber()+"' AND (Loan_Dr <> 0 OR Loan_Cr <> 0 OR " +
					"ActivityRef = 'B/F'  OR Interest_Dr <> 0 OR Interest_Cr <> 0   ) " +
					"UNION ALL  SELECT * FROM BOSA000010.MEM_STATEMENT05 WHERE Employer_Code='001' AND " +
					"Payroll_No='"+member.getPayrollNo()+"' AND Reference_Code='"+loansInService.getLoanSerialNumber()+"' AND (Loan_Dr <> 0 OR Loan_Cr <> 0 OR" +
					" ActivityRef = 'B/F'  OR Interest_Dr <> 0 OR Interest_Cr <> 0   ) UNION ALL  SELECT * FROM " +
					"BOSA000010.MEM_STATEMENT06 WHERE Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"' AND " +
					"Reference_Code='"+loansInService.getLoanSerialNumber()+"' AND (Loan_Dr <> 0 OR Loan_Cr <> 0 OR ActivityRef = 'B/F'  OR " +
					"Interest_Dr <> 0 OR Interest_Cr <> 0   ) UNION ALL  SELECT * FROM BOSA000010.MEM_STATEMENT07 " +
					"WHERE Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"' AND Reference_Code='"+loansInService.getLoanSerialNumber()+"' AND" +
					" (Loan_Dr <> 0 OR Loan_Cr <> 0 OR ActivityRef = 'B/F'  OR Interest_Dr <> 0 OR Interest_Cr <> 0) " +
					"UNION ALL  SELECT * FROM BOSA000010.MEM_STATEMENT08 WHERE Employer_Code='001' " +
					"AND Payroll_No='"+member.getPayrollNo()+"' AND Reference_Code='"+loansInService.getLoanSerialNumber()+"' AND (Loan_Dr <> 0 OR Loan_Cr <> 0" +
					" OR ActivityRef = 'B/F'  OR Interest_Dr <> 0 OR Interest_Cr <> 0   ) " +
					"UNION ALL  SELECT * FROM BOSA000010.MEM_STATEMENT09 WHERE Employer_Code='001' AND " +
					"Payroll_No='"+member.getPayrollNo()+"' AND Reference_Code='"+loansInService.getLoanSerialNumber()+"' AND (Loan_Dr <> 0 OR Loan_Cr <> 0 OR " +
					"ActivityRef = 'B/F'  OR Interest_Dr <> 0 OR Interest_Cr <> 0   ) UNION ALL  SELECT * FROM " +
					"BOSA000010.MEM_STATEMENT10 WHERE Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"' AND " +
					"Reference_Code='"+loansInService.getLoanSerialNumber()+"' AND (Loan_Dr <> 0 OR Loan_Cr <> 0 OR ActivityRef = 'B/F'  " +
					"OR Interest_Dr <> 0 OR Interest_Cr <> 0   ) UNION ALL  SELECT * FROM BOSA000010.MEM_STATEMENT11 " +
					"WHERE Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"' AND Reference_Code='"+loansInService.getLoanSerialNumber()+"' " +
					"AND (Loan_Dr <> 0 OR Loan_Cr <> 0 OR ActivityRef = 'B/F'  OR Interest_Dr <> 0 OR " +
					"Interest_Cr <> 0   ) UNION ALL  SELECT * FROM BOSA000010.MEM_STATEMENT12 WHERE " +
					"Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"' AND Reference_Code='"+loansInService.getLoanSerialNumber()+"' AND " +
					"(Loan_Dr<>0 OR Loan_Cr<>0 OR ActivityRef = 'B/F'  OR (Interest_Dr <> 0 OR Interest_Cr <> 0) ))" +
					"A ORDER BY Document_Date;";
			ps=connectionbosa000010.prepareStatement(query);

			rs=ps.executeQuery();
			List<MemberStatement>   loans = new ArrayList<>();

			double LoanBalance=0.0;
			double interestbal=0.0;

			while (rs.next()){
				MemberStatement memberStatement = new MemberStatement();
				memberStatement.setDocument_Date(rs.getString("Document_Date"));
				memberStatement.setActivityRef(rs.getString("ActivityRef"));
				memberStatement.setDocument_Number(rs.getString("Document_Number"));
				memberStatement.setPeference_Name(rs.getString("reference_name"));
				//memberStatement.setDocument_Date(resultSet.getString("Document_Date"));
				//memberStatement.setShare_Bal(rs.getString("Share_Bal"));

				memberStatement.setLoan_Cr(rs.getString("Loan_Cr"));
				memberStatement.setLoan_Dr(rs.getString("Loan_Dr"));
				memberStatement.setInterest_Cr(rs.getString("Interest_Cr"));
				memberStatement.setInterest_Dr(rs.getString("Interest_Dr"));


				double loan_Dr =Double.parseDouble(rs.getString("Loan_Dr"));
				double  loan_Cr= Double.parseDouble(rs.getString("Loan_Cr"));

				LoanBalance+=(loan_Dr+loan_Cr);

				memberStatement.setRunningBalance(String.valueOf(LoanBalance));


				double Interest_Cr= Double.parseDouble(rs.getString("Interest_Dr"));
				double Interest_Dr=Double.parseDouble(rs.getString("Interest_Cr"));

                 interestbal +=(Interest_Dr+Interest_Cr);
                 memberStatement.setInterest_Bal(String.valueOf(interestbal));

				loans.add(memberStatement);

			}

			rs.close();
			ps.close();
			return loans;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<MemberStatement>();
		}
	}




	public  static  List<MemberStatement> fetchMemberStatement(String schemeCode, Member member) throws ClassNotFoundException {
		Connection bosa00001Conn =obJBDCConnection.getConnection("bosa000010");
		List<MemberStatement> memberStatements = new ArrayList<>();
		try{
			Statement statement = bosa00001Conn.createStatement();
			// Result set get the result of the SQL query
			ResultSet resultSet = statement.executeQuery(
                              "SELECT * FROM (  SELECT SysCode,Employer_Code,Payroll_No,Document_Number,Document_Date,ActivityRef,Reference_Name,Period,Share_Dr,Share_Cr,Scheme,0 AS Share,0 AS Deposit,SS.SchemeName,IF(TblAdv.Advice IS NULL OR TblAdv.Advice=0,SS.Contribution,TblAdv.Advice) AS Contr  FROM bosa000010.MEM_STATEMENT01 AS A01 LEFT JOIN Main.SavingSchemes AS SS ON A01.Scheme=SS.SchemeCode LEFT JOIN Main.SharesAdvise AS TblAdv ON A01.Employer_Code=TblAdv.EmployerNo AND A01.Payroll_no=TblAdv.PayrollNo AND A01.Scheme=TblAdv.SchemeCode WHERE (Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"') AND (A01.Share_Dr <> 0 or A01.Share_Cr <> 0)  AND SS.IsWelfare = 'N'  AND A01.Scheme IN ('"+schemeCode+"')  UNION ALL  SELECT SysCode,Employer_Code,Payroll_No,Document_Number,Document_Date,ActivityRef,Reference_Name,Period,Share_Dr,Share_Cr,Scheme,0 AS Share,0 AS Deposit,SS.SchemeName,IF(TblAdv.Advice IS NULL OR TblAdv.Advice=0,SS.Contribution,TblAdv.Advice) AS Contr FROM bosa000010.MEM_STATEMENT02 AS A02 LEFT JOIN Main.SavingSchemes AS SS ON A02.Scheme=SS.SchemeCode LEFT JOIN Main.SharesAdvise AS TblAdv ON A02.Employer_Code=TblAdv.EmployerNo AND A02.Payroll_no=TblAdv.PayrollNo AND A02.Scheme=TblAdv.SchemeCode WHERE (Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"') AND (A02.Share_Dr <> 0 or A02.Share_Cr <> 0)  AND SS.IsWelfare = 'N'  AND A02.Scheme IN ('"+schemeCode+"')  UNION ALL  SELECT SysCode,Employer_Code,Payroll_No,Document_Number,Document_Date,ActivityRef,Reference_Name,Period,Share_Dr,Share_Cr,Scheme,0 AS Share,0 AS Deposit,SS.SchemeName,IF(TblAdv.Advice IS NULL OR TblAdv.Advice=0,SS.Contribution,TblAdv.Advice) AS Contr FROM bosa000010.MEM_STATEMENT03 AS A03 LEFT JOIN Main.SavingSchemes AS SS ON A03.Scheme=SS.SchemeCode LEFT JOIN Main.SharesAdvise AS TblAdv ON A03.Employer_Code=TblAdv.EmployerNo AND A03.Payroll_no=TblAdv.PayrollNo AND A03.Scheme=TblAdv.SchemeCode WHERE (Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"') AND (A03.Share_Dr <> 0 or A03.Share_Cr <> 0)  AND SS.IsWelfare = 'N'  AND A03.Scheme IN ('"+schemeCode+"')  UNION ALL  SELECT SysCode,Employer_Code,Payroll_No,Document_Number,Document_Date,ActivityRef,Reference_Name,Period,Share_Dr,Share_Cr,Scheme,0 AS Share,0 AS Deposit,SS.SchemeName,IF(TblAdv.Advice IS NULL OR TblAdv.Advice=0,SS.Contribution,TblAdv.Advice) AS Contr FROM bosa000010.MEM_STATEMENT04 AS A04 LEFT JOIN Main.SavingSchemes AS SS ON A04.Scheme=SS.SchemeCode LEFT JOIN Main.SharesAdvise AS TblAdv ON A04.Employer_Code=TblAdv.EmployerNo AND A04.Payroll_no=TblAdv.PayrollNo AND A04.Scheme=TblAdv.SchemeCode WHERE (Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"') AND (A04.Share_Dr <> 0 or A04.Share_Cr <> 0)  AND SS.IsWelfare = 'N'  AND A04.Scheme IN ('"+schemeCode+"')  UNION ALL  SELECT SysCode,Employer_Code,Payroll_No,Document_Number,Document_Date,ActivityRef,Reference_Name,Period,Share_Dr,Share_Cr,Scheme,0 AS Share,0 AS Deposit,SS.SchemeName,IF(TblAdv.Advice IS NULL OR TblAdv.Advice=0,SS.Contribution,TblAdv.Advice) AS Contr FROM bosa000010.MEM_STATEMENT05 AS A05 LEFT JOIN Main.SavingSchemes AS SS ON A05.Scheme=SS.SchemeCode LEFT JOIN Main.SharesAdvise AS TblAdv ON A05.Employer_Code=TblAdv.EmployerNo AND A05.Payroll_no=TblAdv.PayrollNo AND A05.Scheme=TblAdv.SchemeCode WHERE (Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"') AND (A05.Share_Dr <> 0 or A05.Share_Cr <> 0)  AND SS.IsWelfare = 'N'  AND A05.Scheme IN ('"+schemeCode+"')  UNION ALL  SELECT SysCode,Employer_Code,Payroll_No,Document_Number,Document_Date,ActivityRef,Reference_Name,Period,Share_Dr,Share_Cr,Scheme,0 AS Share,0 AS Deposit,SS.SchemeName,IF(TblAdv.Advice IS NULL OR TblAdv.Advice=0,SS.Contribution,TblAdv.Advice) AS Contr FROM bosa000010.MEM_STATEMENT06 AS A06 LEFT JOIN Main.SavingSchemes AS SS ON A06.Scheme=SS.SchemeCode LEFT JOIN Main.SharesAdvise AS TblAdv ON A06.Employer_Code=TblAdv.EmployerNo AND A06.Payroll_no=TblAdv.PayrollNo AND A06.Scheme=TblAdv.SchemeCode WHERE (Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"') AND (A06.Share_Dr <> 0 or A06.Share_Cr <> 0)  AND SS.IsWelfare = 'N'  AND A06.Scheme IN ('"+schemeCode+"')  UNION ALL  SELECT SysCode,Employer_Code,Payroll_No,Document_Number,Document_Date,ActivityRef,Reference_Name,Period,Share_Dr,Share_Cr,Scheme,0 AS Share,0 AS Deposit,SS.SchemeName,IF(TblAdv.Advice IS NULL OR TblAdv.Advice=0,SS.Contribution,TblAdv.Advice) AS Contr FROM bosa000010.MEM_STATEMENT07 AS A07 LEFT JOIN Main.SavingSchemes AS SS ON A07.Scheme=SS.SchemeCode LEFT JOIN Main.SharesAdvise AS TblAdv ON A07.Employer_Code=TblAdv.EmployerNo AND A07.Payroll_no=TblAdv.PayrollNo AND A07.Scheme=TblAdv.SchemeCode WHERE (Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"') AND (A07.Share_Dr <> 0 or A07.Share_Cr <> 0)  AND SS.IsWelfare = 'N'  AND A07.Scheme IN ('"+schemeCode+"')  UNION ALL  SELECT SysCode,Employer_Code,Payroll_No,Document_Number,Document_Date,ActivityRef,Reference_Name,Period,Share_Dr,Share_Cr,Scheme,0 AS Share,0 AS Deposit,SS.SchemeName,IF(TblAdv.Advice IS NULL OR TblAdv.Advice=0,SS.Contribution,TblAdv.Advice) AS Contr FROM bosa000010.MEM_STATEMENT08 AS A08 LEFT JOIN Main.SavingSchemes AS SS ON A08.Scheme=SS.SchemeCode LEFT JOIN Main.SharesAdvise AS TblAdv ON A08.Employer_Code=TblAdv.EmployerNo AND A08.Payroll_no=TblAdv.PayrollNo AND A08.Scheme=TblAdv.SchemeCode WHERE (Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"') AND (A08.Share_Dr <> 0 or A08.Share_Cr <> 0)  AND SS.IsWelfare = 'N'  AND A08.Scheme IN ('"+schemeCode+"')  UNION ALL  SELECT SysCode,Employer_Code,Payroll_No,Document_Number,Document_Date,ActivityRef,Reference_Name,Period,Share_Dr,Share_Cr,Scheme,0 AS Share,0 AS Deposit,SS.SchemeName,IF(TblAdv.Advice IS NULL OR TblAdv.Advice=0,SS.Contribution,TblAdv.Advice) AS Contr FROM bosa000010.MEM_STATEMENT09 AS A09 LEFT JOIN Main.SavingSchemes AS SS ON A09.Scheme=SS.SchemeCode LEFT JOIN Main.SharesAdvise AS TblAdv ON A09.Employer_Code=TblAdv.EmployerNo AND A09.Payroll_no=TblAdv.PayrollNo AND A09.Scheme=TblAdv.SchemeCode WHERE (Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"') AND (A09.Share_Dr <> 0 or A09.Share_Cr <> 0)  AND SS.IsWelfare = 'N'  AND A09.Scheme IN ('"+schemeCode+"')  UNION ALL  SELECT SysCode,Employer_Code,Payroll_No,Document_Number,Document_Date,ActivityRef,Reference_Name,Period,Share_Dr,Share_Cr,Scheme,0 AS Share,0 AS Deposit,SS.SchemeName,IF(TblAdv.Advice IS NULL OR TblAdv.Advice=0,SS.Contribution,TblAdv.Advice) AS Contr FROM bosa000010.MEM_STATEMENT10 AS A10 LEFT JOIN Main.SavingSchemes AS SS ON A10.Scheme=SS.SchemeCode LEFT JOIN Main.SharesAdvise AS TblAdv ON A10.Employer_Code=TblAdv.EmployerNo AND A10.Payroll_no=TblAdv.PayrollNo AND A10.Scheme=TblAdv.SchemeCode WHERE (Employer_Code='001' AND Payroll_No='"+member.getPayrollNo()+"') AND (A10.Share_Dr <> 0 or A10.Share_Cr <> 0)  AND SS.IsWelfare = 'N'  AND A10.Scheme IN ('"+schemeCode+"')) AS tblx  ORDER BY Document_Date,SysCode ASC;"


					);
//					.executeQuery("select `mem_statement01`.`Document_Date`,`mem_statement01`.`ActivityRef`, `mem_statement01`.`Document_Number`,`mem_statement01`.`reference_name`,`mem_statement01`.`Share_Bal`,`mem_statement01`.`Share_Cr`,`mem_statement01`.`Share_Dr` From mem_statement01  where Scheme="+schemeCode+" AND Payroll_No="+payrollNo+" UNION select `mem_statement03`.`Document_Date`,`mem_statement03`.`ActivityRef`,`mem_statement03`.`Document_Number`,`mem_statement03`.`reference_name`,`mem_statement03`.`Share_Bal`,`mem_statement03`.`Share_Cr`,`mem_statement03`.`Share_Dr` From mem_statement03  where Scheme="+schemeCode+" AND Payroll_No="+payrollNo+" UNION select `mem_statement02`.`Document_Date`,`mem_statement02`.`ActivityRef`,`mem_statement02`.`Document_Number`,`mem_statement02`.`reference_name`,`mem_statement02`.`Share_Bal`,`mem_statement02`.`Share_Cr`,`mem_statement02`.`Share_Dr` From mem_statement02  where Scheme="+schemeCode+" AND Payroll_No="+payrollNo+" UNION select `mem_statement04`.`Document_Date`,`mem_statement04`.`ActivityRef`,`mem_statement04`.`Document_Number`,`mem_statement04`.`reference_name`,`mem_statement04`.`Share_Bal`,`mem_statement04`.`Share_Cr`,`mem_statement04`.`Share_Dr` From mem_statement04  where Scheme="+schemeCode+" AND Payroll_No="+payrollNo+" UNION select `mem_statement05`.`Document_Date`,`mem_statement05`.`ActivityRef`,`mem_statement05`.`Document_Number`,`mem_statement05`.`reference_name`,`mem_statement05`.`Share_Bal`,`mem_statement05`.`Share_Cr`,`mem_statement05`.`Share_Dr` From mem_statement05  where Scheme="+schemeCode+" AND Payroll_No="+payrollNo+" UNION select `mem_statement06`.`Document_Date`,`mem_statement06`.`ActivityRef`,`mem_statement06`.`Document_Number`,`mem_statement06`.`reference_name`,`mem_statement06`.`Share_Bal`,`mem_statement06`.`Share_Cr`,`mem_statement06`.`Share_Dr` From mem_statement06  where Scheme="+schemeCode+" AND Payroll_No="+payrollNo+""
//					);

//			.executeQuery("SELECT `mem_statement01`.`Document_Date`, `mem_statement01`.`reference_name` FROM `mem_statement01` UNION SELECT `mem_statement02`.`Document_Date\n" +
//					"`, `mem_statement02`.`reference_name` FROM `mem_statement02`;");


  double runningBalance=0.0;
   while (resultSet.next()) {
				MemberStatement memberStatement = new MemberStatement();
				memberStatement.setDocument_Date(resultSet.getString("Document_Date"));
				memberStatement.setActivityRef(resultSet.getString("ActivityRef"));
				memberStatement.setDocument_Number(resultSet.getString("Document_Number"));
				memberStatement.setPeference_Name(resultSet.getString("reference_name"));
				//memberStatement.setDocument_Date(resultSet.getString("Document_Date"));
			   // memberStatement.setShare_Bal(resultSet.getString("Share_Bal"));
				memberStatement.setShare_Cr(resultSet.getString("Share_Cr"));
				memberStatement.setShare_Dr(resultSet.getString("Share_Dr"));

//				memberStatement.setLoan_Dr(resultSet.getString("Loan_Dr"));



				double Share_Dr =Double.parseDouble(resultSet.getString("Share_Dr"));
				double  Share_Cr= Double.parseDouble(resultSet.getString("Share_Cr"));

	            runningBalance+=(Share_Dr+Share_Cr);

	           memberStatement.setRunningBalance(String.valueOf(runningBalance));
	         memberStatements.add(memberStatement);
			}
			resultSet.close();
			statement.close();
			return   memberStatements ;

		}catch (Exception e){
			e.printStackTrace();
			return  new ArrayList<>();
		}

	}




}
