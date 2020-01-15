package org.nganga.bank.account.adapter;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.nganga.bank.account.domain.Account;
import org.nganga.bank.account.domain.Customer;
import org.nganga.bank.account.port.CustomerAccountPort;

public class CustomerAccountFileAdapter implements CustomerAccountPort {

	;
	private FileOutputStream f;
	private PrintWriter out;
	private ObjectInputStream in;

	public CustomerAccountFileAdapter() {

		try {
			f = new FileOutputStream(new File("myObjects.txt"), true);
			out = new PrintWriter(f);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createAccount(Customer cust) {

		StringBuilder builder = new StringBuilder();
		builder.append(cust.getAccount().getAccountNumber() + ",");
		builder.append(cust.getAccount().getCustomerId() + ",");
		builder.append(cust.getAccount().isActive() + ",");
		builder.append(cust.getAccount().getDateOpened());
		out.println(builder.toString());
		out.flush();

	}

	public List<Account> getCustomerAccounts(String identity) {
		List<Account> account = new ArrayList<>();

		try {
			BufferedReader buf = new BufferedReader(new FileReader(new File("myObjects.txt")));

			String record = buf.readLine();
			while (record != null) {
				if (record.contains(identity)) {
					String line[] = record.split("\\,");

					Account acc = new Account();
					acc.setAccountNumber(line[0]);
					acc.setActive(line[2].equalsIgnoreCase("true") ? true : false);
					acc.setCustomerId(line[1]);
					acc.setDateOpened(LocalDate.parse(line[3]));
					account.add(acc);
				}

				record = buf.readLine();
			}

			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return account;

	}

}
