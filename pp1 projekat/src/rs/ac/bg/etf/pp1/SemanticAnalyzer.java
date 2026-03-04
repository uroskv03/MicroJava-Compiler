package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.structure.HashTableDataStructure;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;

public class SemanticAnalyzer extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	Obj currentMethod = null;
	boolean returnFound = false;
	boolean errorDetected = false;
	int nVars;
	private String currentEnumName = ""; 
	private int currentEnumVal = -1;
	
	private Struct currentType;
	private int constant;
	private Struct constantType;
	private Struct boolType = Tab.find("bool").getType();
	private Struct enumType = Tab.find("enum").getType();
	
	private Obj mainMethod;
	private boolean ret = false;
	
	Stack<List<Struct>> actParsList = new Stack<>();
	List<String> enumList = new ArrayList<>();
	SymbolDataStructure enumHash = new HashTableDataStructure();
	List<String> enumCurrList = new ArrayList<>();
	List<Integer> caseList = new ArrayList<>();
	private int loopCnt = 0;
	private int switchCnt = 0;
	private int fpPos = 1;
	
	Logger log = Logger.getLogger(getClass());
	private Obj currentProgram;

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
   @Override
    public void visit(ProgramName progName){
	   currentProgram = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
	   Tab.openScope();
	   actParsList.push(new ArrayList<>());
    }
	   
    @Override
    public void visit(Program program){
    	actParsList.pop();
    	nVars = Tab.currentScope.getnVars();
    	Tab.chainLocalSymbols(currentProgram); //dodaj globalne promenljive
    	Tab.closeScope();
    	currentProgram = null;
    	if(mainMethod == null || mainMethod.getLevel() > 0)
			report_error("Nema main metode", program);
    }
    
    @Override
    public void visit(Type type) {
    	Obj typeObj = Tab.find(type.getTypeName());
    	if(typeObj == Tab.noObj) {
    		report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", type);
    		type.struct = Tab.noType;
    		currentType = Tab.noType;
    	} 
    	else if (typeObj.getKind() != Obj.Type) {   // && !enumList.contains(type.getTypeName())
			report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
			type.struct = Tab.noType;
			currentType = Tab.noType;
    	} else {
    		type.struct = typeObj.getType();
    		currentType = typeObj.getType();
    	}
    }

    @Override
	public void visit(ConstFirst ConstFirst) {
		Obj constObj = Tab.find(ConstFirst.getI1());
		if(constObj != Tab.noObj) {
			report_error("Dvostruka definicija konstante: " + ConstFirst.getI1(), ConstFirst);
		}
		else {
			if(constantType.assignableTo(currentType)) {
				constObj = Tab.insert(Obj.Con, ConstFirst.getI1(), currentType);  
				constObj.setAdr(constant);
			}
			else {
				report_error("Neadekvatna dodela konstante: " + ConstFirst.getI1(), ConstFirst);
			}
		}
	}
	
	@Override
	public void visit(ConstTypeN constTypeN) {
		constant = constTypeN.getN1();
		constantType = Tab.intType;
	}
	
	@Override
	public void visit(ConstTypeC constTypeC) {
		constant = constTypeC.getC1();
		constantType = Tab.charType;
	}
	
	@Override
	public void visit(ConstTypeB constTypeB) {
		constant = constTypeB.getB1();
		constantType = boolType;
	}
	
	@Override
	public void visit(VarIdent1 var_ident1) {
		Obj varObj = null;
		if(currentMethod == null) 
			varObj = Tab.find(var_ident1.getI1()); //globalne
		else
			varObj = Tab.currentScope().findSymbol(var_ident1.getI1());
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, var_ident1.getI1(), currentType);  
			varDeclCount++;
		}
		else{
			report_error("Dvostruka definicija promenljive: " + var_ident1.getI1(), var_ident1);
		}
	}
	
	@Override
	public void visit(VarIdent2 var_ident_array) {
		Obj varObj = null;
		if(currentMethod == null) 
			varObj = Tab.find(var_ident_array.getI1());
		else
			varObj = Tab.currentScope().findSymbol(var_ident_array.getI1());
		
		if(varObj == null || varObj == Tab.noObj) {
			varObj = Tab.insert(Obj.Var, var_ident_array.getI1(), new Struct(Struct.Array, currentType));
			varDeclCount++; 
		}
		else{
			report_error("Dvostruka definicija promenljive: " + var_ident_array.getI1(), var_ident_array);
		}
	}
	
	@Override
	public void visit(MethodRetAndNameVoid methodRetAndNameVoid) {
		methodRetAndNameVoid.obj = Tab.insert(Obj.Meth, methodRetAndNameVoid.getI1(), Tab.noType);
		currentMethod = methodRetAndNameVoid.obj;
		Tab.openScope();
		if(methodRetAndNameVoid.getI1().equalsIgnoreCase("main"))
			mainMethod = currentMethod;
	}
	
	@Override
	public void visit(MethodRetAndNameType methRetAndNameType) {
		methRetAndNameType.obj = Tab.insert(Obj.Meth, methRetAndNameType.getI2(), currentType);
		currentMethod = methRetAndNameType.obj;
		Tab.openScope();
	}
	
