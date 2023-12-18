package com.app.integraljjapi.api;


interface Visitor {
  Object visit(Exp e);
  Object visit(INT e);
  Object visit(Num e);
  Object visit(RNum e);
}
