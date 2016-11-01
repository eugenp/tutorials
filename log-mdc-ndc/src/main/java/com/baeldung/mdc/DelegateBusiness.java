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

	public boolean transfer(Long amount) {
		preTransfer();
		boolean outcome = delegate.transfer(amount);
		postTransfer(outcome);
		return outcome;
	}

	abstract protected void postTransfer(boolean outcome);

	abstract protected void preTransfer();
	
	
	
}
