package net.gvmtool

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

@EnableAutoConfiguration
@ComponentScan("net.gvmtool")
class Application {
    static void main(String[] args) {
        new SpringApplication(Application).run(args)
    }
}
