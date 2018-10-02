package findItems;

import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

public class FindItemsBasedOnValues {

    static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) throws ParseException {
        FindItemsBasedOnValues findItems = new FindItemsBasedOnValues();
        findItems.findItemsImpl();
    }

    @Test
    public void findItemsImpl() throws ParseException {
        List<Client> expectedList = new ArrayList<Client>();
        Client expectedClient = new Client(1001, DATE_FORMAT.parse("01-02-2018"));
        expectedList.add(expectedClient);

        List<Company> listOfCompanies = new ArrayList<Company>();
        List<Client> listOfClients = new ArrayList<Client>();
        populate(listOfCompanies, listOfClients);

        List<Client> filteredList = listOfClients.stream()
            .filter(client -> listOfCompanies.stream()
                .anyMatch(company -> company.getType()
                    .equals("eMart") && company.getProjectId()
                    .equals(client.getProjectId()) && company.getStart()
                    .after(client.getDate()) && company.getEnd()
                    .before(client.getDate())))
            .collect(Collectors.toList());

        assertEquals(expectedClient.getProjectId(), filteredList.get(0)
            .getProjectId());
        assertEquals(expectedClient.getDate(), filteredList.get(0)
            .getDate());
    }

    private void populate(List<Company> companyList, List<Client> clientList) throws ParseException {
        Company company1 = new Company(1001, DATE_FORMAT.parse("01-03-2018"), DATE_FORMAT.parse("01-01-2018"), "eMart");
        Company company2 = new Company(1002, DATE_FORMAT.parse("01-02-2018"), DATE_FORMAT.parse("01-04-2018"), "commerce");
        Company company3 = new Company(1003, DATE_FORMAT.parse("01-06-2018"), DATE_FORMAT.parse("01-02-2018"), "eMart");
        Company company4 = new Company(1004, DATE_FORMAT.parse("01-03-2018"), DATE_FORMAT.parse("01-06-2018"), "blog");

        Collections.addAll(companyList, company1, company2, company3, company4);

        Client client1 = new Client(1001, DATE_FORMAT.parse("01-02-2018"));
        Client client2 = new Client(1003, DATE_FORMAT.parse("01-04-2018"));
        Client client3 = new Client(1005, DATE_FORMAT.parse("01-07-2018"));
        Client client4 = new Client(1007, DATE_FORMAT.parse("01-08-2018"));

        Collections.addAll(clientList, client1, client2, client3, client4);
    }
}

class Company {
    Long projectId;
    Date start;
    Date end;
    String type;

    public Company(long projectId, Date start, Date end, String type) {
        super();
        this.projectId = projectId;
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public String getType() {
        return type;
    }

}

class Client {
    Long projectId;
    Date date;

    public Client(long projectId, Date date) {
        super();
        this.projectId = projectId;
        this.date = date;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Date getDate() {
        return date;
    }

}