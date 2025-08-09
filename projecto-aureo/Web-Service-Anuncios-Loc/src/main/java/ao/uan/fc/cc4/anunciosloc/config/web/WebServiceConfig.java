package ao.uan.fc.cc4.anunciosloc.config.web;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
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

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext contexto) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(contexto);
        servlet.setTransformWsdlLocations(true);

        ServletRegistrationBean<MessageDispatcherServlet> registration =
                new ServletRegistrationBean<>(servlet, "/ws/*");
        // importante: faz com que o servlet trate requisições OPTIONS
        registration.addInitParameter("dispatchOptionsRequest", "true");
    return registration;
}

    // 🔹 Filtro CORS para permitir chamadas SOAP pelo navegador
    @Bean
public FilterRegistrationBean<Filter> corsFilterRegistration() {
    FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new Filter() {
        @Override
        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                throws IOException, ServletException {

            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            String origin = request.getHeader("Origin");
            if (origin == null || origin.isEmpty()) {
                origin = "*";
            }

            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Vary", "Origin");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, HEAD");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, SOAPAction, X-Requested-With, Authorization");
            response.setHeader("Access-Control-Max-Age", "3600");
            // se precisares de credenciais:
            // response.setHeader("Access-Control-Allow-Credentials", "true");

            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                // responde direto ao pré‑flight
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }

            chain.doFilter(req, res);
        }
    });

    registration.addUrlPatterns("/ws/*");
    registration.setName("soapCorsFilter");
    registration.setOrder(Integer.MIN_VALUE); // garantir execução antes de outros filtros
    return registration;
}

    @Bean(name = "user")
    DefaultWsdl11Definition userWsdlDefinition(XsdSchema userSchema) {
        DefaultWsdl11Definition wsdl11def = new DefaultWsdl11Definition();
        wsdl11def.setPortTypeName("/apiSoapHttpAnunciosLocUser");
        wsdl11def.setLocationUri("/ws");
        wsdl11def.setTargetNamespace("http://user.soap.xml");
        wsdl11def.setSchema(userSchema);
        return wsdl11def;
    }

    @Bean
    XsdSchema userSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/user_schema.xsd"));
    }

    @Bean(name = "infra")
    DefaultWsdl11Definition infraWsdlDefinition(XsdSchema infraSchema) {
        DefaultWsdl11Definition wsdl11def = new DefaultWsdl11Definition();
        wsdl11def.setPortTypeName("/apiSoapHttpAnunciosLocEstacao");
        wsdl11def.setLocationUri("/ws");
        wsdl11def.setTargetNamespace("http://infra.soap.xml");
        wsdl11def.setSchema(infraSchema);
        return wsdl11def;
    }

    @Bean
    XsdSchema infraSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/infra_schema.xsd"));
    }

    @Bean(name = "anuncio")
    DefaultWsdl11Definition anuncioWsdlDefinition(XsdSchema anuncioSchema) {
        DefaultWsdl11Definition wsdl11def = new DefaultWsdl11Definition();
        wsdl11def.setPortTypeName("/apiSoapHttpAnunciosLocReplication");
        wsdl11def.setLocationUri("/ws");
        wsdl11def.setTargetNamespace("http://anuncio.soap.xml");
        wsdl11def.setSchema(anuncioSchema);
        return wsdl11def;
    }

    @Bean
    XsdSchema anuncioSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/anuncio_schema.xsd"));
    }
}