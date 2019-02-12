// Copyright 2018 The casbin Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example.demo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.StringTokenizer;

@WebFilter("/*")
public class HttpBasicAuthnFilter implements Filter {
    private String realm = "Protected";

    // Gets HTTP basic authentication's user name and password.
    private String getUserPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            StringTokenizer st = new StringTokenizer(authHeader);
            if (st.hasMoreTokens()) {
                String basic = st.nextToken();

                if (basic.equalsIgnoreCase("Basic")) {

                    String credentials = authHeader.substring("Basic".length()).trim();
                    System.out.println("-------------credentials-------------" + credentials);
                    return credentials;
//                    try {
//                        String credentials = new String(Base64.getDecoder().decode(st.nextToken()), "UTF-8");
//                        int p = credentials.indexOf(":");
//                        if (p != -1) {
//                            return credentials;
//                        } else {
//                            unauthorized(response, "Invalid authentication token");
//                        }
//                    } catch (UnsupportedEncodingException e) {
//                        throw new Error("Couldn't retrieve authentication", e);
//                    }
                }
            }
        } else {
            unauthorized(response, "Authorization header not found");
        }

        return "";
    }

    // Checks the correctness of user name and password as you like.
    private boolean checkUserPassword(String username, String password) {
        return true;
    }

    // Returns HTTP 401 Unauthorized if the authentication fails.
    private void unauthorized(HttpServletResponse response, String message) throws IOException {
        response.setHeader("WWW-Authenticate", "Basic realm=\"" + realm + "\"");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    // Filters all requests through HTTP basic authentication.
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // Get user name and password from HTTP header.
        String credentials = getUserPassword(request, response);
        if (credentials.equals("")) {
            return;
        }
        int p = credentials.indexOf(":");
        String username = credentials.substring(0, p).trim();
        String password = credentials.substring(p + 1).trim();

        // Check the user name and password.
        if (!checkUserPassword(username, password)) {
            unauthorized(response, "Bad credentials");
        }

        // All passed, go to the next handler.
        filterChain.doFilter(servletRequest, servletResponse);
    }
}