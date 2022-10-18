import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.GameObject;

import static org.dreambot.api.Client.getLocalPlayer;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;

public class MiningStageOne {
    public static void mineOres() {
        Area miningArea = new Area(3222, 3149, 3226, 3145);
        GameObject ore = GameObjects.closest(11360, 11361);

        if(!miningArea.contains(Players.getLocal()) && Skills.getRealLevel(Skill.MINING) < 15) {
            while (!miningArea.contains(Players.getLocal()) && !Players.getLocal().isMoving()) {
                Main.botStatus = "Walking to mining area...";
                Walking.walk(miningArea.getRandomTile());
                sleep(1200);
            }
        }
        if(!Inventory.isFull() && Inventory.contains("Bronze pickaxe") && miningArea.contains(Players.getLocal())) {
            if(Players.getLocal().getAnimation() == -1 && !Inventory.isFull()) {
                sleep(1000);
                Main.botStatus = "Mining ores...";
                ore.interact("Mine");
                sleep(4000);
                sleepUntil(() -> Players.getLocal().getAnimation() == -1, 250000);
            }
        }
        if(Inventory.isFull() && Inventory.contains("Tin ore")) {
            Main.botStatus = "Dropping ores....";
            Inventory.dropAllExcept("Bronze pickaxe");
            sleep(4000);
        }
    }
}
