package TechE.content.blocks.distribution;

import TechE.content.items.ModItem;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.DirectionalUnloader;
import mindustry.world.blocks.distribution.DuctRouter;

import static mindustry.type.ItemStack.with;

public class ModDistribution {
    public static Block superRouter,superUnloader;
    public static void load(){
        superRouter=new DuctRouter("super-router"){{
            requirements(Category.distribution, with(Items.silicon, 10,Items.graphite,20,
                    ModItem.AmultipleSteel,15));
            health = 150;
            speed = 1f;
            regionRotated1 = 1;
            solid = false;
            alwaysUnlocked=true;
        }};
        superUnloader = new DirectionalUnloader("super-unloader"){{
            requirements(Category.distribution, with(Items.graphite, 20, Items.silicon, 20, ModItem.AmultipleSteel, 15));
            health = 250;
            speed = 1f;
            solid = false;
            underBullets = true;
            regionRotated1 = 1;
            alwaysUnlocked=true;
        }};
    }
}
