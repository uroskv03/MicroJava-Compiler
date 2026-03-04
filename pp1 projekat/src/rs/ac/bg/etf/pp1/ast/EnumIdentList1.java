// generated with ast extension for cup
// version 0.8
// 17/1/2026 14:37:44


package rs.ac.bg.etf.pp1.ast;

public class EnumIdentList1 extends EnumIdentList {

    private EnumIdentList EnumIdentList;
    private EnumIdent EnumIdent;

    public EnumIdentList1 (EnumIdentList EnumIdentList, EnumIdent EnumIdent) {
        this.EnumIdentList=EnumIdentList;
        if(EnumIdentList!=null) EnumIdentList.setParent(this);
        this.EnumIdent=EnumIdent;
        if(EnumIdent!=null) EnumIdent.setParent(this);
    }

    public EnumIdentList getEnumIdentList() {
        return EnumIdentList;
    }

    public void setEnumIdentList(EnumIdentList EnumIdentList) {
        this.EnumIdentList=EnumIdentList;
    }

    public EnumIdent getEnumIdent() {
        return EnumIdent;
    }

    public void setEnumIdent(EnumIdent EnumIdent) {
        this.EnumIdent=EnumIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EnumIdentList!=null) EnumIdentList.accept(visitor);
        if(EnumIdent!=null) EnumIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EnumIdentList!=null) EnumIdentList.traverseTopDown(visitor);
        if(EnumIdent!=null) EnumIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EnumIdentList!=null) EnumIdentList.traverseBottomUp(visitor);
        if(EnumIdent!=null) EnumIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EnumIdentList1(\n");

        if(EnumIdentList!=null)
            buffer.append(EnumIdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EnumIdent!=null)
            buffer.append(EnumIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EnumIdentList1]");
        return buffer.toString();
    }
}
