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

package com.example.demo;;

import org.casbin.jcasbin.main.Enforcer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;


import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.util.Util;
import org.casbin.adapter.JDBCAdapter;
@WebFilter("/*")
public class JCasbinAuthzFilter implements Filter {
    static Enforcer enforcer;

    // Initialize jCasbin's enforcer with model and policy rules.
    // Here we load policy from file, you can choose to load policy from database.
    JDBCAdapter a = new JDBCAdapter("com.mysql.cj.jdbc.Driver", "jdbc:postgresql://localhost/jcasbin", "vbmac", "", true);

    public void init(FilterConfig filterConfig) throws ServletException {
        enforcer = new Enforcer("examples/authz_model.conf", a);
        enforcer.loadPolicy();
    }

    // In this demo, we use HTTP basic authentication as the authentication method.
    // This method retrieves the user name from the HTTP header and passes it to jCasbin.
    // You can change to your own authentication method like OAuth, JWT, Apache Shiro, etc.
    // You need to implement this getUser() method to make sure jCasbin can get the
    // authenticated user name.
    private String getUser(HttpServletRequest request) {
        String res = "";

        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Basic")) {
            //String base64Credentials = authorization.substring("Basic".length()).trim();
            String credentials = authorization.substring("Basic".length()).trim();
            System.out.println("-------credentials from casbin filter------" + credentials);
           // String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                    //Charset.forName("UTF-8"));
            final String[] values = credentials.split(":", 2);
            res = values[0];
        }

        return res;
    }

    // Filters all requests through jCasbin's authorization.
    // If jCasbin allows the request, pass the request to next handler.
    // If jCasbin denies the request, return HTTP 403 Forbidden.
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String user = getUser(request);
        String path = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("(" + user + ", " + path + ", " + method + ")");

        enforcer.loadPolicy();

        if (enforcer.enforce(user, path, method)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    public void destroy() {
    }
}