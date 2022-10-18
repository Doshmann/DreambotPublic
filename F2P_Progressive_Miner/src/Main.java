import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.URL;

@ScriptManifest(author = "Mr.S",
        description = "Simple F2P Power-Miner",
        name = "F2P Power Miner (basic)",
        category = Category.MINING, version = 1)

public class Main extends AbstractScript{
    private long timeBegan;
    private long timeRan;
    private int beginningXP;
    private final Image paintBackground = this.getImage();
    private DrawMouseUtil drawMouseUtil = new DrawMouseUtil();
    public static String botStatus = "Online";

    @Override //Infinite loop
    public int onLoop() {
        if(Skills.getRealLevel(Skill.MINING) < 15) {
            MiningStageOne.mineOres();
        }
        if(Skills.getRealLevel(Skill.MINING) >= 15) {
            MiningStageTwo.mineOres();
        }
        return 0;
    }

    private Image getImage() {
        try {
            return ImageIO.read(new URL("https://i.imgur.com/cQpHJf7.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    //When script start load this.
    public void onStart() {
        SkillTracker.start(Skill.MINING);
        this.timeBegan = System.currentTimeMillis();
        this.beginningXP = Skills.getExperience(Skill.MINING);
        drawMouseUtil.setRainbow(true);
        log("Bot Started, you have probably made a bad decision today");
    }

    //When script ends do this.
    public void onExit() {
        log("Bot Ended, you've realised the mistake that you've made, congratulations; although it's probably too late");
        log("Congratulations, the bot ran for a total of: " + Timer.formatTime(this.timeRan));
    }

    private void drawShadowString(Graphics g, String s, int x, int y) {
        g.setFont(new Font("Ariel", 1, 15));
        g.setColor(Color.BLACK);
        g.drawString(s, x + 1, y + 1);
        g.setColor(Color.WHITE);
        g.drawString(s, x, y);
    }

    public void onPaint(Graphics g) {
        this.timeRan = System.currentTimeMillis() - this.timeBegan;
        int currentXp = Skills.getExperience(Skill.MINING);
        int xpGained = currentXp - this.beginningXP;
        this.drawShadowString(g, "Bot Status: " + botStatus, 20, 330);
        this.drawShadowString(g, "Runtime: " + Timer.formatTime(this.timeRan), 20, 315);
        this.drawShadowString(g, "Gained XP: " + xpGained, 20, 285);
        this.drawShadowString(g, "Exp / Hour: " + SkillTracker.getGainedExperiencePerHour(Skill.MINING), 20, 300);
        drawMouseUtil.drawRotatingCrossMouse(g);
        drawMouseUtil.drawTrail(g);
        if (this.paintBackground != null) {
            g.drawImage(this.paintBackground, 0, 339, (ImageObserver)null);
        }
    }
}
