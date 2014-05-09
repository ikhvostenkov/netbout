/**
 * Copyright (c) 2009-2014, netbout.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are PROHIBITED without prior written permission from
 * the author. This product may NOT be used anywhere and on any computer
 * except the server platform of netbout Inc. located at www.netbout.com.
 * Federal copyright law prohibits unauthorized reproduction by any means
 * and imposes fines up to $25,000 for violation. If you received
 * this code accidentally and without intent to use it, please report this
 * incident to the author by email.
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
package com.netbout.dynamo;

import com.jcabi.urn.URN;
import com.netbout.spi.Aliases;
import com.netbout.spi.Bout;
import com.netbout.spi.Friend;
import com.netbout.spi.Friends;
import com.netbout.spi.Inbox;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Integration case for {@link DyInbox}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 */
public final class DyInboxITCase {

    /**
     * DyInbox can list bouts and create.
     * @throws Exception If there is some problem inside
     */
    @Test
    public void makesAndListsBouts() throws Exception {
        final String alias = "jeffrey";
        final Aliases aliases =
            new DyBase().user(new URN("urn:test:88")).aliases();
        aliases.add(alias);
        final Inbox inbox = aliases.iterator().next().inbox();
        final long number = inbox.start();
        MatcherAssert.assertThat(
            inbox,
            Matchers.<Bout>iterableWithSize(1)
        );
        final Bout bout = inbox.bout(number);
        final Friends friends = bout.friends();
        MatcherAssert.assertThat(
            friends,
            Matchers.<Friend>iterableWithSize(1)
        );
        MatcherAssert.assertThat(
            friends.iterator().next().alias(),
            Matchers.equalTo(alias)
        );
    }

}