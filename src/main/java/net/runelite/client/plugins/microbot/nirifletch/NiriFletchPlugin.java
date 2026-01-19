package net.runelite.client.plugins.microbot.nirifletch;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.PluginConstants;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.*;

@PluginDescriptor(
        name = "Niri Fletcher",
        description = "Niri's custom fletching plugin",
        authors = { "Niri" },
        version = NiriFletchPlugin.version,
        minClientVersion = "1.9.9.1",
        tags = {"fletching", "microbot", "skills", "niri"},
        iconUrl = "https://chsami.github.io/Microbot-Hub/NiriFletchPlugin/assets/icon.png",
        cardUrl = "https://chsami.github.io/Microbot-Hub/NiriFletchPlugin/assets/card.png",
        enabledByDefault = PluginConstants.DEFAULT_ENABLED,
        isExternal = PluginConstants.IS_EXTERNAL
)
@Slf4j
public class NiriFletchPlugin extends Plugin {

    public static final String version = "1.6.3";

    @Inject
    private FletchingConfig config;

    @Provides
    FletchingConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(FletchingConfig.class);
    }
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private FletchingOverlay fletchingOverlay;

    FletchingScript fletchingScript;


    @Override
    protected void startUp() throws AWTException {
		Microbot.pauseAllScripts.compareAndSet(true, false);
        if (overlayManager != null) {
            overlayManager.add(fletchingOverlay);
        }
        fletchingScript = new FletchingScript();
        fletchingScript.run(config);
    }

    protected void shutDown() {
        fletchingScript.shutdown();
        overlayManager.remove(fletchingOverlay);
    }
}
