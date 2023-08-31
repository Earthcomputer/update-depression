package net.earthcomputer.updatedepression;

import com.google.gson.stream.JsonReader;
import com.mojang.logging.LogUtils;
import net.earthcomputer.updatedepression.command.UpdateDepressionCommands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class UpdateDepression implements ModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<String, String> defaultTranslations = new HashMap<>();

    private static void loadDefaultTranslations() {
        try (BufferedReader reader = Files.newBufferedReader(FabricLoader.getInstance().getModContainer("update-depression").orElseThrow().findPath("assets/update-depression/lang/en_us.json").orElseThrow())) {
            JsonReader json = new JsonReader(reader);
            json.beginObject();
            while (json.hasNext()) {
                String key = json.nextName();
                String value = json.nextString();
                defaultTranslations.put(key, value);
            }
            json.endObject();
        } catch (IOException e) {
            LOGGER.error("Failed to read default translations", e);
        }
    }

    public static Component translatableWithFallback(String key, Object... args) {
        return Component.translatableWithFallback(key, defaultTranslations.getOrDefault(key, key), args);
    }

    @Override
    public void onInitialize() {
        loadDefaultTranslations();

        CommandRegistrationCallback.EVENT.register((dispatcher, context, environment) -> UpdateDepressionCommands.register(dispatcher, context));
    }
}
