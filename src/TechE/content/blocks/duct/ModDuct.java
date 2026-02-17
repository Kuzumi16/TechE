package TechE.content.blocks.duct;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.distribution.ItemBridge;
import mindustry.world.meta.BuildVisibility;

public class ModDuct {
    public static ItemBridge tdq;
    public static void load(){
        tdq=new ItemBridge("tdq"){
            {
                range = 6;
                health = 90;
                transportTime = 2.0F;
                itemCapacity = 15;
                hasPower = false;
                requirements = ItemStack.with(Items.lead, 20, Items.graphite, 10,
                        Items.titanium, 15, Items.silicon, 10);
                buildVisibility = BuildVisibility.shown;
                category = Category.distribution;
                alwaysUnlocked=true;
            }
        };
    }
}
