package com.idfconnect.sample.filter;

import com.google.common.net.HttpHeaders;
import com.idfconnect.ssorest.common.collections.CollectionProviderException;
import com.idfconnect.ssorest.common.collections.mvp.MultiValuedProperties;
import com.idfconnect.ssorest.common.http.ServletConfigPropertiesHelper;
import com.idfconnect.ssorest.plugin.filter.SSORestServletFilterPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>CORSFilter class.</p>
 *
 * @author nghia
 * @since 1.0
 */
@WebFilter(urlPatterns = { "/*" })
public class CORSFilter implements Filter {

    static  String                ACCESS_CONTROL_MAX_AGE           = "AccessControlMaxAge";
    static  String                ACCESS_CONTROL_ALLOW_CREDENTIALS = "AccessControlAllowCredentials";
    static  String                ACCESS_CONTROL_ALLOW_HEADERS     = "AccessControlAllowHeaders";
    static  String                ACCESS_CONTROL_ORIGIN            = "AccessControlAllowOrigin";
    static  String                ACCESS_CONTROL_ALLOW_METHODS     = "AccessControlAllowMethods";

    Logger logger = LoggerFactory.getLogger(getClass());
    private MultiValuedProperties parameters                       = null;

    /** {@inheritDoc} */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            parameters = ServletConfigPropertiesHelper.loadFilterProperties(SSORestServletFilterPlugin.class, filterConfig);
        } catch (CollectionProviderException ce) {
            throw new ServletException("Unable to load Servlet's properties", ce);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.debug("CORSFilter HTTP Request: {}", request.getMethod());
        HttpServletResponse resp = (HttpServletResponse) response;

        List<String> allowOrigins = parameters.get(ACCESS_CONTROL_ORIGIN);
        String host = ((HttpServletRequest) servletRequest).getHeader(HttpHeaders.ORIGIN);

        if (allowOrigins != null && allowOrigins.size() > 0) {
            boolean allowed = allowOrigins.contains("*");
            if (allowed)
                resp.setHeader(com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            else {
                allowOrigins.forEach(origin -> {
                    if (origin.equalsIgnoreCase(host))
                        resp.setHeader(com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, host);
                });
            }
        }

        List<String> allowMethods = parameters.get(ACCESS_CONTROL_ALLOW_METHODS);
        if (allowMethods != null && allowMethods.size() > 0) {
            StringBuffer sb = new StringBuffer("");
            int i = 1;
            for (String method : allowMethods) {
                sb.append(method);
                if (i != allowMethods.size())
                    sb.append(",");
                i++;
            }
            resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, sb.toString());
        }

        int maxAge = parameters.getFirstValueAsInt(ACCESS_CONTROL_MAX_AGE);
        if (maxAge > 0)
            resp.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, Integer.toString(maxAge));

        Boolean allowcreds = parameters.getFirstValueAsBoolean(ACCESS_CONTROL_ALLOW_CREDENTIALS);
        if (allowcreds != null)
            resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, allowcreds.toString());

        List<String> allowHeaders = parameters.get(ACCESS_CONTROL_ALLOW_HEADERS);
        if (allowHeaders != null && allowHeaders.size() > 0) {
            StringBuffer sb = new StringBuffer("");
            int i = 1;
            for (String header : allowHeaders) {
                sb.append(header);
                if (i != allowHeaders.size())
                    sb.append(",");
                i++;
            }
            resp.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, sb.toString());
        }

        resp.setHeader(com.google.common.net.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Set-Cookie");

        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        chain.doFilter(request, response);
    }

    /** {@inheritDoc} */
    @Override
    public void destroy() {

    }
}
