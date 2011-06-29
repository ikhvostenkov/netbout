/**
 * Copyright (c) 2009-2011, netBout.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are PROHIBITED without prior written permission from
 * the author. This product may NOT be used anywhere and on any computer
 * except the server platform of netBout Inc. located at www.netbout.com.
 * Federal copyright law prohibits unauthorized reproduction by any means
 * and imposes fines up to $25,000 for violation. If you received
 * this code occasionally and without intent to use it, please report this
 * incident to the author by email: privacy@netbout.com.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package com.netbout.rest;

// bout manipulation engine from com.netbout:netbout-engine
import com.netbout.engine.User;

// JDK
import java.security.Principal;

// JAX-RS
import javax.ws.rs.core.SecurityContext;

/**
 * Authenticator.
 *
 * @author Yegor Bugayenko (yegor@netbout.com)
 * @version $Id$
 */
public final class Auth {

    /**
     * Name of identity.
     */
    private User user;

    /**
     * Public ctor.
     * @param bldr Factory builder
     * @param ctx The context
     * @todo #103 Here we should validate that this identity can be
     *       used with currently logged in user. If the user is not
     *       logged in - we should throw a runtime exception.
     */
    public Auth(final FactoryBuilder bldr, final SecurityContext ctx) {
        final Principal principal = ctx.getUserPrincipal();
        if (principal == null) {
            throw new NotLoggedInException();
        }
        final Long num = Long.valueOf(principal.getName());
        this.user = bldr.getUserFactory().find(num);
    }

    /**
     * Get currently logged in user.
     * @return The user
     */
    public User user() {
        return this.user;
    }

}