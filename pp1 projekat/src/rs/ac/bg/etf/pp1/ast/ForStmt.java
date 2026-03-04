// generated with ast extension for cup
// version 0.8
// 17/1/2026 14:37:44


package rs.ac.bg.etf.pp1.ast;

public class ForStmt extends Matched {

    private ForHelp ForHelp;
    private DesignatorStmt DesignatorStmt;
    private Semi0 Semi0;
    private ConditionH ConditionH;
    private Semi1 Semi1;
    private SemiHelp SemiHelp;
    private ForDesignatorStmt ForDesignatorStmt;
    private StatementHelp StatementHelp;

    public ForStmt (ForHelp ForHelp, DesignatorStmt DesignatorStmt, Semi0 Semi0, ConditionH ConditionH, Semi1 Semi1, SemiHelp SemiHelp, ForDesignatorStmt ForDesignatorStmt, StatementHelp StatementHelp) {
        this.ForHelp=ForHelp;
        if(ForHelp!=null) ForHelp.setParent(this);
        this.DesignatorStmt=DesignatorStmt;
        if(DesignatorStmt!=null) DesignatorStmt.setParent(this);
        this.Semi0=Semi0;
        if(Semi0!=null) Semi0.setParent(this);
        this.ConditionH=ConditionH;
        if(ConditionH!=null) ConditionH.setParent(this);
        this.Semi1=Semi1;
        if(Semi1!=null) Semi1.setParent(this);
        this.SemiHelp=SemiHelp;
        if(SemiHelp!=null) SemiHelp.setParent(this);
        this.ForDesignatorStmt=ForDesignatorStmt;
        if(ForDesignatorStmt!=null) ForDesignatorStmt.setParent(this);
        this.StatementHelp=StatementHelp;
        if(StatementHelp!=null) StatementHelp.setParent(this);
    }

    public ForHelp getForHelp() {
        return ForHelp;
    }

    public void setForHelp(ForHelp ForHelp) {
        this.ForHelp=ForHelp;
    }

    public DesignatorStmt getDesignatorStmt() {
        return DesignatorStmt;
    }

    public void setDesignatorStmt(DesignatorStmt DesignatorStmt) {
        this.DesignatorStmt=DesignatorStmt;
    }

    public Semi0 getSemi0() {
        return Semi0;
    }

    public void setSemi0(Semi0 Semi0) {
        this.Semi0=Semi0;
    }

    public ConditionH getConditionH() {
        return ConditionH;
    }

    public void setConditionH(ConditionH ConditionH) {
        this.ConditionH=ConditionH;
    }

    public Semi1 getSemi1() {
        return Semi1;
    }

    public void setSemi1(Semi1 Semi1) {
        this.Semi1=Semi1;
    }

    public SemiHelp getSemiHelp() {
        return SemiHelp;
    }

    public void setSemiHelp(SemiHelp SemiHelp) {
        this.SemiHelp=SemiHelp;
    }

    public ForDesignatorStmt getForDesignatorStmt() {
        return ForDesignatorStmt;
    }

    public void setForDesignatorStmt(ForDesignatorStmt ForDesignatorStmt) {
        this.ForDesignatorStmt=ForDesignatorStmt;
    }

    public StatementHelp getStatementHelp() {
        return StatementHelp;
    }

    public void setStatementHelp(StatementHelp StatementHelp) {
        this.StatementHelp=StatementHelp;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForHelp!=null) ForHelp.accept(visitor);
        if(DesignatorStmt!=null) DesignatorStmt.accept(visitor);
        if(Semi0!=null) Semi0.accept(visitor);
        if(ConditionH!=null) ConditionH.accept(visitor);
        if(Semi1!=null) Semi1.accept(visitor);
        if(SemiHelp!=null) SemiHelp.accept(visitor);
        if(ForDesignatorStmt!=null) ForDesignatorStmt.accept(visitor);
        if(StatementHelp!=null) StatementHelp.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForHelp!=null) ForHelp.traverseTopDown(visitor);
        if(DesignatorStmt!=null) DesignatorStmt.traverseTopDown(visitor);
        if(Semi0!=null) Semi0.traverseTopDown(visitor);
        if(ConditionH!=null) ConditionH.traverseTopDown(visitor);
        if(Semi1!=null) Semi1.traverseTopDown(visitor);
        if(SemiHelp!=null) SemiHelp.traverseTopDown(visitor);
        if(ForDesignatorStmt!=null) ForDesignatorStmt.traverseTopDown(visitor);
        if(StatementHelp!=null) StatementHelp.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForHelp!=null) ForHelp.traverseBottomUp(visitor);
        if(DesignatorStmt!=null) DesignatorStmt.traverseBottomUp(visitor);
        if(Semi0!=null) Semi0.traverseBottomUp(visitor);
        if(ConditionH!=null) ConditionH.traverseBottomUp(visitor);
        if(Semi1!=null) Semi1.traverseBottomUp(visitor);
        if(SemiHelp!=null) SemiHelp.traverseBottomUp(visitor);
        if(ForDesignatorStmt!=null) ForDesignatorStmt.traverseBottomUp(visitor);
        if(StatementHelp!=null) StatementHelp.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForStmt(\n");

        if(ForHelp!=null)
            buffer.append(ForHelp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStmt!=null)
            buffer.append(DesignatorStmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Semi0!=null)
            buffer.append(Semi0.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionH!=null)
            buffer.append(ConditionH.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Semi1!=null)
            buffer.append(Semi1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SemiHelp!=null)
            buffer.append(SemiHelp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForDesignatorStmt!=null)
            buffer.append(ForDesignatorStmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementHelp!=null)
            buffer.append(StatementHelp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForStmt]");
        return buffer.toString();
    }
}
