package TechE.content.exo.unit;

import TechE.content.exo.others.ExoSounds;
import TechE.content.exo.others.ExoFx;
import mindustry.ai.types.MissileAI;
import mindustry.entities.Effect;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.entities.pattern.ShootHelix;
import mindustry.entities.pattern.ShootMulti;
import mindustry.entities.pattern.ShootPattern;
import mindustry.type.unit.ErekirUnitType;
import TechE.content.exo.graphics.*;
import arc.graphics.*;
import arc.math.*;
import mindustry.ai.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.MissileUnitType;
import mindustry.type.weapons.*;
import mindustry.content.*;
import TechE.content.units.ModAbility.AEnergyFieldAbility;
public class ExoUnit {
    public static UnitType rhea,nemesis,magnapinna,bigThoriumMissile=new BigThoriumMissile();
    public static StatusEffect RheaBuff = new StatusEffect("rhea-buff"){{
        color = ExoPal.erekirPink;
        reloadMultiplier = 1.25f;
        damage = -0.2f;
        effectChance = 0.07f;
        effect = new WaveEffect(){{
            colorFrom = Color.valueOf("ffcbdd");
            colorTo = ExoPal.erekirPink;
            sizeFrom = 3;
            sizeTo = 0f;
            lifetime = 15f;
            strokeTo = 1;
            strokeFrom = 0f;
        }};
    }};
    public static void load(){
        rhea = new ErekirUnitType("rhea") {{
            constructor = UnitEntity::create;
            defaultCommand = UnitCommand.repairCommand;
            buildSpeed = 2.6f;
            rotateSpeed = 0.9f;
            buildBeamOffset = 30;
            shadowElevation = 2f;
            health = 76500f;
            lightRadius = 80;
            fogRadius = 50;
            outlineRadius = 6;
            armor = 16f;
            speed = 1.4f;
            accel = 0.04f;
            drag = 0.04f;
            flying = true;
            fallSpeed = 0.006f;
            crashDamageMultiplier = 20;
            hitSize = 100f;
            engineColor = Color.valueOf("fc81de");
            engineOffset = 58;
            engineSize = 9.5f;
            faceTarget = false;
            singleTarget = true;
            lowAltitude = true;
            abilities.add(new RegenAbility(){{
                //fully regen in 70 seconds
                percentAmount = 1f / (70f * 60f) * 100f;
            }});
            abilities.add(new StatusFieldAbility(RheaBuff, 110f, 100f, 260f){{
                parentizeEffects = true;
                effectY = 14.25f;
                activeEffect = new WaveEffect(){{
                    colorFrom = Color.valueOf("ffcbdd");
                    colorTo = ExoPal.erekirPink;
                    interp = Interp.circle;
                    sizeFrom = 0;
                    sizeTo = 160f;
                    lifetime = 95f;
                    strokeTo = 0;
                    strokeFrom = 8f;
                }};

                applyEffect = Fx.none;
            }});
            immunities.add(RheaBuff);
            parts.addAll(
                    new RegionPart("-glow"){{
                        mirror = false;
                        under = true;
                        layer = -1;
                        color = colorTo = Color.valueOf("8400ff");
                        blending = Blending.additive;
                        outline = false;
                        progress = PartProgress.warmup;
                    }},
                    new HoverPart(){{
                        color = ExoPal.erekirPink;
                        circles = 3;
                        stroke = 6;
                        sides = 360;
                        phase = 100;
                        radius = 48f;
                        mirror = false;
                        layer = Layer.effect;
                        y = 14.25f;
                    }}
            );
            setEnginesMirror(
                    new UnitEngine(28, -56, 5, 270),
                    new UnitEngine(-28, -56, 5, 270)
            );
            weapons.add(new Weapon("teche-rhea-energy-ball"){{
                x = 0f;
                y = 14.25f;
                shootCone = 25;
                mirror = false;
                reload = 700;
                shootSound = ExoSounds.bigLaserShoot;
                shootY = 0f;
                rotateSpeed = 5;
                rotate = true;
                minWarmup = 0.8f;
                smoothReloadSpeed = 0.15f;
                recoil = 0f;
                parts.addAll(
                        new ShapePart() {{
                            circle = true;
                            layer = 114;
                            radiusTo = 0;
                            radius = 9;
                            color = Color.white;
                            progress = PartProgress.reload;
                        }},
                        new ShapePart() {{
                            circle = true;
                            layer = 110;
                            radiusTo = 0;
                            radius = 13;
                            color = Color.valueOf("ffcbdd");
                            progress = PartProgress.reload;
                        }},
                        new ShapePart() {{
                            circle = true;
                            layer = 110;
                            radiusTo = 0;
                            radius = 16;
                            color = ExoPal.erekirPink;
                            progress = PartProgress.reload;
                        }},
                        new RegionPart("-pew") {{
                            mirror = false;
                            under = true;
                            growY = 12;
                            growX = 50;
                            y = 12;
                            x = 16;
                            layer = 110;
                            color = ExoPal.erekirPink;
                            colorTo = Color.valueOf("d370d300");
                            blending = Blending.additive;
                            outline = false;
                            progress = PartProgress.warmup;
                        }},
                        //main
                        new HaloPart() {{
                            tri = true;
                            y = 12;
                            radius = 7.6f;
                            layer = Layer.effect;
                            haloRadius = haloRadiusTo = 12;
                            strokeTo = 2;
                            shapes = 1;
                            triLength = 10;
                            triLengthTo = 43;
                            color = ExoPal.erekirPink;
                            progress = PartProgress.warmup;
                        }},
                        new HaloPart() {{
                            tri = true;
                            shapeRotation = 180;
                            y = 12;
                            radius = 7.6f;
                            layer = Layer.effect;
                            haloRadius = haloRadiusTo = 12;
                            strokeTo = 2;
                            shapes = 1;
                            triLength = triLengthTo = 4;
                            color = ExoPal.erekirPink;
                            progress = PartProgress.warmup;
                        }},
                        //sides
                        new HaloPart() {{
                            tri = true;
                            y = 12;
                            radius = 5.6f;
                            layer = Layer.effect;
                            haloRotation = 33;
                            haloRadius = haloRadiusTo = 12;
                            strokeTo = 2;
                            shapes = 1;
                            triLength = 6;
                            triLengthTo = 23;
                            color = ExoPal.erekirPink;
                            progress = PartProgress.warmup;
                        }},
                        new HaloPart() {{
                            tri = true;
                            y = 12;
                            radius = 5.6f;
                            layer = Layer.effect;
                            haloRotation = 33;
                            shapeRotation = 180;
                            haloRadius = haloRadiusTo = 12;
                            strokeTo = 2;
                            shapes = 1;
                            triLength = triLengthTo = 4;
                            color = ExoPal.erekirPink;
                            progress = PartProgress.warmup;
                        }},
                        //sides
                        new HaloPart() {{
                            tri = true;
                            y = 12;
                            radius = 5.6f;
                            layer = Layer.effect;
                            haloRotation = -33;
                            haloRadius = haloRadiusTo = 12;
                            strokeTo = 2;
                            shapes = 1;
                            triLength = 6;
                            triLengthTo = 23;
                            color = ExoPal.erekirPink;
                            progress = PartProgress.warmup;
                        }},
                        new HaloPart() {{
                            tri = true;
                            y = 12;
                            radius = 5.6f;
                            layer = Layer.effect;
                            haloRotation = 33;
                            shapeRotation = 180;
                            haloRadius = haloRadiusTo = 12;
                            strokeTo = 2;
                            shapes = 1;
                            triLength = 4;
                            triLengthTo = 4;
                            color = ExoPal.erekirPink;
                            progress = PartProgress.warmup;
                        }},
                        //sides2
                        new HaloPart() {{
                            tri = true;
                            y = 12;
                            radius = 4.3f;
                            layer = Layer.effect;
                            haloRotation = -60;
                            haloRadius = haloRadiusTo = 12;
                            strokeTo = 2;
                            shapes = 1;
                            triLength = 0;
                            triLengthTo = 10;
                            color = ExoPal.erekirPink;
                            progress = PartProgress.warmup;
                        }},
                        new HaloPart() {{
                            tri = true;
                            y = 12;
                            radius = 4.3f;
                            layer = Layer.effect;
                            haloRotation = -60;
                            shapeRotation = 180;
                            haloRadius = haloRadiusTo = 12;
                            strokeTo = 2;
                            shapes = 1;
                            triLength = 0;
                            triLengthTo = 10;
                            color = ExoPal.erekirPink;
                            progress = PartProgress.warmup;
                        }},
                        //sides2
                        new HaloPart() {{
                            tri = true;
                            y = 12;
                            radius = 4.3f;
                            layer = Layer.effect;
                            haloRotation = 60;
                            haloRadius = haloRadiusTo = 12;
                            strokeTo = 2;
                            shapes = 1;
                            triLength = 0;
                            triLengthTo = 10;
                            color = ExoPal.erekirPink;
                            progress = PartProgress.warmup;
                        }},
                        new HaloPart() {{
                            tri = true;
                            y = 12;
                            radius = 4.3f;
                            layer = Layer.effect;
                            haloRotation = 60;
                            shapeRotation = 180;
                            haloRadius = haloRadiusTo = 12;
                            strokeTo = 2;
                            shapes = 1;
                            triLength = 0;
                            triLengthTo = 10;
                            color = ExoPal.erekirPink;
                            progress = PartProgress.warmup;
                        }}
                );
                bullet = new BasicBulletType(4f, 70){{
                    parts.addAll(
                            new ShapePart() {{
                                circle = true;
                                layer = 114;
                                radiusTo = 0;
                                radius = 9;
                                color = Color.white;
                                progress = PartProgress.reload;
                            }},
                            new ShapePart() {{
                                circle = true;
                                layer = Layer.effect;
                                radiusTo = 0;
                                radius = 13;
                                color = Color.valueOf("ffcbdd");
                                progress = PartProgress.reload;
                            }},
                            new ShapePart() {{
                                circle = true;
                                layer = Layer.effect;
                                radiusTo = 0;
                                radius = 16;
                                color = ExoPal.erekirPink;
                                progress = PartProgress.reload;
                            }},
                            new HoverPart(){{
                                color = ExoPal.erekirPink;
                                circles = 3;
                                phase = 100;
                                radius = 48f;
                                mirror = false;
                                layer = Layer.effect;
                            }}
                    );
                    lightRadius = 40;
                    hitColor = trailColor = ExoPal.erekirPink;
                    trailLength = 26;
                    trailWidth = 9;
                    scaleLife = true;
                    collidesGround = collidesTiles = collidesAir = false;
                    width = height = 0;
                    despawnEffect = new MultiEffect(ExoFx.explodeyscathe, ExoFx.colorBomb,
                            new ParticleEffect(){{
                                lightOpacity = 0.5f;
                                line = true;
                                particles = 35;
                                length = 275;
                                offset = 40;
                                strokeFrom = 5;
                                strokeTo = 0;
                                lifetime = 160;
                                lenFrom = 20;
                                lenTo = 10;
                                lightColor = colorFrom = Color.valueOf("ffcbdd");
                                colorTo = ExoPal.erekirPink;
                            }});
                    hitSound = Sounds.explosion;
                    splashDamage = 1276;
                    splashDamageRadius = 100;
                    scaledSplashDamage = true;
                    trailSinScl = 2;
                    trailSinMag = 1.2f;
                    trailChance = 0.3f;
                    trailParam = 3.5f;
                    lifetime = 160f;
                    shootEffect = ExoFx.explodeyscathe;
                    bulletInterval = 3;
                    intervalRandomSpread = 60;
                    intervalBullet = new BasicBulletType(7, 40){{
                        parts.addAll(
                                new ShapePart() {{
                                    circle = true;
                                    layer = 114;
                                    radiusTo = radius = 1;
                                    color = Color.white;
                                }},
                                new ShapePart() {{
                                    circle = true;
                                    layer = Layer.effect;
                                    radiusTo = radius = 2;
                                    color = Color.valueOf("ffcbdd");
                                }},
                                new ShapePart() {{
                                    circle = true;
                                    layer = Layer.effect;
                                    radiusTo = radius = 3;
                                    color = ExoPal.erekirPink;
                                }}
                        );
                        hitSound = Sounds.explosion;
                        hitColor = trailColor = ExoPal.erekirPink;
                        pierceBuilding = pierce = true;
                        pierceCap = 2;
                        homingPower = 0.002f;
                        homingRange = 100;
                        lifetime = 60;
                        width = height = 1;
                        drag = 0.029f;
                        weaveMag = 40;
                        weaveScale = -5;
                        trailSinScl = 2;
                        trailSinMag = 0.8f;
                        trailLength = 9;
                        trailWidth = 2;
                        despawnHit = true;
                        hitEffect = ExoFx.colorBombSmaller;
                    }};
                    fragRandomSpread = 0f;
                    fragBullets = 1;
                    fragBullet = new BasicBulletType(0f, 5){{
                        parts.addAll(
                                new ShapePart() {{
                                    circle = true;
                                    layer = 114;
                                    radiusTo = 0;
                                    radius = 10;
                                    color = Color.white;
                                }},
                                new ShapePart() {{
                                    circle = true;
                                    layer = Layer.effect;
                                    radiusTo = 0;
                                    radius = 17;
                                    color = Color.valueOf("ffcbdd");
                                }},
                                new ShapePart() {{
                                    circle = true;
                                    layer = Layer.effect;
                                    radiusTo = 0;
                                    radius = 20;
                                    color = ExoPal.erekirPink;
                                }},
                                new HoverPart(){{
                                    color = ExoPal.erekirPink;
                                    circles = 2;
                                    phase = 100;
                                    radius = 68f;
                                    mirror = false;
                                    layer = Layer.effect;
                                }},
                                new HoverPart(){{
                                    color = ExoPal.erekirPink;
                                    circles = 2;
                                    phase = 100;
                                    radius = 88f;
                                    mirror = false;
                                    layer = Layer.effect;
                                }}
                        );
                        hitSound = Sounds.explosion;
                        width = height = 0f;
                        lifetime = 240f;
                        hitSize = 4f;
                        splashDamage = 576;
                        splashDamageRadius = 60;
                        scaledSplashDamage = true;
                        backColor = hitColor = trailColor = ExoPal.erekirPink;
                        collidesGround = collidesTiles = collidesAir = false;
                        trailWidth = 2f;
                        trailLength = 6;
                        hitEffect = despawnEffect = new MultiEffect(
                                new ParticleEffect(){{
                                    lightOpacity = 0.5f;
                                    line = true;
                                    particles = 35;
                                    length = 275;
                                    offset = 40;
                                    strokeFrom = 5;
                                    strokeTo = 0;
                                    lifetime = 60;
                                    lenFrom = 20;
                                    lenTo = 10;
                                    lightColor = colorFrom = Color.valueOf("ffcbdd");
                                    colorTo = ExoPal.erekirPink;
                                }},
                                new WaveEffect(){{
                                    colorFrom = Color.valueOf("ffcbdd");
                                    colorTo = ExoPal.erekirPink;
                                    sizeFrom = 70;
                                    sizeTo = 0f;
                                    lifetime = 55f;
                                    strokeTo = 19;
                                    strokeFrom = 0f;
                                }},
                                new WaveEffect(){{
                                    colorFrom = Color.valueOf("ffcbdd");
                                    colorTo = ExoPal.erekirPink;
                                    sizeFrom = 50;
                                    sizeTo = 0f;
                                    lifetime = 55f;
                                    strokeTo = 7;
                                    strokeFrom = 0f;
                                }}
                        );
                        bulletInterval = 1;
                        intervalRandomSpread = 360;
                        intervalBullet = new BasicBulletType(5, 16){{
                            parts.addAll(
                                    new ShapePart() {{
                                        circle = true;
                                        layer = 114;
                                        radiusTo = radius = 2;
                                        color = Color.white;
                                    }},
                                    new ShapePart() {{
                                        circle = true;
                                        layer = Layer.effect;
                                        radiusTo = radius = 3;
                                        color = Color.valueOf("ffcbdd");
                                    }},
                                    new ShapePart() {{
                                        circle = true;
                                        layer = Layer.effect;
                                        radiusTo = radius = 4;
                                        color = ExoPal.erekirPink;
                                    }}
                            );
                            hitSound = Sounds.explosionAfflict;
                            hitColor = trailColor = ExoPal.erekirPink;
                            weaveMag = 30;
                            weaveScale = 14;
                            weaveRandom = false;
                            pierceBuilding = pierce = true;
                            hitEffect = new MultiEffect(
                                    new ParticleEffect(){{
                                        lightOpacity = 0.5f;
                                        line = true;
                                        particles = 10;
                                        length = 45;
                                        offset = 40;
                                        strokeFrom = 2;
                                        strokeTo = 0;
                                        lifetime = 30;
                                        lenFrom = 7;
                                        lenTo = 3;
                                        lightColor = colorFrom = Color.valueOf("ffcbdd");
                                        colorTo = ExoPal.erekirPink;
                                    }},
                                    new WaveEffect(){{
                                        colorFrom = Color.valueOf("ffcbdd");
                                        colorTo = ExoPal.erekirPink;
                                        interp = Interp.circleOut;
                                        sizeFrom = 0;
                                        sizeTo = 20f;
                                        lifetime = 35f;
                                        strokeTo = 0;
                                        strokeFrom = 2f;
                                    }}
                            );
                            pierceCap = 5;
                            lifetime = 65;
                            width = height = 1;
                            drag = -0.017f;
                            trailLength = 9;
                            trailWidth = 2;
                            despawnHit = true;
                            spawnBullets.add(new BasicBulletType(7, 9){{
                                parts.addAll(
                                        new ShapePart() {{
                                            circle = true;
                                            layer = 114;
                                            radiusTo = radius = 1;
                                            color = Color.white;
                                        }},
                                        new ShapePart() {{
                                            circle = true;
                                            layer = Layer.effect;
                                            radiusTo = radius = 2;
                                            color = Color.valueOf("ffcbdd");
                                        }},
                                        new ShapePart() {{
                                            circle = true;
                                            layer = Layer.effect;
                                            radiusTo = radius = 3;
                                            color = ExoPal.erekirPink;
                                        }}
                                );
                                fragRandomSpread = 0f;
                                fragSpread = 120;
                                fragBullets = 3;
                                fragBullet = new ShrapnelBulletType(){{
                                    damage = 15f;
                                    width = 10f;
                                    serrations = 0;
                                    length = 3f;
                                    lifetime = 25;
                                    toColor = fromColor = ExoPal.erekirPink;
                                }};
                                hitSound = Sounds.explosionAfflict;
                                drag = -0.0015f;
                                width = height = 0f;
                                lifetime = 42f;
                                pierceCap = 5;
                                pierce = true;
                                pierceBuilding = true;
                                hitColor = trailColor = ExoPal.erekirPink;
                                trailWidth = 2f;
                                trailLength = 9;
                                weaveScale = 5;
                                weaveMag = 30;
                                despawnHit = true;
                                hitEffect = ExoFx.colorBombSmaller;
                            }});
                            fragRandomSpread = 0f;
                            fragBullets = 1;
                            fragBullet = new LaserBulletType(){{
                                damage = 20f;
                                sideWidth = 0f;
                                width = 25f;
                                length = 80f;
                                hitColor = ExoPal.erekirPink;
                                colors = new Color[]{ExoPal.erekirPink.cpy().a(0.4f), ExoPal.erekirPink, Color.white};
                            }};
                        }};
                    }};
                }};
            }});
            weapons.add(new RepairBeamWeapon("teche-rhea-mount") {{
                mirror = rotate = true;
                alternate = controllable = false;
                rotateSpeed = 5;
                y = 0;
                x = 61.75f;
                laserColor = healColor = ExoPal.erekirPink;
                targetBuildings = true;
                targetUnits = false;
                beamWidth = 1f;
                repairSpeed = 0.9f;
                fractionRepairSpeed = 0.03f;
                shootY = 10;
                shootCone = 15;
                pulseRadius = 10;
                pulseStroke = 3;
                bullet = new BulletType(){{
                    shootEffect = new WaveEffect(){{
                        colorFrom = Color.valueOf("ffcbdd");
                        colorTo = ExoPal.erekirPink;
                        sizeFrom = 0;
                        sizeTo = 7f;
                        lifetime = 25f;
                        strokeTo = 4;
                        strokeFrom = 0f;
                    }};
                    maxRange = 260f;
                }};
            }});
            weapons.add(new RepairBeamWeapon("teche-rhea-mount") {{
                mirror = rotate = true;
                alternate = controllable = false;
                rotateSpeed = 5;
                y = 14;
                x = 41.75f;
                laserColor = healColor = ExoPal.erekirPink;
                targetBuildings = true;
                targetUnits = false;
                beamWidth = 1f;
                repairSpeed = 0.9f;
                fractionRepairSpeed = 0.03f;
                shootY = 10;
                shootCone = 15;
                pulseRadius = 10;
                pulseStroke = 3;
                bullet = new BulletType(){{
                    shootEffect = new WaveEffect(){{
                        colorFrom = Color.valueOf("ffcbdd");
                        colorTo = ExoPal.erekirPink;
                        sizeFrom = 0;
                        sizeTo = 7f;
                        lifetime = 25f;
                        strokeTo = 4;
                        strokeFrom = 0f;
                    }};
                    maxRange = 260f;
                }};
            }});
            weapons.add(new RepairBeamWeapon("teche-rhea-mount") {{
                mirror = rotate = true;
                alternate = controllable = false;
                rotateSpeed = 5;
                y = 35;
                x = 27f;
                laserColor = healColor = ExoPal.erekirPink;
                targetBuildings = true;
                targetUnits = false;
                beamWidth = 1f;
                repairSpeed = 0.9f;
                fractionRepairSpeed = 0.03f;
                shootY = 10;
                shootCone = 15;
                pulseRadius = 10;
                pulseStroke = 3;
                bullet = new BulletType(){{
                    shootEffect = new WaveEffect(){{
                        colorFrom = Color.valueOf("ffcbdd");
                        colorTo = ExoPal.erekirPink;
                        sizeFrom = 0;
                        sizeTo = 7f;
                        lifetime = 25f;
                        strokeTo = 4;
                        strokeFrom = 0f;
                    }};
                    maxRange = 260f;
                }};
            }});
        }};
        nemesis=new Nemesis();
        magnapinna=new Magnapinna();
    }
    public static class BigThoriumMissile extends MissileUnitType {

