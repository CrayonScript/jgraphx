package com.mxgraph.model;

public enum DropFlagEnum
{
    OUTER(1),
    INNER_1(2),
    INNER_2(4);

    public int bit;
    public int bitIndex;

    DropFlagEnum(int bit)
    {
        this.bit = bit;
        this.bitIndex = 0;
        while ((bit & 1) == 0) { this.bitIndex++; bit = bit >> 1; }
    }
}



