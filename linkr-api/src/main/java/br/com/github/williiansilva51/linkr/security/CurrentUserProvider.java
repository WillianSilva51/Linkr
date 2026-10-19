package br.com.github.williiansilva51.linkr.security;

import java.util.Set;

public interface CurrentUserProvider {
    boolean isAuthenticated();

    boolean isAdmin();

    Set<String> getRoles();
}