        public BigThoriumMissile() {
            super("big-thorium-missile");

            // Basic properties
            constructor = UnitEntity::create;
            controller = u -> new MissileAI();
            flying = true;
            lowAltitude = true;
            hidden = true;
            physics = false;
            // Stats
            speed = 5.6f;
            rotateSpeed = 0.9f;
            health = 800f;
            hitSize = 7f;
            maxRange = 10f;

            // Visual properties
            engineLayer = 110f;
            outlineColor = Color.valueOf("36363c");
            engineColor = Color.valueOf("9681fb");
            trailColor = Color.valueOf("9681fb");
            missileAccelTime = 45f;
            engineOffset = 19f;
            engineSize = 5.3f;
            trailLength = 22;
            drawCell = true;

            initializeWeapons();
            initializeAbilities();
        }

        private void initializeWeapons() {
            weapons.add(
                    new Weapon("bbom boom"){{
                        shootCone = 360f;
                        shootOnDeath = true;
                        controllable = true;

                        bullet = new ArtilleryBulletType(){{
                            killShooter = true;
                            lifetime=60f;
                            hitEffect = new MultiEffect(
                                    Fx.explosion,
                                    new ExplosionEffect(){{
                                        lifetime = 40f;
                                        waveStroke = 4f;
                                        waveLife = 12f;
                                        waveColor = Color.white;
                                        sparkColor = Pal.suppress;
                                        smokeColor = Pal.sapBulletBack;
                                        waveRad = 35f;
                                        smokeSize = 3.5f;
                                        smokes = 8;
                                        smokeSizeBase = 0f;
                                        sparks = 12;
                                        sparkRad = 45f;
                                        sparkLen = 5f;
                                        sparkStroke = 1.8f;
                                    }}
                            );

                            shootEffect = new ExplosionEffect(){{
                                lifetime = 30f;
                                waveStroke = 3f;
                                waveLife = 6f;
                                waveColor = Color.valueOf("9c50ff");
                                sparkColor = Color.valueOf("bf92f9");
                                smokeColor = Color.valueOf("9681fb");
                                waveRad = 25f;
                                smokeSize = 2.5f;
                                smokes = 5;
                                smokeSizeBase = 0f;
                                sparks = 6;
                                sparkRad = 30f;
                                sparkLen = 4f;
                                sparkStroke = 1.5f;
                            }};

                            // 添加尾迹效果，类似 disrupt 的粒子风格
                            trailEffect = new ParticleEffect(){{
                                particles = 3;
                                length = 15f;
                                baseLength = 5f;
                                sizeFrom = 2f;
                                sizeTo = 0f;
                                colorFrom = Color.valueOf("9c50ff");
                                colorTo = Color.valueOf("bf92f9");
                                interp = Interp.pow3Out;
                            }};

                            instantDisappear = true;
                            hitSoundVolume = 4f;
                            hitSound = Sounds.explosion;
                            buildingDamageMultiplier = 0.8f;
                            splashDamage = 300f;
                            suppressionRange = 90f;
                            splashDamageRadius = 100f;
                            status = StatusEffects.blasted;
                            statusDuration = 100f;

                            // 添加 disrupt 风格的视觉特性
                            backColor = Color.valueOf("9681fb");
                            frontColor = Color.valueOf("bf92f9");
                            hitColor = Pal.suppress;
                        }};
                    }}
            );
        }

