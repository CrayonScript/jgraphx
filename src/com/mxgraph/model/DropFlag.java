package com.mxgraph.model;

public enum DropFlag
{
    OUTER(1),
    INNER_1(2),
    INNER_2(4);

    public int bit;
    public int bitIndex;

    DropFlag(int bit)
    {
        this.bit = bit;
        this.bitIndex = 0;
        while ((bit & 1) == 0) { this.bitIndex++; bit = bit >> 1; }
    }
}



