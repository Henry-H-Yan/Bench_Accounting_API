import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		try {
			
			System.out.println("henry");
			GlobalDataClass.DeleteFileData();
			// Create a client to read json Data
			Client client = Client.create();
		
			for (int i = 1; i <= GlobalDataClass.MAX_PAGE; i++) {
			
				if (GlobalDataClass
						.exists("http://resttest.bench.co/transactions/" + i
								+ ".json")) {

					/*
					 * Create resource url
					 */
					WebResource webResource = client
							.resource("http://resttest.bench.co/transactions/"
									+ i + ".json");
					/*
					 * Get the response here as string
					 */
					ClientResponse response = webResource.accept(
							"application/json").get(ClientResponse.class);
					/*
					 * If not getting correct response
					 */
					if (response.getStatus() != 200) {
						throw new RuntimeException(
								"Failed : HTTP error code : "
										+ response.getStatus());
					}
					/*
					 * Convert the response as string
					 */
					String output = response.getEntity(String.class);
					/*
					 * Json Object create with output
					 */
					JSONObject obj = new JSONObject(output);
					/*
					 * Parse the transaction tag in json And Get details data to
					 * variable
					 */

					JSONArray arr = obj.getJSONArray("transactions");
					for (int j = 0; j < arr.length(); j++) {
						String _Date = arr.getJSONObject(j).getString("Date");
						String _Ledger = arr.getJSONObject(j).getString(
								"Ledger");
						String _Amount = arr.getJSONObject(j).getString(
								"Amount");
						String _Company = arr.getJSONObject(j).getString(
								"Company");
						/*
						 * If the category or Ledger is null then It will show
						 * as payment Category
						 */
						if (_Ledger.equals("")) {
							_Ledger = "Payment_Category";
						}
						/*
						 * Insert total data into a transaction Arraylist
						 */
						GlobalDataClass.InsertTransaction(_Date, _Ledger,
								_Amount, _Company);
						/*
						 * Add data to Expense Category List
						 */
						GlobalDataClass.AddExpCat(_Ledger,
								Float.parseFloat(_Amount));
						/*
						 * Add data to Date wise expense category List
						 */
						GlobalDataClass.AddDateCat(_Date,
								Float.parseFloat(_Amount));
					}
				} else {
					// System.out.println("End");
					/*
					 * Break if not exist
					 */
					break;
				}

			}
			GlobalDataClass
					.SaveToFile("######################## Total Balance ##########################");

			GlobalDataClass.CalculateTotalBalance();

			GlobalDataClass.SaveToFile("Total Balance : "
					+ GlobalDataClass.totalBalance);
			GlobalDataClass
					.SaveToFile("_________________________ Vendor Names _________________________");
			/*
			 * Get all vendor name from transaction arraylist
			 */
			for (int i = 0; i < GlobalDataClass._transactions.size(); i++) {
				/*
				 * Replace non- capital character, numeric character
				 */
				String name = GlobalDataClass._transactions.get(i)
						.get_Company().replaceAll("[^A-Z ]", "");
	
				GlobalDataClass.SaveToFile(name);

			}
			GlobalDataClass
					.SaveToFile("_________________________ Expense Category  _________________________");
			for (int i = 0; i < GlobalDataClass._ExpCat.size(); i++) {
				String Cat = GlobalDataClass._ExpCat.get(i).get_Cat();
				float Exp = GlobalDataClass._ExpCat.get(i).get_expense();
				GlobalDataClass.SaveToFile(Cat + " : " + Exp);
			}

			GlobalDataClass
					.SaveToFile("_________________________ Date Wise _________________________");
			for (int i = 0; i < GlobalDataClass._DateCat.size(); i++) {
				String Cat = GlobalDataClass._DateCat.get(i).get_Date();
				float Exp = GlobalDataClass._DateCat.get(i).get_Exp();
				GlobalDataClass.SaveToFile(Cat + " : " + Exp);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}
