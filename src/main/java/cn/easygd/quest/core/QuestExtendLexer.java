package cn.easygd.quest.core;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;

/**
 * @author VD
 */
public class QuestExtendLexer extends QuestLexer{

    public QuestExtendLexer(CharStream input) {
        super(input);
    }

    @Override
    public Token nextToken() {
        Token token = super.nextToken();


        return token.getType() == NEWLINE ? nextToken() : token;
    }
}
