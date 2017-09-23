package com.smile24es.ts_project.service;

import com.smile24es.ts_project.beans.Model.DataSet;
import com.smile24es.ts_project.beans.Model.LikelihoodRecode;
import com.smile24es.ts_project.beans.Model.LikelihoodTable;
import com.smile24es.ts_project.beans.Model.ListOfLikelihoodTables;
import com.smile24es.ts_project.beans.Model.NiveBaseResult;
import com.smile24es.ts_project.beans.Model.NiveBayearsTestResult;
import com.smile24es.ts_project.beans.Model.Recode;
import com.smile24es.ts_project.dao.LikelihoodRecodeDao;
import com.smile24es.ts_project.dao.LikelihoodTableDao;
import com.smile24es.ts_project.utill.NaiveBaseAlgo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("niveBayesService")
@Transactional
public class NiveBayesServiceImpl implements NiveBayesService {

    private static final Logger SL4J_LOGGER = LoggerFactory.getLogger(NiveBayesServiceImpl.class);

    @Autowired
    NaiveBaseAlgo naiveBaseAlgo;

    @Autowired
    LikelihoodRecodeDao likelihoodRecodeDao;

    @Autowired
    LikelihoodTableDao likelihoodTableDao;

    @Override
    public ListOfLikelihoodTables trainAlgo(DataSet dataSet) {
        likelihoodRecodeDao.deleteAllLikelihoodRecodes();
        likelihoodTableDao.deleteAllLikelihoodTables();
        ListOfLikelihoodTables listOfLikelihoodTables = naiveBaseAlgo.trainingNaiveBayesAlgo(dataSet);
        for (LikelihoodTable likelihoodTable : listOfLikelihoodTables.getListOfLikelihoodTables().values()) {
            Map<String, LikelihoodRecode> listOfLikelihoodRecode = likelihoodTable.getListOfLikelihoodRecode();
            com.smile24es.ts_project.beans.LikelihoodTable daoLikelihoodTable = new com.smile24es.ts_project.beans.LikelihoodTable();
            daoLikelihoodTable.setCriteria(likelihoodTable.getCriteria());
            daoLikelihoodTable.setTotalNegative(likelihoodTable.getTotalNegative());
            daoLikelihoodTable.setTotalPositive(likelihoodTable.getTotalPositive());
            daoLikelihoodTable.setNegativeProbability(likelihoodTable.getNegativeProbability());
            daoLikelihoodTable.setPositiveProbability(likelihoodTable.getPositiveProbability());
            likelihoodTableDao.saveLikelihoodTable(daoLikelihoodTable);
            for (LikelihoodRecode likelihoodRecode : listOfLikelihoodRecode.values()) {
                com.smile24es.ts_project.beans.LikelihoodRecode daoLickelihoodrecode = new com.smile24es.ts_project.beans.LikelihoodRecode();
                daoLickelihoodrecode.setPropertyName(likelihoodRecode.getProperty());
                daoLickelihoodrecode.setPositiveRecodes(likelihoodRecode.getNumberOfPositiveResponse());
                daoLickelihoodrecode.setNegativeRecodes(likelihoodRecode.getNumberOfNegativeResponse());
                daoLickelihoodrecode.setCriteriaProbability(likelihoodRecode.getCriteriaProbability());
                daoLickelihoodrecode.setLikelihoodTableId(daoLikelihoodTable);
                likelihoodRecodeDao.saveLikelihoodRecode(daoLickelihoodrecode);
            }

        }
        return listOfLikelihoodTables;
    }

    @Override
    public List<com.smile24es.ts_project.beans.LikelihoodTable> findAllLikelihoodTables() {
        return likelihoodTableDao.findAllLikelihoodTables();
    }

    @Override
    public NiveBaseResult getPrediction(Recode recode) {

        Map<String, LikelihoodTable> mapOfLikelihoodTables = new HashMap<>();

        List<com.smile24es.ts_project.beans.LikelihoodTable> daoLikelihoodTables = likelihoodTableDao.findAllLikelihoodTables();

        for (com.smile24es.ts_project.beans.LikelihoodTable daoLikelihoodtable : daoLikelihoodTables) {
            LikelihoodTable likelihoodTable = new LikelihoodTable();
            String criteria = daoLikelihoodtable.getCriteria();
            likelihoodTable.setCriteria(criteria);
            likelihoodTable.setTotalPositive(daoLikelihoodtable.getTotalPositive());
            likelihoodTable.setTotalNegative(daoLikelihoodtable.getTotalNegative());
            likelihoodTable.setPositiveProbability(daoLikelihoodtable.getPositiveProbability());
            likelihoodTable.setNegativeProbability(daoLikelihoodtable.getNegativeProbability());

            com.smile24es.ts_project.beans.LikelihoodTable likelihoodTable1 = new com.smile24es.ts_project.beans.LikelihoodTable();
            likelihoodTable1.setLikelihoodTableId(daoLikelihoodtable.getLikelihoodTableId());
            List<com.smile24es.ts_project.beans.LikelihoodRecode> listOflikelihoodRceodes = likelihoodRecodeDao.findLikelihoodRecodeByLikelihoodTable(likelihoodTable1);
            Map<String, LikelihoodRecode> likelihoodRecodeMap = new HashMap<>();
            for (com.smile24es.ts_project.beans.LikelihoodRecode daolikelihoodRecode : listOflikelihoodRceodes) {
                LikelihoodRecode likelihoodRecode = new LikelihoodRecode();
                likelihoodRecode.setCriteriaProbability(daolikelihoodRecode.getCriteriaProbability());
                likelihoodRecode.setNumberOfPositiveResponse(daolikelihoodRecode.getPositiveRecodes());
                likelihoodRecode.setNumberOfNegativeResponse(daolikelihoodRecode.getNegativeRecodes());
                String propertyName = daolikelihoodRecode.getPropertyName();
                likelihoodRecode.setProperty(propertyName);
                likelihoodRecodeMap.put(propertyName, likelihoodRecode);
            }
            likelihoodTable.setListOfLikelihoodRecode(likelihoodRecodeMap);
            mapOfLikelihoodTables.put(criteria, likelihoodTable);
        }

        ListOfLikelihoodTables listOfLikelihoodTables = new ListOfLikelihoodTables();
        listOfLikelihoodTables.setListOfLikelihoodTables(mapOfLikelihoodTables);

        NiveBaseResult naiveBaseResult = naiveBaseAlgo.getPrediction(listOfLikelihoodTables, recode);
        return naiveBaseResult;
    }

