package cn.easygd.quest.engine.runtime.module;

import cn.easygd.quest.engine.utils.markdown.MarkdownInfo;

/**
 * @author VD
 */
public interface DeliverModule {

    /**
     * build markdown
     *
     * @return markdown
     */
    MarkdownInfo buildMarkdown();
}
