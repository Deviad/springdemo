//package com.example.springdemo.configs;
//
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AuthorizationServerConfigurer extends
//        AuthorizationServerConfigurerAdapter {
//    AuthenticationManager authenticationManager;
//    public AuthorizationServerConfigurer(
//            AuthenticationConfiguration authenticationConfiguration
//    ) throws Exception {
//        this.authenticationManager =
//                authenticationConfiguration.getAuthenticationManager();
//    }
//    @Override
//    public void configure(
//            ClientDetailsServiceConfigurer clients
//    ) throws Exception {
//        clients.inMemory()
//                .withClient("client")
//                .authorizedGrantTypes("password")
//                .secret("{noop}secret")
//                .scopes("all");
//    }
//    @Override
//    public void configure(
//            AuthorizationServerEndpointsConfigurer endpoints
//    ) throws Exception {
//        endpoints.authenticationManager(authenticationManager);
//    }
//}
