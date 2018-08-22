package org.baeldung.springvault;

import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;

/**
 * Hello world!
 *
 */
public class SpringVaultApplication {
    public static void main(String[] args) {
        VaultTemplate vaultTemplate = new VaultTemplate(new VaultEndpoint(), new TokenAuthentication("00000000-0000-0000-0000-000000000000"));

        Credential secrets = new Credential("username", "password");

        vaultTemplate.write("secret/myapp", secrets);

        VaultResponseSupport<Credential> response = vaultTemplate.read("secret/myapp", Credential.class);
        String username = response.getData()
            .getPassword();

        vaultTemplate.delete("secret/myapp");
    }
}
