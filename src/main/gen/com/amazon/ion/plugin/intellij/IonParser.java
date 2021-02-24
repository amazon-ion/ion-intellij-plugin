// This is a generated file. Not intended for manual editing.
package com.amazon.ion.plugin.intellij;

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
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
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
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = q_string(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // sexpression_atom+
  public static boolean atoms(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "atoms")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ATOMS, "<atoms>");
    r = sexpression_atom(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!sexpression_atom(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "atoms", c)) break;
    }
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // LOB_START (BLOB_VALUE | BAD_CHARACTER) LOB_END
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

  // BLOB_VALUE | BAD_CHARACTER
  private static boolean blob_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "blob_1")) return false;
    boolean r;
    r = consumeToken(b, BLOB_VALUE);
    if (!r) r = consumeToken(b, BAD_CHARACTER);
    return r;
  }

  /* ********************************************************** */
  // LOB_START (string) LOB_END
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

  // (string)
  private static boolean clob_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "clob_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = string(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // struct | list | sexpression
  public static boolean container(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "container")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CONTAINER, "<container>");
    r = struct(b, l + 1);
    if (!r) r = list(b, l + 1);
    if (!r) r = sexpression(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // list_element*
  public static boolean elements(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "elements")) return false;
    Marker m = enter_section_(b, l, _NONE_, ELEMENTS, "<elements>");
    while (true) {
      int c = current_position_(b);
      if (!list_element(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "elements", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  /* ********************************************************** */
  // item_*
  static boolean ionFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ionFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ionFile", c)) break;
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
  // LBRACKET [elements] RBRACKET
  public static boolean list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list")) return false;
    if (!nextTokenIs(b, LBRACKET)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LIST, null);
    r = consumeToken(b, LBRACKET);
    p = r; // pin = 1
    r = r && report_error_(b, list_1(b, l + 1));
    r = p && consumeToken(b, RBRACKET) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [elements]
  private static boolean list_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_1")) return false;
    elements(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // value (COMMA | &RBRACKET)
  static boolean list_element(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_element")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = value(b, l + 1);
    p = r; // pin = 1
    r = r && list_element_1(b, l + 1);
    exit_section_(b, l, m, r, p, IonParser::not_bracket_or_next_value);
    return r || p;
  }

  // COMMA | &RBRACKET
  private static boolean list_element_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_element_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    if (!r) r = list_element_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &RBRACKET
  private static boolean list_element_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_element_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RBRACKET);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // struct_pair*
  public static boolean members(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "members")) return false;
    Marker m = enter_section_(b, l, _NONE_, MEMBERS, "<members>");
    while (true) {
      int c = current_position_(b);
      if (!struct_pair(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "members", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  /* ********************************************************** */
  // !(RBRACE | pair)
  static boolean not_brace_or_next_pair(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_brace_or_next_pair")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !not_brace_or_next_pair_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACE | pair
  private static boolean not_brace_or_next_pair_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_brace_or_next_pair_0")) return false;
    boolean r;
    r = consumeToken(b, RBRACE);
    if (!r) r = pair(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !(RBRACKET | value)
  static boolean not_bracket_or_next_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_bracket_or_next_value")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !not_bracket_or_next_value_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RBRACKET | value
  private static boolean not_bracket_or_next_value_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_bracket_or_next_value_0")) return false;
    boolean r;
    r = consumeToken(b, RBRACKET);
    if (!r) r = value(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !(RPAREN | OPERATOR | value)
  static boolean not_paren_or_next_member(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_paren_or_next_member")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !not_paren_or_next_member_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // RPAREN | OPERATOR | value
  private static boolean not_paren_or_next_member_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_paren_or_next_member_0")) return false;
    boolean r;
    r = consumeToken(b, RPAREN);
    if (!r) r = consumeToken(b, OPERATOR);
    if (!r) r = value(b, l + 1);
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
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, COMMENT)) break;
      if (!empty_element_parsed_guard_(b, "pair_0", c)) break;
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
  // LPAREN [sexpression_operator] [atoms] RPAREN
  public static boolean sexpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sexpression")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && sexpression_1(b, l + 1);
    r = r && sexpression_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, SEXPRESSION, r);
    return r;
  }

  // [sexpression_operator]
  private static boolean sexpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sexpression_1")) return false;
    sexpression_operator(b, l + 1);
    return true;
  }

  // [atoms]
  private static boolean sexpression_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sexpression_2")) return false;
    atoms(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // OPERATOR | value
  static boolean sexpression_atom(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sexpression_atom")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, OPERATOR);
    if (!r) r = value(b, l + 1);
    exit_section_(b, l, m, r, false, IonParser::not_paren_or_next_member);
    return r;
  }

  /* ********************************************************** */
  // (annotation)* (symbol)
  public static boolean sexpression_operator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sexpression_operator")) return false;
    if (!nextTokenIs(b, "<sexpression operator>", IDENTIFIER, Q_START)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SEXPRESSION_OPERATOR, "<sexpression operator>");
    r = sexpression_operator_0(b, l + 1);
    r = r && sexpression_operator_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (annotation)*
  private static boolean sexpression_operator_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sexpression_operator_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!sexpression_operator_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "sexpression_operator_0", c)) break;
    }
    return true;
  }

  // (annotation)
  private static boolean sexpression_operator_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sexpression_operator_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = annotation(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (symbol)
  private static boolean sexpression_operator_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sexpression_operator_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = symbol(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
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
  // LBRACE [members] RBRACE
  public static boolean struct(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, STRUCT, null);
    r = consumeToken(b, LBRACE);
    p = r; // pin = 1
    r = r && report_error_(b, struct_1(b, l + 1));
    r = p && consumeToken(b, RBRACE) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // [members]
  private static boolean struct_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_1")) return false;
    members(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // pair (COMMA | &RBRACE)
  static boolean struct_pair(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_pair")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = pair(b, l + 1);
    p = r; // pin = 1
    r = r && struct_pair_1(b, l + 1);
    exit_section_(b, l, m, r, p, IonParser::not_brace_or_next_pair);
    return r || p;
  }

  // COMMA | &RBRACE
  private static boolean struct_pair_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_pair_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    if (!r) r = struct_pair_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // &RBRACE
  private static boolean struct_pair_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "struct_pair_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, RBRACE);
    exit_section_(b, l, m, r, false, null);
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
  // (annotation)* (string | symbol | blob | clob | INTEGER | DECIMAL | HEXINT | BININT | BOOLEAN | NULL | IDENTIFIER | TIMESTAMP | container)
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
    while (true) {
      int c = current_position_(b);
      if (!value_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "value_0", c)) break;
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

  // string | symbol | blob | clob | INTEGER | DECIMAL | HEXINT | BININT | BOOLEAN | NULL | IDENTIFIER | TIMESTAMP | container
  private static boolean value_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value_1")) return false;
    boolean r;
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
    if (!r) r = container(b, l + 1);
    return r;
  }

}
