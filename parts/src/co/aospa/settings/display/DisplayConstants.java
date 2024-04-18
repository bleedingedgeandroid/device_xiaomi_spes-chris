package co.aospa.settings.display;

public class DisplayConstants {

    public static final String HBM_NODE = "/sys/devices/platform/soc/soc:qcom,dsi-display-primary/hbm";
    public static final String HBM_PROP = "persist.oled.hbm_mode";

    public static final String DC_DIMMING_NODE = "/sys/devices/platform/soc/soc:qcom,dsi-display-primary/msm_fb_ea_enable";
    public static final String DC_DIMMING_PROP = "persist.oled.dcdimming_mode";

    public static final int MODE_OFF = 0;
    public static final int MODE_ON = 1;

    public static final String KEY_HBM = "hbm_pref";
    public static final String KEY_DC_DIMMING = "dcdimming_pref";

}
