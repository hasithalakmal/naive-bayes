/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smile24es.ts_project.beans.Model;

/**
 *
 * @author hasithagamage
 */
public class NiveBayearsTestResult {

    private int totalTrainingDataset;
    private int totalPositivesInTrainingDataset;
    private int totalNegativesInTrainingDataset;

    private int totalTestDataset;
    private int totalPositivesInTestDataset;
    private int totalNegativesInTestDataset;

    private int totalFalsePositiveCount;
    private int totalFalseNegativeCount;
    private int totalUnsuccessCount;
    private double unsuccessProbability;

    private int totalSuccessPositiveCount;
    private int totalSuccessNegativeCount;
    private int totalSuccessCount;
    private double successProbability;

    public int getTotalTrainingDataset() {
        return totalTrainingDataset;
    }

    public void setTotalTrainingDataset(int totalTrainingDataset) {
        this.totalTrainingDataset = totalTrainingDataset;
    }

    public int getTotalTestDataset() {
        return totalTestDataset;
    }

    public void setTotalTestDataset(int totalTestDataset) {
        this.totalTestDataset = totalTestDataset;
    }

    public int getTotalFalsePositiveCount() {
        return totalFalsePositiveCount;
    }

    public void setTotalFalsePositiveCount(int totalFalsePositiveCount) {
        this.totalFalsePositiveCount = totalFalsePositiveCount;
    }

    public int getTotalFalseNegativeCount() {
        return totalFalseNegativeCount;
    }

    public void setTotalFalseNegativeCount(int totalFalseNegativeCount) {
        this.totalFalseNegativeCount = totalFalseNegativeCount;
    }

    public int getTotalUnsuccessCount() {
        return totalUnsuccessCount;
    }

    public void setTotalUnsuccessCount(int totalUnsuccessCount) {
        this.totalUnsuccessCount = totalUnsuccessCount;
    }

    public int getTotalSuccessPositiveCount() {
        return totalSuccessPositiveCount;
    }

    public void setTotalSuccessPositiveCount(int totalSuccessPositiveCount) {
        this.totalSuccessPositiveCount = totalSuccessPositiveCount;
    }

    public int getTotalSuccessNegativeCount() {
        return totalSuccessNegativeCount;
    }

    public void setTotalSuccessNegativeCount(int totalSuccessNegativeCount) {
        this.totalSuccessNegativeCount = totalSuccessNegativeCount;
    }

    public int getTotalSuccessCount() {
        return totalSuccessCount;
    }

    public void setTotalSuccessCount(int totalSuccessCount) {
        this.totalSuccessCount = totalSuccessCount;
    }

    public double getSuccessProbability() {
        return successProbability;
    }

    public void setSuccessProbability(double successProbability) {
        this.successProbability = successProbability;
    }

    public double getUnsuccessProbability() {
        return unsuccessProbability;
    }

    public void setUnsuccessProbability(double unsuccessProbability) {
        this.unsuccessProbability = unsuccessProbability;
    }

    public int getTotalPositivesInTrainingDataset() {
        return totalPositivesInTrainingDataset;
    }

    public void setTotalPositivesInTrainingDataset(int totalPositivesInTrainingDataset) {
        this.totalPositivesInTrainingDataset = totalPositivesInTrainingDataset;
    }

    public int getTotalNegativesInTrainingDataset() {
        return totalNegativesInTrainingDataset;
    }

    public void setTotalNegativesInTrainingDataset(int totalNegativesInTrainingDataset) {
        this.totalNegativesInTrainingDataset = totalNegativesInTrainingDataset;
    }

    public int getTotalPositivesInTestDataset() {
        return totalPositivesInTestDataset;
    }

    public void setTotalPositivesInTestDataset(int totalPositivesInTestDataset) {
        this.totalPositivesInTestDataset = totalPositivesInTestDataset;
    }

    public int getTotalNegativesInTestDataset() {
        return totalNegativesInTestDataset;
    }

    public void setTotalNegativesInTestDataset(int totalNegativesInTestDataset) {
        this.totalNegativesInTestDataset = totalNegativesInTestDataset;
    }

}
