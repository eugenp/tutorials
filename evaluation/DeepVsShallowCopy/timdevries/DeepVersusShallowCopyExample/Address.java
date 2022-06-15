package DeepVersusShallowCopyExample;
/**
 * 
 * @author Tim de Vries
 */
public class Address {
    String addr1 = "";
    String addr2 = "";
    String municipality = "";
    String provState = "";
    String country = "";
    String zipPostal = "";
    
    public Address() {
    }
    
    public Address(String addr1, String addr2, String municipality, String provState,String country, String zipPostal) {
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.municipality = municipality;
        this.provState = provState;
        this.country = country;
        this.zipPostal = zipPostal;
    }
    
    public String getAddr1() { return addr1; }
    
    public String getAddr2() { return addr2; }
    
    public String getMunicipality() { return municipality; }
    
    public String getProvState() { return provState; }
    
    public String getCountry() { return country; }
    
    public String getZipPostal() { return zipPostal; }
    
    public void setAddr1(String addr1) { this.addr1 = addr1; }
    
    public void setAddr2(String addr2) { this.addr2 = addr2; }
    
    public void setMunicipality(String municipality) { this.municipality = municipality; }
    
    public void setProvState(String provState) { this.provState = provState; }
    
    public void setCountry(String country) { this.country = country; }
    
    public void setZipPostal(String zipPostal) { this.zipPostal = zipPostal; }
    
    public String toString() {
        String newLine = "\n";
        String addr = addr1 + newLine;
        addr += addr2 + newLine;
        addr += municipality + newLine;
        addr += provState + newLine;
        addr += country + newLine;
        addr += zipPostal + newLine;
        return addr;       
    }
}
