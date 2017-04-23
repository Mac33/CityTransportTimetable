package com.freeman.mac.citytransporttimetable.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mac on 7. 3. 2017.
 */

public class MinuteMapping {

    private static final int None = 0;
    private static final int LowLevel = 1;
    private static final int L = 2;
    private static final int r = 4;
    private static final int z = 8;
    private static final int m = 16;
    private static final int f = 32;
    private static final int e = 64;
    private static final int S = 128;
    private static final int z2 = 256;
    private static final int b = 512;
    private static final int M = 1024;
    private static final int T = 2048;
    private static final int g = 4096;
    private static final int v = 8192;
    private static final int n = 16384;
    private static final int k = 32798;
    private static final int G = 65536;
    private static final int U = 131072;
    private static final int K = 262144;
    private static final int o = 524288;
    private static final int R = 1048576;
    private static final int V = 2097152;

    public int Minute = 0;

    public int Type = 0;

    public MinuteMapping(int minute, int type) {
        this.Minute = minute;
        this.Type = type;
    }

    public static String getTextSign(int id) {
        switch (id) {
            case None:
                return StringUtils.Empty;
            case LowLevel:
                return "n";
            case L:
                return "L";
            case r:
                return "r";
            case z:
                return "ž";
            case m:
                return "m";
            case f:
                return "f";
            case e:
                return "e";
            case S:
                return "S";
            case z2:
                return "z";
            case M:
                return "M";
            case b:
                return "b";
            case T:
                return "T";
            case g:
                return "g";
            case v:
                return "v";
            case n:
                return "a";
            case k:
                return "k";
            case G:
                return "G";
            case K:
                return "K";
            case o:
                return "o";
            case R:
                return "R";
            case V:
                return "V";
            case U:
                return "U";

            default:
                return StringUtils.Empty;
        }

    }

    public String getText() {

        String minute = String.format("%02d", this.Minute);

        List<Integer> types = new ArrayList<>();
        types.add(LowLevel);
        types.add(L);
        types.add(r);
        types.add(z);
        types.add(m);
        types.add(f);
        types.add(e);
        types.add(S);
        types.add(z2);
        types.add(b);
        types.add(M);
        types.add(T);
        types.add(g);
        types.add(v);
        types.add(n);
        types.add(k);
        types.add(G);
        types.add(K);
        types.add(o);
        types.add(R);
        types.add(V);
        types.add(U);

        for (Integer item : types) {
            if (this.Type == (this.Type | item)) {
                minute = minute + getTextSign(item);
            }
        }

        return minute;
    }

}
