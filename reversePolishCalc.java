// Blake Haddad & Andy Nguyen
// CS-570
// Reverse Polish Calculator Notation Assignment

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

class reversePolishCalc{

    Scanner reader = new Scanner(System.in);  // Reading from System.in

    public Stack<Character> opStack = new Stack<Character>();
    public Vector<String> postfixExp = new Vector<String>();
    public Vector<String> infixExp = new Vector<String>();
    public static void main(String[] args){
        reversePolishCalc calc = new reversePolishCalc();
        calc.promptUserForInfixInput();
        System.out.println("\nInfix Equation:");
        calc.printInfxExp();
        calc.convertToPostfix();
        System.out.println("\nPostfix Equation:");
        calc.printPostfixExp();
        System.out.println();
        // System.out.print(calc.getOpStackTop());

    }

    public void promptUserForInfixInput(){
        String infix = "";
        while(infix.isEmpty()){
                System.out.print("Enter an Infix expression: ");
                infix = reader.nextLine(); // Scans the next token of the input as an int.
                // if(resumeGame == 0 || resumeGame == 1){
                //     break;
                // }
        }
        setInfixExp(infix);
    }

    public void setInfixExp(String infix){
        String toAdd;
        for(int i = 0; i < infix.length(); i++){
            toAdd = "";
            if(infix.charAt(i) == ' '){
                continue;
            }else if(!checkIfNumber(infix.charAt(i))){
                toAdd = Character.toString(infix.charAt(i));
            }else{
                toAdd = Character.toString(infix.charAt(i));
                if(i != infix.length()-1){
                    while(checkIfNumber(infix.charAt(i+1))){
                        i++;
                        toAdd += infix.charAt(i);
                        if(i == infix.length()-1){
                            break;
                        }
                    }
                }
            }
            this.infixExp.add(toAdd);
        }
    }

    public void printInfxExp(){
        for(int i=0; i< this.infixExp.size(); i++){
            System.out.print(" ");
            System.out.print(this.infixExp.get(i));
            System.out.print(" ");
        }
    }

    public void printPostfixExp(){
        for(int i=0; i< this.postfixExp.size(); i++){
            System.out.print(" ");
            System.out.print(this.postfixExp.get(i));
            System.out.print(" ");
        }
    }

    public boolean checkIfNumber(char a){
        if((int) a >= 48 && (int) a <= 57){
            return true;
        }else{
            return false;
        }
    }

    public void convertToPostfix(){
        String t;
        while(!this.infixExp.isEmpty()){
            t = this.infixExp.firstElement();
            this.infixExp.remove(0);
            if(checkIfNumber(t.charAt(0))){
                this.postfixExp.add(t);
            }else if(this.opStack.isEmpty()){
                this.opStack.push(t.charAt(0));
            }else if(t.equals("(")){
                this.opStack.push(t.charAt(0));
            }else if(t.equals(")")){
                while(this.opStack.peek() != '('){
                    this.postfixExp.add(Character.toString(this.opStack.peek()));
                    this.opStack.pop();
                }
                this.opStack.pop();//get rid of ( character
            }else{
                while(!this.opStack.isEmpty() && this.opStack.peek() != '(' && checkPrecedence(t, Character.toString(this.opStack.peek()))){
                    this.postfixExp.add(Character.toString(this.opStack.peek()));
                    this.opStack.pop();
                }
                this.opStack.push(t.charAt(0));
            }
        }
        while(!this.opStack.isEmpty()){
            this.postfixExp.add(Character.toString(this.opStack.peek()));
            this.opStack.pop();
        }

    }

    public boolean checkPrecedence(String a, String b){//checks if precedence of a <= precedence of b. Assumes a comes later in the equation than b
        if(a.equals("+") || a.equals("-")){
            return true;
        }else{
            if(b.equals("*") || b.equals("/")){
                return true;
            }else{
                return false;
            }
        }
    }

    public char getOpStackTop(){
        return (char) this.opStack.peek();
    }

}