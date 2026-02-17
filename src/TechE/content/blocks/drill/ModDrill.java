package TechE.content.blocks.drill;

import static mindustry.type.ItemStack.with;

import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.production.Drill;

public class ModDrill {

    public static Block liziDrill, orePump;

    public static void load() {
        liziDrill = new Drill("liziDrill") {
            {
                requirements(
                    Category.production,
                    with(
                    )
                );
                drillTime = 20f;
                size = 4;
                drawRim = true;
                tier = 15;
                updateEffect = Fx.pulverizeRed;
                updateEffectChance = 0.09f;
                drillEffect = Fx.mineHuge;
                rotateSpeed = 9f;
                warmupSpeed = 0.02f;
                itemCapacity = 20;
                alwaysUnlocked = true;
                //more than the laser drill
                liquidBoostIntensity = 2f;
                consumeLiquid(Liquids.water, 0.1f).boost();
            }
        };

    }

}
