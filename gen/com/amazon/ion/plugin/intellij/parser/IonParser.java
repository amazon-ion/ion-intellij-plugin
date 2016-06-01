// This is a generated file. Not intended for manual editing.
package com.amazon.ion.plugin.intellij.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.amazon.ion.plugin.intellij.psi.IonTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class IonParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == ANNOTATION) {
      r = annotation(b, 0);
    }
    else if (t == ARRAY) {
      r = array(b, 0);
    }
    else if (t == ATOMS) {
      r = atoms(b, 0);
    }
    else if (t == BLOB) {
      r = blob(b, 0);
    }
    else if (t == CLOB) {
      r = clob(b, 0);
    }
    else if (t == ELEMENTS) {
      r = elements(b, 0);
    }
    else if (t == EXPRESSION) {
      r = expression(b, 0);
    }
    else if (t == KEY) {
      r = key(b, 0);
    }
    else if (t == MEMBERS) {
      r = members(b, 0);
    }
    else if (t == OBJECT) {
      r = object(b, 0);
    }
    else if (t == PAIR) {
      r = pair(b, 0);
    }
    else if (t == Q_STRING) {
      r = q_string(b, 0);
    }
    else if (t == QQ_STRING) {
      r = qq_string(b, 0);
    }
    else if (t == QQQ_STRING) {
      r = qqq_string(b, 0);
    }
    else if (t == STRING) {
      r = string(b, 0);
    }
    else if (t == STRUCT) {
      r = struct(b, 0);
    }
    else if (t == SYMBOL) {
      r = symbol(b, 0);
    }
    else if (t == VALUE) {
      r = value(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return ionFile(b, l + 1);
  }

  /* ********************************************************** */
  // (IDENTIFIER | q_string) ANNOTATION_SEPARATOR
  public static boolean annotation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotation")) return false;
    if (!nextTokenIs(b, "<annotation>", IDENTIFIER, Q_START)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ANNOTATION, "<annotation>");
    r = annotation_0(b, l + 1);
    r = r && consumeToken(b, ANNOTATION_SEPARATOR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // IDENTIFIER | q_string
  private static boolean annotation_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotation_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = q_string(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (LBRACKET RBRACKET) | (LBRACKET elements RBRACKET) | (LBRACKET elements COMMA RBRACKET)
  public static boolean array(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array")) return false;
    if (!nextTokenIs(b, LBRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = array_0(b, l + 1);
    if (!r) r = array_1(b, l + 1);
    if (!r) r = array_2(b, l + 1);
    exit_section_(b, m, ARRAY, r);
    return r;
  }

  // LBRACKET RBRACKET
  private static boolean array_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LBRACKET, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // LBRACKET elements RBRACKET
  private static boolean array_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACKET);
    r = r && elements(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // LBRACKET elements COMMA RBRACKET
  private static boolean array_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACKET);
    r = r && elements(b, l + 1);
    r = r && consumeTokens(b, 0, COMMA, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (OPERATOR | value)+
  public static boolean atoms(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atoms")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ATOMS, "<atoms>");
    r = atoms_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!atoms_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "atoms", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // OPERATOR | value
  private static boolean atoms_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atoms_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, OPERATOR);
    if (!r) r = value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LOB_START (BLOB_VALUE)? LOB_END
  public static boolean blob(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blob")) return false;
    if (!nextTokenIs(b, LOB_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LOB_START);
    r = r && blob_1(b, l + 1);
    r = r && consumeToken(b, LOB_END);
    exit_section_(b, m, BLOB, r);
    return r;
  }

  // (BLOB_VALUE)?
  private static boolean blob_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blob_1")) return false;
    consumeToken(b, BLOB_VALUE);
    return true;
  }

  /* ********************************************************** */
  // LOB_START (string)? LOB_END
  public static boolean clob(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "clob")) return false;
    if (!nextTokenIs(b, LOB_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LOB_START);
    r = r && clob_1(b, l + 1);
    r = r && consumeToken(b, LOB_END);
    exit_section_(b, m, CLOB, r);
    return r;
  }

  // (string)?
  private static boolean clob_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "clob_1")) return false;
    clob_1_0(b, l + 1);
    return true;
  }

  // (string)
  private static boolean clob_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "clob_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = string(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // value (COMMA value)*
  public static boolean elements(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elements")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ELEMENTS, "<elements>");
    r = value(b, l + 1);
    r = r && elements_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (COMMA value)*
  private static boolean elements_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elements_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!elements_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "elements_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA value
  private static boolean elements_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elements_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (LPAREN  RPAREN) | (LPAREN atoms RPAREN) | (LPAREN atoms COMMA RPAREN)
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression_0(b, l + 1);
    if (!r) r = expression_1(b, l + 1);
    if (!r) r = expression_2(b, l + 1);
    exit_section_(b, m, EXPRESSION, r);
    return r;
  }

  // LPAREN  RPAREN
  private static boolean expression_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LPAREN, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // LPAREN atoms RPAREN
  private static boolean expression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && atoms(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // LPAREN atoms COMMA RPAREN
  private static boolean expression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && atoms(b, l + 1);
    r = r && consumeTokens(b, 0, COMMA, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean ionFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ionFile")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ionFile", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // value
  static boolean item_(PsiBuilder b, int l) {
    return value(b, l + 1);
  }

  /* ********************************************************** */
  // (QUOTE KEY_NAME QUOTE) | (QQUOTE KEY_NAME QQUOTE) | (QQQUOTE KEY_NAME QQQUOTE) | KEY_NAME
  public static boolean key(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, KEY, "<key>");
    r = key_0(b, l + 1);
    if (!r) r = key_1(b, l + 1);
    if (!r) r = key_2(b, l + 1);
    if (!r) r = consumeToken(b, KEY_NAME);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // QUOTE KEY_NAME QUOTE
  private static boolean key_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, QUOTE, KEY_NAME, QUOTE);
    exit_section_(b, m, null, r);
    return r;
  }

  // QQUOTE KEY_NAME QQUOTE
  private static boolean key_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, QQUOTE, KEY_NAME, QQUOTE);
    exit_section_(b, m, null, r);
    return r;
  }

  // QQQUOTE KEY_NAME QQQUOTE
  private static boolean key_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, QQQUOTE, KEY_NAME, QQQUOTE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // pair (COMMA pair)*
  public static boolean members(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "members")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MEMBERS, "<members>");
    r = pair(b, l + 1);
    r = r && members_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (COMMA pair)*
  private static boolean members_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "members_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!members_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "members_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA pair
  private static boolean members_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "members_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && pair(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // struct | array | expression
  public static boolean object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OBJECT, "<object>");
    r = struct(b, l + 1);
    if (!r) r = array(b, l + 1);
    if (!r) r = expression(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // COMMENT* (key SEPARATOR value)
  public static boolean pair(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pair")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PAIR, "<pair>");
    r = pair_0(b, l + 1);
    r = r && pair_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // COMMENT*
  private static boolean pair_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pair_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "pair_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // key SEPARATOR value
  private static boolean pair_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pair_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = key(b, l + 1);
    r = r && consumeToken(b, SEPARATOR);
    r = r && value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Q_START (Q_VALUE)? Q_END
  public static boolean q_string(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "q_string")) return false;
    if (!nextTokenIs(b, Q_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, Q_START);
    r = r && q_string_1(b, l + 1);
    r = r && consumeToken(b, Q_END);
    exit_section_(b, m, Q_STRING, r);
    return r;
  }

  // (Q_VALUE)?
  private static boolean q_string_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "q_string_1")) return false;
    consumeToken(b, Q_VALUE);
    return true;
  }

  /* ********************************************************** */
  // QQ_START (QQ_VALUE)? QQ_END
  public static boolean qq_string(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qq_string")) return false;
    if (!nextTokenIs(b, QQ_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, QQ_START);
    r = r && qq_string_1(b, l + 1);
    r = r && consumeToken(b, QQ_END);
    exit_section_(b, m, QQ_STRING, r);
    return r;
  }

  // (QQ_VALUE)?
  private static boolean qq_string_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qq_string_1")) return false;
    consumeToken(b, QQ_VALUE);
    return true;
  }

  /* ********************************************************** */
  // QQQ_START (QQQ_VALUE)? QQQ_END
  public static boolean qqq_string(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qqq_string")) return false;
    if (!nextTokenIs(b, QQQ_START)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, QQQ_START);
    r = r && qqq_string_1(b, l + 1);
    r = r && consumeToken(b, QQQ_END);
    exit_section_(b, m, QQQ_STRING, r);
    return r;
  }

  // (QQQ_VALUE)?
  private static boolean qqq_string_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "qqq_string_1")) return false;
    consumeToken(b, QQQ_VALUE);
    return true;
  }

  /* ********************************************************** */
  // qqq_string | qq_string
  public static boolean string(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "string")) return false;
    if (!nextTokenIs(b, "<string>", QQQ_START, QQ_START)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STRING, "<string>");
    r = qqq_string(b, l + 1);
    if (!r) r = qq_string(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (LBRACE RBRACE) | (LBRACE members RBRACE) | (LBRACE members COMMA RBRACE)
  public static boolean struct(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = struct_0(b, l + 1);
    if (!r) r = struct_1(b, l + 1);
    if (!r) r = struct_2(b, l + 1);
    exit_section_(b, m, STRUCT, r);
    return r;
  }

  // LBRACE RBRACE
  private static boolean struct_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LBRACE, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // LBRACE members RBRACE
  private static boolean struct_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && members(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // LBRACE members COMMA RBRACE
  private static boolean struct_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && members(b, l + 1);
    r = r && consumeTokens(b, 0, COMMA, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER | q_string
  public static boolean symbol(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol")) return false;
    if (!nextTokenIs(b, "<symbol>", IDENTIFIER, Q_START)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SYMBOL, "<symbol>");
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = q_string(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (annotation)* (string | symbol | blob | clob | INTEGER | DECIMAL | HEXINT | BININT | BOOLEAN | NULL | IDENTIFIER | TIMESTAMP | object)
  public static boolean value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VALUE, "<value>");
    r = value_0(b, l + 1);
    r = r && value_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (annotation)*
  private static boolean value_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value_0")) return false;
    int c = current_position_(b);
    while (true) {
      if (!value_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "value_0", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // (annotation)
  private static boolean value_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = annotation(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // string | symbol | blob | clob | INTEGER | DECIMAL | HEXINT | BININT | BOOLEAN | NULL | IDENTIFIER | TIMESTAMP | object
  private static boolean value_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = string(b, l + 1);
    if (!r) r = symbol(b, l + 1);
    if (!r) r = blob(b, l + 1);
    if (!r) r = clob(b, l + 1);
    if (!r) r = consumeToken(b, INTEGER);
    if (!r) r = consumeToken(b, DECIMAL);
    if (!r) r = consumeToken(b, HEXINT);
    if (!r) r = consumeToken(b, BININT);
    if (!r) r = consumeToken(b, BOOLEAN);
    if (!r) r = consumeToken(b, NULL);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, TIMESTAMP);
    if (!r) r = object(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

}
