package DAMAnunciosLocService.AnunciosLoc.config.web;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class Wsconfig extends WsConfigurerAdapter {


    @Bean
    ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext contexto) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(contexto);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<MessageDispatcherServlet>(servlet, "/ws/*");
    }

    @Bean(name = "user")
    DefaultWsdl11Definition userWsdlDefinition ( XsdSchema  userSchema ) {
        DefaultWsdl11Definition wsdl11def = new DefaultWsdl11Definition();

        wsdl11def.setPortTypeName("/apiSoapHttpAnunciosLocUser");
        wsdl11def.setLocationUri("/ws");
        wsdl11def.setTargetNamespace("http://user.soap.xml");
        wsdl11def.setSchema(userSchema);

        return wsdl11def;
    }

    @Bean(name = "user2")
    DefaultWsdl11Definition user2WsdlDefinition ( XsdSchema user2Schema ) {
        DefaultWsdl11Definition wsdl11def = new DefaultWsdl11Definition();

        wsdl11def.setPortTypeName("/apiSoapHttpAnunciosLocUser");
        wsdl11def.setLocationUri("/ws");
        wsdl11def.setTargetNamespace("http://user2.soap.xml");
        wsdl11def.setSchema(user2Schema);

        return wsdl11def;
    }

    @Bean
    XsdSchema userSchema () {
        return new SimpleXsdSchema(new ClassPathResource("xsd/user_schema.xsd") );
    }

    @Bean
    XsdSchema user2Schema () {
        return new SimpleXsdSchema(new ClassPathResource("xsd/user2_schema.xsd") );
    }

}