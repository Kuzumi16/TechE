package TechE.content.blocks.production;


import TechE.content.liquid.ModLiquid;
import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.payloads.Constructor;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.SolidPump;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import mindustry.world.draw.DrawWeave;

import static mindustry.type.ItemStack.with;

public class ModBlock1 {
    public static SolidPump csj,ecsj,efcsj;
    public static Block phaseFactory,assmbler;
    public static class SGY extends GenericCrafter {
        public SGY(String name) {
            super(name);
            this.requirements(Category.crafting,with(Items.lead,75,Items.metaglass,55,Items.copper,40,Items.silicon,125,
                    Items.titanium,125,Items.graphite,125));
            this.size=4;
            this.craftEffect= Fx.arcShieldBreak;
            this.hasPower=true;
            this.hasItems=true;
            this.hasLiquids=true;
            this.craftTime=15f;
            this.update=true;
            this.consumeItems(with(Items.coal,1,Items.titanium,1));
            this.consumeLiquid(Liquids.water,0.1f);
            this.consumePower(4f);
            this.liquidCapacity=40;
            this.outputItem=new ItemStack(Items.plastanium,4);
            this.alwaysUnlocked=true;
        }
        }
        public static void load(Boolean isLoad){
            if(isLoad){
                csj=new SolidPump("csj"){{
                    requirements(Category.production,with());
                    hasPower=false;
                    rotateSpeed=1.8f;
                    pumpAmount=1f;
                    result=Liquids.water;
                    health=300;
                    alwaysUnlocked=true;
                    liquidCapacity=300;
                    size=1;
                }};
                ecsj=new SolidPump("ecsj"){{
                    hasPower=false;
                    requirements(Category.production,with(Items.beryllium,20));
                    rotateSpeed=1.8f;
                    pumpAmount=1f;
                    result=Liquids.water;
                    health=300;
                    alwaysUnlocked=true;
                    liquidCapacity=300;
                    size=1;
                }};
                efcsj=new SolidPump("efcsj"){{
                    requirements(Category.production,with(Items.beryllium,20,Items.graphite,20));
                    rotateSpeed=1.8f;
                    pumpAmount=2f;
                    result=Liquids.arkycite;
                    health=200;
                    alwaysUnlocked=true;
                    liquidCapacity=1000;
                    size=2;
                }};
            }
            phaseFactory=new GenericCrafter("phaseFactory"){{
                size=4;
                hasPower=true;
                hasItems=true;
                hasLiquids=true;
                requirements(Category.crafting,with(
                   Items.silicon,100,Items.thorium,50,Items.surgeAlloy,60,
                   Items.metaglass,60,Items.plastanium,70
                ));
                craftEffect=Fx.smeltsmoke;
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawWeave(), new DrawDefault());
                consumePower(12f);
                consumeItems(with(Items.sand,10,Items.thorium,1));
                consumeLiquid(ModLiquid.neutronFluid,2f);
                outputItem=new ItemStack(Items.phaseFabric,16);
                craftTime=25f;
                itemCapacity=60;
                liquidCapacity=60f;
                alwaysUnlocked=true;
                ambientSound = Sounds.loopTech;
                ambientSoundVolume = 0.02f;
            }};
            assmbler=new Constructor("assmbler"){{
                requirements(Category.units, with(Items.silicon, 50,Items.graphite,100));
                regionSuffix = "-dark";
                hasPower = true;
                buildSpeed = 0.8f;
                consumePower(2.5f);
                size = 3;
                //TODO expand this list
                filter = Seq.with(Blocks.tungstenWallLarge, Blocks.berylliumWallLarge, Blocks.carbideWallLarge, Blocks.reinforcedSurgeWallLarge, Blocks.reinforcedLiquidContainer, Blocks.reinforcedContainer, Blocks.beamNode,
                        Blocks.lancer,Blocks.surgeWallLarge);
                alwaysUnlocked=true;
            }};
        }
    }

