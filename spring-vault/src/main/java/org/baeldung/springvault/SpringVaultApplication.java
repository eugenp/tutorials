package org.baeldung.springvault;

import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

public class SpringVaultApplication {

    public static void main(String[] args) {

        Credential secrets = new Credential("username", "password");

        // To Write credentials into the Vault
        VaultTemplate vaultTemplate = new VaultTemplate(new VaultEndpoint(), new TokenAuthentication("00000000-0000-0000-0000-000000000000"));
        vaultTemplate.write("secret/myapp", secrets);

        // To Retrieve credentials from the Vault
        VaultResponseSupport<Credential> response = vaultTemplate.read("secret/myapp", Credential.class);
        String username = response.getData()
            .getPassword();
        String password = response.getData()
            .getPassword();

        System.out.println(response.getData());

        // To Delete
        vaultTemplate.delete("secret/myapp");
    }
}
