package TechE.content.exo;
import TechE.content.exo.block.ExoUnitFactory;
import TechE.content.exo.unit.ExoUnit;
public class ExoLoader {
    public static void load(Boolean isLoad){
        if (isLoad){
            ExoUnit.load();
            ExoUnitFactory.load();
        }

    }
}
