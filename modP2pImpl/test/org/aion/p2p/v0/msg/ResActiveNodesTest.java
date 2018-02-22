package org.aion.p2p.v0.msg;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.aion.p2p.Ctrl;
import org.aion.p2p.Version;
import org.aion.p2p.v0.Node;
import org.aion.p2p.v0.Act;
import org.junit.Assert;
import org.junit.Test;

public class ResActiveNodesTest {

    private Node randomNode(){
        return new Node(
            ThreadLocalRandom.current().nextBoolean(),
            UUID.randomUUID().toString().getBytes(),
            Node.ipStrToBytes(
                ThreadLocalRandom.current().nextInt(0,256) + "." +
                ThreadLocalRandom.current().nextInt(0,256) + "." +
                ThreadLocalRandom.current().nextInt(0,256) + "." +
                ThreadLocalRandom.current().nextInt(0,256)
            ),
            ThreadLocalRandom.current().nextInt()
        );
    }


    @Test
    public void testRoute() {

        ResActiveNodes res = new ResActiveNodes(new ArrayList<>());
        assertEquals(Version.V0, res.getHeader().getVer());
        assertEquals(Ctrl.NET, res.getHeader().getCtrl());
        assertEquals(Act.RES_ACTIVE_NODES, res.getHeader().getAction());

    }

    @Test
    public void testEncodeDecode() {

        int m = ThreadLocalRandom.current().nextInt(0, 20);
        List<Node> srcNodes = new ArrayList<>();
        for(int i = 0; i < m; i++){
            srcNodes.add(randomNode());
        }

        ResActiveNodes res = ResActiveNodes.decode(new ResActiveNodes(srcNodes).encode());
        assertEquals(res.getNodes().size(), m);
        List<Node> tarNodes = res.getNodes();
        for(int i = 0; i < m; i++){

            Node srcNode = srcNodes.get(i);
            Node tarNode = tarNodes.get(i);

            Assert.assertArrayEquals(srcNode.getId(), tarNode.getId());
            Assert.assertEquals(srcNode.getIdHash(), tarNode.getIdHash());
            Assert.assertArrayEquals(srcNode.getIp(), tarNode.getIp());

            Assert.assertTrue(srcNode.getIpStr().equals(tarNode.getIpStr()));
            Assert.assertEquals(srcNode.getPort(), tarNode.getPort());

        }
    }
}