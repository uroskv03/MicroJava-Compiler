// generated with ast extension for cup
// version 0.8
// 17/1/2026 14:37:44


package rs.ac.bg.etf.pp1.ast;

public class ConditionalExprHelp implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private ConditionalExpr ConditionalExpr;

    public ConditionalExprHelp (ConditionalExpr ConditionalExpr) {
        this.ConditionalExpr=ConditionalExpr;
        if(ConditionalExpr!=null) ConditionalExpr.setParent(this);
    }

    public ConditionalExpr getConditionalExpr() {
        return ConditionalExpr;
    }

    public void setConditionalExpr(ConditionalExpr ConditionalExpr) {
        this.ConditionalExpr=ConditionalExpr;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionalExpr!=null) ConditionalExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionalExpr!=null) ConditionalExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionalExpr!=null) ConditionalExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionalExprHelp(\n");

        if(ConditionalExpr!=null)
            buffer.append(ConditionalExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionalExprHelp]");
        return buffer.toString();
    }
}