        private void initializeAbilities() {
            abilities.add(
                    new MoveEffectAbility(){{
                        effect = new MultiEffect(new ParticleEffect(){{
                            particles = 3;
                            sizeFrom = 2.5f;
                            sizeTo = 0f;
                            length = 25f;
                            baseLength = 8f;
                            lifetime = 40f;
                            lightOpacity = 0.3f;
                            interp = Interp.pow3Out;
                            sizeInterp = Interp.pow5In;
                            colorFrom = Color.valueOf("9c50ff");
                            colorTo = Color.valueOf("bf92f9");
                            cone = 20f;
                            strokeFrom = 1.5f;
                            strokeTo = 0f;
                        }},new ParticleEffect(){{
                            particles = 2;
                            sizeFrom = 1.5f;
                            sizeTo = 0f;
                            length = 15f;
                            baseLength = 5f;
                            lifetime = 30f;
                            interp = Interp.circleOut;
                            colorFrom = Color.valueOf("ffffff");
                            colorTo = Color.valueOf("9681fb");
                            cone = 25f;
                            strokeFrom = 1f;
                            strokeTo = 0f;
                        }});
                        interval = 2f;
                        y = -0.25f;
                        rotateEffect = true;
                        rotation = 0f;

                    }}
            );

        }
    }
    // Nemesis.java
    public static class Nemesis extends UnitType {

