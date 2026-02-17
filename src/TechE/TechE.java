package TechE;



import TechE.content.items.*;
import TechE.content.blocks.ModBlockLoader;
import TechE.content.units.ModUnitLoader;
import TechE.content.liquid.ModLiquid;
import mindustry.mod.Mod;
import TechE.content.exo.ExoLoader;
import TechE.content.VanillaModifier;
public class TechE extends Mod {
    @Override
    public void loadContent() {
        ExoLoader.load(true);
        ModItem.load();
        ModLiquid.load();
        ModUnitLoader.load();
        ModBlockLoader.load();
        VanillaModifier.load();
        //addAmmo();
    }
}
