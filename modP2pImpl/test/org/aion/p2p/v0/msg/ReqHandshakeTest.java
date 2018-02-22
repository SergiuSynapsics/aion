/*
 * Copyright (c) 2017-2018 Aion foundation.
 *
 * This file is part of the aion network project.
 *
 * The aion network project is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * The aion network project is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the aion network project source files.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 * Contributors to the aion source files in decreasing order of code volume:
 *
 * Aion foundation.
 *
 */

package org.aion.p2p.v0.msg;

import static org.junit.Assert.*;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.aion.p2p.Ctrl;
import org.aion.p2p.Version;
import org.aion.p2p.v0.Act;
import org.aion.p2p.v0.Node;
import org.junit.Test;

/**
 * @author chris
 */
public class ReqHandshakeTest {

    private byte[] validNodeId = UUID.randomUUID().toString().getBytes();

    private int version = ThreadLocalRandom.current().nextInt();

    private byte[] invalidNodeId = UUID.randomUUID().toString().substring(0, 34).getBytes();

    private int port = ThreadLocalRandom.current().nextInt();

    private String randomIp = ThreadLocalRandom.current().nextInt(0,256) + "." +
            ThreadLocalRandom.current().nextInt(0,256) + "." +
            ThreadLocalRandom.current().nextInt(0,256) + "." +
            ThreadLocalRandom.current().nextInt(0,256);

    @Test
    public void testRoute(){

        ReqHandshake req = new ReqHandshake(validNodeId, version, Node.ipStrToBytes(randomIp), port);
        assertEquals(Version.V0, req.getHeader().getVer());
        assertEquals(Ctrl.NET, req.getHeader().getCtrl());
        assertEquals(Act.REQ_HANDSHAKE, req.getHeader().getAction());
    }

    @Test
    public void testValidEncodeDecode() {

        ReqHandshake req1 = new ReqHandshake(validNodeId, version, Node.ipStrToBytes(randomIp), port);
        byte[] bytes = req1.encode();

        ReqHandshake req2 = ReqHandshake.decode(bytes);
        assertArrayEquals(req1.getNodeId(), req2.getNodeId());
        assertArrayEquals(req1.getIp(), req2.getIp());
        assertEquals(req1.getVersion(), req2.getVersion());
        assertEquals(req1.getPort(), req2.getPort());

    }

    @Test
    public void testInvalidEncodeDecode() {

        ReqHandshake req1 = new ReqHandshake(invalidNodeId, version, Node.ipStrToBytes(randomIp), port);
        byte[] bytes = req1.encode();
        assertNull(bytes);
    }

}
