package com.smile24es.ts_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smile24es.ts_project.beans.Model.DataSet;
import com.smile24es.ts_project.beans.Model.ListOfLikelihoodTables;
import com.smile24es.ts_project.beans.Model.NiveBaseResult;
import com.smile24es.ts_project.beans.Model.NiveBayearsTestResult;
import com.smile24es.ts_project.beans.Model.Recode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import com.smile24es.ts_project.service.NiveBayesService;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by hasithagamage on 5/15/17.
 */
@RestController
public class NaiveBayesRestController extends BaseRestController {

    private static final Logger SL4J_LOGGER = LoggerFactory.getLogger(NaiveBayesRestController.class);

    @Autowired
    NiveBayesService niveBayesService;

    @RequestMapping(value = "likelihood-tables", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity findAllLikelihoodTables() {
        SL4J_LOGGER.info("Starting find all likelihood-tables");
        List<com.smile24es.ts_project.beans.LikelihoodTable> likelihoodTables = niveBayesService.findAllLikelihoodTables();
        SL4J_LOGGER.info("All likelihood-tables are set to response object - {}", likelihoodTables);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(likelihoodTables);
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(NaiveBayesRestController.class.getName()).log(Level.SEVERE, null, ex);
            SL4J_LOGGER.error("String conversion error found in row data");
        }
        return new ResponseEntity<>(jsonInString, HttpStatus.OK);
    }

    @RequestMapping(value = "nive-bayers-predict", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity predictFromAlgo(@RequestBody Recode recode) {
        SL4J_LOGGER.info("Starting to nive-bayers-predict with recode [{}]", recode);
        NiveBaseResult niveByarseResult = niveBayesService.getPrediction(recode);
        return new ResponseEntity<>(niveByarseResult, HttpStatus.OK);
    }
    
    @RequestMapping(value = "traing-nive-bayers-algo", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity trainAlgo(@RequestBody DataSet dataSet) {
        SL4J_LOGGER.info("Starting to train algo with data set [{0}]", dataSet);
        ListOfLikelihoodTables likelihoodTables = niveBayesService.trainAlgo(dataSet);
        return new ResponseEntity<>(likelihoodTables, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "test-nive-bayers", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity testAlgo(@RequestBody DataSet dataSet) {
        SL4J_LOGGER.info("Starting to test algo with data set [{0}]", dataSet);
        NiveBayearsTestResult testResults = niveBayesService.getTestResult(dataSet);
        return new ResponseEntity<>(testResults, HttpStatus.OK);
    }
}
