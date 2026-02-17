package TechE.content.units;

import arc.Core;
import arc.audio.Sound;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;
import mindustry.ui.Bar;
import mindustry.world.meta.StatValues;

import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class ModAbility {
    public static class AForceFieldAbility extends mindustry.entities.abilities.Ability {
        final float range = 140f; // 修复范围
        final float arotation = 0f;
        float hexagonPulse = 0f; // 六边形脉冲动画
        float shieldHitTime = 0f; // 护盾被击时间
        final float shieldHitDuration = 10f; // 闪动持续时间
        float lastHitTime = 0f; // 上次触发时间
        final float hitCooldown = 0.1f; // 0.1秒冷却时间
        /** Shield radius. */
        public float radius = 60f;
        /** Shield regen speed in damage/tick. */
        public float regen = 0.1f;
        /** Maximum shield. */
        public float max = 200f;
        /** Cooldown after the shield is broken, in ticks. */
        public float cooldown = 60f * 5;
        /** Sides of shield polygon. */
        public int sides = 6;
        /** Rotation of shield. */
        public float rotation = 0f;
        final Effect bulletBlockEffect = new Effect(15f, e -> {
            Draw.color(Pal.heal, Color.white, e.fout());
            Lines.stroke(2f * e.fout());

            // 绘制收缩的六边形
            for(int i = 0; i < 6; i++) {
                float angle1 = e.rotation + i * 60f;
                float angle2 = e.rotation + (i + 1) * 60f;
                float size = 10f * e.fout(); // 从大到小收缩
                float x1 = e.x + Angles.trnsx(angle1, size);
                float y1 = e.y + Angles.trnsy(angle1, size);
                float x2 = e.x + Angles.trnsx(angle2, size);
                float y2 = e.y + Angles.trnsy(angle2, size);
                Lines.line(x1, y1, x2, y2);
            }

            // 中心爆炸效果
            Draw.alpha(e.fout() * 0.6f);
            Fill.circle(e.x, e.y, 6f * e.fin());
            Draw.reset();
        });
        /** State. */
        protected float radiusScale, alpha;
        protected boolean wasBroken = true;

        private static float realRad;
        private static Unit paramUnit;
        private static AForceFieldAbility paramField;
        private static final Cons<Bullet> shieldConsumer = trait -> {
            if(trait.team != paramUnit.team && trait.type.absorbable && Intersector.isInRegularPolygon(paramField.sides, paramUnit.x, paramUnit.y, realRad, paramField.rotation, trait.x(), trait.y()) && paramUnit.shield > 0){
                trait.absorb();
                Fx.absorb.at(trait);
                paramUnit.shield -= trait.type().shieldDamage(trait);
            }
        };
        public AForceFieldAbility(float radius, float regen, float max, float cooldown, int sides, float rotation){
            super();
            this.radius = radius;
            this.regen = regen;
            this.max = max;
            this.cooldown = cooldown;
            this.sides = sides;
            this.rotation = rotation;
        }

        @Override
        public void addStats(Table t){
            super.addStats(t);
            t.add(Core.bundle.format("bullet.range", Strings.autoFixed(radius / tilesize, 2)));
            t.row();
            t.add(abilityStat("shield", Strings.autoFixed(max, 2)));
            t.row();
            t.add(abilityStat("repairspeed", Strings.autoFixed(regen * 60f, 2)));
            t.row();
            t.add(abilityStat("cooldown", Strings.autoFixed(cooldown / 60f, 2)));
        }

        @Override
        public void update(Unit unit){
            if(unit.shield <= 0f && !wasBroken){
                unit.shield -= cooldown * regen;

                Fx.shieldBreak.at(unit.x, unit.y, radius, unit.type.shieldColor(unit), this);
            }

            wasBroken = unit.shield <= 0f;

            if(unit.shield < max){
                unit.shield += Time.delta * regen;
            }

            alpha = Math.max(alpha - Time.delta/10f, 0f);

            if(unit.shield > 0){
                radiusScale = Mathf.lerpDelta(radiusScale, 1f, 0.06f);
                paramUnit = unit;
                paramField = this;
                checkRadius(unit);

                Groups.bullet.intersect(unit.x - realRad, unit.y - realRad, realRad * 2f, realRad * 2f, shieldConsumer);
            }else{
                radiusScale = 0f;
            }

            /// /
            hexagonPulse = (hexagonPulse + Time.delta * 0.03f) % 1f;
            // 递减护盾被击时间
            if(shieldHitTime > 0) {
                shieldHitTime -= Time.delta;
            }

            // 更新冷却时间
            if(lastHitTime > 0) {
                lastHitTime -= Time.delta;
            }

            // 检测子弹碰撞（只有在冷却结束后才检测）
            if(lastHitTime <= 0) {
                boolean hitDetected = false;

                // 使用临时列表避免并发修改
                final Bullet[] firstBullet = {null};
                final float[] minDistance = {Float.MAX_VALUE};

                // 找到最近的一个子弹
                Groups.bullet.each(bullet -> {
                    if(bullet.team != unit.team && unit.dst(bullet) <= range * 1.1f) {
                        float distance = unit.dst(bullet);
                        if(distance < minDistance[0]) {
                            firstBullet[0] = bullet;
                            minDistance[0] = distance;
                        }
                    }
                });

                // 只处理最近的一个子弹
                if(firstBullet[0] != null) {
                    // 触发护盾被击效果
                    shieldHitTime = shieldHitDuration;

                    // 创建子弹阻挡特效
                    bulletBlockEffect.at(firstBullet[0].x, firstBullet[0].y, Mathf.random(360f));

                    // 播放音效（音量根据距离调整）
                    float volume = Mathf.clamp(1f - (minDistance[0] / (range * 1.1f)), 0.3f, 1f);
                    Sounds.shieldHit.at(firstBullet[0].x, firstBullet[0].y, volume * 0.7f);

                    // 移除子弹

                    // 设置冷却时间
                    lastHitTime = hitCooldown;
                    hitDetected = true;
                }

                // 如果没有检测到子弹，但护盾还在闪动，继续冷却
                if(!hitDetected && shieldHitTime > 0) {
                    lastHitTime = hitCooldown;
                }
            }
        }

        @Override
        public void death(Unit unit){

            //self-destructing units can have a shield on death
            if(unit.shield > 0f && !wasBroken){
                Fx.shieldBreak.at(unit.x, unit.y, radius, unit.type.shieldColor(unit), this);
            }
        }

        @Override
        public void draw(Unit unit){
            Draw.z(Layer.effect);
            Color sheildColor=wasBroken?Color.red:Color.white;
            // 计算闪动强度
            float hitIntensity = shieldHitTime > 0 ? (shieldHitTime / shieldHitDuration) : 0f;

            // 计算六边形参数
            float hexagonSize = range;
            int sides = 6;
            float pulseFactor = 1f + hexagonPulse * 0.2f + hitIntensity * 0.3f;
            // 绘制主六边形激光轮廓
            Draw.color(Pal.heal, sheildColor, 0.8f + hitIntensity * 0.4f);
            Lines.stroke((3f + hitIntensity * 2f) * pulseFactor);

            for(int i = 0; i < sides; i++){
                float angle1 = rotation + i * 360f / sides;
                float angle2 = rotation + (i + 1) * 360f / sides;

                float x1 = unit.x + Angles.trnsx(angle1, hexagonSize);
                float y1 = unit.y + Angles.trnsy(angle1, hexagonSize);
                float x2 = unit.x + Angles.trnsx(angle2, hexagonSize);
                float y2 = unit.y + Angles.trnsy(angle2, hexagonSize);

                Lines.line(x1, y1, x2, y2);
            }

            // 绘制六边形顶点光点
            for(int i = 0; i < sides; i++){
                float angle = rotation + i * 360f / sides;
                float pointX = unit.x + Angles.trnsx(angle, hexagonSize);
                float pointY = unit.y + Angles.trnsy(angle, hexagonSize);

                Draw.color(Pal.heal, sheildColor, 0.9f + hitIntensity * 0.3f);
                Fill.circle(pointX, pointY, (3f + hitIntensity * 1.5f) * pulseFactor);

                // 顶点激光效果
                Draw.color(Pal.heal, sheildColor, 0.4f + hitIntensity * 0.3f);
                Lines.stroke(1f + hitIntensity * 0.5f);
                Lines.line(unit.x, unit.y, pointX, pointY);
            }

            // 添加能量流动效果
            Draw.color(Pal.heal, sheildColor, 0.3f + hitIntensity * 0.2f);
            float flowSpeed = hitIntensity > 0 ? 0.05f : 0.03f;
            for(int i = 0; i < sides; i++){
                float progress = (hexagonPulse * (1f + hitIntensity) + i * 0.166f) % 1f;
                float currentAngle = rotation + i * 360f / sides;
                float nextAngle = rotation + ((i + 1) % sides) * 360f / sides;

                float moveAngle = currentAngle + (nextAngle - currentAngle) * progress;
                float moveDist = hexagonSize * 0.9f;

                float energyX = unit.x + Angles.trnsx(moveAngle, moveDist);
                float energyY = unit.y + Angles.trnsy(moveAngle, moveDist);

                Fill.circle(energyX, energyY, 2f + hitIntensity * 1f);
            }

            // 被击时添加额外的冲击波效果
            if(hitIntensity > 0) {
                Draw.color(Pal.heal, sheildColor, hitIntensity * 0.6f);
                Lines.stroke(hitIntensity * 2f);
                Lines.circle(unit.x, unit.y, range * (0.8f + hitIntensity * 0.4f));

                // 添加随机粒子（也受冷却限制）
                if(Mathf.chance(hitIntensity * 0.2f)) {
                    float angle = Mathf.random(360f);
                    float dist = Mathf.random(range);
                    float px = unit.x + Angles.trnsx(angle, dist);
                    float py = unit.y + Angles.trnsy(angle, dist);

                    Draw.color(Pal.heal, sheildColor, hitIntensity);
                    Fill.circle(px, py, hitIntensity * 3f);
                }
            }

            Draw.reset();
            checkRadius(unit);
        }

        @Override
        public void displayBars(Unit unit, Table bars){
            bars.add(new Bar("stat.shieldhealth", Pal.accent, () -> unit.shield / max)).row();
        }

        @Override
        public void created(Unit unit){
            unit.shield = max;
        }

        public void checkRadius(Unit unit){
            //timer2 is used to store radius scale as an effect
            realRad = radiusScale * radius;
        }
    }
    public static class AEnergyFieldAbility extends Ability{
        private static final Seq<Healthc> all = new Seq<>();

        public float damage = 1, reload = 100, range = 60;
        public Effect healEffect = Fx.heal, hitEffect = Fx.hitLaserBlast, damageEffect = Fx.chainLightning;
        public StatusEffect status = StatusEffects.electrified;
        public Sound shootSound = Sounds.shootArc;
        public float statusDuration = 60f * 6f;
        public float x, y;
        public boolean targetGround = true, targetAir = true, hitBuildings = true, hitUnits = true;
        public int maxTargets = 25;
        public float healPercent = 3f;
        /** Multiplies healing to units of the same type by this amount. */
        public float sameTypeHealMult = 1f;
        public boolean displayHeal = true;

        public float layer = Layer.bullet - 0.001f, blinkScl = 20f, blinkSize = 0.1f;
        public float effectRadius = 5f, sectorRad = 0.14f, rotateSpeed = 0.5f;
        public int sectors = 5;
        public Color color = Pal.heal;
        public boolean useAmmo = true;

        protected float timer, curStroke;
        protected boolean anyNearby = false;

        void EnergyFieldAbility(){}

        public void EnergyFieldAbility(float damage, float reload, float range){
            this.damage = damage;
            this.reload = reload;
            this.range = range;
        }

        @Override
        public void addStats(Table t){
            if(displayHeal){
                t.add(Core.bundle.get(getBundle() + ".healdescription")).wrap().width(descriptionWidth);
            }else{
                t.add(Core.bundle.get(getBundle() + ".description")).wrap().width(descriptionWidth);
            }
            t.row();

            t.add(Core.bundle.format("bullet.range", Strings.autoFixed(range / tilesize, 2)));
            t.row();
            t.add(abilityStat("firingrate", Strings.autoFixed(60f / reload, 2)));
            t.row();
            t.add(abilityStat("maxtargets", maxTargets));
            t.row();
            t.add(Core.bundle.format("bullet.damage", damage));
            if(status != StatusEffects.none){
                t.row();
                t.add((status.hasEmoji() ? status.emoji() : "") + "[stat]" + status.localizedName).with(l -> StatValues.withTooltip(l, status));
            }
            if(displayHeal){
                t.row();
                t.add(Core.bundle.format("bullet.healpercent", Strings.autoFixed(healPercent, 2)));
                t.row();
                t.add(abilityStat("sametypehealmultiplier", (sameTypeHealMult < 1f ? "[negstat]" : "") + Strings.autoFixed(sameTypeHealMult * 100f, 2)));
            }
        }

        @Override
        public void draw(Unit unit){
            super.draw(unit);

            Draw.z(layer);
            Draw.color(color);
            Tmp.v1.trns(unit.rotation - 90, x, y).add(unit.x, unit.y);
            float rx = Tmp.v1.x, ry = Tmp.v1.y;
            float orbRadius = effectRadius * (1f + Mathf.absin(blinkScl, blinkSize));

            Fill.circle(rx, ry, orbRadius);
            Draw.color();
            Fill.circle(rx, ry, orbRadius / 2f);

            Lines.stroke((0.7f + Mathf.absin(blinkScl, 0.7f)), color);

            for(int i = 0; i < sectors; i++){
                float rot = unit.rotation + i * 360f/sectors - Time.time * rotateSpeed;
                Lines.arc(rx, ry, orbRadius + 3f, sectorRad, rot);
            }

            Lines.stroke(Lines.getStroke() * curStroke);

            if(curStroke > 0){
                for(int i = 0; i < sectors; i++){
                    float rot = unit.rotation + i * 360f/sectors + Time.time * rotateSpeed;
                    Lines.arc(rx, ry, range, sectorRad, rot);
                }
            }

            Drawf.light(rx, ry, range * 1.5f, color, curStroke * 0.8f);

            Draw.reset();
        }

        @Override
        public void update(Unit unit){

            curStroke = Mathf.lerpDelta(curStroke, anyNearby ? 1 : 0, 0.09f);

            if((timer += Time.delta) >= reload && (!useAmmo || unit.ammo > 0 || !state.rules.unitAmmo)){
                Tmp.v1.trns(unit.rotation - 90, x, y).add(unit.x, unit.y);
                float rx = Tmp.v1.x, ry = Tmp.v1.y;
                anyNearby = false;

                all.clear();

                if(hitUnits){
                    Units.nearby(null, rx, ry, range, other -> {
                        if(other != unit && other.checkTarget(targetAir, targetGround) && other.targetable(unit.team) && (other.team != unit.team || other.damaged())){
                            all.add(other);
                        }
                    });
                }

                if(hitBuildings && targetGround){
                    Units.nearbyBuildings(rx, ry, range, b -> {
                        if((b.team != Team.derelict || state.rules.coreCapture) && ((b.team != unit.team && b.block.targetable) || b.damaged()) && !b.block.privileged){
                            all.add(b);
                        }
                    });
                }

                all.sort(h -> h.dst2(rx, ry));
                int len = Math.min(all.size, maxTargets);
                for(int i = 0; i < len; i++){
                    Healthc other = all.get(i);

                    //lightning gets absorbed by plastanium
                    var absorber = Damage.findAbsorber(unit.team, rx, ry, other.getX(), other.getY());
                    if(absorber != null){
                        other = absorber;
                    }

                    if(((Teamc)other).team() == unit.team){
                        if(other.damaged()){
                            anyNearby = true;
                            float healMult = (other instanceof Unit u && u.type == unit.type) ? sameTypeHealMult : 1f;
                            other.heal(healPercent / 100f * other.maxHealth() * healMult);
                            healEffect.at(other);
                            damageEffect.at(rx, ry, 0f, color, other);
                            hitEffect.at(rx, ry, unit.angleTo(other), color);

                            if(other instanceof Building b){
                                Fx.healBlockFull.at(b.x, b.y, 0f, color, b.block);
                            }
                        }
                    }else{
                        anyNearby = true;
                        if(other instanceof Building b){
                            b.damage(unit.team, damage * state.rules.unitDamage(unit.team));
                        }else{
                            other.damage(damage * state.rules.unitDamage(unit.team));
                        }
                        if(other instanceof Statusc s){
                            s.apply(status, statusDuration);
                        }
                        hitEffect.at(other.x(), other.y(), unit.angleTo(other), color);
                        damageEffect.at(rx, ry, 0f, color, other);
                        hitEffect.at(rx, ry, unit.angleTo(other), color);
                    }
                }

                if(anyNearby){
                    shootSound.at(unit);

                    if(useAmmo && state.rules.unitAmmo){
                        unit.ammo --;
                    }
                }

                timer = 0f;
            }
        }
    }

}
