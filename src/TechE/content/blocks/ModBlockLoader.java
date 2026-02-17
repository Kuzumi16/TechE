package TechE.content.blocks;

import TechE.content.blocks.distribution.ModDistribution;
import TechE.content.blocks.production.ModBlock1;
import TechE.content.blocks.production.ModBlock;
import TechE.content.blocks.unit.ModUnitFactory;
import TechE.content.blocks.duct.ModDuct;
import TechE.content.blocks.drill.ModDrill;
import TechE.content.util.UtilLoader;

public class ModBlockLoader {
    public static void load(){
        UtilLoader.load();
        ModBlock.load();
        ModBlock1.load(true);
        ModUnitFactory.load();
        ModDuct.load();
        ModDrill.load();
        ModDistribution.load();
    }
}
