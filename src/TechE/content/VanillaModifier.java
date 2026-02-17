package TechE.content;

import arc.graphics.Color;

import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.ExplosionEffect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.draw.DrawTurret;


public class VanillaModifier {
    public static Block RealAfflict;
    public static void load(){
        RealAfflict = new PowerTurret("RealAfflict") {
            {
                this.requirements(Category.turret, ItemStack.with(new Object[]{Items.surgeAlloy, 125, Items.silicon, 200, Items.graphite, 250, Items.oxide, 40}));
                this.buildCostMultiplier = 1.5F;
                this.shootType = new BasicBulletType() {
                    {
                        this.shootEffect = new MultiEffect(new Effect[]{Fx.shootTitan, new WaveEffect() {
                            {
                                this.colorTo = Pal.surge;
                                this.sizeTo = 26.0F;
                                this.lifetime = 14.0F;
                                this.strokeFrom = 4.0F;
                            }
                        }});
                        this.smokeEffect = Fx.shootSmokeTitan;
                        this.hitColor = Pal.surge;
                        this.sprite = "large-orb";
                        this.trailEffect = Fx.missileTrail;
                        this.trailInterval = 3.0F;
                        this.trailParam = 4.0F;
                        this.pierceCap = 2;
                        this.buildingDamageMultiplier = 0.5F;
                        this.fragOnHit = false;
                        this.speed = 5.0F;
                        this.damage = 230.0F;
                        this.lifetime = 80.0F;
                        this.width = this.height = 16.0F;
                        this.backColor = Pal.surge;
                        this.frontColor = Color.white;
                        this.shrinkX = this.shrinkY = 0.0F;
                        this.trailColor = Pal.surge;
                        this.trailLength = 12;
                        this.trailWidth = 2.2F;
                        this.despawnEffect = this.hitEffect = new ExplosionEffect() {
                            {
                                this.waveColor = Pal.surge;
                                this.smokeColor = Color.gray;
                                this.sparkColor = Pal.sap;
                                this.waveStroke = 4.0F;
                                this.waveRad = 40.0F;
                            }
                        };
                        this.despawnSound = Sounds.explosionAfflict;
                        shootSound = Sounds.shootAfflict;
                        this.fragBullet = this.intervalBullet = new BasicBulletType(3.0F, 45.0F) {
                            {
                                this.width = 9.0F;
                                this.hitSize = 5.0F;
                                this.height = 20.0F;
                                this.pierceCap = 3;
                                this.lifetime = 28.0F;
                                this.pierceBuilding = true;
                                this.hitColor = this.backColor = this.trailColor = Pal.surge;
                                this.frontColor = Color.white;
                                this.trailWidth = 2.1F;
                                this.trailLength = 5;
                                this.hitEffect = this.despawnEffect = new WaveEffect() {
                                    {
                                        this.colorFrom = this.colorTo = Pal.surge;
                                        this.sizeTo = 4.0F;
                                        this.strokeFrom = 4.0F;
                                        this.lifetime = 10.0F;
                                    }
                                };
                                this.buildingDamageMultiplier = 0.3F;
                                this.homingPower = 0.1F;
                            }
                        };
                        this.bulletInterval = 3.0F;
                        this.intervalRandomSpread = 20.0F;
                        this.intervalBullets = 4;
                        this.intervalAngle = 180.0F;
                        this.intervalSpread = 300.0F;
                        this.fragBullets = 20;
                        this.fragVelocityMin = 0.5F;
                        this.fragVelocityMax = 1.5F;
                        this.fragLifeMin = 0.5F;
                    }
                };
                this.drawer = new DrawTurret("reinforced-") {
                    {
                        this.parts.add(new RegionPart("-blade") {
                            {
                                this.progress = PartProgress.recoil;
                                this.heatColor = Color.valueOf("ff6214");
                                this.mirror = true;
                                this.under = true;
                                this.moveX = 2.0F;
                                this.moveY = -1.0F;
                                this.moveRot = -7.0F;
                            }
                        }, new RegionPart("-blade-glow") {
                            {
                                this.progress = PartProgress.recoil;
                                this.heatProgress = PartProgress.warmup;
                                this.heatColor = Color.valueOf("ff6214");
                                this.drawRegion = false;
                                this.mirror = true;
                                this.under = true;
                                this.moveX = 2.0F;
                                this.moveY = -1.0F;
                                this.moveRot = -7.0F;
                            }
                        });
                    }
                };
                this.consumePower(5.0F);
                this.heatRequirement = 15.0F;
                this.maxHeatEfficiency = 3F;
                this.newTargetInterval = 40.0F;
                this.inaccuracy = 1.0F;
                this.shake = 2.0F;
                this.shootY = 4.0F;
                this.outlineColor = Pal.darkOutline;
                this.size = 4;
                this.envEnabled |= 2;
                this.reload = 100.0F;
                this.cooldownTime = this.reload;
                this.recoil = 3.0F;
                this.range = 360.0F;
                this.shootCone = 20.0F;
                this.scaledHealth = 220.0F;
                this.rotateSpeed = 1.5F;
                this.researchCostMultiplier = 0.04F;
                this.limitRange(9.0F);
                this.coolant=this.consumeCoolant(0.2F);
            }
        };
    }

}

