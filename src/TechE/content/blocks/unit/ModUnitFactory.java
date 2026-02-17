package TechE.content.blocks.unit;

import TechE.content.exo.unit.ExoUnit;
import TechE.content.units.ModAttackUnit;
import arc.struct.Seq;
import TechE.content.units.ModUnit;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.type.PayloadStack;
import mindustry.world.Block;
import mindustry.world.blocks.units.UnitAssembler;
import mindustry.world.blocks.units.UnitAssemblerModule;
import mindustry.world.blocks.units.UnitFactory;

import static mindustry.type.ItemStack.with;

public class ModUnitFactory {
    public static Block worker_factory,astral_assembler,astral_dUpgrader,CoreUnitAssembler;
    public static UnitAssembler nemesisFactory;
    public static void load(){
        worker_factory=new UnitFactory("worker-factory"){{
           requirements(Category.units,with(
                   Items.copper,70,Items.lead,50,Items.silicon,60
           ));
           plans= Seq.with(
                   new UnitPlan(ModUnit.workerOne,60f*7f,with(
                           Items.silicon,50,Items.graphite,50)),
                   new UnitPlan(ModUnit.workerOne,60f*7f,with(
                           Items.silicon,50,Items.graphite,50)),
                   new UnitPlan(ModUnit.workerTwo,60f*7f,with(
                           Items.silicon,50,Items.graphite,50)),
                   new UnitPlan(ModUnit.transporter,60f*4f,with(
                           Items.titanium,20,Items.silicon,20
                   ))
           );
           size=3;
           alwaysUnlocked=true;
        }};
        astral_assembler=new UnitAssembler("astral-assembler"){{
            requirements(Category.units,with(Items.silicon,1000,Items.titanium,500,
                    Items.titanium,500,Items.graphite,400,Items.plastanium,700,Items.surgeAlloy,
                    1000,Items.phaseFabric,200));
            regionSuffix = "-dark";
            size = 5;
            areaSize = 16;
            plans.add(new AssemblerUnitPlan(ModAttackUnit.destroyer,60f*60f*2f, PayloadStack.list(
                    UnitTypes.quasar,8,Blocks.surgeWallLarge,20,Blocks.lancer,2
            )), new AssemblerUnitPlan(ModAttackUnit.assister,60f*60f*2.5f,PayloadStack.list(
                    UnitTypes.quasar,8,UnitTypes.mega,4,Blocks.surgeWallLarge,20,Blocks.lancer,2
            )));

            consumePower(10f);
            consumeLiquid(Liquids.cryofluid, 120f / 60f);
            alwaysUnlocked=true;
        }};
        astral_dUpgrader=new UnitAssemblerModule("astral-assembler-module"){{
            requirements(Category.units, with(Items.titanium, 300, Items.thorium, 500, Items.silicon, 250, Items.phaseFabric, 400));
            consumePower(3.5f);
            regionSuffix = "-dark";

            alwaysUnlocked=true;
            size = 5;
        }};
        nemesisFactory=new UnitAssembler("nemesis-factory"){{
            requirements(Category.units,with(Items.graphite,1000,Items.silicon,1000,Items.tungsten,400,
                    Items.surgeAlloy,200,Items.phaseFabric,100,Items.carbide,400,Items.oxide,500));
            size=7;
            areaSize=21;
            plans.add(new AssemblerUnitPlan(ExoUnit.nemesis,60f*60f*5f, PayloadStack.list(
                    UnitTypes.obviate,9, Blocks.carbideWallLarge,12,Blocks.reinforcedSurgeWallLarge,3
            )));
            consumePower(10);
            consumeLiquid(Liquids.cyanogen,0.2f);
            alwaysUnlocked=true;
        }};
        CoreUnitAssembler=new UnitFactory("core-unit-assembler"){{
            size=3;
            requirements(Category.units,with(Items.copper,80,Items.graphite,50,Items.lead,60,Items.silicon,80));
           alwaysUnlocked=true;
           plans=Seq.with(new UnitPlan(UnitTypes.alpha,60f*8f,with(
                   Items.copper,30,Items.lead,50,Items.silicon,40
           )),
           new UnitPlan(UnitTypes.beta,60f*10f,with(Items.copper,50,Items.lead,50,Items.silicon,60,
                   Items.titanium,50)),
           new UnitPlan(UnitTypes.gamma,60f*10f,with(
                   Items.copper,60,Items.lead,40,Items.silicon,70,Items.titanium,50,Items.thorium,30
           )));
        }};
    }
}
