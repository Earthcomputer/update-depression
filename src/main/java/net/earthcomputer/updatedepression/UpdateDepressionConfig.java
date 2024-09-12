package net.earthcomputer.updatedepression;

import dev.xpple.betterconfig.api.Config;

public final class UpdateDepressionConfig {
    private UpdateDepressionConfig() {
    }

    @Config
    public static boolean updateSuppressionCrashFix = false;
    @Config
    public static boolean reintroduceCCESuppression = false;
}
