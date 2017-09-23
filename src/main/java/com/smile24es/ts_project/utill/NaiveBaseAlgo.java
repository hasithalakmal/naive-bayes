package com.smile24es.ts_project.utill;

import com.smile24es.ts_project.beans.Model.CriteriaEnum;
import com.smile24es.ts_project.beans.Model.DataSet;
import com.smile24es.ts_project.beans.Model.LikelihoodRecode;
import com.smile24es.ts_project.beans.Model.LikelihoodTable;
import com.smile24es.ts_project.beans.Model.ListOfLikelihoodTables;
import com.smile24es.ts_project.beans.Model.NiveBaseResult;
import com.smile24es.ts_project.beans.Model.Recode;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component("naiveBaseAlgo")
public class NaiveBaseAlgo {

//    public static void main(String[] args) {
//        ListOfLikelihoodTables listOfLikelihoodTables = trainingNaiveBayesAlgo();
//        System.out.println(listOfLikelihoodTables.toString());
//        String jsonInString = null;
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            jsonInString = mapper.writeValueAsString(listOfLikelihoodTables);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        System.out.println(jsonInString);
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//
//        System.out.println(">>>>>>>>>>>>>> Training Completed >>>>>>>>>>>>>>>>>>>>>>>>");
//
//        double positiveLikelihood = calculatePosteriorProbabilityOfYes(listOfLikelihoodTables, "100", "aaa", "xxx", "xxx", "Hindi", "India", "xxx");
//        double negativeLikelihood = calculatePosteriorProbabilityOfNo(listOfLikelihoodTables, "100", "aaa", "xxx", "xxx", "Hindi", "India", "xxx");
//        double positiveProbability = positiveLikelihood / (positiveLikelihood + negativeLikelihood);
//        double negativeProbability = negativeLikelihood / (positiveLikelihood + negativeLikelihood);
//        System.out.println("positiveProbability = "+positiveProbability);
//        System.out.println("negativeProbability = "+negativeProbability);
//    }
//    public static void main(String[] args) {
//        DataSet trainingDataSet = getTrainingDataset();
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonInString = null;
//        try {
//            jsonInString = mapper.writeValueAsString(trainingDataSet);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        System.out.println(jsonInString);
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//    }
    public NiveBaseResult getPrediction(ListOfLikelihoodTables listOfLikelihoodTables, Recode recode) {
        double positiveLikelihood = calculatePosteriorProbabilityOfYes(listOfLikelihoodTables, recode);
        double negativeLikelihood = calculatePosteriorProbabilityOfNo(listOfLikelihoodTables, recode);
        System.out.println("******************************************************************");
        System.out.println("positiveLikelihood = " + positiveLikelihood + " negativeLikelihood = " + negativeLikelihood);
        System.out.println("******************************************************************");
        double positiveProbability = positiveLikelihood / (positiveLikelihood + negativeLikelihood);
        double negativeProbability = negativeLikelihood / (positiveLikelihood + negativeLikelihood);
        NiveBaseResult niveBaseResult = new NiveBaseResult(positiveLikelihood, negativeLikelihood, positiveProbability, negativeProbability, recode);
        if (positiveProbability >= 5.00) {
            niveBaseResult.setIsProfitable(true);
        } else {
            niveBaseResult.setIsProfitable(false);
        }
        return niveBaseResult;
    }