        public Nemesis() {
            super("T-nemesis");

            // Basic properties
            constructor = UnitEntity::create;
            flying = true;
            lowAltitude = true;
            singleTarget = true;
            faceTarget = true;

            // Stats
            speed = 0.88f;
            accel = 0.4f;
            drag = 0.4f;
            rotateSpeed = 0.7f;
            health = 46500f;
            armor = 20f;
            hitSize = 80f;
            range = 200f;

            // Visual properties
            lightRadius = 120f;
            lightColor = Color.valueOf("a393fe");
            fogRadius = 100f;
            shadowElevation = 5f;
            fallSpeed = 0.006f;
            crashDamageMultiplier = 10f;
            engineSize = 0f;

            // 添加单位拖尾效果
            trailLength = 15;
            trailColor = Color.valueOf("9681fb80");
            trailScl = 2f;

            // 添加引擎粒子效果
            engineColor = Color.valueOf("9681fb");
            engineLayer = 109f;

            // Immunities
            immunities.add(StatusEffects.blasted);
            immunities.add(StatusEffects.wet);
            immunities.add(StatusEffects.tarred);

            initializeParts();
            initializeWeapons();
            initializeAbilities();
        }

        private void initializeParts() {
            parts.addAll(
                    new RegionPart() {{
                        layer = 110;
                        colorTo = Color.valueOf("9681fb");
                        color = Color.valueOf("9681fb");
                        outline = false;
                        suffix = "-glow1";
                        progress = PartProgress.warmup;
                        moveY = -1f;
                    }},
                    new ShapePart() {{
                        y = -16f;
                        x = 29f;
                        hollow = true;
                        circle = true;
                        layer = 110;
                        mirror = true;
                        color = Color.valueOf("a393feff");
                        stroke = 1.2f;
                        strokeTo = 1.2f;
                        radiusTo = 8f;
                        radius = 8f;
                        rotation = 45f;
                    }},
                    new ShapePart() {{
                        y = -16f;
                        x = 29f;
                        hollow = true;
                        circle = true;
                        layer = 110;
                        mirror = true;
                        color = Color.valueOf("a393feff");
                        stroke = 0.5f;
                        strokeTo = 0.5f;
                        radiusTo = 5f;
                        radius = 5f;
                        rotation = -30f;
                    }},
                    new HaloPart() {{
                        y = -16f;
                        x = 29f;
                        radius = 2f;
                        tri = true;
                        layer = 110;
                        color = Color.valueOf("a393feff");
                        mirror = true;
                        haloRotateSpeed = 2.7f;
                        haloRadius = 7f;
                        haloRadiusTo = 7f;
                        stroke = 3f;
                        strokeTo = 3f;
                        shapes = 8;
                        triLengthTo = 10f;
                        triLength = 10f;
                    }},
                    new HaloPart() {{
                        y = -16f;
                        x = 29f;
                        radius = 2.6f;
                        tri = true;
                        layer = 110;
                        color = Color.valueOf("bf92f9");
                        mirror = true;
                        haloRotateSpeed = -1.2f;
                        haloRadius = 6f;
                        haloRadiusTo = 6f;
                        stroke = 2f;
                        strokeTo = 2f;
                        shapes = 6;
                        triLengthTo = 4f;
                        triLength = 4f;
                    }},
                    new HaloPart() {{
                        y = -16f;
                        x = 29f;
                        radius = 3f;
                        tri = true;
                        layer = 110;
                        color = Color.valueOf("ffffff");
                        mirror = true;
                        haloRotateSpeed = 1.5f;
                        haloRadius = 10f;
                        haloRadiusTo = 10f;
                        stroke = 1.5f;
                        strokeTo = 1.5f;
                        shapes = 12;
                        triLengthTo = 2f;
                        triLength = 2f;
                    }}
            );
        }

