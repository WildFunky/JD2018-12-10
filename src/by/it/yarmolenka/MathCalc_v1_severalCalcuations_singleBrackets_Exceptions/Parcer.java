package by.it.yarmolenka.MathCalc_v1_severalCalcuations_singleBrackets_Exceptions;

import by.it.yarmolenka.MathCalc_v1_severalCalcuations_singleBrackets_Exceptions.Operations.*;
import by.it.yarmolenka.MathCalc_v1_severalCalcuations_singleBrackets_Exceptions.Variables.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Parcer {
    Var calc(String expression) throws CalcException {
        if (expression.matches(Patterns.VARIABLE)) {
            return Var.getVariable(expression);
        }
        Pattern bra = Pattern.compile(Patterns.BRACKETS);
        Matcher m1 = bra.matcher(expression);
        while (m1.find()) {
            Var res = calc(m1.group().substring(1, m1.group().length() - 1));
            expression = expression.replace(m1.group(), res.toString());
        }
        Pattern cal = Pattern.compile(Patterns.OPERATION);
        Matcher m = cal.matcher(expression);
        List<String> list = new ArrayList<>();
        while (m.find()) list.add(m.group());
        String[] split = expression.trim().split(Patterns.OPERATION);
        if (list.get(0).equals("=")) {
            if (!split[0].matches(Patterns.VARIABLE))
                throw new CalcException("Обозначайте переменную латинскими маленькими буквами");
            Var res;
            if (split.length == 2)
                res = Var.createVar(split[1]);
            else
                res = calc(expression.substring(expression.indexOf('=') + 1));
            Var.addVar(split[0], res);
            return res;
        }


        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("*")) {
                Var one = Var.createVar(split[i]);
                Var two = Var.createVar(split[i + 1]);
                Var res = Mul.mulVarVar(one, two);
                split[i + 1] = res.toString();
                split[i] = null;
                list.remove(i);
                list.add(i, null);
                continue;
            }
            if (list.get(i).equals("/")) {
                Var one = Var.createVar(split[i]);
                Var two = Var.createVar(split[i + 1]);
                Var res = Div.divVarVar(one, two);
                split[i + 1] = res.toString();
                split[i] = null;
                list.remove(i);
                list.add(i, null);
            }
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null) continue;
            if (list.get(i).equals("+")) {
                Var one = Var.createVar(split[i]);
                Var two = null;
                int k = 0;
                for (int j = i + 1; j < split.length; j++)
                    if (split[j] != null) {
                        two = Var.createVar(split[j]);
                        k = j;
                        break;
                    }
                Var res = Add.addVarVar(one, two);
                split[k] = res.toString();
                split[i] = null;
                list.remove(i);
                list.add(i, null);
                continue;
            }
            if (list.get(i).equals("-")) {
                Var one = Var.createVar(split[i]);
                Var two = null;
                int k = 0;
                for (int j = i + 1; j < split.length; j++)
                    if (split[j] != null) {
                        two = Var.createVar(split[j]);
                        k = j;
                        break;
                    }
                Var res = Sub.subVarVar(one, two);
                split[k] = res.toString();
                split[i] = null;
                list.remove(i);
                list.add(i, null);
            }

        }

        return Var.createVar(split[split.length - 1]);
    }
}