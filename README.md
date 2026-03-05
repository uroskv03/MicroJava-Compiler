# MicroJava-Compiler


## Overview

Developed a full-stack compiler for MicroJava, a simplified subset of Java, designed to translate high-level source code into executable bytecode for the MicroJava Virtual Machine (MJVM).

The project was implemented in four distinct phases, covering the entire compilation pipeline from raw text to machine-executable instructions.

## Lexical Analysis

Tokenized source code into discrete lexical units (keywords, identifiers, literals, operators).

Used JFlex to define regular expressions for the MicroJava language specification.

## Syntax Analysis

Developed an LALR(1) parser using CUP.

Generated an Abstract Syntax Tree (AST) representing the hierarchical structure of the program.

## Semantic Analysis

Enforced static semantics and type rules (ensuring variables are declared before use, type compatibility in expressions).

Performed AST traversal using the Visitor pattern to populate the Symbol Table and manage scopes.

Handled method signatures, inheritance rules, and array initialization checks.

## Code Generation

Translated the validated AST into executable MJVM bytecode.

Generated low-level instructions compatible with the MicroJava runtime environment.


## Technologies & Tools

Language: Java

Lexer Generator: JFlex

Parser Generator: CUP 

Target Architecture: MicroJava Virtual Machine (MJVM)

Build System: Ant

Libraries: mj-runtime, symboltable, log4j


## How to Run

Run the compile or repackage target in build.xml. This automatically triggers the generation of the Lexer and Parser and organizes the packages correctly.

Run MJTest.java. Within this file, you can configure the output to be redirected to an external file.

Run the runObj target to generate the final executable bytecode.

