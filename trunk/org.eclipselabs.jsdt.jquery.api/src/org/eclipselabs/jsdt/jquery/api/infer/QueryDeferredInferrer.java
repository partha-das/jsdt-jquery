package org.eclipselabs.jsdt.jquery.api.infer;

import org.eclipse.wst.jsdt.core.ast.IASTNode;
import org.eclipse.wst.jsdt.core.ast.IAssignment;
import org.eclipse.wst.jsdt.core.ast.IExpression;
import org.eclipse.wst.jsdt.core.ast.IFunctionCall;
import org.eclipse.wst.jsdt.core.ast.ILocalDeclaration;
import org.eclipse.wst.jsdt.core.infer.InferredType;

class QueryDeferredInferrer extends JQueryInferrerSupport {
  

  private static InferredType jQueryDeferred;

  static {
    char[] selector = "jQueryDeferred".toCharArray();
    jQueryDeferred = new InferredType(selector);
  }

  QueryDeferredInferrer(boolean noConflict) {
    super(noConflict);
  }
  
  @Override
  public boolean visit(ILocalDeclaration localDeclaration) {
    if (localDeclaration.getAssignment() != null) {
      // will be handled in other case
      return super.visit(localDeclaration);
    }
    IExpression expression = localDeclaration.getInitialization();
    if (isDeferredConstructorCall(expression)) {
      localDeclaration.setInferredType(jQueryDeferred);
    }
    return super.visit(localDeclaration);
  }
  
  @Override
  public boolean visit(IAssignment assignment) {
    IExpression expression = assignment.getExpression();
    if (isDeferredConstructorCall(expression)) {
      assignment.setInferredType(jQueryDeferred);
    }
    return super.visit(assignment);
  }
  
  private boolean isDeferredConstructorCall(IExpression expression) {
    if (expression.getASTType() == IASTNode.FUNCTION_CALL) {
      IFunctionCall functionCall = (IFunctionCall) expression;
      char[] selector = functionCall.getSelector();
      return (isDeferred(selector) && isJQueryStatic(functionCall.getReceiver()));
    }
    return false;
  }

  static boolean isDeferred(char[] selector) {
    // Deferred
    return selector.length == 8
        && selector[0] == 'D'
        && selector[1] == 'e'
        && selector[2] == 'f'
        && selector[3] == 'e'
        && selector[4] == 'r'
        && selector[5] == 'r'
        && selector[6] == 'e'
        && selector[7] == 'd';
  }

}
