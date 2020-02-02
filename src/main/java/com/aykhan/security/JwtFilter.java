package com.aykhan.security;

import com.aykhan.services.implementations.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@Profile("Secure")
public class JwtFilter extends OncePerRequestFilter {
  private final JwtTokenService jwtTokenService;
  private final MyUserDetailsService userDetailsService;

  @Autowired
  public JwtFilter(JwtTokenService jwtTokenService, MyUserDetailsService userDetailsService) {
    this.jwtTokenService = jwtTokenService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req,
                                  HttpServletResponse res,
                                  FilterChain filterChain) throws ServletException, IOException {
    extractTokenFromRequest(req)
        .flatMap(token -> jwtTokenService.tokenToClaim(token))
        .map(claim -> jwtTokenService.extractUserIdFromClaims(claim))
        .map(id -> userDetailsService.getById(Math.toIntExact(id)))
        .map(ud -> new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities()))
        .ifPresent(auth -> {
          auth.setDetails(
              new WebAuthenticationDetailsSource()
                  .buildDetails(req));
          SecurityContextHolder.getContext().setAuthentication(auth);
        });
    filterChain.doFilter(req, res);
  }

  private Optional<String> extractTokenFromRequest(HttpServletRequest req) {
    // getting header `Authorization`
    final String auth_header = req.getHeader(Const.AUTH_HEADER);

    // look for the token in the header
    if (StringUtils.hasText(auth_header) // is not empty
        && auth_header.startsWith(Const.AUTH_BEARER)) {
      return Optional.of(auth_header.substring(Const.AUTH_BEARER.length())); // cut the "Bearer " substring
    }

    // look for the token in the param `?token=...`
    String par_token = req.getParameter(Const.AUTH_TOKEN);
    if (!StringUtils.isEmpty(par_token)) {
      return Optional.of(par_token.substring(Const.AUTH_BEARER.length()));
    }

    // nothing found...
    return Optional.empty();
  }
}
