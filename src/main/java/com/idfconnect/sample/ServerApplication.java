package com.idfconnect.sample;

import com.idfconnect.sample.filter.CORSFilter;
import com.idfconnect.sample.rest.RestApi;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.Hashtable;
import java.util.Map;

/**
 * JAX-RS application
 *
 * @author Nghia
 */
@ApplicationPath("/")
public class ServerApplication extends ResourceConfig {
    /**
     * <p>Constructor for ServerApplication.</p>
     *
     * @since 1.0
     */
    public ServerApplication() {
        super();
        register(RestApi.class);
//        register(AuthFilter.class);
        register(CORSFilter.class);

        Map<String, Object> props = new Hashtable<String, Object>();
        props.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        this.addProperties(props);
    }
}
