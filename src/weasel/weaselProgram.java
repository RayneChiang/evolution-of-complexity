package weasel;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


/**
 * @author Rayne
 * @version 1.0
 */


public class weaselProgram {

    /**
     *
     * @return random inividuals
     */
    public static List<Character> whenGeneratingRandom_thenCorrect(int targetStringLength) {
        int leftLimit = 32; // numeral '0'
        int rightLimit = 127; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                //.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();


        List<Character> randomStringList = generatedString.chars().mapToObj(c->(char)c).collect(Collectors.toList());
        return randomStringList;
    }


    /**
     * the fitness function
     * @param target target character List
     * @param evaludated evaluated character List
     * @return the fitness number
     */
    public static int fitness(List<Character>target, List<Character>evaludated){
        int fit = 0;
        if(target.size() != evaludated.size()){
            return  0;
        }
        for (int i = 0; i < target.size() ; i++){
            if(target.get(i) == evaludated.get(i)){
                fit++;
            }
        }
        return fit;
    }

    /**
     * implemention of mutation in evoolution algorithm
     * @param list the character List to be muted
     * @return muted character List
     */
    public static List<Character> mutateString (List<Character> list){
        Random random = new Random();
        float maxProbability = (float) 1/list.size();
        for(int i =0; i < list.size(); i++){
            if(Math.random() < maxProbability){
                char replace = random.ints(32,128).limit(1).
                        collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).
                        toString().charAt(0);
                list.set(i,replace);
            }
        }
        return list;
    }

    public static List<Character> crossOver (List<Character> parentOne, List<Character> parentTwo){
        List<Character> returnList = new LinkedList<>();
        for(int i =0; i < parentOne.size(); i++){
            if(Math.random() < 0.5){
                returnList.add(parentOne.get(i));
            }else{
                returnList.add(parentTwo.get(i));
            }
        }
        return returnList;
    }

    public static void main(String[] args) {
        String target = "methinks it is like a weasel";
        int maxIndividuals = 100;
        List<Character> targetList = target.chars().mapToObj(c->(char)c).collect(Collectors.toList());


        // mutation hill-climber which is tooooo slow, and I have waited for a long time without any things happened
//        List<Character> randomList = whenGeneratingRandom_thenCorrect();
//        while(fitness(targetList,randomList)!=targetList.size()){
//            List<Character> mutedList = mutateString(randomList);
//            System.out.println(fitness(targetList,mutedList));
//            if(fitness(targetList,mutedList) > fitness(targetList, randomList)){
//                System.arraycopy(randomList, 0, mutedList, 0, mutedList.size());
//            }
//        }

        ArrayList<List> individuals = new ArrayList<>();
        for(int i=0; i < maxIndividuals; i++){
            individuals.add(whenGeneratingRandom_thenCorrect(target.length()));
        }
        // initial parent A
        int fitnessFound = 0;
        int count = 0;
        while(fitnessFound == 0){
            System.out.println(count);
            count++;
            Random random = new Random();
            List<Character> A = individuals.get(random.nextInt(maxIndividuals));
            List<Character> B = individuals.get(random.nextInt(maxIndividuals));
            List<Character> C = individuals.get(random.nextInt(maxIndividuals));
            List<Character> D = individuals.get(random.nextInt(maxIndividuals));

            List<Character> ParentOne = fitness(targetList, A)> fitness(targetList, B)? A:B;
            List<Character> ParentTwo = fitness(targetList, C)> fitness(targetList, D)? C:D;
            List<Character> Parent = crossOver(ParentOne,ParentTwo);
            List<Character> Child = mutateString(Parent);
            if(fitness(targetList,Parent)==targetList.size()||fitness(targetList,Child)==targetList.size()){
                System.out.println(Parent);
                System.out.println(Child);
                break;
            }
            int posA = random.nextInt(maxIndividuals);
            int posB = random.nextInt(maxIndividuals);
            int setPos = fitness(targetList, individuals.get(posA)) > fitness(targetList, individuals.get(posB))? posB:posA;
            individuals.set(setPos,Child);
        }





    }
}

