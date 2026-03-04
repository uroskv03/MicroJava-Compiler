// generated with ast extension for cup
// version 0.8
// 17/1/2026 14:37:44


package rs.ac.bg.etf.pp1.ast;

public class ConditionalExpr2 extends ConditionalExpr {

    private Empty Empty;
    private Condition Condition;
    private QuestionMark QuestionMark;
    private ConditionalExprHelp ConditionalExprHelp;
    private Colon1 Colon1;
    private ConditionalExpr ConditionalExpr;

    public ConditionalExpr2 (Empty Empty, Condition Condition, QuestionMark QuestionMark, ConditionalExprHelp ConditionalExprHelp, Colon1 Colon1, ConditionalExpr ConditionalExpr) {
        this.Empty=Empty;
        if(Empty!=null) Empty.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.QuestionMark=QuestionMark;
        if(QuestionMark!=null) QuestionMark.setParent(this);
        this.ConditionalExprHelp=ConditionalExprHelp;
        if(ConditionalExprHelp!=null) ConditionalExprHelp.setParent(this);
        this.Colon1=Colon1;
        if(Colon1!=null) Colon1.setParent(this);
        this.ConditionalExpr=ConditionalExpr;
        if(ConditionalExpr!=null) ConditionalExpr.setParent(this);
    }

    public Empty getEmpty() {
        return Empty;
    }

    public void setEmpty(Empty Empty) {
        this.Empty=Empty;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public QuestionMark getQuestionMark() {
        return QuestionMark;
    }

    public void setQuestionMark(QuestionMark QuestionMark) {
        this.QuestionMark=QuestionMark;
    }

    public ConditionalExprHelp getConditionalExprHelp() {
        return ConditionalExprHelp;
    }

    public void setConditionalExprHelp(ConditionalExprHelp ConditionalExprHelp) {
        this.ConditionalExprHelp=ConditionalExprHelp;
    }

    public Colon1 getColon1() {
        return Colon1;
    }

    public void setColon1(Colon1 Colon1) {
        this.Colon1=Colon1;
    }

    public ConditionalExpr getConditionalExpr() {
        return ConditionalExpr;
    }

    public void setConditionalExpr(ConditionalExpr ConditionalExpr) {
        this.ConditionalExpr=ConditionalExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Empty!=null) Empty.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(QuestionMark!=null) QuestionMark.accept(visitor);
        if(ConditionalExprHelp!=null) ConditionalExprHelp.accept(visitor);
        if(Colon1!=null) Colon1.accept(visitor);
        if(ConditionalExpr!=null) ConditionalExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Empty!=null) Empty.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(QuestionMark!=null) QuestionMark.traverseTopDown(visitor);
        if(ConditionalExprHelp!=null) ConditionalExprHelp.traverseTopDown(visitor);
        if(Colon1!=null) Colon1.traverseTopDown(visitor);
        if(ConditionalExpr!=null) ConditionalExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Empty!=null) Empty.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(QuestionMark!=null) QuestionMark.traverseBottomUp(visitor);
        if(ConditionalExprHelp!=null) ConditionalExprHelp.traverseBottomUp(visitor);
        if(Colon1!=null) Colon1.traverseBottomUp(visitor);
        if(ConditionalExpr!=null) ConditionalExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionalExpr2(\n");

        if(Empty!=null)
            buffer.append(Empty.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(QuestionMark!=null)
            buffer.append(QuestionMark.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionalExprHelp!=null)
            buffer.append(ConditionalExprHelp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Colon1!=null)
            buffer.append(Colon1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionalExpr!=null)
            buffer.append(ConditionalExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionalExpr2]");
        return buffer.toString();
    }
}
