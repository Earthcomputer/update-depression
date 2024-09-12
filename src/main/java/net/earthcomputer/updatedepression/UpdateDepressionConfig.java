package net.earthcomputer.updatedepression;

import dev.xpple.betterconfig.api.Config;

public final class UpdateDepressionConfig {
    private UpdateDepressionConfig() {
    }

    @Config
    public static boolean updateSuppressionCrashFix = false;

    @Config(setter = @Config.Setter("setAllowInvalidBlockEntities"))
    private static boolean allowInvalidBlockEntities = false;
    public static void setAllowInvalidBlockEntities(boolean allowInvalidBlockEntities) {
        UpdateDepressionConfig.allowInvalidBlockEntities = allowInvalidBlockEntities;
        computeValues();
    }

    @Config(setter = @Config.Setter("setReintroduceCCESuppression"))
    public static boolean reintroduceCCESuppression = false;
    public static void setReintroduceCCESuppression(boolean reintroduceCCESuppression) {
        UpdateDepressionConfig.reintroduceCCESuppression = reintroduceCCESuppression;
        computeValues();
    }

    public static boolean shouldAllowInvalidBlockEntities = false;

    public static void computeValues() {
        shouldAllowInvalidBlockEntities = allowInvalidBlockEntities || reintroduceCCESuppression;
    }
}
