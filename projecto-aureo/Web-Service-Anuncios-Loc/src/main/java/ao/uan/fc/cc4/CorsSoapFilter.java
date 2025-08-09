package ao.uan.fc.cc4;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class CorsSoapFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Não precisa inicialização especial
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // Permitir qualquer origem (pode trocar para o seu domínio)
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Métodos permitidos
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");

        // Cabeçalhos permitidos
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, SOAPAction");

        // Cache do CORS
        response.setHeader("Access-Control-Max-Age", "3600");

        // Se for pré-flight OPTIONS, retorna direto sem passar para o endpoint SOAP
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        // Não precisa destruir recursos
    }
}