//	@Override
//	public void visit(FormParams formParams) {
//	    Tab.chainLocalSymbols(currentMethod);
//	}
	
	
	
	@Override
	public void visit(MethHelp MethHelp) {
	    Tab.chainLocalSymbols(currentMethod);
	}
	
	
	@Override
	public void visit(MethodDecl methodDecl) {
		//Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		fpPos = 1;
		if(currentMethod.getType() != Tab.noType && !ret) {
			report_error("Nema return u metodi koja mora da ime povratnu vrednost " + currentMethod.getName(),methodDecl);
		}
		
		ret = false;
		currentMethod = null;
	}
	
	
	@Override
	public void visit(FormalParamDecl1 formalParamDecl1) {
		Obj paramObj = null;
		if(currentMethod == null)
			report_error("Semanticka greska u FormalParamDecl1", formalParamDecl1);
		else
			paramObj = Tab.currentScope().findSymbol(formalParamDecl1.getI2());
		
		if(paramObj == null || paramObj == Tab.noObj) {
			paramObj = Tab.insert(Obj.Var, formalParamDecl1.getI2(), currentType);
			paramObj.setFpPos(fpPos);
			fpPos++;
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
		else{
			report_error("Dvostruka definicija formalnog parametra: " + formalParamDecl1.getI2(), formalParamDecl1);
		}
	}
	
	@Override
	public void visit(FormalParamDecl2 formalParamDecl2) {
		Obj paramObj = null;
		if(currentMethod == null)
			report_error("Semanticka greska u FormalParamDecl2", formalParamDecl2);
		else
			paramObj = Tab.currentScope().findSymbol(formalParamDecl2.getI2());
		
		if(paramObj == null || paramObj == Tab.noObj) {
			paramObj = Tab.insert(Obj.Var, formalParamDecl2.getI2(), new Struct(Struct.Array, currentType));
			paramObj.setFpPos(fpPos);
			fpPos++;
			currentMethod.setLevel(currentMethod.getLevel() + 1);
		}
		else{
			report_error("Dvostruka definicija formalnog parametra: " + formalParamDecl2.getI2(), formalParamDecl2);
		}
	}

	
	@Override
	public void visit(ActParsStart actParsStart) {
		List<Struct> a = new ArrayList<>();
		a.add(actParsStart.getExpr().struct);
		actParsList.push(a);
	}
	
	@Override
	public void visit(ActParsAdd actParsAdd) {
		List<Struct> a = actParsList.pop();
		a.add(actParsAdd.getExpr().struct);
		actParsList.push(a);
	}
	
	@Override
	public void visit(ActParsListE actParsListE) {
		 actParsList.add(new ArrayList<>());
	}	
	
	@Override 
	public void visit(FuncCall funcCall) {
		if(funcCall.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Poziv ne metode " + funcCall.getDesignator().obj.getName(), funcCall);
			funcCall.struct = Tab.noType;
		} else {
			boolean err = false;
			funcCall.struct = funcCall.getDesignator().obj.getType();
			List<Struct> list = new ArrayList<>();
			for(Obj local: funcCall.getDesignator().obj.getLocalSymbols()) {
				if(local.getKind() == Obj.Var && local.getLevel() >= 1 && local.getFpPos() >= 1) {
					list.add(local.getType());
				}
			}
			List<Struct> a = actParsList.pop();
			if(list.size() != a.size()) {
				report_error("Razlicit broj argumenata poziva metode " + funcCall.getDesignator().obj.getName() + " " + objToString(funcCall.getDesignator().obj) + "vel1: " + list.size() + " vel2: " + a.size() , funcCall);
				err = true;
			} else {
				for(int i = 0; i < list.size(); i++) {
					Struct el1 = list.get(i);  //tipovi koje prima fja
					Struct el2 = a.get(i);  //tipovi koji su dosli
					if(!el2.assignableTo(el1) && !((el1.getKind() == Struct.Int || el1.getKind() == Struct.Enum) && el2.getKind() == Struct.Enum)) {
						report_error("Losi tipovi argumenata metode " + funcCall.getDesignator().obj.getName(), funcCall);
						err = true;
					}
				}
			}
			if(!err) {
				report_info("Poziv globalne fje: " + funcCall.getDesignator().obj.getName() + objToString(funcCall.getDesignator().obj) , funcCall);
			}
		}

	}
	

	@Override 
	public void visit(CharConst charConst) {
		charConst.struct = Tab.charType;
	}
	
	@Override 
	public void visit(BoolConst boolConst) {
		boolConst.struct = boolType;
	}
	
	@Override 
	public void visit(NumConst numConst) {
		numConst.struct = Tab.intType;
	}
	
	@Override 
	public void visit(Var var) {
		var.struct = var.getDesignator().obj.getType();
		if(var.getDesignator().obj.getFpPos() == -1) {
			var.struct = enumType;
		} 

	}
	
	@Override
	public void visit(NewFacotr newFacotr) {
		if(!newFacotr.getExpr().struct.equals(Tab.intType) && newFacotr.getExpr().struct.getKind() != Struct.Enum) {
			report_error("Velicina niza mora biti int" , newFacotr);
			newFacotr.struct = Tab.noType;	
		} else {
			newFacotr.struct = new Struct(Struct.Array, currentType);
		}
	}
	
	@Override
	public void visit(ExprFacotr exprFacotr) {
		exprFacotr.struct = exprFacotr.getExpr().struct;
	}
	
	@Override
	public void visit(NegFactor negFacotr) {
		if(negFacotr.getFactor().struct.equals(Tab.intType) || negFacotr.getFactor().struct.getKind() == Struct.Enum) {
			negFacotr.struct = Tab.intType;
		} else {
			report_error("Negacija tipa koji nije int", negFacotr);
			negFacotr.struct = Tab.noType;
		}
	}

	@Override
	public void visit(Term1 term1) {
		term1.struct = term1.getFactor().struct;
	}
	
	@Override
	public void visit(TermList termList) {
		Struct l = termList.getTerm().struct;
		Struct r = termList.getFactor().struct;
		if((l.equals(Tab.intType) || l.getKind() == Struct.Enum)  && ( r.equals(Tab.intType)|| r.getKind() == Struct.Enum ) ) {
			termList.struct = Tab.intType;
		} else {
			report_error("Mulop ne int tipom" , termList);
			termList.struct = Tab.noType;
		}
		
	}

	@Override
	public void visit(SimpleExpr1 simpleExpr1) {
		simpleExpr1.struct = simpleExpr1.getTerm().struct;
	}
	
	@Override
	public void visit(SimpleExpr2 simpleExpr2) {
		Struct l = simpleExpr2.getSimpleExpr().struct;
		Struct r = simpleExpr2.getTerm().struct;
		if((l.equals(Tab.intType) || l.getKind() == Struct.Enum)  && ( r.equals(Tab.intType)|| r.getKind() == Struct.Enum ) ) {
			simpleExpr2.struct = Tab.intType;
		} else {
			report_error("Addop ne int tipom" , simpleExpr2);
			simpleExpr2.struct = Tab.noType;
		}
		
	}
	
	@Override
	public void visit(Expr expr) {
		expr.struct = expr.getConditionalExpr().struct;
	}

	@Override
	public void visit(ConditionalExpr1 conditionalExpr1) {
		conditionalExpr1.struct = conditionalExpr1.getCondition().struct;
	}
	
	@Override
	public void visit(ConditionalExpr2 conditionalExpr2) {
		if(!conditionalExpr2.getConditionalExpr().struct.equals(conditionalExpr2.getConditionalExprHelp().getConditionalExpr().struct)) {
			report_error("Izrazi Expr nisu istog tipa za operator ?", conditionalExpr2);
			conditionalExpr2.struct = Tab.noType;
		} else {
			conditionalExpr2.struct =  conditionalExpr2.getConditionalExpr().struct;
		}
	}
	
	@Override
	public void visit(ConditionH1 conditionH1) {
		if(!conditionH1.getCondition().struct.equals(boolType)) {
			report_error("Uslov nije tipa bool " , conditionH1);
		}
	}
	
	@Override
	public void visit(ConditionStart conditionStart) {
		conditionStart.struct = conditionStart.getCondTerm().struct;
	}
	
	@Override
	public void visit(ConditionAdd conditionAdd) {
		if(conditionAdd.getCondition().struct.equals(boolType) && conditionAdd.getCondTerm().struct.equals(boolType)) {
			conditionAdd.struct = boolType;
		} else {
			report_error("OR sa ne bool tipom: " , conditionAdd);
			conditionAdd.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(CondTermStart condTermStart) {
		condTermStart.struct = condTermStart.getCondFact().struct;
	}
	
	@Override
	public void visit(CondTermAdd condTermAdd) {
		if(condTermAdd.getCondFact().struct.equals(boolType) && condTermAdd.getCondTerm().struct.equals(boolType)) {
			condTermAdd.struct = boolType;
		} else {
			report_error("AND sa ne bool tipom: " , condTermAdd);
			condTermAdd.struct = Tab.noType;
		}
	}

	@Override
	public void visit(CondFact1 condFact1) {
		Struct l = condFact1.getSimpleExpr().struct;
		Struct r = condFact1.getSimpleExpr1().struct;
		if(l.compatibleWith(r) || ((l.getKind() == Struct.Enum || l.getKind() == Struct.Int) && (r.getKind() == Struct.Enum || r.getKind() == Struct.Int))) {
			if(l.isRefType() || r.isRefType()) {
				if(condFact1.getRelop() instanceof Relop || condFact1.getRelop() instanceof RelopNeq ) {
					condFact1.struct = boolType;
				} else {
					report_error("Relop pogresna op sa nizovima: " , condFact1);
					condFact1.struct = Tab.noType;
				}
			} else {
				condFact1.struct = boolType;
			}
			
		} else {
			report_error("Relop sa nekompatibilnim tipovima: " + l.getKind() + " " + r.getKind() , condFact1);
			condFact1.struct = Tab.noType;
		}
	}
	
	@Override
	public void visit(CondFact2 condFact2) {
		condFact2.struct = condFact2.getSimpleExpr().struct;
	}
	
	@Override
	public void visit(DesignatorIdent designatorIdent) {
		Obj var = Tab.find(designatorIdent.getI1());
		if(var == Tab.noObj) {
			report_error("Nedefinisana promenljiva: " + designatorIdent.getI1(), designatorIdent);
			designatorIdent.obj = Tab.noObj;
		} else if(var.getKind() != Obj.Var && var.getKind() != Obj.Con && var.getKind() != Obj.Meth) {  
			report_error("Los kind promenljive: " + designatorIdent.getI1(), designatorIdent);
			designatorIdent.obj = Tab.noObj;
		} else {
			designatorIdent.obj = var;
			if(designatorIdent.obj.getKind() == Obj.Con) {
				report_info("Simbolicka konstanta: " + var.getName() + objToString(designatorIdent.obj) ,designatorIdent);
			} else if(designatorIdent.obj.getKind() == Obj.Var) {
				if(designatorIdent.obj.getLevel() == 0) {
					report_info("Globalna prom: " + var.getName() + objToString(designatorIdent.obj) ,designatorIdent);
				} else {
					if (designatorIdent.obj.getFpPos() > 0) {
						report_info("Formalni arg : " + var.getName() + ", " + objToString(designatorIdent.obj), designatorIdent);
					} else {
						report_info("Lokalna prom: " + var.getName() + objToString(designatorIdent.obj) ,designatorIdent);
					}
				}
			}
		}
	}
	
	public void visit(DesignatorDotIdent designatorDotIdent) {
		Obj obj = Tab.find(designatorDotIdent.getDesignatorName().getI1());
		String p = designatorDotIdent.getDesignatorName().getI1() + "." + designatorDotIdent.getI2();
		Obj matchedObj = null;
		if(obj.getType().getKind() != Struct.Enum) {
			report_error("Nije tipa enum: ", designatorDotIdent);
			designatorDotIdent.obj  = Tab.noObj;
		} else {
			for (Obj obj1 : obj.getLocalSymbols()) {
				if(p.equals(obj1.getName())) {
					matchedObj = obj1;
					break;
				}
			}
			if(matchedObj != null) {
				designatorDotIdent.obj  = matchedObj;
				designatorDotIdent.obj.setFpPos(-1);
			} else {
				report_error("Nije deo enum-a: " + p, designatorDotIdent);
				designatorDotIdent.obj  = Tab.noObj;
			}
		}
	}
	
	public void visit(DesignatorLen designatorLen) {
		Obj obj = Tab.find(designatorLen.getDesignatorName().getI1());
		if(obj.getType().getKind() != Struct.Array) {
			report_error("Len mora da bude od niza" + obj.getKind() + " r  " + Struct.Array, designatorLen);
			designatorLen.obj = Tab.noObj;
		} else {
			designatorLen.obj = Tab.lenObj;			
		}
	}
	
	@Override
	public void visit(DesignatorName designatorName) {
		Obj obj = Tab.find(designatorName.getI1());    //OVO MOZE DA BUDE ENUM ILI NIZ
		if(obj == Tab.noObj) { 
			report_error("Nedefinisan niz ili enum: " + designatorName.getI1(), designatorName);
			designatorName.obj = Tab.noObj;
		} else if(obj.getKind() != Obj.Var || obj.getType().getKind() != Struct.Array) {   
			if(obj.getType().getKind() != Struct.Enum) {
				report_error("Los kind niza ili enuma: " + designatorName.getI1(), designatorName);
				designatorName.obj = Tab.noObj;
			} else {
				designatorName.obj = obj;  
				designatorName.obj.setFpPos(-1);
			}
		} else {
			designatorName.obj = obj;
		}
	}
	
	@Override
	public void visit(DesignatorExpr designatorExpr) {
		Obj arr = designatorExpr.getDesignatorName().obj;
		if(arr == Tab.noObj) {
			designatorExpr.obj = Tab.noObj;
		} else if (arr.getType().getKind() == Struct.Enum) {
			report_error("Nedefinisan niz - pokusaj sa enumom" , designatorExpr);
			designatorExpr.obj = Tab.noObj;
		} else if (!designatorExpr.getExpr().struct.equals(Tab.intType) && designatorExpr.getExpr().struct.getKind() != Struct.Enum) {   
			report_error("Index mora biti int" , designatorExpr);
			designatorExpr.obj = Tab.noObj;	
		} else {
			designatorExpr.obj = new Obj(Obj.Elem, arr.getName() + "[$]", arr.getType().getElemType());
			report_info("Pristup el niza: " + arr.getName() + objToString(designatorExpr.obj) ,designatorExpr);
		}
	}

	@Override
	public void visit(DesignatorStatementExpr designatorStatementExpr) {
		Struct el1 = designatorStatementExpr.getExpr().struct;
		Struct el2 = designatorStatementExpr.getDesignator().obj.getType();
		int kind = designatorStatementExpr.getDesignator().obj.getKind();
		if(kind!= Obj.Var && kind != Obj.Elem) {   
			report_error("Losa dodela vrednosti promenljive: " + designatorStatementExpr.getDesignator().obj.getName(), designatorStatementExpr);	
		} else if(!el1.assignableTo(el2) && !((el2.getKind() == Struct.Int || el2.getKind() == Struct.Enum) && el1.getKind() == Struct.Enum) ) {  //&& !(el1.getKind() == Struct.Enum && kind == 1)
			report_error("Losa dodela vrednosti, nije assignableTo:  "  + designatorStatementExpr.getDesignator().obj.getName(), designatorStatementExpr);	
		}
	}
	
	@Override
	public void visit(DesignatorStatementInc designatorStatementInc) {
		int kind = designatorStatementInc.getDesignator().obj.getKind();
		if(kind != Obj.Var && kind != Obj.Elem) {
			report_error("Los inkrement: " + designatorStatementInc.getDesignator().obj.getName(), designatorStatementInc);	
		} else if(!designatorStatementInc.getDesignator().obj.getType().equals(Tab.intType)) {
			report_error("Inkrement ne int tipa   " + designatorStatementInc.getDesignator().obj.getName(), designatorStatementInc);	
		} 
	}
	
	@Override
	public void visit(DesignatorStatementDec designatorStatementDec) {
		int kind = designatorStatementDec.getDesignator().obj.getKind();
		if(kind != Obj.Var && kind != Obj.Elem) {
			report_error("Los dekrement: " + designatorStatementDec.getDesignator().obj.getName(), designatorStatementDec);	
		} else if(!designatorStatementDec.getDesignator().obj.getType().equals(Tab.intType)) {
			report_error("Dekrement ne int tipa   " + designatorStatementDec.getDesignator().obj.getName(), designatorStatementDec);	
		} 
	}
	
	
	@Override 
	public void visit(DesignatorStatementList designatorStatementList) {
		if(designatorStatementList.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("DesignatorStatementList nije ime metode " + designatorStatementList.getDesignator().obj.getName(), designatorStatementList);
		} else {
			boolean err = false;
			List<Struct> list = new ArrayList<>();
			for(Obj local: designatorStatementList.getDesignator().obj.getLocalSymbols()) {
				if(local.getKind() == Obj.Var && local.getLevel() >= 1 && local.getFpPos() >= 1) {
					list.add(local.getType());
				}
			}
			List<Struct> a = actParsList.pop();
			if(list.size() != a.size()) {
				report_error("Razlicit broj argumenata poziva metode " + designatorStatementList.getDesignator().obj.getName() + "vel1: " + list.size() + " vel2: " + a.size(), designatorStatementList);
				err = true;
			} else {
				for(int i = 0; i < list.size(); i++) {
					Struct el1 = list.get(i);
					Struct el2 = a.get(i);
					if(!el2.assignableTo(el1) && !((el1.getKind() == Struct.Int || el1.getKind() == Struct.Enum) && el2.getKind() == Struct.Enum)) {
						report_error("Losi tipovi argumenata metode " + designatorStatementList.getDesignator().obj.getName(), designatorStatementList);
						err = true;
					}
				}
			}
			if(!err) {   
				report_info("Poziv globalne fje: " + designatorStatementList.getDesignator().obj.getName() + objToString(designatorStatementList.getDesignator().obj) , designatorStatementList);
			}
		}
	}
	

	@Override
	public void visit(ReadStmt readStmt) {
		int kind = readStmt.getDesignator().obj.getKind();
		Struct type = readStmt.getDesignator().obj.getType();
		if(kind != Obj.Var && kind != Obj.Elem) {
			report_error("Los read: " + readStmt.getDesignator().obj.getName(), readStmt);	
		} else if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType) && type.getKind() != 6) {
			report_error("Read ne int, char ili bool tipa   " + readStmt.getDesignator().obj.getName(), readStmt);	
		} 
	}
	
	@Override
	public void visit(Printer printer) {
		Struct type = printer.getExpr().struct;
		if(!type.equals(Tab.intType) && !type.equals(Tab.charType) && !type.equals(boolType) && !type.equals(enumType) && type.getKind() != 6) {
			report_error("Print loseg tipa " , printer);	
		} 
	}
	
    public void visit(PrintStmt print) {
		printCallCount++;
	}
	
	
	@Override
	public void visit(ReturnExpr returnExpr) {
		ret = true;
		Struct el1 = currentMethod.getType();  //ret vr fjee     el1=el2
		Struct el2 = returnExpr.getExpr().struct; //ono sto se vrati
		if(!el2.assignableTo(el1) && !((el1.getKind() == Struct.Int || el1.getKind() == Struct.Enum) && el2.getKind() == Struct.Enum)) {
			report_error("Return pogresne vrednosti za metodu: " + currentMethod.getName(), returnExpr);	
		}
//		if(!currentMethod.getType().equals(returnExpr.getExpr().struct) && !(currentMethod.getType().getKind() ==  returnExpr.getExpr().struct.getKind() &&  returnExpr.getExpr().struct.getKind() == 6)) {
//			report_error("Return pogresne vrednosti za metodu: " + currentMethod.getName(), returnExpr);	
//		}
	}
	
	
	@Override
	public void visit(ReturnNoExpr returnNoExpr) {
		ret = true;
		if(currentMethod.getType() != Tab.noType) {
			report_error("Return bez povratne vrednosti za ne void metode: " + currentMethod.getName() , returnNoExpr);	
		}
	}
	
	@Override
	public void visit(ForHelp forHelp) {
		loopCnt++;
	}
	
	@Override
	public void visit(ForStmt forStmt) {
		loopCnt--;
	}
	
	@Override
	public void visit(SwitchHelp switchHelp) {
		switchCnt ++;
	}
	
	@Override
	public void visit(SwitchStmt switchStmt) {
		switchCnt --;
		if(!switchStmt.getExpr().struct.equals(Tab.intType) && switchStmt.getExpr().struct.getKind() != Struct.Enum) {
			report_error("Switch po epxru koji nije tipa int: ", switchStmt);
		}
	}
	
	@Override
	public void visit(CaseList1 caseList1) {
		caseList.clear();
		caseList.add(caseList1.getCase().getNumber1().getN1());
	}
	
	@Override
	public void visit(CaseList2 caseList2) {
		if(caseList.contains(caseList2.getCase().getNumber1().getN1())) {
			report_error("Dva ista broja za razlicite case-eve switcha: " + caseList2.getCase().getNumber1().getN1(), caseList2);
		} else {
			caseList.add(caseList2.getCase().getNumber1().getN1());
		}
		
	}
		
	@Override
	public void visit(ContinueStmt continueStmt) {
		if(loopCnt == 0) {
			report_error("Continue van petlje ",continueStmt);
		}
	}
	
	@Override
	public void visit(BreakStmt breakStmt) {
		if(loopCnt == 0 && switchCnt == 0) {
			report_error("Break van petlje ili switcha",breakStmt);
		}
	}
	

	@Override
	public void visit(EnumDecl enumDecl) {
		currentEnumVal = -1;
		if(enumList.contains(enumDecl.getEnumName().getI1())) {
			enumDecl.struct = Tab.noType;
			report_error("Dvostruka deklaracija enuma sa imenom: " + enumDecl.getEnumName().getI1()  ,enumDecl);
		} else {
			enumList.add(enumDecl.getEnumName().getI1());
			
			Collection<Obj> d = enumHash.symbols();
			SymbolDataStructure currHash = new HashTableDataStructure();
			
			for (Obj obj : d) {
				currHash.insertKey(obj);
			}
			
			enumDecl.struct = new Struct(Struct.Enum,currHash);
			Obj varObj = Tab.insert(Obj.Type, enumDecl.getEnumName().getI1(), enumDecl.struct); // new Struct(Struct.Enum, currHash)
			varObj.setLocals(currHash);
			enumHash = new HashTableDataStructure();
		}
	}
	
	
	@Override
	public void visit(EnumName enumName) {
		currentEnumName = enumName.getI1();
	}
	
	@Override
	public void visit(EnumIdent1 enumIdent1) {
		//enumIdent1.obj = Tab.insert(Obj.Con, currentEnumName + "." + enumIdent1.getI1(), Tab.intType);
		enumIdent1.obj = new Obj(Obj.Con, currentEnumName + "." + enumIdent1.getI1(), Tab.intType);
		if(!enumHash.insertKey(enumIdent1.obj)) {
			report_error("Dvostruka deklaracija polja enuma sa imenom: " + enumIdent1.getI1()  , enumIdent1);
		} else {
			currentEnumVal++;
			enumIdent1.obj.setAdr(currentEnumVal);
		}
	}
	
	@Override
	public void visit(EnumIdent2 enumIdent2) {
		currentEnumVal = enumIdent2.getN2();
		//enumIdent2.obj = Tab.insert(Obj.Con, currentEnumName + "." +  enumIdent2.getI1(), Tab.intType); 
		enumIdent2.obj = new Obj(Obj.Con, currentEnumName + "." +  enumIdent2.getI1(), Tab.intType);
		if(!enumHash.insertKey(enumIdent2.obj)) {
			report_error("Dvostruka deklaracija polja enuma sa imenom: " + enumIdent2.getI1()  , enumIdent2);
		} else {
			enumIdent2.obj.setAdr(currentEnumVal);;
		}
	}
	
	@Override
	public void visit(EnumIdentList enumIdentList) {

	}
	
	private String objToString(Obj obj) {
	    if (obj == null) return "null";
	    String kindName ="";
	    switch(obj.getKind()) {
		    case Obj.Con:  kindName = "Con"; break;
	        case Obj.Var:  kindName = "Var"; break;
	        case Obj.Type:  kindName = "Type"; break;
	        case Obj.Meth: kindName = "Meth"; break;
	        case Obj.Fld:  kindName = "Fld"; break;
	        case Obj.Elem: kindName = "Elem"; break;
	        case Obj.Prog: kindName = "Prog"; break;
	        default: kindName = "None";
	    }
	    return String.format("[Kind:%s, Type:%d, Adr:%d, Lev:%d]", 
                kindName, obj.getType().getKind(), obj.getAdr(), obj.getLevel());
	}
	
	
	
	
    public boolean passed(){
    	return !errorDetected;
    }
    
}
