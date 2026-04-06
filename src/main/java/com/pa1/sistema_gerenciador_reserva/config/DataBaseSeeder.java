package com.pa1.sistema_gerenciador_reserva.config;

import com.pa1.sistema_gerenciador_reserva.domain.Role;
import com.pa1.sistema_gerenciador_reserva.domain.Usuario;
import com.pa1.sistema_gerenciador_reserva.repositorys.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
@Configuration
public class DataBaseSeeder {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository repository, PasswordEncoder encoder) {
        return args -> {
            if (repository.count() == 0) {

                Usuario sindico = new Usuario();
                sindico.setNome("Síndico");
                sindico.setEmail("sindico@sistema.com");
                sindico.setSenha(encoder.encode("sindico123"));
                sindico.setCpf("111.111.111-11");
                sindico.setRoles(Set.of(Role.SINDICO));
                sindico.setPrecisaTrocarSenha(false);
                sindico.setAtivo(true);
                repository.save(sindico);


                Usuario gerente = new Usuario();
                gerente.setNome("Gerente");
                gerente.setEmail("gerente@sistema.com");
                gerente.setSenha(encoder.encode("gerente123"));
                gerente.setCpf("222.222.222-22");
                gerente.setRoles(Set.of(Role.GERENTE));
                gerente.setPrecisaTrocarSenha(false);
                gerente.setAtivo(true);
                repository.save(gerente);

                System.out.println(">>> USUÁRIOS INICIAIS CRIADOS: sindico@sistema.com e gerente@sistema.com");
            }
        };
    }
}

