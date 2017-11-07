package com.company;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;


public class ParserString {
    String str = "(5*(10-3))*25-70/35+5*(3*(10/5))";
    String delimiters = "+-*/()";
    List<String> list = parseString(str);
    int res = (5*(10-3))*25-70/35+5*(3*(10/5));

    public ParserString()
    {
        System.out.println(res);
        make(list);
    }

    public List<String> parseString(String str)
    {
        List<String> list = new CopyOnWriteArrayList<>();

        StringTokenizer sTok = new StringTokenizer(str,delimiters , true);


        while(sTok.hasMoreTokens())
        {
            list.add(sTok.nextToken());
        }
        System.out.println(list);
        return list;
    }

    public void make(List<String> list)
    {
        while (list.size() != 1)
        {
            List<String> tmp = new CopyOnWriteArrayList<>();
            int start = 0, finish = 0;
            int listSize;
            for (int i=0; i<list.size(); i++)
            {
                if (list.get(i).equals("("))
                {
                    start = i;
                }
                if (list.get(i).equals(")"))
                {
                    finish = i;
                    listSize = finish-start;
                    tmp.addAll(list.subList(start+1, finish));
                    String el = makeMath(tmp);
                    for(int c=0; c<=listSize; c++)
                    {
                        list.remove(start);
                    }
                    list.add(start, el);
                    tmp.clear();
                }

            }
            try
            {
                makeMath(list);
            }
            catch(Exception e)
            {
                continue;
            }
            System.out.println(tmp);
            System.out.println(list);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public String makeMath(List<String> list)
    {
        for (String ss: list)
        {
            if (ss.equals("*") || ss.equals("/"))
            {
                for (int i=0; i < list.size(); i++)
                {
                    Double number;
                    if (list.get(i).equals("*"))
                    {
                        number = mult(Double.parseDouble(list.get(i-1)), Double.parseDouble(list.get(i+1)));
                        list.set(i, number.toString());
                        list.remove(i+1); list.remove(i-1);
                    }
                    else if (list.get(i).equals("/"))
                    {
                        number = div(Double.parseDouble(list.get(i-1)), Double.parseDouble(list.get(i+1)));
                        list.set(i, number.toString());
                        list.remove(i+1); list.remove(i-1);
                    }
                }
            }
        }
        System.out.println(list);
        for (String ss: list)
        {
            if (ss.equals("+") || ss.equals("-"))
            {
                for (int i=0; i < list.size(); i++)
                {
                    Double number;
                    if (list.get(i).equals("+"))
                    {
                        number = plus(Double.parseDouble(list.get(i-1)), Double.parseDouble(list.get(i+1)));
                        list.set(i, number.toString());
                        list.remove(i+1); list.remove(i-1);
                    }
                    else if (list.get(i).equals("-"))
                    {
                        number = minus(Double.parseDouble(list.get(i-1)), Double.parseDouble(list.get(i+1)));
                        list.set(i, number.toString());
                        list.remove(i+1); list.remove(i-1);
                    }
                }
            }
        }
        return list.get(0);
    }


    double plus(double a, double b)
    {
        return a+b;
    }
    double minus(double a, double b)
    {
        return a-b;
    }
    double mult(double a, double b)
    {
        return a*b;
    }
    double div(double a, double b)
    {
        return a/b;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new ParserString();

    }

}