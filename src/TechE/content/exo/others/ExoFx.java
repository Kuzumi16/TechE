package TechE.content.exo.others;

import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Mathf.rand;

public class ExoFx {
    public static Effect
            explodeyscathe = new Effect(35f, 160f, e -> {
        color(e.color);
        stroke(e.fout() * 5f);
        float circleRad = 12f + e.finpow() * 60f;
        Lines.circle(e.x, e.y, circleRad);

        rand.setSeed(e.id);
        for(int i = 0; i < 16; i++){
            float angle = rand.random(360f);
            float lenRand = rand.random(0.5f, 1f);
            Tmp.v1.trns(angle, circleRad);

            for(int s : Mathf.signs){
                Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 25f, e.fout() * 30f * lenRand + 6f, angle + 90f + s * 90f);
            }
        }
        color(e.color);
        stroke(e.fout() * 7f);
        circleRad = 9f + e.finpow() * 60f;
        Lines.circle(e.x, e.y, circleRad);

        rand.setSeed(e.id);
        for(int i = 0; i < 16; i++){
            float angle = rand.random(360f);
            float lenRand = rand.random(0.5f, 1f);
            Tmp.v1.trns(angle, circleRad);

            for(int s : Mathf.signs){
                Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 15f, e.fout() * 40f * lenRand + 6f, angle + 90f + s * 90f);
            }
        }
    }),
            colorBombSmaller = new Effect(30f, 100f, e -> {
                color(e.color);
                stroke(e.fout());
                float circleRad = 3f + e.finpow() * 18f;
                Lines.circle(e.x, e.y, circleRad);

                color(e.color);
                for(int i = 0; i < 4; i++){
                    Drawf.tri(e.x, e.y, 2f, 20f * e.fout(), i*90);
                }

                color();
                for(int i = 0; i < 4; i++){
                    Drawf.tri(e.x, e.y, 0.9f, 6f * e.fout(), i*90);
                }

                Drawf.light(e.x, e.y, circleRad * 1.6f, e.color, e.fout());
            }),
            colorBomb = new Effect(40f, 100f, e -> {
                color(e.color);
                stroke(e.fout() * 2f);
                float circleRad = 4f + e.finpow() * 45f;
                Lines.circle(e.x, e.y, circleRad);

                color(e.color);
                for(int i = 0; i < 4; i++){
                    Drawf.tri(e.x, e.y, 4f, 50f * e.fout(), i*90);
                }

                color();
                for(int i = 0; i < 4; i++){
                    Drawf.tri(e.x, e.y, 2f, 15f * e.fout(), i*90);
                }

                Drawf.light(e.x, e.y, circleRad * 1.6f, e.color, e.fout());
            });

}
