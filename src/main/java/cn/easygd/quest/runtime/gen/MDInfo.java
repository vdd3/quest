package cn.easygd.quest.runtime.gen;

/**
 * @author VD
 */
public interface MDInfo {

    /**
     * code statement chek
     */
    void check();

    /**
     * code gen
     */
    void genCode();

    /**
     * gen file
     *
     * @param filePath file path
     */
    void genFile(String filePath);
}
