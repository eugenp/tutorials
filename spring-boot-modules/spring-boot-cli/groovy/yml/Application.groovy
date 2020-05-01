package bael.yml

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.ComponentScan

@EnableAutoConfiguration
@ComponentScan('bael.yml')
class App {

    static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(App)
                .run(args)
    }

}