/*******************************************************************************
 * Copyright (c) 2017-2018 Aion foundation.
 *
 *     This file is part of the aion network project.
 *
 *     The aion network project is free software: you can redistribute it
 *     and/or modify it under the terms of the GNU General Public License
 *     as published by the Free Software Foundation, either version 3 of
 *     the License, or any later version.
 *
 *     The aion network project is distributed in the hope that it will
 *     be useful, but WITHOUT ANY WARRANTY; without even the implied
 *     warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *     See the GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with the aion network project source files.
 *     If not, see <https://www.gnu.org/licenses/>.
 *
 * Contributors to the aion source files in decreasing order of code volume:
 * 
 *     Aion foundation.
 *     
 ******************************************************************************/

package org.aion.p2p.a0;

import java.nio.ByteBuffer;

/**
 * 
 * @author chris
 *
 */
public class ReadBuffer {

    int nodeIdHash = 0;

    ByteBuffer headerBuf = ByteBuffer.allocate(Codec.Header.SIZE);

    ByteBuffer bodyBuf = null;

    Codec.Header header = null;

    byte[] body = null;

    void refreshHeader(){
        headerBuf.clear();
        header = null;
    }

    void refreshBody(){
        bodyBuf = null;
        body = null;
    }

    /**
     * @return boolean
     */
    boolean isHeaderCompleted(){
        return header != null && body == null;
    }

    /**
     * @return boolean
     */
    boolean isBodyCompleted(){
        if(header == null)
            return false;
        else
            return body != null && body.length == header.getLen();
    }

}
