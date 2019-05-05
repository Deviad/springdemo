package com.example.springdemo.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Order(value = Integer.MIN_VALUE)
public class JsonToUrlEncodedAuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        if (Objects.equals(request.getContentType(), "application/json") && Objects.equals(((RequestFacade) request).getServletPath(), "/oauth/token")) {
            InputStream is = request.getInputStream();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is), 16384)) {
                String json = br.lines()
                        .collect(Collectors.joining(System.lineSeparator()));
                HashMap<String, String> result = new ObjectMapper().readValue(json, HashMap.class);
                HashMap<String, String[]> r = new HashMap<>();

                for (String key : result.keySet()) {
                    String[] val = new String[1];
                    val[0] = result.get(key);
                    r.put(key, val);
                }
                String[] val = new String[1];
                val[0] = ((RequestFacade) request).getMethod();
                r.put("_method", val);

                HttpServletRequest s = new MyServletRequestWrapper(((HttpServletRequest) request), r);
                chain.doFilter(s, response);
            }

        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    class MyServletRequestWrapper extends HttpServletRequestWrapper {
        private final HashMap<String, String[]> params;

        MyServletRequestWrapper(HttpServletRequest request, HashMap<String, String[]> params) {
            super(request);
            this.params = params;
        }

        @Override
        public String getParameter(String name) {
            if (this.params.containsKey(name)) {
                return this.params.get(name)[0];
            }
            return "";
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return this.params;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return new Enumerator<>(params.keySet());
        }

        @Override
        public String[] getParameterValues(String name) {
            return params.get(name);
        }
    }

//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Data
//    class JsonToHashMapConverter  {
//
//        private String source;
//        private final ObjectMapper jsonMapper = new ObjectMapper();
//
//        public Object convert(String source) throws IOException {
//            return jsonMapper.readValue(source, LinkedHashMap.class);
//        }
//    }
}
