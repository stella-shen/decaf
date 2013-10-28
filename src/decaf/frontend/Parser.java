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
public final static short DOUBLE=262;
public final static short NULL=263;
public final static short EXTENDS=264;
public final static short THIS=265;
public final static short WHILE=266;
public final static short FOR=267;
public final static short IF=268;
public final static short ELSE=269;
public final static short RETURN=270;
public final static short BREAK=271;
public final static short NEW=272;
public final static short PRINT=273;
public final static short READ_INTEGER=274;
public final static short READ_LINE=275;
public final static short LITERAL=276;
public final static short IDENTIFIER=277;
public final static short AND=278;
public final static short OR=279;
public final static short STATIC=280;
public final static short INSTANCEOF=281;
public final static short LESS_EQUAL=282;
public final static short GREATER_EQUAL=283;
public final static short EQUAL=284;
public final static short NOT_EQUAL=285;
public final static short REPEAT=286;
public final static short UNTIL=287;
public final static short UMINUS=288;
public final static short EMPTY=289;
public final static short ReadLine=290;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    5,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   14,   14,   14,
   25,   25,   22,   22,   24,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   28,   27,   27,   26,   26,   29,   29,   29,   16,   17,
   21,   20,   15,   30,   30,   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    1,
    2,    3,    6,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    1,    3,    1,    0,
    2,    0,    2,    4,    5,    1,    1,    1,    3,    1,
    3,    3,    3,    3,    3,    2,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    3,    3,    4,    5,    5,
    1,    1,    1,    1,    0,    3,    1,    4,    5,    9,
    6,    1,    6,    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   14,   18,
    0,    9,    8,    6,   10,    0,    7,    0,   13,   16,
    0,    0,   17,   11,    0,    4,    0,    0,    0,    0,
   12,    0,   22,    0,    0,    0,    0,    5,    0,    0,
    0,   27,   24,   21,   23,    0,   73,   50,    0,    0,
    0,    0,   82,    0,    0,    0,   72,    0,    0,    0,
   25,    0,    0,   28,   36,   26,    0,   30,   31,   32,
    0,    0,    0,   37,    0,    0,    0,    0,   48,    0,
    0,    0,   46,    0,   47,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   29,   33,   34,   35,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   66,    0,   51,    0,   67,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   68,    0,
    0,    0,   88,    0,    0,   44,    0,    0,   79,    0,
    0,    0,   69,    0,    0,   70,    0,   45,    0,    0,
   83,   68,   81,    0,   84,    0,   80,
};
final static short yydgoto[] = {                          2,
    3,    4,   64,   21,   34,    8,   11,   23,   35,   36,
   65,   46,   66,   67,   68,   69,   70,   71,   72,   73,
   74,   83,   76,   85,   78,  157,   79,  161,  124,  171,
};
final static short yysindex[] = {                      -253,
 -268,    0, -253,    0, -237,    0, -242,  -84,    0,    0,
   96,    0,    0,    0,    0, -234,    0,  389,    0,    0,
   -1,  -75,    0,    0,  -74,    0,   22,  -46,   31,  389,
    0,  389,    0,  -72,   32,   28,   36,    0,  -47,  389,
  -47,    0,    0,    0,    0,    1,    0,    0,   38,   39,
   41,   74,    0,  128,   42,   43,    0,   74,   74,   54,
    0,   35,   44,    0,    0,    0,   26,    0,    0,    0,
   34,   37,   47,    0,   40,  509,    0, -175,    0,   74,
   74,   74,    0,  509,    0,   63,   18,   75,   69,   14,
  -26, -166,  415, -174,   71,    0,    0,    0,    0,   74,
   74,   74,   74,   74,   74,   74,   74,   74,   74,   74,
   74,   74,   74,    0,   74,   77,  439,   62,  466,   81,
   55,  379,  509,   -4,    0,   82,    0,   85,    0,  509,
  562,  541,    7,    7,  -32,  -32,   14,   14,  -26,  -26,
  -26,    7,    7,  477,   75,   35,   74,   35,    0,  498,
   88,   74,    0,   74,   74,    0,   89,   95,    0,  509,
   70, -128,    0,  102,  509,    0,  103,    0,   74,   35,
    0,    0,    0,  104,    0,   35,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  147,    0,   30,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   97,    0,    0,  113,
    0,  113,    0,    0,    0,  116,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -57,    0,    0,    0,    0,    0, -115, -115, -115,
    0,  -58,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  530,    0,  118,    0,    0, -115,
  -58, -115,    0,  108,    0,    0,    0, -115,    0,  488,
  129,    0,    0,    0,    0,    0,    0,    0,    0, -115,
 -115, -115, -115, -115, -115, -115, -115, -115, -115, -115,
 -115, -115, -115,    0, -115,   90,    0,    0,    0,    0,
 -115,    0,   23,    0,    0,    0,    0,    0,    0,  -37,
   45,  -23,    4,  211,  340,  428,  520,  552,  153,  380,
  404,  350,  424,    0,  -35,  -58, -115,  -58,    0,    0,
    0, -115,    0, -115, -115,    0,    0,  127,    0,  -17,
    0,  -33,    0,    0,   25,    0,    0,    0,  -12,  -58,
    0,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  166,  164,   -7,   20,    0,    0,    0,  150,    0,
   16,    0,  -30,  -78,    0,    0,    0,    0,    0,    0,
    0,  739,  790,  770,    0,    0,    0,   24,   48,    0,
};
final static int YYTABLESIZE=946;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         85,
   40,   87,  118,   38,  111,   75,   85,    1,    5,  109,
  107,   85,  108,  114,  110,   28,   28,   64,   28,  114,
   64,   38,   33,   71,   33,   85,    7,  113,   40,  112,
   22,   94,   44,   59,    9,   64,  153,   25,   10,  152,
   60,   71,   24,  111,   58,   58,   31,   58,  109,  107,
  111,  108,  114,  110,   43,  109,   45,   26,  115,  114,
  110,   30,   58,   77,  115,   76,   77,   59,   76,   64,
   32,   40,   39,   87,   60,   42,   41,   80,   81,   58,
   82,   88,   89,   95,   96,   63,   59,   59,   63,   85,
  174,   85,   97,   60,   60,   98,   58,  115,   58,   58,
  100,  116,  120,   63,  115,   99,   59,   59,  121,  125,
  126,  129,  128,   60,   60,  159,  145,  162,   58,   58,
  147,  149,  154,   42,  155,   61,   43,  164,  169,  168,
   43,   43,   43,   43,   43,   43,   43,   63,  152,  175,
  170,   87,  172,  173,  176,  177,    1,   31,   43,   43,
   43,   43,   15,   20,   47,    5,   19,   42,   39,   47,
   47,   42,   47,   47,   47,   65,   86,   74,    6,   65,
   65,   65,   65,   65,   20,   65,   39,   47,  167,   47,
   43,   37,   43,    0,    0,    0,    0,   65,   65,   53,
   65,    0,  158,   53,   53,   53,   53,   53,    0,   53,
    0,   27,   29,    0,   38,    0,    0,    0,   47,    0,
    0,   53,   53,    0,   53,    0,    0,    0,   42,   42,
   19,   65,    0,   85,   85,   85,   85,   85,   85,   85,
    0,   85,   85,   85,   85,    0,   85,   85,   85,   85,
   85,   42,   85,   85,    0,   53,    0,    0,    0,  103,
  104,   60,   85,   85,   60,   64,   85,   12,   13,   14,
   15,   16,   17,   47,   42,   48,   49,   50,   51,   60,
   52,   53,   54,   55,   56,    0,   57,    0,    0,    0,
    0,   58,   58,    0,    0,    0,   62,   58,   58,    0,
   63,   12,   13,   14,   15,   16,   17,   47,    0,   48,
   49,   50,   51,   60,   52,   53,   54,   55,   56,    0,
   57,    0,    0,    0,   92,    0,   47,   47,   48,   48,
   62,    0,   63,   63,   63,   54,   54,   56,   56,   57,
   57,    0,    0,    0,    0,    0,   47,   47,   48,   48,
    0,    0,    0,   63,   63,   54,  122,   56,   56,   57,
   57,    0,   12,   13,   14,   15,   16,   17,    0,    0,
    0,    0,    0,   63,   63,    0,    0,   43,   43,    0,
    0,   43,   43,   43,   43,   18,    0,    0,    0,    0,
   61,    0,    0,   61,   12,   13,   14,   15,   16,   17,
   59,    0,    0,   59,    0,   47,   47,    0,   61,   47,
   47,   47,   47,    0,   86,    0,   65,   65,   59,    0,
   65,   65,   65,   65,    0,    0,   54,    0,    0,    0,
   54,   54,   54,   54,   54,    0,   54,    0,    0,    0,
   53,   53,   61,    0,   53,   53,   53,   53,   54,   54,
   55,   54,   59,    0,   55,   55,   55,   55,   55,    0,
   55,  111,    0,    0,    0,  127,  109,  107,    0,  108,
  114,  110,   55,   55,   57,   55,    0,   57,   62,    0,
    0,   62,   54,    0,  113,  111,  112,    0,    0,  146,
  109,  107,   57,  108,  114,  110,   62,    0,   60,   60,
    0,    0,    0,    0,   60,   60,   55,    0,  113,    0,
  112,    0,  111,    0,    0,  115,  148,  109,  107,    0,
  108,  114,  110,  111,    0,    0,   57,    0,  109,  107,
   62,  108,  114,  110,    0,  113,    0,  112,   56,  115,
   56,   56,   56,    0,  111,    0,  113,    0,  112,  109,
  107,    0,  108,  114,  110,  111,   56,   56,    0,   56,
  109,  107,    0,  108,  114,  110,  115,  113,    0,  112,
   49,    0,   49,   49,   49,    0,   46,  115,  113,  156,
  112,   46,   46,    0,   46,   46,   46,  111,   49,   49,
   56,   49,  109,  107,    0,  108,  114,  110,  115,   46,
  163,   46,   52,    0,   52,   52,   52,    0,  111,  115,
  113,    0,  112,  109,  107,    0,  108,  114,  110,    0,
   52,   52,   49,   52,    0,    0,    0,   61,   61,    0,
   46,  113,    0,  112,    0,    0,    0,   59,   59,    0,
    0,  115,    0,   59,   59,   12,   13,   14,   15,   16,
   17,    0,    0,    0,   52,   12,   13,   14,   15,   16,
   17,    0,  115,    0,    0,  151,    0,   54,   54,    0,
    0,   54,   54,   54,   54,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   55,   55,    0,    0,   55,   55,   55,   55,    0,
    0,    0,  101,  102,    0,    0,  103,  104,  105,  106,
    0,   57,   57,    0,    0,   62,   62,   57,   57,    0,
    0,    0,    0,    0,    0,    0,  101,  102,    0,    0,
  103,  104,  105,  106,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  101,  102,    0,    0,  103,  104,  105,
  106,    0,    0,    0,  101,  102,    0,    0,  103,  104,
  105,  106,    0,    0,    0,   56,   56,    0,    0,   56,
   56,   56,   56,    0,    0,  101,  102,    0,    0,  103,
  104,  105,  106,    0,   75,    0,  101,  102,    0,    0,
  103,  104,  105,  106,    0,    0,    0,   49,   49,    0,
   75,   49,   49,   49,   49,    0,    0,   46,   46,    0,
    0,   46,   46,   46,   46,   77,    0,    0,  101,   75,
    0,    0,  103,  104,  105,  106,    0,    0,    0,   52,
   52,   77,    0,   52,   52,   52,   52,    0,    0,    0,
    0,   84,    0,  103,  104,  105,  106,   90,   91,   93,
   77,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  117,
    0,  119,    0,    0,    0,    0,    0,  123,    0,    0,
    0,    0,    0,    0,   75,    0,   75,    0,    0,  130,
  131,  132,  133,  134,  135,  136,  137,  138,  139,  140,
  141,  142,  143,    0,  144,    0,    0,   75,   75,    0,
  150,    0,    0,    0,   75,   77,    0,   77,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  123,    0,  160,    0,   77,   77,
    0,  165,    0,  166,  160,   77,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   81,   41,   37,   41,   40,  261,  277,   42,
   43,   45,   45,   46,   47,   91,   91,   41,   91,   46,
   44,   59,   30,   41,   32,   59,  264,   60,   41,   62,
   11,   62,   40,   33,  277,   59,   41,   18,  123,   44,
   40,   59,  277,   37,   41,   45,   93,   44,   42,   43,
   37,   45,   46,   47,   39,   42,   41,   59,   91,   46,
   47,   40,   59,   41,   91,   41,   44,   33,   44,   93,
   40,   44,   41,   54,   40,  123,   41,   40,   40,   45,
   40,   40,   40,   40,   59,   41,   33,   33,   44,  123,
  169,  125,   59,   40,   40,   59,   93,   91,   45,   45,
   61,  277,   40,   59,   91,   59,   33,   33,   91,   41,
  277,   41,  287,   40,   40,  146,   40,  148,   45,   45,
   59,   41,   41,  123,   40,  125,   37,   40,   59,   41,
   41,   42,   43,   44,   45,   46,   47,   93,   44,  170,
  269,  122,   41,   41,   41,  176,    0,   93,   59,   60,
   61,   62,  123,   41,   37,   59,   41,  123,   41,   42,
   43,  277,   45,   46,   47,   37,   59,   41,    3,   41,
   42,   43,   44,   45,   11,   47,   59,   60,  155,   62,
   91,   32,   93,   -1,   -1,   -1,   -1,   59,   60,   37,
   62,   -1,  145,   41,   42,   43,   44,   45,   -1,   47,
   -1,  277,  277,   -1,  277,   -1,   -1,   -1,   91,   -1,
   -1,   59,   60,   -1,   62,   -1,   -1,   -1,  277,  277,
  125,   93,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,  270,  271,  272,  273,
  274,  277,  276,  277,   -1,   93,   -1,   -1,   -1,  282,
  283,   41,  286,  287,   44,  279,  290,  257,  258,  259,
  260,  261,  262,  263,  277,  265,  266,  267,  268,   59,
  270,  271,  272,  273,  274,   -1,  276,   -1,   -1,   -1,
   -1,  278,  279,   -1,   -1,   -1,  286,  284,  285,   -1,
  290,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,  267,  268,   93,  270,  271,  272,  273,  274,   -1,
  276,   -1,   -1,   -1,  261,   -1,  263,  263,  265,  265,
  286,   -1,  278,  279,  290,  272,  272,  274,  274,  276,
  276,   -1,   -1,   -1,   -1,   -1,  263,  263,  265,  265,
   -1,   -1,   -1,  290,  290,  272,  272,  274,  274,  276,
  276,   -1,  257,  258,  259,  260,  261,  262,   -1,   -1,
   -1,   -1,   -1,  290,  290,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,  280,   -1,   -1,   -1,   -1,
   41,   -1,   -1,   44,  257,  258,  259,  260,  261,  262,
   41,   -1,   -1,   44,   -1,  278,  279,   -1,   59,  282,
  283,  284,  285,   -1,  277,   -1,  278,  279,   59,   -1,
  282,  283,  284,  285,   -1,   -1,   37,   -1,   -1,   -1,
   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,   -1,
  278,  279,   93,   -1,  282,  283,  284,  285,   59,   60,
   37,   62,   93,   -1,   41,   42,   43,   44,   45,   -1,
   47,   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,   59,   60,   41,   62,   -1,   44,   41,   -1,
   -1,   44,   93,   -1,   60,   37,   62,   -1,   -1,   41,
   42,   43,   59,   45,   46,   47,   59,   -1,  278,  279,
   -1,   -1,   -1,   -1,  284,  285,   93,   -1,   60,   -1,
   62,   -1,   37,   -1,   -1,   91,   41,   42,   43,   -1,
   45,   46,   47,   37,   -1,   -1,   93,   -1,   42,   43,
   93,   45,   46,   47,   -1,   60,   -1,   62,   41,   91,
   43,   44,   45,   -1,   37,   -1,   60,   -1,   62,   42,
   43,   -1,   45,   46,   47,   37,   59,   60,   -1,   62,
   42,   43,   -1,   45,   46,   47,   91,   60,   -1,   62,
   41,   -1,   43,   44,   45,   -1,   37,   91,   60,   93,
   62,   42,   43,   -1,   45,   46,   47,   37,   59,   60,
   93,   62,   42,   43,   -1,   45,   46,   47,   91,   60,
   93,   62,   41,   -1,   43,   44,   45,   -1,   37,   91,
   60,   -1,   62,   42,   43,   -1,   45,   46,   47,   -1,
   59,   60,   93,   62,   -1,   -1,   -1,  278,  279,   -1,
   91,   60,   -1,   62,   -1,   -1,   -1,  278,  279,   -1,
   -1,   91,   -1,  284,  285,  257,  258,  259,  260,  261,
  262,   -1,   -1,   -1,   93,  257,  258,  259,  260,  261,
  262,   -1,   91,   -1,   -1,  277,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,   -1,
   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,  278,  279,   -1,   -1,  278,  279,  284,  285,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,  284,
  285,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,  283,
  284,  285,   -1,   -1,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,   -1,  278,  279,   -1,   -1,  282,
  283,  284,  285,   -1,   46,   -1,  278,  279,   -1,   -1,
  282,  283,  284,  285,   -1,   -1,   -1,  278,  279,   -1,
   62,  282,  283,  284,  285,   -1,   -1,  278,  279,   -1,
   -1,  282,  283,  284,  285,   46,   -1,   -1,  278,   81,
   -1,   -1,  282,  283,  284,  285,   -1,   -1,   -1,  278,
  279,   62,   -1,  282,  283,  284,  285,   -1,   -1,   -1,
   -1,   52,   -1,  282,  283,  284,  285,   58,   59,   60,
   81,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   80,
   -1,   82,   -1,   -1,   -1,   -1,   -1,   88,   -1,   -1,
   -1,   -1,   -1,   -1,  146,   -1,  148,   -1,   -1,  100,
  101,  102,  103,  104,  105,  106,  107,  108,  109,  110,
  111,  112,  113,   -1,  115,   -1,   -1,  169,  170,   -1,
  121,   -1,   -1,   -1,  176,  146,   -1,  148,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  145,   -1,  147,   -1,  169,  170,
   -1,  152,   -1,  154,  155,  176,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=290;
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
"CLASS","DOUBLE","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN",
"BREAK","NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND",
"OR","STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL",
"REPEAT","UNTIL","UMINUS","EMPTY","ReadLine",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : DOUBLE",
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
"Stmt : RepeatStmt",
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
"BoolExpr : Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"ExprList : NEW IDENTIFIER '(' ')'",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' BoolExpr ';' SimpleStmt ')' Stmt",
"RepeatStmt : REPEAT Stmt UNTIL '(' BoolExpr ')'",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
};

