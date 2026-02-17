package TechE.content.units;


import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.entities.abilities.EnergyFieldAbility;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.entities.bullet.RailBulletType;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.PowerAmmoType;
import TechE.content.units.ModAbility.AForceFieldAbility;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

public class ModAttackUnit {
    public static UnitType destroyer, assister;
    public static final Effect customSpark = new Effect(20f, e -> {
        Draw.color(Pal.heal, Color.white, e.fin());
        Lines.stroke(e.fout() * 1.5f);

        // 绘制主要粒子
        float size = e.fout() * 4f;
        Fill.circle(e.x, e.y, size);

        // 添加拖尾效果
        for(int i = 0; i < 3; i++){
            float progress = i / 3f;
            float offset = e.fin() * 8f * progress;
            float alpha = (1f - progress) * e.fout();

            Draw.alpha(alpha);
            Fill.circle(
                    e.x - Angles.trnsx(e.rotation, offset),
                    e.y - Angles.trnsy(e.rotation, offset),
                    size * (1f - progress)
            );
        }

        Draw.reset();
    });
    public static void load(){
        destroyer =new destroyer();
        assister = new assister();
    }
    public static class destroyer extends UnitType{
        public static Seq<Ability> DAblities;
        public destroyer() {
            super("destroyer");
            DAblities=this.abilities;
            this.constructor= LegsUnit::create;
            this.hitSize = 30f;
            this.health = 58000f;
            this.armor = 12f;
            this.stepShake = 1.8f;
            this.rotateSpeed = 1.2f;
            this.drownTimeMultiplier = 6f;
            this.legCount = 6;
            this.legLength = 20;
            this.legBaseOffset = 11f;
            this.legMoveSpace = 2f;
            this.legForwardScl = 0.58f;
            this.hovering = true;
            this.shadowElevation = 0.2f;
            this.ammoType = new PowerAmmoType(4000);
            this.groundLayer = Layer.legUnit;
            this.speed = 0.2f;
            this.alwaysUnlocked=true;
            this.drawShields = false;
            this.abilities.add(new RepairFieldAbility(400, 60 * 3, 140f));
            this.abilities.add(new EnergyFieldAbility(100,60*2f,140f));
            this.abilities.add(new Ability(){
                final float range = 140f; // 修复范围
                float rotation = 0f;
                float hexagonPulse = 0f; // 六边形脉冲动画
                @Override
                public void update(Unit unit){
                    rotation += Time.delta * 0.8f;
                    hexagonPulse = (hexagonPulse + Time.delta * 0.03f) % 1f;
                    // ... 治疗逻辑 ...
                }
                @Override
                public void draw(Unit unit){
                    Draw.z(Layer.effect);

                    // 计算六边形参数（与修复场范围相关）
                    float hexagonSize = range * 0.7f; // 六边形大小与修复场范围成正比
                    int sides = 6; // 六边形
                    float pulseFactor = 1f + hexagonPulse * 0.2f; // 脉冲效果

                    // 绘制主六边形激光轮廓
                    Draw.color(Pal.heal, 0.8f);
                    Lines.stroke(3f * pulseFactor);

                    for(int i = 0; i < sides; i++){
                        float angle1 = rotation + i * 360f / sides;
                        float angle2 = rotation + (i + 1) * 360f / sides;

                        float x1 = unit.x + Angles.trnsx(angle1, hexagonSize);
                        float y1 = unit.y + Angles.trnsy(angle1, hexagonSize);
                        float x2 = unit.x + Angles.trnsx(angle2, hexagonSize);
                        float y2 = unit.y + Angles.trnsy(angle2, hexagonSize);

                        Lines.line(x1, y1, x2, y2);
                    }

                    // 绘制内部的次级六边形
                    Draw.color(Pal.heal, 0.6f);
                    Lines.stroke(2f);
                    float innerSize = hexagonSize * 0.6f;

                    for(int i = 0; i < sides; i++){
                        float angle1 = -rotation * 1.5f + i * 360f / sides;
                        float angle2 = -rotation * 1.5f + (i + 1) * 360f / sides;

                        float x1 = unit.x + Angles.trnsx(angle1, innerSize);
                        float y1 = unit.y + Angles.trnsy(angle1, innerSize);
                        float x2 = unit.x + Angles.trnsx(angle2, innerSize);
                        float y2 = unit.y + Angles.trnsy(angle2, innerSize);

                        Lines.line(x1, y1, x2, y2);
                    }

                    // 绘制六边形顶点光点
                    for(int i = 0; i < sides; i++){
                        float angle = rotation + i * 360f / sides;
                        float pointX = unit.x + Angles.trnsx(angle, hexagonSize);
                        float pointY = unit.y + Angles.trnsy(angle, hexagonSize);

                        Draw.color(Pal.heal, 0.9f);
                        Fill.circle(pointX, pointY, 3f * pulseFactor);

                        // 顶点激光效果
                        Draw.color(Pal.heal, 0.4f);
                        Lines.stroke(1f);
                        Lines.line(unit.x, unit.y, pointX, pointY);
                    }

                    // 添加死星特色的激光能量流动效果
                    Draw.color(Pal.heal, 0.3f);
                    for(int i = 0; i < sides; i++){
                        float progress = (hexagonPulse + i * 0.166f) % 1f;
                        float currentAngle = rotation + i * 360f / sides;
                        float nextAngle = rotation + ((i + 1) % sides) * 360f / sides;

                        float moveAngle = currentAngle + (nextAngle - currentAngle) * progress;
                        float moveDist = hexagonSize * 0.9f;

                        float energyX = unit.x + Angles.trnsx(moveAngle, moveDist);
                        float energyY = unit.y + Angles.trnsy(moveAngle, moveDist);

                        Fill.circle(energyX, energyY, 2f);
                    }

                    Draw.reset();
                }
            });
            this.weapons.add(new Weapon("destroyer-laser"){{
                shootSound = Sounds.shootCorvus;
                chargeSound = Sounds.chargeCorvus;
                soundPitchMin = 1f;
                top = false;
                mirror = false;
                shake = 14f;
                shootY = 5f;
                x = y = 0;
                reload = 320f;
                recoil = 0f;
                heatColor=Color.red;
                cooldownTime = 350f;
                shootStatusDuration = 60f * 2f;
                shootStatus = StatusEffects.unmoving;
                shoot.firstShotDelay = Fx.greenLaserCharge.lifetime;
                parentizeEffects = true;
                // 在修复场能力的 draw 方法中添加六边形特效

                bullet = new LaserBulletType(){{
                    length = 600f;
                    damage = 1000f;
                    width = 110f;

                    lifetime = 70f;

                    lightningSpacing = 35f;
                    lightningLength = 8;
                    lightningDelay = 1.1f;
                    lightningLengthRand = 15;
                    lightningDamage = 50;
                    lightningAngleRand = 40f;
                    largeHit = true;
                    lightColor = lightningColor = Pal.heal;

                    chargeEffect = Fx.greenLaserCharge;

                    healPercent = 25f;
                    collidesTeam = true;

                    sideWidth = 0.8f;
                    sideLength = 200f;
                    sideAngle = 30f;
                    colors = new Color[]{Pal.heal.cpy().a(0.4f), Pal.heal, Color.white};
                    hitEffect = Fx.massiveExplosion; // 使用一个更大的爆炸效果
                    hitSize = 20f;
                }};
            }});
            for(float wx : new Float[]{-20f, 20f}) {
                this.weapons.add(new Weapon(("teche-destroyer-side-weapon")) {{
                    shake = 4;
                    rotate=true;
                    rotateSpeed=0.6f;
                    rotationLimit=240f;
                    shootY = 13;
                    reload = 120f;
                    mirror = false;
                    top = false;
                    x=wx;
                    y=-3f;
                    shootSound = Sounds.shootCorvus;
                    cooldownTime = 90;
                    heatColor=Color.red;
                    bullet = new RailBulletType() {{
                        shootEffect = new Effect(24, e -> {
                            e.scaled(10, b -> {
                                Draw.color(Color.white, Pal.heal, b.fin());
                                Lines.stroke(b.fout() * 3 + 0.2f);
                                Lines.circle(b.x, b.y, b.fin() * 50);
                            });
                            Draw.color(Pal.heal);
                            for (int i : Mathf.signs) {
                                Drawf.tri(e.x, e.y, 13 * e.fout(), 85, e.rotation + 90 * i);
                            }
                        });
                        trailWidth=40f;
                        length = 700;
                        pointEffectSpace = 30;
                        pierceEffect = Fx.railHit;
                        pointEffect = new Effect(16, e -> {
                            Draw.color(Pal.heal);
                            for (int i : Mathf.signs) {
                                Drawf.tri(e.x, e.y, 22 * e.fout(), 25, e.rotation + 90 + 90 * i);
                            }
                        });
                        hitEffect = Fx.massiveExplosion;
                        smokeEffect = Fx.shootBig2;
                        damage = 200f;
                        lifetime = 10;
                        pierceCap = 3;
                    }};
                }}
                );

            }
        }
    }
    public static class assister extends UnitType{

