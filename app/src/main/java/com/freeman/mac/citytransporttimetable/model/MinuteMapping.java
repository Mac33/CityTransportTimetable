package com.freeman.mac.citytransporttimetable.model;


/**
 * Created by Mac on 7. 3. 2017.
 */

public class MinuteMapping {

        public static final int None = 0;
        public static final int LowLevel = 1;
        public static final int L = 2;
        public static final int r = 4;
        public static final int z = 8;
        public static final int m = 16;
        public static final int f = 32;
        public static final int e = 64;





    public int Minute = 0;

    public int Type = 0;

    public MinuteMapping(int minute, int type)
    {
        this.Minute = minute;
        this.Type = type ;
    }

    public String getText()
    {

        String minute = String.format("%02d", this.Minute);
        if (this.Type == (this.Type | LowLevel) )
        {
            minute = minute + "n";
        }

        if (this.Type == (this.Type | r ) )
        {
            minute = minute + "r";
        }

        if (this.Type == (this.Type | L ) )
        {
            minute = minute + "L";
        }
        if (this.Type == (this.Type | z ) )
        {
            minute = minute + "ž";
        }
        if (this.Type == (this.Type | m ) )
        {
            minute = minute + "m";
        }
        if (this.Type == (this.Type | f ) )
        {
            minute = minute + "f";
        }
        if (this.Type == (this.Type | e ) )
        {
            minute = minute + "e";
        }

        return minute;
    }


    public static String getTextSign(int id)
    {
        switch (id)
        {
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
            default:
                return StringUtils.Empty;
        }

    }

}
