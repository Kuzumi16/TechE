package TechE.content.util;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;

import static arc.math.Angles.randLenVectors;

public class TEEffect {
    public static Effect square45_6_45;
    public static void load(){
        square45_6_45 = new Effect(45f, e -> {
            Draw.color(e.color, Color.white, e.fout() * 0.3f);
            randLenVectors(e.id, 6, 27f * e.finpow(), (x, y) -> {
                Fill.square(e.x + x, e.y + y, 5f * e.fout(), 45);
                Drawf.light(e.x + x, e.y + y, e.fout() * 9F, e.color, 0.7f);
            });
        });
    }
}
