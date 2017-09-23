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
public class NiveBaseResult {
   private double positiveLikelihood;
   private double negativeLikelihood;
   private double positiveProbability;
   private double negativeProbability;
   private Recode recode;
   private boolean isProfitable;

    public NiveBaseResult(double positiveLikelihood, double negativeLikelihood, double positiveProbability, double negativeProbability, Recode recode) {
        this.positiveLikelihood = positiveLikelihood;
        this.negativeLikelihood = negativeLikelihood;
        this.positiveProbability = positiveProbability;
        this.negativeProbability = negativeProbability;
        this.recode = recode;
    }

    public Recode getRecode() {
        return recode;
    }

    public void setRecode(Recode recode) {
        this.recode = recode;
    }

    public double getPositiveLikelihood() {
        return positiveLikelihood;
    }

    public void setPositiveLikelihood(double positiveLikelihood) {
        this.positiveLikelihood = positiveLikelihood;
    }

    public double getNegativeLikelihood() {
        return negativeLikelihood;
    }

    public void setNegativeLikelihood(double negativeLikelihood) {
        this.negativeLikelihood = negativeLikelihood;
    }

    public double getPositiveProbability() {
        return positiveProbability;
    }

    public void setPositiveProbability(double positiveProbability) {
        this.positiveProbability = positiveProbability;
    }

    public double getNegativeProbability() {
        return negativeProbability;
    }

    public void setNegativeProbability(double negativeProbability) {
        this.negativeProbability = negativeProbability;
    }

    public boolean isIsProfitable() {
        return isProfitable;
    }

    public void setIsProfitable(boolean isProfitable) {
        this.isProfitable = isProfitable;
    }
   
   
}