        public assister() {
            super("assister");
            this.constructor= LegsUnit::create;
            this.hitSize = 30f;
            this.health = 28000f;
            this.armor = 12f;
            this.stepShake = 1.8f;
            this.rotateSpeed = 1.2f;
            this.drownTimeMultiplier = 6f;
            this.legCount = 6;
            this.legLength = 12;
            this.legBaseOffset = 11f;
            this.legMoveSpace = 2f;
            this.legForwardScl = 0.58f;
            this.hovering = true;
            this.shadowElevation = 0.2f;
            this.ammoType = new PowerAmmoType(4000);
            this.groundLayer = Layer.legUnit;
            this.speed = 0.5f;
            this.alwaysUnlocked=true;
            this.drawShields = false;
            this.weapons.add(new Weapon("teche-assister-laser"){
                {
                    x = 0;
                    y = -2;
                    shootSound = Sounds.shootCorvus;
                    soundPitchMin = 0.9f;
                    soundPitchMax = 1.2f;
                    top = false;
                    mirror = false;
                    shake = 20f;
                    shootY = 5f;
                    reload = 70f; // 更快的装填速度
                    heatColor = Color.valueOf("ff7f38"); // 更亮的发热颜色
                    cooldownTime = 80f; // 更快的冷却
                    shootStatusDuration = 20f * 2f;
                    parentizeEffects = true;
                    // 添加新特效

                    bullet = new LaserBulletType(){{
                        damage = 50f; // 增加伤害
                        buildingDamageMultiplier = 1.8f; // 对建筑额外伤害
                        splashDamage = 60f; // 溅射伤害
                        splashDamageRadius = 40f; // 溅射半径
                        hitEffect = Fx.hitLancer; // 命中特效
                        despawnEffect = Fx.none;
                        hitSize = 8f;
                        drawSize = 420f;
                        lifetime = 60f;
                        recoil = 0.8f;
                        sideAngle = 20f;
                        sideWidth = 2f;
                        sideLength = 90f;
                        width = 35f; // 更宽的激光w
                        healPercent = 15f; // 增加治疗百分比
                        collidesTeam = true;
                        length = 380f; // 更长的激光
                        hitColor = Pal.heal;
                        // 更丰富的颜色渐变
                        colors = new Color[]{Pal.heal.cpy().a(0.4f), Pal.heal, Color.white};
                        // 添加光晕效果
                        lightColor = Color.valueOf("ff7f5a");
                        lightRadius = 15f;
                        // 添加持续伤害效果
                        status = StatusEffects.electrified;
                        statusDuration = 60f;
                        // 添加穿透能力
                        pierce = true;
                        pierceCap = 3;
                        pierceDamageFactor = 0.7f;

                    }
                        @Override
                        public void draw(Bullet b){
                            super.draw(b); // 先绘制原始激光

                            // 添加粒子散发效果
                            Draw.z(Layer.effect + 0.1f); // 在激光上方绘制

                            // 沿激光长度生成粒子
                            int particleCount = (int)(length / 15f); // 根据长度决定粒子数量
                            for(int i = 0; i < particleCount; i++){
                                float progress = i / (float)particleCount;
                                float dist = progress * length;

                                // 计算粒子位置
                                float px = b.x + Angles.trnsx(b.rotation(), dist);
                                float py = b.y + Angles.trnsy(b.rotation(), dist);

                                // 添加随机偏移
                                float offsetX = Mathf.range(8f);
                                float offsetY = Mathf.range(8f);

                                // 绘制粒子
                                Draw.color(Pal.heal, Mathf.random(0.3f, 0.8f));
                                Fill.circle(px + offsetX, py + offsetY, Mathf.random(1f, 2.5f));
                            }

                            Draw.reset();
                        }

                        // 重写 update 方法添加动态粒子
                        @Override
                        public void update(Bullet b){
                            super.update(b);

                                // 手动绘制粒子效果
                            if(Mathf.chanceDelta(0.4f)){
                                    float dist = Mathf.random(length);
                                    float px = b.x + Angles.trnsx(b.rotation(), dist);
                                    float py = b.y + Angles.trnsy(b.rotation(), dist);

                                    // 创建自定义粒子效果
                                    Time.run(0f, () -> {
                                        for(int i = 0; i < 2; i++){
                                            float angle = Mathf.random(360f);
                                            float speed = Mathf.random(1f, 3f);
                                            float life = Mathf.random(20f, 40f);

                                            // 手动模拟粒子
                                            Effect e = new Effect(life, ef -> {
                                                Draw.color(Pal.heal, ef.fout());
                                                float moveDist = speed * ef.fin();
                                                Fill.circle(
                                                        px + Angles.trnsx(angle, moveDist),
                                                        py + Angles.trnsy(angle, moveDist),
                                                        ef.fout() * 2f
                                                );
                                            });
                                            e.at(px, py);
                                        }
                                    });
                                }

                        }

            };
        }




            });
            // 在死星的定义中添加能力

            this.abilities.add(new EnergyFieldAbility(20f, 60f * 2f, 120f),
                    new Ability() {
                        final float buffRadius = 120f;

                        @Override
                        public void update(Unit unit) {
                            super.update(unit);
                            Units.nearby(unit.team, unit.x, unit.y, buffRadius, other -> {
                                other.apply(StatusEffects.overclock);
                            });
                        }
                    },
                    new AForceFieldAbility(140f,4f,10000f,14,6,0f));

            for(float wx:new Float[]{25f,-25f}){
                this.weapons.add(new Weapon("teche-assister-side-laser"){{
                    x = wx;
                    y = 0f;
                    shootSound = Sounds.shootLaser;
                    soundPitchMin = 0.9f;
                    soundPitchMax = 1.2f;
                    rotate = true;
                    rotateSpeed = 1.8f;
                    rotationLimit=180f;
                    top = false;
                    mirror = false;
                    shake = 20f;
                    shootY = 5f;
                    reload = 45f; // 更快的装填速度
                    recoil = 0.01f; // 轻微后坐力增加真实感
                    heatColor = Color.valueOf("ff7f38"); // 更亮的发热颜色
                    cooldownTime = 50f; // 更快的冷却
                    shootStatusDuration = 20f * 2f;
                    parentizeEffects = true;
                    ejectEffect = Fx.casing4;
                    // 添加新特效
                    bullet=new LaserBulletType(){{
                        damage = 20f;
                        recoil = 0f;
                        sideAngle = 45f;
                        sideWidth = 1f;
                        sideLength = 70f;
                        healPercent = 10f;
                        collidesTeam = true;
                        length = 135f;
                        colors = new Color[]{Pal.heal.cpy().a(0.4f), Pal.heal, Color.white};
                    }};
                }});
            }
}

