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
package com.netbout.rest;

import com.rexsl.core.XslResolver;
import com.rexsl.test.JaxbConverter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.Marshaller;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;
import org.xmlmatchers.XmlMatchers;
import org.xmlmatchers.transform.XmlConverters;

/**
 * Test case for {@link JaxbBundle}.
 * @author Yegor Bugayenko (yegor@netbout.com)
 * @version $Id$
 */
public final class JaxbBundleTest {

    /**
     * Object can be converted to XML text.
     * @throws Exception If there is some problem inside
     */
    @Test
    public void testBundleToXmlConversion() throws Exception {
        final JaxbBundle bundle = new JaxbBundle("root")
            .add("employee")
                .attr("age", "10")
                .add("dept")
                    .add("country", "DE")
                    .up()
                    .add("salary", "> \u20AC 50,000")
                    .up()
                    .attr("boss", "Charles de Batz-Castelmore d'Artagnan")
                .up()
            .up();
        MatcherAssert.assertThat(
            XmlConverters.the(bundle.element()),
            XmlMatchers.hasXPath(
                "/root/employee[@age='10']/dept/country[.='DE']"
            )
        );
    }

    /**
     * Object can be converted to XML through JAXB.
     * @throws Exception If there is some problem inside
     */
    @Test
    public void testMarshallingWorks() throws Exception {
        final XslResolver resolver = new XslResolver();
        final Providers providers = Mockito.mock(Providers.class);
        Mockito.doReturn(resolver).when(providers).getContextResolver(
            Marshaller.class,
            MediaType.APPLICATION_XML_TYPE
        );
        final Resource resource = Mockito.mock(Resource.class);
        Mockito.doReturn(providers).when(resource).providers();
        final JaxbBundle bundle = new JaxbBundle("alpha")
            .add("beta-1")
                .attr("name", "Joe")
            .up()
            .add("beta-2")
                .add("gamma", "works fine, isn't it?")
                .up()
            .up();
        final Page page = PageBuilder.INSTANCE.build(resource, "test")
            .append(bundle.element())
            .append("Test me");
        MatcherAssert.assertThat(
            JaxbConverter.the(page, JaxbBundle.class),
            XmlMatchers.hasXPath(
                "/page/alpha/beta-2/gamma[contains(.,'works')]"
            )
        );
    }

}
