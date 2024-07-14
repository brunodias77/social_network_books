package com.brunodias.social_network_books.security;

import com.brunodias.social_network_books.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // Gera um construtor com argumentos para todos os campos final na classe
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override // Método para carregar detalhes do usuário pelo nome de usuário (email)
    @Transactional // Garante que a operação seja realizada em uma transação
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao foi encontrado !"));    }
}