        private void initializeWeapons() {
            weapons.addAll(
                    new Weapon("teche-engine"){{
                        x = 33f;
                        y = -39f;
                        mirror = true;
                        alternate = false;
                        top = true;
                        display = false;
                        rotate = false;
                        baseRotation = 180f;
                        shootY = 10f;
                        shootSound = Sounds.none;
                        alwaysShooting = true;
                        alwaysContinuous = true;
                        continuous = true;
                        parentizeEffects = true;

                        bullet = new ContinuousFlameBulletType(){{
                            colors = new Color[]{
                                    Color.valueOf("9681fb50"),
                                    Color.valueOf("9681fb"),
                                    Color.valueOf("bf92f9"),
                                    Color.valueOf("ffffff")
                            };
                            damage = 4f;
                            layer = 110f;
                            intervalBullets = 2;
                            intervalRandomSpread = 1f;
                            bulletInterval = 2.7f;

                            intervalBullet = new BulletType(){{
                                despawnHit = true;
                                despawnEffect = new MultiEffect();
                                shootEffect = new MultiEffect();
                                hitEffect = new MultiEffect(){{
                                    followParent = true;
                                    rotWithParent = true;
                                    effects = new Effect[]{
                                            new ParticleEffect(){{
                                                followParent = true;
                                                rotWithParent = true;
                                                line = true;
                                                layer = 108f;
                                                particles = 1;
                                                lifetime = 31f;
                                                interp = Interp.circleOut;
                                                length = 35f;
                                                baseLength = 8f;
                                                cone = 20f;
                                                strokeFrom = 2f;
                                                lenFrom = 10f;
                                                lenTo = 0f;
                                                colorFrom = Color.valueOf("9681fb");
                                                colorTo = Color.valueOf("9681fb");
                                            }}
                                    };
                                }};
                                lifetime = 0f;
                                instantDisappear = true;
                            }};

                            drawFlare = false;
                            collides = false;
                            width = 4f;
                            divisions = 20;
                            length = 27f;
                        }};
                    }},

                    new Weapon("teche-engine2"){{
                        x = 53f;
                        y = -35f;
                        mirror = true;
                        alternate = false;
                        top = true;
                        display = false;
                        rotate = false;
                        baseRotation = 224f;
                        shootY = 14f;
                        shootSound = Sounds.none;
                        alwaysShooting = true;
                        alwaysContinuous = true;
                        continuous = true;
                        parentizeEffects = true;

                        bullet = new ContinuousFlameBulletType(){{
                            colors = new Color[]{
                                    Color.valueOf("9681fb50"),
                                    Color.valueOf("9681fb"),
                                    Color.valueOf("bf92f9"),
                                    Color.valueOf("ffffff")
                            };
                            damage = 4f;
                            layer = 110f;
                            intervalBullets = 2;
                            intervalRandomSpread = 1f;
                            bulletInterval = 2.7f;

                            intervalBullet = new BulletType(){{
                                despawnHit = true;
                                despawnEffect = new MultiEffect();
                                shootEffect = new MultiEffect();
                                hitEffect = new MultiEffect(){{
                                    followParent = true;
                                    rotWithParent = true;
                                    effects = new Effect[]{
                                            new ParticleEffect(){{
                                                followParent = true;
                                                rotWithParent = true;
                                                line = true;
                                                layer = 108f;
                                                particles = 1;
                                                lifetime = 31f;
                                                interp = Interp.circleOut;
                                                length = 45f;
                                                baseLength = 8f;
                                                cone = 25f;
                                                strokeFrom = 2f;
                                                lenFrom = 6f;
                                                lenTo = 0f;
                                                colorFrom = Color.valueOf("9681fb");
                                                colorTo = Color.valueOf("9681fb");
                                            }}
                                    };
                                }};
                                lifetime = 0f;
                                instantDisappear = true;
                            }};

                            drawFlare = false;
                            collides = false;
                            width = 5.3f;
                            divisions = 20;
                            length = 20f;
                        }};
                    }},

                    new Weapon("teche-nemesis-missile-weapon") {{
                        shootCone = 300f;
                        mirror = true;
                        alternate = false;
                        top = true;
                        shoot.shots = 3;
                        shoot.shotDelay = 5f;
                        baseRotation = -21f;
                        x = 60f;
                        y = 8f;
                        rotate = false;
                        layerOffset = -1f;
                        reload = 240f;
                        recoil = 4.5f;
                        inaccuracy = 25f;

                        parts.addAll(
                                new RegionPart() {{
                                    suffix = "-big-missile";
                                    colorTo = Color.valueOf("ffffff00");
                                    color = Color.valueOf("ffffff");
                                    layerOffset = -1f;
                                    under = true;
                                    outlineLayerOffset = 1f;
                                    moveY = -5f;
                                    progress = PartProgress.warmup;
                                }},
                                new RegionPart() {{
                                    suffix = "-missile-glow";
                                    color = Color.valueOf("a393fe");
                                    layer = 109;
                                    progress = PartProgress.warmup;
                                }}
                        );

                        shootSound = Sounds.acceleratorLaunch;

                        bullet = new BasicBulletType() {{
                            spawnUnit = bigThoriumMissile;
                            shootEffect = new MultiEffect(Fx.lightningShoot);
                            smokeEffect = Fx.missileTrailSmoke;
                            trailEffect = Fx.missileTrail;
                            trailInterval = 2f;
                            trailLength = 15;
                            trailColor = Color.valueOf("a393fe80");
                        }};
                    }},

                    new Weapon("teche-nemesis-weapon") {{
                        top = true;
                        mirror = false;
                        layerOffset = -0.0001f;
                        y = 0f;
                        x = 0f;
                        cooldownTime = 60f;
                        rotate = false;
                        shootY = 0f;
                        shootX = 0f;
                        reload = 40f;
                        recoils = 4;
                        recoil = 1.5f;
                        shake = 3f;
                        ejectEffect = Fx.smokeCloud;
                        shootSound = Sounds.explosionArtilleryShockBig;

                        parts.addAll(
                                new RegionPart() {{
                                    suffix = "-barrel-1";
                                    under = true;
                                    outlineLayerOffset = -0.001f;
                                    recoilIndex = 1;
                                    progress = PartProgress.recoil;
                                    moveY = -5f;
                                    color = Color.valueOf("a393fe");
                                }},
                                new RegionPart() {{
                                    suffix = "-barrel-2";
                                    under = true;
                                    outlineLayerOffset = -0.001f;
                                    recoilIndex = 0;
                                    progress = PartProgress.recoil;
                                    moveY = -5f;
                                    color = Color.valueOf("a393fe");
                                }},
                                new RegionPart() {{
                                    suffix = "-barrel-3";
                                    under = true;
                                    outlineLayerOffset = -0.001f;
                                    recoilIndex = 3;
                                    progress = PartProgress.recoil;
                                    moveY = -5f;
                                    color = Color.valueOf("a393fe");
                                }},
                                new RegionPart() {{
                                    suffix = "-barrel-4";
                                    under = true;
                                    outlineLayerOffset = -0.001f;
                                    recoilIndex = 2;
                                    progress = PartProgress.recoil;
                                    moveY = -5f;
                                    color = Color.valueOf("a393fe");
                                }}
                        );

                        shoot = new ShootMulti() {{
                            source = new ShootBarrel() {{
                                barrels = new float[]{
                                        42f, 65f, 0f,
                                        -42f, 65f, 0f,
                                        24f, 64f, 0f,
                                        -24f, 64f, 0f
                                };
                            }};
                            dest = new ShootPattern[]{
                                    new ShootHelix() {{
                                        scl = 3.5f;
                                        mag = 3f;
                                        shots = 3;
                                    }}
                            };
                        }};

                        bullet = new BasicBulletType() {{
                            sprite = "missile-large";
                            hitEffect = new MultiEffect(Fx.sapExplosion, Fx.circleColorSpark, Fx.shockwave, Fx.lightning);
                            intervalBullets = 4;
                            intervalRandomSpread = 60f;

                            intervalBullet = new LightningBulletType() {{
                                lightningColor = Color.valueOf("a393feff");
                                buildingDamageMultiplier = 1f;
                                damage = 80f;
                                lightningLength = 12;
                                lightningLengthRand = 2;
                                lightningColor = Color.valueOf("a393fe");

                            }};

                            hitColor = Color.valueOf("9681fb");
                            lifetime = 40f;
                            speed = 16f;
                            status = StatusEffects.sapped;
                            statusDuration = 90f;
                            width = 18f;
                            height = 30f;
                            despawnHit = true;
                            shootEffect = new MultiEffect(Fx.explosion, Fx.colorSparkBig, Fx.lightningShoot);
                            shrinkY = 0f;
                            shrinkX = 0f;
                            damage = 75f;
                            trailLength = 15;
                            trailWidth = 6f;
                            trailColor = Color.valueOf("a393feff");
                            backColor = Color.valueOf("a393feff");
                            frontColor = Color.valueOf("ffffff");
                            lightColor = Color.valueOf("a393fe");
                            lightRadius = 15f;
                            lightOpacity = 0.8f;

                            // 添加自定义更新效果
                        }};
                    }}
            );
        }

