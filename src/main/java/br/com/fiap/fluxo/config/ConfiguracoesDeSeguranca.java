
package br.com.fiap.fluxo.config;

import br.com.fiap.fluxo.enums.Roles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class ConfiguracoesDeSeguranca {

    @Value("${fluxo.jwt-secret}")
    private String secret;

    // AUTENTICAÇÃO

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder.encode("123"))
                .roles(Roles.ADMIN.name())
                .build();

        UserDetails operador = User
                .withUsername("operador")
                .password(passwordEncoder.encode("456"))
                .roles(Roles.OPERADOR.name())
                .build();

        return new InMemoryUserDetailsManager(admin, operador);
    }

    // JWT

    @Bean
    public SecretKey secretKey() {
        return new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                "HmacSha256"
        );
    }

    @Bean
    public JwtEncoder jwtEncoder(SecretKey secretKey) {
        return NimbusJwtEncoder
                .withSecretKey(secretKey)
                .algorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey secretKey) {
        return NimbusJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthorityPrefix("");
        authoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(
                authoritiesConverter
        );

        return converter;
    }

    // AUTORIZAÇÃO

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter jwtAuthenticationConverter) {

        return http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Swagger
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Login
                        .requestMatchers(
                                HttpMethod.POST,
                                "/autenticacao/login"
                        ).authenticated()

                        // Cadastro de linhas e estações
                        .requestMatchers(
                                HttpMethod.POST,
                                "/linhas",
                                "/estacoes"
                        ).hasRole(Roles.ADMIN.name())

                        // Registro de ocupações
                        .requestMatchers(
                                HttpMethod.POST,
                                "/ocupacoes"
                        ).hasAnyRole(
                                Roles.ADMIN.name(),
                                Roles.OPERADOR.name()
                        )

                        // Consultas
                        .requestMatchers(
                                HttpMethod.GET,
                                "/linhas", "/linhas/**",
                                "/estacoes", "/estacoes/**",
                                "/ocupacoes", "/ocupacoes/**",
                                "/alertas", "/alertas/**"
                        ).hasAnyRole(
                                Roles.ADMIN.name(),
                                Roles.OPERADOR.name()
                        )

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/linhas/**",
                                "/estacoes/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/linhas/**",
                                "/estacoes/**"
                        ).hasRole("ADMIN")

                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/actuator/**").hasRole(Roles.ADMIN.name())

                        // Demais endpoints bloqueados
                        .anyRequest().denyAll()
                )

                .httpBasic(Customizer.withDefaults())

                .oauth2ResourceServer(resource ->
                        resource.jwt(config ->
                                config.jwtAuthenticationConverter(
                                        jwtAuthenticationConverter
                                )
                        )
                )

                .build();
    }
}
