
public class Transactions {
	
	String _Date;
	String _Ledger;
	Float _Amount;
	String _Company;
	
	
	// getter setter for all
	
	/**
	 *  Used to 
	 * @param other
	 * @return
	 */
	
	public Transactions(String d, String l, Float a, String c){
		_Date= d;
		_Ledger=l;
		_Amount=a;
		_Company=c;
		
	}
	
	public boolean equals(Transactions other){
		return this._Date.equals(other._Date) &&
				this._Ledger.equals(other._Date) &&
				this._Amount.equals(other._Amount)
				&& this._Company.equals(other._Company);
		
	}
	public String get_Date() {
		return _Date;
	}
	public void set_Date(String _Date) {
		this._Date = _Date;
	}
	public String get_Ledger() {
		return _Ledger;
	}
	public void set_Ledger(String _Ledger) {
		this._Ledger = _Ledger;
	}
	public Float get_Amount() {
		return _Amount;
	}
	public void set_Amount(Float _Amount) {
		this._Amount = _Amount;
	}
	public String get_Company() {
		return _Company;
	}
	public void set_Company(String _Company) {
		this._Company = _Company;
	}
	
}
