package xyz.blackdev.Blueberry.utils.config.configs;

public class DefaultConfig {
    private String capeTexture;
    private boolean showNameTag;
    private boolean customFont;


    public DefaultConfig() {
        this.capeTexture = "default_cape";
        this.showNameTag = true;
        this.customFont = false;
    }


    public String getCapeTexture() {
        return capeTexture;
    }

    public void setCapeTexture(String capeTexture) {
        this.capeTexture = capeTexture;
    }

    public boolean isShowNameTag() {
        return showNameTag;
    }

    public void setShowNameTag(boolean showNameTag) {
        this.showNameTag = showNameTag;
    }

    public boolean isCustomFont() {
        return customFont;
    }

    public void setCustomFont(boolean customFont) {
        this.customFont = customFont;
    }
}