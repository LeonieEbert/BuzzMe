package com.example.buzzme.Utils;

import android.graphics.Color;

/**
 * Created by User on 30.04.2018.
 * Methode um die Schriftfarbe des Projektes in Abhängigkeit der Hintergrundfarbe festzulegen
 */

public class ColorUtil {
    private static int getBrightness(int color) {
        int[] rgb = {Color.red(color), Color.green(color), Color.blue(color)};
        return (int) Math.sqrt(rgb[0] * rgb[0] * .241 + rgb[1]
                * rgb[1] * .691 + rgb[2] * rgb[2] * .068);
    }

    public static int getTextColorBasedOnBackgroundBrightness(int color) {
        if (getBrightness(color) < 130)
            return Color.WHITE;
        else
            return Color.BLACK;
    }
}
