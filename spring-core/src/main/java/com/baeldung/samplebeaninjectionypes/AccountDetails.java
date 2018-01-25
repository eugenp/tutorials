package com.baeldung.samplebeaninjectionypes;

public class AccountDetails {

        private Long accountNumber;

        private String accountType;

        private String accountName;

        public AccountDetails(Long accountNumber, String accountType, String accountName) {
                this.accountNumber = accountNumber;
                this.accountType = accountType;
                this.accountName = accountName;
        }

        public Long getAccountNumber() {
                return accountNumber;
        }

        public void setAccountNumber(Long accountNumber) {
                this.accountNumber = accountNumber;
        }

        public String getAccountType() {
                return accountType;
        }

        public void setAccountType(String accountType) {
                this.accountType = accountType;
        }

        public String getAccountName() {
                return accountName;
        }

        public void setAccountName(String accountName) {
                this.accountName = accountName;
        }
}
