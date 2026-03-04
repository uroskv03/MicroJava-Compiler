package rs.ac.bg.etf.pp1;

import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;

import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	private int jumpContextLevel;
	
	public int getMainPc(){
		return mainPc;
	}
	
	private void initializePredeclaredMethods() {
        // 'ord' i 'chr' imaju isto kod
        Obj ordMethod = Tab.find("ord");
        Obj chrMethod = Tab.find("chr");
        ordMethod.setAdr(Code.pc);
        chrMethod.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);
 
        Obj lenMethod = Tab.find("len");
        lenMethod.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.arraylength);
        Code.put(Code.exit);
        Code.put(Code.return_);
 
    }
	
	CodeGenerator() {
		this.initializePredeclaredMethods();
	}
	
	@Override
	public void visit(Printer printer){
		if(printer.getExpr().struct == Tab.charType){
			Code.put(Code.bprint);
		}else{
			Code.put(Code.print); 
		}
	}
	
	@Override
	public void visit(CN1 cn1){
		Code.loadConst(cn1.getN1()); 
	}
	

	
	@Override
	public void visit(NoCN noCN){
		Code.loadConst(0); 
	}
	
	@Override
	public void visit(NumConst numConst){
		Code.loadConst(numConst.getN1());
	}
	
	@Override
	public void visit(CharConst charConst){
		Code.loadConst(charConst.getC1());
	}
	
	@Override
	public void visit(BoolConst boolConst) {
		Code.loadConst(boolConst.getB1());
	}
	
	@Override
	public void visit(Var var) {
		Obj obj = var.getDesignator().obj;
	    if (obj.getKind() == Obj.Var || obj.getKind() == Obj.Con || obj.getKind() == Obj.Elem) {
	        Code.load(obj);
	    }
	}
	
	@Override
	public void visit(NegFactor negFactor) {
		Code.put(Code.neg);
	}
	
	@Override
	public void visit(NewFacotr newFacotr) {
		Code.put(Code.newarray);
		if(newFacotr.getType().struct.equals(Tab.charType)) {
			Code.put(0);
		} else {
			Code.put(1);
		}
		
	}
	
	@Override
	public void visit(FuncCall funcCall) {
		int offset = funcCall.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	
	
	@Override
	public void visit(DesignatorName designatorName) {
		if(!(designatorName.getParent() instanceof DesignatorDotIdent))
			Code.load(designatorName.obj);
	}
	
	@Override
	public void visit(DesignatorStatementExpr designatorStatementExpr) {
		Code.store(designatorStatementExpr.getDesignator().obj);
	}
	@Override
	public void visit(DesignatorStatementInc designatorStatementInc) {
		if(designatorStatementInc.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatementInc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designatorStatementInc.getDesignator().obj);
	}
	@Override
	public void visit(DesignatorStatementDec designatorStatementDec) {
		if(designatorStatementDec.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		Code.load(designatorStatementDec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designatorStatementDec.getDesignator().obj);
	}
	
	
	@Override
	public void visit(DesignatorStatementList designatorStatementList) {
		int offset = designatorStatementList.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		if(designatorStatementList.getDesignator().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	@Override
	public void visit(SimpleExpr2 simpleExpr2){
		if(simpleExpr2.getAddop() instanceof Addop1) {
			Code.put(Code.add);
		} else {
			Code.put(Code.sub);
		}
	}
	
	@Override
	public void visit(TermList termList){
		if(termList.getMulop() instanceof Mulop1) {
			Code.put(Code.mul);
		} else if(termList.getMulop() instanceof Divop) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}
	
	@Override
	public void visit(MethodRetAndNameVoid methodRetAndNameVoid){
		methodRetAndNameVoid.obj.setAdr(Code.pc);
		if(methodRetAndNameVoid.getI1().equalsIgnoreCase("main")) {
			this.mainPc = Code.pc;
		}
		
		Code.put(Code.enter);
		Code.put(methodRetAndNameVoid.obj.getLevel() ); 
		Code.put(methodRetAndNameVoid.obj.getLocalSymbols().size()); 
	
	}
	
	@Override
	public void visit(MethodRetAndNameType methodRetAndNameType){
		methodRetAndNameType.obj.setAdr(Code.pc);
		
		Code.put(Code.enter);
		Code.put(methodRetAndNameType.obj.getLevel() ); 
		Code.put(methodRetAndNameType.obj.getLocalSymbols().size());  
	
	}
	
	@Override
	public void visit(MethodDecl methodDecl){
	//zavsne fje u okviru metode
		Code.put(Code.exit);  
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ReturnExpr returnExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	@Override
	public void visit(ReturnNoExpr returnNoExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	@Override
	public void visit(ReadStmt readStmt){
		if(readStmt.getDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		} else {
			Code.put(Code.read);
		}
		Code.store(readStmt.getDesignator().obj);
	}
	
	
	private int returnRelOp(Relop relop) {
		if(relop instanceof RelopEq)
			return Code.eq;
		else if(relop instanceof RelopNeq)
			return Code.ne;
		else if(relop instanceof RelopLess)
			return Code.lt;
		else if(relop instanceof RelopLesse)
			return Code.le;
		else if(relop instanceof RelopGr)
			return Code.gt;
		else if(relop instanceof RelopGre)
			return Code.ge;
		else
			return 0;
	}
	private Stack<Integer> skipCondFact = new Stack<>();
	private Stack<Integer> skipCondition = new Stack<>();
	private Stack<Integer> skipFalse = new Stack<>();
	private Stack<Integer> skipQ = new Stack<>();
	private Stack<Integer> skipElse = new Stack<>();

	private Stack<Integer> forConditionStart = new Stack<>();

	private Stack<Integer> skipIncrement = new Stack<>();
	private Stack<Integer> forIncStart = new Stack<>();
	
	private Stack<Integer> breakJump = new Stack<>();
	
	private Stack<Integer> switchNext = new Stack<>();
	private Stack<Integer> flagNext = new Stack<>();
	private Stack<Integer> flagNext1 = new Stack<>();


	private Stack<Integer> contextStack = new Stack<>();
	
	
	private int flagIndex = 255;
	
	@Override
	public void visit(CondFact2 condFact2) {
		if(jumpContextLevel < 1) {
			return;
		}
		if(condFact2.getParent().getParent().getParent().getParent().getParent() instanceof DesignatorExpr) {
			Code.put(Code.aload);
		}

		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);   //ako nije tacno skoci, ako je tacno ostane u andu
		skipCondFact.push(Code.pc - 2);
		
	}
	
	@Override
	public void visit(CondFact1 condFact1) {
		if(jumpContextLevel < 1) {
			return;
		}
		
		if(condFact1.getParent().getParent().getParent().getParent().getParent() instanceof DesignatorExpr) {
			Code.put(Code.aload);
		}

		Code.putFalseJump(returnRelOp(condFact1.getRelop()), 0); 
		skipCondFact.push(Code.pc - 2);
	}
	
	@Override
	public void visit(ConditionAdd conditionAdd) {
		if(jumpContextLevel <1) {
			return;
		}
		//prosli sve and-ove jednog or-a, eventualno pocinje sledeci
		Code.putJump(0); 
		skipCondition.push(Code.pc - 2);
		//ovde vracam netacne
		while(!skipCondFact.empty())
			Code.fixup(skipCondFact.pop());
		
	}
	
	@Override
	public void visit(ConditionStart conditionStart) {
		if(jumpContextLevel <1) {
			return;
		}
		//tacne
		Code.putJump(0); 
		skipCondition.push(Code.pc - 2);
		//namesta se da ako je neki netacan u andu skace na kraj term-a (da ide dalje ako ima sledeci or ili da zavrsi condition)
		while(!skipCondFact.empty())
			Code.fixup(skipCondFact.pop());
		
	}
	
	@Override
	public void visit(Empty empty) {
		if(!(empty.getParent() instanceof ConditionalExpr1))
			jumpContextLevel++; 
	}
	

	
	
	
	@Override
	public void visit(ConditionH1 conditionH1) {
	    jumpContextLevel--;
		//kraj svih or-ova
		Code.putJump(0); //ako je netacno ide se na else
		skipFalse.push(Code.pc - 2);
		//tacni ostaju
		while(!skipCondition.empty())
			Code.fixup(skipCondition.pop());
	}
	
	@Override
	public void visit(QuestionMark QuestionMark) {
	    jumpContextLevel--;
		//kraj svih or-ova
		Code.putJump(0); //ako je netacno ide se na else
		skipFalse.push(Code.pc - 2);
		//tacni ostaju
		while(!skipCondition.empty())
			Code.fixup(skipCondition.pop());
	}

//	@Override
//	public void visit(ConditionHl1 conditionHl1) {
//	    jumpContextLevel--;
//		//kraj svih or-ova
//
//		Code.putJump(0); //ako je netacno ide se na kraj
//		skipToEnd.push(Code.pc - 2);
//		

//		
//		
//		//tacni ostaju
//		while(!skipCondition.empty())
//			Code.fixup(skipCondition.pop());
//		//tacne
//	}
	
	@Override
	public void visit(UnmatchedIf unmatchedIf) {
		//nema elsa, samo netacne "ozive"
		Code.fixup(skipFalse.pop());
	}
	
	@Override
	public void visit(UnmatchedIfElse unmatchedIfElse) {
		//izbaci tacne ubaci netacne - U visitu else-a i na kraju se ozive tacne - ovde
		Code.fixup(skipElse.pop());
	}
	
	@Override
	public void visit(MatchedStatement matchedStatement) {
		Code.fixup(skipElse.pop());
	}
	
	@Override
	public void visit(Else e) {
		Code.putJump(0); //tacne bacamo na kraj ELSE
		skipElse.push(Code.pc - 2);
		Code.fixup(skipFalse.pop());
	}
	
	@Override
	public void visit(ForStmt forStmt) {  //kraj
		Code.putJump(forIncStart.pop());
		Code.fixup(skipFalse.pop());
		while(breakJump.peek() != -10) {
			Code.fixup(breakJump.pop());
		}
		breakJump.pop();
		contextStack.pop();
	}
	
	@Override
	public void visit(Semi0 semi0) {
		forConditionStart.push(Code.pc);
	}
	
	@Override
	public void visit(Semi1 semi1) {
		Code.putJump(0);
		skipIncrement.push(Code.pc - 2);
		forIncStart.push(Code.pc);
	}
	
	@Override
	public void visit(ForDesignatorStmt forDesignatorStmt) {
		Code.putJump(forConditionStart.pop());
		Code.fixup(skipIncrement.pop());
	}
	
	@Override
	public void visit(BreakStmt breakStmt) {
		Code.putJump(0);
		breakJump.push(Code.pc-2);
	}
	
	@Override
	public void visit(ForHelp forHelp) {
		contextStack.push(2);
		breakJump.push(-10);
	}
	
	@Override
	public void visit(ContinueStmt continueStmt) {
		for (int i = contextStack.size() - 1; i >= 0; i--) {
	        int c = contextStack.get(i);
	        if (c == 1) {  //switch
	        	Code.put(Code.pop);
	        } else if (c == 2) { //petlja
	            break;
	        }
	    }

		Code.putJump(forIncStart.peek());
	}
	
	@Override
	public void visit(SwitchHelp switchHelp) {
		contextStack.push(1);
		breakJump.push(-10);
		Code.loadConst(0);
		Code.put(Code.store);
		Code.put(flagIndex);
	}
	
	@Override
	public void visit(CaseHelp caseHelp) {
		Code.put(Code.load);
		Code.put(flagIndex);
		Code.loadConst(0);
		Code.putFalseJump(Code.eq, 0);
		flagNext1.push(Code.pc-2); 
		Code.put(Code.dup);
	}
	
	@Override
	public void visit(Number1 number) {
		int adr = number.getN1();
		Code.loadConst(adr);
	}
	
	@Override
	public void visit(Colon colon) {
		Code.put(Code.load);
		Code.put(flagIndex);
		Code.loadConst(0);
		Code.putFalseJump(Code.eq, 0);
		flagNext.push(Code.pc-2); 
		Code.putFalseJump(Code.eq, 0);
		switchNext.push(Code.pc-2);

		if(!flagNext.empty()) {
			Code.fixup(flagNext.pop());
		}
		
		if(!flagNext1.empty()) {
			Code.fixup(flagNext1.pop());
		}
	}
	
	@Override
	public void visit(StatementListHelp statementListHelp) {
		Code.loadConst(1);
		Code.put(Code.store);
		Code.put(flagIndex);
	}
	
	@Override
	public void visit(Case case1) {
		if(!switchNext.empty())
		Code.fixup(switchNext.pop());
	}
	
	@Override
	public void visit(SwitchStmt switchStmt) { //kraj
		Code.put(Code.pop);   //izbaci 1 expr viska sa steka
		contextStack.pop();
	}
	
	@Override
	public void visit(Rbrace rbrace) {
		while(breakJump.peek() != -10) {
			Code.fixup(breakJump.pop());
		}
		breakJump.pop();
	}
	
	
	@Override
	public void visit(ConditionalExprHelp  conditionalExprHelp ) {
		Code.putJump(0);
		skipQ.push(Code.pc - 2);
	}
	
	@Override
	public void visit(Colon1 colon1) {
		Code.fixup(skipFalse.pop());
	}
	
	@Override
	public void visit(ConditionalExpr2 conditionalExpr2) {
		Code.fixup(skipQ.pop());
	}
	
	@Override
	public void visit(DesignatorLen designatorLen) {
		Code.put(Code.arraylength);
	}
		
}
	

