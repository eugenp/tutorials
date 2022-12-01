package com.baeldung.instanceofalternative.enumallt;

public enum DinosaurEnum {
	Anatotitan {
		@Override
		public String behaviour() {
			return "Aggressive";
		}
	},
	Euraptor {
		@Override
		public String behaviour() {
			return "Calm";
		}
	};

	public abstract String behaviour();

}
