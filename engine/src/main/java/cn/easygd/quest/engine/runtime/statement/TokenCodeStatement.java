package cn.easygd.quest.engine.runtime.statement;

import cn.easygd.quest.engine.core.QuestLexer;
import cn.easygd.quest.engine.runtime.enums.StatementType;

import java.util.Arrays;
import java.util.List;

/**
 * @author VD
 */
public class TokenCodeStatement extends CodeStatement {

    private static final List<Integer> SPACE_TOKEN_INDEX = Arrays.asList(QuestLexer.COMMA, QuestLexer.NEW, QuestLexer.QUESTION);

    private static final List<Integer> DOUBLE_SPACE_TOKEN_INDEX = Arrays.asList(QuestLexer.COLON, QuestLexer.DOT);

    /**
     * token index
     */
    private Integer tokenIndex;

    /**
     * token
     */
    private String token;

    /**
     * token value
     */
    private String value;

    /**
     * build content
     *
     * @return content
     */
    @Override
    public String buildContent() {
        if (SPACE_TOKEN_INDEX.contains(tokenIndex)) {
            return String.format("%s ", value);
        }
        return value;
    }

    /**
     * statement type
     *
     * @return statement type
     */
    @Override
    public StatementType type() {
        return StatementType.TOKEN;
    }

    public Integer getTokenIndex() {
        return tokenIndex;
    }

    public void setTokenIndex(Integer tokenIndex) {
        this.tokenIndex = tokenIndex;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getToken() {
        return token;
    }

    public String getValue() {
        return value;
    }
}