        @Override
        public void draw(Unit unit) {
            super.draw(unit);
            // 绘制能量光环效果
            if (unit.isShooting() || unit.moving()) {
                Draw.z(Layer.flyingUnit + 0.1f); // 在单位上方绘制
                Draw.color(Pal.heal, Color.valueOf("ff7f5a"), Mathf.absin(Time.time, 6f, 0.7f));
                Lines.stroke(1.5f * Mathf.absin(Time.time, 6f, 1f));
                Lines.circle(unit.x, unit.y, unit.hitSize() / 1.2f + Mathf.absin(Time.time, 5f, 3f));
                Draw.reset();
            }
        }

        @Override
        public void update(Unit unit) {
            super.update(unit);
            if(unit.moving() && Mathf.chanceDelta(0.3f)){
                float offset = unit.hitSize * 0.7f;
                float angle = unit.rotation() + Mathf.range(30f);
                float x = unit.x + Angles.trnsx(angle, offset);
                float y = unit.y + Angles.trnsy(angle, offset);

                // 创建能量粒子效果

                customSpark.at(x,y,unit.rotation());
            }

            // 攻击时产生更强的能量场
            if(unit.isShooting() && Mathf.chanceDelta(0.5f)){
                float spread = 360f * Mathf.random();
                for(int i = 0; i < 3; i++){
                    float angle = spread + i * 120f + Mathf.range(15f);
                    float len = unit.hitSize * (0.8f + Mathf.random(0.4f));
                    Effect customHitLancer = getCustomHitLancer(unit, angle, len);
                    customHitLancer.lifetime = 15f;
                }
            }
        }

        private static Effect getCustomHitLancer(Unit unit, float angle, float len) {
            float x = unit.x + Angles.trnsx(angle, len);
            float y = unit.y + Angles.trnsy(angle, len);

            // 创建能量爆发效果
            Effect customHitLancer=new Effect(15, e -> {
                color(Pal.heal);
                stroke(e.fout() * 1.5f);

                randLenVectors(e.id, 8, e.finpow() * 17f, (ux, uy) -> {
                    float ang = Mathf.angle(ux, uy);
                    lineAngle(e.x + ux, e.y + uy, ang, e.fout() * 4 + 1f);
                });
            });
            customHitLancer.at(x,y, angle);
            return customHitLancer;
        }
    }}
