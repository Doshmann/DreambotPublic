package Mining;
import Main.Main;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.wrappers.interactive.GameObject;

import static org.dreambot.api.Client.getLocalPlayer;
import static org.dreambot.api.methods.MethodProvider.*;


public class MiningStageOne {
    public static void mineOres() {
        int min = 20000; int max = 40000;
        int min2 = 1000; int max2 = 2000;
        Area miningArea = new Area(3222, 3149, 3226, 3145);
        GameObject ore = GameObjects.closest(11360, 11361);

        if(!miningArea.contains(getLocalPlayer()) && Inventory.contains("Bronze pickaxe") && Skills.getRealLevel(Skill.MINING) < 15) {
            if(!miningArea.contains(getLocalPlayer()) && !getLocalPlayer().isMoving()) {
                int randomSleepTime2 = (int) (Math.random()*(max2-min2+1)+min2);
                Main.botStatus = "Walking to mining area...";
                Walking.walk(miningArea.getRandomTile());
                log("Sleeping for: " + randomSleepTime2);
                sleepUntil(() -> !getLocalPlayer().isMoving(), 5000);
                sleep(randomSleepTime2);
            }
        }
        if(!Inventory.isFull() && Inventory.contains("Bronze pickaxe") && miningArea.contains(getLocalPlayer()) && Main.skillToStopAt > Skills.getRealLevel(Skill.MINING)) {
            if(getLocalPlayer().getAnimation() == -1 && !Inventory.isFull()) {
                int ores = Inventory.count("Tin ore");
                int randomSleepTime = (int) (Math.random()*(max-min+1)+min);
                int randomSleepTime2 = (int) (Math.random()*(max2-min2+1)+min2);
                sleep(randomSleepTime2);
                Main.botStatus = "Mining ores...";
                ore.interact("Mine");
                log("Sleeping for: " + randomSleepTime2);
                sleep(randomSleepTime2);
                sleepUntil(() -> Inventory.count("Tin ore") > ores || Dialogues.canContinue(), randomSleepTime);
            }
        }
        if(Inventory.isFull() && Inventory.contains("Tin ore")) {
            int randomSleepTime2 = (int) (Math.random()*(max2-min2+1)+min2);
            Main.botStatus = "Dropping ores....";
            Inventory.dropAllExcept("Bronze pickaxe");
            sleep(randomSleepTime2);
        }
    }
}
