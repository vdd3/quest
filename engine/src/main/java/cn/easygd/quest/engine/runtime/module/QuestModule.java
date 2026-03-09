package cn.easygd.quest.engine.runtime.module;

/**
 * @author VD
 */
public abstract class QuestModule {

    /**
     * original script
     */
    private String originalScript;

    public String getOriginalScript() {
        return originalScript;
    }

    public void setOriginalScript(String originalScript) {
        this.originalScript = originalScript;
    }
}
