package com.mitchej123.hodgepodge.util;

import JinRyuu.JRMCore.JRMCoreH;

import java.util.ArrayList;
import java.util.List;

public class JbraHelper {

    public static List<String[]> formsEff = new ArrayList<String[]>() {{
        add(new String[]{"âÂ", "âŞ", "âŢ"});
        add(new String[]{"âş", "ĂĂ", "Ăâ"});
        add(new String[]{"âş", "ĂĂ", "Ăâ"});
        add(new String[]{"âĂ", "Âţ", "âÎ"});
        add(new String[]{"Ăî", "\u3040ぁ", "\u3040あ"});
    }};

    public static float getCustomWidth(int state, final int race, String statusEff) {
        float f2 = 1.0f;
        if (race == 0) { //Human
            if (statusEff.contains(formsEff.get(0)[0])) { //Human Race, Full Release
                f2 = 1.05f;
            } else if (statusEff.contains(formsEff.get(0)[1])) { //Human Race, High Tension
                f2 = 1.02f;
            } else if (statusEff.contains(formsEff.get(0)[2])) { //Human Race, Mystic
                f2 = 0.95f;
            }
        } else if (race == 1 || race == 2) { //Saiyan/HalfSaiyan
            if (statusEff.contains(formsEff.get(1)[0])) { //Saiyan, SSJ1
                f2 = 1.05f;
            } else if (statusEff.contains(formsEff.get(1)[1])) { //Saiyan, SSJ2
                f2 = 1.08f;
            } else if (statusEff.contains(formsEff.get(1)[2])) { //Saiyan, SSJ3
                f2 = 1.2f;
            }
        } else if (race == 3) { //Namekian
            if (statusEff.contains(formsEff.get(3)[0])) { //Namekian Buffed
                f2 = 1.1f;
            } else if (statusEff.contains(formsEff.get(3)[1])) { //Namekian, Ultra
                f2 = 1.02f;
            } else if (statusEff.contains(formsEff.get(3)[2])) { //Namekian, Berserk
                f2 = 1.05f;
            }
        } else if (race == 4) { //Arcosian
            if (statusEff.contains(formsEff.get(4)[0])) { //Arcosian, Full Release
                f2 = 1.08f;
            } else {
                f2 = JRMCoreH.TransFrBlk[state];
            }
        }
        return f2;
    }

    public static float getCustomHeight(int state, final int race, String statusEff) {
        float f3 = 1f;
        if (race == 0) { //Human
            if (statusEff.contains(formsEff.get(0)[0])) {
                f3 = 1.02f;
            } else if (statusEff.contains(formsEff.get(0)[1])) {
                f3 = 1.00f;
            } else if (statusEff.contains(formsEff.get(0)[2])) {
                f3 = 1.000f;
            }
        } else if (race == 1 || race == 2) { //Saiyan/HalfSaiyan
            if (statusEff.contains(formsEff.get(1)[0])) {
                f3 = 1.02f;
            } else if (statusEff.contains(formsEff.get(1)[1])) {
                f3 = 1.06f;
            } else if (statusEff.contains(formsEff.get(1)[2])) {
                f3 = 1.22f;
            }
        } else if (race == 3) { //Namekian
            if (statusEff.contains(formsEff.get(3)[0])) {
                f3 = 1.1f;
            } else if (statusEff.contains(formsEff.get(3)[1])) {
                f3 = 1.02f;
            } else if (statusEff.contains(formsEff.get(3)[2])) {
                f3 = 1.05f;
            }
        } else if (race == 4) { //Arcosian
            if (statusEff.contains(formsEff.get(4)[0])) { //Arcosian, Full Release
                f3 = 1.0f;
            } else {
                f3 = JRMCoreH.TransFrBlk[state];
            }
        }
        return f3;
    }

}
