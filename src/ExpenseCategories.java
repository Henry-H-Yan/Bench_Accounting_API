
public class ExpenseCategories {
	
	public String _Cat;      // holds name  - string
	public float _expense; // holds value - float
	
	
   @Override
	    public int hashCode() {
	      return (int) (_Cat.hashCode() * 17 + _expense * 71);
	      
	    }
	  public boolean equals(Object obj) {
	       if (!(obj instanceof ExpenseCategories))
	            return false;
	        if (obj == this)
	            return true;

	        ExpenseCategories other = (ExpenseCategories) obj;
	    return this._Cat.equals(other._Cat)  && this._expense==other._expense;
	    }
		  

	
	public String get_Cat() {
		return _Cat;
	}
	public void set_Cat(String _Cat) {
		this._Cat = _Cat;
	}
	public float get_expense() {
		return _expense;
	}
	public void set_expense(float _expense) {
		this._expense = _expense;
	}

}
