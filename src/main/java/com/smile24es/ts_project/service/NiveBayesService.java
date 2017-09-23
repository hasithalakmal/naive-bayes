package com.smile24es.ts_project.service;

import com.smile24es.ts_project.beans.Model.DataSet;
import com.smile24es.ts_project.beans.Model.ListOfLikelihoodTables;
import com.smile24es.ts_project.beans.Model.NiveBaseResult;
import com.smile24es.ts_project.beans.Model.NiveBayearsTestResult;
import com.smile24es.ts_project.beans.Model.Recode;
import java.util.List;

public interface NiveBayesService {

    List<com.smile24es.ts_project.beans.LikelihoodTable> findAllLikelihoodTables();

    NiveBaseResult getPrediction(Recode recode);
    
    NiveBayearsTestResult getTestResult(DataSet dataSet);

    ListOfLikelihoodTables trainAlgo(DataSet dataSet);

}
