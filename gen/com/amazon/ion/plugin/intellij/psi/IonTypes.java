// This is a generated file. Not intended for manual editing.
package com.amazon.ion.plugin.intellij.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.amazon.ion.plugin.intellij.psi.impl.*;

public interface IonTypes {

  IElementType ANNOTATION = new IonElementType("ANNOTATION");
  IElementType ARRAY = new IonElementType("ARRAY");
  IElementType ATOMS = new IonElementType("ATOMS");
  IElementType BLOB = new IonElementType("BLOB");
  IElementType CLOB = new IonElementType("CLOB");
  IElementType ELEMENTS = new IonElementType("ELEMENTS");
  IElementType EXPRESSION = new IonElementType("EXPRESSION");
  IElementType KEY = new IonElementType("KEY");
  IElementType MEMBERS = new IonElementType("MEMBERS");
  IElementType OBJECT = new IonElementType("OBJECT");
  IElementType PAIR = new IonElementType("PAIR");
  IElementType QQQ_STRING = new IonElementType("QQQ_STRING");
  IElementType QQ_STRING = new IonElementType("QQ_STRING");
  IElementType Q_STRING = new IonElementType("Q_STRING");
  IElementType STRING = new IonElementType("STRING");
  IElementType STRUCT = new IonElementType("STRUCT");
  IElementType SYMBOL = new IonElementType("SYMBOL");
  IElementType VALUE = new IonElementType("VALUE");

  IElementType ANNOTATION_SEPARATOR = new IonTokenType("ANNOTATION_SEPARATOR");
  IElementType BININT = new IonTokenType("BININT");
  IElementType BLOB_VALUE = new IonTokenType("BLOB_VALUE");
  IElementType BOOLEAN = new IonTokenType("BOOLEAN");
  IElementType COMMA = new IonTokenType("COMMA");
  IElementType COMMENT = new IonTokenType("COMMENT");
  IElementType DECIMAL = new IonTokenType("DECIMAL");
  IElementType HEXINT = new IonTokenType("HEXINT");
  IElementType IDENTIFIER = new IonTokenType("IDENTIFIER");
  IElementType INTEGER = new IonTokenType("INTEGER");
  IElementType KEY_NAME = new IonTokenType("KEY_NAME");
  IElementType LBRACE = new IonTokenType("LBRACE");
  IElementType LBRACKET = new IonTokenType("LBRACKET");
  IElementType LOB_END = new IonTokenType("LOB_END");
  IElementType LOB_START = new IonTokenType("LOB_START");
  IElementType LPAREN = new IonTokenType("LPAREN");
  IElementType NULL = new IonTokenType("NULL");
  IElementType OPERATOR = new IonTokenType("OPERATOR");
  IElementType QQQUOTE = new IonTokenType("QQQUOTE");
  IElementType QQQ_END = new IonTokenType("QQQ_END");
  IElementType QQQ_START = new IonTokenType("QQQ_START");
  IElementType QQQ_VALUE = new IonTokenType("QQQ_VALUE");
  IElementType QQUOTE = new IonTokenType("QQUOTE");
  IElementType QQ_END = new IonTokenType("QQ_END");
  IElementType QQ_START = new IonTokenType("QQ_START");
  IElementType QQ_VALUE = new IonTokenType("QQ_VALUE");
  IElementType QUOTE = new IonTokenType("QUOTE");
  IElementType Q_END = new IonTokenType("Q_END");
  IElementType Q_START = new IonTokenType("Q_START");
  IElementType Q_VALUE = new IonTokenType("Q_VALUE");
  IElementType RBRACE = new IonTokenType("RBRACE");
  IElementType RBRACKET = new IonTokenType("RBRACKET");
  IElementType RPAREN = new IonTokenType("RPAREN");
  IElementType SEPARATOR = new IonTokenType("SEPARATOR");
  IElementType TIMESTAMP = new IonTokenType("TIMESTAMP");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == ANNOTATION) {
        return new IonAnnotationImpl(node);
      }
      else if (type == ARRAY) {
        return new IonArrayImpl(node);
      }
      else if (type == ATOMS) {
        return new IonAtomsImpl(node);
      }
      else if (type == BLOB) {
        return new IonBlobImpl(node);
      }
      else if (type == CLOB) {
        return new IonClobImpl(node);
      }
      else if (type == ELEMENTS) {
        return new IonElementsImpl(node);
      }
      else if (type == EXPRESSION) {
        return new IonExpressionImpl(node);
      }
      else if (type == KEY) {
        return new IonKeyImpl(node);
      }
      else if (type == MEMBERS) {
        return new IonMembersImpl(node);
      }
      else if (type == OBJECT) {
        return new IonObjectImpl(node);
      }
      else if (type == PAIR) {
        return new IonPairImpl(node);
      }
      else if (type == QQQ_STRING) {
        return new IonQqqStringImpl(node);
      }
      else if (type == QQ_STRING) {
        return new IonQqStringImpl(node);
      }
      else if (type == Q_STRING) {
        return new IonQStringImpl(node);
      }
      else if (type == STRING) {
        return new IonStringImpl(node);
      }
      else if (type == STRUCT) {
        return new IonStructImpl(node);
      }
      else if (type == SYMBOL) {
        return new IonSymbolImpl(node);
      }
      else if (type == VALUE) {
        return new IonValueImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
