package cn.easygd.quest.core;

import cn.easygd.quest.runtime.module.QuestModule;
import cn.easygd.quest.runtime.statement.CodeStatement;
import cn.easygd.quest.runtime.statement.TokenCodeStatement;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * @author VD
 */
public abstract class QuestStatementVisitor<T extends QuestModule> extends QuestParserBaseVisitor<Void> {

    /**
     * 获取模块
     *
     * @return 模块
     */
    public abstract T getModule();

    protected CodeStatement convertTree(ParseTree tree) {
        // token tree
        if(tree instanceof TerminalNode){
            return buildTokenStatement((TerminalNode) tree);
        }else if (tree instanceof ParserRuleContext){
            // rule tree
        }
        return null;
    }

    protected TokenCodeStatement buildTokenStatement(TerminalNode node) {
        TokenCodeStatement tokenCodeStatement = new TokenCodeStatement();

        // token text
        String tokenTxt = node.getText();
        // token
        int typeIndex = node.getSymbol().getType();
        String token = QuestParser.VOCABULARY.getSymbolicName(typeIndex);

        tokenCodeStatement.setToken(token);
        tokenCodeStatement.setValue(tokenTxt);

        return tokenCodeStatement;
    }
}
