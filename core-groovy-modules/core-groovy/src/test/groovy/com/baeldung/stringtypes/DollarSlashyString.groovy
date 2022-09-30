package groovy.com.baeldung.stringtypes

import org.junit.Test

class DollarSlashyString {

    @Test
    void 'dollar slashy string'() {
        def name = "John"

        def dollarSlashy = $/
            Hello $name!,

            I can show you $ sign or escaped dollar sign: $$ 
            Both slashes works: \ or /, but we can still escape it: $/
            
            We have to escape opening and closing delimiter:
            - $$$/  
            - $/$$
        /$

        print(dollarSlashy)
    }
}
