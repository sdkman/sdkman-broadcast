package net.gvmtool.config

import net.gvmtool.converter.BroadcastIdMessageConverter
import net.gvmtool.converter.BroadcastListMessageConverter
import net.gvmtool.converter.BroadcastMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConverterConfiguration {

    @Bean
    BroadcastIdMessageConverter broadcastIdMessageConverter() {
        new BroadcastIdMessageConverter()
    }

    @Bean
    BroadcastMessageConverter broadcastMessageConverter() {
        new BroadcastMessageConverter()
    }

    @Bean
    BroadcastListMessageConverter broadcastListMessageConverter() {
        new BroadcastListMessageConverter()
    }
}