        private void initializeAbilities() {
            abilities.addAll(
                    new SuppressionFieldAbility() {{
                        reload = 30f;
                        range = 180f;
                        layer = 109f;
                        y = -16f;
                        x = 29f;
                        particles = 25;
                        particleSize = 4f;
                        rotateScl = 10f;
                        active = true;
                        color = Color.valueOf("a393fe");
                    }},
                    new SuppressionFieldAbility() {{
                        reload = 30f;
                        range = 180f;
                        layer = 109f;
                        y = -16f;
                        x = -29f;
                        particles = 25;
                        particleSize = 4f;
                        rotateScl = 10f;
                        active = true;
                        color = Color.valueOf("a393fe");
                    }},

                    new AEnergyFieldAbility() {{
                        color = Color.valueOf("a393feff");
                        statusDuration = 120f;
                        status = StatusEffects.sapped;
                        maxTargets = 50;
                        damage = 35f;
                        y = 25.5f;
                        range = 320f;
                        sectors = 8;
                        rotateSpeed = 3f;
                        sectorRad = 0.15f;
                        effectRadius = 8f;
                        reload = 35f;
                    }}
            );
        }

        @Override
        public void setStats() {
            super.setStats();

        }

        @Override
        public void draw(Unit unit) {
            super.draw(unit);

            // 添加自定义光晕效果
            Drawf.light(unit.x, unit.y, lightRadius * 1.5f, lightColor, 0.6f);

            // 添加核心能量脉冲效果

            if (unit.isShooting()) {
                Drawf.light(unit.x, unit.y + 20f, 40f, Color.valueOf("ff5555"), 0.8f);
            }
        }


    }
    public static class Magnapinna extends UnitType {

