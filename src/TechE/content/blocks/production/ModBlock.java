package TechE.content.blocks.production;


import TechE.content.items.ModItem;
import TechE.content.liquid.ModLiquid;
import TechE.content.util.EffectWrapper;
import TechE.content.util.TEEffect;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.Effect;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.BeamDrill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class ModBlock {
    public static Block advancedLaserDrill, largeAdvancedLaserDrill, molecularGlassFactory, nanoCompressor,
            carbohydratePlasticSteelFactory, thermalMeltAlloyFactory, neutronFluidMixer,
            largePyratiteMixer, largeBlastMixer, neutronZeroDefectSiliconFactory, Crusher,AmultlipleSteelFactory,
            waterGasStove;
    public static void load(){
        advancedLaserDrill =new BeamDrill("advanced-laser-drill"){{
            requirements(Category.production, with(Items.beryllium, 40));
            consumePower(0.15f);

            drillTime = 40f;
            tier = 3;
            size = 2;
            range = 7;
            fogRadius = 3;
            alwaysUnlocked=true;
            consumeLiquid(Liquids.hydrogen, 0.25f / 60f).boost();
        }};
        largeAdvancedLaserDrill=new BeamDrill("large-advanced-laser-drill"){{
            requirements(Category.production, with(Items.beryllium, 40,Items.silicon,80,Items.tungsten,100,Items.oxide,30));
            consumePower(0.3f);

            drillTime = 15f;
            tier = 5;
            size = 3;
            range = 9;
            fogRadius = 5;
            alwaysUnlocked=true;
            consumeLiquid(Liquids.nitrogen, 0.2f / 60f);
            consumeLiquid(Liquids.cyanogen, 0.25f / 60f).boost();
        }};
        molecularGlassFactory = new GenericCrafter("molecular-glass-factory"){{
            requirements(Category.crafting,with(Items.lead,100,Items.copper,100,Items.metaglass,20));
            size=2;
            craftEffect=Fx.arcShieldBreak;
            hasPower=true;
            hasLiquids=true;
            outputItem=new ItemStack(Items.metaglass,6);
            craftTime=80f;
            consumeLiquid(Liquids.water,0.2f);
            consumePower(1.5f);
            ambientSound = Sounds.loopSmelter;
            alwaysUnlocked=true;
            ambientSoundVolume = 0.07f;
            drawer=new DrawMulti(new DrawDefault(),new DrawArcSmelt());
        }};
        nanoCompressor =new GenericCrafter("nano-compressor"){{
            requirements(Category.crafting,with(Items.copper,140,Items.lead,120,Items.graphite,80));
            size=3;
            craftEffect=Fx.pulverizeMedium;
            hasPower=true;
            hasLiquids=true;
            hasItems=true;
            outputItem=new ItemStack(Items.graphite,10);
            craftTime=60f;
            consumeItems(with(Items.coal,4));
            consumePower(1f);
            consumeLiquid(Liquids.water,0.1f);
            alwaysUnlocked=true;


        }};
        carbohydratePlasticSteelFactory =new ModBlock1.SGY("carbohydrate-plastic-steel-factory");
        thermalMeltAlloyFactory =new GenericCrafter("thermal-melt-alloy-factory"){{
            requirements(Category.crafting,with(Items.copper,140,Items.lead,120,Items.graphite,80,Items.silicon,120,
                    Items.plastanium,200));
            size=5;
            craftTime=20f;
            hasPower=true;
            hasLiquids=true;
            hasItems=true;
            outputItem=new ItemStack(Items.surgeAlloy,15);
            consumePower(6f);
            consumeLiquid(Liquids.cryofluid,0.3f);
            consumeItems(with(Items.plastanium,1.5,Items.silicon,2.5f,Items.graphite,4));
            alwaysUnlocked=true;
            itemCapacity=100;
            liquidCapacity=50;
            craftEffect=Fx.burning;
        }};
        neutronFluidMixer =new GenericCrafter("neutron-fluid-mixer"){{
            size=2;
            alwaysUnlocked=true;
            liquidCapacity=100;
            itemCapacity=50;
            hasPower=true;
            hasLiquids=true;
            hasItems=true;
            requirements(Category.crafting,with(Items.graphite,60,Items.titanium,70,
                    Items.surgeAlloy,100,Items.plastanium,70));
            consumeLiquid(Liquids.cryofluid,0.3f);
            consumeItems(with(Items.titanium,1));
            consumePower(10f);
            craftTime=15f;
            outputLiquid= new LiquidStack(ModLiquid.neutronFluid,2.5f);
            liquidCapacity=100;
            itemCapacity=50;
        }};
        largePyratiteMixer =new GenericCrafter("largePyratiteMixer"){{
           size=4;
           requirements(Category.crafting,with(Items.copper,140,Items.lead,120,Items.graphite,80,Items.silicon,120));
           hasLiquids=true;
           hasPower=true;
           hasItems=true;
           craftTime=10f;
           consumePower(2f);
           consumeLiquid(Liquids.water,0.2f);
           consumeItems(new ItemStack(Items.coal,1));
           outputItem=new ItemStack(Items.pyratite,6);
           alwaysUnlocked=true;
        }};
        largeBlastMixer =new GenericCrafter("largeBlastMixer"){{
            size=4;
            requirements(Category.crafting,with(Items.copper,140,Items.lead,120,Items.graphite,80,Items.silicon,120));
            hasLiquids=true;
            hasPower=true;
            hasItems=true;
            craftTime=15f;
            consumePower(2f);
            consumeLiquid(Liquids.water,0.2f);
            consumeItems(new ItemStack(Items.pyratite,4));
            outputItem=new ItemStack(Items.blastCompound,10);
            alwaysUnlocked=true;
            liquidCapacity=100;
            itemCapacity=50;
        }};
        neutronZeroDefectSiliconFactory =new GenericCrafter("neutronZeroDefectSiliconFactory"){{
            size=5;
            requirements(Category.crafting,with(
                    Items.copper,120,Items.lead,150,
                    Items.titanium,200,Items.silicon,100,Items.plastanium,100,
                    Items.metaglass,120,Items.thorium,120,Items.graphite,100
            ));
            hasLiquids=true;
            hasPower=true;
            hasItems=true;
            craftTime=15f;
            consumePower(12f);
            consumeLiquid(Liquids.cryofluid,0.5f);
            consumeItems(new ItemStack(Items.sand,8));
            outputItem=new ItemStack(Items.silicon,20);
            alwaysUnlocked=true;
            liquidCapacity=100;
            itemCapacity=100;
            updateEffect=new MultiEffect( new Effect[]{
                    new WaveEffect(){{
                        sizeFrom = 0;
                        sizeTo = 20;
                        lifetime = 60;
                        sides = 6;
                        colorFrom = Color.valueOf("FFFFFF");
                        colorTo = Color.valueOf("FFFFFF");
                        strokeFrom = 5;
                        strokeTo = 0;
                    }},
                    new WaveEffect(){{
                        sizeFrom = 0;
                        sizeTo = 20;
                        lifetime = 60;
                        sides = 6;
                        rotation = 30;
                        colorFrom = Color.valueOf("FFFFFF");
                        colorTo = Color.valueOf("FFFFFF");
                        strokeFrom = 5;
                        strokeTo = 0;
                    }}
            });
        }};
        Crusher =new GenericCrafter("Crusher"){{
            size=2;
            requirements(Category.crafting,with(Items.copper,40,Items.lead,40,Items.silicon,80));
            hasItems=true;
            hasPower=true;
            drawer = new DrawMulti(new DrawDefault(), new DrawRegion("-1"){{
                spinSprite = true;
                rotateSpeed = 4f;
            }}, new DrawRegion("-top"));
            craftTime=60f;
            consumeItems(new ItemStack(Items.scrap,12));
            consumePower(1f);
            outputItem=new ItemStack(Items.sand,12);
            itemCapacity=96;
            alwaysUnlocked=true;
            updateEffect=Fx.pulverizeMedium;
        }};
        AmultlipleSteelFactory=new GenericCrafter("Amultiple-steel-factory"){{
            size=3;
            requirements(Category.crafting,with(
                    Items.silicon,100,Items.titanium,100,Items.graphite,100
            ));
            hasPower=true;
            hasLiquids=true;
            hasItems=true;
            lightColor = ModItem.AmultipleSteel.color;
            updateEffect = EffectWrapper.wrap(Fx.smeltsmoke, lightColor);
            craftEffect = EffectWrapper.wrap(TEEffect.square45_6_45, lightColor);;
            outputItem = new ItemStack(ModItem.AmultipleSteel, 3);
            consumeLiquid(Liquids.cryofluid,0.4f);
            consumeItems(with(Items.silicon,2,Items.graphite,3,Items.titanium,4));
            consumePower(4f);
            craftTime=28f;
            alwaysUnlocked=true;
        }};
        waterGasStove=new HeatCrafter("waterGasStove"){{
            craftEffect = Fx.none;
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.12f;

            // 液体处理
            outputLiquid = new LiquidStack(Liquids.hydrogen, 0.45f);
            liquidCapacity = 30f;
            hasPower = true;
            hasLiquids = true;

            // 热量系统
            heatRequirement = 8f;
            maxEfficiency = 5f;

            // 生产参数
            craftTime = 120f;
            size = 3;

            // 绘制器
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.water),
                    new DrawLiquidTile(Liquids.hydrogen),
                    new DrawParticles(){{
                        alpha = 0.15f;
                        particleRad = 12f;
                        particleSize = 9f;
                        particleLife = 110f;
                        particles = 15;
                        rotateScl = -3f;
                        reverse = true;
                        color = Color.valueOf("9eabf7");
                    }},
                    new DrawDefault(),
                    new DrawHeatInput()
            );

            // 可见性和分类
            buildVisibility = BuildVisibility.shown;
            category = Category.crafting;

            // 建造需求
            requirements = ItemStack.with(
                    Items.silicon, 100,
                    Items.tungsten, 100,
                    Items.oxide, 55
            );

            // 消耗配置
            consumeLiquid(Liquids.water, 0.3f);
            consumeItem(Items.graphite, 3);
            consumePower(4f);
        }};
    }

}
