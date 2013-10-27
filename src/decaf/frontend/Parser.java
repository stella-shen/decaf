//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short NULL=262;
public final static short EXTENDS=263;
public final static short THIS=264;
public final static short WHILE=265;
public final static short FOR=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short RETURN=269;
public final static short BREAK=270;
public final static short NEW=271;
public final static short PRINT=272;
public final static short READ_INTEGER=273;
public final static short READ_LINE=274;
public final static short LITERAL=275;
public final static short IDENTIFIER=276;
public final static short AND=277;
public final static short OR=278;
public final static short STATIC=279;
public final static short INSTANCEOF=280;
public final static short LESS_EQUAL=281;
public final static short GREATER_EQUAL=282;
public final static short EQUAL=283;
public final static short NOT_EQUAL=284;
public final static short UMINUS=285;
public final static short EMPTY=286;
public final static short ForStmt=287;
public final static short ReadLine=288;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   14,   14,   14,   23,   23,
   20,   20,   22,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   21,   21,   21,   21,   25,   25,
   24,   24,   26,   26,   26,   16,   19,   15,   27,   27,
   17,   17,   18,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    2,    0,    1,    2,    1,    1,
    1,    2,    2,    2,    1,    3,    1,    0,    2,    0,
    2,    4,    5,    1,    1,    1,    3,    1,    3,    3,
    3,    3,    3,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    2,    3,    3,    4,    5,    5,    1,    1,
    1,    0,    3,    1,    4,    5,    1,    6,    2,    0,
    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    8,    7,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   26,   23,   20,   22,    0,   70,   48,    0,    0,    0,
   77,    0,    0,    0,   69,    0,    0,    0,   24,   31,
    0,   27,   35,   25,    0,   29,   30,    0,    0,    0,
    0,    0,    0,    0,   46,    0,    0,   44,    0,   45,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   28,
   32,   33,   34,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   39,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   64,    0,   49,
   65,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   66,
    0,    0,    0,   83,    0,   42,    0,    0,   76,    0,
   67,    0,    0,   68,   43,    0,   78,   66,   79,
};
final static short yydgoto[] = {                          2,
    3,    4,   62,   20,   33,    8,   11,   22,   34,   35,
   63,   45,   64,   65,   66,   67,   68,   69,   70,   78,
   72,   80,   74,  147,   75,  117,  157,
};
final static short yysindex[] = {                      -258,
 -259,    0, -258,    0, -234,    0, -242,  -88,    0,    0,
 -109,    0,    0,    0,    0, -238,  -85,    0,    0,  -17,
  -89,    0,    0,  -70,    0,   11,  -31,   20,  -85,    0,
  -85,    0,  -68,   25,   26,   33,    0,  -51,  -85,  -51,
    0,    0,    0,    0,   -1,    0,    0,   46,   47,   69,
    0, -203,   48,   51,    0,   69,   69,   49,    0,    0,
   58,    0,    0,    0,   41,    0,    0,   42,   45,   54,
   18,  471,    0, -171,    0,   69,   69,    0,  471,    0,
   66,   19,   83,   74,   -6,  -38, -169,  -32,   76,    0,
    0,    0,    0,   69,   69,   69,   69,   69,   69,   69,
   69,   69,   69,   69,   69,   69,   69,    0,   69,   80,
  349,  408,   84,   63,  300,  471,    8,    0,   85,    0,
    0,  471,  504,  493,  378,  378,  532,  532,   -6,   -6,
  -38,  -38,  -38,  378,  378,  419,   83,   31,   31,    0,
  430,   81,   69,    0,   69,    0,   89,   88,    0, -135,
    0,   93,  471,    0,    0,   31,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  137,    0,   15,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   87,    0,    0,  106,    0,
  106,    0,    0,    0,  112,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -58,    0,    0,    0,    0,  -55,
    0,    0,    0,    0,    0, -121, -121, -121,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  482,    0,  441,    0,    0, -121, -121,    0,  103,    0,
    0,    0, -121,    0,  527,  124,    0,    0,    0,    0,
    0,    0,    0, -121, -121, -121, -121, -121, -121, -121,
 -121, -121, -121, -121, -121, -121, -121,    0, -121,   98,
    0,    0,    0, -121,    0,   39,    0,    0,    0,    0,
    0,  104,    2,   34,  450,  452,    4,    6,  555,  594,
  151,  160,  325,  461,  463,    0,  -35,  -58,  -58,    0,
    0,    0, -121,    0, -121,    0,    0,  123,    0,  -33,
    0,    0,   40,    0,    0,  -58,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  174,  167,   38,   16,    0,    0,    0,  148,    0,
  -16,    0, -119,    0,    0,    0,    0,    0,    0,  -27,
  785,  -20,    0,    0,    0,   43,    0,
};
final static int YYTABLESIZE=930;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         80,
   38,   27,    1,   82,  105,   72,   80,  108,  120,  103,
  101,   80,  102,  108,  104,   18,    5,   71,  149,  150,
   27,   42,   27,   44,   73,   80,   21,  107,    7,  106,
  105,   57,   24,    9,   10,  103,  159,   23,   58,  108,
  104,   25,   61,   56,   59,   61,   60,   59,  144,   60,
   29,  143,  109,   12,   13,   14,   15,   16,  109,   31,
   61,   30,   59,   57,   60,   38,   32,   82,   32,   39,
   58,   41,   81,   40,   62,   56,   43,   62,   94,   74,
   73,   57,   74,   73,  109,   76,   77,   83,   58,   80,
   84,   80,   62,   56,   61,   57,   59,   89,   60,   90,
   91,   57,   58,   92,  110,  113,  119,   56,   58,  114,
   71,   71,   93,   56,  118,   57,  121,   73,   73,  137,
  152,   41,   58,   59,  140,  145,   62,   56,   71,  155,
   82,  143,  156,  158,   41,   73,    1,   14,   41,   41,
   41,   41,   41,   41,   41,    5,   19,   12,   13,   14,
   15,   16,   18,   41,   40,   30,   41,   41,   41,   41,
   63,   81,   36,   71,   63,   63,   63,   63,   63,   17,
   63,   12,   13,   14,   15,   16,    6,   19,   36,  148,
    0,    0,   63,   63,    0,   63,   26,   51,   41,    0,
   41,   51,   51,   51,   51,   51,   52,   51,    0,    0,
   52,   52,   52,   52,   52,   28,   52,   37,    0,   51,
   51,    0,   51,    0,    0,    0,   63,   40,   52,   52,
   40,   52,    0,   80,   80,   80,   80,   80,   80,    0,
   80,   80,    0,   80,    0,   80,   80,   80,   80,   80,
   40,   80,   80,   51,   95,   96,    0,    0,   97,   98,
   99,  100,   52,   80,   80,   12,   13,   14,   15,   16,
   46,    0,   47,   48,    0,   49,    0,   50,   51,   52,
   53,   54,    0,   55,    0,    0,    0,    0,   61,   61,
   59,   59,   60,   60,    0,   60,   61,   12,   13,   14,
   15,   16,   46,    0,   47,   48,    0,   49,    0,   50,
   51,   52,   53,   54,    0,   55,    0,    0,    0,   87,
   46,   62,   47,    0,    0,    0,    0,   60,   61,   52,
    0,   54,    0,   55,   46,    0,   47,    0,    0,    0,
   46,    0,   47,   52,    0,   54,   61,   55,    0,   52,
    0,   54,    0,   55,   46,    0,   47,    0,    0,    0,
   61,    0,    0,  115,    0,   54,   61,   55,    0,    0,
    0,   53,    0,    0,    0,   53,   53,   53,   53,   53,
   61,   53,    0,    0,   41,   41,    0,    0,   41,   41,
   41,   41,    0,   53,   53,  105,   53,    0,    0,  138,
  103,  101,    0,  102,  108,  104,    0,    0,    0,    0,
   63,   63,    0,    0,   63,   63,   63,   63,  107,    0,
  106,    0,    0,    0,  105,    0,    0,   53,    0,  103,
  101,    0,  102,  108,  104,    0,    0,   51,   51,    0,
    0,   51,   51,   51,   51,    0,   52,   52,    0,  109,
   52,   52,   52,   52,  105,    0,    0,    0,  139,  103,
  101,    0,  102,  108,  104,  105,    0,    0,    0,    0,
  103,  101,    0,  102,  108,  104,  105,  107,  109,  106,
    0,  103,  101,    0,  102,  108,  104,   45,  107,    0,
  106,    0,   45,   45,    0,   45,   45,   45,    0,  107,
   56,  106,   58,   56,    0,   58,    0,    0,  109,   37,
   45,   57,   45,   55,   57,    0,   55,  105,   56,  109,
   58,  146,  103,  101,    0,  102,  108,  104,   44,   57,
  109,   55,  151,   44,   44,    0,   44,   44,   44,  105,
  107,   45,  106,    0,  103,  101,    0,  102,  108,  104,
  105,   44,   56,   44,   58,  103,  101,    0,  102,  108,
  104,    0,  107,   57,  106,   55,   12,   13,   14,   15,
   16,  109,    0,  107,    0,  106,    0,   54,  105,   54,
   54,   54,   44,  103,  101,  142,  102,  108,  104,    0,
    0,    0,    0,  109,    0,   54,   54,    0,   54,    0,
    0,  107,    0,  106,  109,   47,    0,   47,   47,   47,
    0,   53,   53,    0,    0,   53,   53,   53,   53,    0,
    0,    0,    0,   47,   47,    0,   47,    0,    0,   54,
    0,    0,  109,    0,    0,   95,   96,    0,    0,   97,
   98,   99,  100,    0,   50,    0,   50,   50,   50,    0,
    0,    0,    0,    0,    0,    0,    0,   47,    0,    0,
    0,    0,   50,   50,    0,   50,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   95,   96,   50,    0,   97,   98,
   99,  100,    0,    0,    0,   95,   96,    0,    0,   97,
   98,   99,  100,    0,    0,    0,   95,   96,    0,    0,
   97,   98,   99,  100,    0,    0,    0,   45,   45,    0,
    0,   45,   45,   45,   45,    0,   56,   56,   58,   58,
    0,    0,   56,   56,   58,   58,    0,   57,   57,   55,
   55,    0,    0,   57,   57,   55,   55,   95,   96,    0,
    0,   97,   98,   99,  100,    0,    0,    0,   44,   44,
    0,    0,   44,   44,   44,   44,    0,    0,    0,   95,
    0,    0,    0,   97,   98,   99,  100,    0,    0,    0,
    0,    0,    0,    0,   97,   98,   99,  100,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   54,   54,    0,    0,   54,   54,   54,
   54,    0,   97,   98,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   47,   47,    0,   79,   47,   47,   47,   47,    0,
   85,   86,   88,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  111,  112,    0,    0,    0,    0,    0,  116,    0,    0,
   50,   50,    0,    0,   50,   50,   50,   50,  122,  123,
  124,  125,  126,  127,  128,  129,  130,  131,  132,  133,
  134,  135,    0,  136,    0,    0,    0,    0,  141,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  116,    0,    0,    0,    0,    0,  153,    0,  154,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   91,  261,   59,   37,   41,   40,   46,   41,   42,
   43,   45,   45,   46,   47,  125,  276,   45,  138,  139,
   91,   38,   91,   40,   45,   59,   11,   60,  263,   62,
   37,   33,   17,  276,  123,   42,  156,  276,   40,   46,
   47,   59,   41,   45,   41,   44,   41,   44,   41,   44,
   40,   44,   91,  257,  258,  259,  260,  261,   91,   40,
   59,   93,   59,   33,   59,   41,   29,   52,   31,   44,
   40,  123,  276,   41,   41,   45,   39,   44,   61,   41,
   41,   33,   44,   44,   91,   40,   40,   40,   40,  123,
   40,  125,   59,   45,   93,   33,   93,   40,   93,   59,
   59,   33,   40,   59,  276,   40,  276,   45,   40,   91,
  138,  139,   59,   45,   41,   33,   41,  138,  139,   40,
   40,  123,   40,  125,   41,   41,   93,   45,  156,   41,
  115,   44,  268,   41,   37,  156,    0,  123,   41,   42,
   43,   44,   45,   46,   47,   59,   41,  257,  258,  259,
  260,  261,   41,  123,  276,   93,   59,   60,   61,   62,
   37,   59,   59,   41,   41,   42,   43,   44,   45,  279,
   47,  257,  258,  259,  260,  261,    3,   11,   31,  137,
   -1,   -1,   59,   60,   -1,   62,  276,   37,   91,   -1,
   93,   41,   42,   43,   44,   45,   37,   47,   -1,   -1,
   41,   42,   43,   44,   45,  276,   47,  276,   -1,   59,
   60,   -1,   62,   -1,   -1,   -1,   93,  276,   59,   60,
  276,   62,   -1,  257,  258,  259,  260,  261,  262,   -1,
  264,  265,   -1,  267,   -1,  269,  270,  271,  272,  273,
  276,  275,  276,   93,  277,  278,   -1,   -1,  281,  282,
  283,  284,   93,  287,  288,  257,  258,  259,  260,  261,
  262,   -1,  264,  265,   -1,  267,   -1,  269,  270,  271,
  272,  273,   -1,  275,   -1,   -1,   -1,   -1,  277,  278,
  277,  278,  277,  278,   -1,  287,  288,  257,  258,  259,
  260,  261,  262,   -1,  264,  265,   -1,  267,   -1,  269,
  270,  271,  272,  273,   -1,  275,   -1,   -1,   -1,  261,
  262,  278,  264,   -1,   -1,   -1,   -1,  287,  288,  271,
   -1,  273,   -1,  275,  262,   -1,  264,   -1,   -1,   -1,
  262,   -1,  264,  271,   -1,  273,  288,  275,   -1,  271,
   -1,  273,   -1,  275,  262,   -1,  264,   -1,   -1,   -1,
  288,   -1,   -1,  271,   -1,  273,  288,  275,   -1,   -1,
   -1,   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,
  288,   47,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   59,   60,   37,   62,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   60,   -1,
   62,   -1,   -1,   -1,   37,   -1,   -1,   93,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,  277,  278,   -1,   91,
  281,  282,  283,  284,   37,   -1,   -1,   -1,   41,   42,
   43,   -1,   45,   46,   47,   37,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   37,   60,   91,   62,
   -1,   42,   43,   -1,   45,   46,   47,   37,   60,   -1,
   62,   -1,   42,   43,   -1,   45,   46,   47,   -1,   60,
   41,   62,   41,   44,   -1,   44,   -1,   -1,   91,   59,
   60,   41,   62,   41,   44,   -1,   44,   37,   59,   91,
   59,   93,   42,   43,   -1,   45,   46,   47,   37,   59,
   91,   59,   93,   42,   43,   -1,   45,   46,   47,   37,
   60,   91,   62,   -1,   42,   43,   -1,   45,   46,   47,
   37,   60,   93,   62,   93,   42,   43,   -1,   45,   46,
   47,   -1,   60,   93,   62,   93,  257,  258,  259,  260,
  261,   91,   -1,   60,   -1,   62,   -1,   41,   37,   43,
   44,   45,   91,   42,   43,  276,   45,   46,   47,   -1,
   -1,   -1,   -1,   91,   -1,   59,   60,   -1,   62,   -1,
   -1,   60,   -1,   62,   91,   41,   -1,   43,   44,   45,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   -1,   59,   60,   -1,   62,   -1,   -1,   93,
   -1,   -1,   91,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   41,   -1,   43,   44,   45,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,
   -1,   -1,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  277,  278,   93,   -1,  281,  282,
  283,  284,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,  277,  278,  277,  278,
   -1,   -1,  283,  284,  283,  284,   -1,  277,  278,  277,
  278,   -1,   -1,  283,  284,  283,  284,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,  277,
   -1,   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,  281,  282,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   50,  281,  282,  283,  284,   -1,
   56,   57,   58,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   76,   77,   -1,   -1,   -1,   -1,   -1,   83,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   94,   95,
   96,   97,   98,   99,  100,  101,  102,  103,  104,  105,
  106,  107,   -1,  109,   -1,   -1,   -1,   -1,  114,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  137,   -1,   -1,   -1,   -1,   -1,  143,   -1,  145,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=288;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN","BREAK",
"NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR",
"STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL","UMINUS",
"EMPTY","ForStmt","ReadLine",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : BOOL",
"Type : VOID",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Expr '+' Expr",
"Expr : THIS",
"Expr : '(' Expr ')'",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : '-' Expr",
"Expr : Expr '<' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr '>' Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '!' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : ReadLine '(' ')'",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"ExprList : NEW IDENTIFIER '(' ')'",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
};

//#line 423 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 557 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 52 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 58 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 62 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 72 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 78 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 83 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
					}
break;
case 8:
//#line 88 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
					}
break;
case 9:
//#line 93 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
					}
break;
case 10:
//#line 98 "Parser.y"
{
						yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
					}
break;
case 11:
//#line 103 "Parser.y"
{
						yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
					}
break;
case 12:
//#line 109 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 115 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 14:
//#line 119 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 15:
//#line 125 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 16:
//#line 129 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 17:
//#line 133 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 19:
//#line 141 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 20:
//#line 148 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 21:
//#line 152 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 22:
//#line 159 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 23:
//#line 163 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 169 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 25:
//#line 175 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 26:
//#line 179 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 27:
//#line 186 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 28:
//#line 191 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 36:
//#line 206 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 37:
//#line 210 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 38:
//#line 214 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 40:
//#line 221 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 41:
//#line 227 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 42:
//#line 234 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 43:
//#line 240 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 44:
//#line 249 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 47:
//#line 255 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 48:
//#line 259 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 49:
//#line 263 "Parser.y"
{
                		yyval.expr = val_peek(1).expr;
                	}
break;
case 50:
//#line 267 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 271 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 275 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 279 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 283 "Parser.y"
{
               			yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 55:
//#line 287 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 56:
//#line 291 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 57:
//#line 295 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 58:
//#line 299 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 59:
//#line 303 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 60:
//#line 307 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 61:
//#line 311 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 62:
//#line 315 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 63:
//#line 319 "Parser.y"
{
               			yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 64:
//#line 323 "Parser.y"
{
               			yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
               		}
break;
case 65:
//#line 327 "Parser.y"
{
               			yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
               		}
break;
case 66:
//#line 331 "Parser.y"
{
               			yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
               		}
break;
case 67:
//#line 335 "Parser.y"
{
               			yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
               		}
break;
case 68:
//#line 339 "Parser.y"
{
               			yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(4).loc);
               		}
break;
case 69:
//#line 345 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 70:
//#line 349 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 72:
//#line 356 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 73:
//#line 363 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 74:
//#line 367 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 75:
//#line 372 "Parser.y"
{
                		
                	}
break;
case 76:
//#line 378 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 77:
//#line 385 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 78:
//#line 391 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 79:
//#line 397 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 80:
//#line 401 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 81:
//#line 407 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 82:
//#line 411 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 83:
//#line 417 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1138 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
