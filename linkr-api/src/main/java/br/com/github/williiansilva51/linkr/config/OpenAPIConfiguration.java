package br.com.github.williiansilva51.linkr.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Linkr API",
        version = "0.0.1",
        description = "Encurtador de URL feito em Java",
        contact = @Contact(name = "Willian Silva", email = "antonio.willian051@gmail.com"),
        license = @License(name = "MIT License", url = "https://opensource.org/license/mit")
))
class OpenAPIConfiguration {
}
