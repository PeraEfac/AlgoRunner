/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorunner.data;

import java.util.Objects;

/**
 *
 * @author Sandaruwan
 */
public class Leg implements Comparable<Leg> {

    float fromPipe;
    float tillPipe;

    String fromDate;
    String tillDate;

    Long id;
    boolean isUp;

    float pipeRatio;

    float userTolerance;

    public float getUserTolerance() {
        return userTolerance;
    }

    public void setUserTolerance(float userTolerance) {
        this.userTolerance = userTolerance;
    }

    public void setPipeRatio() {
        float timeDif = Float.parseFloat(fromDate.substring(8)) - Float.parseFloat(tillDate.substring(8));
        this.pipeRatio = (fromPipe - tillPipe) / timeDif;
        if (this.pipeRatio < 0) {
            this.pipeRatio = -this.pipeRatio;
        }
    }

    public float getPipeRatio() {
        return this.pipeRatio;
    }

    public boolean isIsUp() {
        return isUp;
    }

    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }

    public float getFromPipe() {
        return fromPipe;
    }

    public void setFromPipe(float fromPipe) {
        this.fromPipe = fromPipe;
    }

    public float getTillPipe() {
        return tillPipe;
    }

    public void setTillPipe(float tillPipe) {
        this.tillPipe = tillPipe;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Leg o) {
        return Long.compare(getId(), o.getId());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Leg other = (Leg) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
