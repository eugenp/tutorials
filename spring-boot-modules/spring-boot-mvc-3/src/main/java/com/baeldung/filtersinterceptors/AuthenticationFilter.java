package com.baeldung.filtersinterceptors;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws
            ServletException, IOException {
        String usrName = request.getHeader(“userName”);
        logger.info("Successfully authenticated user  " +
                userName);
        filterChain.doFilter(request, response);
    }

    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}