package br.com.sfpacim.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe de configuração principal do Spring Security.
 *
 * <p>
 * Define a política de autenticação, gerenciamento de sessão e
 * expõe os Beans necessários para a segurança da aplicação.
 *
 * @author Matheus F. N. Pereira
 */
@Configuration
@EnableWebSecurity
public class SegurancaConfig {

    /**
     * Expõe o BCryptPasswordEncoder como um Bean gerenciado pelo Spring.
     * 
     * <p>
     * Isso permite que ele seja injetado em outros serviços (como o UsuarioService)
     * para fazer o hash de senhas (RF06).
     *
     * @return Uma instância do PasswordEncoder (BCrypt).
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura a cadeia de filtros de segurança (Security Filter Chain) do Spring.
     * 
     * <p>
     * Define quais endpoints são públicos (ex: /autenticacao/**, /swagger-ui/**) e
     * quais são privados (exigindo autenticação).
     *
     * @param http O construtor de segurança do Http.
     * @return O SecurityFilterChain construído.
     * @throws Exception Exceção qualquer.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Desabilita a proteção CSRF (Cross-Site Request Forgery),
                // pois a autenticação será stateless (via token JWT).
                .csrf(csrf -> csrf.disable())

                // Define a política de gerenciamento de sessão como STATELESS (sem estado).
                // A API não criará ou manterá sessões de usuário (padrão RESTful).
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configura as regras de autorização para os endpoints HTTP
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso público (não autenticado) ao endpoint de cadastro (RF07).
                        .requestMatchers("/autenticacao/cadastro").permitAll()

                        // Permite acesso público (não autenticado) à documentação do Swagger.
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                        // Exige autenticação para todas as outras requisições.
                        .anyRequest().authenticated())
                .build();
    }
}
