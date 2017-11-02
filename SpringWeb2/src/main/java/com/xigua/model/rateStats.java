package com.xigua.model;

import java.io.Serializable;

public class rateStats implements Serializable{
    /**
     * XIGUA
     */
    private static final long serialVersionUID = 3113714221063388972L;
    
    private String ifIndex;
    private String ifSubindex;
    //if-curr-tx-octet-rate
    private String octetTx;
    //if-curr-rx-octet-rate
    private String octetRx;
    //if-curr-tx-pkt-rate
    private String pktTxRate;
    //if-curr-rx-pkt-rate
    private String pktRxRate;
    //if-curr-tx-octet-peak-rate
    private String octetTxPeak;
    //if-curr-rx-octet-peak-rate
    private String octetRxPeak;
    public String getIfIndex() {
        return ifIndex;
    }
    public void setIfIndex(String ifIndex) {
        this.ifIndex = ifIndex;
    }
    public String getIfSubindex() {
        return ifSubindex;
    }
    public void setIfSubindex(String ifSubindex) {
        this.ifSubindex = ifSubindex;
    }
    public String getOctetTx() {
        return octetTx;
    }
    public void setOctetTx(String octetTx) {
        this.octetTx = octetTx;
    }
    public String getOctetRx() {
        return octetRx;
    }
    public void setOctetRx(String octetRx) {
        this.octetRx = octetRx;
    }
    public String getPktTxRate() {
        return pktTxRate;
    }
    public void setPktTxRate(String pktTxRate) {
        this.pktTxRate = pktTxRate;
    }
    public String getPktRxRate() {
        return pktRxRate;
    }
    public void setPktRxRate(String pktRxRate) {
        this.pktRxRate = pktRxRate;
    }
    public String getOctetTxPeak() {
        return octetTxPeak;
    }
    public void setOctetTxPeak(String octetTxPeak) {
        this.octetTxPeak = octetTxPeak;
    }
    public String getOctetRxPeak() {
        return octetRxPeak;
    }
    public void setOctetRxPeak(String octetRxPeak) {
        this.octetRxPeak = octetRxPeak;
    }
    
}
