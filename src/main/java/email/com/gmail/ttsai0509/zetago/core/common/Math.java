package email.com.gmail.ttsai0509.zetago.core.common;

import java.util.Random;

public enum Math {

    ; // Utility

    public static final Random random = new Random();

    public static int floor(float f) {
        int xi = (int) f;
        return f < xi ? xi - 1 : xi;
    }

    public static int ceil(float f) {
        int xi = (int) f;
        return f < xi ? xi : xi + 1;
    }

    public static float sign(float f) {
        return (Float.floatToIntBits(f) & 0x80000000) | 0b1;
    }

    public static float abs(float f) {
        return f < 0 ? -f : f;
    }

    public static float clamp(float f, float low, float high) {
        if (f < low)
            return low;
        if (f > high)
            return high;
        return f;
    }

    public static float dst2(float px, float py, float pz, float qx, float qy, float qz) {
        float dx = qx - px;
        float dy = qy - py;
        float dz = qz - pz;
        return dx * dx + dy * dy + dz * dz;
    }

}
