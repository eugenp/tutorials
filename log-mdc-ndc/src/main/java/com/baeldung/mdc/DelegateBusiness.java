package com.baeldung.mdc;

public abstract class DelegateBusiness {
	
	private BusinessService delegate = null;

	public DelegateBusiness(BusinessService delegate) {
		super();
		this.delegate = delegate;
	}
	
	public DelegateBusiness() {
		this( new DefaultBusinessService() );
	}

	public boolean transfer(long amount) {
		preTransfer(amount);
		boolean outcome = delegate.transfer(amount);
		postTransfer(amount, outcome);
		return outcome;
	}

	abstract protected void postTransfer(long amount, boolean outcome);

	abstract protected void preTransfer(long amount);
	
	
	
}