        public Magnapinna() {
            super("magnapinna");

            // Basic properties
            constructor = UnitEntity::create;
            flying = false;
            lowAltitude = true;
            targetAir = true;
            canBoost = true;
            boostMultiplier = 1.0f;

            // Stats
            speed = 0.50f;
            accel = 0.18f;
            drag = 0.18f;
            rotateSpeed = 0.8f;
            health = 137200f;
            armor = 37f;
            hitSize = 77f;
            range = 300f;

            // Visual properties
            engineOffset = 51f;
            engineSize = 9f;
            trailScl = 5.6f;
            trailLength = 85;

            initializeWeapons();
            initializeAbilities();
        }

        private void initializeWeapons() {
            weapons.addAll(
                    new Weapon("teche-magnapinna-weapon"){{
                        y = 0f;
                        x = 29f;
                        top = true;
                        mirror = true;
                        alternate = false;
                        rotate = true;
                        rotateSpeed = 2f;
                        shootY = 1f;
                        inaccuracy = 10f;
                        reload = 237f;
                        recoil = 3f;
                        shake = 1f;
                        shootCone = 20f;
                        shootSound = Sounds.beamHeal;
                        continuous = true;
                        cooldownTime = 190f;

                        bullet = new ContinuousLaserBulletType(){{
                            damage = 125f;
                            length = 200f;
                            width = 6.5f;
                            collidesTeam = true;
                            healPercent = 0.7f;
                            hitEffect = Fx.hitMeltHeal;
                            drawSize = 300f;
                            lifetime = 480f;
                            shake = 1f;
                            status = StatusEffects.electrified;
                            statusDuration = 80f;
                            despawnEffect = Fx.smokeCloud;
                            smokeEffect = Fx.smokeCloud;
                            colors = new Color[]{
                                    Color.valueOf("98ffa9AA"),
                                    Color.valueOf("98ffa9DA"),
                                    Color.valueOf("98ffa9"),
                                    Color.valueOf("FFFFFFF")
                            };
                            shootEffect = Fx.hitEmpSpark;
                        }};
                    }},

                    new Weapon("teche-emp-emitter"){{
                        y = -15f;
                        x = 0f;
                        top = true;
                        shootY = 0f;
                        rotate = false;
                        flipSprite = true;
                        reload = 367f;
                        recoil = 0f;
                        shootStatusDuration = 100f;
                        shootStatus = StatusEffects.unmoving;
                        ignoreRotation = true;
                        ejectEffect = new MultiEffect();
                        shootSound = Sounds.shootLaser;

                        shoot = new ShootPattern(){{
                            firstShotDelay = 80f;
                        }};

                        bullet = new EmpBulletType(){{
                            shootEffect = Fx.greenLaserCharge;
                            chargeSound = Sounds.shootLaser;

                            despawnEffect = new MultiEffect(){{
                                effects = new Effect[]{
                                        new ParticleEffect(){{
                                            particles = 1;
                                            sizeFrom = 25f;
                                            sizeTo = 0f;
                                            length = 0f;
                                            lifetime = 45f;
                                            colorFrom = Color.valueOf("FFFFFFF");
                                            colorTo = Color.valueOf("FFFFFFF");
                                            cone = 360f;
                                        }},
                                        new ParticleEffect(){{
                                            particles = 1;
                                            sizeFrom = 25f;
                                            sizeTo = 0f;
                                            length = 0f;
                                            lifetime = 45f;
                                            colorFrom = Color.valueOf("98ffa9");
                                            colorTo = Color.valueOf("98ffa9");
                                            cone = 360f;
                                        }},
                                        new ParticleEffect(){{
                                            particles = 1;
                                            sizeFrom = 67f;
                                            sizeTo = 27f;
                                            length = 0f;
                                            spin = 6f;
                                            lifetime = 70f;
                                            region = "teche-emitter-effect";// 需要自定义纹理
                                            colorFrom = Color.valueOf("98ffa9");
                                            colorTo = Color.valueOf("98ffAA");
                                        }},
                                        new WaveEffect(){{
                                            lifetime = 20f;
                                            sizeFrom = 0f;
                                            sizeTo = 60f;
                                            strokeFrom = 6f;
                                            strokeTo = 0f;
                                            colorFrom = Color.valueOf("69CB87FF");
                                            colorTo = Color.valueOf("98ffa9");
                                        }},
                                        new WaveEffect(){{
                                            lifetime = 50f;
                                            sizeFrom = 330f;
                                            sizeTo = 330f;
                                            strokeFrom = 7f;
                                            strokeTo = 0f;
                                            colorFrom = Color.valueOf("98ffa9");
                                            colorTo = Color.valueOf("98ffa9");
                                        }}
                                };
                            }};

                            lightOpacity = 0f;
                            trailEffect = new MultiEffect();
                            lifetime = 1f;
                            splashDamage = 70f;
                            splashDamageRadius = 330f;
                            shrinkY = 0f;
                            shrinkX = 0f;
                            radius = 330f;
                            timeIncrease = 14.5f;
                            powerDamageScl = 5.5f;
                            powerSclDecrease = 0.55f;
                            unitDamageScl = 1.2f;
                            status = StatusEffects.electrified;
                            statusDuration = 100f;
                            speed = 0f;
                            width = 0.01f;
                            height = 0.01f;
                            fragBullets = 30;
                            fragAngle = 20f;
                            fragVelocityMin = 1f;
                            fragVelocityMax = 1f;

                            fragBullet = new LaserBulletType(){{
                                shootEffect = Fx.hitEmpSpark;
                                length = 300f;
                                width = 35f;
                                damage = 900f;
                                hitEffect = Fx.hitMeltHeal;
                                colors = new Color[]{
                                        Color.valueOf("98ffa9DA"),
                                        Color.valueOf("98ffa9"),
                                        Color.valueOf("FFFFFFF")
                                };
                                sideAngle = 25f;
                                sideLength = 0f;
                                sideWidth = 0f;
                            }};
                        }};
                    }},

                    new RepairBeamWeapon("magnapinna-heal"){{
                        mirror = true;
                        alternate = false;
                        rotateSpeed = 3f;
                        y = -35f;
                        x = 28f;
                        shootY = 8f;
                        reload = 1f;
                        recoil = 0f;
                        shake = 1f;
                        rotate = true;
                        shootSound = Sounds.shootLaser;
                        controllable = false;
                        autoTarget = true;
                        beamWidth = 0.9f;
                        pulseRadius = 6f;
                        pulseStroke = 2f;
                        repairSpeed = 7.75f;

                        bullet = new BulletType(){{
                            shootEffect = Fx.hitEmpSpark;
                            maxRange = 60f;
                        }};
                    }},

                    new PointDefenseWeapon("e-point"){{
                        controllable = false;
                        autoTarget = true;
                        targetInterval = 5f;
                        targetSwitchInterval = 5f;
                        y = -15f;
                        rotate = true;
                        shootY = 4f;
                        reload = 2f;
                        recoil = 1f;
                        shootSound = Sounds.shootLaser;

                        bullet = new BulletType(){{
                            hitEffect = Fx.pointHit;
                            smokeEffect = Fx.pointHit;
                            maxRange = 100f;
                            damage = 40f;
                        }};
                    }},

                    new RepairBeamWeapon("magnapinna-heal"){{
                        mirror = true;
                        alternate = false;
                        rotateSpeed = 3f;
                        y = 35f;
                        x = 28f;
                        shootY = 8f;
                        reload = 1f;
                        recoil = 0f;
                        shake = 1f;
                        rotate = true;
                        shootSound = Sounds.shootLaser;
                        controllable = false;
                        autoTarget = true;
                        beamWidth = 0.9f;
                        pulseRadius = 6f;
                        pulseStroke = 2f;
                        repairSpeed = 7.75f;

                        bullet = new BulletType(){{
                            shootEffect = Fx.hitEmpSpark;
                            maxRange = 60f;
                        }};
                    }}
            );
        }

        private void initializeAbilities() {
            abilities.add(
                    new AEnergyFieldAbility(){{
                        statusDuration = 100f;
                        status = StatusEffects.electrified;
                        color = Color.valueOf("98ffa9ff");
                        maxTargets = 50;
                        y = -15f;
                        damage = 85f;
                        healPercent = 2f;
                        range = 175f;
                        sectors = 5;
                        rotateSpeed = 3f;
                        hitBuildings = true;
                        sectorRad = 0.14f;
                        effectRadius = 0f;
                        reload = 70f;
                    }}
            );
        }
    }
        }
