package net.gvmtool.config

import net.gvmtool.converter.BroadcastMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class ConverterConfiguration {

    @Bean
    BroadcastMessageConverter broadcastMessageConverter() {
        new BroadcastMessageConverter()
    }

}