//#line 441 "Parser.y"
    
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
//#line 574 "Parser.java"
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
//#line 53 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 59 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 63 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 73 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 79 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 83 "Parser.y"
{
							yyval.type = new Tree.TypeIdent(Tree.DOUBLE, val_peek(0).loc);
						}
break;
case 8:
//#line 87 "Parser.y"
{
							yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
						}
break;
case 9:
//#line 91 "Parser.y"
{
							yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
						}
break;
case 10:
//#line 95 "Parser.y"
{
							yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
						}
break;
case 11:
//#line 99 "Parser.y"
{
							yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
						}
break;
case 12:
//#line 103 "Parser.y"
{
							yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
						}
break;
case 13:
//#line 109 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 14:
//#line 115 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 119 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 125 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 129 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 133 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 141 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 148 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 152 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 159 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 163 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 169 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 175 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 179 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 186 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 191 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 38:
//#line 207 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 39:
//#line 211 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 40:
//#line 215 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 222 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 228 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 44:
//#line 235 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 45:
//#line 241 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 46:
//#line 250 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 49:
//#line 256 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 50:
//#line 260 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 51:
//#line 264 "Parser.y"
{
                		yyval.expr = val_peek(1).expr;
                	}
break;
case 52:
//#line 268 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 272 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 276 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 280 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 284 "Parser.y"
{
               			yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 57:
//#line 288 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 58:
//#line 292 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 59:
//#line 296 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 60:
//#line 300 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 61:
//#line 304 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 62:
//#line 308 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 63:
//#line 312 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 64:
//#line 316 "Parser.y"
{
               			yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 65:
//#line 320 "Parser.y"
{
               			yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
               		}
break;
case 66:
//#line 324 "Parser.y"
{
               			yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
               		}
break;
case 67:
//#line 328 "Parser.y"
{
               			yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
               		}
break;
case 68:
//#line 332 "Parser.y"
{
               			yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
               		}
break;
case 69:
//#line 336 "Parser.y"
{
               			yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
               		}
break;
case 70:
//#line 340 "Parser.y"
{
               			yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(4).loc);
               		}
break;
case 71:
//#line 346 "Parser.y"
{
						yyval.expr = val_peek(0).expr;
					}
break;
case 72:
//#line 352 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 73:
//#line 356 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 75:
//#line 363 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 76:
//#line 370 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 77:
//#line 374 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 78:
//#line 379 "Parser.y"
{
                		
                	}
break;
case 79:
//#line 385 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 80:
//#line 391 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 81:
//#line 397 "Parser.y"
{
						yyval.stmt = new Tree.RepeatLoop(val_peek(1).expr, val_peek(4).stmt, val_peek(5).loc);
					}
break;
case 82:
//#line 403 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 83:
//#line 409 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 84:
//#line 415 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 85:
//#line 419 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 86:
//#line 425 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 87:
//#line 429 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 88:
//#line 435 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1179 "Parser.java"
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