    private static double calculatePosteriorProbabilityOfYes(ListOfLikelihoodTables listOfLikelihoodTables, Recode recode) {
        double posteriorProbability = 0.00;
        double likelihoodProbabilityOnDirector = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.DIRECTOR.toString(), recode.getDirector(), true);
        double likelihoodProbabilityOnActor = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.ACTOR_ONE.toString(), recode.getActorOne(), true);
        double likelihoodProbabilityGenrs = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.GENRES.toString(), recode.getGenres(), true);
        double likelihoodProbabilityBudgetID = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.BUDGET_ID.toString(), recode.getBudgetID(), true);
        double likelihoodProbabilityOnLanguage = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.LANGUAGE.toString(), recode.getLanguage(), true);
        double likelihoodProbabilityOnCountry = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.COUNTRY.toString(), recode.getCountry(), true);
        double likelihoodProbabilityOnDuration = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.DURATION.toString(), recode.getDuration(), true);
        posteriorProbability = likelihoodProbabilityOnDirector * likelihoodProbabilityOnActor * likelihoodProbabilityGenrs * likelihoodProbabilityBudgetID
                * likelihoodProbabilityOnLanguage * likelihoodProbabilityOnCountry * likelihoodProbabilityOnDuration * listOfLikelihoodTables.getListOfLikelihoodTables().get(CriteriaEnum.DIRECTOR.toString()).getPositiveProbability();

        return posteriorProbability;
    }

    private static double calculatePosteriorProbabilityOfNo(ListOfLikelihoodTables listOfLikelihoodTables, Recode recode) {
        double posteriorProbability = 0.00;
        double likelihoodProbabilityOnDirector = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.DIRECTOR.toString(), recode.getDirector(), false);
        double likelihoodProbabilityOnActor = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.ACTOR_ONE.toString(), recode.getActorOne(), false);
        double likelihoodProbabilityGenrs = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.GENRES.toString(), recode.getGenres(), false);
        double likelihoodProbabilityBudgetID = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.BUDGET_ID.toString(), recode.getBudgetID(), false);
        double likelihoodProbabilityOnLanguage = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.LANGUAGE.toString(), recode.getLanguage(), false);
        double likelihoodProbabilityOnCountry = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.COUNTRY.toString(), recode.getCountry(), false);
        double likelihoodProbabilityOnDuration = calculateLikelihoodProbability(listOfLikelihoodTables, CriteriaEnum.DURATION.toString(), recode.getDuration(), false);
        posteriorProbability = posteriorProbability = likelihoodProbabilityOnDirector * likelihoodProbabilityOnActor * likelihoodProbabilityGenrs * likelihoodProbabilityBudgetID
                * likelihoodProbabilityOnLanguage * likelihoodProbabilityOnCountry * likelihoodProbabilityOnDuration * listOfLikelihoodTables.getListOfLikelihoodTables().get(CriteriaEnum.DIRECTOR.toString()).getNegativeProbability();

        return posteriorProbability;
    }

    private static double calculateLikelihoodProbability(ListOfLikelihoodTables listOfLikelihoodTables, String criteria, String property, boolean isProfitable) {
        double likelihoodProbability = 1.00;
        LikelihoodTable likelihoodTable = listOfLikelihoodTables.getListOfLikelihoodTables().get(criteria);
        if (likelihoodTable != null) {
            LikelihoodRecode likelihoodRecode = likelihoodTable.getListOfLikelihoodRecode().get(property);

            double response = 0.00;
            double totalResponse = 0.00;
            if (likelihoodRecode != null) {
                if (isProfitable) {
                    response = (double) likelihoodRecode.getNumberOfPositiveResponse();
                    totalResponse = (double) likelihoodTable.getTotalPositive();
                } else {
                    response = (double) likelihoodRecode.getNumberOfNegativeResponse();
                    totalResponse = (double) likelihoodTable.getTotalNegative();
                    System.out.println("response = " + response + "totalResponse = " + totalResponse);
                }

                likelihoodProbability = response / totalResponse;
            }
        }
        System.out.println("isProfitable = " + isProfitable + " --- " + criteria + " --- " + property + "  ---  " + likelihoodProbability);
        return likelihoodProbability;
    }

    public ListOfLikelihoodTables trainingNaiveBayesAlgo(DataSet trainingDataSet) {
        //DataSet trainingDataSet = getTrainingDataset();

        ListOfLikelihoodTables listOfLikelihoodTables;
        Map<String, LikelihoodTable> likelihoodTables = new HashMap<String, LikelihoodTable>();

        for (CriteriaEnum criteriaEnum : CriteriaEnum.values()) {
            LikelihoodTable likelihoodTable = getLikelihoodTable(trainingDataSet, criteriaEnum.toString());
            if (likelihoodTable != null) {
                likelihoodTables.put(criteriaEnum.toString(), likelihoodTable);
            }
        }

        if (likelihoodTables.isEmpty()) {
            listOfLikelihoodTables = null;
        } else {
            listOfLikelihoodTables = new ListOfLikelihoodTables();
            listOfLikelihoodTables.setListOfLikelihoodTables(likelihoodTables);
        }
        return listOfLikelihoodTables;
    }

    private static LikelihoodTable getLikelihoodTable(DataSet trainingDataSet, String criteria) {

        Map<String, LikelihoodRecode> mapOfLikelihoodRecodes;
        if (criteria.equals(CriteriaEnum.DIRECTOR.toString())) {
            mapOfLikelihoodRecodes = getListOfLikeHoodRecodesOnNumberOfDirectorsFBLikes(trainingDataSet);
        } else if (criteria.equals(CriteriaEnum.ACTOR_ONE.toString())) {
            mapOfLikelihoodRecodes = getListOfLikeHoodRecodesOnActorOneName(trainingDataSet);
        } else if (criteria.equals(CriteriaEnum.GENRES.toString())) {
            mapOfLikelihoodRecodes = getListOfLikeHoodRecodesOnActorTwoName(trainingDataSet);
        } else if (criteria.equals(CriteriaEnum.BUDGET_ID.toString())) {
            mapOfLikelihoodRecodes = getListOfLikeHoodRecodesOnActorThreeName(trainingDataSet);
        } else if (criteria.equals(CriteriaEnum.COUNTRY.toString())) {
            mapOfLikelihoodRecodes = getListOfLikeHoodRecodesOnCountry(trainingDataSet);
        } else if (criteria.equals(CriteriaEnum.DURATION.toString())) {
            mapOfLikelihoodRecodes = getListOfLikeHoodRecodesOnDuration(trainingDataSet);
        } else if (criteria.equals(CriteriaEnum.LANGUAGE.toString())) {
            mapOfLikelihoodRecodes = getListOfLikeHoodRecodesOnLanguage(trainingDataSet);
        } else {
            mapOfLikelihoodRecodes = new HashMap<String, LikelihoodRecode>();
        }

        LikelihoodTable likelihoodTable;
        if (mapOfLikelihoodRecodes.isEmpty()) {
            likelihoodTable = null;
        } else {
            likelihoodTable = new LikelihoodTable();
            likelihoodTable.setCriteria(criteria);

            likelihoodTable.setListOfLikelihoodRecode(mapOfLikelihoodRecodes);

            int numberOfTotalPositiveResponses = 0;
            int numberOfTotalNegativeResponses = 0;

            for (LikelihoodRecode likelihoodRecode : mapOfLikelihoodRecodes.values()) {
                numberOfTotalPositiveResponses += likelihoodRecode.getNumberOfPositiveResponse();
                numberOfTotalNegativeResponses += likelihoodRecode.getNumberOfNegativeResponse();
            }
            likelihoodTable.setTotalNegative(numberOfTotalNegativeResponses);
            likelihoodTable.setTotalPositive(numberOfTotalPositiveResponses);
            likelihoodTable.setPositiveProbability((double) numberOfTotalPositiveResponses / (double) trainingDataSet.getListOfRecodes().size());
            likelihoodTable.setNegativeProbability((double) numberOfTotalNegativeResponses / (double) trainingDataSet.getListOfRecodes().size());
        }

        return likelihoodTable;
    }

    private static Map<String, LikelihoodRecode> getListOfLikeHoodRecodesOnNumberOfDirectorsFBLikes(DataSet trainingDataSet) {
        Map<String, LikelihoodRecode> likelihoodRecodeMap = new HashMap<String, LikelihoodRecode>();

        for (Recode recode : trainingDataSet.getListOfRecodes()) {
            String directorFacebookLikes = recode.getDirector();
            populateLikelihoodRecode(likelihoodRecodeMap, recode, directorFacebookLikes);
        }

        Map<String, LikelihoodRecode> mapOfLikelihoodRecodes;
        if (likelihoodRecodeMap.isEmpty()) {
            mapOfLikelihoodRecodes = new HashMap<String, LikelihoodRecode>();
        } else {
            mapOfLikelihoodRecodes = calculateLikelihoodRecodesProbabilities(trainingDataSet, likelihoodRecodeMap);
        }

        return mapOfLikelihoodRecodes;
    }

    private static void populateLikelihoodRecode(Map<String, LikelihoodRecode> likelihoodRecodeMap, Recode recode, String directorFacebookLikes) {
        if (StringUtils.isNotBlank(directorFacebookLikes)) {
            LikelihoodRecode likelihoodRecode;
            //if property is already in list
            if (!likelihoodRecodeMap.containsKey(directorFacebookLikes)) {
                likelihoodRecode = new LikelihoodRecode(directorFacebookLikes, 0, 0, 0);
                likelihoodRecodeMap.put(directorFacebookLikes, likelihoodRecode);
            }

            boolean isProfitable = recode.isProfitable();
            if (isProfitable) {
                likelihoodRecodeMap.get(directorFacebookLikes).increaseNumberOfPositiveResponse();
            } else {
                likelihoodRecodeMap.get(directorFacebookLikes).increaseNumberOfNegativeResponse();
            }
        }
    }

    private static Map<String, LikelihoodRecode> getListOfLikeHoodRecodesOnActorOneName(DataSet trainingDataSet) {
        Map<String, LikelihoodRecode> likelihoodRecodeMap = new HashMap<String, LikelihoodRecode>();

        for (Recode recode : trainingDataSet.getListOfRecodes()) {
            String actorOneName = recode.getActorOne();
            populateLikelihoodRecode(likelihoodRecodeMap, recode, actorOneName);
        }

        Map<String, LikelihoodRecode> mapOfLikelihoodRecodes;
        if (likelihoodRecodeMap.isEmpty()) {
            mapOfLikelihoodRecodes = new HashMap<String, LikelihoodRecode>();
        } else {
            mapOfLikelihoodRecodes = calculateLikelihoodRecodesProbabilities(trainingDataSet, likelihoodRecodeMap);
        }

        return mapOfLikelihoodRecodes;
    }

    private static Map<String, LikelihoodRecode> getListOfLikeHoodRecodesOnActorTwoName(DataSet trainingDataSet) {
        Map<String, LikelihoodRecode> likelihoodRecodeMap = new HashMap<String, LikelihoodRecode>();

        for (Recode recode : trainingDataSet.getListOfRecodes()) {
            String actorTwoName = recode.getGenres();
            populateLikelihoodRecode(likelihoodRecodeMap, recode, actorTwoName);
        }

        Map<String, LikelihoodRecode> mapOfLikelihoodRecodes;
        if (likelihoodRecodeMap.isEmpty()) {
            mapOfLikelihoodRecodes = new HashMap<String, LikelihoodRecode>();
        } else {
            mapOfLikelihoodRecodes = calculateLikelihoodRecodesProbabilities(trainingDataSet, likelihoodRecodeMap);
        }

        return mapOfLikelihoodRecodes;
    }

    private static Map<String, LikelihoodRecode> getListOfLikeHoodRecodesOnActorThreeName(DataSet trainingDataSet) {
        Map<String, LikelihoodRecode> likelihoodRecodeMap = new HashMap<String, LikelihoodRecode>();

        for (Recode recode : trainingDataSet.getListOfRecodes()) {
            String threeName = recode.getBudgetID();
            populateLikelihoodRecode(likelihoodRecodeMap, recode, threeName);
        }

        Map<String, LikelihoodRecode> mapOfLikelihoodRecodes;
        if (likelihoodRecodeMap.isEmpty()) {
            mapOfLikelihoodRecodes = new HashMap<String, LikelihoodRecode>();
        } else {
            mapOfLikelihoodRecodes = calculateLikelihoodRecodesProbabilities(trainingDataSet, likelihoodRecodeMap);
        }

        return mapOfLikelihoodRecodes;
    }

    private static Map<String, LikelihoodRecode> getListOfLikeHoodRecodesOnCountry(DataSet trainingDataSet) {
        Map<String, LikelihoodRecode> likelihoodRecodeMap = new HashMap<String, LikelihoodRecode>();

        for (Recode recode : trainingDataSet.getListOfRecodes()) {
            String country = recode.getCountry();
            populateLikelihoodRecode(likelihoodRecodeMap, recode, country);
        }

        Map<String, LikelihoodRecode> mapOfLikelihoodRecodes;
        if (likelihoodRecodeMap.isEmpty()) {
            mapOfLikelihoodRecodes = new HashMap<String, LikelihoodRecode>();
        } else {
            mapOfLikelihoodRecodes = calculateLikelihoodRecodesProbabilities(trainingDataSet, likelihoodRecodeMap);
        }

        return mapOfLikelihoodRecodes;
    }

    private static Map<String, LikelihoodRecode> getListOfLikeHoodRecodesOnDuration(DataSet trainingDataSet) {
        Map<String, LikelihoodRecode> likelihoodRecodeMap = new HashMap<String, LikelihoodRecode>();

        for (Recode recode : trainingDataSet.getListOfRecodes()) {
            String duration = recode.getDuration();
            populateLikelihoodRecode(likelihoodRecodeMap, recode, duration);
        }

        Map<String, LikelihoodRecode> mapOfLikelihoodRecodes;
        if (likelihoodRecodeMap.isEmpty()) {
            mapOfLikelihoodRecodes = new HashMap<String, LikelihoodRecode>();
        } else {
            mapOfLikelihoodRecodes = calculateLikelihoodRecodesProbabilities(trainingDataSet, likelihoodRecodeMap);
        }

        return mapOfLikelihoodRecodes;
    }

    private static Map<String, LikelihoodRecode> getListOfLikeHoodRecodesOnLanguage(DataSet trainingDataSet) {
        Map<String, LikelihoodRecode> likelihoodRecodeMap = new HashMap<String, LikelihoodRecode>();

        for (Recode recode : trainingDataSet.getListOfRecodes()) {
            String language = recode.getLanguage();
            populateLikelihoodRecode(likelihoodRecodeMap, recode, language);
        }

        Map<String, LikelihoodRecode> mapOfLikelihoodRecodes;
        if (likelihoodRecodeMap.isEmpty()) {
            mapOfLikelihoodRecodes = new HashMap<String, LikelihoodRecode>();
        } else {
            mapOfLikelihoodRecodes = calculateLikelihoodRecodesProbabilities(trainingDataSet, likelihoodRecodeMap);
        }

        return mapOfLikelihoodRecodes;
    }

    private static Map<String, LikelihoodRecode> calculateLikelihoodRecodesProbabilities(DataSet trainingDataSet,
            Map<String, LikelihoodRecode> likelihoodRecodeMap) {
        boolean isZeroResults = false;
        for (LikelihoodRecode likelihoodRecode : likelihoodRecodeMap.values()) {
            if (likelihoodRecode.getNumberOfPositiveResponse() == 0 || likelihoodRecode.getNumberOfNegativeResponse() == 0) {
                isZeroResults = true;
                break;
            }
        }

//        if (isZeroResults) {
//            for (LikelihoodRecode likelihoodRecode : likelihoodRecodeMap.values()) {
//                likelihoodRecode.increaseNumberOfPositiveResponse();
//                likelihoodRecode.increaseNumberOfNegativeResponse();
//            }
//        }

        for (LikelihoodRecode likelihoodRecode : likelihoodRecodeMap.values()) {
            double probability = ((double) (likelihoodRecode.getNumberOfPositiveResponse() + likelihoodRecode.getNumberOfNegativeResponse()) / (double) trainingDataSet.getListOfRecodes().size());
            likelihoodRecode.setCriteriaProbability(probability);
        }
        return likelihoodRecodeMap;
    }

    private static DataSet getTrainingDataset() {
        /**
         * rainy - 100, Overcast - 200, sunny - 300 hot - aaa, mild - bbb, cool
         * - ccc High - English, Normal - Hindi true - India, false - USA
         */
        Recode r1 = new Recode("Short", "Action", "D Most Popular", "Most Popular", "USA", "English", "High", false);
        Recode r2 = new Recode("Medium", "Action", "D Popular", "Popular", "USA", "English", "High", false);
        Recode r3 = new Recode("Short", "Adventure", "D Popular", "Average", "UK", "English", "Medium", true);
        Recode r4 = new Recode("Medium", "Action", "D Average", "Most Popular", "UK", "English", "Medium", true);
        Recode r5 = new Recode("Long", "Action", "D Most Popular", "Least", "USA", "English", "Medium", true);
        Recode r6 = new Recode("Medium", "Action", "D Average", "Popular", "USA", "English", "Low", false);
        Recode r7 = new Recode("Long", "Adventure", "D Least", "Average", "UK", "English", "Low", false);
        Recode r8 = new Recode("Long", "Action", "D Popular", "Most Popular", "USA", "English", "Low", false);
        Recode r9 = new Recode("Medium", "Adventure", "D Average", "Least", "USA", "English", "Low", true);
        Recode r10 = new Recode("Long", "Action", "D Most Popular", "Popular", "UK", "English", "Low", true);
        Recode r11 = new Recode("Short", "Action", "D Least", "Average", "USA", "English", "Medium", false);
        Recode r12 = new Recode("Short", "Action", "D Popular", "Most Popular", "UK", "English", "Medium", false);
        Recode r13 = new Recode("Medium", "Action", "D Least", "Popular", "USA", "English", "Medium", false);
        Recode r14 = new Recode("Short", "Adventure", "D Average", "Average", "UK", "English", "High", false);
        Recode r15 = new Recode("Medium", "Action", "D Most Popular", "Most Popular", "USA", "English", "Medium", false);

        List<Recode> recodeList = new ArrayList<Recode>();
        recodeList.add(r1);
        recodeList.add(r2);
        recodeList.add(r3);
        recodeList.add(r4);
        recodeList.add(r5);
        recodeList.add(r6);
        recodeList.add(r7);
        recodeList.add(r8);
        recodeList.add(r9);
        recodeList.add(r10);
        recodeList.add(r11);
        recodeList.add(r12);
        recodeList.add(r13);
        recodeList.add(r14);
        recodeList.add(r15);

        DataSet dataSet = new DataSet();
        dataSet.setListOfRecodes(recodeList);

        return dataSet;
    }
}