    @Override
    public NiveBayearsTestResult getTestResult(DataSet dataSet) {

        List<com.smile24es.ts_project.beans.LikelihoodTable> likelihoodtables = likelihoodTableDao.findAllLikelihoodTables();
        int totalPositivesOnTrainingDataset = 0;
        int totalNegativesOnTrainingDataset = 0;
        for (com.smile24es.ts_project.beans.LikelihoodTable likelihoodTable : likelihoodtables) {
            totalPositivesOnTrainingDataset += likelihoodTable.getTotalPositive();
            totalNegativesOnTrainingDataset += likelihoodTable.getTotalNegative();
        }
        int totalTrainingDataset = totalPositivesOnTrainingDataset + totalNegativesOnTrainingDataset;

        int totalTestDataset = 0;
        int totalPositivesInTestDataset = 0;
        int totalNegativesInTestDataset = 0;

        int totalFalsePositiveCount = 0;
        int totalFalseNegativeCount = 0;
        int totalUnsuccessCount = 0;
        double unsuccessProbability = 0.00;

        int totalSuccessPositiveCount = 0;
        int totalSuccessNegativeCount = 0;
        int totalSuccessCount = 0;
        double successProbability = 0.00;

        for (Recode recode : dataSet.getListOfRecodes()) {
            totalTestDataset++;
            boolean isProfitableActualy = recode.isProfitable();
            NiveBaseResult niveBaseResult = this.getPrediction(recode);
            boolean isProfitableInPrediction = niveBaseResult.isIsProfitable();

            if (isProfitableActualy) {
                totalPositivesInTestDataset++;
                if (isProfitableInPrediction) {
                    totalSuccessPositiveCount++;
                    totalSuccessCount++;
                } else {
                    totalFalseNegativeCount++;
                    totalUnsuccessCount++;
                }
            } else {
                totalNegativesInTestDataset++;
                if (isProfitableInPrediction) {
                    totalFalsePositiveCount++;
                    totalUnsuccessCount++;
                } else {
                    totalSuccessNegativeCount++;
                    totalSuccessCount++;
                }
            }

        }
        
        successProbability = (double)totalSuccessCount/totalTestDataset;
        unsuccessProbability = (double)totalUnsuccessCount/totalTestDataset;

        NiveBayearsTestResult niveBayearsTestResult = new NiveBayearsTestResult();
        niveBayearsTestResult.setTotalTrainingDataset(totalTrainingDataset);
        niveBayearsTestResult.setTotalPositivesInTrainingDataset(totalPositivesOnTrainingDataset);
        niveBayearsTestResult.setTotalNegativesInTrainingDataset(totalNegativesOnTrainingDataset);
        niveBayearsTestResult.setTotalTestDataset(totalTestDataset);
        niveBayearsTestResult.setTotalPositivesInTestDataset(totalPositivesInTestDataset);
        niveBayearsTestResult.setTotalNegativesInTestDataset(totalNegativesInTestDataset);
        niveBayearsTestResult.setTotalFalsePositiveCount(totalFalsePositiveCount);
        niveBayearsTestResult.setTotalFalseNegativeCount(totalFalseNegativeCount);
        niveBayearsTestResult.setTotalUnsuccessCount(totalUnsuccessCount);
        niveBayearsTestResult.setTotalSuccessPositiveCount(totalSuccessPositiveCount);
        niveBayearsTestResult.setTotalSuccessNegativeCount(totalSuccessNegativeCount);
        niveBayearsTestResult.setTotalSuccessCount(totalSuccessCount);
        niveBayearsTestResult.setSuccessProbability(successProbability);
        niveBayearsTestResult.setUnsuccessProbability(unsuccessProbability);

        return niveBayearsTestResult;//To change body of generated methods, choose Tools | Templates.
    }

}
