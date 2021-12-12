package com.l4ikaa.a24;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {

    private List<String> solutions = new ArrayList<String>();

    public Solver(int w, int x, int y, int z){
        List<Double> numbers = new ArrayList<Double>();
        numbers.add(w+0.0);
        numbers.add(x+0.0);
        numbers.add(y+0.0);
        numbers.add(z+0.0);
        List<String> strArr = new ArrayList<String>();
        strArr.add(w+"");
        strArr.add(x+"");
        strArr.add(y+"");
        strArr.add(z+"");
        helper(numbers,strArr);
        solutions=removeDuplicates(solutions);
    }

    public boolean isImpossible(){
        return solutions.size()==0;
    }

    public List<String> getSolutions(){
        return solutions;
    }

    public void helper(List<Double> arr, List<String> strArr ) {

        if ( arr.size() == 1 ) {
            if ( Math.abs(arr.get(0)-24) < 0.001 ) {
                String eq = strArr.get(0);
                for ( int i = 0; i < eq.length(); i++ ) {
                    if ( eq.charAt(i) == '(' && eq.charAt(i+2) == ')' ) {
                        eq=eq.substring(0,i)+eq.charAt(i+1)+eq.substring(i+3);
                        //2+4/2*6
                    } else if ( eq.charAt(i) == '(' && eq.charAt(i+3) == ')' ) {
                        eq=eq.substring(0,i)+eq.charAt(i+1)
                                +eq.charAt(i+2)+eq.substring(i+4);
                    }
                }
                eq = reorder(eq);
                solutions.add(reorder(eq));
            }
            return;
        }
        for ( int i = 0; i < arr.size(); i++ ) {
            for ( int j = i+1; j < arr.size(); j++ ) {
                List<Double> nArr = new ArrayList<Double>();
                List<String> nString = new ArrayList<String>();
                for ( int k = 0; k < arr.size(); k++ ) {
                    if ( k!=i && k!=j ) {
                        nArr.add(arr.get(k));
                        nString.add(""+strArr.get(k));
                    }
                }

                double v1 = arr.get(i);
                double v2 = arr.get(j);
                String s1 = strArr.get(i);
                String s2 = strArr.get(j);

                if ( v1 > v2 || (v1 == v2 && s1.compareTo(s2) < 0) ) {
                    double tempI = v1;
                    String tempS = s1;
                    v1 = v2;
                    v2 = tempI;
                    s1 = s2;
                    s2= tempS;
                }

                int[][] ops = new int[2][4];
                // current problem: duplicates
                // solution: normalizing
                boolean para = false;
                for ( char c : s1.toCharArray()) {
                    if ( c == '(' )
                        para = true;
                    if ( c == '+' && !para  ) {
                        ops[0][0]++;
                    } else if ( c == '-' && !para ) {
                        ops[0][1]++;
                    } else if ( c == '*' ) {
                        ops[0][2]++;
                    } else if ( c == '/' ) {
                        ops[0][3]++;
                    }
                }
                para = false;
                for ( char c : s2.toCharArray() ) {
                    if ( c == '(' )
                        para = true;
                    if ( c == '+' && !para  ) {
                        ops[1][0]++;
                    } else if ( c == '-' && !para ) {
                        ops[1][1]++;
                    } else if ( c == '*' ) {
                        ops[1][2]++;
                    } else if ( c == '/' ) {
                        ops[1][3]++;
                    }
                }

                nArr.add(v1+v2);
                nString.add(s1+"+"+s2);
                helper(nArr, nString);
                nArr.remove(nArr.size()-1);
                nString.remove(nString.size()-1);

                nArr.add(v1*v2); //check for 3 & 4 conditions
                String sMul = "";
                if ( (ops[1][1]+ops[1][0])==0 )
                    sMul+=(s2+"*");
                else
                    sMul+=("("+s2+")"+"*");
                if ( (ops[0][1]+ops[0][0])==0 )
                    sMul+=s1;
                else
                    sMul+=("("+s1+")");
                nString.add(sMul);
                helper(nArr, nString);
                nArr.remove(nArr.size()-1);
                nString.remove(nString.size()-1);

                if ( (ops[0][1]+ops[0][0])==0 ) {
                    nArr.add(v2-v1);
                    nString.add(s2+"-"+s1);
                    helper(nArr, nString);
                    nArr.remove(nArr.size()-1);
                    nString.remove(nString.size()-1);
                }

                if ( (ops[0][3]+ops[0][2])==0 ) {
                    nArr.add(v2/v1);
                    if ( s1.length() == 1 ) {
                        if ( ( ops[1][1]+ops[1][0]) !=0 )
                            nString.add("("+s2+")"+"/"+s1);
                        else
                            nString.add(s2+"/"+s1);
                    } else if ( (ops[1][1]+ops[1][0])==0 )
                        nString.add(s2+"/"+"("+s1+")");
                    else
                        nString.add("("+s2+")"+"/"+"("+s1+")");
                    helper(nArr, nString);
                    nArr.remove(nArr.size()-1);
                    nString.remove(nString.size()-1);
                }

            }
        }
    }

    public List<String> removeDuplicates(List<String> list) {
        ArrayList<String> newList = new ArrayList<String>();
        for ( String s : list ) {
            if ( !newList.contains(s) ) {
                newList.add(s);
            }
        }
        return newList;
    }

    public void printList(List<String> list) {
        for ( String s : list ) {
            System.out.println(s);
        }
    }

    public String reorder(String eq) {
        /* Steps
         * If there are parentheses
         * 1 pair
         * 3 #'s in parentheses: reorder within parentheses
         * 2 #'s in parentheses: reorder outside parentheses
         * 2 pairs
         * already in order
         * No parentheses: reorder
         * Separate equation into chunks
         * 1 chunk is separated by - and + or parentheses
         * pass in parentheses chunk to be reordered using reorder method
         * make parenthesized portion 0 (as placeholder)
         * reorder chunks (with just * and / symbols
         */
        int count = 0;
        for ( char c : eq.toCharArray() )
            if ( c=='(' ) count++;
        if ( count == 2 )
            return eq;
        if ( count == 1 ) {
            int i = eq.indexOf('(');
            int j = eq.indexOf(')');
            String pEq = eq.substring(i+1,j);
            String newEq= eq.substring(0,i)+"a"+eq.substring(j+1);
            // replace parenthesized eq w/ a
            if ( newEq.length() <= 4 )
                return eq;
            newEq = reorder(newEq);
            i = newEq.indexOf('a');
            return newEq.substring(0,i)+"("+pEq+")"+newEq.substring(i+1);
        }

        // no parentheses case
        if ( eq.contains("*") || eq.contains("/") ) { //remove chunk and replace it with b
            int i;
            if ( eq.contains("*") && eq.contains("/") )
                i = Math.min(eq.indexOf("*"),eq.indexOf("/"));
            else if ( eq.contains("*") && !eq.contains("/") )
                i = eq.indexOf("*");
            else
                i = eq.indexOf("/");
            int j = i+1;
            while ( i >= 0 && !isOp(eq.charAt(i)) )
                i--;
            while ( j < eq.length() && eq.charAt(j)!='-' && eq.charAt(j)!='+' )
                j++;
            i--;
            j--;
            String newEq = eq.substring(0,i)+"b"+eq.substring(j+1);
            String chunk = eq.substring(i,j+1);
            newEq = reorder(newEq);
            chunk="*"+chunk;
            List<String> list = new ArrayList<String>();
            int start = 0;
            int last = 1;
            while ( last < chunk.length() ) {
                if ( isOp(chunk.charAt(last)) ) {
                    list.add(chunk.substring(start,last));
                    start = last;
                }
                last++;
            }
            list.add(chunk.substring(start));
            Collections.sort(list);
            chunk = "";
            for ( String s : list ) {
                chunk+=s;
            }
            chunk=chunk.substring(1);
            i = newEq.indexOf('b');
            return newEq.substring(0,i)+chunk+newEq.substring(i+1);
        }
        eq="+"+eq;
        List<String> list = new ArrayList<String>();
        int start = 0;
        int last = 1;
        while ( last < eq.length() ) {
            if ( isOp(eq.charAt(last)) ) {
                list.add(eq.substring(start,last));
                start = last;
            }
            last++;
        }
        list.add(eq.substring(start));
        Collections.sort(list);
        eq = "";
        for ( String s : list ) {
            eq+=s;
        }
        eq=eq.substring(1);
        return eq;

    }

    public boolean isNum(char c) {
        return (c=='0' || c=='1' || c=='2' || c=='3' || c=='4' || c=='5' || c=='6' ||
                c=='7' || c=='8' || c=='9');
    }

    public boolean isOp(char c) {
        return (c=='+' || c=='-' || c=='/' || c=='*' );
    }

}

