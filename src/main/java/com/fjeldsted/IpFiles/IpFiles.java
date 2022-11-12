package com.fjeldsted.IpFiles;

import io.ipfs.api.IPFS;

public class IpFiles {
    IPFS ipfs;

    public IpFiles() {
        this.ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
    }

    public IPFS getIPFS() {
        return this.ipfs;
    }
}
