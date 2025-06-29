package com.baeldung.fj;

import fj.F;
import fj.Show;
import fj.data.Array;
import fj.data.List;
import fj.data.Option;
import fj.function.Characters;
import fj.function.Integers;

public class FunctionalJavaMain {

    public static final F<Integer, Boolean> isEven = i -> i % 2 == 0;

    public static void main(String[] args) {

        List<Integer> fList = List.list(3, 4, 5, 6);
        List<Boolean> evenList = fList.map(isEven);
        Show.listShow(Show.booleanShow).println(evenList);

        fList = fList.map(i -> i + 1);
        Show.listShow(Show.intShow).println(fList);

        Array<Integer> a = Array.array(17, 44, 67, 2, 22, 80, 1, 27);
        Array<Integer> b = a.filter(Integers.even);
        Show.arrayShow(Show.intShow).println(b);

        Array<String> array = Array.array("Welcome", "To", "baeldung");
        Boolean isExist = array.exists(s -> List.fromString(s).forall(Characters.isLowerCase));
        System.out.println(isExist);

        Array<Integer> intArray = Array.array(17, 44, 67, 2, 22, 80, 1, 27);
        int sum = intArray.foldLeft(Integers.add, 0);
        System.out.println(sum);

        Option<Integer> n1 = Option.some(1);
        Option<Integer> n2 = Option.some(2);

        F<Integer, Option<Integer>> f1 = i -> i % 2 == 0 ? Option.some(i + 100) : Option.none();

        Option<Integer> result1 = n1.bind(f1);
        Option<Integer> result2 = n2.bind(f1);

        Show.optionShow(Show.intShow).println(result1);
        Show.optionShow(Show.intShow).println(result2);
    }

}
