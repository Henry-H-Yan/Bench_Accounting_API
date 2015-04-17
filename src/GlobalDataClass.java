import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 *  Encapsulate all the info and features required 
 *  
 * 
 * @author henry-h-yan
 *
 */
public class GlobalDataClass {
	public static float totalBalance = 0;
	public static float totalCount = 0;
	public final static int MAX_PAGE = 100; // max number of json pages, may change due to requirements


	public static ArrayList<Transactions> _transactions = new ArrayList<Transactions>();
	public static ArrayList<ExpenseCategories> _ExpCat = new ArrayList<ExpenseCategories>();
	public static ArrayList<DateCat> _DateCat = new ArrayList<DateCat>();

	/*
	 * Insert all data to transaction Arraylist
	 */
	public static void InsertTransaction(String _Date, String _Ledger,
			String _Amount, String _Company) {
		Transactions t = new Transactions( _Date, _Ledger,
				Float.parseFloat(_Amount ) ,_Company);

		if (!CheckDuplicate(t)) {


			GlobalDataClass._transactions.add(t);
			// System.out.println("Data added");
		}
	}

	/*
	 * Check the duplicate data base of identity
	 */
	public static boolean CheckDuplicate(Transactions t) {
		boolean isfound = false;
		for (int i = 0; i < GlobalDataClass._transactions.size(); i++) {
			if (t.equals(GlobalDataClass._transactions.get(i))) {
				return true; // is duplicate
			} 
		}
		return false;
	}

	/**
	 * get the total balance for all data
	 */
	public static void CalculateTotalBalance() {
		totalBalance = 0;
		for (Transactions cur: GlobalDataClass._transactions) {
			GlobalDataClass.totalBalance += cur.get_Amount();
		}
	}

	/**
	 * Check the Category exist or not if exist then Update the expense and if
	 * not exist then Add the Category as new entry
	 */
	public static void AddExpCat(String cat, float exp) {
		boolean exists = false;
		
		// check if expense category exists
		for (int i = 0; i < GlobalDataClass._ExpCat.size(); i++) {
			if (GlobalDataClass._ExpCat.get(i).get_Cat().equalsIgnoreCase(cat)) {
				GlobalDataClass._ExpCat.get(i).set_expense(
						GlobalDataClass._ExpCat.get(i).get_expense() + exp);
				exists = true;
			}
		}
		
		// if need to update: 
		if (!exists) {
			ExpenseCategories exp_cat = new ExpenseCategories();
			exp_cat.set_Cat(cat);
			exp_cat.set_expense(exp);
			GlobalDataClass._ExpCat.add(exp_cat);
		}

	}

	/*
	 * Check the Date exist or not if exist then Update the expense and if not
	 * exist then Add the Date as new entry
	 */
	public static void AddDateCat(String date, float exp) {
		boolean exists = false;
		for (int i = 0; i < GlobalDataClass._DateCat.size(); i++) {
			if (GlobalDataClass._DateCat.get(i).get_Date()
					.equalsIgnoreCase(date)) {
				exists = true;
				GlobalDataClass._DateCat.get(i).set_Exp(
						GlobalDataClass._DateCat.get(i).get_Exp() + exp);
			}
		}
		// need to add new 
		if (!exists) {
			DateCat date_cat = new DateCat();
			date_cat.set_Date(date);
			date_cat.set_Exp(exp);
			GlobalDataClass._DateCat.add(date_cat);
		}

	}

	/*
	 * Save the data fo file an d show to console
	 */
	public static void SaveToFile(String str) {
		// Create file and save and in appened mode
		FileWriter fw = null;
		try {
			fw = new FileWriter("data.txt", true);
			fw.write(str + "\r\n");
			fw.close();
			fw = null;
			System.out.println(str);
		} catch (Exception ioe) {

		}

	}

	public static void DeleteFileData() {

		try {
			PrintWriter writer = new PrintWriter("mydata.txt", "UTF-8");
			writer.close();
		} catch (Exception ioe) {

		}

	}

	/*
	 * Method for detecting if a page is valid 
	 */
	public static boolean exists(String URLName) {
		try {
			HttpURLConnection.setFollowRedirects(false);
		
			HttpURLConnection con = (HttpURLConnection) new URL(URLName)
			.openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	

}
